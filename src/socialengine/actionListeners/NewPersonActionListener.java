package socialengine.actionListeners;

import socialengine.components.EnvironmentFrame;
import socialengine.components.NewPersonDialog;
import java.awt.event.*;

/**
 * Displays a modal dialog option frame that creates new person objects.
 */
public class NewPersonActionListener implements ActionListener
{
    /**
     * Creates a new action listener for the new person action.
     * @param parentFrame the environment frame calling this action
     */
    public NewPersonActionListener(EnvironmentFrame parentFrame)
    {
        this.parentFrame = parentFrame;
    }

    public void actionPerformed(ActionEvent event)
    {
        parentFrame.getEnvironment().pause();
        NewPersonDialog newPDialog = new NewPersonDialog(parentFrame);

        newPDialog.setVisible(true);
    }

    private EnvironmentFrame parentFrame;
}