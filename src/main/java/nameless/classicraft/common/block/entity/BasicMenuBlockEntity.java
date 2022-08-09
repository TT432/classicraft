package nameless.classicraft.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author DustW
 **/
public abstract class BasicMenuBlockEntity extends BasicBlockEntity implements MenuProvider {

    protected BasicMenuBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(defaultName());
    }

    String name;

    protected String defaultName() {
        return name == null ? setDefaultName() : name;
    }

    protected String setDefaultName() {
        ResourceLocation registryName = getType().getRegistryName();
        if (registryName != null)
            name = "container." + registryName.toString().replace("/", ".");
        return name;
    }
}
