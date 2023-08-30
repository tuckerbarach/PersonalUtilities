import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Arrays;

/**
 * Write a description of class ToDoListStoring here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ToDoListStoring
{
    String ID;
    String title;
    String description;
    int priority;
    String color;
    String timeEnd;

    public static ArrayList<ToDoListStoring> toDoListItems = new ArrayList<ToDoListStoring>();
    public static ArrayList<JPanel> toDoListPanels = new ArrayList<JPanel>();

    public ToDoListStoring(String ID, String title, String description, int priority, String color, String timeEnd)
    {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.color = color;
        this.timeEnd = timeEnd;
    }

    public static ArrayList<JPanel> readFileCreateItems(Window window) throws IOException
    {
        FileReader fileObject = new FileReader("res/data/to_do_list_items.txt");
        Scanner scan = new Scanner(fileObject);
        int counter = 0;

        while(scan.hasNext())
        {
            counter += 1;
            scan.nextLine();
        }
        scan.close();
        counter = counter / 6; // 6 = amount of lines in 1 ToDoListItem

        Scanner scan2 = new Scanner(new FileReader("res/data/to_do_list_items.txt"));
        for(int i = 0; i < counter; i++)
        {
            String ID_ = scan2.nextLine();
            ID_ = ID_.substring(ID_.indexOf(":") + 2); 
            String title_ = scan2.nextLine();
            title_ = title_.substring(title_.indexOf(":") + 2);
            String description_ = scan2.nextLine();
            description_ = description_.substring(description_.indexOf(":") + 2);
            String priority__ = scan2.nextLine();
            int priority_ = Integer.parseInt(priority__.substring(priority__.indexOf(":") + 2));
            String color_ = scan2.nextLine();
            color_ = color_.substring(color_.indexOf(":") + 2);
            String timeEnd_ = scan2.nextLine();
            timeEnd_ = timeEnd_.substring(timeEnd_.indexOf(":") + 2);

            toDoListItems.add(new ToDoListStoring(ID_, title_, description_, priority_, color_, timeEnd_));
        }

        scan2.close();
        for(ToDoListStoring item : toDoListItems)
        {
            toDoListPanels.add(ToDoListItem.createItemFromFile(item, window));
        }

        return toDoListPanels;
    }

    public static void storeItem(ToDoListStoring newItem) throws IOException
    {
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("res/data/to_do_list_items.txt"), true));

        writer.println("ID: " + newItem.ID);
        writer.println("Title: " + newItem.title);
        writer.println("Description: " + newItem.description);
        writer.println("Priority: " + newItem.priority);
        writer.println("Color: " + newItem.color);
        writer.print("Time End: " + newItem.timeEnd);
        //writer.println("\n");

        writer.close();
    }

    public static ArrayList<String> readData() throws IOException
    {
        FileReader fileObject = new FileReader("res/data/to_do_list_items.txt");
        Scanner scan = new Scanner(fileObject);
        ArrayList<String> allData = new ArrayList<String>();

        while(scan.hasNext())
        {
            allData.add(scan.nextLine());
        }
        scan.close();
        return allData;
    }

    //whichChanged - 1 = titlePanel, 2 = descriptionPanel, 3 = priorityPanel, 4 = colorPanel, 5 = timerPanel
    public static void changeData(String ID, int whichChanged, String oldText, String newText) throws IOException
    {

        String returnString = "";
        ArrayList<String> allData = readData();
        int lineNumber = allData.indexOf("ID: " + ID) + whichChanged;
        //Go to array to make a direct replacement:
        //https://www.geeksforgeeks.org/convert-an-arraylist-of-string-to-a-string-array-in-java/:
        Object[] objArr = allData.toArray(); 
        String[] arrayData = Arrays.copyOf(objArr, objArr.length, String[].class);
        if(!oldText.equals(""))
        {
            arrayData[lineNumber] = arrayData[lineNumber].replace(oldText, newText);
        }
        else
        {
            arrayData[lineNumber] = arrayData[lineNumber].substring(0, arrayData[lineNumber].indexOf(":") + 1);
            arrayData[lineNumber] = arrayData[lineNumber] + " " + newText;
        }

        PrintWriter writer = new PrintWriter("res/data/to_do_list_items.txt");
        for(String s : arrayData)
        {
            writer.println(s);
        }
        writer.close();
        writer.flush();
    }

    public static void updateIDs() throws IOException
    {
        ArrayList<String> allData = readData();
        String returnString = "";
        int idCounter = 0;
        for(int i = 0; i < allData.size(); i++)
        {
            if(allData.get(i).contains("ID: "))
            {
                allData.set(i, "ID: " + idCounter);
                idCounter++;
            }
        }

        PrintWriter writer = new PrintWriter("res/data/to_do_list_items.txt");
        for(String s : allData)
        {
            writer.println(s);
        }   
        writer.close();
    }

    public static void removeItem(String ID) throws IOException
    {
        ArrayList<String> allData = readData();
        int startRemoveLine = allData.indexOf("ID: " + ID);
        int endRemoveLine = startRemoveLine + 5; // 5 = amount of data in a TODOLIST item
        String returnString = "";

        for(int i = endRemoveLine; i >= startRemoveLine; i--)
        {
            allData.remove(i);
        }

        PrintWriter writer = new PrintWriter("res/data/to_do_list_items.txt");
        for(String s : allData)
        {
            writer.println(s);
        }
        writer.close();

        updateIDs();
    }
}
