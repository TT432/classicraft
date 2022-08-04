package nameless.classicraft.common.block.entity.crop;

import nameless.classicraft.common.block.entity.RotAbleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * @author DustW
 */
public class CropBlockEntity extends RotAbleBlockEntity {
    public CropBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    protected IntegerProperty age() {
        return ((CropBlock) getBlockState().getBlock()).getAgeProperty();
    }

    protected boolean isMax() {
        return ((CropBlock) getBlockState().getBlock()).isMaxAge(getBlockState());
    }

    @Override
    public void tick() {
        if (isMax()) {
            super.tick();
        }
        else {
            if (getHolder().getCurrent() != 0)
                getHolder().setCurrent(0);
        }
    }

    @Override
    protected void replaceOnRot(Level level) {
        level.setBlock(getBlockPos(), getBlockState().setValue(age(), 0), Block.UPDATE_ALL_IMMEDIATE);

        getHolder().setCurrent(getHolder().getMax());
    }
}
