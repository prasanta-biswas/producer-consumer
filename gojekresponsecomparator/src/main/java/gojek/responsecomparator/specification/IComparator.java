package gojek.responsecomparator.specification;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public interface IComparator<X,Y> {
    public boolean compare(X url1, X url2);
    public void getData(Y filePath1, Y filePath2);
}
