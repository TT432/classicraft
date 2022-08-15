package nameless.classicraft.mixin;

import nameless.classicraft.common.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DustW
 */
@Mixin(BlockItem.class)
public abstract class MixinBlockItem extends Item {
    public MixinBlockItem(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "getBlockEntityData", at = @At("RETURN"), cancellable = true)
    private static void getBlockEntityDataCC(ItemStack stack, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag returnValue = cir.getReturnValue();

        if (returnValue == null) {
            returnValue = new CompoundTag();
        }

        CompoundTag finalReturnValue = returnValue;
        stack.getCapability(ModCapabilities.ROT).ifPresent(rot -> {
            finalReturnValue.putFloat("rot", rot.getRotValue());
            cir.setReturnValue(finalReturnValue);
        });
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (itemstack.is(Items.TURTLE_EGG) && pPlayer.isShiftKeyDown()) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                ThrownEgg thrownegg = new ThrownEgg(pLevel, pPlayer) {
                    @Override
                    protected void onHit(HitResult pResult) {
                        HitResult.Type hitresult$type = pResult.getType();
                        if (hitresult$type == HitResult.Type.ENTITY) {
                            this.onHitEntity((EntityHitResult)pResult);
                        } else if (hitresult$type == HitResult.Type.BLOCK) {
                            this.onHitBlock((BlockHitResult)pResult);
                        }

                        if (hitresult$type != HitResult.Type.MISS) {
                            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                        }
                        if (!this.level.isClientSide) {
                            if (this.random.nextInt(8) == 0) {
                                int i = 1;
                                if (this.random.nextInt(32) == 0) {
                                    i = 4;
                                }

                                for(int j = 0; j < i; ++j) {
                                    var chicken = EntityType.TURTLE.create(this.level);
                                    assert chicken != null;
                                    chicken.setAge(-24000);
                                    chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                                    this.level.addFreshEntity(chicken);
                                }
                            }

                            this.level.broadcastEntityEvent(this, (byte)3);
                            this.discard();
                        }
                    }
                };
                thrownegg.setItem(itemstack);
                thrownegg.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(thrownegg);
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        }

        return super.use(pLevel, pPlayer, pHand);
    }
}
