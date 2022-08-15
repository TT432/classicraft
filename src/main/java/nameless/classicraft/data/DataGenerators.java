package nameless.classicraft.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nameless.classicraft.Classicraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(modid = Classicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            //generator.addProvider(new ModRecipes(generator));
            generator.addProvider(new ModLootTables(generator));
            //generator.addProvider(new TechTreeEntryProvider(generator));
            //ModBlockTagProvider blockTags = new ModBlockTagProvider(generator, event.getExistingFileHelper());
            //generator.addProvider(blockTags);
            //generator.addProvider(new ModItemTagProvider(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            //generator.addProvider(new ModBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new ModItemModels(generator, event.getExistingFileHelper()));
        }
    }
}