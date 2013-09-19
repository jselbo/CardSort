package cards;

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CardSort extends JFrame
{
    private MainPanel p;
    private JMenuBar menuBar;
    private JMenu file, help;
    private JMenuItem history, about;
    
    public CardSort(String s)
    {
        super(s);
        p = new MainPanel(this);
        menuBar = new JMenuBar();
        file = new JMenu("File");
        history = new JMenuItem("History...");
        history.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                p.showSortHistory();
            }
        });
        file.add(history);
        
        help = new JMenu("Help");
        about = new JMenuItem("About...");
        about.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(CardSort.this, "A basic card sorting program.\n\nCreated by Josh.",
                                              "About Card Sort", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(about);
        
        menuBar.add(file);
        menuBar.add(help);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(p);
        setJMenuBar(menuBar);
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - (getWidth() / 2), center.y - (getHeight() / 2));
        
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        new CardSort("Card Sort");
    }
}