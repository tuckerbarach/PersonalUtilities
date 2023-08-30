import java.awt.*;
import javax.swing.*; 
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class TimeSelection extends Window
{
    static JComboBox monthBox, dayBox, yearBox, hourBox, minuteBox;
    static JPanel titlePanel, leftPanel, rightPanel, bottomPanel;
    static JButton cancelButton, submitButton;
    static JFrame frame;
    public static boolean isSelected, timeNotSelected;
    public static String[] returnArray;

    public static void createPanel(JLabel label, String ID, boolean needsAdded)
    {
        returnArray = new String[2];
        String[] selectionArray = new String[5];
        String returnString = "";
        isSelected = false;
        timeNotSelected = true;
        ArrayList<JComboBox> comboArray = new ArrayList<JComboBox>();
        frame = new JFrame();
        frame.setTitle("Personal Utilities");

        frame.setLayout(new BorderLayout());

        titlePanel = new JPanel();
        frame.add(titlePanel, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("<html>Enter when you want <br/> to have this done by:</html>", SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        frame.add(leftPanel, BorderLayout.WEST);

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        frame.add(rightPanel, BorderLayout.EAST);

        String[] months = {"Select Month:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthBox = new JComboBox(months);

        String[] days = {"Select Day:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        dayBox = new JComboBox(days);

        String[] years = new String[] {"Select Year:", "2020", "2021", "2022", "2023", "2024", "2025",
                "2026", "2027", "2028", "2029", "2030"};
        yearBox = new JComboBox(years);  

        String[] hours = new String[] {"Select Hour:", "00:", "01:", "02:", "03:", "04:", "05:",
                "06:", "07:", "08:", "09:", "10:", "11:",
                "12:", "13:", "14:", "15:", "16:", "17:",
                "18:", "19:", "20:", "21:", "22:", "23:"};
        hourBox = new JComboBox(hours); 

        String[] minutes = new String[] {"Select Minute:", ":00", ":05", ":10", ":15", ":20", ":25", ":30",
                ":35", ":40", ":45", ":50", ":55"};
        minuteBox = new JComboBox(minutes); 

        ImageIcon icon = new ImageIcon("res/calendar_icon.png"); 
        JLabel thumbnail = new JLabel();
        thumbnail.setIcon(icon);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        comboArray.add(monthBox);
        comboArray.add(dayBox);
        comboArray.add(yearBox);
        comboArray.add(hourBox);
        comboArray.add(minuteBox);

        //String dateStop //String[] timeLeftAndDate = {DateDifference.timeDifference(dateStop), dateStop};

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    frame.dispose();
                } 
            } );
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    //gets all the JComboBox selections and returns them:
                    for(int i = 0; i < comboArray.size(); i++)
                    {

                        if(((String)comboArray.get(i).getSelectedItem()).contains("Select"))
                        {
                            JLabel errorMessage = new JLabel("Info not selected!");
                            bottomPanel.add(errorMessage);
                            bottomPanel.revalidate();
                        }
                        else
                        {
                            isSelected = true;
                            selectionArray[i] = (String)comboArray.get(i).getSelectedItem();
                        }
                    }

                    if(isSelected)
                    {
                        ToDoListItem.endTime = selectionArray[0] + "/" + selectionArray[1] + "/" + selectionArray[2] + " " + selectionArray[3].replace(":", "") + selectionArray[4] + ":00";
                        returnArray[0] = selectionArray[0] + "/" + selectionArray[1] + "/" + selectionArray[2] + " " + selectionArray[3].replace(":", "") + selectionArray[4] + ":00";
                        returnArray[1] = DateDifference.timeDifference(returnArray[0]);

                        frame.dispose();
                        addTimeToLabel(returnArray, label, ID, needsAdded);
                    }
                } 
            } );

        leftPanel.add(monthBox);
        leftPanel.add(dayBox);
        leftPanel.add(yearBox);
        //rightPanel.add(thumbnail);
        rightPanel.add(hourBox);
        rightPanel.add(minuteBox);
        bottomPanel.add(submitButton);
        bottomPanel.add(cancelButton);

        // Make submit button default:
        JRootPane rootPane = SwingUtilities.getRootPane(submitButton); 
        rootPane.setDefaultButton(submitButton);

        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);

    }

    public static void addTimeToLabel(String[] timeArray, JLabel label, String ID, boolean needsAdded)
    {
        label.setText(timeArray[1]);
        if(needsAdded)
        {
            ToDoListItem.timerLabels.add(label);
        }
        try
        {
            ToDoListStoring.changeData(ID, 5, "", timeArray[0]);
        }
        catch(IOException io)
        {

        }
    }
}