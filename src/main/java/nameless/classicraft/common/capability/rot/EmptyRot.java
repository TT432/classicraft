package nameless.classicraft.common.capability.rot;

import java.util.List;

/**
 * @author DustW
 */
public class EmptyRot extends AbstractRot {
    public EmptyRot() {
        super(0, 0, false, r -> List.of());
    }

    @Override
    public RotLevel getLevel() {
        return RotLevel.ROT;
    }

    @Override
    public FoodType getType() {
        return FoodType.NONE;
    }
}
