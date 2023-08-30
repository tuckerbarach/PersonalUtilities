import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import javax.swing.border.*;

//import org.python.util.PythonInterpreter;

public class UpcomingEvents
{
    final static int ITEMS_IN_CALENDAR_PANEL = 3;

    public static ArrayList<JPanel> getUpcomingEvents() throws IOException
    {
        FileReader fileObject = new FileReader("res/data/calendar_data.txt");
        Scanner scan = new Scanner(fileObject);
        ArrayList<String> allData = new ArrayList<String>();
        ArrayList<JPanel> panels = new ArrayList<JPanel>();

        while(scan.hasNext())
        {
            allData.add(scan.nextLine());
        }
        scan.close();
        for(int i = 0; i < allData.size(); i += ITEMS_IN_CALENDAR_PANEL)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel startTime = new JLabel("Start Time: " + allData.get(i));
            JLabel endTime = new JLabel("End Time: " + allData.get(i + 1));
            JLabel title = new JLabel(allData.get(i + 2));
            title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

            JPanel topPanel = new JPanel();
            JPanel centerPanel = new JPanel();
            JPanel bottomPanel = new JPanel();
            //centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            //centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            topPanel.add(title);
            centerPanel.add(startTime);
            bottomPanel.add(endTime);

            panel.add(topPanel);
            panel.add(centerPanel);
            panel.add(bottomPanel);

            panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

            panels.add(panel); // add to ArrayList for display later
        }
        return panels;
    }
}