package KieranKhan.PokerEquityAPI.Solver;

public class Tests {
    public void test1() {
        Hand hand = new Hand(new Card[]{new Card(2, "c"), new Card(3, "d"), new Card(4, "s"),
                new Card(5, "s"), new Card(14, "s")});

        Hand hand2 = new Hand(new Card[]{new Card(8, "c"), new Card(9, "d"), new Card(10, "h"),
                new Card(11, "h"), new Card(14, "c")});

        System.out.println(hand.bestHand() + " : " + hand2.bestHand());
        System.out.println(hand.compareTo(hand2));

        System.out.println(hand.rank.toString() + " : " + hand2.rank.toString());
    }

    public void test2() {
        Solver s = new Solver();
        //s.exec();
    }
}
