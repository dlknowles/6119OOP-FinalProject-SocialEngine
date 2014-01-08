package socialengine.responses;

import socialengine.collections.*;
import socialengine.components.*;
import socialengine.events.*;
import java.awt.geom.*;
import java.util.logging.*;

/**
 * Defines an investigate response.
 */
public class InvestigateResponse implements Response
{
    public InvestigateResponse(Person p, Event e)
    {
        this.p = p;
        this.e = e;
    }

    public void setTasks()
    {
        // Define a moveTo task
        Class[] moveToParams = {Point2D.class, Point2D.class, boolean.class};
        Object[] moveToParamValues = {p.getCenter(), e.getCenter(), true};

        // Define a look task
        Class[] lookParams = {int.class, boolean.class};
        Object[] lookParamValues = {(int) (Math.random() * 5), true};

        try {
            Task moveTask = new Task("moveTo", moveToParams, moveToParamValues);
            Task lookTask = new Task("look", lookParams, lookParamValues);

            p.addTask(moveTask);
            p.addTask(lookTask);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InvestigateResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Person p;
    Event e;
}
