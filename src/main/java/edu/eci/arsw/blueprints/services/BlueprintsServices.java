/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    BlueprintsFilter filter = null;
    
    public void addNewBlueprint(Blueprint bp){
        try {
            bpp.saveBlueprint(bp);
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el blueprint" + e.getMessage(), e);
        }
    }

    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpp.getAllBlueprints();
        // aplicar el filtro a cada uno
        Set<Blueprint> filtered = new java.util.HashSet<>();
        for (Blueprint bp : blueprints) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = bpp.getBlueprint(author, name);
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint no encontrado: " + author + " - " + name);
        }
        return filter.filter(bp);
    }

    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
        Set<Blueprint> filtered = new java.util.HashSet<>();
        for (Blueprint bp : blueprints) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }

}
