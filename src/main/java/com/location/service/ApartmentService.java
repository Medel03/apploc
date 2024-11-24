package com.location.service;

import com.location.model.Apartment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentService {

    public List<Apartment> findApartments(String quartier, String type, Integer budget) {
        // Liste d'exemples d'appartements
        List<Apartment> apartments = new ArrayList<>();
        apartments.add(new Apartment("Appartement", "Zohour", 2500));
        apartments.add(new Apartment("Studio", "Narjiss", 1500));
        apartments.add(new Apartment("Appartement", "Route Ain Chkef", 3000));
        apartments.add(new Apartment("Studio", "Zohour", 1200));
        apartments.add(new Apartment("Appartement", "Narjiss", 3200));

        // Appliquer les filtres si définis
        if (quartier != null && !quartier.isEmpty()) {
            apartments.removeIf(apartment -> !apartment.getQuartier().equals(quartier));
        }

        if (type != null && !type.isEmpty()) {
            apartments.removeIf(apartment -> !apartment.getType().equals(type));
        }

        if (budget != null) {
            apartments.removeIf(apartment -> apartment.getPrice() > budget);
        }

        return apartments;
    }
}


