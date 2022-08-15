package nameless.classicraft.common.capability.rot;

import nameless.classicraft.common.rot.RotHolder;
import nameless.classicraft.common.rot.RotItems;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @author DustW
 */
public class NormalRot extends AbstractRot {

    public static int getSecond(Item item) {
        return RotItems.MAP.get(item);
    }

    public static boolean canUse(ItemStack stack) {
        return RotItems.MAP.containsKey(stack.getItem());
    }

    ItemStack food;

    public NormalRot(ItemStack food) {
        super(new RotHolder(getSecond(food.getItem()), getSecond(food.getItem())), true, rot ->
                List.of(new TextComponent("%.2f天后腐烂".formatted(rot.getHolder().getCurrent() / 24000)),
                        new TextComponent("速度%d%%".formatted((int) (rot.getFinalSpeed() * 100)))), 1);

        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof NormalRot rot && rot.food.equals(this.food);
    }

    @Override
    public int hashCode() {
        return food.hashCode();
    }
}
