package KieranKhan.PokerEquityAPI.Solver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.text.DecimalFormat;

public class Solver {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> board = new ArrayList<>();
    private final int[] possibleNums = new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    private final String[] possibleSuits = new String[] {"s", "d", "c", "h"};
    private HashMap<String, Integer> cardMap = new HashMap<>();

    public Solver() {
        makeMap(cardMap);
    }

    public Solver(/*@JsonProperty("hands")*/ List<String> cards, /*@JsonProperty("board")*/ List<String> board) {
        makeMap(cardMap);
        this.cards = parseList(cards);
        this.board = parseList(board);

        System.out.println(cards.toString());
        System.out.println(board.toString());
    }

    public ArrayList<Card> parseList(List<String> data) {
        ArrayList<Card> arr = new ArrayList<>();

        for(String s : data) {
            arr.add(new Card(cardMap.get(s.substring(0, s.length()-1)), String.valueOf(s.charAt(s.length() - 1))));
        }
        return arr;
    }

    public List<String> exec2() {
        return solve(5-board.size(), board);
    }

    private Card[] genCards(int size) {
        Card[] middle = new Card[5];
        int count = 0;
        while(count < size) {
            int r1 = (int) (Math.random() * 13);
            int r2 = (int) (Math.random() * 4);
            Card temp = new Card(possibleNums[r1], possibleSuits[r2]);
            if(cards.contains(temp))
                continue;
            middle[count] = temp;
            count++;
            //System.out.println(temp);
        }
        return middle;
    }

    private Hand bestHand(HashSet<Hand> cards) {
        Hand best = null;
        for(Hand h : cards) {
            if(best == null)
                best = h;
            else if(h.compareTo(best) > 0) {
                best = h;
            }
        }
        return best;
    }


    private Card[] makeArray(Card a, Card b, Card[] middle) {
        Card[] arr = new Card[7];
        arr[0] = a;
        arr[1] = b;
        for(int i = 2; i < arr.length; i++) {
            arr[i] = middle[i-2];
        }
        return arr;
    }

    private void makeMap(HashMap<String, Integer> map) {
        for(int i = 2; i < possibleNums.length - 2; i++) {
            map.put("" + i, i);
        }
        map.put("j", 11);
        map.put("q", 12);
        map.put("k", 13);
        map.put("a", 14);
    }

    public List<String> solve(int size, ArrayList<Card> middle) {

        List<String> wins = new ArrayList<>();
        double wins1 = 0, wins2 = 0, ties = 0;
        int iterations = 30000;

        for(int i = 0; i < iterations; i++) {
            Card[] mid = genCards(size);

            for(int j = 0; j < middle.size(); j++)
                mid[j+size] = middle.get(j);


            Hand hand1 = bestHand(Combination.makeCombination(makeArray(cards.get(0), cards.get(1), mid), 7, 5));
            Hand hand2 = bestHand(Combination.makeCombination(makeArray(cards.get(2), cards.get(3), mid), 7, 5));

            int result = hand1.compareTo(hand2);

            //System.out.println(hand1.toString() + " : " + hand2.toString());
//            System.out.println(middle.toString());
//            System.out.println(result);

            if(result > 0)
                wins1++;
            else if(result < 0)
                wins2++;
            else
                ties++;
        }
        DecimalFormat decFormat = new DecimalFormat("#.##");
        System.out.println("Hand1 win: " + decFormat.format((wins1/iterations)*100) +
                "%, Hand2 win: " + decFormat.format((wins2/iterations)*100) +
                "%, Tie: " + decFormat.format((ties/iterations)*100) + "%");
        wins.add(decFormat.format((wins1/iterations)*100));
        wins.add(decFormat.format((wins2/iterations)*100));
        wins.add(decFormat.format((ties/iterations)*100));

        return wins;
    }

}
