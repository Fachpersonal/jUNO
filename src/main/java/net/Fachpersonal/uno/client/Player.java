package net.Fachpersonal.uno.client;

public class Player {
    private int card_count;
    private String username;

    public Player(String username, int card_count) {
        this.username = username;
        this.card_count = card_count;
    }

    public int getCard_count() {
        return card_count;
    }

    public String getUsername() {
        return username;
    }
}
