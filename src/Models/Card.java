package Models;

public class Card {
    final static String RED = "\033[31m", RESET = "\u001B[0m";
    private String name;
    private String surname;
    private int position;
    private float ovr;
    private int atk;
    private int def;
    private String team;
    private String idCard;

    /**
     * Constructor de la carta
     * @param name, nombre del jugador
     * @param surname, apellido del jugador
     * @param position, posicion del jugador
     * @param atk, valor de ataque del jugador
     * @param def, valor de defensa del jugador
     * @param team, equipo del jugador
     * @param idCard, id de la Carta
     */
    public Card(String name, String surname, int position,int atk, int def, String team, String idCard) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.ovr = (atk+def)/2f;
        this.atk = atk;
        this.def = def;
        this.team = team;
        this.idCard = idCard;
    }

    /**
     * Modifica el metodo toString de la clase Player
     * @return, String que imprimir
     */
    @Override
    public String toString() {
        return  this.name + " " + this.surname + RED + " POS:" + RESET + this.position + RED + " OVR: " + RESET + this.ovr + RED + " ATK: " + RESET + this.atk + RED + " DEF: " +
                RESET + this.def + RED + " TEAM: " + RESET + this.team;
    }

    /**
     * Modifica el metodo equals de la clase Player
     * @return, booleano de la comparación
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Card cardToCompare = (Card) obj;

        return this.name.equalsIgnoreCase(cardToCompare.name);
    }

    /**
     * Retorna el nombre del jugador
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Reenombra el nombre del jugador
     * @param name, nombre del jugador
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la posición del jugador
     * @return, posición del jugador
     */
    public int getPosition() {
        return position;
    }

    /**
     * Devuelve la media del jugador
     * @return, media del jugador
     */
    public float getOvr() {
        return ovr;
    }

    /**
     * Devuelve el valor de ataque del jugador
     * @return, valor de ataque del jugador
     */
    public int getAtk() {
        return atk;
    }

    /**
     * Devuelve el valor de defensa del jugador
     * @return, valor de defensa del jugador
     */
    public int getDef() {
        return def;
    }
}
