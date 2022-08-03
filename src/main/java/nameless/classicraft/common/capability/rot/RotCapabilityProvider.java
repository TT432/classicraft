package nameless.classicraft.common.capability.rot;

import net.minecraft.core.Direction;
import net.minecraft.nbt.FloatTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 */
public class RotCapabilityProvider extends CapabilityProvider<RotCapabilityProvider> implements INBTSerializable<FloatTag> {

    LazyOptional<AbstractRot> cap;

    public RotCapabilityProvider(LazyOptional<AbstractRot> cap) {
        super(RotCapabilityProvider.class);

        this.cap = cap;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.cap.cast();
    }

    @Override
    public FloatTag serializeNBT() {
        return cap.map(abstractRot -> FloatTag.valueOf(abstractRot.getRotValue())).orElse(FloatTag.ZERO);
    }

    @Override
    public void deserializeNBT(FloatTag nbt) {
        cap.ifPresent(abstractRot -> abstractRot.setRotValue(nbt.getAsFloat()));
    }
}
