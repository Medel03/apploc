package com.location.controller;

import com.location.model.Apartment;
import com.location.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    // Afficher la page de recherche
    @GetMapping("/")
    public String showSearchForm(Model model) {
        return "index";  // index.html
    }

    // Traiter le formulaire de recherche et afficher les résultats
    @PostMapping("/search")
    public String searchApartments(@RequestParam(value = "quartier", required = false) String quartier,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "budget", required = false) Integer budget,
                                   Model model) {
        List<Apartment> apartments = apartmentService.findApartments(quartier, type, budget);
        model.addAttribute("apartments", apartments);
        return "index";  // Retourne à index.html avec les résultats
    }
}

