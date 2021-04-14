package Models;

public class LopezMiquel_Card {
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
     * Constructor de la carta del maindeck
     * @param name Nombre de la carta
     * @param surname Apellido de la carta
     * @param position Posicion de la carta
     * @param atk Valor del ataque de la carta
     * @param def Valor de la defensa de la carta
     * @param team Nombre del equipo de la carta
     * @param idCard Valor de la id de la carta
     */
    public LopezMiquel_Card(String name, String surname, int position, int atk, int def, String team, String idCard) {
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
     *Constructor de la carta usada de forma auxiliar
     * @param name Nombre de la carta
     * @param surname Apellido de la carta
     * @param position Posicion de la carta
     * @param atk Valor del ataque de la carta
     * @param def Valor de la defensa de la carta
     * @param team Nombre del equipo de la carta
     */
    public LopezMiquel_Card(String name, String surname, int position, int atk, int def, String team) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.ovr = (atk+def)/2f;
        this.atk = atk;
        this.def = def;
        this.team = team;
    }

    /**
     * Override de la impresion de las cartas
     * @return Carta de la carta
     */
    @Override
    public String toString() {
        return  name + " " + surname +  RED + " POS: " + RESET + position +RED + " OVR: " + RESET + ovr + RED + " ATK: " + RESET + atk + RED + " DEF: " +
                RESET + def + RED + " TEAM: " + RESET + team;
    }

    /**
     * Equals para la carta de la carta
     * @param obj Cartas
     * @return Booleano
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        LopezMiquel_Card cardToCompare = (LopezMiquel_Card) obj;

        return this.idCard.equalsIgnoreCase(cardToCompare.idCard);
    }

    /**
     * Getter del nombre de la carta
     * @return nombre de la carta
     */
    public String getName() {
        return name;
    }

    /**
     * Getter del apellido de la carta
     * @return apellido de la carta
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter del nombre de la carta
     * @param name EL nuevo nombre de la carta
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter de la posicion de la carta
     * @return Posicion de la carta
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getter de la media de la carta
     * @return Media de la carta
     */
    public float getOvr() {
        return ovr;
    }

    /**
     * Getter del ataque de la carta
     * @return Ataque de la carta
     */
    public int getAtk() {
        return atk;
    }

    /**
     * Getter de la defensa de la carta
     * @return Defensa de la carta
     */
    public int getDef() {
        return def;
    }
}
