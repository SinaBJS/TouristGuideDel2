package com.example.touristguidepart2.controller;

import com.example.touristguidepart2.model.TagEnum;
import com.example.touristguidepart2.model.TouristAttraction;
import com.example.touristguidepart2.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllTouristAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.getTouristAttractionByName(name);
        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /* @PostMapping("/add")
    public ResponseEntity<String> addAttraction(@RequestBody TouristAttraction touristAttraction) {
        touristService.addTouristAttraction(touristAttraction);
        return new ResponseEntity<>("Attraction has been added!" , HttpStatus.CREATED);
    }*/

    @GetMapping("/add")
    public String showAddAttractionForm(Model model) {
        List<String> cities = touristService.getPredefinedCities();
        List<TagEnum> tags = touristService.getPredefinedTags();
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);
        model.addAttribute("attraction", new TouristAttraction("", "", "", null));
        return "addAttractionForm";
    }

    @PostMapping("/save")
    public String saveAttraction(@RequestParam("name") String name,
                                 @RequestParam("city") String city,
                                 @RequestParam("description") String description,
                                 @RequestParam("tags") List<String> tags) {
        // Create a new TouristAttraction object with the submitted data
        TouristAttraction newAttraction = new TouristAttraction(name, description, city, tags);

        // Add the new attraction to the repository
        touristService.addTouristAttraction(newAttraction);

        // Redirect to the attractions page or any other appropriate page
        return "redirect:/attractions";
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateAttraction(@RequestParam String name, @RequestBody TouristAttraction updatedAttraction) {
        if (touristService.getTouristAttractionByName(name) == null) {
            return new ResponseEntity<>(name + " could not be found", HttpStatus.NOT_FOUND);
        }
        touristService.updateTouristAttraction(name, updatedAttraction);
        return new ResponseEntity<>(name + " has been updated", HttpStatus.OK);
    }

    @GetMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name) {
        if (touristService.getTouristAttractionByName(name) == null) {
            return new ResponseEntity<>(name + " could not be found", HttpStatus.NOT_FOUND);
        }
        touristService.deleteTouristAttraction(name);
        touristService.getAllTouristAttractions().forEach(System.out::println);
        return new ResponseEntity<>(name + " has been deleted", HttpStatus.OK);
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getTouristAttractionByName(name);
        if (attraction != null) {
            model.addAttribute("tags", attraction.getTags());
            model.addAttribute("attractionName", name);
        }
        return "tags";
    }
}
