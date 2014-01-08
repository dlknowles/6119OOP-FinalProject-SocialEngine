package socialengine.events;

import socialengine.components.*;
import socialengine.responses.*;
import java.awt.geom.*;

/**
 * Defines an event that a person can smell.
 */
public class SmellEvent extends Event
{
    public SmellEvent(Point2D location)
    {
        super(location);
    }
    
    @Override
    public boolean isPerceived(Person person)
    {
        return true;
    }

    @Override
    public void setResponse(Person person)
    {
        Response r = new InvestigateResponse(person, this);
        r.setTasks();
    }

}
