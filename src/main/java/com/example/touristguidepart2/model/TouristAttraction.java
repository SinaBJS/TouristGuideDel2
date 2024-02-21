package com.example.touristguidepart2.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {

    private String name;

    private String description;
    private String city;
    private List<String> tags;

    public TouristAttraction(String name, String description, String city, List<String> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags(){
        return tags;
    }
    public void addTag(String tag){
        this.tags.add(tag);
    }
    public void removeTag(String tag){
        this.tags.remove(tag);
    }

}
