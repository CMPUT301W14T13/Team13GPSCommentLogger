package cmput301w14t13.project.services.elasticsearch;

import java.util.ArrayList;
import java.util.Collection;

//Taken From https://github.com/rayzhangcl/ESDemo/tree/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei

/**
 * Holder for the servers response to an elastic search operation
 * 
 * @param  <T >
 */
public class ElasticSearchSearchResponse<T> {
    int took;
    boolean timed_out;
    transient Object _shards;
 
    ElasticSearchHits<T> hits;
    boolean exists;    
   
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits.getHits();        
    }
    public Collection<T> getSources() {
        Collection<T> out = new ArrayList<T>();
        for (ElasticSearchResponse<T> essrt : getHits()) {
            out.add( essrt.getSource() );
        }
        return out;
    }
    public String toString() {
        return (super.toString() + ":" + took + "," + _shards + "," + exists + ","  + hits);     
    }
}
