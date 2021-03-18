package com.company;

import Models.Card;
import Models.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final static String RED = "\033[31m", RESET = "\u001B[0m";
    static ArrayList<Player> playersList = new ArrayList<>();
    static ArrayList<Card> mainDeck = new ArrayList<>();
    static ArrayList<Card> botFloor = new ArrayList<>();
    static ArrayList<Card> playerFloor = new ArrayList<>();

    public static void main(String[] args) {
	    addPlayer();
        addCardsToMainDeck();
        dealCards(0);
        dealCards(1);
        game();
    }

    public static void game(){
        boolean victory = false;

        victory = fight();

        if (victory){
            System.out.println("You win game");
        }else {
            System.out.println("You lose game");
        }

    }

    public static boolean fight(){
        int victory = 0;

        victory+=oneVsOneDEF();

        victory+=oneVsOneATK();

        victory+=twoVsTwo();

        victory+=oneVsOneDEF();

        return victory >= 3;
    }

    public static int oneVsOneDEF(){
        int victory=0;

        System.out.println("This round you def");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getDef()>=botFloor.get(0).getAtk()){
            System.out.println("Has ganado esta ronda");
            victory++;
        }else {
            System.out.println("Has perdido esta ronda");
        }
        clearArray();
        return victory;
    }

    public static int oneVsOneATK(){
        int victory=0;

        System.out.println("This round you atack");
        round(0,false);
        round(1,false);
        System.out.println(botFloor);
        System.out.println(playerFloor);
        if (playerFloor.get(0).getAtk()>=botFloor.get(0).getDef()){
            System.out.println("Has ganado esta ronda");
            victory++;
        }else {
            System.out.println("Has perdido esta ronda");
        }
        clearArray();
        return victory;
    }

    public static int twoVsTwo(){
        int victory=0;

        System.out.println("This round you play 2 vs 2");
        round(0,true);
        round(1,true);
        System.out.println(botFloor);
        System.out.println(playerFloor);

        if ((playerFloor.get(0).getOvr()+playerFloor.get(1).getOvr())>=(botFloor.get(0).getDef()+botFloor.get(1).getDef())){
            System.out.println("Has ganado esta ronda");
            victory+=2;
        }else {
            System.out.println("Has perdido esta ronda");
        }
        clearArray();
        return victory;
    }

    public static void clearArray(){
        playerFloor.clear();
        botFloor.clear();
    }

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

    public static Card turn(int player){
        Card election = new Card("default", "default", 0, 0, 0, "default", "default");
        String card;
        Scanner sc = new Scanner(System.in);
        boolean correctElection=false;

        for (Card c:playersList.get(player).getDeck()) {
            System.out.println(c);
        }
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

    public static void dealCards( int x ){
        for (int i = 0; i < 5; i++) {
            int number = (int)(Math.random()*mainDeck.size());
            playersList.get(x).addCardToDeck(mainDeck.get(number));
            mainDeck.remove(number);
        }
    }

    public static void addPlayer(){
        Player pc = new Player("Bot");
        playersList.add(pc);
        Player miquel = new Player("miqueel97");
        playersList.add(miquel);
    }

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
        Card plEarvin = new Card("Earvin 'Magic'", "Johnson", 1,99, 99, "Los Angeles Lakers (1987)", "PLEJ");
        mainDeck.add(plEarvin);
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