package nameless.classicraft.common.capability.rot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import nameless.classicraft.common.rot.RotHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
    private float finalSpeed;

    @NotNull
    public List<Component> getMsg() {
        if (hasExMsg)
            return exMsg.apply(this);
        return List.of();
    }

    public RotHolder.RotLevel getLevel() {
        return getHolder().getLevel();
    }

    public MutableComponent getLevelName() {
        return switch (getLevel()) {
            case FRESH ->   new TranslatableComponent("level.classicraft.fresh");
            case STALE ->   new TranslatableComponent("level.classicraft.stale");
            case SPOILED -> new TranslatableComponent("level.classicraft.spoiled");
            case ROT ->     new TranslatableComponent("level.classicraft.rotten");
        };
    }

    public ChatFormatting getLevelNameColor() {
        return switch (getLevel()) {
            case FRESH ->   ChatFormatting.GREEN;
            case STALE ->   ChatFormatting.YELLOW;
            case SPOILED -> ChatFormatting.GOLD;
            case ROT ->     ChatFormatting.RED;
        };
    }

    public float getRotValue() {
        return holder.getCurrent();
    }

    public void setRotValue(float v) {
        holder.setCurrent(v);
    }

}