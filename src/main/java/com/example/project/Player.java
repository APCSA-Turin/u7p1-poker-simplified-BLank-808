package com.example.project;

import java.util.ArrayList;



public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards= new ArrayList<>(); //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){   
        boolean pair1=false;
        boolean pair2=false;
        boolean three=false;
        boolean four=false;
        boolean sameS=false;
        boolean assending=true;
        allCards.clear();
        allCards.add(hand.get(0));
        allCards.add(hand.get(1));
        for (Card C : communityCards) {
            allCards.add(C);
        }

        sortAllCards();

        //System.out.println(allCards);
        ArrayList<Integer> frequencyR= findRankingFrequency();
        //System.out.println(frequencyR);
        ArrayList<Integer> frequencyS= findSuitFrequency();
        //System.out.println(frequencyS);
        //CompareSuits
        if(frequencyS.size()==1){
            sameS=true;
        }

        //compare Rank frequency
        if (frequencyR.contains(4)){
            four=true;
        }else if (frequencyR.contains(3) && frequencyR.contains(2)){
            three=true;
            pair1=true;
        }else if (frequencyR.contains(3)){
            three=true;
        }else if(frequencyR.contains(2)){
            pair1=true;
            if(frequencyR.size()==3){
                pair2=true;
            }
        }

        Card prev=null;
        for (Card card : allCards) {
            if(prev!=null){
                if(Utility.getRankValue(card.getRank()) != (Utility.getRankValue(prev.getRank()) +1)){
                    assending=false;
                    break;
                }
            }
            prev=card;
        }

        if(sameS && assending && allCards.get(0).getRank().equals("10")){
            return "Royal Flush";
        }else if(sameS && assending){
            return "Straight Flush";
        }else if (four){
            return "Four of a Kind";
        } else if (three && pair1){
            return "Full House";
        } else if (sameS) {
            return "Flush";
        } else if(assending) {
            return "Straight";
        } else if (three){
            return "Three of a Kind";
        } else if (pair1 && pair2){
            return "Two Pair";
        } else if (pair1){
            return "A Pair";
        } else if (!containsCard(communityCards, allCards.get(4))){
            return "High Card";
        }else{
        return "Nothing";
        }
    }

    public void sortAllCards(){
        for (int index = 0; index < allCards.size(); index++) {
            int val = Utility.getRankValue(allCards.get(index).getRank());
            for ( int i=index-1; i>=-1; i--) {
                if(i==-1 || Utility.getRankValue(allCards.get(i).getRank())<val){
                    allCards.add(i+1,allCards.get(index));
                    allCards.remove(index+1);
                    break;
                }
            }
        }
    } 

    public static boolean containsCard(ArrayList<Card> list, Card c ){
        for (Card n: list){
            if(n.getRank().equals(c.getRank()) && n.getSuit().equals(c.getSuit())){
                return true;
            }
        }
        return false;
    }



    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> flist= new ArrayList<>();
        Card prev=allCards.get(0);
        flist.add(findFrequency(prev, "R"));
        
        for (Card card : allCards) {
            //System.out.println(!card.getRank().equals(prev.getRank()) + " " + card);
            if(!card.getRank().equals(prev.getRank())){
                flist.add(findFrequency(card, "R"));
                prev=card;
            }
        }
        return flist;
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> flist= new ArrayList<>();
        ArrayList<String> suitscontained= new ArrayList<>();
        for (Card card : allCards) {
            if(!suitscontained.contains(card.getSuit())){
                flist.add(findFrequency(card, "S"));
                suitscontained.add(card.getSuit());
            }
        }
        return flist; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public int findFrequency(Card card, String R_or_S){
        String val;
        int count=0;
        if(R_or_S.equals("R")){
            val= card.getRank();
            for (Card c : allCards) {
                if(c.getRank().equals(val)){
                    count++;
                }
            }
        }else{
            val=card.getSuit();
            for (Card c : allCards) {
                if(c.getSuit().equals(val)){
                    count++;
                }
            }
        }

        return count;
    }

    public int getHighestInHand(){
        int c1 =Utility.getRankValue(hand.get(0).getRank());
        int c2 =Utility.getRankValue(hand.get(1).getRank());
        if(c1>c2){
            return c1;
        } else {
            return c2;
        }
    }

    public int getlowestInHand(){
        int c1 =Utility.getRankValue(hand.get(0).getRank());
        int c2 =Utility.getRankValue(hand.get(1).getRank());
        if(c1>c2){
            return c2;
        } else {
            return c1;
        }
    }

    public void clear(){
        hand.clear();
    }
}
