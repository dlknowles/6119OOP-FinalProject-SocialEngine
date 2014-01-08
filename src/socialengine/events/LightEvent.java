package socialengine.events;

import socialengine.components.*;
import java.awt.*;
import java.awt.geom.*;
import socialengine.responses.*;

/**
 * Defines a visual light event.
 */
public class LightEvent extends Event
{
    public LightEvent(Point2D location)
    {
        super(location);

        duration = 20;
        intensity = Math.random() * 100;
        pretty = Math.random() * 100;
        rushFactor = Math.random() * 100;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.ORANGE);
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Light Event", (float) getCenter().getX(), (float) getCenter().getY());

        g.setColor(Color.BLACK);
    }

    public boolean isPerceived(Person person)
    {
        if (observedBy.contains(person.getPersonNumber()))
            return false;

        if (person.getTasks().size() == 0)
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
                    // check if event occurs within cone of perception
                    if (isInConeOfPerception(person, this.getCenter()))
                    {
                        return true;
                    }
                    else
                    {
                        //check if light bleeds over into cone of perception
                        if (isBleedOver(person))
                        {
                            return true;
                        }
                    }
                }

            }
        }

        return false;
    }

    private boolean isBleedOver(Person p)
    {
        //get of event
        double eCenterX = this.getCenter().getX();
        double eCenterY = this.getCenter().getY();

        //get corners of bounding box for event
        Point2D topLeft = new Point2D.Double((eCenterX - (this.getWidth() / 2)), (eCenterY - (this.getHeight() / 2)));

        Point2D topRight = new Point2D.Double((eCenterX + (this.getWidth() / 2)), (eCenterY - (this.getHeight() / 2)));

        Point2D bottomLeft = new Point2D.Double((eCenterX - (this.getWidth() / 2)), (eCenterY + (this.getHeight() / 2)));

        Point2D bottomRight = new Point2D.Double((eCenterX + (this.getWidth() / 2)), (eCenterY + (this.getHeight() / 2)));

        if (isInConeOfPerception(p, topLeft))
        {
            return true;
        }

        if (isInConeOfPerception(p, topRight))
        {
            return true;
        }

        if (isInConeOfPerception(p, bottomLeft))
        {
            return true;
        }

        if (isInConeOfPerception(p, bottomRight))
        {
            return true;
        }

        return false;
    }

    private boolean isInConeOfPerception(Person p, Point2D tPoint)
    {
        double px = p.getCenter().getX();
        double py = p.getCenter().getY();
        double ex = tPoint.getX();
        double ey = tPoint.getY();
        double ax = p.getPerPoint1().getX();
        double ay = p.getPerPoint1().getY();
        double bx = p.getPerPoint2().getX();
        double by = p.getPerPoint2().getY();

        // find x and y properties for the vectors a-p, e-p, b-p
        double apx = ax - px;
        double apy = ay - py;
        double epy = ey - py;
        double epx = ex - px;
        double bpy = by - py;
        double bpx = bx - px;

        //find z for cross products ap x ep, ap x bp and bp x ep
        double apepz = apx * epy - apy * epx;
        double apbpz = apx * bpy - apy * bpx;
        double bpepz = bpx * epy - bpy * epx;
        double bpapz = bpx * apy - bpy * apx;

        //if the product of apepz and apbpz is >=0 then point is on correct side of line ap
        boolean testAP = (apepz * apbpz >= 0);
        boolean testBP = (bpepz * bpapz >= 0);

        //if it not on the correct side then return false
        return testAP && testBP;
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
        //calculate intensty factor
        //if it is too intense the person will run from it
        //if it is dull (intensity towards 0) then the person
        //will ignore it
        double intenseFactor;

        if (intensity >= 90)
        {
            intenseFactor = intensity;
        }
        else if (intensity <= 30)
        {
            intenseFactor = 0;
        }
        else
        {
            if (intensity <= 55)
            {
                intenseFactor = ((100 - ((55 - intensity) * 4))) * (-1);
            }
            else
            {
                intenseFactor = ((100 - ((intensity - 55) * 4))) * (-1);
            }
        }

        double responseFactor = (intenseFactor + (person.getSerious() - 50) - person.getCurious() + person.getNervous());

    if (responseFactor < -5)
        {
           Response r = new InvestigateResponse(person, this);
            r.setTasks();
            observedBy.add(person.getPersonNumber());
        }
        else if (responseFactor > 5)
        {
            person.getTasks().clearQueue();
            Response r = new RunResponse(person, this);
            r.setTasks();
        }
    }

    private double intensity;
    private double pretty;
    private double rushFactor;
}
