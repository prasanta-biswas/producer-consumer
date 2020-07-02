package gojek.responsecomparator.processor;

import gojek.responsecomparator.service.URLComparatorImpl;
import gojek.responsecomparator.model.Data;

import java.util.concurrent.BlockingQueue;

/**
 * Created by prasantabiswas on 16/09/18.
 */
public class URLDataConsumer implements Runnable {

    private BlockingQueue<Data> queue;

    public URLDataConsumer(BlockingQueue<Data> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Data data = queue.poll();
            if(data == null && !URLComparatorImpl.isProducerAlive())
                return;
            if(data != null) {
                URLComparatorImpl comparator = new URLComparatorImpl();
                boolean result = comparator.compare(data.getValue1(), data.getValue2());
                if(comparator.getError() == null) {
                    if (result) {
                        System.out.println("Comparison of ["+data.getValue1() + " , " + data.getValue2()+"]: Equal");
                    } else {
                        System.out.println("Comparison of ["+data.getValue1() + " , " + data.getValue2()+"]: Not Equal");
                    }
                }
                else{
                    System.err.println("Comparison of ["+data.getValue1() + " , " + data.getValue2()+"]: Error - "+comparator.getError());
                }
            }
        }
    }
}
