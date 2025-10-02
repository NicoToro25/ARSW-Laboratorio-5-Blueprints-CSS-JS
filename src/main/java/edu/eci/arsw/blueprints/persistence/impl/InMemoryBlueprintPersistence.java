/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author NicoToro
 */

@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    // 3 planos y dos asociados al mismo autor
    public InMemoryBlueprintPersistence(){

        Blueprint bp1 = new Blueprint( "Nicolas", "plano1", new Point[]{new Point(0, 0), new Point(10, 10)});
        Blueprint bp2 = new Blueprint( "Nicolas", "plano2", new Point[]{new Point(1, 1), new Point(11, 11)});
        Blueprint bp3 = new Blueprint( "Toro", "plano3", new Point[]{new Point(2, 2), new Point(12, 12)});

        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> result=new HashSet<>();
        for (Map.Entry <Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                result.add(entry.getValue());
            }
        }
        if (result.isEmpty()) {
            throw new BlueprintNotFoundException("No se encontraron resultados para " + author);
        }
        return result;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        return new HashSet<>(blueprints.values());
    }
}
