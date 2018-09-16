package gojek.responsecomparator.test;

import gojek.responsecomparator.implementation.Comparator;
import gojek.responsecomparator.specification.IComparator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class ComparatorTest {

    @Test
    public void testCompareValidUrl(){
        IComparator comparator = new Comparator();
        Assert.assertTrue(comparator.compare("https://reqres.in/api/users/1","https://reqres.in/api/users/1"));
    }

    @Test
    public void testCompareInvalidUrl(){
        IComparator comparator = new Comparator();
        Assert.assertFalse(comparator.compare("htt://reqres.in/api/users/1","htps://reqres.in/api/users/1"));
    }

}
