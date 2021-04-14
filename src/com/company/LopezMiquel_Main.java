package com.company;

import Models.LopezMiquel_Card;
import Models.LopezMiquel_Player;

import java.util.ArrayList;
import java.util.Scanner;

public class LopezMiquel_Main {
    final static String RED = "\033[31m", RESET = "\u001B[0m";
    static ArrayList<LopezMiquel_Player> playersList = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> mainDeck = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> botFloor = new ArrayList<>();
    static ArrayList<LopezMiquel_Card> playerFloor = new ArrayList<>();
    static int scoreboard [] = new int[2];

    /**
     * Funcion que hace el programa ejecutable
     * @param args x
     */
    public static void main(String[] args) {
        int option;

        do {
            option=menu();

            switch (option) {
                case 1:
                    userOptions();
                    break;
                case 2:
                    openPacket();
                    break;
                case 3:
                    dealCards(0);
                    dealCards(1);
                    match();
                    break;
                case 4:
                    addPlayerManual();
                    break;
                case 5:
                    howToPlay();
                    break;
                case 6:
                    disponibleCards();
                    break;
                case 7:
                    System.out.println("Has escogido salir");
                    break;
            }
        }while (option!=7);

    }

    /**
     * Cartas disponibles en el juego
     */
    public static void disponibleCards(){
        System.out.println( "******************************* CARTAS DISPONIBLES ************************************\n"+
                            RED + "Prime Legends:\n" + RESET + "     Jugadores legendarios en su mejor estado de forma.\n"+
                            RED + "Future Stars:\n" + RESET + "     Jugadores que actualmente no estan en la NBA pero nadie duda que estaran.\n"+
                            RED + "Celebrity:\n" + RESET + "     Jugadores que han participado en el All-Star game como celebridades.\n"+
                            RED + "Rosters NBA (2020-2021):\n" + RESET +
                            "     -Miami Heat\n" + "     -Los Angeles Lakers\n" + "     -Dallas Mavericks\n"+
                            "     -Brooklyn Nets\n"+ "     -Milwaukee Bucks\n"+ "     -Denver Nuggets\n"+
                            "     -Philadelphia 76ers\n"+
                            RED + "Rosters Euroliga (2020-2021):\n" + RESET +
                            "     -Real Madrid\n" + "     -FC Barcelona\n" +
                            RED + "Recuento de cartas:\n" + RESET +
                            RED + "Bases: "+ RESET + countCards(1) + RED + " Escoltas: "+ RESET + countCards(2) +
                            RED + " Aleros: "+ RESET + countCards(3) + RED + " Ala pivots: "+ RESET + countCards(4) +
                            RED + " Pivots: "+ RESET + countCards(5) + RED + " Total: "+ RESET + mainDeck.size()+ "\n"+
                            "******************************* CARTAS DISPONIBLES ************************************\n");
    }

    /**
     * Cuenta el numero de cartas dependiendo de su posicion
     * @param postion Posicion del jugador
     * @return Numero de cartas de la misma posicion
     */
    public static int countCards(int postion){
        int x = 0;
        for (int i = 0; i < mainDeck.size(); i++) {
            if (mainDeck.get(i).getPosition()==postion){
                x++;
            }
        }
        return x;
    }

    /**
     * Explicación de como funciona el juego
     */
    public static void howToPlay(){
        System.out.println(
                        "*********************************** HOW TO PLAY ***********************************************\n"+
                        "Este juego trata sobre una simulación de un partido de baloncesto, es un juego de cartas donde\n" +
                        "cada carta es un jugador de la NBA. Tendras a tu disposición 5 cartas las cuales son\n" +
                        "base(1), un escolta(2), un alero(3), un ala-pívot(4) y un pívot(5).\n" +
                        "Cada carta contiene 3 valores ATK, DEF y OVR. Estos valores son asignados por el creador\n" +
                        "del juego, el OVR es la media entre el ataque y la defensa.\n"+
                        "El modo de juego se basa en la siguiente forma, un partido de baloncesto consiste\n"+
                        "en 4 parciales, en este juego cada parcial esta definido por 4 acciones.\n"+
                        "Hay tres tipos de acciones:\n" +
                        "   Acción defensiva:\n" +
                        "   Se compara la puntuación defensiva de la carta escogida versus el ataque de la carta rival\n" +
                        "   Acción ofensiva:\n" +
                        "   Se compara la puntuación ofensiva de la carta escogida versus la defensa de la carta rival\n" +
                        "   2 contra 2:\n" +
                        "   Se compara la puntuación OVR de la carta escogida versus el OVR de la carta rival\n" +
                        "El orden de las acciones de los periodos sera:\n" +
                        "   Primera acción defensiva\n"+
                        "   Segunda acción ofensiva\n"+
                        "   Tercera acción 2 vs 2\n"+
                        "   Cuarta acción defensiva\n"+
                        "El jugador que sume el mayor numero de puntos durante los cuatro periodos ganara la partida.\n"+
                        "*********************************** HOW TO PLAY ***********************************************\n"
        );
    }

    /**
     * Ordena la Arraylist del jugador o posición
     */
    public static void orderPosition(){
        LopezMiquel_Card aux;

        for (int i = 0; i < playersList.get(1).getDeck().size(); i++) {
            for (int j = 0; j < playersList.get(1).getDeck().size(); j++) {
                if (playersList.get(1).getDeck().get(i).getPosition()<=playersList.get(1).getDeck().get(j).getPosition()){
                    aux = playersList.get(1).getDeck().get(i);
                    playersList.get(1).getDeck().set(i, playersList.get(1).getDeck().get(j));
                    playersList.get(1).getDeck().set(j,aux);
                }
            }
        }
    }

    /**
     * Imprime el menu
     * @return Retorna la opción del menu
     */
    public static int menu(){
        int option;
        String[] options = new String[]{"Bienvenido a LaSalleNBA", "1.  Opciones del usuario",
                "2.  Abrir paquete", "3.  Jugar", "4.  Agregar un fantasy player manualmente",
                "5.  Como jugar", "6.  Cartas disponibles", "7.  Salir"};

        for (String x:options) {
            System.out.println(x);
        }
        option=numbersWithRange("Escoge una opción: ", 1, 7);

        mainDeck.clear();
        clearArray();
        addPlayer();
        addCardsToMainDeck();

        return option;
    }

    /**
     * Anade una carta de forma manual
     */
    public static void addPlayerManual(){
        Scanner sc = new Scanner(System.in);
        String name, surname, team;
        int position, atk, def;

        name = onlyString("Nombre: ");
        surname = onlyString("Apellido: ");
        position = numbersWithRange("Posición: ", 1, 5);
        atk = numbersWithRange("Ataque: ", 50, 99);
        def = numbersWithRange("Defensa: ", 50, 99);
        System.out.print("Equipo: ");
        team= sc.nextLine();

        LopezMiquel_Card newPlayer = new LopezMiquel_Card(name, surname, position,atk, def, team);
        mainDeck.add(newPlayer);
    }

    /**
     * Abrir un sobre
     */
    public static void openPacket(){
        int number;
        for (int i = 0; i < 5; i++) {
            number = (int)(Math.random()*mainDeck.size());
            playersList.get(1).getStickerAlbum().add(mainDeck.get(number));
            System.out.println(mainDeck.get(number));
        }
    }

    /**
     * Las opciones que tiene el usuario
     */
    public static void userOptions(){
        int option;
        Scanner sc = new Scanner(System.in);

        System.out.println( "1.  Cambiar el nombre\n"+
                "2.  Mostrar el album de cromos");
        option=numbersWithRange("Escoge una opción: ", 1, 2);

        switch (option){
            case 1:
                System.out.print("Escoge un nuevo nombre: ");
                playersList.get(1).setNickname(sc.nextLine());
                break;
            case 2:
                for (LopezMiquel_Card x : playersList.get(1).getStickerAlbum()){
                    System.out.println(x);
                }
                break;
        }
    }

    /**
     * Hace la 4 veces la acción singleQuarter()
     */
    public static void match(){
        quarter(1);
        quarter(2);
        quarter(3);
        quarter(4);
    }

    /**
     * Se produce una ronda del juego (oneVsOneDEF(), oneVsOneATK(), twoVsTwo() y oneVsOneDEF())
     */
    public static void quarter(int x){

        oneVsOneDEF();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        oneVsOneATK();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        twoVsTwo();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

        oneVsOneDEF();
        scoreboard();
        if (x!=4){
            dealOneCard(0);
            dealOneCard(1);
        }

    }

    /**
     * Imprime el marcador actual
     */
    public static void scoreboard(){
        System.out.println("*********************************\n" +
                           "                                 \n"+
                           "      " + playersList.get(1).getNickname() + "         " + playersList.get(0).getNickname() + "      \n"+
                           "        " + scoreboard[1] + "             " + scoreboard[0] + "       \n"+
                           "                                 \n"+
                           "*********************************\n");
    }

    /**
     * Compara el atributo def de la carta del usuario vs el atributo atk del bot
     * Asigna valor al marcador
     */
    public static void oneVsOneDEF(){
        System.out.println("Esta ronda defiendes");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getDef()>=botFloor.get(0).getAtk()){
            scoreboard[1]+=playerFloor.get(0).getDef();
            scoreboard[0]+=botFloor.get(0).getAtk();
            System.out.println("Has ganado esta ronda");
        }else {
            System.out.println("Has perdido esta ronda");
            scoreboard[1]+=playerFloor.get(0).getDef();
            scoreboard[0]+=botFloor.get(0).getAtk();
        }
        clearArray();
    }

    /**
     * Compara el atributo atk de la carta del usuario vs el atributo def del bot
     * Asigna valor al marcador
     */
    public static void oneVsOneATK(){
        System.out.println("Esta ronda atacas");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getAtk()>=botFloor.get(0).getDef()){
            System.out.println("Has ganado esta ronda");
            scoreboard[1]+=playerFloor.get(0).getAtk();
            scoreboard[0]+=botFloor.get(0).getDef();
        }else {
            System.out.println("Has perdido esta ronda");
            scoreboard[1]+=playerFloor.get(0).getAtk();
            scoreboard[0]+=botFloor.get(0).getDef();
        }
        clearArray();
    }

    /**
     * Compara el atributo ovr de la carta del usuario vs el atributo ovr del bot
     * Asigna valor al marcador
     */
    public static void twoVsTwo(){
        System.out.println("Ronda especial 2 vs 2");
        round(0,true);
        round(1,true);
        System.out.println(botFloor);
        System.out.println(playerFloor);

        if ((playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr())>=(botFloor.get(0).getOvr()+botFloor.get(1).getOvr())){
            scoreboard[1]+=playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr();
            scoreboard[0]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            System.out.println("Has ganado esta ronda");
        }else {
            scoreboard[1]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
            scoreboard[0]+=botFloor.get(0).getOvr()+botFloor.get(1).getOvr();
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
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
            }else{
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
                number = (int)(Math.random()*playersList.get(player).getDeck().size());
                botFloor.add(playersList.get(0).getDeck().get(number));
                playersList.get(0).getDeck().remove(number);
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
    public static LopezMiquel_Card turn(int player){
        LopezMiquel_Card election = new LopezMiquel_Card("default", "default", 0, 0, 0, "default", "default");
        String card;
        Scanner sc = new Scanner(System.in);
        boolean correctElection=false;

        System.out.println("*****************************************************************");
        for (LopezMiquel_Card c:playersList.get(player).getDeck()) {
            System.out.println(c);
        }
        System.out.println("*****************************************************************");

        do {
            System.out.print("Nombre del jugador: ");
            card = sc.nextLine();
            election.setName(card);
            int same=0, count=0;
            String subCard="";


            for (int cardOrder = 0; cardOrder < playersList.get(player).getDeck().size(); cardOrder++) {
                if (playersList.get(player).getDeck().get(cardOrder).getName().equalsIgnoreCase(card)){
                    same=sameName();
                    if (same==1){
                        if(count==0){
                            System.out.print("Apellido del jugador: ");
                            subCard=sc.nextLine();
                            count++;
                        }
                        if (playersList.get(player).getDeck().get(cardOrder).getName().equalsIgnoreCase(card) && playersList.get(player).getDeck().get(cardOrder).getSurname().equalsIgnoreCase(subCard)){
                            election = playersList.get(player).getDeck().get(cardOrder);
                            playersList.get(player).getDeck().remove(cardOrder);
                            correctElection=true;
                            break;
                        }
                    }else{
                        election = playersList.get(player).getDeck().get(cardOrder);
                        playersList.get(player).getDeck().remove(cardOrder);
                        correctElection=true;
                        break;
                    }
                }
            }
            if (!correctElection){
                System.out.println(RED + "Carta no encontrada" + RESET);
            }
        }while (!correctElection);

        return election;
    }

    /**
     * Comprueba si en las 5 cartas que tienes en la mano hay dos con el mismo nombre
     * @return 1 si hay 2 o mas con el mismo nombre
     */
    public static int sameName(){
        int same=0;

        for (int i = 0; i < playersList.get(1).getDeck().size(); i++) {
            for (int j = 0; j < playersList.get(1).getDeck().size(); j++) {
                if(playersList.get(1).getDeck().get(i).getName().equalsIgnoreCase(playersList.get(1).getDeck().get(j).getName())){
                    same++;
                }
            }
        }
        if (same>5){
            return 1;
        }else{
            return 2;
        }
    }

    /**
     * Distribuye 5 cartas a cada jugador
     * @param x Jugador al que asignar las cartas
     */
    public static void dealCards(int x){
        int number = (int)(Math.random()*mainDeck.size());
        boolean flag=false;
        clearArray();

        playersList.get(x).addCardToDeck(mainDeck.get(number));
        mainDeck.remove(number);
        do {
            number = (int)(Math.random()*mainDeck.size());
            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==playersList.get(x).getDeck().get(playersList.get(x).getDeck().size()-1).getPosition()){
                    playersList.get(x).getDeck().remove(playersList.get(x).getDeck().size()-1);
                    flag=true;
                    break;
                }
            }
            if (!flag){
                mainDeck.remove(number);
            }
            flag=false;
        }while (playersList.get(x).getDeck().size()<5);
        orderPosition();
    }

    /**
     * Reparte una casta y comprueba que no haya de esa posicion
     * @param x jugador al que repartir
     */
    public static void dealOneCard(int x){
        boolean flag=false;
        do {
            int number = (int)(Math.random()*mainDeck.size());
            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==playersList.get(x).getDeck().get(playersList.get(x).getDeck().size()-1).getPosition()){
                    playersList.get(x).getDeck().remove(playersList.get(x).getDeck().size()-1);
                    flag=true;
                    break;
                }
            }
            if (!flag){
                mainDeck.remove(number);
            }
            flag=false;
        }while (playersList.get(x).getDeck().size()<5);
        orderPosition();
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
        boolean correctValue;

        do{
            System.out.print(input);
            correctValue = sc.hasNextInt();

            if(!correctValue){
                System.out.println(RED+"ERROR: No has escrito un numero"+RESET);
                sc.nextLine();
            }else{
                x = sc.nextInt();
                sc.nextLine();

                if (x < valorMin || x > valorMax){
                    System.out.println(RED+"ERROR: El numero esta fuera del rango"+RESET);
                    correctValue = false;
                }
            }
        }while(!correctValue);
        return x;
    }

    /**
     * Añadir jugadores
     */
    public static void addPlayer(){
        LopezMiquel_Player pc = new LopezMiquel_Player("Bot");
        playersList.add(pc);
        LopezMiquel_Player miquel = new LopezMiquel_Player("miqueel97");
        playersList.add(miquel);
    }

    /**
     * Añadir cartas al deck
     */
    public static void addCardsToMainDeck(){
        //**************************************************************************************************************************************
        //Prime Legends
        //**************************************************************************************************************************************
        LopezMiquel_Card plMichael = new LopezMiquel_Card("Michael", "Jordan", 2,99, 99, "Chicago Bulls (1992)", "PLMJ");
        mainDeck.add(plMichael);
        LopezMiquel_Card plKobe = new LopezMiquel_Card("Kobe", "Bryant", 3,99, 99, "Los Angeles Lakers (2002)", "PLKB");
        mainDeck.add(plKobe);
        LopezMiquel_Card plLarry = new LopezMiquel_Card("Larry", "Bird", 3,99, 99, "Boston Celtics (1984)", "PLLB");
        mainDeck.add(plLarry);
        LopezMiquel_Card plAllen = new LopezMiquel_Card("Allen", "Iverson", 1,99, 85, "Philadelphia 76ers (2001)", "PLAI");
        mainDeck.add(plAllen);
        LopezMiquel_Card plShaquille = new LopezMiquel_Card("Shaquille", "O'Neal", 5,99, 99, "Los Angeles Lakers (2002)", "PLSO");
        mainDeck.add(plShaquille);
        LopezMiquel_Card plKarl = new LopezMiquel_Card("Karl", "Malone", 5,90, 90, "Utah Jazz (1989)", "PLKM");
        mainDeck.add(plKarl);
        LopezMiquel_Card plKareem = new LopezMiquel_Card("Kareem", "Abdul-Jabbar", 5,99, 95, "Los Angeles Lakers (1980)", "PLKA");
        mainDeck.add(plKareem);
        LopezMiquel_Card plMagic = new LopezMiquel_Card("Magic", "Johnson", 1,99, 95, "Los Angeles Lakers (1987)", "PLEJ");
        mainDeck.add(plMagic);
        LopezMiquel_Card plWilt = new LopezMiquel_Card("Wilt", "Chamberlain", 5,99, 99, "Philadelphia 76ers (1961)", "PLWC");
        mainDeck.add(plWilt);
        LopezMiquel_Card plBill = new LopezMiquel_Card("Bill", "Russell", 5,99, 99, "Boston Celtics (1962)", "PLBR");
        mainDeck.add(plBill);
        LopezMiquel_Card plJason = new LopezMiquel_Card("Jason", "Kidd", 1,95, 85, "Dallas Mavericks (2011)", "PLJK");
        mainDeck.add(plJason);
        LopezMiquel_Card plOscar = new LopezMiquel_Card("Oscar", "Robertson", 1,95, 90, "Boston Celtics (1962)", "PLOR");
        mainDeck.add(plOscar);
        LopezMiquel_Card plJohn = new LopezMiquel_Card("John", "Stockton", 1,90, 80, "Utah Jazz (1989)", "PLJS");
        mainDeck.add(plJohn);
        LopezMiquel_Card plSteve = new LopezMiquel_Card("Steve", "Nash", 1,95, 85, "Phoenix Suns (2004)", "PLSN");
        mainDeck.add(plSteve);
        LopezMiquel_Card plBob = new LopezMiquel_Card("Bob", "Cousy", 1,90, 80, "Boston Celtics (1960)", "PLBC");
        mainDeck.add(plBob);
        LopezMiquel_Card plNate = new LopezMiquel_Card("Nate", "Robinson", 1,80, 80, "New York Knicks (2008)", "PLNR");
        mainDeck.add(plNate);
        LopezMiquel_Card plDerrick = new LopezMiquel_Card("Derrick", "Rose", 1,99, 99, "Chicago Buells (2010)", "PLDR");
        mainDeck.add(plDerrick);
        LopezMiquel_Card plRussell = new LopezMiquel_Card("Russell", "Westbrook", 1,99, 99, "Oklahoma City Thunder (2016)", "PLRW");
        mainDeck.add(plRussell);
        LopezMiquel_Card plPau = new LopezMiquel_Card("Pau", "Gasol", 5,99, 95, "Los Angeles Lakers (2010)", "PLPG");
        mainDeck.add(plPau);
        LopezMiquel_Card plKevin = new LopezMiquel_Card("Kevin", "Garnet", 5,99, 95, "Boston Celtics (2008)", "PLKG");
        mainDeck.add(plKevin);
        LopezMiquel_Card plRondo = new LopezMiquel_Card("Rajon", "Rondo", 1,85, 95, "Boston Celtics (2008)", "PLRR");
        mainDeck.add(plKevin);
        LopezMiquel_Card plPierce = new LopezMiquel_Card("Paul", "Pierce", 3,97, 90, "Boston Celtics (2008)", "PLPP");
        mainDeck.add(plPierce);
        LopezMiquel_Card plRay = new LopezMiquel_Card("Ray", "Allen", 2,95, 90, "Boston Celtics (2008)", "PLRA");
        mainDeck.add(plRay);
        LopezMiquel_Card plStephen = new LopezMiquel_Card("Stephen", "Curry", 1,99, 90, "Golden State Warriors (2019)", "PLSC");
        mainDeck.add(plStephen);
        LopezMiquel_Card plKlay = new LopezMiquel_Card("Klay", "Thompson", 2,95, 95, "Golden State Warriors (2019)", "PLKT");
        mainDeck.add(plKlay);
        LopezMiquel_Card plKevinD = new LopezMiquel_Card("Kevin", "Durant", 3,99, 95, "Golden State Warriors (2019)", "PLKD");
        mainDeck.add(plKevinD);
        LopezMiquel_Card plDraymond = new LopezMiquel_Card("Draymond", "Green", 5,80, 95, "Golden State Warriors (2019)", "PLDG");
        mainDeck.add(plDraymond);
        LopezMiquel_Card plCarmelo = new LopezMiquel_Card("Carmelo", "Anthony", 4,90, 90, "New York Knicks (2012)", "PLCA");
        mainDeck.add(plCarmelo);
        LopezMiquel_Card plLebronM = new LopezMiquel_Card("Lebron", "James", 3,99, 99, "Miami Heat (2013)", "PLLJM");
        mainDeck.add(plLebronM);
        LopezMiquel_Card plLebronC = new LopezMiquel_Card("Lebron", "James", 3,99, 95, "Cleveland Cavaliers (2009)", "PLLJC");
        mainDeck.add(plLebronC);
        LopezMiquel_Card plDwyane = new LopezMiquel_Card("Dwyane", "Wade", 2,95, 95, "Cleveland Cavaliers (2009)", "PLDD");
        mainDeck.add(plDwyane);
        //**************************************************************************************************************************************
        //Future Stars
        //**************************************************************************************************************************************
        LopezMiquel_Card fsZaire = new LopezMiquel_Card("Zaire", "Wade", 1,85, 85, "Brewster Bobcats (2021)", "FSZW");
        mainDeck.add(fsZaire);
        LopezMiquel_Card fsBronny = new LopezMiquel_Card("Bronny", "James", 1,85, 85, "Syerra Canyon School (2021)", "FSBJ");
        mainDeck.add(fsBronny);
        LopezMiquel_Card fsJuan = new LopezMiquel_Card("Juan", "Nunez", 1,85, 85, "Real Madrid (2021)", "FSJN");
        mainDeck.add(fsJuan);
        LopezMiquel_Card fsCade = new LopezMiquel_Card("Cade", "Cunningham", 1,85, 85, "Oklahoma State Cowboys men's basketball (2021)", "FSCC");
        mainDeck.add(fsCade);
        LopezMiquel_Card fsJalen = new LopezMiquel_Card("Jalen", "Green", 1,85, 85, "NBA G League Ignite (2021)", "FSJG");
        mainDeck.add(fsJalen);
        LopezMiquel_Card fsTristan = new LopezMiquel_Card("Tristan", "Vukcevic", 3,85, 85, "Real Madrid (2021)", "FSTV");
        mainDeck.add(fsTristan);
        LopezMiquel_Card fsUsman = new LopezMiquel_Card("Usman", "Garuba", 4,85, 85, "Real Madrid (2021)", "FSUG");
        mainDeck.add(fsUsman);
        LopezMiquel_Card fsLeandro = new LopezMiquel_Card("Leandro", "Bolmaro", 2,85, 85, "FC Barcelona (2021)", "FSLB");
        mainDeck.add(fsUsman);
        //**************************************************************************************************************************************
        //Celebrity
        //**************************************************************************************************************************************
        LopezMiquel_Card cBad = new LopezMiquel_Card("Bad", "Bunny", 3,80, 80, "Celebrity Team", "CBB");
        mainDeck.add(cBad);
        LopezMiquel_Card cJustin = new LopezMiquel_Card("Justin", "Bieber", 2,80, 80, "Celebrity Team", "CJB");
        mainDeck.add(cJustin);
        LopezMiquel_Card cRachel = new LopezMiquel_Card("Rachel", "DeMita", 2,80, 80, "Celebrity Team", "CRD");
        mainDeck.add(cRachel);
        LopezMiquel_Card cKevin = new LopezMiquel_Card("Kevin", "Hart", 2,80, 80, "Celebrity Team", "CKH");
        mainDeck.add(cKevin);
        LopezMiquel_Card cSnoop = new LopezMiquel_Card("Snoop", "Dogg", 4,80, 80, "Celebrity Team", "CSD");
        mainDeck.add(cSnoop);
        LopezMiquel_Card cUsain = new LopezMiquel_Card("Usain", "Bolt", 4,80, 80, "Celebrity Team", "CUB");
        mainDeck.add(cUsain);
        LopezMiquel_Card cVinny = new LopezMiquel_Card("Vinny", "Guadagnino", 3,80, 80, "Celebrity Team", "CVG");
        mainDeck.add(cVinny);
        LopezMiquel_Card cArmando = new LopezMiquel_Card("Pitbull", " ", 4,80, 80, "Celebrity Team", "CAP");
        mainDeck.add(cArmando);
        LopezMiquel_Card cIce = new LopezMiquel_Card("Ice", "Cube", 5,80, 80, "Celebrity Team", "CIC");
        mainDeck.add(cIce);
        LopezMiquel_Card cJustinT = new LopezMiquel_Card("Justin", "Timberlake", 4,80, 80, "Celebrity Team", "CSD");
        mainDeck.add(cJustinT);
        //**************************************************************************************************************************************
        //Brooklyn Nets
        //**************************************************************************************************************************************
        LopezMiquel_Card bnKyrie = new LopezMiquel_Card("Kyrie", "Irving", 1,92, 90, "Brooklyn Nets", "BNKI");
        mainDeck.add(bnKyrie);
        LopezMiquel_Card bnTyler = new LopezMiquel_Card("Tyler", "Jhonson", 1,75, 70, "Brooklyn Nets", "BNTJ");
        mainDeck.add(bnTyler);
        LopezMiquel_Card bnChris = new LopezMiquel_Card("Chris", "Chiozza", 1,55, 50, "Brooklyn Nets", "BNCC");
        mainDeck.add(bnChris);
        LopezMiquel_Card bnSpencer = new LopezMiquel_Card("Spencer", "Dinwiddie", 1,85, 80, "Brooklyn Nets", "BNSD");
        mainDeck.add(bnSpencer);
        LopezMiquel_Card bnJames = new LopezMiquel_Card("James", "Harden", 2,99, 85, "Brooklyn Nets", "BNJH");
        mainDeck.add(bnJames);
        LopezMiquel_Card bnLandry = new LopezMiquel_Card("Landry", "Shamet", 2,72, 71, "Brooklyn Nets", "BNLS");
        mainDeck.add(bnLandry);
        LopezMiquel_Card bnBruce = new LopezMiquel_Card("Bruce", "Brown", 2,63, 58, "Brooklyn Nets", "BNBB");
        mainDeck.add(bnBruce);
        LopezMiquel_Card bnJoe = new LopezMiquel_Card("Joe", "Harrys", 3,81, 75, "Brooklyn Nets", "BNJA");
        mainDeck.add(bnJoe);
        LopezMiquel_Card bnTimothe = new LopezMiquel_Card("Timothe", "Luwawu", 3,61, 55, "Brooklyn Nets", "BNTL");
        mainDeck.add(bnTimothe);
        LopezMiquel_Card bnKevin = new LopezMiquel_Card("Kevin", "Durant", 4,99, 95, "Brooklyn Nets", "BNKD");
        mainDeck.add(bnKevin);
        LopezMiquel_Card bnBlake = new LopezMiquel_Card("Blake", "Griffin", 4,87, 86, "Brooklyn Nets", "BNKG");
        mainDeck.add(bnBlake);
        LopezMiquel_Card bnJeff = new LopezMiquel_Card("Jeff", "Green", 4,72, 73, "Brooklyn Nets", "BNJG");
        mainDeck.add(bnJeff);
        LopezMiquel_Card bnReggie = new LopezMiquel_Card("Reggie", "Perry", 4,59, 54, "Brooklyn Nets", "BNRP");
        mainDeck.add(bnReggie);
        LopezMiquel_Card bnDeAndre = new LopezMiquel_Card("DeAndre", "Jordan", 5,85, 85, "Brooklyn Nets", "BNDJ");
        mainDeck.add(bnDeAndre);
        LopezMiquel_Card bnLaMarcus = new LopezMiquel_Card("LaMarcus", "Aldridge", 5,85, 85, "Brooklyn Nets", "BNNC");
        mainDeck.add(bnBlake);
        //**************************************************************************************************************************************
        //Philadelphia 76ers
        //**************************************************************************************************************************************
        LopezMiquel_Card p7Ben = new LopezMiquel_Card("Ben", "Simmons", 1,90, 85, "Philadelphia 76ers", "P7BS");
        mainDeck.add(p7Ben);
        LopezMiquel_Card p7George = new LopezMiquel_Card("George", "Hill", 1,70, 75, "Philadelphia 76ers", "P7GH");
        mainDeck.add(p7George);
        LopezMiquel_Card p7Tyrese = new LopezMiquel_Card("Tyrese", "Maxey", 1,60, 62, "Philadelphia 76ers", "P7TM");
        mainDeck.add(p7Tyrese);
        LopezMiquel_Card p7Isaiah = new LopezMiquel_Card("Isaiah", "Joe", 1,62, 59, "Philadelphia 76ers", "P7IJ");
        mainDeck.add(p7Isaiah);
        LopezMiquel_Card p7Seth = new LopezMiquel_Card("Seth", "Curry", 2,73, 70, "Philadelphia 76ers", "P7SC");
        mainDeck.add(p7Seth);
        LopezMiquel_Card p7Shake = new LopezMiquel_Card("Shake", "Milton", 2,65, 60, "Philadelphia 76ers", "P7SM");
        mainDeck.add(p7Shake);
        LopezMiquel_Card p7Danny = new LopezMiquel_Card("Danny", "Green", 3,70, 70, "Philadelphia 76ers", "P7DG");
        mainDeck.add(p7Danny);
        LopezMiquel_Card p7Matisse = new LopezMiquel_Card("Matisse", "Thybulle", 3,62, 54, "Philadelphia 76ers", "P7MT");
        mainDeck.add(p7Matisse);
        LopezMiquel_Card p7Furkan = new LopezMiquel_Card("Furkan", "Korkmaz", 3,62, 53, "Philadelphia 76ers", "P7FK");
        mainDeck.add(p7Furkan);
        LopezMiquel_Card p7Tobias = new LopezMiquel_Card("Tobias", "Harris", 4,85, 80, "Philadelphia 76ers", "P7TH");
        mainDeck.add(p7Tobias);
        LopezMiquel_Card p7Mike = new LopezMiquel_Card("Mike", "Scott", 4,70, 64, "Philadelphia 76ers", "P7MS");
        mainDeck.add(p7Mike);
        LopezMiquel_Card p7Anthony = new LopezMiquel_Card("Anthony", "Tolliver", 4,63, 60, "Philadelphia 76ers", "P7AT");
        mainDeck.add(p7Anthony);
        LopezMiquel_Card p7Joel = new LopezMiquel_Card("Joel", "Embid", 5,95, 90, "Philadelphia 76ers", "P7JE");
        mainDeck.add(p7Joel);
        LopezMiquel_Card p7Dwight = new LopezMiquel_Card("Dwight", "Howard", 5,75, 70, "Philadelphia 76ers", "P7DH");
        mainDeck.add(p7Dwight);
        //**************************************************************************************************************************************
        //Miami Heat
        //**************************************************************************************************************************************
        LopezMiquel_Card mhGoran = new LopezMiquel_Card("Goran", "Dragic", 1,75, 80, "Miami Heat", "MHGD");
        mainDeck.add(mhGoran);
        LopezMiquel_Card mhGabe = new LopezMiquel_Card("Gabe", "Vincent", 1,60, 59, "Miami Heat", "MHGV");
        mainDeck.add(mhGabe);
        LopezMiquel_Card mhKendrick = new LopezMiquel_Card("Kendrick", "Nunn", 1,70, 70, "Miami Heat", "MHKN");
        mainDeck.add(mhKendrick);
        LopezMiquel_Card mhTyler = new LopezMiquel_Card("Tyler", "Herro", 2,80, 75, "Miami Heat", "MHTH");
        mainDeck.add(mhTyler);
        LopezMiquel_Card mhAvery = new LopezMiquel_Card("Avery", "Bradley", 2,70, 70, "Miami Heat", "MHAB");
        mainDeck.add(mhAvery);
        LopezMiquel_Card mhMax = new LopezMiquel_Card("Max", "Strus", 2,55, 55, "Miami Heat", "MHMS");
        mainDeck.add(mhMax);
        LopezMiquel_Card mhJimmy = new LopezMiquel_Card("Jimmy", "Butler", 3,90, 90, "Miami Heat", "MHJB");
        mainDeck.add(mhJimmy);
        LopezMiquel_Card mhDuncan = new LopezMiquel_Card("Duncan", "Robinson", 3,75, 70, "Miami Heat", "MHDR");
        mainDeck.add(mhDuncan);
        LopezMiquel_Card mhAndre = new LopezMiquel_Card("Andre", "Iguodala", 3,70, 75, "Miami Heat", "MHAI");
        mainDeck.add(mhAndre);
        LopezMiquel_Card mhMoe = new LopezMiquel_Card("Moe", "Harkless", 3,60, 65, "Miami Heat", "MHMH");
        mainDeck.add(mhMoe);
        LopezMiquel_Card mhBam = new LopezMiquel_Card("Bam", "Adebayo", 4,90, 94, "Miami Heat", "MHBA");
        mainDeck.add(mhBam);
        LopezMiquel_Card mhPrecious = new LopezMiquel_Card("Precious", "Achiuwa", 4,70, 72, "Miami Heat", "MHPA");
        mainDeck.add(mhPrecious);
        LopezMiquel_Card mhChris = new LopezMiquel_Card("Chris", "Silva", 4,65, 62, "Miami Heat", "MHCS");
        mainDeck.add(mhPrecious);
        LopezMiquel_Card mhKZ = new LopezMiquel_Card("KZ", "Okpala", 4,62, 59, "Miami Heat", "MHKO");
        mainDeck.add(mhKZ);
        LopezMiquel_Card mhKelly = new LopezMiquel_Card("Kelly", "Olynyk", 5,80, 85, "Miami Heat", "MHKY");
        mainDeck.add(mhKelly);
        LopezMiquel_Card mhUdonis = new LopezMiquel_Card("Udonis", "Haslem", 5,72, 73, "Miami Heat", "MHUH");
        mainDeck.add(mhUdonis);
        LopezMiquel_Card mhMeyers = new LopezMiquel_Card("Meyers", "Leonard", 5,74, 76, "Miami Heat", "MHML");
        mainDeck.add(mhMeyers);
        //**************************************************************************************************************************************
        //Los Angeles Lakers
        //**************************************************************************************************************************************
        LopezMiquel_Card lalLebron = new LopezMiquel_Card("LeBron", "James", 1,99, 95, "Los Angeles Lakers", "LALLJ");
        mainDeck.add(lalLebron);
        LopezMiquel_Card lalCaruso = new LopezMiquel_Card("Alex", "Caruso", 1, 70, 75, "Los angeles Lakers", "LALAC");
        mainDeck.add(lalCaruso);
        LopezMiquel_Card lalDennis = new LopezMiquel_Card("Dennis", "Schroder", 2,80, 75, "Los Angeles Lakers", "LALDS");
        mainDeck.add(lalDennis);
        LopezMiquel_Card lalWesley = new LopezMiquel_Card("Wesley", "Matthews", 2,70, 70, "Los Angeles Lakers", "LALWM");
        mainDeck.add(lalWesley);
        LopezMiquel_Card lalCaldwell = new LopezMiquel_Card("Kentavious", "Caldwell-Pope", 3,85, 70, "Los Angeles Lakers", "LALKD");
        mainDeck.add(lalCaldwell);
        LopezMiquel_Card lalKyle = new LopezMiquel_Card("Kyle", "Kuzma", 3,85, 80, "Los Angeles Lakers", "LALKK");
        mainDeck.add(lalKyle);
        LopezMiquel_Card lalHorton = new LopezMiquel_Card("Talen", "Horton", 3,70, 75, "Los Angeles Lakers", "LALTH");
        mainDeck.add(lalHorton);
        LopezMiquel_Card lalMcKinnie = new LopezMiquel_Card("Alfonzo", "McKinnie", 3,60, 65, "Los Angeles Lakers", "LALAM");
        mainDeck.add(lalMcKinnie);
        LopezMiquel_Card lalDavis = new LopezMiquel_Card("Anthony", "Davis", 4,93, 90, "Los Angeles Lakers", "LALAD");
        mainDeck.add(lalDavis);
        LopezMiquel_Card lalMorris = new LopezMiquel_Card("Markieff", "Morris", 4,74, 75, "Los Angeles Lakers", "LALMM");
        mainDeck.add(lalMorris);
        LopezMiquel_Card lalDudley = new LopezMiquel_Card("Jared", "Dudley", 4,60, 33, "Los Angeles Lakers", "LALJD");
        mainDeck.add(lalDudley);
        LopezMiquel_Card lalCacok = new LopezMiquel_Card("Devontae", "Cacok", 4,60, 60, "Los Angeles Lakers", "LALDC");
        mainDeck.add(lalCacok);
        LopezMiquel_Card lalGasol = new LopezMiquel_Card("Marc", "Gasol", 5,70, 85, "Los Angeles Lakers", "LALMG");
        mainDeck.add(lalGasol);
        LopezMiquel_Card lalHarrell = new LopezMiquel_Card("Montrezl", "Harrell", 5,85, 85, "Los Angeles Lakers", "LALMH");
        mainDeck.add(lalHarrell);
        LopezMiquel_Card lalAnteto = new LopezMiquel_Card("Kostas", "Antetokoumpo", 5,61, 65, "Los Angeles Lakers", "LALKA");
        mainDeck.add(lalAnteto);
        LopezMiquel_Card lalAndre = new LopezMiquel_Card("Andre", "Drummond", 5,85, 80, "Los Angeles Lakers", "LALAD");
        mainDeck.add(lalAndre);
        //**************************************************************************************************************************************
        //Dallas Mavericks
        //**************************************************************************************************************************************
        LopezMiquel_Card dmLuka = new LopezMiquel_Card("Luka", "Doncic", 1,99, 90, "Dallas Mavericks", "DMLD");
        mainDeck.add(dmLuka);
        LopezMiquel_Card dmJalen = new LopezMiquel_Card("Jalen", "Brunson", 1,69, 65, "Dallas Mavericks", "DMJB");
        mainDeck.add(dmJalen);
        LopezMiquel_Card dmTrey = new LopezMiquel_Card("Trey", "Burke", 1,65, 62, "Dallas Mavericks", "DMTBU");
        mainDeck.add(dmTrey);
        LopezMiquel_Card dmTyrell = new LopezMiquel_Card("Tyrell", "Terry", 1,51, 50, "Dallas Mavericks", "DMTT");
        mainDeck.add(dmTyrell);
        LopezMiquel_Card dmJosh = new LopezMiquel_Card("Josh", "Richarson", 2,65, 62, "Dallas Mavericks", "DMJR");
        mainDeck.add(dmJosh);
        LopezMiquel_Card dmJJ = new LopezMiquel_Card("J.J.", "Redick", 2,80, 75, "Dallas Mavericks", "DMJJ");
        mainDeck.add(dmJJ);
        LopezMiquel_Card dmNate = new LopezMiquel_Card("Nate", "Hinton", 2,63, 64, "Dallas Mavericks", "DMNH");
        mainDeck.add(dmNate);
        LopezMiquel_Card dmTyler = new LopezMiquel_Card("Tyler", "Bey", 2,61, 69, "Dallas Mavericks", "DMTBE");
        mainDeck.add(dmTyler);
        LopezMiquel_Card dmDorian = new LopezMiquel_Card("Dorian", "Finney-Smith", 3,70, 75, "Dallas Mavericks", "DMDF");
        mainDeck.add(dmDorian);
        LopezMiquel_Card dmHardaway = new LopezMiquel_Card("Tim", "Hardaway Jr.", 3,85, 80, "Dallas Mavericks", "DMTH");
        mainDeck.add(dmHardaway);
        LopezMiquel_Card dmJoshG = new LopezMiquel_Card("Josh", "Green", 3,65, 62, "Dallas Mavericks", "DMJG");
        mainDeck.add(dmJoshG);
        LopezMiquel_Card dmMaxi = new LopezMiquel_Card("Maxi", "Kleber", 4,75, 70, "Dallas Mavericks", "DMMK");
        mainDeck.add(dmMaxi);
        LopezMiquel_Card dmNicolo = new LopezMiquel_Card("Nicolo", "Melli", 4,55, 54, "Dallas Mavericks", "DMNM");
        mainDeck.add(dmNicolo);
        LopezMiquel_Card dmKristaps = new LopezMiquel_Card("Kristaps", "Porzingis", 5,99, 90, "Dallas Mavericks", "DMKP");
        mainDeck.add(dmKristaps);
        LopezMiquel_Card dmWillie = new LopezMiquel_Card("W.", "Cauley-Stein", 5,75, 72, "Dallas Mavericks", "DMWK");
        mainDeck.add(dmWillie);
        LopezMiquel_Card dmDwight = new LopezMiquel_Card("Dwight", "Powell", 5,74, 74, "Dallas Mavericks", "DMDP");
        mainDeck.add(dmDwight);
        LopezMiquel_Card dmBoban = new LopezMiquel_Card("Boban", "Marjanovic", 5,69, 69, "Dallas Mavericks", "DMBM");
        mainDeck.add(dmBoban);
        //**************************************************************************************************************************************
        //Denver Nuggets
        //**************************************************************************************************************************************
        LopezMiquel_Card dnJamal = new LopezMiquel_Card("Jamal", "Murray", 1,90, 85, "Denver Nuggets", "DNJM");
        mainDeck.add(dnJamal);
        LopezMiquel_Card dnFacundo = new LopezMiquel_Card("Facundo", "Campazzo", 1,70, 70, "Denver Nuggets", "DNFC");
        mainDeck.add(dnFacundo);
        LopezMiquel_Card dnWill = new LopezMiquel_Card("Will", "Barton", 2,70, 71, "Denver Nuggets", "DNWB");
        mainDeck.add(dnWill);
        LopezMiquel_Card dnMonte = new LopezMiquel_Card("Monte", "Morris", 2,70, 70, "Denver Nuggets", "DNMM");
        mainDeck.add(dnMonte);
        LopezMiquel_Card dnMichael = new LopezMiquel_Card("Michael", "Porter Jr.", 3,85, 85, "Denver Nuggets", "DNMP");
        mainDeck.add(dnMichael);
        LopezMiquel_Card dnPJ = new LopezMiquel_Card("P.J.", "Dozier", 3,71, 65, "Denver Nuggets", "DNPJ");
        mainDeck.add(dnPJ);
        LopezMiquel_Card dnVlatko = new LopezMiquel_Card("Vlatko", "Cancar", 3,65, 66, "Denver Nuggets", "DNVC");
        mainDeck.add(dnVlatko);
        LopezMiquel_Card dnAaron = new LopezMiquel_Card("Aaron", "Gordon", 4,90, 85, "Denver Nuggets", "DNAG");
        mainDeck.add(dnAaron);
        LopezMiquel_Card dnPaul = new LopezMiquel_Card("Paul", "Millsap", 4,75, 75, "Denver Nuggets", "DNPM");
        mainDeck.add(dnPaul);
        LopezMiquel_Card dnJaMychal = new LopezMiquel_Card("JaMichal", "Green", 4,65, 66, "Denver Nuggets", "DNJG");
        mainDeck.add(dnJaMychal);
        LopezMiquel_Card dnNikola = new LopezMiquel_Card("Nikola", "Jokic", 5,90, 90, "Denver Nuggets", "DNNJ");
        mainDeck.add(dnNikola);
        LopezMiquel_Card dnJaVale = new LopezMiquel_Card("JaVale", "McGee", 5,70, 75, "Denver Nuggets", "DNJV");
        mainDeck.add(dnJaVale);
        LopezMiquel_Card dnBol = new LopezMiquel_Card("Bol", "Bol", 5,80, 70, "Denver Nuggets", "DNBB");
        mainDeck.add(dnBol);
        //**************************************************************************************************************************************
        //Milwaukee Bucks
        //**************************************************************************************************************************************
        LopezMiquel_Card mbJrue = new LopezMiquel_Card("Jrue", "Holiday", 1,80, 72, "Milwaukee Bucks", "MBJH");
        mainDeck.add(mbJrue);
        LopezMiquel_Card mbJeff = new LopezMiquel_Card("Jeff", "Teague", 1,75, 70, "Milwaukee Bucks", "MBJT");
        mainDeck.add(mbJeff);
        LopezMiquel_Card mbSam = new LopezMiquel_Card("Sam", "Merrill", 1,65, 62, "Milwaukee Bucks", "MBSM");
        mainDeck.add(mbSam);
        LopezMiquel_Card mbDonte = new LopezMiquel_Card("Donte", "DiVincenzo", 2,79, 73, "Milwaukee Bucks", "MBDD");
        mainDeck.add(mbDonte);
        LopezMiquel_Card mbBryn = new LopezMiquel_Card("Bryn", "Forbes", 2,62, 65, "Milwaukee Bucks", "MBBF");
        mainDeck.add(mbBryn);
        LopezMiquel_Card mbKhris = new LopezMiquel_Card("Khris", "Middleton", 3,90, 80, "Milwaukee Bucks", "MBKM");
        mainDeck.add(mbKhris);
        LopezMiquel_Card mbPat = new LopezMiquel_Card("Pat", "Connaughton", 3,70, 64, "Milwaukee Bucks", "MBPC");
        mainDeck.add(mbPat);
        LopezMiquel_Card mbThanasis = new LopezMiquel_Card("Thanasis", "Antetokounmpo", 3,70, 70, "Milwaukee Bucks", "MBTA");
        mainDeck.add(mbThanasis);
        LopezMiquel_Card mbRodions = new LopezMiquel_Card("Rodions", "Kurucs", 3,62, 59, "Milwaukee Bucks", "MBRK");
        mainDeck.add(mbRodions);
        LopezMiquel_Card mbGiannis = new LopezMiquel_Card("Giannis", "Antetokounmpo", 4,99, 99, "Milwaukee Bucks", "MBGA");
        mainDeck.add(mbGiannis);
        LopezMiquel_Card mbPJ = new LopezMiquel_Card("P.J.", "Tucker", 4,75, 75, "Milwaukee Bucks", "MBPJ");
        mainDeck.add(mbPJ);
        LopezMiquel_Card mbJordan = new LopezMiquel_Card("Jordan", "Nwora", 4,64, 60, "Milwaukee Bucks", "MBJN");
        mainDeck.add(mbJordan);
        LopezMiquel_Card mbBrook = new LopezMiquel_Card("Brook", "Lopez", 5,80, 81, "Milwaukee Bucks", "MBBL");
        mainDeck.add(mbBrook);
        LopezMiquel_Card mbBobby = new LopezMiquel_Card("Bobby", "Portis", 5,76, 72, "Milwaukee Bucks", "MBBP");
        mainDeck.add(mbBobby);
        //**************************************************************************************************************************************
        //Real Madrid
        //**************************************************************************************************************************************
        LopezMiquel_Card rmNicolas = new LopezMiquel_Card("Nicolas", "Laprovittola", 1,72, 70, "Real Madrid", "RMNL");
        mainDeck.add(rmNicolas);
        LopezMiquel_Card rmCarlos = new LopezMiquel_Card("Carlos", "Alocen", 1,65, 68, "Real Madrid", "RMCA");
        mainDeck.add(rmCarlos);
        LopezMiquel_Card rmSergio = new LopezMiquel_Card("Sergio", "Llull", 2,75, 70, "Real Madrid", "RMSL");
        mainDeck.add(rmSergio);
        LopezMiquel_Card rmJaycee = new LopezMiquel_Card("Jaycee", "Carroll", 2,75, 60, "Real Madrid", "RMJC");
        mainDeck.add(rmJaycee);
        LopezMiquel_Card rmFabien = new LopezMiquel_Card("Fabien", "Causer", 3,67, 67, "Real Madrid", "RMFC");
        mainDeck.add(rmFabien);
        LopezMiquel_Card rmRudy = new LopezMiquel_Card("Rodolfo (Rudy)", "Fernandez", 3,77, 70, "Real Madrid", "RMRF");
        mainDeck.add(rmRudy);
        LopezMiquel_Card rmAlberto = new LopezMiquel_Card("Alberto", "Abalde", 3,65, 69, "Real Madrid", "RMAA");
        mainDeck.add(rmAlberto);
        LopezMiquel_Card rmTaylor = new LopezMiquel_Card("Jeffery", "Taylor", 3,70, 68, "Real Madrid", "RMJT");
        mainDeck.add(rmTaylor);
        LopezMiquel_Card rmAnthony = new LopezMiquel_Card("Anthony", "Randolph", 4,75, 75, "Real Madrid", "RMAR");
        mainDeck.add(rmAnthony);
        LopezMiquel_Card rmFelipe = new LopezMiquel_Card("Felipe", "Reyes", 4,65, 65, "Real Madrid", "RMFR");
        mainDeck.add(rmFelipe);
        LopezMiquel_Card rmTrey = new LopezMiquel_Card("Trey", "Thompkins", 4,75, 70, "Real Madrid", "RMTT");
        mainDeck.add(rmTrey);
        LopezMiquel_Card rmAlex = new LopezMiquel_Card("Alex", "Tyus", 5,68, 67, "Real Madrid", "RMAT");
        mainDeck.add(rmAlex);
        LopezMiquel_Card rmUsman = new LopezMiquel_Card("Usman", "Garuba", 5,77, 75, "Real Madrid", "RMUG");
        mainDeck.add(rmUsman);
        LopezMiquel_Card rmVincent = new LopezMiquel_Card("Vincent", "Poirier", 5,70, 70, "Real Madrid", "RMVP");
        mainDeck.add(rmVincent);
        LopezMiquel_Card rmWalter = new LopezMiquel_Card("Walter", "Tavares", 5,73, 74, "Real Madrid", "RMWT");
        mainDeck.add(rmWalter);
        //**************************************************************************************************************************************
        //FC Barcelona
        //**************************************************************************************************************************************
        LopezMiquel_Card fbLeo = new LopezMiquel_Card("Leo", "Westermann", 1,65, 67, "FC Barcelona", "FBLW");
        mainDeck.add(fbLeo);
        LopezMiquel_Card fbNick = new LopezMiquel_Card("Nick", "Calathes", 1,68, 65, "FC Barcelona", "FBNK");
        mainDeck.add(fbNick);
        LopezMiquel_Card fbAlex = new LopezMiquel_Card("Alex", "Abrines", 2,70, 70, "FC Barcelona", "FBAA");
        mainDeck.add(fbAlex);
        LopezMiquel_Card fbKyle = new LopezMiquel_Card("Kyle", "Kuric", 2,67, 68, "FC Barcelona", "FBKK");
        mainDeck.add(fbKyle);
        LopezMiquel_Card fbAdam = new LopezMiquel_Card("Adam", "Hanga", 3,67, 68, "FC Barcelona", "FBAH");
        mainDeck.add(fbAdam);
        LopezMiquel_Card fbCory = new LopezMiquel_Card("Cory", "Higgins", 3,69, 65, "FC Barcelona", "FBCH");
        mainDeck.add(fbCory);
        LopezMiquel_Card fbVictor = new LopezMiquel_Card("Victor", "Claver", 3,62, 63, "FC Barcelona", "FBVC");
        mainDeck.add(fbVictor);
        LopezMiquel_Card fbRolands = new LopezMiquel_Card("Rolands", "Kuric", 4,61, 60, "FC Barcelona", "FBRS");
        mainDeck.add(fbRolands);
        LopezMiquel_Card fbPierre = new LopezMiquel_Card("Pierre", "Oriola", 4,70, 65, "FC Barcelona", "FBPO");
        mainDeck.add(fbPierre);
        LopezMiquel_Card fbRata = new LopezMiquel_Card("Nikola", "Mirotic", 4,75, 70, "FC Barcelona", "FBNM");
        mainDeck.add(fbRata);
        LopezMiquel_Card fbBrandon = new LopezMiquel_Card("Brondon", "Davies", 5,69, 65, "FC Barcelona", "FBBD");
        mainDeck.add(fbBrandon);
        LopezMiquel_Card fbArtem = new LopezMiquel_Card("Artem", "Pustovyi", 5,60, 58, "FC Barcelona", "FBAP");
        mainDeck.add(fbArtem);
        LopezMiquel_Card fbPau = new LopezMiquel_Card("Pau", "Gasol", 5,65, 55, "FC Barcelona", "FBPG");
        mainDeck.add(fbPau);
    }

    /**
     * Solo letras
     * @param input Pregunta
     * @return String
     */
    public static String onlyString(String input){
        Scanner sc = new Scanner(System.in);
        String x;
        boolean correctValue=false;

        do {
            System.out.print(input);
            x=sc.nextLine();

            try {
                Integer.parseInt(x);
                System.out.println(RED + "Nomes pots escriure lletres." + RESET);
            } catch (NumberFormatException excepcion) {
                correctValue=true;
            }
        }while(!correctValue);

        return  x;
    }
}