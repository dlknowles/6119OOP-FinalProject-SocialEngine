package socialengine.actionListeners;

import java.awt.event.*;
import socialengine.components.*;

/**
 * Displays the about dialog modal frame.
 */
public class AboutActionListener implements ActionListener
{
    /**
     * Creates a new action listener that displays the about dialog frame.
     * @param parentFrame the frame that calls the action
     */
    public AboutActionListener(EnvironmentFrame parentFrame)
    {
        this.parentFrame = parentFrame;
    }

    public void actionPerformed(ActionEvent event)
    {
        parentFrame.getEnvironment().pause();
        AboutDialog ad = new AboutDialog(parentFrame);
        ad.setVisible(true);
    }

    EnvironmentFrame parentFrame;
}