package socialengine.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import socialengine.layouts.*;

/**
 * An option dialog frame that creates new events.
 */
public class NewEventDialog extends JDialog
{
    /**
     * Creates a new event dialog frame.
     */
    public NewEventDialog(final EnvironmentFrame parentFrame)
    {
        final ButtonGroup eventRadioGroup = new ButtonGroup();
        JPanel eventSelectionPanel = getEventSelectionPanel(eventRadioGroup);
        this.parentFrame = parentFrame;
        wasPaused = parentFrame.getEnvironment().isPaused();

        JLabel numEventsLabel = new JLabel("Number of events to add: ");
        final JTextField numEventsText = new JTextField(4);

        JLabel eventTypeLabel = new JLabel("Select the type of event: ");       

        JButton addButton = new JButton("Add Events");
        addButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        int numEvents = Integer.parseInt(numEventsText.getText());
                        String eventType =
                            eventRadioGroup.getSelection().getActionCommand();

                        // only allow the maximum number of people
                        if (parentFrame.getEnvironment().getEvents().size() +
                            numEvents > Environment.MAX_EVENTS)
                        {
                            String errorMessage =
                                "There is only room left for " +
                                Integer.toString(Environment.MAX_EVENTS -
                                parentFrame.getEnvironment().getEvents().size()) +
                                " more events.";

                            JOptionPane.showMessageDialog(parentFrame,
                                errorMessage,
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {

                            for (int i = 0; i < numEvents; ++i)
                            {
                                parentFrame.getEnvironment().addRandomEvent(eventType);
                            }

                            dispose();
                        }
                    }
                    catch (NumberFormatException ex)
                    {                        
                        JOptionPane.showMessageDialog(parentFrame,
                            "The number of events must\n" +
                            "be an integer value.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(parentFrame,
                            eventRadioGroup.getSelection().getActionCommand(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                dispose();
            }
        });

        setModal(true);
        setTitle("Add New Person Objects");
        setLocation(400, 300);
        getContentPane().setLayout(new FormLayout());

        getContentPane().add(numEventsLabel);
        getContentPane().add(numEventsText);

        getContentPane().add(eventTypeLabel);
        getContentPane().add(eventSelectionPanel);

        getContentPane().add(addButton);
        getContentPane().add(cancelButton);

        pack();
    }

     @Override
    public void dispose()
    {
        parentFrame.getEnvironment().unPause();

        super.dispose();
    }

    /**
     * Builds the panel that contains the event type selections.
     * @param radioGroup the radio button group of event type selections
     * @return the panel that contains the event type selections
     */
    private JPanel getEventSelectionPanel(ButtonGroup eventRadioGroup)
    {
        JRadioButton lightEventButton = new JRadioButton("Light Event");
        lightEventButton.setActionCommand("LightEvent");
        lightEventButton.setSelected(true);

        JRadioButton soundEventButton = new JRadioButton("Sound Event");
        soundEventButton.setActionCommand("SoundEvent");

        JRadioButton scaryEventButton = new JRadioButton("Scary Event");
        scaryEventButton.setActionCommand("ScaryEvent");

        eventRadioGroup.add(lightEventButton);
        eventRadioGroup.add(soundEventButton);
        eventRadioGroup.add(scaryEventButton);

        JPanel eventPanel = new JPanel(new GridLayout(0, 1));
        eventPanel.add(lightEventButton);
        eventPanel.add(soundEventButton);
        eventPanel.add(scaryEventButton);

        return eventPanel;
    }

    private EnvironmentFrame parentFrame;
    boolean wasPaused;
}
