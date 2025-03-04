package com.example.project;
import java.util.ArrayList;
import java.util.Scanner;


public class Game{

    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        if (Utility.getHandRanking(p1Hand)>Utility.getHandRanking(p2Hand)){
            return "Player 1 wins!";
        }else if (Utility.getHandRanking(p1Hand)<Utility.getHandRanking(p2Hand)){
            return "Player 2 wins!";
        }else{
            String p1MaxR=p1.getAllCards().get(4).getRank();
            String p2MaxR=p2.getAllCards().get(4).getRank();
            if (p1Hand.equals("Three of a Kind") || p1Hand.equals("Full House")){
                //Checks ranking of 3 of a kind before defaulting to highcard
                p1MaxR = p1.getAllCards().get(p1.findRankingFrequency().lastIndexOf(3)+1).getRank();
                p2MaxR = p2.getAllCards().get(p2.findRankingFrequency().lastIndexOf(3)+1).getRank();
                    if (Utility.getRankValue(p1MaxR)>Utility.getRankValue(p2MaxR)){
                        return "Player 1 wins!"; 
                    }else if (Utility.getRankValue(p1MaxR)<Utility.getRankValue(p2MaxR)){
                        return "Player 2 wins!";
                    }else{
                        if(p1.getHighestInHand()>p2.getHighestInHand() || p1.getlowestInHand()> p2.getlowestInHand()){
                            return "Player 1 wins!"; 
                        }else if (p1.getHighestInHand()<p2.getHighestInHand()  || p1.getlowestInHand()< p2.getlowestInHand()){
                            return "Player 2 wins!";
                        }else{
                            return"Tie!";
                        }
                    }
                } else if (p1Hand.contains("Pair")){
                //Checks ranking of pair before defaulting to highcard
                p1MaxR = p1.getAllCards().get(p1.findRankingFrequency().lastIndexOf(2)+1).getRank();
                p2MaxR = p2.getAllCards().get(p2.findRankingFrequency().lastIndexOf(2)+1).getRank();
                    if (Utility.getRankValue(p1MaxR)<Utility.getRankValue(p2MaxR)){
                        return "Player 1 wins!"; 
                    }else if (Utility.getRankValue(p1MaxR)>Utility.getRankValue(p2MaxR)){
                        return "Player 2 wins!";
                    } else {
                        if(p1.getHighestInHand()>p2.getHighestInHand()){
                            return "Player 1 wins!"; 
                        }else if (p1.getHighestInHand()<p2.getHighestInHand()){
                            return "Player 2 wins!";
                        }else{
                            return"Tie!";
                        }
                    }
                } else {
                        if(p1.getHighestInHand()>p2.getHighestInHand()){
                            return "Player 1 wins!"; 
                        }else if (p1.getHighestInHand()<p2.getHighestInHand()){
                            return "Player 2 wins!";
                        }else{
                            return"Tie!";
                        }
                }
        }
    }

    public static void play(){ //simulate card playing
        int tokens=100;
        Deck deck = new Deck();
        deck.shuffleDeck();
        Player p1= new Player();
        Player p2 = new Player();
        ArrayList<Card> comunityCards = new ArrayList<>();
        System.out.println("Wanna play some poker with me?");
        System.out.print("Response: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        int stake = 0;
        while(!input.equals("no") && tokens>0 && tokens<200){
            p1.clear();
            p2.clear();
            comunityCards.clear();
            System.out.println("Current tokens: " + tokens);
            System.out.print("Stake: ");
            stake=scan.nextInt();
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
            comunityCards.add(deck.drawCard());
            comunityCards.add(deck.drawCard());
            comunityCards.add(deck.drawCard());
            System.out.println("Community cards: " + comunityCards);
            System.out.println("Your cards: " + p1.getHand());
            System.out.print("Fold or Raise? ");
            input = scan.next();
            if(input.equals("raise")){
                stake+=stake;
                String victor = Game.determineWinner(p1, p2, p1.playHand(comunityCards), p2.playHand(comunityCards), comunityCards);
                System.out.println(victor);
                if(victor.equals("Player 2 wins!")){
                    tokens-=stake;
                }else if (victor.equals("Player 1 wins!")){
                    tokens+=stake;
                }
            }else{
                tokens-=stake;
                System.out.println("Player 1 loses");
            }
            deck.pregamecheck(7);
        }
        scan.close();
        if(tokens>=200){
            System.out.println("You won");
        }else if (tokens<=0){
            System.out.println("Guess you went broke");
        }else{
            System.out.println("Till next time then");
        }
    }
        
    public static void main(String[] args) {
     Game.play();

    }

}