package naifcanbasci.util;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class ArrayMap<Key, Value> {

	private Array<Key> keys = new Array<Key>();
	private Array<Value> values = new Array<Value>();

	public synchronized Value put(Key k, Value v) {
		if (keys.contains(k)) {
			int i = keys.indexOf(k);
			values.set(i, v);
			return v;
		}
		keys.put(k);
		values.put(v);
		return v;
	}

	public synchronized Value put(Key k, Value v, Value d) {
		if (v == null)
			return put(k, d);
		return put(k, v);
	}
	
	public synchronized void putAll(ArrayMap<Key, Value> map) {
		for(Key k: map.keys)
			put(k, map.get(k));
	}

	public synchronized Value puts(Key k, Value v) {
		if (v == null) return null;
		return put(k, v);
	}

	public synchronized Value get(Key k) {
		return opt(k, null);
	}

	public synchronized Value opt(Key k, Value d) {
		if (keys.contains(k))
			return values.get(keys.indexOf(k));
		else
			return d;
	}

	public synchronized void forEach(final BiConsumer<Key, Value> con) {
		for(Key k: keys)
			con.accept(k, get(k));
	}

	@Override
	public synchronized ArrayMap<Key, Value> clone() {
		ArrayMap<Key, Value> map = new ArrayMap<Key, Value>();
		map.keys = keys.clone();
		map.values = values.clone();
		return map;
	}

	public synchronized Array<Entry<Key, Value>> entries() {
		final Array<Entry<Key, Value>> arr = new Array<Entry<Key, Value>>();
		forEach(new BiConsumer<Key, Value>() {

				@Override
				public void accept(Key k, Value v) {
					arr.put(new Entry<Key,Value>(k, v));
				}
			});
		return arr;
	}

	@Override
	public synchronized String toString() {
		String str = "{";
		for (int i=0; i < keys.size(); i++) {
			Key k = keys.get(i);
			Value v = values.get(i);
			if (k instanceof String) {
				str += String.format("\"%s\": ", k.toString());
			} else {
				str += k.toString();
			}
			if (v instanceof String) {
				str += String.format("\"%s\"", v.toString());
			} else {
				str += v.toString();
			}
			if (i + 1 != keys.size())
				str += ",\n ";
		}
		str += "}";
		return str;
	}

	public Array<Key> keySet() {
		return keys.clone();
	}

	public Array<Value> values() {
		return values.clone();
	}

	public class Entry<Key, Value> {
		public final Key k;
		public final Value v;
		
		public Entry(Key k, Value v) {
			this.k = k;
			this.v = v;
		}
	}
}
