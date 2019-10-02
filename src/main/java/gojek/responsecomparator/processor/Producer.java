package gojek.responsecomparator.processor;

import gojek.responsecomparator.implementation.Comparator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
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
        Scanner fileReader1 = null;
        Scanner filerReader2 = null;
        FileInputStream inputStream1 = null;
        FileInputStream inputStream2 = null;
        try {
            inputStream1 = new FileInputStream(file1);
            inputStream2 = new FileInputStream(file2);
            fileReader1 = new Scanner(inputStream1, "UTF-8");
            filerReader2 = new Scanner(inputStream2, "UTF-8");

            while (fileReader1.hasNextLine() && filerReader2.hasNextLine()) {
                Comparator comparator = new Comparator();
                comparator.setUrl1(fileReader1.nextLine().trim());
                comparator.setUrl2(filerReader2.nextLine().trim());
                queue.put(comparator);
            }
        } catch (InterruptedException | IOException e) {
            System.out.println("Error occurred: "+e);
        } finally {
            if(inputStream1 != null) {
                try {
                    inputStream1.close();
                }catch (IOException e){}
            }
            if(inputStream2 != null) {
                try {
                    inputStream2.close();
                }catch (IOException e){}
            }
            if(fileReader1 != null)
                fileReader1.close();
            if(filerReader2 != null)
                filerReader2.close();
        }
    }
}
