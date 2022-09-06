package nameless.classicraft.common.block;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nameless.classicraft.Classicraft;
import nameless.classicraft.common.block.entity.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Classicraft.MOD_ID);

    public static final RegistryObject<Block> FRIDGE = REGISTER.register("fridge",
            () -> new FridgeBlock(wood(), ModBlockEntities.FRIDGE));
    public static final RegistryObject<Block> MUSHROOM_PLANTER = REGISTER.register("mushroom_planter",
            () -> new MushroomPlanterBlock(wood().noOcclusion(), ModBlockEntities.MUSHROOM_PLANTER));
    public static final RegistryObject<Block> GLISTERING_MELON = REGISTER.register("glistering_melon", () -> new GlisteringMelonBlock(melon()));
    public static final RegistryObject<Block> CACTUS_FRUIT = REGISTER.register("cactus_fruit", () -> new CactusFruitBlock(cactus()));
    public static final RegistryObject<Block> UNLIT_TORCH = REGISTER.register("unlit_torch", () -> new UnlitTorchBlock(torch()));
    public static final RegistryObject<Block> WALL_UNLIT_TORCH = REGISTER.register("wall_unlit_torch", () -> new WallUnlitTorchBlock(torch()));
    public static final RegistryObject<Block> UNLIT_SOUL_TORCH = REGISTER.register("unlit_soul_torch", () -> new UnlitTorchBlock(torch()));
    public static final RegistryObject<Block> WALL_UNLIT_SOUL_TORCH = REGISTER.register("wall_unlit_soul_torch", () -> new WallUnlitTorchBlock(torch()));

    static BlockBehaviour.Properties torch() {
        return BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD);
    }

    static BlockBehaviour.Properties wood() {
        return BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD);
    }

    static BlockBehaviour.Properties melon() {
        return BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_LIGHT_GREEN).strength(1.0F).sound(SoundType.WOOD);
    }

    static BlockBehaviour.Properties cactus() {
        return BlockBehaviour.Properties.of(Material.CACTUS).randomTicks().strength(0.4F).noOcclusion().sound(SoundType.WOOL);
    }
}
