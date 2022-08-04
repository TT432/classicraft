package nameless.classicraft.common.block.entity.crop;

import nameless.classicraft.common.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author DustW
 */
public class WheatBlockEntity extends CropBlockEntity {
    public WheatBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.WHEAT.get(), pWorldPosition, pBlockState);
    }
}
