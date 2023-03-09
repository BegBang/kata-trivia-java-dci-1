package trivia;

public enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");


    Category(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
