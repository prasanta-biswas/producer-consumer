package gojek.responsecomparator.implementation;

import gojek.responsecomparator.handlers.XmlHandler;
import gojek.responsecomparator.processor.Consumer;
import gojek.responsecomparator.handlers.JsonHandler;
import gojek.responsecomparator.processor.Producer;
import gojek.responsecomparator.specification.IComparator;
import gojek.responsecomparator.utility.Helper;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Comparator implements IComparator<String,String>{
    private File file1;
    private File file2;
    boolean isEqual;
    private String url1;
    private String url2;
    private String error;
    private Helper utility = Helper.getInstance();
    private static final int NUMBER_OF_CONSUMER = 10;
    private static final int QUEUE_SIZE = 100;
    private static BlockingQueue<Comparator> queue;
    private static Thread producerThread;
    private static Collection<Thread> threadCollections;

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    @Override
    public boolean compare(String url1, String url2) {
        try{
            Boolean result = false;
            Response response1 = utility.getResponse(url1);
            Response response2 = utility.getResponse(url2);

            if(response1.contentType().contains("json") && response2.contentType().contains("json"))
                result = JsonHandler.compareJson(response1,response2);
            else if(response1.contentType().contains("xml") && response2.contentType().contains("xml"))
                result = XmlHandler.compareXml(response1,response2);
            else
                this.error = "Content type mismatch";

            return result;
        }
        catch (Exception e) {
            this.error = e.getMessage();
            return this.isEqual = false;
        }
    }

    @Override
    public void getData(String filePath1, String filePath2) {
        this.file1 = new File(filePath1);
        this.file2 = new File(filePath2);
        queue = new LinkedBlockingDeque<>(QUEUE_SIZE);
        threadCollections = new ArrayList<>();

        createAndStartProducer();
        createAndStartConsumer();

        for(Thread t: threadCollections){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Comparison finished");
        System.out.println("------------------------------------");

    }

    @Override
    public void display()
    {
        if(error == null) {
            if (isEqual) {
                System.out.println(url1 + " equals " + url2);
            } else {
                System.out.println(url1 + " not equals " + url2);
            }
        }
        else
            System.out.println(error);
    }

    private void createAndStartProducer() {
        Producer producer = new Producer(file1,file2,queue);
        producerThread = new Thread(producer,"producer");
        producerThread.start();
        threadCollections.add(producerThread);
    }

    private void createAndStartConsumer() {
        for(int i = 0; i < NUMBER_OF_CONSUMER; i++){
            Thread consumerThread = new Thread(new Consumer(queue), "consumer-"+i);
            threadCollections.add(consumerThread);
            consumerThread.start();
        }
    }

    public static boolean isProducerAlive(){
        if(producerThread.isAlive())
            return true;
        else
            return false;
    }
}
