package com.jeevitharoyapathi.assignment1.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Video implements Serializable {

    @JsonProperty("id")
    private String mId;

    @JsonProperty("key")
    private String mKey;

    @JsonProperty("name")
    private String mName;

    @JsonProperty("site")
    private String mSite;

    @JsonProperty("size")
    private int mSize;

    @JsonProperty("type")
    private String mType;

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(final String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(final String site) {
        mSite = site;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(final int size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(final String type) {
        mType = type;
    }

    Video() {}

    @JsonIgnore
    public Video(JSONObject jsonObject) {
        try {
            setId(jsonObject.getString("id"));
            setKey(jsonObject.getString("key"));
            setName(jsonObject.getString("name"));
            setSite(jsonObject.getString("site"));
            setSize(jsonObject.getInt("size"));
            setType(jsonObject.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JsonIgnore
    public static ArrayList<Video> fromJSONArray(JSONArray array) {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Video> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Video(array.getJSONObject(x)));
                //results.add(mapper.readValue(array.getJSONObject(x).toString(),Movie.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}