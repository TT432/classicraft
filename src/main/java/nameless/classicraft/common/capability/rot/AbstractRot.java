package nameless.classicraft.common.capability.rot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * @author DustW
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRot {
    private final float maxRotValue;
    private float rotValue;
    private boolean hasExMsg;
    private Function<AbstractRot, List<Component>> exMsg;

    public float getRotPercent() {
        return rotValue / maxRotValue;
    }

    @NotNull
    public List<Component> getMsg() {
        if (hasExMsg)
            return exMsg.apply(this);
        return List.of();
    }

    public abstract RotLevel getLevel();

    public enum RotLevel {
        FRESH,
        STALE,
        SPOILED,
        ROT
    }

    public abstract FoodType getType();

    public enum FoodType {
        MEET,
        PLANT,
        OTHER,
        NONE
    }
}
