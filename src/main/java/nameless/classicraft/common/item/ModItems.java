package nameless.classicraft.common.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nameless.classicraft.Classicraft;
import nameless.classicraft.common.block.ModBlocks;
import nameless.classicraft.common.block.UnlitCandleholderBlock;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Classicraft.MOD_ID);

    public static final RegistryObject<Item> FRIDGE = block(ModBlocks.FRIDGE);
    public static final RegistryObject<Item> UNLIT_LANTERN = block(ModBlocks.UNLIT_LANTERN);
    public static final RegistryObject<Item> UNLIT_SOUL_LANTERN = block(ModBlocks.UNLIT_SOUL_LANTERN);
    public static final RegistryObject<Item> MUSHROOM_PLANTER = block(ModBlocks.MUSHROOM_PLANTER);
    public static final RegistryObject<Item> GLISTERING_MELON = block(ModBlocks.GLISTERING_MELON);
    public static final RegistryObject<Item> NETHER_MUSHROOM_STEW = normal("nether_mushroom_stew");
    public static final RegistryObject<Item> CACTUS_FRUIT = normal("cactus_fruit");
    public static final RegistryObject<Item> COOKED_EGG = normal("cooked_egg");
    public static final RegistryObject<Item> ROTTEN_FOOD = normal("rotten_food", p -> p.food(new FoodProperties.Builder().build()));
    public static final RegistryObject<Item> CLASSIC_CRAFT = REGISTER.register("classic_craft", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLOCK_ROT_VIEWER = REGISTER.register("block_rot_viewer", () -> new BlockRotViewerItem(base()));
    public static final RegistryObject<Item> UNLIT_TORCH = REGISTER.register("unlit_torch", () -> new StandingAndWallBlockItem(
            ModBlocks.UNLIT_TORCH.get(), ModBlocks.WALL_UNLIT_TORCH.get(), base()));
    public static final RegistryObject<Item> UNLIT_SOUL_TORCH = REGISTER.register("unlit_soul_torch", () -> new StandingAndWallBlockItem(
            ModBlocks.UNLIT_SOUL_TORCH.get(), ModBlocks.WALL_UNLIT_SOUL_TORCH.get(), base()));

    // 批量添加不同材料和颜色的烛台物品
    public static final ArrayList<RegistryObject<Item>> UNLIT_CANDLEHOLDERS = new ArrayList<RegistryObject<Item>>(){{
        for (RegistryObject<Block> UNLIT_CANDLEHOLDER: ModBlocks.UNLIT_CANDLEHOLDERS)
                add(block(UNLIT_CANDLEHOLDER));
    }};

    public static final ArrayList<RegistryObject<Item>> UNLIT_LARGE_CANDLEHOLDERS = new ArrayList<RegistryObject<Item>>(){{
        for (RegistryObject<Block> UNLIT_LARGE_CANDLEHOLDER: ModBlocks.UNLIT_LARGE_CANDLEHOLDERS)
            add(block(UNLIT_LARGE_CANDLEHOLDER));
    }};

    public static final RegistryObject<Item> UNLIT_FIRE_BOW = block(ModBlocks.UNLIT_FIRE_BOW);
    public static final RegistryObject<Item> UNLIT_SOUL_FIRE_BOW = block(ModBlocks.UNLIT_SOUL_FIRE_BOW);
    public static final RegistryObject<Item> UNLIT_LARGE_FIRE_BOW = block(ModBlocks.UNLIT_LARGE_FIRE_BOW);
    public static final RegistryObject<Item> UNLIT_LARGE_SOUL_FIRE_BOW = block(ModBlocks.UNLIT_LARGE_SOUL_FIRE_BOW);

    private static RegistryObject<Item> normal(String name) {
        return normal(name, p -> p);
    }

    private static RegistryObject<Item> normal(String name, Function<Item.Properties, Item.Properties> func) {
        return REGISTER.register(name, () -> new Item(func.apply(base())));
    }

    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), base()));
    }

    private static Item.Properties base() {
        return new Item.Properties().tab(Classicraft.TAB);
    }

}