package socialengine.components;

import java.awt.*;
import javax.swing.*;

/**
 * The main JFrame. Displays the state of the environment and the menu bar.
 */
public class EnvironmentFrame extends JFrame
{
    public EnvironmentFrame(int width, int height)
    {
        // Set the frame settings
        getContentPane().setBackground(Color.white);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle("CPSC 6119 Final Project - Social Engine");
        this.setJMenuBar(new EnvironmentMenuBar(this));
        this.setLocationByPlatform(true);

        // Set up the environment
        e = new Environment();
        e.setHeight(height - HEIGHTOFFSET);
        e.setWidth(width - WIDTHOFFSET);
        e.setLocation(XOFFSET, YOFFSET);
        this.add(e);
    }

    /**
     * Gets the environment associated with this environment frame.
     * @return the environment
     */
    public Environment getEnvironment() { return e; }

    private static final int WIDTHOFFSET = 25;
    private static final int HEIGHTOFFSET = 70;
    private static final int XOFFSET = 8;
    private static final int YOFFSET = 8;
    private Environment e;    
}
