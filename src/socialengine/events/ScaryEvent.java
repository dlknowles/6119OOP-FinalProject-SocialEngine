package socialengine.events;

import socialengine.components.*;
import socialengine.responses.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * An event that is scary to most people. This event is only here for testing
 * purposes. It should always generate a run response. Normally, we wouldn't
 * want to build such an event that always generates the same response. The
 * person's attributes should determine this, but we leave it here so that
 * we have an event that can adequately test the run response.
 */
public class ScaryEvent extends Event
{
    /**
     * Creates a new scary event.
     * @param location the location of the event
     */
    public ScaryEvent(Point2D location)
    {
        super(location);
    }

    @Override
    public boolean isPerceived(Person person)
    {
        if (person.getSightDistance() == 0)
        {
            return false;
        }
        else
        {
            double distance = getDistanceFrom(person);

            if (distance <= 0 || distance <= person.getSightDistance())
            {
//                observedBy.add(person.getPersonNumber());
                return true;
            }
        }

        return false;
    }

   private double getDistanceFrom(Person p)
    {
        double deltaX = p.getCurrentLocation().getX() - location.getX();
        double deltaY = p.getCurrentLocation().getY() - location.getY();

        double distance =
            Math.sqrt((deltaX * deltaX) + (deltaY * deltaY)) - (width / 2);

        return distance;
    }

    @Override
    public void setResponse(Person person)
    {
        person.getTasks().clearQueue();
        Response r = new RunResponse(person, this);
        r.setTasks();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Scary Event", (float) getCenter().getX(), (float) getCenter().getY());
    }
}
