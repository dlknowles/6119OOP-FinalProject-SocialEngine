package socialengine.components;

import socialengine.collections.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;

/**
 * Defines a Person.
 */
public class Person extends JComponent
{
    /**
     * Creates a new person.
     */
    public Person()
    {
        currentLocation = new
            Point2D.Double((double) (Math.random() * Environment.getMaxX()),
                            (double) (Math.random() * Environment.getMaxY()));
        width = 10;
        height = 10;
        speed = Math.random() * 2;
        tasks = new TaskQueue(100);
        taskStartTime = null;
        count++;
        personNumber = count;
        movementVector = new Point2D.Double();
        setRandomMovementVector();
        eyesight = Math.random() * 100;
        hearing = Math.random() * 100;
        /*
         *Attributes added by me
         */
        curious = Math.random() * 100;
        serious = Math.random() * 100;    //0 = want to have fun all the tim 100 = serious all the time
        nervous = Math.random() * 100;
        compassion = Math.random() * 100;
        outgoing = Math.random() * 100;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        Ellipse2D e = new Ellipse2D.Double(
            currentLocation.getX(), currentLocation.getY(), width, height);
        g2.fill(e);

        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(personNumber), (float) getCenter().getX(), 
                     (float) getCenter().getY());

        g2.draw(e);

        calcPerceptionPoints();

        if (displayAttributes)
            displayAttributes(g2);

        if (displayVision)
            drawLineOfSight(g2);
    }

    /**
     * Displays relevant attributes. Used mostly for testing and to see what
     * kind of people are being created.
     * @param g2 the graphics context
     */
    private void displayAttributes(Graphics2D g2)
    {
        float centerX = (float) getCenter().getX();
        float centerY = (float) getCenter().getY();
        String directionString = String.valueOf(movementVector.getX()) + ", " +
            String.valueOf(movementVector.getY()) + ")";

        g2.drawString("direction: " + directionString, centerX, centerY + 10);
        g2.drawString("eyesight: " + String.valueOf(getSightDistance()),
                      centerX, centerY + 22);
        g2.drawString("hearing: " + String.valueOf(getHearingDistance()),
                      centerX, centerY + 34);
    }

    /**
     * Set the values for the points that define the person's line of sight.
     */
    private void calcPerceptionPoints()
    {
        double centerX = getCenter().getX();
        double centerY = getCenter().getY();

        // Get the mid-point of the farthest line of the perception triangle
        Point2D endPoint = getEndPoint(
            new Point2D.Double(centerX, centerY),
            movementVector, getSightDistance());

        // Get the top point of the perception triangle
        // First, we need to modify the slope based on the quadrant the person is looking at
        Point2D[] newSlopes = getNewSlopes();
        double distance2 = (getSightDistance() * Math.sin(Math.toDegrees(60))) / Math.sin(Math.toDegrees(30));
        perPoint1 = getEndPoint(endPoint, newSlopes[0], distance2);
        perPoint2 = getEndPoint(endPoint, newSlopes[1], distance2);
    }

    /**
     * Displays the person's line of sight as a triangle. It should be noted
     * that the person will not be able to see things at the outer angles of
     * the triangle. The line of sight is limited by the person's sight distance.
     * You should view the outer angles and edge more like an arc.
     * @param g2 the graphics context
     */
    private void drawLineOfSight(Graphics2D g2)
    {
        double centerX = getCenter().getX();
        double centerY = getCenter().getY();
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawLine((int) Math.round(centerX),
                    (int) Math.round(centerY),
                    (int) Math.round(perPoint1.getX()),
                    (int) Math.round(perPoint1.getY()));

        // Get the bottom point of the perception triangle
        g2.drawLine((int) Math.round(centerX),
                    (int) Math.round(centerY),
                    (int) Math.round(perPoint2.getX()),
                    (int) Math.round(perPoint2.getY()));

        g2.drawLine((int) Math.round(perPoint1.getX()),
                    (int) Math.round(perPoint1.getY()),
                    (int) Math.round(perPoint2.getX()),
                    (int) Math.round(perPoint2.getY()));

        g2.setColor(Color.BLACK);
    }

    /**
     * Helper method that gets an array of two slopes used to build the line
     * of sight triangle.
     * @return the slopes of the side edges of the line of sight triangle
     */
    private Point2D[] getNewSlopes()
    {
        double mX = movementVector.getX();
        double mY = movementVector.getY();
        Point2D[] newSlopes = new Point2D.Double[2];

        newSlopes[0] = movementVector;
        newSlopes[1] = movementVector;

        if (mX < 0 && mY != 0)
        {
            // Looking to the top left or bottom left
            newSlopes[0] = new Point2D.Double(mY, mX * -1);
            newSlopes[1] = new Point2D.Double(mY * -1, mX);
        }
        else if (mX > 0 && mY != 0)
        {
            // Looking to the top right or bottom right
            newSlopes[0] = new Point2D.Double(mY, mX * -1);
            newSlopes[1] = new Point2D.Double(mY * -1, mX);
        }
        else if (mX == 0 && mY != 0)
        {
            // Looking directly up or down
            newSlopes[0] = new Point2D.Double(mY, mX);
            newSlopes[1] = new Point2D.Double(mY * -1, mX);
        }
        else if (mY == 0 && mX != 0)
        {
            // Looking left or right
            newSlopes[0] = new Point2D.Double(mY, mX);
            newSlopes[1] = new Point2D.Double(mY, mX * -1);
        }

        return newSlopes;
    }

    /**
     * Finds the point on a line that is a specified distance away from another
     * point.
     * @param startPoint the starting point
     * @param slope the slope of the line (in other words the direction the
     * other point is in)
     * @param distance the distance the end point is from the start point
     * @return the end point (the point that is the specified distance away
     * from the starting point and in the direction of the slope)
     */
    private Point2D getEndPoint(Point2D startPoint,
                                        Point2D slope,
                                        double distance)
    {

        double slopeLength = Math.sqrt((slope.getX() * slope.getX()) +
                                (slope.getY() * slope.getY()));
        Point2D newSlope =
            new Point2D.Double((slope.getX() / slopeLength) * distance,
                                (slope.getY() / slopeLength) * distance);
        Point2D endPoint =
            new Point2D.Double(newSlope.getX() + startPoint.getX(),
                                newSlope.getY() + startPoint.getY());

        return endPoint;
    }

    /**
     * Makes the person move to a specific location.
     * @param originalLocation the location the person was before moving
     * @param destination the point the person is moving to
     * @param isTask is this method being called as a part of the task queue?
     */
    public void moveTo(Point2D originalLocation, Point2D destination, boolean isTask)
    {
        double distance = currentLocation.distance(destination);

        // If not already at the destination
        if (distance == 0)
        {
            if (isTask) tasks.remove();
        }
        else
        {
            Point2D originalM = new Point2D.Double(
                destination.getX() - originalLocation.getX(),
                destination.getY() - originalLocation.getY());

            Point2D m = new Point2D.Double(
                destination.getX() - currentLocation.getX(),
                destination.getY() - currentLocation.getY());

            if ((m.getX() > 0 && originalM.getX() < 0) ||
                (m.getX() < 0 && originalM.getX() > 0) ||
                (m.getY() > 0 && originalM.getY() < 0) ||
                (m.getY() < 0 && originalM.getY() > 0))
            {
                setNewLocation(destination.getX(), destination.getY());
                tasks.remove();
                setRandomMovementVector();
            }
            else
            {
                // double time = distance / speed;
                double dx = m.getX() / distance;
                double dy = m.getY() / distance;

                if (dx > 1) dx = 0.9;
                else if (dx < -1) dx = -0.9;

                if (dy > 1) dy = 0.9;
                else if (dy < -1) dy = -0.9;

                movementVector = new Point2D.Double(dx, dy);
                translate(movementVector.getX() * speed,
                          movementVector.getY() * speed);
                // translate(dx * speed, dy * speed);
            }
        }
    }

    /**
     * Makes the person run away from a point.
     * @param runningFrom the point to run from
     * @param isTask is this method being called as a part of the task queue?
     */
    public void runFrom(Point2D runningFrom, boolean isTask)
    {
        // have the person run at their maximum speed in any direction away from
        // the runningFrom point.
        // this task gets removed from the queue each time because the person
        // should keep running until the event is no longer perceived.
        double distance = currentLocation.distance(runningFrom);
        double runSpeed = speed * MAX_SPEED_MULTIPLIER;
        Point2D m = new Point2D.Double(
            runningFrom.getX() - currentLocation.getX(),
            runningFrom.getY() - currentLocation.getY());
        double dx = -m.getX() / distance;
        if (dx > 1) dx = 0.9;
        else if (dx < -1) dx = -0.9;

        double dy = -m.getY() / distance;
        if (dy > 1) dy = 0.9;
        else if (dy < -1) dy = -0.9;

        movementVector.setLocation(dx, dy);

        translate(dx * runSpeed, dy * runSpeed);
        tasks.remove();
    }

    /**
     * Tells the person to stop and look around.
     * @param seconds the number of seconds the person should look
     * @param isTask is this method being called from the task queue?
     */
    public void look(int seconds, boolean isTask)
    {
        if (taskStartTime == null) taskStartTime = Calendar.getInstance();

        Calendar nowEnd = Calendar.getInstance();
        nowEnd.add(Calendar.SECOND, -seconds);

        if (taskStartTime.compareTo(nowEnd) <= 0)
        {
            taskStartTime = null;
            if (isTask) tasks.remove();
        }
    }

    /**
     * Randomizes the movement vector.
     */
    private void setRandomMovementVector()
    {
        double dx;
        double dy;

        dx = Math.random() * (double) (rand.nextInt(3) - 1);
        dy = Math.random() * (double) (rand.nextInt(3) - 1);

        if (dx > 1) dx = Math.random() * 1;
        else if (dx < -1) dx = Math.random() * -1;

        if (dy > 1) dy = Math.random() * 1;
        else if (dy < -1) dy = Math.random() * -1;

        if (dx == 0 && dy == 0)
        {
            dx = 0.1;
            dy = 0.1;
        }

        movementVector.setLocation(dx, dy);
    }

    /**
     * Tells the person object to move.
     * @param dx distance to move on x-axis
     * @param dy distance to move on y-axis
     */
    private void translate(double dx, double dy)
    {
        Point2D newLocation = new Point2D.Double(
            currentLocation.getX() + dx, currentLocation.getY() + dy);

        currentLocation = newLocation;
        //movementVector.setLocation(dx, dy);
    }

    /**
     * Sets the current location of the person.
     * @param x the x coordinate of the new location
     * @param y the y coordinate of the new location
     */
    public void setNewLocation(double x, double y)
    {
        Point2D newLocation = new Point2D.Double(x, y);

        currentLocation = newLocation;
    }

    /**
     * Gets the person's current location.
     * @return the person's current location
     */
    public Point2D getCurrentLocation() { return currentLocation; }

    /**
     * Sets the person's speed.
     * @param newValue the value to set as the person's speed
     */
    public void setSpeed(double newValue) { speed = newValue; }

    /**
     * Gets the person's current speed.
     * @return the person's speed
     */
    public double getSpeed() { return speed; }

    /**
     * Adds a task to the person's task queue.
     * @param t the task to add
     */
    public void addTask(Task t) { tasks.add(t); }

    /**
     * Tells the person to perform current tasks. If there are no tasks, the
     * person will travel through the environment at random.
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public void update()
        throws IllegalAccessException, InvocationTargetException
    {
        if (tasks.size() > 0)
        {
            Method m = tasks.peek().getTheMethod();
            m.invoke(this, tasks.peek().getParameterValues());
        }
        else
        {
            translate(movementVector.getX() * speed, movementVector.getY() * speed);
        }

        adjustLocation();
    }

    /**
     * Checks to see if the person has gone outside the bounds of the
     * environment. If so, it adjusts the location to be inside the environment
     * and sets the movement/direction to a random vector.
     */
    private void adjustLocation()
    {
        double newX = currentLocation.getX();
        double newY = currentLocation.getY();

        if (newX < Environment.getMinX() || newX > Environment.getMaxX() ||
            newY < Environment.getMinY() || newY > Environment.getMaxY())
        {
            setRandomMovementVector();

            if (currentLocation.getX() < Environment.getMinX())
            {
                newX = Environment.getMinX();
            }
            else if (currentLocation.getX() > Environment.getMaxX())
            {
                newX = Environment.getMaxX();
            }

            if (currentLocation.getY() < Environment.getMinY())
            {
                newY = Environment.getMinY();
            }
            else if (currentLocation.getY() > Environment.getMaxY())
            {
                newY = Environment.getMaxY();
            }

            setNewLocation(newX, newY);
        }
    }

    /**
     * Calculates the distance of the person's sight range.
     * @return the distance of the person's sight range
     */
    public double getSightDistance() { return eyesight * 2; }

    /**
     * Calculates the distance of the person's hearing range.
     * @return the distance of the person's hearing range
     */
    public double getHearingDistance() { return hearing * 2; }

    /**
     * Returns the person's task queue.
     * @return the person's task queue
     */
    public TaskQueue getTasks() { return tasks; }

    /**
     * Gets the person's identification number.
     * @return the person's identification number
     */
    public int getPersonNumber() { return personNumber; }

    /**
     * Gets the person's seriousness level.
     * @return how serious the person is
     */
    public double getSerious() { return serious; }

    /**
     * Gets the person's curiousness level
     * @return how curious a person is
     */
    public double getCurious() { return curious; }

    /**
     * Gets the person's nervousness level
     * @return how nervous a person is
     */
    public double getNervous() { return nervous; }

    /**
     * Gets the person's compassion level
     * @return how compassionate a person is
     */
    public double getCompassion() { return compassion; }

    /**
     * Gets how outgoing a person is
     * @return how outgoing a person is
     */
    public double getOutgoing() { return outgoing; }

    /**
     * Gets the center of the person's graphical display.
     * @return the center of the person's graphical display
     */
    public Point2D getCenter()
    {
        double centerX = currentLocation.getX() + (double) (width / 2);
        double centerY = currentLocation.getY() + (double) (height / 2);

        return new Point2D.Double(centerX, centerY);
    }

    /**
     * Gets the person's first line of sight perception point.
     * @return the person's first line of sight perception point
     */
    public Point2D getPerPoint1() { return perPoint1; }

    /**
     * Gets the person's second line of sight perception point.
     * @return the person's second line of sight perception point
     */
    public Point2D getPerPoint2() { return perPoint2; }

    /**
     * Sets the person count to zero
     */
    public static void resetPersonCount() { count = 0; }

    /**
     * Decrements the person count by one.
     */
    public static void decrementPersonNumber() { count--; }

    /**
     * Builds a person with random attributes.
     * @return a person with random attributes
     */
    public static Person getRandomPerson()
    {
        // Create a new person
        Person p = new Person();
        p.setNewLocation(
            (double) (Math.random() * Environment.getMaxX()),
            (double) (Math.random() * Environment.getMaxY()));

        return p;
    }

    /**
     * Determines if the person's field of vision is displayed.
     * @return true if vision is displayed, false if not
     */
    public static boolean isVisionDisplayed() { return displayVision; }

    /**
     * Tells the person object to display it's field of vision.
     */
    public static void displayFieldOfVision() { displayVision = true; }

    /**
     * Tells the person object to not display it's field of vision.
     */
    public static void hideFieldOfVision() { displayVision = false; }

    /**
     * Determines whether or not person attributes will be displayed.
     * @return true if attributes should be displayed, false if not
     */
    public static boolean isAttributesDisplayed() { return displayAttributes; }

    /**
     * Tells person objects to display their attributes.
     */
    public static void displayAttributes() { displayAttributes = true; }

    /**
     * Tells person objects not to display attributes.
     */
    public static void hideAttributes() { displayAttributes = false; }

    private Point2D currentLocation;
    private int width;
    private int height;
    private double speed;
    private static final double MAX_SPEED_MULTIPLIER = 2;
    private TaskQueue tasks;
    Calendar taskStartTime;
    private static int count = 0;
    private int personNumber;
    private Point2D movementVector;
    private double eyesight;
    private double hearing;
    private static Random rand = new Random();
    /*
     *Attributes added by me
     */
    private double curious;
    private double serious;    //0 = want to have fun all the tim 100 = serious all the time
    private double nervous;
    private double compassion;
    private double outgoing;
    private Point2D perPoint1;
    private Point2D perPoint2;
    private static boolean displayVision;
    private static boolean displayAttributes;
}