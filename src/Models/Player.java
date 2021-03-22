package Models;

import java.util.ArrayList;

public class Player {
    String nickname;
    String password;
    ArrayList<Card> deck = new ArrayList<>();

    public void addCardToDeck( Card x){
        this.deck.add(x);
    }

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", deck=" + deck +
                '}';
    }
}
