package vehiclesmodules;

public enum Colors {
    BLUE, WHITE, GREEN, GRAY, RED, YELLOW;

    public static Colors get(String color) {
        for (Colors c : Colors.values()) {
            if (color.equalsIgnoreCase(c.name())) return c;
        }
        return null;
    }
}
