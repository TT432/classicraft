package nameless.classicraft.mixin;

import nameless.classicraft.common.rot.RotManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
@Mixin(BlockEntity.class)
public abstract class MixinBlockEntity {
    @Shadow @Nullable public abstract Level getLevel();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initCC(BlockEntityType pType, BlockPos pWorldPosition, BlockState pBlockState, CallbackInfo ci) {
        if (this instanceof Container c) {
            RotManager.INSTANCE.addInfoByPos(pWorldPosition, RotManager.Info.blockEntity(
                    List.of(new InvWrapper(c)),
                    this::getLevel,
                    pWorldPosition,
                    () -> pBlockState
            ));
        }
    }
}
