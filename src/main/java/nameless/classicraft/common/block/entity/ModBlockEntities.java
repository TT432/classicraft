package nameless.classicraft.common.block.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nameless.classicraft.Classicraft;
import nameless.classicraft.common.block.entity.crop.CarrotBlockEntity;
import nameless.classicraft.common.block.entity.crop.PotatoBlockEntity;
import nameless.classicraft.common.block.entity.crop.WheatBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Classicraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<CakeBlockEntity>> CAKE = REGISTER.register("cake",
            () -> BlockEntityType.Builder.of(CakeBlockEntity::new, Blocks.CAKE).build(null));

    public static final RegistryObject<BlockEntityType<PumpkinBlockEntity>> PUMPKIN = REGISTER.register("pumpkin",
            () -> BlockEntityType.Builder.of(PumpkinBlockEntity::new, Blocks.PUMPKIN).build(null));

    public static final RegistryObject<BlockEntityType<WheatBlockEntity>> WHEAT = REGISTER.register("wheat",
            () -> BlockEntityType.Builder.of(WheatBlockEntity::new, Blocks.WHEAT).build(null));

    public static final RegistryObject<BlockEntityType<CarrotBlockEntity>> CARROT = REGISTER.register("carrot",
            () -> BlockEntityType.Builder.of(CarrotBlockEntity::new, Blocks.CARROTS).build(null));

    public static final RegistryObject<BlockEntityType<PotatoBlockEntity>> POTATO = REGISTER.register("potato",
            () -> BlockEntityType.Builder.of(PotatoBlockEntity::new, Blocks.POTATOES).build(null));
}
