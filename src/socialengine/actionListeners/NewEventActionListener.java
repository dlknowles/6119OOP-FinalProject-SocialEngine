package socialengine.actionListeners;

import java.awt.event.*;
import socialengine.components.*;

/**
 * Displays a modal dialog option frame that creates new event objects.
 */
public class NewEventActionListener implements ActionListener
{
    /**
     * Creates a new action listener for the new person action.
     * @param parentFrame the environment frame calling this action
     */
    public NewEventActionListener(EnvironmentFrame parentFrame)
    {
        this.parentFrame = parentFrame;
    }

    public void actionPerformed(ActionEvent e)
    {
        parentFrame.getEnvironment().pause();
        NewEventDialog newEDialog = new NewEventDialog(parentFrame);
        newEDialog.setVisible(true);
    }

    private EnvironmentFrame parentFrame;
}
