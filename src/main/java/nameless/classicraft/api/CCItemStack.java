package nameless.classicraft.api;

import nameless.classicraft.common.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 */
public interface CCItemStack extends IForgeItemStack {

    @Nullable
    @Override
    default CompoundTag getShareTag() {
        CompoundTag returnValue = IForgeItemStack.super.getShareTag();

        if (returnValue == null) {
            returnValue = new CompoundTag();
        }

        CompoundTag finalReturnValue = returnValue;
        getCapability(ModCapabilities.ROT).ifPresent(rot ->
                finalReturnValue.putFloat("rot", rot.getRotValue()));

        return returnValue;
    }

    @Override
    default void readShareTag(@Nullable CompoundTag nbt) {
        IForgeItemStack.super.readShareTag(nbt);

        if (nbt != null && nbt.contains("rot")) {
            getCapability(ModCapabilities.ROT).ifPresent(rot ->
                    rot.setRotValue(nbt.getFloat("rot")));
        }
    }
}
