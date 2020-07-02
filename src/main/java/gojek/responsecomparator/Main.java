package gojek.responsecomparator;

import gojek.responsecomparator.service.URLComparatorImpl;
import gojek.responsecomparator.specification.IComparator;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Main {
    private static final IComparator COMPARATOR = new URLComparatorImpl();

    public static void main(String [] args) {

        if (args.length == 0) {
            System.out.println("No argument specified.");
            return;
        } else if (args.length < 2) {
            System.out.println("Requires two file paths as argument. One argument specified.");
            return;
        }
        COMPARATOR.setSource(args[0], args[1]);
    }
}
