package trivia;

import java.util.LinkedList;
import java.util.Queue;

public enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
