package nameless.classicraft.common.capability.rot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * @author DustW
 */
public class NormalRot extends AbstractRot {

    static final List<Item> MEET_LIST = List.of(
            Items.BEEF, Items.COOKED_BEEF,
            Items.SALMON, Items.COOKED_SALMON, Items.MUTTON, Items.COOKED_MUTTON, Items.TROPICAL_FISH, Items.PUFFERFISH,
            Items.COD, Items.COOKED_COD,
            Items.CHICKEN, Items.COOKED_CHICKEN,
            Items.PORKCHOP, Items.COOKED_PORKCHOP,
            Items.RABBIT, Items.COOKED_RABBIT, Items.RABBIT_STEW,
            Items.SPIDER_EYE
    );

    static final List<Item> PLANT_LIST = List.of(
            Items.POTATO, Items.BAKED_POTATO,
            Items.GLOW_BERRIES,
            Items.BEETROOT,
            Items.MELON_SLICE,
            Items.APPLE,
            Items.CARROT,
            Items.DRIED_KELP,
            Items.BREAD,
            Items.COOKIE,
            Items.SWEET_BERRIES,
            Items.CAKE,
            Items.PUMPKIN_PIE
    );

    static final List<Item> OTHER_LIST = List.of(
            Items.CHORUS_FRUIT,
            Items.POISONOUS_POTATO,
            Items.HONEY_BOTTLE,
            Items.GOLDEN_APPLE,
            Items.GOLDEN_CARROT,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.MUSHROOM_STEW,
            Items.BEETROOT_SOUP
    );

    public static boolean canUse(ItemStack stack) {
        return MEET_LIST.contains(stack.getItem()) ||
                PLANT_LIST.contains(stack.getItem()) ||
                OTHER_LIST.contains(stack.getItem());
    }

    ItemStack food;
    FoodType type;

    public NormalRot(ItemStack food) {
        super(100, 100, true, rot -> {
            var prefix = new TextComponent(" 当前新鲜度");

            return List.of(new TextComponent("新鲜度:"), switch (rot.getLevel()) {
                case FRESH -> prefix.append(new TextComponent("新鲜").withStyle(ChatFormatting.GREEN));
                case STALE -> prefix.append(new TextComponent("陈旧").withStyle(ChatFormatting.YELLOW));
                case SPOILED -> prefix.append(new TextComponent("变质").withStyle(ChatFormatting.GOLD));
                case ROT -> prefix.append(new TextComponent("腐坏").withStyle(ChatFormatting.RED));
            });
        });

        this.food = food;

        if (MEET_LIST.contains(food.getItem())) {
            type = FoodType.MEET;
        }
        else if (PLANT_LIST.contains(food.getItem())) {
            type = FoodType.PLANT;
        }
        else if (OTHER_LIST.contains(food.getItem())) {
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
