import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;

/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu extends Window implements ActionListener {
    public static JPanel tempPanel;
    public static String currentNoise = "OK Alert";
    public static JPanel mainPanel, leftPanel, rightPanel, topPanel, bottomPanel;
    public static JTextArea helpText;

    public static void toggleBar(boolean isOn, Window window) {

        if (isOn) {
            JButton toDoList = new JButton(new ImageIcon("res/to_do_list1.png"));
            toDoList.setBorderPainted(false);
            JButton calendar = new JButton(new ImageIcon("res/calendar.png"));
            calendar.setBorderPainted(false);
            JButton contacts = new JButton(new ImageIcon("res/contacts.png"));
            contacts.setBorderPainted(false);
            JButton emails = new JButton(new ImageIcon("res/emails.png"));
            emails.setBorderPainted(false);
            JButton passwords = new JButton(new ImageIcon("res/passwords.png"));
            passwords.setBorderPainted(false);
            passwords.setActionCommand("passwords");
            passwords.addActionListener(window);
            JButton custom1 = new JButton("Custom 1");
            JButton custom2 = new JButton("Custom 2");
            JPanel rightPanel = new JPanel();
            rightPanel.setVisible(true);

            // Define new buttons with different width on help of the ---
            // Set up the title for different panels
            rightPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

            // Set up the BoxLayout
            BoxLayout layout1 = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);

            rightPanel.setLayout(layout1);

            // Add the buttons into the panel with three different alignment options
            toDoList.setAlignmentX(Component.RIGHT_ALIGNMENT);
            calendar.setAlignmentX(Component.RIGHT_ALIGNMENT);
            contacts.setAlignmentX(Component.RIGHT_ALIGNMENT);
            emails.setAlignmentX(Component.RIGHT_ALIGNMENT);
            passwords.setAlignmentX(Component.RIGHT_ALIGNMENT);
            custom1.setAlignmentX(Component.RIGHT_ALIGNMENT);
            custom2.setAlignmentX(Component.RIGHT_ALIGNMENT);
            rightPanel.add(toDoList);
            rightPanel.add(calendar);
            rightPanel.add(contacts);
            rightPanel.add(emails);
            rightPanel.add(passwords);
            //rightPanel.add(custom1);
            //rightPanel.add(custom2);

            window.add(rightPanel, BorderLayout.EAST);
            // Set the window to be visible as the default to be false
            window.revalidate();
            window.pack();
            window.setVisible(true);
            storePanel(rightPanel);
        } else {
            window.remove(tempPanel);
        }
    }

    public static void storePanel(JPanel panel) {
        tempPanel = panel;
    }

    public static void createHelpPanel(Window window, String currentNoise) throws IOException {
        Window.helpWindowOpen = true;

        JFrame frame = new JFrame();
        frame.setTitle("Personal Utilities");

        mainPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();

        FileReader fileObject = new FileReader("res/data/settings_data.txt");
        Scanner scan = new Scanner(fileObject);
        String theSound = scan.nextLine();
        String storedColor = scan.nextLine();
        scan.close();
        theSound = theSound.substring(theSound.indexOf(":") + 2);
        updateNoise(theSound);
        storedColor = storedColor.substring(storedColor.indexOf(":") + 2);


        leftPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        rightPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel helpLabel = new JLabel("Help");
        helpLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        leftPanel.add(helpLabel);

        helpText = new JTextArea();
        String helpMessage = "\n   Personal Utilities is an application that will be used like a center console." +
                "\n\n   Currently, it holds features that tell me what I have to do and what's on my calendar,\n\n   but" +
                " eventually, it will have features were I can quickly jump to places in my \n\n   browser and manage " +
                "personal features.";
        helpText.setText(helpMessage);
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        helpText.setEditable(false);
        Color color = leftPanel.getBackground();
        helpText.setBackground(color);
        leftPanel.add(helpText);

        changeAllColors(window, storedColor);

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        rightPanel.add(settingsLabel);

        String[] noises = new String[]{"OK Alert", "Phone Buzz", "Small Tune"};
        JComboBox noiseSelection = new JComboBox(noises);
        noiseSelection.setSelectedItem(currentNoise);
        noiseSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Get the source of the component, which is our combo
                // box.
                JComboBox comboBox = (JComboBox) event.getSource();

                // Print the selected items and the action command.
                String selected = (String) comboBox.getSelectedItem();
                updateNoise(selected);
                try {
                    updateSettingsData(1, selected);
                } catch (IOException io) {

                }

            }
        });
        JLabel noiseLabel = new JLabel("Select Notification Sound:");
        rightPanel.add(noiseLabel);
        rightPanel.add(noiseSelection);
        JLabel colorLabel = new JLabel("Select A Color:");
        rightPanel.add(colorLabel);


        // add color stuff:
        JButton colorWheelButton = new JButton(new ImageIcon("res/color_wheel_icon.png"));
        colorWheelButton.setBorderPainted(false);
        rightPanel.add(colorWheelButton);

        colorWheelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JColorChooser chooser = new JColorChooser();
                chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
                chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
                chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
                chooser.removeChooserPanel(chooser.getChooserPanels()[1]);
                rightPanel.add(chooser);
                rightPanel.revalidate();

                chooser.getSelectionModel().addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent arg0) {
                        Color color = chooser.getColor();

                        rightPanel.remove(chooser);
                        rightPanel.revalidate();

                        //Updating storing:
                        String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());  //0000FF
                        try {
                            updateSettingsData(2, hex);
                        } catch (IOException io) {

                        }
                        changeAllColors(window, hex);
                    }
                });

            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
                Window.helpWindowOpen = false;

            }
        });
        bottomPanel.add(closeButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.setPreferredSize(new Dimension(1125, 750));
        frame.pack();
        frame.setVisible(true);

    }


    public static void updateNoise(String newNoise) {
        currentNoise = newNoise;
    }

    // ID 1 = noise, ID 2 = color
    public static void updateSettingsData(int ID, String newText) throws IOException {
        FileReader fileObject = new FileReader("res/data/settings_data.txt");
        Scanner scan = new Scanner(fileObject);
        String[] allData = new String[2];
        int index = 0;

        while (scan.hasNext()) {
            allData[index] = scan.nextLine();
            index++;
        }
        scan.close();

        if (ID == 1) {
            allData[0] = "Notification Sound: " + newText;
        } else {
            allData[1] = "Background Color: " + newText;
        }

        PrintWriter writer = new PrintWriter("res/data/settings_data.txt");
        for (String s : allData) {
            writer.println(s);
        }
        writer.close();
    }

    public static void changeAllColors(Window window, String storedColor) {
        leftPanel.setBackground(Color.decode(storedColor));
        rightPanel.setBackground(Color.decode(storedColor));
        topPanel.setBackground(Color.decode(storedColor));
        bottomPanel.setBackground(Color.decode(storedColor));
        mainPanel.setBackground(Color.decode(storedColor));
        helpText.setBackground(Color.decode(storedColor));
        window.rightPanel.setBackground(Color.decode(storedColor));
        window.leftPanel.setBackground(Color.decode(storedColor));
        window.centerPanel.setBackground(Color.decode(storedColor));
        window.topPanel.setBackground(Color.decode(storedColor));
        window.bottomPanel.setBackground(Color.decode(storedColor));
        ToDoListMenu.labelPanel.setBackground(Color.decode(storedColor));
        window.revalidate();
    }


}
