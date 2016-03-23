import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Created by SONY on 2/21/2016.
 */
public class ApplicationRunner {
    public static void main(String[]args)throws Exception
    {
        javax.swing.SwingUtilities.invokeAndWait(Application::new);
    }
}
