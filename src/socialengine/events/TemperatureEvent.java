package socialengine.events;

import socialengine.components.*;
import socialengine.responses.*;
import java.awt.geom.*;

/**
 * Defines an event that causes a change in temperature an area of the
 * environment that is noticable to nearby person objects.
 */
public class TemperatureEvent extends Event
{
    public TemperatureEvent(Point2D location)
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
