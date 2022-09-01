package nameless.classicraft;

import nameless.classicraft.common.block.ModBlocks;
import nameless.classicraft.common.block.entity.ModBlockEntities;
import nameless.classicraft.common.item.ModItems;
import nameless.classicraft.common.menu.ModMenuTypes;
import nameless.classicraft.event.PlayerUseItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Nameless
 */
@Mod(Classicraft.MOD_ID)
public class Classicraft {
    public static final String MOD_ID = "classicraft";
    public static final CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ROTTEN_FOOD.get());
        }
    };

    public Classicraft() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlockEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
        ModMenuTypes.REGISTER.register(bus);
        ModBlocks.REGISTER.register(bus);
        MinecraftForge.EVENT_BUS.register(new PlayerUseItem());
    }
}
