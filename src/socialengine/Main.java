package socialengine;

import socialengine.components.EnvironmentFrame;

/**
 * The entry point for the application.
 */
public class Main
{
    /**
     * The entry point for the application.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EnvironmentFrame ef = new EnvironmentFrame(800, 600);
        ef.setVisible(true);
    }

}
