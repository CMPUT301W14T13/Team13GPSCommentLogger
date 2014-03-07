package com.CMPUT301W14T13.gpscommentlogger.model;

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
