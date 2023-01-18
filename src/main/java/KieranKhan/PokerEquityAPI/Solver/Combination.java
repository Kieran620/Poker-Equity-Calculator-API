package KieranKhan.PokerEquityAPI.Solver;

import java.util.HashSet;

public class Combination {

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Starting and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    static void combinationUtil(Card arr[], Card data[], int start,
                                int end, int index, int r, HashSet<Hand> cardCombos)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            Card[] temp = new Card[5];
            for (int j=0; j<r; j++) {
                //System.out.print(data[j].toString()+" ");
                temp[j] = data[j];
            }
            cardCombos.add(new Hand(temp));
            //System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, cardCombos);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static HashSet<Hand> makeCombination(Card arr[], int n, int r)
    {
        HashSet<Hand> cardCombos = new HashSet<>();
        // A temporary array to store all combination one by one
        Card data[]=new Card[r];

        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r, cardCombos);

        return cardCombos;
    }

    public void makeCombos(Card[] arr) {
        if(arr.length != 7)
            throw new IllegalArgumentException();
        int r = 5;
        int n = arr.length;

    }

    /*Driver function to check for above function*/
//    public static void main (String[] args) {
//        int arr[] = {1, 2, 3, 4, 5, 6, 7};
//        Card cardArr[] = {new Card(1, "s"), new Card(2, "s"), new Card(3, "d"),
//                new Card(4, "c"), new Card(5, "h"), new Card(6, "s"), new Card(7, "s")};
//        int r = 5;
//        int n = arr.length;
//        printCombination(cardArr, n, r);
//    }
}
