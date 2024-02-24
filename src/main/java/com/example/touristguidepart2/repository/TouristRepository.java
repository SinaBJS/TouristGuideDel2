package com.example.touristguidepart2.repository;

import com.example.touristguidepart2.model.TagEnum;
import com.example.touristguidepart2.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TouristRepository {

    private List<TouristAttraction> touristAttractions;
    private List<String> predefinedCities = Arrays.asList("Copenhagen", "Aarhus", "Aalborg", "Odense");

    public List<String> getPredefinedCities() {
        return predefinedCities;
    }

    public List<TagEnum> getPredefinedTags() {
        return Arrays.asList(TagEnum.values());
    }

    public TouristRepository() {
        touristAttractions = new ArrayList<>();

        touristAttractions.add(new TouristAttraction("The Little Mermaid", "Iconic danish landmark from the tales of Hans Christian Andersen", "Copenhagen", List.of("Landmark", "Free")));

        touristAttractions.add(new TouristAttraction("Tivoli Gardens", "One of the oldest amusement parks in the world, based in the center of Copenhagen", "Copenhagen", List.of("Amusement park", "Admisssion Price")));
    }

    // Create
    public void addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttractions.add(touristAttraction);
    }

    // Read
    public List<TouristAttraction> getAllTouristAttractions() {
        return touristAttractions;
    }

    public TouristAttraction getTouristAttractionByName(String name) {
        for (TouristAttraction attraction : touristAttractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    // Update
    public void updateTouristAttraction(String name, TouristAttraction updatedAttraction) {
        for (int i = 0; i < touristAttractions.size(); i++) {
            if (touristAttractions.get(i).getName().equalsIgnoreCase(name)) {
                touristAttractions.set(i, updatedAttraction);
                return;
            }
        }
    }

    // Delete
    public void deleteTouristAttraction(String name) {
        touristAttractions.removeIf(attraction -> attraction.getName().equalsIgnoreCase(name));
    }

}
