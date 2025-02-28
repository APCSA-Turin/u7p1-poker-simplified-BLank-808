package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;
    int topCard;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public  void initializeDeck(){ //hint.. use the utility class
        int rank=0;
        int suit=0;
        String[] Ranks=Utility.getRanks();
        String[] Suits=Utility.getSuits();
        for (int i=0; i<52; i++)
        {
            cards.add(new Card(Ranks[rank], Suits[suit]));

            rank++;
            if(rank==12){
                rank=0;
            }
            suit++;
            if (suit==4){
                suit=0;
            }
        }
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
        topCard=0;
    }

    public  Card drawCard(){
       Card c=cards.get(topCard);
       topCard++;
       return c;
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}