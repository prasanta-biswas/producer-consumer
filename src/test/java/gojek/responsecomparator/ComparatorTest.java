package gojek.responsecomparator;

import gojek.responsecomparator.service.URLComparatorImpl;
import gojek.responsecomparator.specification.IComparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class ComparatorTest {

    @Test
    public void testCompareValidUrl(){
        IComparator comparator = new URLComparatorImpl();
        Assert.assertTrue(comparator.compare("https://reqres.in/api/users/1","https://reqres.in/api/users/1"));
    }

    @Test
    public void testCompareInvalidUrl(){
        IComparator comparator = new URLComparatorImpl();
        Assert.assertFalse(comparator.compare("htt://reqres.in/api/users/1","htps://reqres.in/api/users/1"));
    }

}
