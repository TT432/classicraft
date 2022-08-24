package nameless.classicraft.common.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nameless.classicraft.Classicraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import tt432.millennium.common.recipes.base.BaseRecipe;
import tt432.millennium.common.recipes.base.Recipe;

/**
 * @author DustW
 */
@Recipe(Classicraft.MOD_ID + ":expanded_recipe")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpandedRecipe extends BaseRecipe<CraftingContainer> implements CraftingRecipe {
    NonNullList<Ingredient> inputs;
    ItemStack result;

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        int size = pContainer.getContainerSize();
        int size1 = inputs.size();

        for (int i = 0; i < size && i < size1; i++) {
            if (!inputs.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    public NonNullList<Ingredient> getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }
}
