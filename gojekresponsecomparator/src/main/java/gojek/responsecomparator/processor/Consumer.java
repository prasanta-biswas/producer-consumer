package gojek.responsecomparator.processor;

import gojek.responsecomparator.implementation.Comparator;

import java.util.concurrent.BlockingQueue;

/**
 * Created by prasantabiswas on 16/09/18.
 */
public class Consumer implements Runnable {

    private BlockingQueue<Comparator> queue;

    public Consumer(BlockingQueue<Comparator> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Comparator comparator = queue.poll();
            if(comparator == null)
                return;
            comparator.compare(comparator.getUrl1(), comparator.getUrl2());
            comparator.display();
        }
    }
}
