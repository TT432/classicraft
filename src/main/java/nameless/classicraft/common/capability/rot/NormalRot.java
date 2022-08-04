package nameless.classicraft.common.capability.rot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * @author DustW
 */
public class NormalRot extends AbstractRot {

    /** 天（秒） */
    static int d(int t) {
        return t * 24000 / 20;
    }

    /** 时（秒） */
    static int h(int t) {
        return d(t) / 24;
    }

    static final Map<Item, Integer> MEET_LIST = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Items.BEEF, d(3)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_BEEF, d(2)),
            new AbstractMap.SimpleEntry<>(Items.SALMON, d(1)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_SALMON, d(2)),
            new AbstractMap.SimpleEntry<>(Items.MUTTON, d(3)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_MUTTON, d(2)),
            new AbstractMap.SimpleEntry<>(Items.TROPICAL_FISH, d(2)),
            new AbstractMap.SimpleEntry<>(Items.PUFFERFISH, d(2)),
            new AbstractMap.SimpleEntry<>(Items.COD, d(1)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_COD, d(2)),
            new AbstractMap.SimpleEntry<>(Items.CHICKEN, d(1)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_CHICKEN, d(2)),
            new AbstractMap.SimpleEntry<>(Items.PORKCHOP, d(3)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_PORKCHOP, d(1)),
            new AbstractMap.SimpleEntry<>(Items.RABBIT, d(1)),
            new AbstractMap.SimpleEntry<>(Items.COOKED_RABBIT, d(2)),
            new AbstractMap.SimpleEntry<>(Items.RABBIT_STEW, h(8)),
            new AbstractMap.SimpleEntry<>(Items.SPIDER_EYE, d(7))
    );

    static final Map<Item, Integer> PLANT_LIST = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Items.POTATO, d(65)),
            new AbstractMap.SimpleEntry<>(Items.BAKED_POTATO, d(2)),
            new AbstractMap.SimpleEntry<>(Items.GLOW_BERRIES, d(1)),
            new AbstractMap.SimpleEntry<>(Items.BEETROOT, d(30)),
            new AbstractMap.SimpleEntry<>(Items.MELON_SLICE, h(4)),
            new AbstractMap.SimpleEntry<>(Items.APPLE, d(15)),
            new AbstractMap.SimpleEntry<>(Items.CARROT, d(7)),
            new AbstractMap.SimpleEntry<>(Items.DRIED_KELP, d(180)),
            new AbstractMap.SimpleEntry<>(Items.BREAD, d(180)),
            new AbstractMap.SimpleEntry<>(Items.COOKIE, d(15)),
            new AbstractMap.SimpleEntry<>(Items.SWEET_BERRIES, d(1)),
            new AbstractMap.SimpleEntry<>(Items.CAKE, d(1)),
            new AbstractMap.SimpleEntry<>(Items.WHEAT, d(1095)),
            new AbstractMap.SimpleEntry<>(Items.PUMPKIN, d(30)),
            new AbstractMap.SimpleEntry<>(Items.PUMPKIN_PIE, d(1))
    );

    static final Map<Item, Integer> OTHER_LIST = Map.of(
            Items.CHORUS_FRUIT, d(1),
            Items.POISONOUS_POTATO, d(7),
            Items.HONEY_BOTTLE, d(540),
            Items.GOLDEN_APPLE, d(72),
            Items.GOLDEN_CARROT, d(8),
            Items.ENCHANTED_GOLDEN_APPLE, d(648),
            Items.MUSHROOM_STEW, d(3),
            Items.BEETROOT_SOUP, d(5),
            Items.SUSPICIOUS_STEW, d(3)
    );

    public static int getSecond(Item item) {
        return MEET_LIST.getOrDefault(item, PLANT_LIST.getOrDefault(item, OTHER_LIST.get(item)));
    }

    public static boolean canUse(ItemStack stack) {
        return MEET_LIST.containsKey(stack.getItem()) ||
                PLANT_LIST.containsKey(stack.getItem()) ||
                OTHER_LIST.containsKey(stack.getItem());
    }

    ItemStack food;
    FoodType type;

    public NormalRot(ItemStack food) {
        super(getSecond(food.getItem()), getSecond(food.getItem()), true, rot -> {
            var prefix = new TextComponent(" 当前新鲜度");

            return List.of(new TextComponent("新鲜度:"), switch (rot.getLevel()) {
                case FRESH -> prefix.append(new TextComponent("新鲜").withStyle(ChatFormatting.GREEN));
                case STALE -> prefix.append(new TextComponent("陈旧").withStyle(ChatFormatting.YELLOW));
                case SPOILED -> prefix.append(new TextComponent("变质").withStyle(ChatFormatting.GOLD));
                case ROT -> prefix.append(new TextComponent("腐坏").withStyle(ChatFormatting.RED));
            });
        });

        this.food = food;

        if (MEET_LIST.containsKey(food.getItem())) {
            type = FoodType.MEET;
        }
        else if (PLANT_LIST.containsKey(food.getItem())) {
            type = FoodType.PLANT;
        }
        else if (OTHER_LIST.containsKey(food.getItem())) {
            type = FoodType.OTHER;
        }
        else {
            type = FoodType.NONE;
        }
    }

    @Override
    public RotLevel getLevel() {
        float percent = getRotPercent();

        if (percent >= .75) {
            return RotLevel.FRESH;
        }
        else if (percent >= .51) {
            return RotLevel.STALE;
        }
        else if (percent >= .26) {
            return RotLevel.SPOILED;
        }
        else {
            return RotLevel.ROT;
        }
    }

    @Override
    public FoodType getType() {
        return type;
    }
}
