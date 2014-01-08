package socialengine.collections;

import socialengine.components.Person;
import java.lang.reflect.Method;

/**
 * An action that will be added to a person's task queue.
 */
public class Task
{
    /**
     * Creates a new task.
     * @param methodName the name of the Person method that will be called
     * @param parameters an array of the parameter types accepted by the method
     * @param parameterValues an array of each parameter value
     * @throws java.lang.NoSuchMethodException
     */
    public Task(String methodName, Class parameters[], Object parameterValues[]) 
        throws NoSuchMethodException
    {
        this.methodName = methodName;
        paramObj = parameterValues;
        theMethod = Person.class.getDeclaredMethod(methodName, parameters);
    }

    /**
     * Gets the method that is called by this task.
     * @return the method that is called by this task
     */
    public Method getTheMethod() { return theMethod; }

    /**
     * Gets the parameter values that will need to be passed to the method
     * called by this task.
     * @return an array of parameter values to be passed to the method
     */
    public Object[] getParameterValues() { return paramObj; }

    private String methodName;
    private Object paramObj[];
    private Method theMethod;
}
