package nameless.classicraft.data;

import nameless.classicraft.data.recipe.ExpandedRecipeSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import tt432.millennium.data.recipe.AbstractRecipeProvider;

import java.util.function.Consumer;

/**
 * @author DustW
 */
public class ModRecipeProvider extends AbstractRecipeProvider {
    protected ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void addCustomRecipes() {
        recipes.add(new ExpandedRecipeSet());
    }

    @Override
    protected void vanillaRecipes(Consumer<FinishedRecipe> consumer) {

    }
}
