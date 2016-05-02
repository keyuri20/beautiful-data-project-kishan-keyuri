package cs594Homework2;

import java.util.Collection;

import org.json.simple.JSONObject;

/**
 * Interface to define collector behavior
 *
 * It should be able to download data from source and save data.
 */
//@SuppressWarnings("hiding")
public interface Collector<ModelClass, JSONObject> {
    /**
     * Mungee method is to clean data. e.g. remove data rows with errors
     */
    //Collection<T> mungee(Collection<R> src);
	Collection<ModelClass> mungee(JSONObject json);

    void save(Collection<ModelClass> data);
}