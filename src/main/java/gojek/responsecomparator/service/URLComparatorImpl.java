package gojek.responsecomparator.service;

import gojek.responsecomparator.handlers.JsonHandler;
import gojek.responsecomparator.handlers.XmlHandler;
import gojek.responsecomparator.model.Data;
import gojek.responsecomparator.processor.URLDataConsumer;
import gojek.responsecomparator.processor.URLDataProducer;
import gojek.responsecomparator.specification.IComparator;
import gojek.responsecomparator.utility.Helper;
import io.restassured.response.Response;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class URLComparatorImpl implements IComparator<String, String> {

    private String error;
    private boolean isEqual;
    private File file1;
    private File file2;
    private static final int NUMBER_OF_CONSUMER = 1;
    private static final int QUEUE_SIZE = 100;
    private static BlockingQueue<Data> queue;
    private static Thread producerThread;
    private static Collection<Thread> threadCollections;

    public URLComparatorImpl(){
        this.error = null;
        this.isEqual = false;
        this.file1 = null;
        this.file2 = null;
        queue = null;
    }

    public String getError(){
        return this.error;
    }

    public void setSource(String filePath1, String filePath2) {
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

    private void createAndStartProducer()  {
        URLDataProducer producer = new URLDataProducer(file1, file2, queue);
        producerThread = new Thread(producer,"producer");
        producerThread.start();
        threadCollections.add(producerThread);
    }

    private void createAndStartConsumer() {
        for(int i = 0; i < NUMBER_OF_CONSUMER; i++){
            Thread consumerThread = new Thread(new URLDataConsumer(queue), "consumer-"+i);
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

    @Override
    public boolean compare(String url1, String url2) {
        try{
            URL uri1 = new URL(url1);
            URL uri2 = new URL(url2);
            Response response1 = Helper.getResponse(uri1.toString());
            Response response2 = Helper.getResponse(uri2.toString());

            if(response1.contentType().contains("json") && response2.contentType().contains("json"))
                this.isEqual = JsonHandler.compareJson(response1,response2);
            else if(response1.contentType().contains("xml") && response2.contentType().contains("xml"))
                this.isEqual = XmlHandler.compareXml(response1,response2);
            else
                this.error = "Unsupported content type: ["+response1.contentType()+"] ,["+response2.contentType()+"]";

            return isEqual;
        }
        catch (Exception e) {
            this.error = e.getMessage();
            return this.isEqual = false;
        }
    }
}
