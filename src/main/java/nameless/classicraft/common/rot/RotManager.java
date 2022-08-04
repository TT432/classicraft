package nameless.classicraft.common.rot;

import nameless.classicraft.common.capability.ModCapabilities;
import nameless.classicraft.common.capability.rot.EmptyRot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public enum RotManager {
    INSTANCE;

    Queue<Runnable> buffer = new ArrayDeque<>();

    List<Info> infos = new ArrayList<>();

    public void addInfo(Info info) {
        buffer.add(() -> infos.add(info));
    }

    public void removeInfo(Info info) {
        buffer.add(() -> infos.remove(info));
    }

    Map<BlockPos, Info> byPos = new HashMap<>();

    public void addInfoByPos(BlockPos pos, Info info) {
        buffer.add(() -> {
            byPos.put(pos, info);
            infos.add(info);
        });
    }

    public void removeInfoByPos(BlockPos pos) {
        buffer.add(() -> infos.remove(byPos.remove(pos)));
    }

    @FunctionalInterface
    public interface SetAction {
        void set(IItemHandler handler, int slot, ItemStack stack);

        SetAction IMPL = (inv, num, stack) -> {
            inv.extractItem(num, 64, false);
            inv.insertItem(num, stack, false);
        };
    }

    public record Info(List<IItemHandler> handlers,
                       @NotNull Supplier<Level> level,
                       @NotNull Supplier<Vec3> pos,
                       @Nullable ItemEntity entity,
                       @NotNull Supplier<BlockState> block,
                       SetAction action,
                       boolean needLevelNotNull) {
        public static Info blockEntity(List<IItemHandler> handlers,
                                Supplier<Level> level,
                                BlockPos pos,
                                Supplier<BlockState> block) {
            return new Info(handlers, level, () -> new Vec3(pos.getX(), pos.getY(), pos.getZ()),
                    null, block, SetAction.IMPL, true);
        }

        public static Info itemEntity(ItemEntity entity) {
            return new Info(List.of(new ItemStackHandler(NonNullList.of(ItemStack.EMPTY, entity.getItem()))),
                    entity::getLevel, entity::position, entity, () -> null, (h, n, s) -> entity.setItem(s), true);
        }

        public static Info playerInv(Container container, Player player) {
            return new Info(List.of(new InvWrapper(container)), player::getLevel, player::position,
                    null, () -> null, SetAction.IMPL, true);
        }

        public static Info enderChest(Container container) {
            return new Info(List.of(new InvWrapper(container)), () -> null, () -> null, null,
                    Blocks.ENDER_CHEST::defaultBlockState, SetAction.IMPL, false);
        }
    }

    public void second() {
        for (int j = 0; j < infos.size(); j++) {
            Info info = infos.get(j);

            if (info.needLevelNotNull && info.level.get() == null) {
                continue;
            }

            float finalSpeed = baseSpeed() *
                    dimCoefficient(info.level.get()) *
                    biomeCoefficient(info.level.get(), info.pos.get()) *
                    containerCoefficient(info.block.get()) *
                    drop(info.entity);

            for (IItemHandler handler : info.handlers) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack inSlot = handler.getStackInSlot(i);

                    int finalI = i;
                    inSlot.getCapability(ModCapabilities.ROT).ifPresent(rot -> {
                        if (rot instanceof EmptyRot || inSlot.isEmpty()) {
                            return;
                        }

                        rot.setRotValue(Math.max(0, rot.getRotValue() - finalSpeed));

                        if (rot.getRotValue() <= 0) {
                            info.action.set(handler, finalI, switch (rot.getType()) {
                                case MEET -> new ItemStack(Items.ROTTEN_FLESH, inSlot.getCount());
                                case PLANT -> new ItemStack(Items.BONE_MEAL, inSlot.getCount());
                                default -> ItemStack.EMPTY;
                            });
                        }
                    });
                }
            }
        }

        while (buffer.peek() != null) {
            buffer.poll().run();
        }
    }

    float baseSpeed() {
        return 1;
    }

    float dimCoefficient(Level level) {
        if (level == null) {
            return 1;
        }

        if (level.dimension() == Level.NETHER) {
            return 2;
        }

        return 1;
    }

    float biomeCoefficient(Level level, Vec3 pos) {
        if (level == null || pos == null) {
            return 1;
        }

        Biome biome = level.getBiome(new BlockPos(pos)).value();

        if (biome.getBaseTemperature() < .31) {
            return .7F;
        }

        if (biome.getBaseTemperature() > .99) {
            return 1.25F;
        }

        return 1;
    }

    float containerCoefficient(BlockState container) {
        if (container == null) {
            return 1;
        }

        if (container.is(Blocks.ENDER_CHEST)) {
            return 1.25F;
        }
        else if (container.is(Blocks.SHULKER_BOX)) {
            return .75F;
        }

        return 1;
    }

    float drop(ItemEntity entity) {
        if (entity != null) {
            return 1.5F;
        }

        return 1;
    }
}
