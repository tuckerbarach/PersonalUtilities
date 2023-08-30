import java.awt.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.io.*;

/**
 * Write a description of class ToDoListItem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ToDoListItem extends Window
{
    static JPanel mainPanel;
    static JPanel itemTitlePanel; // top
    static JPanel itemToggleEditPanel; //right side
    static JPanel itemPriorityPanel; //left side
    static JPanel itemDescriptionPanel; //center
    static JPanel itemExtraPanel; // bottom

    static JLabel titleLabel;
    static JLabel descriptionLabel;

    static JButton priorityButton, colorWheelButton, timerButton, editTitleButton, editDescriptionButton, removeButton;
    static JColorChooser chooser;

    static ArrayList<JLabel> timerLabels = new ArrayList<JLabel>();
    public static String endTime;

    final static Color color = Color.blue; //default background
    static boolean timeNotSelected;
    static String[] timeItems;

    /**
     * Creates a new JPanel with proper formats for a ToDoList item
     * @return JPanel
     */

    public static JPanel createItem(Window window)
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        itemTitlePanel = new JPanel();
        itemToggleEditPanel = new JPanel();
        itemPriorityPanel = new JPanel();
        itemDescriptionPanel = new JPanel();
        itemExtraPanel = new JPanel();

        JLabel timerLabel = new JLabel();

        String title = JOptionPane.showInputDialog("Enter a title");
        String description = JOptionPane.showInputDialog("Enter a description");       
        int thePriority = setPriority(itemPriorityPanel, false, -1);

        //String timeLeft = timeItems[0]; //countdown

        titleLabel = new JLabel(title);

        descriptionLabel = new JLabel(description);

        itemTitlePanel.add(titleLabel);
        itemDescriptionPanel.add(descriptionLabel);

        // Add edit icons
        editTitleButton = new JButton(new ImageIcon("res/edit_label_icon.png"));
        editTitleButton.setBorderPainted(false);
        editTitleButton.setActionCommand("edit_title_and_description");
        editTitleButton.setVisible(false);

        editDescriptionButton = new JButton(new ImageIcon("res/edit_label_icon.png"));
        editDescriptionButton.setBorderPainted(false);
        editDescriptionButton.setActionCommand("edit_title_and_description");

        editDescriptionButton.setVisible(false);

        removeButton = new JButton(new ImageIcon("res/remove_icon.png"));
        removeButton.setBorderPainted(false);
        removeButton.setActionCommand("remove");
        removeButton.setName("remove");

        removeButton.setVisible(false);

        itemTitlePanel.add(editTitleButton);
        itemDescriptionPanel.add(editDescriptionButton);

        colorWheelButton = new JButton(new ImageIcon("res/color_wheel_icon.png"));
        colorWheelButton.setBorderPainted(false);
        colorWheelButton.setActionCommand("color");
        colorWheelButton.setName("color");

        itemToggleEditPanel.add(colorWheelButton);
        itemToggleEditPanel.add(removeButton);

        //Timer pane:
        timerButton = new JButton(new ImageIcon("res/timer_icon.png"));
        timerButton.setBorderPainted(false);
        timerButton.setActionCommand("timer_icon");

        itemExtraPanel.add(timerButton);
        timerLabel.setText(""); //timeLeft
        itemExtraPanel.add(timerLabel);
        //timerLabels.add(timerLabel);

        editTitleButton.addActionListener(window);
        priorityButton.addActionListener(window);
        editDescriptionButton.addActionListener(window); 
        colorWheelButton.addActionListener(window);
        timerButton.addActionListener(window);
        removeButton.addActionListener(window);

        itemTitlePanel.setName("1");
        itemDescriptionPanel.setName("2");
        itemPriorityPanel.setName("3");
        itemToggleEditPanel.setName("4");
        itemExtraPanel.setName("5");

        mainPanel.add(itemTitlePanel, BorderLayout.NORTH);
        mainPanel.add(itemToggleEditPanel, BorderLayout.EAST);
        mainPanel.add(itemPriorityPanel, BorderLayout.WEST);
        mainPanel.add(itemDescriptionPanel, BorderLayout.CENTER);
        mainPanel.add(itemExtraPanel, BorderLayout.SOUTH);
        itemTitlePanel.setBackground(color);
        itemToggleEditPanel.setBackground(color);
        itemPriorityPanel.setBackground(color);
        itemDescriptionPanel.setBackground(color);
        itemExtraPanel.setBackground(color);

        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        String colorHex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        //0000FF
        // add to storage:
        ToDoListStoring.toDoListItems.add(new ToDoListStoring( Window.panelID + "", title, description, thePriority, colorHex, ""));
        try
        {
            ToDoListStoring.storeItem(new ToDoListStoring(Window.panelID + "", title, description, thePriority, colorHex, ""));
        }
        catch(IOException io)
        {
            //herehere
        } 
        TimeSelection.createPanel(timerLabel, Window.panelID + "", true);
        return mainPanel;
    }

    public static JPanel createItemFromFile(ToDoListStoring item, Window window)
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        itemTitlePanel = new JPanel();
        itemToggleEditPanel = new JPanel();
        itemPriorityPanel = new JPanel();
        itemDescriptionPanel = new JPanel();
        itemExtraPanel = new JPanel();

        JLabel timerLabel = new JLabel();

        String title = item.title;
        String description = item.description;     
        int thePriority = item.priority;
        setPriority(itemPriorityPanel, true, thePriority);
        String theColor = item.color;
        String timeLeft = DateDifference.timeDifference(item.timeEnd);

        titleLabel = new JLabel(title);

        descriptionLabel = new JLabel(description);

        itemTitlePanel.add(titleLabel);
        itemDescriptionPanel.add(descriptionLabel);

        // Add edit icons
        editTitleButton = new JButton(new ImageIcon("res/edit_label_icon.png"));
        editTitleButton.setBorderPainted(false);
        editTitleButton.setActionCommand("edit_title_and_description");
        editTitleButton.setVisible(false);

        editDescriptionButton = new JButton(new ImageIcon("res/edit_label_icon.png"));
        editDescriptionButton.setBorderPainted(false);
        editDescriptionButton.setActionCommand("edit_title_and_description");

        editDescriptionButton.setVisible(false);

        itemTitlePanel.add(editTitleButton);
        itemDescriptionPanel.add(editDescriptionButton);

        removeButton = new JButton(new ImageIcon("res/remove_icon.png"));
        removeButton.setBorderPainted(false);
        removeButton.setActionCommand("remove");
        removeButton.setName("remove");

        removeButton.setVisible(false);

        colorWheelButton = new JButton(new ImageIcon("res/color_wheel_icon.png"));
        colorWheelButton.setBorderPainted(false);
        colorWheelButton.setActionCommand("color");
        colorWheelButton.setName("color");

        itemToggleEditPanel.add(colorWheelButton);
        itemToggleEditPanel.add(removeButton);

        //Timer pane:
        timerButton = new JButton(new ImageIcon("res/timer_icon.png"));
        timerButton.setBorderPainted(false);
        timerButton.setActionCommand("timer_icon");

        itemExtraPanel.add(timerButton);
        timerLabel.setText(timeLeft);
        itemExtraPanel.add(timerLabel);
        timerLabels.add(timerLabel);

        editTitleButton.addActionListener(window);
        priorityButton.addActionListener(window);
        editDescriptionButton.addActionListener(window); 
        colorWheelButton.addActionListener(window);
        timerButton.addActionListener(window);
        removeButton.addActionListener(window);

        itemTitlePanel.setName("1");
        itemDescriptionPanel.setName("2");
        itemPriorityPanel.setName("3");
        itemToggleEditPanel.setName("4");
        itemExtraPanel.setName("5");

        mainPanel.add(itemTitlePanel, BorderLayout.NORTH);
        mainPanel.add(itemToggleEditPanel, BorderLayout.EAST);
        mainPanel.add(itemPriorityPanel, BorderLayout.WEST);
        mainPanel.add(itemDescriptionPanel, BorderLayout.CENTER);
        mainPanel.add(itemExtraPanel, BorderLayout.SOUTH);
        itemTitlePanel.setBackground(Color.decode(theColor));
        itemToggleEditPanel.setBackground(Color.decode(theColor));
        itemPriorityPanel.setBackground(Color.decode(theColor));
        itemDescriptionPanel.setBackground(Color.decode(theColor));
        itemExtraPanel.setBackground(Color.decode(theColor));

        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        return mainPanel;
    }

    /**
     * Changes the text of the ToDoListItem's title
     * @param title what the new title should be
     */
    public static void changeWords(JLabel label, String newWords)
    {
        label.setText(newWords);
    }

    public static void createItemOffFile(ToDoListItem item)
    {

    }

    public static int setPriority(JPanel priorityPanel, boolean isFromFile, int thePriority)
    {
        ImageIcon img = new ImageIcon("res/priority1.png");
        int priority = -1;

        if(!isFromFile)
        {
            String[] priorityChoices = {"1", "2", "3"};
            String priorityString = (String)JOptionPane.showInputDialog(
                    null,                   //could specify location in     frame
                    "Select the priority",   //the question
                    "Personal Utilities",               //the frame title
                    JOptionPane.PLAIN_MESSAGE,      //the icon type 
                    img,                       /**THIS IS THE IMAGE ICON*/
                    priorityChoices,                    //array of options
                    priorityChoices[0]);                //the default to be selected
            priority = Integer.parseInt(priorityString);
        }

        if(priority == 1 || thePriority == 1)
        {
            priorityButton = new JButton(new ImageIcon("res/priority1.png"));
        }

        else if(priority == 2 || thePriority == 2)
        {
            priorityButton = new JButton(new ImageIcon("res/priority2.png"));
        }

        else if(priority == 3 || thePriority == 3)
        {
            priorityButton = new JButton(new ImageIcon("res/priority3.png"));
        }

        priorityButton.setBorderPainted(false);
        priorityButton.setActionCommand("priority");
        priorityPanel.add(priorityButton);

        return priority;
    }

    public static int changePriority(JButton button)
    {
        {
            ImageIcon img = new ImageIcon("res/priority1.png");

            String[] priorityChoices = {"1", "2", "3"};
            String priorityString = (String)JOptionPane.showInputDialog(
                    null,                   //could specify location in     frame
                    "Select the priority",   //the question
                    "Personal Utilities",               //the frame title
                    JOptionPane.PLAIN_MESSAGE,      //the icon type 
                    img,                       /**THIS IS THE IMAGE ICON*/
                    priorityChoices,                    //array of options
                    priorityChoices[0]);                //the default to be selected
            int priority = Integer.parseInt(priorityString);

            if(priority == 1)
            {
                button.setIcon(new ImageIcon("res/priority1.png"));
            }

            else if(priority == 2)
            {
                button.setIcon(new ImageIcon("res/priority2.png"));
            }

            else
            {
                button.setIcon(new ImageIcon("res/priority3.png"));
            }

            return priority;
        }
    }

    public static void addColorChooser(Component[] comps, JPanel panel, JButton buttonToHide, JPanel wholePanel, String wholePanelID)
    {
        chooser = new JColorChooser();
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
        buttonToHide.setVisible(false); // hide original color button
        panel.add(chooser);

        chooser.getSelectionModel().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    Color color = chooser.getColor();
                    for(Component comp : comps)
                    {
                        comp.setBackground(color);
                    }
                    panel.remove(chooser);
                    buttonToHide.setVisible(true);
                    panel.revalidate();

                    //Updating storing:
                    String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());  //0000FF
                    try
                    {
                        ToDoListStoring.changeData(wholePanelID, 4, "", hex);
                    }
                    catch(IOException io)
                    {

                    }
                }
            });
    }

    public static void updateAllTimers()
    {
        if(timerLabels.size() != 0)
        {
            for(JLabel label : timerLabels)
            {
                // gathers all the information from current time:
                String timeLeft = label.getText();
                if(!timeLeft.equals("Timer has expired!"))
                {
                    int daysLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf("d")));
                    timeLeft = timeLeft.substring(timeLeft.indexOf("d") + 2);
                    int hoursLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf("h")));
                    timeLeft = timeLeft.substring(timeLeft.indexOf("h") + 2);
                    int minutesLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf("m")));
                    timeLeft = timeLeft.substring(timeLeft.indexOf("m") + 2);
                    int secondsLeft = Integer.parseInt(timeLeft.substring(0, timeLeft.indexOf("s")));

                    int totalSecondsLeft = (daysLeft * 86400) + (hoursLeft * 3600) + (minutesLeft * 60) + secondsLeft;
                    // updates the JLabel and decreases time left by one sec:
                    if(totalSecondsLeft != 0)
                    {
                         totalSecondsLeft -= 1;
                    }

                    if(totalSecondsLeft == 0)
                    {
                        DesktopNotifications.playSound();
                        ToDoListItem.findTextForNotification(label); //makes a notification
                    }

                    daysLeft = totalSecondsLeft / 86400;
                    totalSecondsLeft = totalSecondsLeft - (daysLeft * 86400);
                    hoursLeft = totalSecondsLeft / 3600;
                    totalSecondsLeft = totalSecondsLeft - (hoursLeft * 3600);
                    minutesLeft = totalSecondsLeft / 60;
                    secondsLeft = totalSecondsLeft - (minutesLeft * 60);

                    if(totalSecondsLeft <= 0)
                    {
                        label.setText("Timer has expired!");
                    }
                    else
                    {
                        label.setText(daysLeft + "d " + hoursLeft + "h " + minutesLeft + "m " + secondsLeft + "s");
                    }

                    }
                }
            }
        }


    public static String[] sendLength(String dateStart)
    {
        ImageIcon img = new ImageIcon("res/calendar_icon.png");

        String[] months = new String[] {"01", "02", "03", "04",
                "05", "06", "07", "08",
                "09", "10", "11", "12"};
        String month = (String)JOptionPane.showInputDialog(
                null,                   //could specify location in     frame
                "Select the month",   //the question
                "Personal Utilities",               //the frame title
                JOptionPane.PLAIN_MESSAGE,      //the icon type 
                img,                       /**THIS IS THE IMAGE ICON*/
                months,                    //array of options
                months[0]);                //the default to be selected

        String[] days = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String day = (String)JOptionPane.showInputDialog(
                null,                   //could specify location in     frame
                "Select the day",   //the question
                "Personal Utilities",               //the frame title
                JOptionPane.PLAIN_MESSAGE,      //the icon type 
                img,                       /**THIS IS THE IMAGE ICON*/
                days,                    //array of options
                days[0]);                //the default to be selected

        String[] years = new String[] {"2020", "2021", "2022", "2023", "2024", "2025",
                "2026", "2027", "2028", "2029", "2030"};
        String year = (String)JOptionPane.showInputDialog(
                null,                   //could specify location in     frame
                "Select the year",   //the question
                "Personal Utilities",               //the frame title
                JOptionPane.PLAIN_MESSAGE,      //the icon type 
                img,                       /**THIS IS THE IMAGE ICON*/
                years,                    //array of options
                years[1]);                //the default to be selected

        String[] hours = new String[] {"00:", "01:", "02:", "03:", "04:", "05:",
                "06:", "07:", "08:", "09:", "10:", "11:",
                "12:", "13:", "14:", "15:", "16:", "17:",
                "18:", "19:", "20:", "21:", "22:", "23:"};

        String hour = (String)JOptionPane.showInputDialog(
                null,                   //could specify location in     frame
                "Select the hour (military)",   //the question
                "Personal Utilities",               //the frame title
                JOptionPane.PLAIN_MESSAGE,      //the icon type 
                img,                       /**THIS IS THE IMAGE ICON*/
                hours,                    //array of options
                hours[0]);                //the default to be selected

        String[] minutes = new String[] {":00", ":05", ":10", ":15", ":20", ":25", ":30",
                ":35", ":40", ":45", ":50", ":55"};
        String minute = (String)JOptionPane.showInputDialog(
                null,                   //could specify location in     frame
                "Select the minute",   //the question
                "Personal Utilities",               //the frame title
                JOptionPane.PLAIN_MESSAGE,      //the icon type 
                img,                       /**THIS IS THE IMAGE ICON*/
                minutes,                    //array of options
                minutes[0]);                //the default to be selected

        String dateStop = month + "/" + day + "/" + year + " " + hour.replace(":", "") + minute + ":00";   //01/14/2012 09:29:58 
        String[] timeLeftAndDate = {DateDifference.timeDifference(dateStop), dateStop};

        return timeLeftAndDate;
    }

    public static void setLength(JLabel label, String newText)
    {
        label.setText(newText);
    }

    public static String getTheText(JLabel label)
    {
        return label.getText();
    }

    public static void findTextForNotification(JLabel label)
    {
        JPanel panel = (JPanel) label.getParent().getParent().getComponent(0);

        for(Component comp : panel.getComponents())
        {
            if(comp instanceof JLabel)
            {
                JLabel theLabel = (JLabel) comp;
                DesktopNotifications.createNotification(theLabel.getText(), 5000); //creates a 5sec notification
            }
        }
    }
}
