package socialengine.components;

import socialengine.events.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * Defines the environment where person objects live and event occur.
 */
public class Environment extends JComponent
{
    /**
     * Creates a new environment.
     */
    public Environment()
    {
        width = 800;
        height = 600;
        x = 10;
        y = 10;
        people = new ArrayList<Person>();
        Person.hideFieldOfVision();
        events = new ArrayList<socialengine.events.Event>();
        running = true;
        autoEvents = false;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // We use the paintComponent method for the game loop.
        // After drawing the events and people, call updateEnvironment().
        // updateEnvironment calls repaint() when it's finished, which in turn
        // calls paintComponent, and we do it all again.
        drawEvents(g);
        drawPeople(g);
        updateEnvironment();
    }

    /**
     * Draws all of the person objects in the people array to the screen.
     * @param g the graphics object to use
     */
    private void drawPeople(Graphics g)
    {
        for (Person p : people)
            p.paintComponent(g);
    }

    /**
     * Draws all of the events in the environment.
     * @param g the graphics context
     */
    private void drawEvents(Graphics g)
    {
        for (socialengine.events.Event e : events)
            e.paintComponent(g);
    }

    /**
     * Adds a person to the environment's people array.
     * @param p the person to add
     */
    public void addPerson(Person p)
    {
        people.add(p);
        repaint();
    }

    /**
     * Adds an event to the environment's event array.
     * @param e the event to add
     */
    public void addEvent(socialengine.events.Event e)
    {
        events.add(e);
        repaint();
    }

    @Override
    public void setLocation(int x, int y)
    {
        Environment.x = x;
        Environment.y = y;
    }
    
    @Override
    public int getWidth() { return width; }

    public void setWidth(int newValue) { width = newValue; }

    @Override
    public int getHeight() { return height; }

    /**
     * Sets the height of the environment component.
     * @param newValue the height of the environment
     */
    public void setHeight(int newValue) { height = newValue; }

    /**
     * Gets the maximum x-coordinate that a child component can have before it
     * will no longer be rendered in the environment.
     * @return the maximum x-coordinate for the environment
     */
    public static int getMaxX() { return x + width - 20; }

    /**
     * Gets the minimum x-coordinate that a child component can have before it
     * will no longer be rendered in the environment.
     * @return the minimum x-coordinate for the environment
     */
    public static int getMinX() { return x; }

    /**
     * Gets the maximum y-coordinate that a child component can have before it
     * will no longer be rendered in the environment.
     * @return the maximum y-coordinate for the environment
     */
    public static int getMaxY() { return y + height - 20; }

    /**
     * Gets the minimum y-coordinate that a child component can have before it
     * will no longer be rendered in the environment.
     * @return the minimum y-coordinate for the environment
     */
    public static int getMinY() { return y; }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    /**
     * Creates random events at random intervals.
     */
    private void generateEvents()
    {
        // create events
        if (Math.random() * 500 > 499.9)
        {
            socialengine.events.Event soundEvent = new SoundEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

            addEvent(soundEvent);
        }

        if (Math.random() * 500 > 499.9)
        {
            socialengine.events.Event scaryEvent = new ScaryEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

            addEvent(scaryEvent);
        }

        if (Math.random() * 500 > 499.9)
        {
            socialengine.events.Event lightEvent = new LightEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

            addEvent(lightEvent);
        }
    }

    /**
     * Updates the environment. Creates new events. Tells events and people to
     * update.
     */
    private void updateEnvironment()
    {
        if (running)
        {
            if (autoEvents)
                generateEvents();

            // update events
            for (socialengine.events.Event e : events)
            {
                for (Person p : people)
                {
                    if (e.isPerceived(p))
                        e.setResponse(p);
                }
            }

            for (int i = 0; i < events.size(); i++)
            {
                if (events.get(i).isExpired())
                    events.remove(i);
            }
            events.trimToSize();

            // update people
            for (Person p : people)
            {
                try {
                    p.update();
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // repaint the environment and keep the game loop running
        repaint();
    }

    /**
     * Removes all people and events from the environment.
     */
    public void clearEnvironment()
    {
        people.clear();
        events.clear();
        Person.resetPersonCount();
        repaint();
    }

    /**
     * Gets the person objects in the environment.
     * @return the people in the environment
     */
    public ArrayList<Person> getPeople() { return people; }

    /**
     * Gets the events in the environment.
     * @return the events in the environment
     */
    public ArrayList<socialengine.events.Event> getEvents() { return events; }

    /**
     * Pauses the environment.
     */
    public void pause() { running = false; }

    /**
     * Un pauses the environment.
     */
    public void unPause() { running = true; }

    /**
     * Determines if the environment is paused.
     * @return true if paused, false if not paused
     */
    public boolean isPaused() { return running == false; }

    /**
     * Determines if the environment is generating events automatically.
     * @return true if environment is generating events automatically, false
     * if it is not
     */
    public boolean isAutomaticEvents() { return autoEvents; }

    /**
     * Turns off the environment's automatic event creation.
     */
    public void stopAutoEvents() { autoEvents = false; }

    /**
     * Turns on the environment's automatic event creation.
     */
    public void startAutoEvents() { autoEvents = true; }

    /**
     * Adds a random person to the environment.
     */
    public void addRandomPerson()
    {
        addPerson(Person.getRandomPerson());
    }

    /**
     * Adds a random event of the specified type to the environment.
     * @param eventTypeName the name of the event type to add
     */
    public void addRandomEvent(String eventTypeName)
    {
        if (eventTypeName.equalsIgnoreCase("LightEvent"))
        {
            addEvent(socialengine.events.Event.getRandomLightEvent());
        }
        else if (eventTypeName.equalsIgnoreCase("SoundEvent"))
        {
            addEvent(socialengine.events.Event.getRandomSoundEvent());
        }
        else if (eventTypeName.equalsIgnoreCase("ScaryEvent"))
        {
            addEvent(socialengine.events.Event.getRandomScaryEvent());
        }
    }

    private ArrayList<Person> people;
    private ArrayList<socialengine.events.Event> events;
    private static int width;
    private static int height;
    private static int x;
    private static int y;
    private boolean running;
    private boolean autoEvents;
    public static final int MAX_PEOPLE = 200;
    public static final int MAX_EVENTS = 200;
}
