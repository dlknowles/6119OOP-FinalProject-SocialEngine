package socialengine.events;

import socialengine.components.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * Defines events that take place within the environment.
 */
public abstract class Event extends JComponent
{
    public Event(Point2D location)
    {
        this.location = location;
        this.width = (int) (Math.round(Math.random() * MAX_SIZE));
        this.height = (int) (Math.round(Math.random() * MAX_SIZE));
        this.duration = (int) (Math.round(Math.random() * MAX_DURATION));
        startTime = Calendar.getInstance();
        observedBy = new ArrayList<Integer>();
    }
    
    public Point2D getCurrentLocation()
    {
        return location;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        Ellipse2D r = new Ellipse2D.Double(
            location.getX(), location.getY(), width, height);

        g2.draw(r);
    }

    public boolean isExpired()
    {
        Calendar nowEnd = Calendar.getInstance();
        nowEnd.add(Calendar.SECOND, -duration);

        if (startTime.compareTo(nowEnd) <= 0)
        {
            return true;
        }
        
        return false;
    }

    public Point2D getCenter()
    {
        double centerX = location.getX() + (double) (width / 2);
        double centerY = location.getY() + (double) (height / 2);

        return new Point2D.Double(centerX, centerY);
    }

    /**
     * Gets a random light event.
     * @return a random light event
     */
    public static Event getRandomLightEvent()
    {
        Event lEvent = new LightEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

        return lEvent;
    }

    /**
     * Gets a random sound event.
     * @return a random sound event
     */
    public static Event getRandomSoundEvent()
    {
         Event sEvent = new SoundEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

        return sEvent;
    }

    /**
     * Gets a random scary event.
     * @return a random scary event
     */
    public static Event getRandomScaryEvent()
    {
         Event sEvent = new ScaryEvent(
            new Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                (double) (Math.random() * Environment.getMaxY())));

        return sEvent;
    }

    /**
     * Gets a random event.
     * @return a random event
     */
    public static Event getRandomEvent()
    {
        double randomizer = Math.random() * 500;

        if (randomizer < 200)
            return getRandomLightEvent();
        else if (randomizer < 400)
            return getRandomSoundEvent();
        else
            return getRandomScaryEvent();
    }

    public abstract boolean isPerceived(Person person);
    public abstract void setResponse(Person person);


    protected Point2D location;
    protected int width;
    protected int height;
    protected int intensity;
    protected int duration;
    protected Calendar startTime;
    protected ArrayList<Integer> observedBy;
    private static final int MAX_SIZE = 100;
    private static final int MAX_DURATION = 30;
}
