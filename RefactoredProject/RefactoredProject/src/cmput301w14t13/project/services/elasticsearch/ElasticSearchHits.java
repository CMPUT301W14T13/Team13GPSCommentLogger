package cmput301w14t13.project.services.elasticsearch;

import java.util.Collection;

/**
 * Holder for elastic search results.
 * @param  <T >
 */
public class ElasticSearchHits<T> {
	
    int total;
    double max_score;

    Collection<ElasticSearchResponse<T>> hits;
    
    /**
	 * Return elastic search results
	 * @return  Collection<ElasticSearchResponse<T>>
	 * 
	 */
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}
