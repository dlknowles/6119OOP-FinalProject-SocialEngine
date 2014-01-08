package socialengine.events;

import socialengine.components.*;
import socialengine.responses.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * Defines an audible sound event.
 */
public class SoundEvent extends Event
{
    public SoundEvent(Point2D location)
    {
        super(location);

        observedBy = new ArrayList<Integer>();
        duration = 20;//(int) Math.random() * 19 + 1;
        /*
         *Attributes added by me
         */
        decibals = Math.random() * 100;
        harmFactor = Math.random() * 100;
        rushFactor = Math.random() * 100;
    }

    public boolean isPerceived(Person person)
    {
        if (observedBy.contains(person.getPersonNumber()))
            return false;

        if (person.getTasks().size() == 0)
        {
            if (person.getHearingDistance() == 0)
            {
                return false;
            }
            else
            {
                double distance = getDistanceFrom(person);

                if (distance <= 0 || distance <= person.getHearingDistance())
                {
                    // check for collision
                    if (isCollided(person))
                    {
                        //observedBy.add(person.getPersonNumber());
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            }
        }

        return false;
    }

    private boolean isCollided(Person p)
    {
        return true;
    }

    private double getDistanceFrom(Person p)
    {
        double deltaX = p.getCurrentLocation().getX() - location.getX();
        double deltaY = p.getCurrentLocation().getY() - location.getY();

        double distance =
            Math.sqrt((deltaX * deltaX) + (deltaY * deltaY)) - (width / 2);

        return distance;
    }

    public void setResponse(Person person)
    {
        //Response is going to be either Investigate or Run based on
        //the results of a claculation using attributes of the person
        //object and attributes of the event
        double responseFactor = (harmFactor - (rushFactor - (person.getSerious() - 50))-person.getCurious() + person.getNervous());
//        responseFactor = 10;
        if (responseFactor < -5)
        {
            observedBy.add(person.getPersonNumber());
            Response r = new InvestigateResponse(person, this);
            r.setTasks();
        }
        else if (responseFactor > 5)
        {
            person.getTasks().clearQueue();
            Response r = new RunResponse(person, this);
            r.setTasks();
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLUE);
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Sound Event", (float) getCenter().getX(), (float) getCenter().getY());
        g.setColor(Color.BLACK);
    }

    // private ArrayList<Integer> observedBy;
    /*
     *Attributes added by me
     */
    private double decibals;
    private double harmFactor;
    private double rushFactor;

}
