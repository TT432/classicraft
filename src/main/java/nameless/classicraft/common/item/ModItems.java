package nameless.classicraft.common.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nameless.classicraft.Classicraft;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Classicraft.MOD_ID);

    public static final RegistryObject<Item> ROTTEN_FOOD = normal("rotten_food");

    private static RegistryObject<Item> normal(String name) {
        return REGISTER.register(name, () -> new Item(base()));
    }

    private static Item.Properties base() {
        return new Item.Properties().tab(Classicraft.TAB);
    }
}
