package gojek.responsecomparator.processor;

import gojek.responsecomparator.implementation.Comparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by prasantabiswas on 16/09/18.
 */
public class Producer implements Runnable{

    private File file1;
    private File file2;
    private BlockingQueue<Comparator> queue;

    public Producer(File file1, File file2, BlockingQueue<Comparator> queue){
        this.file1 = file1;
        this.file2 = file2;
        this.queue = queue;
    }

    @Override
    public void run() {
        LineIterator it1 = null;
        LineIterator it2 = null;
        try {
            it1 = FileUtils.lineIterator(file1, "UTF-8");
            it2 = FileUtils.lineIterator(file2, "UTF-8");

            while (it1.hasNext() && it2.hasNext()) {
                Comparator comparator = new Comparator();
                comparator.setUrl1(it1.nextLine().trim());
                comparator.setUrl2(it2.nextLine().trim());
                queue.put(comparator);
            }
        } catch (InterruptedException | IOException e) {
            System.out.println("Error occurred: "+e);
        } finally {
            if(it1 != null)
                LineIterator.closeQuietly(it1);
            if(it2 != null)
                LineIterator.closeQuietly(it2);
        }
    }
}
