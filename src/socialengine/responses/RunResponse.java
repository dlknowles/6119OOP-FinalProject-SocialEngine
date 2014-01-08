package socialengine.responses;

import socialengine.collections.*;
import socialengine.events.*;
import java.awt.geom.*;
import java.util.logging.*;
import socialengine.components.*;

/**
 *
 * @author lee.knowles
 */
public class RunResponse implements Response
{

    public RunResponse(Person p, Event e)
    {
        this.p = p;
        this.e = e;
    }

    public void setTasks()
    {
        // Define a moveTo task
        Class[] runFromParams = {Point2D.class, boolean.class};
        Object[] runFromParamValues = {e.getCenter(), true};

        try {
            Task runFromTask = new Task("runFrom", runFromParams, runFromParamValues);

            p.addTask(runFromTask);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InvestigateResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Person p;
    Event e;
}
