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

    public static void main(String[] args) {
        int option;

        do {
            System.out.println("Bienvenido a LaSalleNBA");
            System.out.println( "1.  Opciones del usuario\n"+
                    "2.  Abrir paquete\n"+
                    "3.  Jugar\n"+
                    "4.  Agregar un jugador manualmente\n"+
                    "5.  Como jugar\n"+
                    "6.  Salir");
            option=numbersWithRange("Escoge una opción: ", 1, 6);

            mainDeck.clear();
            clearArray();
            addPlayer();
            addCardsToMainDeck();

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
                    System.out.println("Básicamente es una simulación de " +
                            "un partido de baloncesto en modo de cartas.\n" +
                            "Cada jugador dispones de un base, un escolta, un alero, un ala pívot y un pívot.\n" +
                            "Hay tres tipos de turnos:\n" +
                            "DEF\n" +
                            "Se compara la puntuación defensiva de la carta escogida versus el ataque de la otra carta\n" +
                            "ATK\n" +
                            "Se compara la puntuación ofensiva de la carta escogida versus la defensa de la otra carta\n" +
                            "2vs2\n" +
                            "Se compara la puntuación media de la carta escogida versus la puntuación media de la carta\n\n" +
                            "El jugador que sume el mayor numero de puntos en el numero de rondas seleccionadas\n" +
                            "ganara.\n");
                    break;
                case 6:
                    System.out.println("Has escogido salir");
                    break;
            }
        }while (option!=6);

    }

    public static void addPlayerManual(){
        Scanner sc = new Scanner(System.in);
        String name, surname, team;
        int position, atk, def, number;
        number = (int)(Math.random());

        name = onlyString("Nombre: ");
        surname = onlyString("Apellido: ");
        position = numbersWithRange("Posición: ", 1, 5);
        atk = numbersWithRange("Ataque: ", 50, 99);
        def = numbersWithRange("Defensa: ", 50, 99);
        System.out.println("Equipo: ");
        team= sc.nextLine();

        LopezMiquel_Card newPlayer = new LopezMiquel_Card(name, surname, position,atk, def, team);
        mainDeck.add(newPlayer);
    }

    /**
     * Abrir un sobre
     */
    public static void openPacket(){
        int number = (int)(Math.random()*mainDeck.size());
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
            System.out.println("Has ganado el cuarto");
        }else {
            System.out.println("Has perdido el cuarto");
        }
    }

    /**
     * Se produce una ronda del juego (oneVsOneDEF(), oneVsOneATK(), twoVsTwo() y oneVsOneDEF())
     * @return int, 1 o 0 dependiendo si ha ganado
     */
    public static boolean quarter(){

        oneVsOneDEF();
        scoreboard();
        dealOneCard(0);
        dealOneCard(1);

        oneVsOneATK();
        scoreboard();
        dealOneCard(0);
        dealOneCard(1);

        twoVsTwo();
        scoreboard();
        dealOneCard(0);
        dealOneCard(1);

        oneVsOneDEF();
        dealOneCard(0);
        dealOneCard(1);

        return scoreboard[0]>scoreboard[1];
    }

    /**
     * Imprime el marcador actual
     */
    public static void scoreboard(){
        System.out.println("*********************************\n" +
                           "                                 \n"+
                           "      " + playersList.get(1).getNickname() + "         " + playersList.get(0).getNickname() + "      \n"+
                           "        " + scoreboard[1] + "              " + scoreboard[0] + "       \n"+
                           "                                 \n"+
                           "*********************************\n");
    }

    /**
     * Compara el atributo def de la carta del usuario vs el atributo atk del bot
     * Asigna valor al marcador
     * @return Si el jugador ha ganado o ha perdido
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
     * @return Si el jugador ha ganado o ha perdido
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
     * @return Si el jugador ha ganado o ha perdido
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
            System.out.print("Escoge una opción: ");
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
                System.out.println(RED + "Carta no encontrada" + RESET);
            }
        }while (!correctElection);

        return election;
    }

    /**
     * Distribuye 5 cartas a cada jugador
     * @param x Jugador al que asignar las cartas
     */
    public static void dealCards(int x){
        int number = (int)(Math.random()*mainDeck.size());
        clearArray();

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
     * Reparte una casta y comprueba que no haya de esa posicion
     * @param x jugador al que repartir
     */
    public static void dealOneCard(int x){
        do {
            int number = (int)(Math.random()*mainDeck.size());

            playersList.get(x).addCardToDeck(mainDeck.get(number));
            for (int i = 0; i < playersList.get(x).getDeck().size()-1; i++) {
                if (playersList.get(x).getDeck().get(i).getPosition()==mainDeck.get(number).getPosition()){
                    playersList.get(x).getDeck().remove(i);
                }
            }
        }while (playersList.get(x).getDeck().size()<5);
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
                System.out.println(RED+"ERROR: No has escrito un numero"+RESET);
                sc.nextLine();
            }else{
                x = sc.nextInt();
                sc.nextLine();

                if (x < valorMin || x > valorMax){
                    System.out.println(RED+"ERROR: El numero esta fuera del rango"+RESET);
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
        LopezMiquel_Card plAllen = new LopezMiquel_Card("Allen", "Iverson", 1,99, 99, "Philadelphia 76ers (2001)", "PLAI");
        mainDeck.add(plAllen);
        LopezMiquel_Card plShaquille = new LopezMiquel_Card("Shaquille", "O'Neal", 5,99, 99, "Los Angeles Lakers (2002)", "PLSO");
        mainDeck.add(plShaquille);
        LopezMiquel_Card plKarl = new LopezMiquel_Card("Karl", "Malone", 5,99, 99, "Utah Jazz (1989)", "PLKM");
        mainDeck.add(plKarl);
        LopezMiquel_Card plKareem = new LopezMiquel_Card("Kareem", "Abdul-Jabbar", 5,99, 99, "Los Angeles Lakers (1980)", "PLKA");
        mainDeck.add(plKareem);
        LopezMiquel_Card plMagic = new LopezMiquel_Card("Magic", "Johnson", 1,99, 99, "Los Angeles Lakers (1987)", "PLEJ");
        mainDeck.add(plMagic);
        LopezMiquel_Card plWilt = new LopezMiquel_Card("Wilt", "Chamberlain", 5,99, 99, "Philadelphia 76ers (1961)", "PLWC");
        mainDeck.add(plWilt);
        LopezMiquel_Card plBill = new LopezMiquel_Card("Bill", "Russell", 5,99, 99, "Boston Celtics (1962)", "PLBR");
        mainDeck.add(plBill);
        //**************************************************************************************************************************************
        //Brooklyn Nets
        //**************************************************************************************************************************************
        LopezMiquel_Card bnKyrie = new LopezMiquel_Card("Kyrie", "Irving", 1,92, 90, "Brooklyn Nets", "BNKI");
        mainDeck.add(bnKyrie);
        LopezMiquel_Card bnTyler = new LopezMiquel_Card("Tyler", "Jhnson", 1,75, 70, "Brooklyn Nets", "BNTJ");
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
        LopezMiquel_Card bnNicolas = new LopezMiquel_Card("Nicolas", "Claxton", 5,54, 49, "Brooklyn Nets", "BNNC");
        mainDeck.add(bnNicolas);
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
        LopezMiquel_Card lalDamian = new LopezMiquel_Card("Damian", "Jones", 5,60, 65, "Los Angeles Lakers", "LALDS");
        mainDeck.add(lalDamian);
    }

    /**
     * Solo letras
     * @param input Pregunta
     * @return String
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
}