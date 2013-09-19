package cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class IOUtility
{
    public static int[] getSortHistory()
    {
        BufferedReader reader = null;
        int[] history = new int[10];
        
        try
        {
            reader = new BufferedReader(new FileReader("history/history.txt"));
            for (int i = 0; i < 10; i++)
                history[i] = Integer.parseInt(reader.readLine());
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Could not open file. The specified file could not be found.",
                                          "Error Opening File", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "An unknown error occurred while opening the file.",
                                          "Error Opening File", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e) {}
            }
        }
        
        return history;
    }
    
    public static void setSortHistory(int[] history)
    {
        PrintWriter writer = null;
        
        try
        {
            writer = new PrintWriter(new FileWriter("history/history.txt"));
            for (int i = 0; i < 10; i++)
                writer.println(Integer.toString(history[i]));
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Could not write to file. The specified file could not be found.",
                                          "Error Writing to File", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "An unknown error occurred while writing to the file.",
                                          "Error Writing to File", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            if (writer != null)
                writer.close();
        }
    }
}