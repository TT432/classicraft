package nameless.classicraft;

import nameless.classicraft.common.block.entity.ModBlockEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Nameless
 */
@Mod(Classicraft.MOD_ID)
public class Classicraft {
    public static final String MOD_ID = "classicraft";

    public Classicraft() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlockEntities.REGISTER.register(bus);
    }
}
