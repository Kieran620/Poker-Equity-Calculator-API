package KieranKhan.PokerEquityAPI.Solver;

import java.util.HashMap;

public class Card implements Comparable<Card> {
    public int number;
    public String suit;


    public Card(int num, String suit) {
        number = num;
        this.suit = suit;
    }

    public int compareTo(Card other) {
        return number - other.number;
    }

    @Override
    public boolean equals(Object o) {
        Card c = (Card) o;
        if(c instanceof Card) {
            return number == c.number && suit.equals(c.suit);
        }
        return false;
    }

    public boolean sameSuit(Card other) {
        return suit.equals(other.suit);
    }

    @Override
    public String toString() {
        return number + suit;
    }
}
