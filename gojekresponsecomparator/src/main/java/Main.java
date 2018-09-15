import gojek.responsecomparator.implementation.Comparator;
import gojek.responsecomparator.specification.IComparator;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Main {

    private static final IComparator<String, String> COMPARATOR = new Comparator();
    public static void main(String [] args){
        COMPARATOR.getData("a","v");
        COMPARATOR.getData("/Users/prasantabiswas/Desktop/file1.txt", "/Users/prasantabiswas/Desktop/file2.txt");
    }

}
