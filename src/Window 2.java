import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * Write a description of class Window here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Window extends JFrame implements ActionListener
{
    JPanel bottomPanel, rightPanel, topPanel, leftPanel, centerPanel, toDoPanel, toDoPanel2, scrollPane, scrollPane2;
    JButton menuButton, helpButton;
    JScrollPane toDoPane, calendarScrollPane;
    Component[] panelComps;
    public ArrayList<JPanel> toDoItems;

    boolean isMenuBarEnabled = false;
    boolean isEditEnabled = false;
    public static boolean helpWindowOpen = false;
    final String FONT = "Times";
    public static int panelID = 0;

    public Window()
    {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        this.setTitle("Personal Utilities");

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel = new JPanel();
        topPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();
        toDoPanel = new JPanel();

        bottomPanel.setName("BOTTOM");
        rightPanel.setName("RIGHT");
        topPanel.setName("TOP");
        leftPanel.setName("LEFT");
        centerPanel.setName("CENTER");
        this.setName("FRAME");

        toDoItems = new ArrayList<JPanel>();

        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        //Buttons:
        menuButton = new JButton(new ImageIcon("res/menu_icon.png"));
        menuButton.setBorderPainted(false);
        menuButton.addActionListener(this);
        menuButton.setActionCommand("menu");
        helpButton = new JButton(new ImageIcon("res/help_icon.png"));
        helpButton.setBorderPainted(false);
        helpButton.addActionListener(this);
        helpButton.setActionCommand("help");
        bottomPanel.add(helpButton);

        // Center Panel
        ToDoListMenu toDoListMenu = new ToDoListMenu();
        toDoListMenu.createMenu(centerPanel, this);
        centerPanel.add(toDoPanel, BorderLayout.CENTER);

        toDoPanel2 = new JPanel();
        toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.Y_AXIS));
        toDoPanel2.setLayout(new BoxLayout(toDoPanel2, BoxLayout.Y_AXIS));
        toDoPane = new JScrollPane(toDoPanel2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        toDoPanel.add(toDoPane);

        // Left Panel
        JLabel eventsLabel = new JLabel ("Upcoming Events");
        eventsLabel.setFont(new Font(FONT, Font.PLAIN, 35));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        leftPanel.add(eventsLabel);

        scrollPane2 = new JPanel();
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
        scrollPane2.setLayout(new BoxLayout(scrollPane2, BoxLayout.Y_AXIS));
        calendarScrollPane = new JScrollPane(scrollPane2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.add(calendarScrollPane);
        leftPanel.add(scrollPane, BorderLayout.CENTER);


        //Top Panel
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(menuButton);


        // adding stored items:
        try
        {
            //set the background:
            getAndSetBackground();

            ArrayList<JPanel> filePanels = ToDoListStoring.readFileCreateItems(this);
            for(JPanel panel : filePanels)
            {
                panel.setName(panelID + "");
                panelID += 1;
                toDoItems.add(panel);
                toDoPanel2.add(panel);
            }

            //the  .exec method needs an array with: 1. interpreter name, 2. file name
            String[] fileInfo = { "python", "/Users/tuckerbarach/IdeaProjects/Personal\\ Utilities/src/store_items_from_calendar.py"};
            // create runtime Object to execute an external command
            Runtime myRunTime = Runtime.getRuntime();
            //execute external command: run Test.py from python, using the fileInfo above
            Process myProcess = myRunTime.exec(fileInfo);

            BufferedReader reader = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));

            //YOUR JAVA CODE CAN USE THE OUTPUT (PRINTED STATEMENTS) OF THE PYTHON PROGRAM HOWEVER YOU WANT
            //   HERE, I WILL JUST DISPLAY IT:
            String line = "";
            while(line != null)
            {
                line = reader.readLine();
                // display each output line form python script
                if(line != null) System.out.println(line);
            }

            ArrayList<JPanel> calendarPanels = UpcomingEvents.getUpcomingEvents();

            for(JPanel panel : calendarPanels)
            {
                scrollPane2.add(panel);
            }
        }
        catch(Exception io)
        {
            System.out.println(io.getStackTrace());
        }

        this.setPreferredSize(new Dimension(1125, 750)); 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        //updating countdown timer for todo list items:
        Timer toDoTimer = new Timer(1000, this);
        toDoTimer.setActionCommand("updateCountdowns");
        toDoTimer.start();

        //TimeSelection.createPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("menu"))
        {
            isMenuBarEnabled = !isMenuBarEnabled;
            if(isMenuBarEnabled)
            {
                rightPanel.setVisible(false);
                Menu.toggleBar(true, this);
            }

            else
            {
                rightPanel.setVisible(true);
                Menu.toggleBar(false, this);
            }
        }

        else if(e.getActionCommand().equals("help"))
        {
            if(!helpWindowOpen)
            {
                try
                {
                    Menu.createHelpPanel(this, Menu.currentNoise);
                }
                catch(IOException io)
                {

                }
            }
        }

        else if(e.getActionCommand().equals("plus"))
        {
            JPanel newPanel = ToDoListItem.createItem(this); //creates JPanel with proper formats
            toDoItems.add(newPanel);

            //for(JPanel p : toDoItems)
            //{
            //p.setBackground(Color.blue);
            //toDoPanel2.add(p);
            //}
            newPanel.setName(panelID + "");
            panelID += 1;
            toDoPanel2.add(newPanel);
            this.pack();
        }

        else if(e.getActionCommand().equals("remove"))
        {
            JPanel panelToRemove = (JPanel)((JButton)e.getSource()).getParent().getParent(); //gets all JPanels in toDoPanel
            String removePanelID = panelToRemove.getName();
            //toDoPanel2.remove(panelToRemove);
            panelToRemove.setVisible(false);

            int tempPanelID = 0;
            try
            {
                ToDoListStoring.removeItem(removePanelID);
            }
            catch(IOException io)
            {

            }

            for(JPanel panel : toDoItems)
            {
                if(panel.getName().equals(removePanelID))
                {
                    toDoItems.remove(panel);
                    break;
                }
            }
            // update JPanel IDs
            for(JPanel panel : toDoItems)
            {
                panel.setName(tempPanelID + "");
                tempPanelID++;
            }   
        }

        else if(e.getActionCommand().equals("edit_title_and_description"))
        {
            String currentText = "";
            String newText = "";
            Component[] editComps = ((JButton)e.getSource()).getParent().getComponents();
            for(Component comp : editComps)
            {   
                if(comp instanceof JLabel)
                {
                    currentText = ToDoListItem.getTheText((JLabel)comp);
                    newText = JOptionPane.showInputDialog("Enter the new text:");
                    ToDoListItem.changeWords((JLabel)comp, newText);
                }
            }

            Component thisPanel = ((JButton)e.getSource()).getParent().getParent();
            try
            {
                int miniPanelIndex = Integer.parseInt(((JButton)e.getSource()).getParent().getName()); //gets the ID of which toDoItemPanel border
                ToDoListStoring.changeData(thisPanel.getName(), miniPanelIndex, currentText, newText);
            }
            catch(IOException io)
            {

            }
        }

        else if(e.getActionCommand().equals("edit"))
        {
            isEditEnabled = !isEditEnabled;
            for(JPanel panel : toDoItems)
            {
                JPanel titlePanel = (JPanel) panel.getComponent(3); //Center
                for(Component c : titlePanel.getComponents())
                {
                    if(c instanceof JButton)
                    {
                        c = (JButton) c;
                        c.setVisible(isEditEnabled);
                    }
                }

                JPanel descriptionPanel = (JPanel) panel.getComponent(0); //North
                for(Component c : descriptionPanel.getComponents())
                {
                    if(c instanceof JButton)
                    {
                        c = (JButton) c;
                        c.setVisible(isEditEnabled);
                    }
                }

                JPanel editPanel = (JPanel) panel.getComponent(1); //East
                for(Component c : editPanel.getComponents())
                {
                    if(c instanceof JButton)
                    {
                        c = (JButton) c;
                        if(c.getName().equals("remove"))
                        {
                            c.setVisible(isEditEnabled);
                        }
                    }
                }
            }
        }

        else if(e.getActionCommand().equals("updateCountdowns"))
        {
            ToDoListItem.updateAllTimers();
        }

        else if(e.getActionCommand().equals("timer_icon"))
        {
            Component[] timerComps = ((JButton)e.getSource()).getParent().getComponents();
            JPanel panel = (JPanel)((JButton)e.getSource()).getParent().getParent(); //gets all JPanels in toDoPanel
            String selectedID = panel.getName();
            for(Component comp : timerComps)
            {   
                if(comp instanceof JLabel)
                {
                    TimeSelection.createPanel((JLabel)comp, selectedID, false);
                    //String[] newLength = TimeSelection.returnArray;
                    
                    //ToDoListItem.setLength((JLabel) comp, newLength[0]);
                    //try
                    //{
                    //    ToDoListStoring.changeData(selectedID, 5, "", newLength[1]);
                    //}
                    //catch(IOException io)
                    //{

                    //}
                }
            }
        }

        else if(e.getActionCommand().equals("priority"))
        {
            JPanel panel = (JPanel)((JButton)e.getSource()).getParent().getParent(); //gets all JPanels in toDoPanel
            String selectedID = panel.getName();
            int newPriority = ToDoListItem.changePriority((JButton)e.getSource());

            try
            {
                ToDoListStoring.changeData(selectedID, 3, "", newPriority + "");
            }
            catch(IOException io)
            {

            }

        }

        else if(e.getActionCommand().equals("color"))
        {
            //chooser.getSelectionModel().addChangeListener(new ChangeListener());

            panelComps = ((JButton)e.getSource()).getParent().getParent().getComponents(); //gets all JPanels in toDoPanel
            JPanel panelToAdd = (JPanel)((JButton)e.getSource()).getParent().getParent().getComponent(1);

            JPanel panel = (JPanel)((JButton)e.getSource()).getParent().getParent(); //gets all JPanels in toDoPanel
            String selectedID = panel.getName();

            ToDoListItem.addColorChooser(panelComps, panelToAdd, (JButton)e.getSource(), panel, selectedID);
            this.revalidate();

        }

        else if(e.getActionCommand().equals("passwords"))
        {
            // play unlocking animation or something cool (if time)

        }
    }

    public void getAndSetBackground() throws IOException
    {
        FileReader fileObject = new FileReader("res/data/settings_data.txt");
        Scanner scan = new Scanner(fileObject);
        scan.nextLine();
        String storedColor = scan.nextLine();
        scan.close();
        storedColor = storedColor.substring(storedColor.indexOf(":") + 2);

        rightPanel.setBackground(Color.decode(storedColor));
        leftPanel.setBackground(Color.decode(storedColor));
        centerPanel.setBackground(Color.decode(storedColor));
        topPanel.setBackground(Color.decode(storedColor));
        bottomPanel.setBackground(Color.decode(storedColor));
        ToDoListMenu.labelPanel.setBackground(Color.decode(storedColor));
        this.revalidate();
    }
}
