package cards;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;

public class Card extends GameElement
{
    public final static int HEART = 1, DIAMOND = 2, SPADE = 3, CLUB = 4;

    private int value, suit;
    private boolean isUp = false;

    private Image up, down;

    public Card(int value, int suit)
    {
        this.value = value;
        this.suit = suit;

        char s;
        switch (suit)
        {
            case HEART: s = 'H';
                    break;
            case DIAMOND: s = 'D';
                    break;
            case SPADE: s = 'S';
                    break;
            case CLUB: s = 'C';
                    break;
            default: throw new IllegalArgumentException("Suit must be a valid Card suit constant.");
        }

        up = new ImageIcon("images/" + Integer.toString(value) + s + ".png").getImage();
        down = new ImageIcon("images/down.png").getImage();
    }

    @Override
    public void paint(Graphics g)
    {
        if (isUp)
            setImage(up);
        else
            setImage(down);
        super.paint(g);
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
    
    public int getSortValue()
    {
        int sortValue = -1;
        switch (suit)
        {
            case HEART: sortValue = value;
                break;
            case DIAMOND: sortValue = 13 + value;
                break;
            case SPADE: sortValue = 26 + value;
                break;
            case CLUB: sortValue = 39 + value;
                break;
        }
        return sortValue;
    }

    public void setSuit(int suit)
    {
        this.suit = suit;
    }

    public int getSuit()
    {
        return suit;
    }

    public void setUp(boolean isUp)
    {
        this.isUp = isUp;
    }

    public boolean isUp()
    {
        return isUp;
    }
}