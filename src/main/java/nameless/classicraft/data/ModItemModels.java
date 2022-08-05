package nameless.classicraft.data;

import nameless.classicraft.Classicraft;
import nameless.classicraft.common.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Classicraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //base();
        simpleTexture(ModItems.ROTTEN_FOOD);
    }

    void block(RegistryObject<? extends Block> block) {
        withExistingParent(block.getId().getPath(), blockName(block.getId()));
    }

    ResourceLocation blockName(ResourceLocation block) {
        return new ResourceLocation(block.getNamespace(), "block/" + block.getPath());
    }

    void base() {
        ForgeRegistries.ITEMS.forEach(item -> {
            if (item.getRegistryName().getNamespace().equals(modid)) {
                if (item instanceof BlockItem) {
                    withExistingParent(item.getRegistryName().getPath(), modLoc("block/" + item.getRegistryName().getPath()));
                }
                else {
                    simpleTexture(() -> item);
                }
            }
        });
    }

    final ResourceLocation POWDER = modLoc("item/powder");

    void powder(Supplier<Item> item) {
        singleTexture(item.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", POWDER);
    }

    final ResourceLocation GEAR = modLoc("item/gear");

    void gear(Supplier<Item> item) {
        singleTexture(item.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", GEAR);
    }

    void simpleTexture(Supplier<Item> itemSupplier) {
        String name = itemSupplier.get().getRegistryName().getPath();

        try {
            ResourceLocation texture = modLoc("item/" + name);

            if (existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
                singleTexture(name, mcLoc("item/generated"), "layer0", texture);
            }
        }
        catch (Exception ignored) {}
    }
}