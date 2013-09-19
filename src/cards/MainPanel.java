package cards;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel implements Runnable, ActionListener
{
    public final static Dimension DIM = new Dimension(700, 550);
    
    private JFrame parent;
    private boolean running = true;
    private Game game;
    
    public MainPanel(JFrame parent)
    {
        this.parent = parent;
        
        setPreferredSize(DIM);
        setBackground(new Color(0, 127, 0));
        initComponents();
        sort.addActionListener(this);
        shuffle.addActionListener(this);
        
        game = new Game(parent);
        game.clearCards();
        game.deal();
        new Thread(this).start();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        slomo = new javax.swing.JCheckBox();
        sortType = new javax.swing.JComboBox();
        shuffle = new javax.swing.JButton();
        sort = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Card Manipulation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255)));
        jPanel1.setOpaque(false);

        slomo.setText("Slow motion");
        slomo.setOpaque(false);

        sortType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bubble Sort", "Selection Sort", "Insertion Sort", "Quicksort", "Comb Sort" }));

        shuffle.setFont(new java.awt.Font("Tahoma", 1, 11));
        shuffle.setText("Shuffle");

        sort.setFont(new java.awt.Font("Tahoma", 1, 11));
        sort.setText("Sort");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shuffle)
                .addGap(237, 237, 237)
                .addComponent(sort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slomo, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(sortType, 0, 114, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(shuffle, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addComponent(sort, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slomo)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(294, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.white);
        
        if (game.isDealing())
            g.drawString("Dealing...", jPanel1.getX() + shuffle.getX() + shuffle.getWidth() + 5,
                         jPanel1.getY() + shuffle.getY() + (shuffle.getHeight() / 2));
        else if (game.isSorting())
            g.drawString("Sorting...", jPanel1.getX() + shuffle.getX() + shuffle.getWidth() + 5,
                         jPanel1.getY() + shuffle.getY() + (shuffle.getHeight() / 2));
        else if (((String)(sortType.getSelectedItem())).equals("Quicksort"))
            g.drawString("Slow motion is unavailable for quicksort.", jPanel1.getX() + shuffle.getX() + shuffle.getWidth() + 5,
                         jPanel1.getY() + shuffle.getY() + (shuffle.getHeight() / 2));
        game.paint(g);
    }
    
    public void run()
    {
        while (running)
        {
            game.run();
            
            if (game.isDealing() || game.isSorting())
            {
                shuffle.setEnabled(false);
                sort.setEnabled(false);
            }
            else
            {
                shuffle.setEnabled(true);
                sort.setEnabled(!game.isSorted());
            }
            
            if (((String)(sortType.getSelectedItem())).equals("Quicksort"))
            {
                slomo.setSelected(false);
                slomo.setEnabled(false);
            }
            else
                slomo.setEnabled(true);
            
            repaint();
            try
            {
                Thread.sleep(15);
            }
            catch (InterruptedException ex)
            {
                System.out.println("The thread has been interrupted.");
                System.out.println("Details:");
                ex.printStackTrace(System.out);
            }
        }
    }
    
    public void showSortHistory()
    {
        game.showSortHistory();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();
        if (src.equals(shuffle))
        {
            game.clearCards();
            game.shuffle(700);
        }
        else if (src.equals(sort))
        {
            String choice = (String)sortType.getSelectedItem();
            boolean slow = slomo.isSelected();
            if (choice.equals("Bubble Sort"))
                game.sort(Game.BUBBLE_SORT, slow);
            else if (choice.equals("Selection Sort"))
                game.sort(Game.SELECTION_SORT, slow);
            else if (choice.equals("Insertion Sort"))
                game.sort(Game.INSERTION_SORT, slow);
            else if (choice.equals("Quicksort"))
                game.sort(Game.QUICKSORT, slow);
            else if (choice.equals("Comb Sort"))
                game.sort(Game.COMB_SORT, slow);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton shuffle;
    private javax.swing.JCheckBox slomo;
    private javax.swing.JButton sort;
    private javax.swing.JComboBox sortType;
    // End of variables declaration//GEN-END:variables
    
}
