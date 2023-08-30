import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * Write a description of class DesktopNotifications here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DesktopNotifications extends JFrame {
    public static JFrame frame;
    public static JPanel imagePanel, topPanel, titlePanel;

    public static void createNotification(String text, int milliseconds) {
        frame = new JFrame();
        frame.setUndecorated(true); // removes title bar
        frame.setPreferredSize(new Dimension(250, 75));
        frame.setLayout(new BorderLayout());

        imagePanel = new JPanel();
        frame.add(imagePanel, BorderLayout.WEST);
        ImageIcon icon = new ImageIcon("res/utilities_icon.png");
        JLabel thumbnail = new JLabel();
        thumbnail.setIcon(icon);
        imagePanel.add(thumbnail);

        topPanel = new JPanel();
        frame.add(topPanel, BorderLayout.NORTH);
        JLabel top = new JLabel("Personal Utilities");
        topPanel.add(top);

        titlePanel = new JPanel();
        frame.add(titlePanel, BorderLayout.CENTER);
        JLabel title = new JLabel(text);
        titlePanel.add(title);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - frame.getWidth();
        int y = 30;
        frame.setLocation(x - 250 - 5, y); // 250 = X dimension - top right display

        frame.pack();
        frame.setVisible(true);

        //https://stackoverflow.com/questions/2258066/java-run-a-function-after-a-specific-number-of-seconds:
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        frame.dispose();
                    }
                },
                milliseconds
        );
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("notification timer")) {
            System.out.println("test");
            frame.dispose();
        }
    }

    public static void playSound() {
        // plays sound effect through terminal:
        try
        {
            if(Menu.currentNoise.equals("OK Alert"))
            {
                Runtime.getRuntime().exec("afplay res/ok-notification-alert_C_major.wav");
            }

            else if(Menu.currentNoise.equals("Phone Buzz"))
            {
                Runtime.getRuntime().exec("afplay res/sms-alert-3-daniel_simon.wav");
            }

            else if(Menu.currentNoise.equals("Small Tune"))
            {
                Runtime.getRuntime().exec("afplay res/sms-alert-4-daniel_simon.wav");
            }

        } catch (IOException io)
        {
            System.out.println("error");
        }
    }

}
