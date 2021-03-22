package Models;

import java.util.ArrayList;

public class Player {
    String nickname;
    String password;
    ArrayList<Card> deck = new ArrayList<>();

    /**
     * Añadir una carta al deck
     * @param x Carta que añadir
     */
    public void addCardToDeck( Card x){
        this.deck.add(x);
    }

    /**
     * Constructor del player
     * @param nickname, nombre del player
     */
    public Player(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retorna el nickname del player
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Modifica el nickname del player
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retorna la deck del player
     * @return
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Modifica el metodo toString de la clase Player
     * @return, String que imprimir
     */
    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", deck=" + deck +
                '}';
    }
}
