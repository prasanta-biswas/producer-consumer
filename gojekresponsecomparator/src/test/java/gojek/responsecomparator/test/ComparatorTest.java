package gojek.responsecomparator.test;

import gojek.responsecomparator.implementation.Comparator;
import gojek.responsecomparator.specification.IComparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class ComparatorTest {

    @Test
    public void testCompare(){
        IComparator comparator = new Comparator();
        Assert.assertTrue(comparator.compare("a","b"));
    }

    @Test
    public void testGetData(){

    }

}
