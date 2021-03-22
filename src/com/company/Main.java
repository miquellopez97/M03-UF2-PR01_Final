package com.company;

import Models.Card;
import Models.Player;

import java.util.ArrayList;
import java.util.Scanner;

/*
Una funcion para cuando solo sumas una carta
Ordenar deck del jugador por posicion
Dibujar marcador
Dibujar campo con jugadores
Escoger jugador mediante numero
Menu
    1. Cuenta
        1.1 Cambiar nombre
        1.2 Mostrar Album de cromos
    2. Abrir sobres
    3. Jugar
        3.1 Jugar solo un cuarto
        3.2 Jugar un partido
    4. Creditos
    5. Salir do{}while(opcion!=5)
*/

public class Main {
    final static String RED = "\033[31m", RESET = "\u001B[0m";
    static ArrayList<Player> playersList = new ArrayList<>();
    static ArrayList<Card> mainDeck = new ArrayList<>();
    static ArrayList<Card> botFloor = new ArrayList<>();
    static ArrayList<Card> playerFloor = new ArrayList<>();
    static int scoreboard [] = new int[2];

    public static void main(String[] args) {
	    addPlayer();
        addCardsToMainDeck();
        dealCards(0);
        dealCards(1);
        match();
    }

    /**
     * Hace la 4 veces la acción singleQuarter()
     */
    public static void match(){
        singleQuarter();
        singleQuarter();
        singleQuarter();
        singleQuarter();
    }

    /**
     * Hace la acción de un quarto y anuncia el ganador
     */
    public static void singleQuarter(){
        boolean victory = false;

        victory = quarter();

        if (victory){
            System.out.println("You win quarter");
        }else {
            System.out.println("You lose quarter");
        }
        System.out.print("This quarter end " + scoreboard[0] + " : " + scoreboard[1]);
    }

    /**
     * Se produce una ronda del juego (oneVsOneDEF(), oneVsOneATK(), twoVsTwo() y oneVsOneDEF())
     * @return int, 1 o 0 dependiendo si ha ganado
     */
    public static boolean quarter(){

        oneVsOneDEF();
        scoreboard();
        dealCards(0);
        dealCards(1);

        oneVsOneATK();
        scoreboard();
        dealCards(0);
        dealCards(1);

        twoVsTwo();
        scoreboard();
        dealCards(0);
        dealCards(1);

        oneVsOneDEF();
        dealCards(0);
        dealCards(1);

        return scoreboard[0]>scoreboard[1];
    }

    /**
     * Imprime el marcador actual
     */
    public static void scoreboard(){
        System.out.println("El marcador actual es " + scoreboard[0] + " : " + scoreboard[1]);
    }

    /**
     * Compara el atributo def de la carta del usuario vs el atributo atk del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void oneVsOneDEF(){
        System.out.println("This round you def");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getDef()>=botFloor.get(0).getAtk()){
            scoreboard[0]+=playerFloor.get(0).getDef();
            scoreboard[1]+=botFloor.get(0).getAtk();
            System.out.println("Has ganado esta ronda");
        }else {
            System.out.println("Has perdido esta ronda");
            scoreboard[0]+=playerFloor.get(0).getDef();
            scoreboard[1]+=botFloor.get(0).getAtk();
        }
        clearArray();
    }

    /**
     * Compara el atributo atk de la carta del usuario vs el atributo def del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void oneVsOneATK(){
        System.out.println("This round you attack");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getAtk()>=botFloor.get(0).getDef()){
            System.out.println("Has ganado esta ronda");
            scoreboard[0]+=playerFloor.get(0).getAtk();
            scoreboard[1]+=botFloor.get(0).getDef();
        }else {
            System.out.println("Has perdido esta ronda");
            scoreboard[0]+=playerFloor.get(0).getAtk();
            scoreboard[1]+=botFloor.get(0).getDef();
        }
        clearArray();
    }

    /**
     * Compara el atributo ovr de la carta del usuario vs el atributo ovr del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
     */
    public static void twoVsTwo(){
        System.out.println("This round you play 2 vs 2");
        round(0,true);
        round(1,true);
        System.out.println(botFloor);
        System.out.println(playerFloor);

        if ((playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr())>=(botFloor.get(0).getOvr()+botFloor.get(1).getOvr())){
            scoreboard[0]+=playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr();
            scoreboard[1]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            System.out.println("Has ganado esta ronda");
        }else {
            scoreboard[0]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            scoreboard[1]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            System.out.println("Has perdido esta ronda");
        }
        clearArray();
    }

    /**
     * Limpia la Array de cartas de cada jugador
     */
    public static void clearArray(){
        playerFloor.clear();
        botFloor.clear();
    }

    /**
     * Añade la carta elegida por el jugador y la añade a la array para ese turno
     * @param player Jugador que hace la acción
     * @param two Si es true es ronda es 2 vs 2 si no 1 vs 1
     */
    public static void round(int player, boolean two){
        if (player == 0){
            int number = (int)(Math.random()*playersList.get(player).getDeck().size());
            if (!two){
                botFloor.add(playersList.get(player).getDeck().get(number));
                playersList.get(player).getDeck().remove(number);
            }else{
                botFloor.add(playersList.get(player).getDeck().get(number));
                playersList.get(player).getDeck().remove(number);
                number = (int)(Math.random()*playersList.get(player).getDeck().size());
                botFloor.add(playersList.get(player).getDeck().get(number));
                playersList.get(player).getDeck().remove(number);
            }
        }else{
            if (!two){
                playerFloor.add(turn(player));
            }else{
                playerFloor.add(turn(player));
                playerFloor.add(turn(player));
            }
        }
    }

    /**
     * Elección de la carta y comprueba si la tienes en el deck
     * @param player Player que hace la acción
     * @return Retorna la carta si la tienes en el deck
     */
    public static Card turn(int player){
        Card election = new Card("default", "default", 0, 0, 0, "default", "default");
        String card;
        Scanner sc = new Scanner(System.in);
        boolean correctElection=false;

        System.out.println("*****************************************************************");
        for (Card c:playersList.get(player).getDeck()) {
            System.out.println(c);
        }
        System.out.println("*****************************************************************");

        do {
            System.out.print("Choose a card: ");
            card = sc.nextLine();
            election.setName(card);

            for (int cardOrder = 0; cardOrder < playersList.get(player).getDeck().size(); cardOrder++) {
                if (playersList.get(player).getDeck().get(cardOrder).getName().equalsIgnoreCase(card)){
                    election = playersList.get(player).getDeck().get(cardOrder);
                    playersList.get(player).getDeck().remove(cardOrder);
                    correctElection=true;
                    break;
                }
            }
            if (!correctElection){
                System.out.println(RED + "Card not found" + RESET);
            }
        }while (!correctElection);

        return election;
    }

    /**
     * Distribuye 5 cartas a cada jugador
     * @param x Jugador al que asignar las cartas
     */
    public static void dealCards( int x){
        boolean position = false;
        int number = (int)(Math.random()*mainDeck.size());

        playersList.get(x).addCardToDeck(mainDeck.get(number));
        mainDeck.remove(number);
        do {
            number = (int)(Math.random()*mainDeck.size());

            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==mainDeck.get(number).getPosition()){
                    playersList.get(x).getDeck().remove(i);
                }
            }
        }while (playersList.get(x).getDeck().size()<5);
    }

    /**
     * Control de errores, solo String
     * @param input, pregunta al usuario
     * @return String, con control de errores
     */
    public static String onlyString(String input){
        Scanner sc = new Scanner(System.in);
        String x;
        boolean valorCorrecte=false;

        do {
            System.out.print(input);
            x=sc.nextLine();

            try {
                Integer.parseInt(x);
                System.out.println(RED + "Nomes pots escriure lletres." + RESET);
            } catch (NumberFormatException excepcion) {
                valorCorrecte=true;
            }
        }while(!valorCorrecte);

        return  x;
    }

    /**
     * Control de errores, leer un int con un rango
     * @param input Prgeunta al usuario
     * @param valorMin Valor mínimo del rango
     * @param valorMax Valor máximo del rango
     * @return Valor de la pregunta al usuario
     */
    public static int numbersWithRange(String input, int valorMin, int valorMax){
        Scanner sc = new Scanner(System.in);
        int x = 0;
        boolean valorCorrecte = false;

        do{
            System.out.print(input);
            valorCorrecte = sc.hasNextInt();

            if(!valorCorrecte){
                System.out.println(RED+"ERROR: No has escrito un entero"+RESET);
                sc.nextLine();
            }else{
                x = sc.nextInt();
                sc.nextLine();

                if (x < valorMin || x > valorMax){
                    System.out.println(RED+"ERROR: Valor fuera del rango"+RESET);
                    valorCorrecte = false;
                }
            }
        }while(!valorCorrecte);
        return x;
    }

    /**
     * Añadir jugadores
     */
    public static void addPlayer(){
        Player pc = new Player("Bot");
        playersList.add(pc);
        Player miquel = new Player("miqueel97");
        playersList.add(miquel);
    }

    /**
     * Añadir cartas al deck
     */
    public static void addCardsToMainDeck(){
        //**************************************************************************************************************************************
        //Prime Legends
        //**************************************************************************************************************************************
        Card plMichael = new Card("Michael", "Jordan", 2,99, 99, "Chicago Bulls (1992)", "PLMJ");
        mainDeck.add(plMichael);
        Card plKobe = new Card("Kobe", "Bryant", 3,99, 99, "Los Angeles Lakers (2002)", "PLKB");
        mainDeck.add(plKobe);
        Card plLarry = new Card("Larry", "Bird", 3,99, 99, "Boston Celtics (1984)", "PLLB");
        mainDeck.add(plLarry);
        Card plAllen = new Card("Allen", "Iverson", 1,99, 99, "Philadelphia 76ers (2001)", "PLAI");
        mainDeck.add(plAllen);
        Card plShaquille = new Card("Shaquille", "O'Neal", 5,99, 99, "Los Angeles Lakers (2002)", "PLSO");
        mainDeck.add(plShaquille);
        Card plKarl = new Card("Karl", "Malone", 5,99, 99, "Utah Jazz (1989)", "PLKM");
        mainDeck.add(plKarl);
        Card plKareem = new Card("Kareem", "Abdul-Jabbar", 5,99, 99, "Los Angeles Lakers (1980)", "PLKA");
        mainDeck.add(plKareem);
        Card plMagic = new Card("Magic", "Johnson", 1,99, 99, "Los Angeles Lakers (1987)", "PLEJ");
        mainDeck.add(plMagic);
        Card plWilt = new Card("Wilt", "Chamberlain", 5,99, 99, "Philadelphia 76ers (1961)", "PLWC");
        mainDeck.add(plWilt);
        Card plBill = new Card("Bill", "Russell", 5,99, 99, "Boston Celtics (1962)", "PLBR");
        mainDeck.add(plBill);
        //**************************************************************************************************************************************
        //Brooklyn Nets
        //**************************************************************************************************************************************
        Card bnKyrie = new Card("Kyrie", "Irving", 1,92, 90, "Brooklyn Nets", "BNKI");
        mainDeck.add(bnKyrie);
        Card bnTyler = new Card("Tyler", "Jhnson", 1,75, 70, "Brooklyn Nets", "BNTJ");
        mainDeck.add(bnTyler);
        Card bnChris = new Card("Chris", "Chiozza", 1,55, 50, "Brooklyn Nets", "BNCC");
        mainDeck.add(bnChris);
        Card bnSpencer = new Card("Spencer", "Dinwiddie", 1,85, 80, "Brooklyn Nets", "BNSD");
        mainDeck.add(bnSpencer);
        Card bnJames = new Card("James", "Harden", 2,99, 85, "Brooklyn Nets", "BNJH");
        mainDeck.add(bnJames);
        Card bnLandry = new Card("Landry", "Shamet", 2,72, 71, "Brooklyn Nets", "BNLS");
        mainDeck.add(bnLandry);
        Card bnBruce = new Card("Bruce", "Brown", 2,63, 58, "Brooklyn Nets", "BNBB");
        mainDeck.add(bnBruce);
        Card bnJoe = new Card("Joe", "Harrys", 3,81, 75, "Brooklyn Nets", "BNJA");
        mainDeck.add(bnJoe);
        Card bnTimothe = new Card("Timothe", "Luwawu", 3,61, 55, "Brooklyn Nets", "BNTL");
        mainDeck.add(bnTimothe);
        Card bnKevin = new Card("Kevin", "Durant", 4,99, 95, "Brooklyn Nets", "BNKD");
        mainDeck.add(bnKevin);
        Card bnBlake = new Card("Blake", "Griffin", 4,87, 86, "Brooklyn Nets", "BNKG");
        mainDeck.add(bnBlake);
        Card bnJeff = new Card("Jeff", "Green", 4,72, 73, "Brooklyn Nets", "BNJG");
        mainDeck.add(bnJeff);
        Card bnReggie = new Card("Reggie", "Perry", 4,59, 54, "Brooklyn Nets", "BNRP");
        mainDeck.add(bnReggie);
        Card bnDeAndre = new Card("DeAndre", "Jordan", 5,85, 85, "Brooklyn Nets", "BNDJ");
        mainDeck.add(bnDeAndre);
        Card bnNicolas = new Card("Nicolas", "Claxton", 5,54, 49, "Brooklyn Nets", "BNNC");
        mainDeck.add(bnNicolas);
        //**************************************************************************************************************************************
        //Miami Heat
        //**************************************************************************************************************************************
        Card mhGoran = new Card("Goran", "Dragic", 1,75, 80, "Miami Heat", "MHGD");
        mainDeck.add(mhGoran);
        Card mhGabe = new Card("Gabe", "Vincent", 1,60, 59, "Miami Heat", "MHGV");
        mainDeck.add(mhGabe);
        Card mhKendrick = new Card("Kendrick", "Nunn", 1,70, 70, "Miami Heat", "MHKN");
        mainDeck.add(mhKendrick);
        Card mhTyler = new Card("Tyler", "Herro", 2,80, 75, "Miami Heat", "MHTH");
        mainDeck.add(mhTyler);
        Card mhAvery = new Card("Avery", "Bradley", 2,70, 70, "Miami Heat", "MHAB");
        mainDeck.add(mhAvery);
        Card mhMax = new Card("Max", "Strus", 2,55, 55, "Miami Heat", "MHMS");
        mainDeck.add(mhMax);
        Card mhJimmy = new Card("Jimmy", "Butler", 3,90, 90, "Miami Heat", "MHJB");
        mainDeck.add(mhJimmy);
        Card mhDuncan = new Card("Duncan", "Robinson", 3,75, 70, "Miami Heat", "MHDR");
        mainDeck.add(mhDuncan);
        Card mhAndre = new Card("Andre", "Iguodala", 3,70, 75, "Miami Heat", "MHAI");
        mainDeck.add(mhAndre);
        Card mhMoe = new Card("Moe", "Harkless", 3,60, 65, "Miami Heat", "MHMH");
        mainDeck.add(mhMoe);
        Card mhBam = new Card("Bam", "Adebayo", 4,90, 94, "Miami Heat", "MHBA");
        mainDeck.add(mhBam);
        Card mhPrecious = new Card("Precious", "Achiuwa", 4,70, 72, "Miami Heat", "MHPA");
        mainDeck.add(mhPrecious);
        Card mhChris = new Card("Chris", "Silva", 4,65, 62, "Miami Heat", "MHCS");
        mainDeck.add(mhPrecious);
        Card mhKZ = new Card("KZ", "Okpala", 4,62, 59, "Miami Heat", "MHKO");
        mainDeck.add(mhKZ);
        Card mhKelly = new Card("Kelly", "Olynyk", 5,80, 85, "Miami Heat", "MHKY");
        mainDeck.add(mhKelly);
        Card mhUdonis = new Card("Udonis", "Haslem", 5,72, 73, "Miami Heat", "MHUH");
        mainDeck.add(mhUdonis);
        Card mhMeyers = new Card("Meyers", "Leonard", 5,74, 76, "Miami Heat", "MHML");
        mainDeck.add(mhMeyers);
        //**************************************************************************************************************************************
        //Los Angeles Lakers
        //**************************************************************************************************************************************
        Card lalLebron = new Card("LeBron", "James", 1,99, 95, "Los Angeles Lakers", "LALLJ");
        mainDeck.add(lalLebron);
        Card lalCaruso = new Card("Alex", "Caruso", 1, 70, 75, "Los angeles Lakers", "LALAC");
        mainDeck.add(lalCaruso);
        Card lalDennis = new Card("Dennis", "Schroder", 2,80, 75, "Los Angeles Lakers", "LALDS");
        mainDeck.add(lalDennis);
        Card lalWesley = new Card("Wesley", "Matthews", 2,70, 70, "Los Angeles Lakers", "LALWM");
        mainDeck.add(lalWesley);
        Card lalCaldwell = new Card("Kentavious", "Caldwell-Pope", 3,85, 70, "Los Angeles Lakers", "LALKD");
        mainDeck.add(lalCaldwell);
        Card lalKyle = new Card("Kyle", "Kuzma", 3,85, 80, "Los Angeles Lakers", "LALKK");
        mainDeck.add(lalKyle);
        Card lalHorton = new Card("Talen", "Horton", 3,70, 75, "Los Angeles Lakers", "LALTH");
        mainDeck.add(lalHorton);
        Card lalMcKinnie = new Card("Alfonzo", "McKinnie", 3,60, 65, "Los Angeles Lakers", "LALAM");
        mainDeck.add(lalMcKinnie);
        Card lalDavis = new Card("Anthony", "Davis", 4,93, 90, "Los Angeles Lakers", "LALAD");
        mainDeck.add(lalDavis);
        Card lalMorris = new Card("Markieff", "Morris", 4,74, 75, "Los Angeles Lakers", "LALMM");
        mainDeck.add(lalMorris);
        Card lalDudley = new Card("Jared", "Dudley", 4,60, 33, "Los Angeles Lakers", "LALJD");
        mainDeck.add(lalDudley);
        Card lalCacok = new Card("Devontae", "Cacok", 4,60, 60, "Los Angeles Lakers", "LALDC");
        mainDeck.add(lalCacok);
        Card lalGasol = new Card("Marc", "Gasol", 5,70, 85, "Los Angeles Lakers", "LALMG");
        mainDeck.add(lalGasol);
        Card lalHarrell = new Card("Montrezl", "Harrell", 5,85, 85, "Los Angeles Lakers", "LALMH");
        mainDeck.add(lalHarrell);
        Card lalAnteto = new Card("Kostas", "Antetokoumpo", 5,61, 65, "Los Angeles Lakers", "LALKA");
        mainDeck.add(lalAnteto);
        Card lalDamian = new Card("Damian", "Jones", 5,60, 65, "Los Angeles Lakers", "LALDS");
        mainDeck.add(lalDamian);
    }
}