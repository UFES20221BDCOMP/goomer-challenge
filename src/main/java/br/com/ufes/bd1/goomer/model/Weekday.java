package br.com.ufes.bd1.goomer.model;

public enum Weekday {
    MON("monday"),
    TUE("tuesday"),
    WED("wednesday"),
    THU("thursday"),
    FRI("friday"),
    SAT("saturday"),
    SUN("sunday");

    private final String name;

    Weekday(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Weekday fromName(String name) {
        for (Weekday weekday : Weekday.values()) {
            if (weekday.name.equals(name)) {
                return weekday;
            }
        }
        throw new IllegalArgumentException("Invalid weekday: " + name);
    }
}
