package com.example.touristguidepart2.service;

import com.example.touristguidepart2.model.TagEnum;
import com.example.touristguidepart2.model.TouristAttraction;
import com.example.touristguidepart2.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//test...
@Service
public class TouristService {

    private TouristRepository touristRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }


    // Create
    public void addTouristAttraction(TouristAttraction touristAttraction) {
        touristRepository.addTouristAttraction(touristAttraction);
    }

    // Read
    public List<TouristAttraction> getAllTouristAttractions() {
        return touristRepository.getAllTouristAttractions();
    }

    public TouristAttraction getTouristAttractionByName(String name) {
        return touristRepository.getTouristAttractionByName(name);
    }

    // Update
    public void updateTouristAttraction(String name, TouristAttraction updatedAttraction) {
        touristRepository.updateTouristAttraction(name, updatedAttraction);
    }

    // Delete
    public void deleteTouristAttraction(String name) {
        touristRepository.deleteTouristAttraction(name);
    }

    public List<String> getPredefinedCities() {
        return touristRepository.getPredefinedCities();
    }

    public List<TagEnum> getPredefinedTags() {
        return touristRepository.getPredefinedTags();
    }
}
