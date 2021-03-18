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

    @Override
    public String toString() {
        return  name + " " + surname + RED + " OVR: " + RESET + ovr + RED + " ATK: " + RESET + atk + RED + " DEF: " +
                RESET + def + RED + " TEAM: " + RESET + team;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Card cardToCompare = (Card) obj;

        return this.name.equalsIgnoreCase(cardToCompare.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getOvr() {
        return ovr;
    }

    public void setOvr(float ovr) {
        this.ovr = ovr;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
