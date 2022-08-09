package nameless.classicraft.api;

import net.minecraft.world.item.ItemStack;

/**
 * @author DustW
 */
public interface RotReduceListener {
    float onRotReduce(ItemStack rotItem, float originRotValue);
}
