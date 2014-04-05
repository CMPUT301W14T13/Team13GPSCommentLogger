package cmput301w14t13.project.services.elasticsearch;


/**
 * Object to hold and organize
 * one elastic search result
 *
 * @param <T>
 */
public class ElasticSearchResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;
    
    public T getSource() {
        return _source;
    }
    
    public String getESID() {
        return _id;
    }
}
