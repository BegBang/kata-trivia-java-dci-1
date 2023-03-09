package trivia;

public class Player {
    private final String name;
    private int coins;
    private int position;
    private boolean inJail;

    public Player(String name) {
        this.name = name;
        this.coins = 0;
        this.position = 0;
        this.inJail = false;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoin() {
        this.coins++;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
