package nameless.classicraft.common.block.entity;

import lombok.Getter;
import nameless.classicraft.api.TickAble;
import nameless.classicraft.common.rot.RotHolder;
import nameless.classicraft.common.rot.RotManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

/**
 * @author DustW
 */
public class RotAbleBlockEntity extends BlockEntity implements TickAble {
    static final Map<Block, Integer> TICK_MAP = Map.of(
            Blocks.CAKE, d(1),
            Blocks.MELON, d(15),
            Blocks.PUMPKIN, d(30),
            Blocks.WHEAT, d(7),
            Blocks.CARROTS, d(7),
            Blocks.POTATOES, d(30)
    );

    /** 天（秒） */
    static int d(int t) {
        return t * 24000 / 20;
    }

    @Getter
    RotHolder holder;

    public RotAbleBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);

        float v = TICK_MAP.get(pBlockState.getBlock());
        holder = new RotHolder(v, v);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putFloat("rot", holder.getCurrent());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("rot")) {
            holder.setCurrent(pTag.getFloat("rot"));
        }
    }

    int timer;

    protected void replaceOnRot(Level level) {
        level.removeBlock(getBlockPos(), false);
    }

    @Override
    public void tick() {
        if (getLevel() != null && !getLevel().isClientSide && timer++ > 20) {
            timer = 0;
            holder.setCurrent(Math.max(holder.getCurrent() - RotManager.INSTANCE.getFinalSpeed(getLevel(), getBlockPos(), getBlockState()), 0));

            if (holder.getCurrent() == 0) {
                replaceOnRot(getLevel());
            }
        }
    }
}
