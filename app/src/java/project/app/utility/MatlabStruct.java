package project.app.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiarash on 12/11/16.
 */
public class MatlabStruct {

    public static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<Pair<String, Object>> fields;

    public MatlabStruct() {
        fields = new ArrayList<>();
    }

    public MatlabStruct(String... names) {
        this();
        for (String name : names)
            fields.add(new Pair<>(name, null));
    }

    public MatlabStruct(Pair<String, ?>... pairs) {
        this();
        for (Pair p : pairs)
            fields.add(p);
    }

    public Pair find(String name) {
        // Linear search for the name
        for (int i = 0; i < fields.size(); i ++)
            if (fields.get(i).key.equals(name))
                return fields.get(i);
        return null;
    }

    public <T> T get(String name) {
        Pair p = find(name);
        return (p == null ? null : (T) p.value);
    }

    public void add(String name, Object value){
        Pair p = new Pair(name, value);
        fields.add(p);
    }

    public void set(String name, Object value) {
        Pair p = find(name);
        if (p == null)
            fields.add(new Pair<>(name, value));
        else
            p.value = value;
    }

    public Object[] toArray() {
        Object[] res = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i ++)
            res[i] = fields.get(i).value;
        return res;
    }
}
