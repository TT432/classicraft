package nameless.classicraft.common.item;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttachFoods {
    static final Map<Item, FoodProperties> MAP = ImmutableMap.<Item, FoodProperties>builder()
            .put(Items.ROTTEN_FLESH, food(0, 0.0)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 45, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 45, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 45, 1), 1).build())
            .put(ModItems.ROTTEN_FOOD.get(), food(0, 0.0)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 45, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 45, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 45, 1), 1).build())
            .put(Items.SUGAR_CANE, food(2, 1.2).build())
            .put(Items.SUGAR, food(1, 0.6)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, 1), 1).build())
            .put(Items.FERMENTED_SPIDER_EYE, food(4, 6.4)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 4, 1), 1).build())
            .put(Items.EGG, food(2, 0.4).build())
            .put(Items.TURTLE_EGG, food(2, 0.4).build())
            .put(Items.BROWN_MUSHROOM, food(1, 0.2)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 15, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 15, 2), 1).build())
            .put(Items.RED_MUSHROOM, food(1, 0.2)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 4), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 30, 1), 1).build())
            .put(Items.CRIMSON_FUNGUS, food(1, 0.2)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 4), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 30, 1), 1).build())
            .put(Items.WARPED_FUNGUS, food(1, 0.2)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 15, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 15, 2), 1).build())
            .put(Items.GLISTERING_MELON_SLICE, food(4, 4.8)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 0, 1), 1).build())
            .put(ModItems.GLISTERING_MELON.get(), food(12, 4.8)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 0, 2), 1).build())
            .put(ModItems.COOKED_EGG.get(), food(6, 9.6).build())
            .put(ModItems.NETHER_MUSHROOM_STEW.get(), food(6, 7.2).build())
            .put(Items.MELON, food(6, 1.2).build())
            .put(Items.PUMPKIN, food(4, 1.2).build())
            .put(ModItems.CACTUS_FRUIT.get(), food(2, 0.4).build())
            .put(Items.CAKE, food(14, 2.8).build()).build();

    public static boolean isAttach(Item item) {
        return MAP.containsKey(item);
    }

    public static FoodProperties getFood(Item item) {
        return MAP.get(item);
    }

    static FoodProperties.Builder food(int n, double s) {
        return new FoodProperties.Builder().nutrition(n).saturationMod((float) (s / 2 / n));
    }
}