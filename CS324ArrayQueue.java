public class CS324ArrayQueue<T> implements CS324QueueADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private T[] queue;
    private int front, rear, count;
    
    @SuppressWarnings("unchecked")
    public CS324ArrayQueue() {
        queue = (T[]) new Object[DEFAULT_CAPACITY];
        front = rear = count = 0;
    }
    
    public void enqueue(T element) {
        if (size() == queue.length) expandCapacity();
        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }
    
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        T result = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        return result;
    }
    
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return queue[front];
    }
    
    public boolean isEmpty() { return count == 0; }
    public int size() { return count; }
    
    public void clear() {
        for (int i = 0; i < queue.length; i++)
            queue[i] = null;
        front = rear = count = 0;
    }
    
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        T[] larger = (T[]) new Object[queue.length * 2];
        for (int i = 0; i < count; i++) {
            larger[i] = queue[front];
            front = (front + 1) % queue.length;
        }
        front = 0;
        rear = count;
        queue = larger;
    }
}