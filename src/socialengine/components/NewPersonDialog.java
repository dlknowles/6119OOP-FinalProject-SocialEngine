package socialengine.components;

import socialengine.layouts.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays a modal dialog option frame for adding Person objects.
 */
public class NewPersonDialog extends JDialog
{
    /**
     * Creates a new person dialog frame.
     * @param parentFrame the parent frame of the new person dialog frame
     */
    public NewPersonDialog(final EnvironmentFrame parentFrame)
    {
        this.parentFrame = parentFrame;
        wasPaused = parentFrame.getEnvironment().isPaused();
        
        JLabel numPeopleLabel = new JLabel("Number of people to add: ");
        final JTextField numPeopleText = new JTextField(4);
        
        JButton addPersonButton = new JButton("Add People");
        addPersonButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        int numPeople = Integer.parseInt(numPeopleText.getText());

                        // only allow the maximum number of people
                        if (parentFrame.getEnvironment().getPeople().size() + numPeople > Environment.MAX_PEOPLE)
                        {
                            String errorMessage =
                                "There is only room left for " +
                                Integer.toString(Environment.MAX_PEOPLE - parentFrame.getEnvironment().getPeople().size()) +
                                " more people.";

                            JOptionPane.showMessageDialog(parentFrame,
                                errorMessage,
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            for (int i = 0; i < numPeople; ++i)
                            {
                                parentFrame.getEnvironment().addRandomPerson();
                            }

                            dispose();
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(parentFrame,
                            "The number of people must\n" +
                            "be an integer value.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e) { dispose(); }
            });
        
        setModal(true);
        setTitle("Add New Person Objects");
        setLocation(400, 300);
        setLayout(new FormLayout());
        
        add(numPeopleLabel);
        add(numPeopleText);

        add(addPersonButton);
        add(cancelButton);
        pack();
    }


    @Override
    public void dispose()
    {
        parentFrame.getEnvironment().unPause();

        super.dispose();
    }

    EnvironmentFrame parentFrame;
    boolean wasPaused;
}
