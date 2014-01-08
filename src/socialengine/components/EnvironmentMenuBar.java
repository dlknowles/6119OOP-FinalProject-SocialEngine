package socialengine.components;

import java.awt.event.*;
import javax.swing.*;
import socialengine.actionListeners.*;
import socialengine.events.*;

/**
 *
 */
public class EnvironmentMenuBar extends JMenuBar
{
    public EnvironmentMenuBar(EnvironmentFrame parentFrame)
    {
        super();
        this.parentFrame = parentFrame;

        add(getFileMenu());
        add(getEnvironmentMenu());
        add(getOptionsMenu());
        add(getHelpMenu2());
    }

     /**
     * Gets the file menu for the menu bar.
     * @return the file menu
     */
    private JMenu getFileMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem pauseItem = new JMenuItem("Pause");

        pauseItem.addActionListener(new PauseActionListener(parentFrame));

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new
            ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    System.exit(0);
                }
            });

        fileMenu.add(pauseItem);
        fileMenu.add(new javax.swing.JSeparator(SwingConstants.HORIZONTAL));
        fileMenu.add(exitItem);

        return fileMenu;
    }

    /**
     * Builds the environment menu.
     * @return the environment menu
     */
    private JMenu getEnvironmentMenu()
    {
        JMenu envMenu = new JMenu("Environment");
        JMenuItem addEventsItem = new JMenuItem("Add Events");
        JMenuItem newEventItem = new JMenuItem("Add Random Event");

        JMenuItem clearEnvironmentItem = new JMenuItem("Clear Environment");
        JMenuItem addPeopleItem = new JMenuItem("Add People");
        JMenuItem newPersonItem = new JMenuItem("Add Random Person");

        newEventItem.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.getEnvironment().addEvent(Event.getRandomEvent());
            }
        });

        addPeopleItem.addActionListener(new NewPersonActionListener(parentFrame));
        newPersonItem.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.getEnvironment().addPerson(Person.getRandomPerson());
            }
        });

        addEventsItem.addActionListener(new NewEventActionListener(parentFrame));

        clearEnvironmentItem.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.getEnvironment().clearEnvironment();
            }
        });

        envMenu.add(newPersonItem);
        envMenu.add(addPeopleItem);
        envMenu.add(new javax.swing.JSeparator(SwingConstants.HORIZONTAL));
        envMenu.add(newEventItem);
        envMenu.add(addEventsItem);
        envMenu.add(new javax.swing.JSeparator(SwingConstants.HORIZONTAL));
        envMenu.add(clearEnvironmentItem);

        return envMenu;
    }

    private JMenu getOptionsMenu()
    {
        JMenu optionsMenu = new JMenu("Options");
        JCheckBoxMenuItem autoEventsItem = new JCheckBoxMenuItem("Automatic Event Creation");
        JCheckBoxMenuItem displayFOVItem = new JCheckBoxMenuItem("Show Person Fields of Vision");
        JCheckBoxMenuItem displayAttributesItem = new JCheckBoxMenuItem("Show Person Attributes");

        autoEventsItem.addItemListener(
            new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                AbstractButton button = (AbstractButton) e.getItem();
                if (button.isSelected())
                {
                    parentFrame.getEnvironment().startAutoEvents();
                }
                else
                {
                    parentFrame.getEnvironment().stopAutoEvents();
                }
            }
        });

        displayFOVItem.addItemListener(
            new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                AbstractButton button = (AbstractButton) e.getItem();
                if (button.isSelected())
                {
                    Person.displayFieldOfVision();
                }
                else
                {
                    Person.hideFieldOfVision();
                }
            }
        });

        displayAttributesItem.addItemListener(
            new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                AbstractButton button = (AbstractButton) e.getItem();
                if (button.isSelected())
                {
                    Person.displayAttributes();
                }
                else
                {
                    Person.hideAttributes();
                }
            }
        });

        optionsMenu.add(autoEventsItem);
        optionsMenu.add(displayFOVItem);
        optionsMenu.add(displayAttributesItem);

        return optionsMenu;
    }
   
    private JMenu getHelpMenu2()
    {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new AboutActionListener(parentFrame));

        helpMenu.add(aboutItem);

        return helpMenu;
    }

    EnvironmentFrame parentFrame;
}
