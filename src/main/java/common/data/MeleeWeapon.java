package common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Melee weapon enum.
 */
public enum MeleeWeapon implements Serializable {
    MANREAPER,
    LIGHTNING_CLAW,
    POWER_FIST;

    /**
     * Method that shows enum list.
     * @return enum list in string format.
     */
    public static String nameList() {
    StringBuilder nameList = new StringBuilder();
    for (MeleeWeapon category : values()) {
        nameList.append(category.name()).append(", ");
    }
    return nameList.substring(0, nameList.length() - 2);
}

    public static ArrayList<String> nameArr() {
        ArrayList<String> arr = new ArrayList<>();
        for (MeleeWeapon category: values()) {
            arr.add(category.name());
        }
        return arr;
    }
}
