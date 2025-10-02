package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;

import java.util.Set;

public interface BlueprintsFilter {
    public Blueprint filter(Blueprint bp);
}
