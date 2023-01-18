package KieranKhan.PokerEquityAPI.Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Hand implements Comparable<Hand>{
    private Card[] hand = new Card[5];
    public ArrayList<Integer> rank;

    public Hand(Card[] h) {
        if(h.length != 5) {
            System.out.println("Incorrect card array size");
            throw new IllegalArgumentException();
        }
        hand = h;
        sort();
    }

    public int compareTo(Hand other) {
        int result = bestHand() - other.bestHand();
        if(result == 0) {
            for(int i = 0; i < rank.size(); i++) {
                //System.out.println("ranks: " + rank.toString() + ", " + other.rank.toString());
                result = rank.get(i) - other.rank.get(i);
                if(result != 0) {
                    break;
                }
            }
        }
        return result;
    }

    public int bestHand() {
        if(straightFlush()) {
            //System.out.println("Straight Flush");
            return 8;
        }
        else if(fourOfAKind()) return 7;
        else if(fullHouse()) return 6;
        else if (flush()) {
            //System.out.println("Flush");
            return 5;}
        else if(straight()) {
            //System.out.println("Straight");
            return 4;
        }
        else if(threeOfAKind()) return 3;
        else if(twoPair()) return 2;
        else if(pair()) return 1;
        else if(highCard()) {
            //System.out.println("high card");
            return 0;
        }
        else {
            System.out.println("NOOOOOOOOOOOOOOOOO");
            return -1;
        }
    }

    public boolean highCard() {
        rank = new ArrayList<>();
        for(Card c : hand) {
            rank.add(0, c.number);
        }
        return true;
    }

    public boolean pair() {
        rank = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        boolean result = false;
        for(Card c : hand) {
            if(!set.add(c.number)) {
                rank.add(0, c.number);
                rank.add(0, c.number);
                set.remove(c.number);
                result = true;
            }
        }
        if(result) {
            for(Integer i : set) {
                rank.add(2, i);
            }
        }
        return result;
    }

    public boolean twoPair() {
        rank = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        int count = 0;
        for(Card c : hand) {
            if(!set.add(c.number)) {
                set.remove(c.number);
                count++;
                if(rank.size() > 0 && c.number > rank.get(0)) {
                    rank.add(0, c.number);
                    rank.add(0, c.number);
                } else {
                    rank.add(c.number);
                    rank.add(c.number);
                }
            }
        }
        rank.add((int) set.toArray()[0]);
        return count == 2;
    }

    public boolean threeOfAKind() {
        rank = new ArrayList<>();
        int count = 1;
        boolean result = false;
        int x = 0;
        for(int i = 1; i < hand.length; i++) {
            if(hand[i].number == hand[i-1].number)
                count++;
            else
                count = 1;
            if(count == 3) {
                for(int j = i-2; j <= i; j++) {
                    rank.add(0, hand[j].number);
                }
                result = true;
                x = i;
            }
        }
        if(result) {
            for(int k = hand.length-1; k >= 0; k--) {

                if(hand[k].number < rank.get(2)) {
                    rank.add(hand[k].number);
                }
                else if(hand[k].number > rank.get(2)) {
                    rank.add(hand[k].number);
                }
            }
        }
//        boolean result = false;
//        int count = 1;
//        for(int i = hand.length-1; i >= 1; i--) {
//            if(hand[i] == hand[i-1]) {
//                count++;
//                rank.add(hand[i].number);
//            }
//            else {
//                count = 1;
//                rank.clear();
//            }
//            if(count == 3)
//        }
        return result;
    }

    public boolean straight() {
        rank = new ArrayList<>();
        boolean result = true;
        for(int i = 1; i < hand.length; i++) {
            if(hand[i].number != (hand[i-1].number + 1)) {
                if (result && i == hand.length-1 && (hand[i].compareTo(hand[0]) == 12))
                    result = true;
                else
                    result = false;
            }
            rank.add(hand[i-1].number);
        }
        rank.add(hand[hand.length-1].number);
        return result;
    }

    public boolean flush() {
        rank = new ArrayList<>();
        rank.add(0, hand[0].number);
        boolean result = true;
        for(int i = 1; i < hand.length; i++) {
            if(!hand[i].sameSuit(hand[i-1])) {
                result = false;
            }
            rank.add(hand[i].number);
        }
        return result;
    }

    public boolean fullHouse() {

        boolean result = false;
        if(threeOfAKind()) {
            int count = 1;
            if (rank.get(rank.size()-1) == rank.get(rank.size()-2)) {
                result = true;
            }
            if(result && threeOfAKind()) return true;
        }

        return result;
        //return pair() && threeOfAKind() && (rank.get(0) != rank.get(4));
    }

    public boolean fourOfAKind() {
        rank = new ArrayList<>();
        int count = 1;
        boolean result = false;
        for(int i = 1; i < hand.length; i++) {
            if (hand[i].number == hand[i - 1].number) {
                count++;
            }
            else
                count = 1;
            if(count == 4) {
                for(int j = i-3; j <= i; j++) {
                    rank.add(0, hand[j].number);
                }
                rank.add(hand[0].number == 7 ? hand[hand.length-1].number : hand[0].number);
                result = true;
                break;
            }

        }
        return result;
    }

    public boolean straightFlush() {
        return straight() && flush();
    }

    @Override
    public String toString() {
        String s = "";
        for(Card c : hand) {
            s += c.toString() + " ";
        }
        return s;
    }

    public void sort() {
        int N = hand.length;

        // Build heap (rearrange array)
        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(hand, N, i);

        // One by one extract an element from heap
        for (int i = N - 1; i > 0; i--) {
            // Move current root to end
            Card temp = hand[0];
            hand[0] = hand[i];
            hand[i] = temp;

            // call max heapify on the reduced heap
            heapify(hand, i, 0);
        }
    }

    void heapify(Card arr[], int N, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < N && arr[l].compareTo(arr[largest]) > 0)
            largest = l;

        // If right child is larger than largest so far
        if (r < N && arr[r].compareTo(arr[largest]) > 0 )
            largest = r;

        // If largest is not root
        if (largest != i) {
            Card swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }
}
