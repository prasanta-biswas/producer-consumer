package gojek.responsecomparator.specification;

import gojek.responsecomparator.model.Data;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public interface IComparator<X,Y> {
    public void setSource(X x, Y y);
    public boolean compare(X x, Y y);
}
