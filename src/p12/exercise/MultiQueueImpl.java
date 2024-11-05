package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{
private final Map<Q, Queue<T>> queues = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {

        return queues.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {

        containsKeyException(queue, true);

        queues.put(queue, new LinkedList<>());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {

        containsKeyException(queue, false);

        return queues.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {  

        containsKeyException(queue, false);

        queues.get(queue).add(elem);
    }

    @Override
    public T dequeue(Q queue) {      

        containsKeyException(queue, false);

        return queues.get(queue).poll();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {

        Map<Q, T> result = new HashMap<>();

        for (Q queue : queues.keySet()) {

            T elem = queues.get(queue).poll();
            result.put(queue, elem);
        }
        return result;
    }

    @Override
    public Set<T> allEnqueuedElements() {

        Set<T> result = new HashSet<>();

        for (Queue<T> q : queues.values()) {

            result.addAll(q);
        }
        return result;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {

        containsKeyException(queue, false);

        Queue<T> q = queues.get(queue);

        List<T> elements = new ArrayList<>(q);

        q.clear();

        return elements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {

        containsKeyException(queue, false);

        Queue<T> q = queues.remove(queue);

        if (queues.isEmpty()) {

            throw new IllegalStateException("non ci sono altre queue");
            
        }
        Queue<T> alternativeQueue = queues.values().iterator().next();

        alternativeQueue.addAll(q);
    }

    public void containsKeyException(Q queue, boolean b) {

        if (queues.containsKey(queue) && b == true) {

            throw new IllegalArgumentException("la queue esiste già");
        }

        if (queues.containsKey(queue) == false && b == false) {

            throw new IllegalArgumentException("la queue non esiste");
            
        }
    }

}
