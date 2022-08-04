package nameless.classicraft.common.rot;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author DustW
 */
@AllArgsConstructor
@Data
public class RotHolder {
    final float max;
    float current;

    public float getRotPercent() {
        return current / max;
    }

    public RotLevel getLevel() {
        float percent = getRotPercent();

        if (percent >= .75) {
            return RotLevel.FRESH;
        }
        else if (percent >= .51) {
            return RotLevel.STALE;
        }
        else if (percent >= .26) {
            return RotLevel.SPOILED;
        }
        else {
            return RotLevel.ROT;
        }
    }

    public enum RotLevel {
        FRESH,
        STALE,
        SPOILED,
        ROT
    }
}
