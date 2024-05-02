import java.util.ArrayList;
import java.util.Iterator;

public class IterableMultiKeySortedCollection<T extends Comparable<T>> implements IterableMultiKeySortedCollectionInterface<T> {

    public ArrayList<T> list;
	public Iterator<T> iterator;
	public int size;

    public IterableMultiKeySortedCollection(){
		this.list = new ArrayList<T>();
		this.size = 0;
	}

    @Override
    public boolean insert(KeyListInterface<T> data) {
        return true; 
    }

    @Override
    public boolean contains(Comparable<KeyListInterface<T>> data) {
        if(list.contains(data)) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if(size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        this.list = new ArrayList<T>();
    }

    @Override
    public boolean insertSingleKey(T key) {
        list.add(key);

		if(list.contains(key)) {
			size++;
			return true;	
		}
		
		return false;
    }

    @Override
    public int numKeys() {
        return list.size(); 
    }

    @Override
    public Iterator<T> iterator() {
        this.iterator = list.iterator();
		return iterator;
    }

    // @Override
    // public void setIterationStartPoint(Comparable<T> startPoint) {
    //     this.iterator = list.listIterator(list.indexOf(startPoint));
    // }

    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.iterator = list.listIterator(list.indexOf(startPoint));
    }
    
}
