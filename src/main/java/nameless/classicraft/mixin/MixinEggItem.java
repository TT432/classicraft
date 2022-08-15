package nameless.classicraft.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DustW
 */
@Mixin(EggItem.class)
public class MixinEggItem extends Item {
    public MixinEggItem(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "use", cancellable = true, at = @At("HEAD"))
    private void useCC(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!pPlayer.isShiftKeyDown())
            cir.setReturnValue(super.use(pLevel, pPlayer, pHand));
    }
}
