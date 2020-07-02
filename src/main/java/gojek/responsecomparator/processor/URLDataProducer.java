package gojek.responsecomparator.processor;

import gojek.responsecomparator.model.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * Created by prasantabiswas on 16/09/18.
 */
public class URLDataProducer implements Runnable{

    private final File file1;
    private final File file2;
    private final BlockingQueue<Data> queue;

    public URLDataProducer(File file1, File file2, BlockingQueue<Data> queue){
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
                Data data = new Data();
                data.setValue1(fileReader1.nextLine().trim());
                data.setValue2(filerReader2.nextLine().trim());
                queue.put(data);
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
