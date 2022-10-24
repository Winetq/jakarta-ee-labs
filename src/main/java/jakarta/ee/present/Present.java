package jakarta.ee.present;

public enum Present {
    STICK,
    BOOK,
    PLAYSTATION,
    XBOX,
    CAR,
    BALL,
    SHOES,
    PC;

    public static Present of(String s) {
        for (Present present : Present.values()) {
            if (s.equalsIgnoreCase(present.name())) {
                return present;
            }
        }
        throw new IllegalArgumentException();
    }
}
