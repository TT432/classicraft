package nameless.classicraft;

import nameless.classicraft.common.block.entity.ModBlockEntities;
import nameless.classicraft.common.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlockEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
    }
}
