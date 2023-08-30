import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * Write a description of class UpcomingEvents here.
 *
 * @author Tucker Barach
 * @version 0.0.1
 */
public class ToDoListMenu extends JFrame
{
    final String FONT = "Times";
    public static JPanel labelPanel;

    /**
     * Creates a JPanel with proper formats including a header and a buttons panel
     * @param panel JPanel which is being modified
     * @param window Window which is being modified
     */
    
    public void createMenu(JPanel panel, Window window)
    {
        //Setting up JPanel:
        panel.setLayout(new BorderLayout());
        JPanel toDoPanel = new JPanel();

        //Creating Label Panel on Top:
        labelPanel = new JPanel();
        panel.add(labelPanel, BorderLayout.NORTH);
        labelPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        JLabel toDoLabel = new JLabel ("To Do List");
        toDoLabel.setFont(new Font(FONT, Font.PLAIN, 35));
        labelPanel.add(toDoLabel);

        //Creating Buttons Panel on Bottom:
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JButton plusButton = new JButton(new ImageIcon("res/plus_icon.png"));
        plusButton.setBorderPainted(false);
        plusButton.setActionCommand("plus");
        plusButton.addActionListener(window);
        buttonsPanel.add(plusButton);

        JButton editButton = new JButton(new ImageIcon("res/edit_icon.png"));
        editButton.setBorderPainted(false);
        editButton.setActionCommand("edit");
        editButton.addActionListener(window);
        buttonsPanel.add(editButton);

    }
}
