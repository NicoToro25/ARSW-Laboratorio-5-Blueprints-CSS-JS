package edu.eci.arsw.blueprints.controllers;


import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices services;

    @GetMapping
    public ResponseEntity<?> getAllBlueprints() {
        try {
            Set<Blueprint> data = services.getAllBlueprints();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error al obtener los planos.",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable("author") String author) {
        try {
            Set<Blueprint> blueprints = services.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Autor no encontrado.",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprintsByAuthorAndName(@PathVariable("author") String author, @PathVariable("name") String name) {
        try {
            Blueprint blueprints = services.getBlueprint(author, name);
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Autor no encontrado.",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addBlueprint(@RequestBody Blueprint blueprint) {
        try {
            services.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el plano.",HttpStatus.FORBIDDEN);
        }
    }
}
