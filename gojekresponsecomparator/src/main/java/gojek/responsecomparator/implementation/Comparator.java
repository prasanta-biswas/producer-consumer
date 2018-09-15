package gojek.responsecomparator.implementation;

import gojek.responsecomparator.specification.IComparator;
import gojek.responsecomparator.utility.Helper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Comparator implements IComparator<String,String>{
    private File file1;
    private File file2;
    private Helper utility = Helper.getInstance();

    public boolean isEqual() {
        return isEqual;
    }

    boolean isEqual;
    public boolean compare(String url1, String url2) {
        try{
            String response1 = utility.getResponse(url1);
            String response2 = utility.getResponse(url2);
            if(response1.equals(response2))
                return isEqual = true;
            else
                return isEqual = false;
        }
        catch (IOException e) {
            return isEqual = false;
        }
    }

    public void getData(String filePath1, String filePath2) {
        this.file1 = new File(filePath1);
        this.file2 = new File(filePath2);

        LineIterator it1 = null;
        LineIterator it2 = null;
        try {
            it1 = FileUtils.lineIterator(file1, "UTF-8");
            it2 = FileUtils.lineIterator(file2, "UTF-8");

            while (it1.hasNext() && it2.hasNext()) {
                String line1 = it1.nextLine();
                String line2 = it2.nextLine();
                if(compare(line1,line2)) {
                    System.out.println(line1 + " equals " + line2);
                    isEqual = true;
                }
                else {
                    System.out.println(line1 + " not equals " + line2);
                    isEqual = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred: "+e);
        } finally {
            if(it1 != null)
                LineIterator.closeQuietly(it1);
            if(it2 != null)
                LineIterator.closeQuietly(it2);
        }

    }
}
