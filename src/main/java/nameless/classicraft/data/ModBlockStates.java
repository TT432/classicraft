package nameless.classicraft.data;

import nameless.classicraft.Classicraft;
import nameless.classicraft.common.block.ModBlocks;
import nameless.classicraft.common.block.MushroomPlanterBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author DustW
 **/
public class ModBlockStates extends BlockStateProvider {

    ExistingFileHelper ex;

    public ModBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Classicraft.MOD_ID, helper);
        this.ex = helper;
    }

    @Override
    protected void registerStatesAndModels() {
        VariantBlockStateBuilder.PartialBlockstate state =
                getVariantBuilder(ModBlocks.MUSHROOM_PLANTER.get()).partialState();

        for (int i = 0; i <= 3; i++) {
            for (int i1 = 0; i1 <= 1; i1++) {
                for (int i2 = 0; i2 <= 5; i2++) {
                    for (int i3 = 0; i3 <= 1; i3++) {
                        ConfiguredModel[] models = mushroomModel(i, i1, i2, i3);
                        Iterator<ConfiguredModel> it = Arrays.stream(models).iterator();

                        for (Direction direction : Direction.Plane.HORIZONTAL) {
                            state.with(MushroomPlanterBlock.GROW_STATE, i)
                                    .with(MushroomPlanterBlock.DIRT, i1)
                                    .with(MushroomPlanterBlock.WOOD, i2)
                                    .with(MushroomPlanterBlock.MUSHROOM, i3)
                                    .with(MushroomPlanterBlock.FACING, direction)
                                    .addModels(it.next());
                        }
                    }
                }
            }
        }
    }

    ConfiguredModel[] mushroomModel(int gs, int d, int w, int m) {
        boolean isRed = m == MushroomPlanterBlock.Mushroom.RED.ordinal();
        return ConfiguredModel.allYRotations(new ModelFile.ExistingModelFile(
                new ResourceLocation(Classicraft.MOD_ID,
                "block/mushroom_planter/" + "mushroom_planter_" +
                        MushroomPlanterBlock.WOODS[w].name().toLowerCase(Locale.ROOT) +
                        "_" + MushroomPlanterBlock.DIRTS[d].name().toLowerCase(Locale.ROOT) +
                        "_" + (isRed ? "red_mushroom" : "brown_mushroom") + "_" + gs
        ), ex), 0, false);
    }

    public static final ResourceLocation TOP = new ResourceLocation(Classicraft.MOD_ID, "block/top");

    void simpleSideBlock(RegistryObject<? extends Block> block) {
        String name = block.getId().getPath();
        ResourceLocation texture = blockTexture(block.get());
        BlockModelBuilder model = models().cubeBottomTop(name, texture, TOP, TOP);
        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(model));
    }

    ResourceLocation withSuffix(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

}