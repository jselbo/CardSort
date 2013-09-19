package cards;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game
{
    public final static int BUBBLE_SORT = 0, SELECTION_SORT = 1, INSERTION_SORT = 2, QUICKSORT = 3, COMB_SORT = 4;
    
    private JFrame parent;
    private Card[] deck;
    private int iteration = 0, currCard = 0, currSort = -1;
    private boolean dealing = false, sorting = false, sorted = true;
    private int[] sortHistory; //1st 5 are # of iterations, 2nd 5 are fastest times
    
    //Comb sort variable
    int gap = 52;
    
    public Game(JFrame parent)
    {
        this.parent = parent;
        
        deck = new Card[52];
        for (int i = 0; i < 52; i++)
        {
            int value, suit;
            value = i%13 + 1;
            switch ((int)(i/13))
            {
                case 0: suit = Card.HEART;
                    break;
                case 1: suit = Card.DIAMOND;
                    break;
                case 2: suit = Card.SPADE;
                    break;
                case 3: suit = Card.CLUB;
                    break;
                default: suit = Card.HEART;
            }
            Card c = new Card(value, suit);
            c.setUp(true);
            deck[i] = c;
        }
        
        sortHistory = IOUtility.getSortHistory();
    }
    
    public void paint(Graphics g)
    {
        for (Card c : deck)
            c.paint(g);
    }
    
    public void run()
    {
        iteration++;
        
        if (dealing && iteration%3 == 0)
        {
            if (currCard < 13)
                deck[currCard].setLocation(25 + 50 * currCard, 50);
            else if (currCard < 26)
                deck[currCard].setLocation(25 + 50 * currCard - 13 * 50, 150);
            else if (currCard < 39)
                deck[currCard].setLocation(25 + 50 * currCard - 26 * 50, 250);
            else
                deck[currCard].setLocation(25 + 50 * currCard - 39 * 50, 350);
            currCard++;
            if (currCard >= 52)
            {
                currCard = 0;
                dealing = false;
            }
        }
        
        if (sorting && iteration%6 == 0)
        {
            boolean stillSorting = false;
            switch (currSort)
            {
                case BUBBLE_SORT: stillSorting = bubbleSort();
                    break;
                case SELECTION_SORT: stillSorting = selectionSort();
                    break;
                case INSERTION_SORT: stillSorting = insertionSort();
                    break;
                case QUICKSORT: //Due to its recursive nature, quicksort is unable to run in slow motion
                    break;
                case COMB_SORT: stillSorting = combSort();
                    break;
            }
            for (int i = 0; i < deck.length; i++)
            {
                if (i < 13)
                    deck[i].setLocation(25 + 50 * i, 50);
                else if (i < 26)
                    deck[i].setLocation(25 + 50 * i - 13 * 50, 150);
                else if (i < 39)
                    deck[i].setLocation(25 + 50 * i - 26 * 50, 250);
                else
                    deck[i].setLocation(25 + 50 * i - 39 * 50, 350);
            }
            if (!stillSorting)
            {
                sorting = false;
                currSort = -1;
                sorted = true;
            }
        }
    }
    
    public void clearCards()
    {
        for (Card c : deck)
            c.setLocation(200, -100);
    }
    
    public void deal()
    {
        dealing = true;
    }
    
    public boolean isDealing()
    {
        return dealing;
    }
    
    public synchronized void shuffle(int count)
    {
        sorted = false;
        for (int i = 0; i < count; i++)
        {
            int r1 = (int)(Math.random() * deck.length);
            int r2 = (int)(Math.random() * deck.length);
            swap(r1, r2);
        }
        deal();
    }
    
    public void showSortHistory()
    {
        new SortHistoryDialog(parent, false).setVisible(true);
    }
    
    public synchronized void sort(int sortType, boolean slomo)
    {
        if (slomo)
        {
            sorting = true;
            currSort = sortType;
        }
        else
        {
            clearCards();
            long startTime = System.nanoTime();
            switch (sortType)
            {
                case BUBBLE_SORT: while (bubbleSort()) {}
                    break;
                case SELECTION_SORT: while (selectionSort()) {}
                    break;
                case INSERTION_SORT: while (insertionSort()) {}
                    break;
                case QUICKSORT: quicksort(0, deck.length - 1);
                    break;
                case COMB_SORT: while (combSort()) {}
                    break;
                default: throw new IllegalArgumentException("Sort type must be a valid Game sort type constant.");
            }
            
            long timeElapsed = System.nanoTime() - startTime;
            JOptionPane.showMessageDialog(parent, "Time elapsed: " + timeElapsed + " nanoseconds",
                                          "Sorting complete", JOptionPane.INFORMATION_MESSAGE);
            switch (sortType)
            {
                case BUBBLE_SORT:
                    sortHistory[0]++;
                    if (sortHistory[5] > timeElapsed)
                        sortHistory[5] = (int)timeElapsed;
                    break;
                case SELECTION_SORT:
                    sortHistory[1]++;
                    if (sortHistory[6] > timeElapsed)
                        sortHistory[6] = (int)timeElapsed;
                    break;
                case INSERTION_SORT:
                    sortHistory[2]++;
                    if (sortHistory[7] > timeElapsed)
                        sortHistory[7] = (int)timeElapsed;
                    break;
                case QUICKSORT:
                    sortHistory[3]++;
                    if (sortHistory[8] > timeElapsed)
                        sortHistory[8] = (int)timeElapsed;
                    break;
                case COMB_SORT:
                    sortHistory[4]++;
                    if (sortHistory[9] > timeElapsed)
                        sortHistory[9] = (int)timeElapsed;
                    break;
            }
            IOUtility.setSortHistory(sortHistory);
            sorted = true;
            deal();
        }
    }
    
    private synchronized boolean bubbleSort()
    {
        boolean changed = false;
        for (int i = 0; i < deck.length - 1; i++)
        {
            if (deck[i].getSortValue() > deck[i+1].getSortValue())
            {
                swap(i, i+1);
                changed = true;
            }
        }
        return changed;
    }
    
    private synchronized boolean selectionSort()
    {
        int min = currCard;
        for (int k = currCard+1; k < deck.length; k++)
        {
            if (deck[k].getSortValue() < deck[min].getSortValue())
                min = k;
        }
        if (min != currCard)
        {
            swap(currCard, min);
        }
        currCard++;
        if (currCard > deck.length)
        {
            currCard = 0;
            return false;
        }
        return true;
    }
    
    private synchronized boolean insertionSort()
    {
        int i = currCard;
        while (i > 0 && deck[i-1].getSortValue() > deck[i].getSortValue())
        {
            swap(i, i-1);
            i--;
        }
        currCard++;
        if (currCard >= deck.length)
        {
            currCard = 0;
            return false;
        }
        return true;
    }
    
    private boolean quicksort(int left, int right)
    {
        int i = left, j = right;
        Card pivot = deck[(left + right) / 2];
        do
        {
            while (deck[i].getSortValue() < pivot.getSortValue())
                i++;
            while (deck[j].getSortValue() > pivot.getSortValue())
                j--;
            if (i <= j)
            {
                swap(i, j);
                i++;
                j--;
            }
        }
        while (i <= j);
        
        if (left < j)
            quicksort(left, j);
        if (right > i)
            quicksort(i, right);
        return false;
    }
    
    private synchronized boolean combSort()
    {
        if (gap > 1) 
            gap = (int)(gap / 1.247330950103979);
        int i = 0;
        boolean swapped = false;
        while (i + gap < deck.length)
        {
            if (deck[i].getSortValue() > deck[i + gap].getSortValue())
            {
                swap(i, i + gap);
                swapped = true;
            }
            i++;
        }
        if (gap > 1 || swapped)
            return true;
        else
        {
            gap = deck.length;
            return false;
        }
    }
    
    public boolean isSorting()
    {
        return sorting;
    }
    
    public boolean isSorted()
    {
        return sorted;
    }
    
    private synchronized void swap(int c1, int c2)
    {
        Card temp = deck[c1];
        deck[c1] = deck[c2];
        deck[c2] = temp;
    }
}
