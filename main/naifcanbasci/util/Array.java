package naifcanbasci.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class Array<Value> implements Iterable {

	private static Object EMPTY = new Object();

	private Object[] arr = new Object[0];

	@Override
	public synchronized Iterator<Value> iterator() {
		return new Iterator<Value>() {
			
			int pos;
			Object[] ar = arr.clone();

			@Override
			public boolean hasNext() {
				return pos < ar.length;
			}

			@Override
			public Value next() {
				return (Value)ar[pos++];
			}
		};
	}

	@Override
	public synchronized Spliterator spliterator() {
		return null;
	}

	public synchronized Object[] toArray() {
		return arr.clone();
	}

	public synchronized boolean isEmpty() {
		return arr.length == 0;
	}

	public synchronized boolean put(Value v) {
		Object[] narr = new Object[arr.length + 1];
		for(int i=0; i < arr.length; i++)
			narr[i] = arr[i];
		narr[arr.length] = v;
		arr = narr;
		return true;
	}

	public synchronized Value set(int i, Value v) {
		return (Value)(arr[i] = v);
	}

	public synchronized Value get(int index) {
		return (Value)arr[index];
	}

	public synchronized Value remove(int index) {
		Value oo = (Value)arr[index];
		arr[index] = EMPTY;
		Object[] narr = new Object[arr.length - 1];
		int ctr = 0;
		for(Object o: arr)
			if(o != EMPTY)
				narr[ctr++] = o;
		return oo;
	}

	public synchronized Value remove(Value v) {
		return remove(indexOf(v));
	}

	public synchronized boolean contains(Value v) {
		for(Object o: arr)
			if(o.equals(v))
				return true;
		return false;
	}

	public synchronized int indexOf(Value v) {
		for(int i=0; i < arr.length; i++)
			if(arr[i] == v || arr[i].equals(v))
				return i;
		return -1;
	}

	public synchronized int size() {
		return arr.length;
	}

	@Override
	public synchronized Array<Value> clone() {
		Array<Value> a = new Array<Value>();
		a.arr = arr.clone();
		return a;
	}

	@Override
	public synchronized String toString() {
		String str = "[";
		for(int i=0; i < arr.length; i++) {
			Object v = arr[i];
			if(v instanceof String)
				str += String.format("\"%s\"", v);
			else
				str += v;
			if(i + 1 != size())
				str += ",\n ";
		}
		return str + "]";
	}

}
