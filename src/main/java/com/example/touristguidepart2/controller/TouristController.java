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
import org.springframework.web.util.UriComponentsBuilder;

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
/*
    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.getTouristAttractionByName(name);
        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
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
        return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@RequestParam("name") String name,
                                 @RequestParam("city") String city,
                                 @RequestParam("description") String description,
                                 @RequestParam("tags") List<String> tags) {

        TouristAttraction newAttraction = new TouristAttraction(name, description, city, tags);

        touristService.addTouristAttraction(newAttraction);

        return "redirect:/attractions";
    }


    @GetMapping("/{name}/edit")
    public String showEditAttractionForm(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getTouristAttractionByName(name);

        List<String> cities = touristService.getPredefinedCities();
        List<TagEnum> tags = touristService.getPredefinedTags();
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);


        model.addAttribute("attraction", attraction);
        model.addAttribute("originalName", name);


        return "editAttraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute("attraction") TouristAttraction updatedAttraction,
                                   @RequestParam("originalName") String originalName) {

        touristService.updateTouristAttraction(originalName, updatedAttraction);

        return "redirect:/attractions";
    }


    @GetMapping("/{name}/delete")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name) {
        TouristAttraction attractionToDelete = touristService.getTouristAttractionByName(name);
        if (attractionToDelete != null) {
            touristService.deleteTouristAttraction(name);
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(UriComponentsBuilder.fromPath("/attractions").build().toUri())
                    .build();
        } else {
            return new ResponseEntity<>(name + " not found", HttpStatus.NOT_FOUND);
        }
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
