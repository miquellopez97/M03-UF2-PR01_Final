package Models;

import java.util.ArrayList;

public class LopezMiquel_Player {
    String nickname;
    ArrayList<LopezMiquel_Card> stickerAlbum = new ArrayList<>();
    ArrayList<LopezMiquel_Card> deck = new ArrayList<>();

    /**
     * Anadir una carta al Array de cartas
     * @param x Carta a anadir
     */
    public void addCardToDeck( LopezMiquel_Card x){
        this.deck.add(x);
    }

    /**
     * Constructor del player, solo un elemento obligatorio
     * @param nickname Nombre del usuario
     */
    public LopezMiquel_Player(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter del nombre
     * @return Nombre
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter del nombre
     * @param nickname Nuevo nombre
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter del mazo de cartas
     * @return mazo de cartas
     */
    public ArrayList<LopezMiquel_Card> getDeck() {
        return deck;
    }

    /**
     * Getter del album de cromos del jugador
     * @return Album de cromos
     */
    public ArrayList<LopezMiquel_Card> getStickerAlbum() {
        return stickerAlbum;
    }

    /**
     * Override de como imprimir un player
     * @return Player
     */
    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", deck=" + deck +
                '}';
    }
}
