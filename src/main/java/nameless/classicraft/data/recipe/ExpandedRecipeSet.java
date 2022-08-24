package nameless.classicraft.data.recipe;

import nameless.classicraft.Classicraft;
import nameless.classicraft.common.block.MushroomPlanterBlock;
import nameless.classicraft.common.item.ModItems;
import nameless.classicraft.common.recipe.ExpandedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tt432.millennium.data.recipe.AbstractRecipeSet;

/**
 * @author DustW
 */
public class ExpandedRecipeSet extends AbstractRecipeSet {
    static final Ingredient BONE_MEAL = Ingredient.of(Items.BONE_MEAL);
    static final Ingredient ROTTEN_FOOD = Ingredient.of(ModItems.ROTTEN_FOOD.get());

    @Override
    protected void addRecipes() {
        mushroomPlanter();
    }

    void mushroomPlanter() {
            for (MushroomPlanterBlock.Wood wood : MushroomPlanterBlock.WOODS) {
                ItemStack result = new ItemStack(ModItems.MUSHROOM_PLANTER.get());

                result.getOrCreateTag().putInt("dirt", MushroomPlanterBlock.Dirt.PODZOL.ordinal());
                result.getOrCreateTag().putInt("wood", wood.ordinal());

                ExpandedRecipe recipe = new ExpandedRecipe(NonNullList.of(Ingredient.EMPTY,
                        Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY,
                        BONE_MEAL, Ingredient.of(MushroomPlanterBlock.WOOD_BLOCKS.inverse().get(wood)), BONE_MEAL,
                        ROTTEN_FOOD, Ingredient.of(MushroomPlanterBlock.DIRT_BLOCKS.inverse().get(MushroomPlanterBlock.Dirt.PODZOL)), ROTTEN_FOOD), result);

                recipe.type = Classicraft.MOD_ID + ":expanded_recipe";

                addRecipe(new ResourceLocation(Classicraft.MOD_ID, "mushroomw%dd%d".formatted(wood.ordinal(), MushroomPlanterBlock.Dirt.PODZOL.ordinal())),
                        baseRecipe(recipe), "expanded_recipe");
            }
    }
}
