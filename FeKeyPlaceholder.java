public class FeKeyPlaceholder<T extends Comparable<T>> implements IterableMultiKeySortedCollectionInterface{
    public FeKeyPlaceholder(){
    }

    public boolean insertSingleKey(T key){
	return true;
    }

    public int numKeys(){
	return 0;
    }

    public Interator<T> iterator(){
	return new Iterator<T>();
    }

    public void setIterationStartPoint(Comparable<T> startPoint){

    }
}
