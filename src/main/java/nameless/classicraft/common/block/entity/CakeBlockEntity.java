package nameless.classicraft.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author DustW
 */
public class CakeBlockEntity extends RotAbleBlockEntity {

    public CakeBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.CAKE.get(), pWorldPosition, pBlockState);
    }
}
