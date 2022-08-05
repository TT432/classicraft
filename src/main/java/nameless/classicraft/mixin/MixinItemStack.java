package nameless.classicraft.mixin;

import com.mojang.datafixers.util.Pair;
import nameless.classicraft.api.CCItemStack;
import nameless.classicraft.common.capability.ModCapabilities;
import nameless.classicraft.common.rot.RotHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author DustW
 */
@Mixin(value = ItemStack.class)
public abstract class MixinItemStack implements CCItemStack {
    @Shadow public abstract boolean isEmpty();

    @Shadow public abstract Item getItem();

    @Shadow @Nullable public abstract CompoundTag getTag();

    @Shadow public abstract int getCount();

    @Shadow public abstract int getMaxStackSize();

    @Shadow public abstract void grow(int pIncrement);

    @Shadow public abstract void shrink(int pDecrement);

    @Shadow public abstract Component getHoverName();

    @Inject(method = "overrideOtherStackedOnMe", cancellable = true, at = @At("HEAD"))
    private void overrideOtherStackedOnMeCC(ItemStack other, Slot pSlot, ClickAction pAction,
                                            Player pPlayer, SlotAccess pAccess, CallbackInfoReturnable<Boolean> cir) {

        if (other.isEmpty() || isEmpty() || getItem() != other.getItem() || !Objects.equals(getTag(), other.getTag())) {
            cir.setReturnValue(false);
            return;
        }

        other.getCapability(ModCapabilities.ROT).ifPresent(rotOther -> {
            int count = other.getCount();

            getCapability(ModCapabilities.ROT).ifPresent(rotSelf -> {
                int selfCount = getCount();

                int finalCount = Math.min(count, getMaxStackSize() - selfCount);
                var finalRot = (rotOther.getRotValue() - rotSelf.getRotValue()) * finalCount / (selfCount + finalCount);

                other.shrink(finalCount);
                grow(finalCount);

                rotSelf.setRotValue(finalRot + rotSelf.getRotValue());

                cir.setReturnValue(true);
            });
        });
    }

    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    private void finishUsingItemCC(Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        getCapability(ModCapabilities.ROT).ifPresent(rot -> {
            FoodProperties properties = getFoodProperties(entity);

            if (properties != null) {
                if (!level.isClientSide) {
                    for (Pair<MobEffectInstance, Float> pair : properties.getEffects()) {
                        if (pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()){
                            entity.addEffect(new MobEffectInstance(pair.getFirst()));
                        }
                    }

                    int foodLevelModifier;
                    float saturationLevelModifier;

                    if (getItem() == Items.ROTTEN_FLESH) {
                        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60 * 20, 2));
                        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60 * 20, 2));
                        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60 * 20, 1));
                        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * 20, 2));

                        foodLevelModifier = 0;
                        saturationLevelModifier = 0;
                    }
                    else {
                        switch (rot.getLevel()) {
                            case STALE -> {
                                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 15 * 20));

                                foodLevelModifier = (int) (properties.getNutrition() * .75);
                                saturationLevelModifier = properties.getSaturationModifier() * .75F;
                            }
                            case SPOILED -> {
                                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 1));
                                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 30 * 20));

                                foodLevelModifier = (int) (properties.getNutrition() * .5);
                                saturationLevelModifier = properties.getSaturationModifier() * .5F;
                            }
                            case ROT -> {
                                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 45 * 20, 2));
                                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 45 * 20, 1));
                                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 45 * 20));

                                foodLevelModifier = (int) (properties.getNutrition() * .25);
                                saturationLevelModifier = properties.getSaturationModifier() * .25F;
                            }
                            default -> {
                                foodLevelModifier = 0;
                                saturationLevelModifier = 0;
                            }
                        }
                    }

                    boolean needShirk = false;

                    if (entity instanceof Player player && foodLevelModifier != 0 && saturationLevelModifier != 0) {
                        player.getFoodData().eat(foodLevelModifier, saturationLevelModifier);

                        if (!player.getAbilities().instabuild) {
                            needShirk = true;
                        }
                    }

                    if (needShirk) {
                        shrink(1);
                    }
                }

                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F,
                        level.random.nextFloat() * 0.1F + 0.9F);

                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        entity.getEatingSound((ItemStack) (Object) this), SoundSource.NEUTRAL,
                        1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);

                cir.setReturnValue((ItemStack) (Object) this);
            }
        });
    }

    @Inject(method = "hasCustomHoverName", cancellable = true, at = @At("HEAD"))
    private void hasCustomHoverNameCC(CallbackInfoReturnable<Boolean> cir) {
        if (getHoverName().getString().equals("腐烂食物")) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getBarWidth", cancellable = true, at = @At("HEAD"))
    private void getBarWidthCC(CallbackInfoReturnable<Integer> cir) {
        getCapability(ModCapabilities.ROT).ifPresent(rot -> {
            RotHolder holder = rot.getHolder();
            cir.setReturnValue(Math.round(holder.getCurrent() * 13.0F / holder.getMax()));
        });
    }

    @Inject(method = "getBarColor", cancellable = true, at = @At("HEAD"))
    private void getBarColorCC(CallbackInfoReturnable<Integer> cir) {
        getCapability(ModCapabilities.ROT).ifPresent(rot -> {
            RotHolder holder = rot.getHolder();
            float f = Math.max(0.0F, holder.getCurrent() / holder.getMax());
            cir.setReturnValue(Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F));
        });
    }

    @Inject(method = "isBarVisible", cancellable = true, at = @At("HEAD"))
    private void isBarVisibleCC(CallbackInfoReturnable<Boolean> cir) {
        getCapability(ModCapabilities.ROT).ifPresent(rot -> {
            if (rot.getRotValue() < rot.getHolder().getMax()) {
                cir.setReturnValue(true);
            }
        });
    }
}
