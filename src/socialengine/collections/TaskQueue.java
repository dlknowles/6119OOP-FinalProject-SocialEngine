package socialengine.collections;

import java.util.Iterator;

/**
 * A bounded first-in-first-out queue that holds person object tasks.
 */
public class TaskQueue
{
    /**
     * Creates an empty task queue.
     * @param capacity the maximum number of tasks to accept at one time
     */
    public TaskQueue(int capacity)
    {
        elements = new Task[capacity];
        count = 0;
        head = 0;
        tail = 0;
    }

    /**
     * Gets an iterator that iterates through the tasks in the queue.
     * @return an iterator that iterates through the tasks in the queue
     */
    public Iterator<Task> iterator() {
        return new
            Iterator<Task>()
            {
                public boolean hasNext() { return visited < count; }

                public Task next()
                {
                    int index = (head + visited) % elements.length;
                    Task t = (Task) elements[index];
                    visited++;
                    return t;
                }

                public void remove()
                {
                    head = (head + 1) % elements.length;
                    count--;
                }

                private int visited = 0;
            };
    }

    /**
     * Removes the task at the head of the queue.
     * @return the task at the head of the queue
     */
    public Task remove()
    {
        Task t = (Task) elements[head];
        head = (head + 1) % elements.length;
        count--;
        return t;
    }

    /**
     * Adds a task to the queue.
     * @param t the task to add
     * @return true if the task was added, false if it was not
     * @precondition !isFull()
     */
    public boolean add(Task t)
    {
        if (!isFull())
        {
            elements[tail] = t;
            tail = (tail + 1) % elements.length;
            count++;
            return true;
        }
        else return false;
    }

    /**
     * Checks to see if the queue is full.
     * @return true if the queue is full, false if it is not
     */
    public boolean isFull() { return count == elements.length; }

    /**
     * Gets the number of tasks currently in the queue.
     * @return the number of tasks currently in the queue
     */
    public int size() { return count; }

    /**
     * Gets the task at the head of the queue.
     * @return the task at the head of the queue
     */
    public Task peek()
    {
        if (count > 0) return (Task) elements[head];
        else return null;
    }

    /**
     * Changes the task at the head of the queue.
     * @param t the new task
     */
    public void modifyHead(Task t)
    {
        elements[head] = t;
    }

    /**
     * Clears out all tasks from the queue.
     */
    public void clearQueue()
    {
        count = 0;
        head = 0;
        tail = 0;
    }

    private int count;
    private Task[] elements;
    private int head;
    private int tail;
}
