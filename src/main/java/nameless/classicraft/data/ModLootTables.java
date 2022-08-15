package nameless.classicraft.data;

import nameless.classicraft.common.block.ModBlocks;
import nameless.classicraft.common.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 **/
public class ModLootTables extends BaseLootTableProvider {

    public ModLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        simple(ModBlocks.FRIDGE);
        simple(ModBlocks.GLISTERING_MELON);

        LootPool.Builder builder = LootPool.lootPool()
                .name(ModBlocks.CACTUS_FRUIT.getId().toString())
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ModItems.CACTUS_FRUIT.get()))
                .add(LootItem.lootTableItem(Items.CACTUS));

        lootTables.put(ModBlocks.CACTUS_FRUIT.get(), LootTable.lootTable().withPool(builder));
    }

    void simple(RegistryObject<? extends Block> block) {
        lootTables.put(block.get(), createSimpleTable(block.getId().getPath(), block.get()));
    }
}