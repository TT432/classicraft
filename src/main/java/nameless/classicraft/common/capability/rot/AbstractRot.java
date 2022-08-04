package nameless.classicraft.common.capability.rot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import nameless.classicraft.common.rot.RotHolder;
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
    private final RotHolder holder;
    private boolean hasExMsg;
    private Function<AbstractRot, List<Component>> exMsg;

    @NotNull
    public List<Component> getMsg() {
        if (hasExMsg)
            return exMsg.apply(this);
        return List.of();
    }

    public RotHolder.RotLevel getLevel() {
        return getHolder().getLevel();
    }

    public abstract FoodType getType();

    public float getRotValue() {
        return holder.getCurrent();
    }

    public void setRotValue(float v) {
        holder.setCurrent(v);
    }

    public enum FoodType {
        MEET,
        PLANT,
        OTHER,
        NONE
    }
}
