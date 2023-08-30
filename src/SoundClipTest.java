import java.io.*;

public class SoundClipTest {

    public static void main(String args[]) throws IOException
    {
        try
        {
            Runtime.getRuntime().exec("afplay res/ok-notification-alert_C_major.wav");

        } catch (Exception e)
        {
            System.out.println("error");
        }
    }
}
