/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }


    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Blueprint bp1 = new Blueprint("lilo", "plano1", new Point[]{new Point(1, 1), new Point(10, 10)});
        Blueprint bp2 = new Blueprint("lilo", "plano2", new Point[]{new Point(2, 2), new Point(20, 20)});
        Blueprint bp3 = new Blueprint("isa", "plano3", new Point[]{new Point(3, 3), new Point(30, 30)});

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        ibpp.saveBlueprint(bp3);

        Set<Blueprint> liloPlans = ibpp.getBlueprintsByAuthor("lilo");
        assertEquals("lilo should have 2 blueprints", 2, liloPlans.size());

        Set<Blueprint> isaPlans = ibpp.getBlueprintsByAuthor("isa");
        assertEquals("isa should have 1 blueprints", 1, isaPlans.size());
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void getBlueprintsByAuthorNotFoundTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        ibpp.getBlueprintsByAuthor("Nicolas");
    }

    @Test
    public void getAllBlueprintsTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Blueprint bp1 = new Blueprint("isa", "plano1", new Point[]{new Point(1, 1), new Point(10, 10)});
        Blueprint bp2 = new Blueprint("nicolas", "plano1", new Point[]{new Point(2, 2), new Point(20, 20)});
        Blueprint bp3 = new Blueprint("bella", "plano1", new Point[]{new Point(3, 3), new Point(30, 30)});

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        ibpp.saveBlueprint(bp3);

        Set<Blueprint> all = ibpp.getAllBlueprints();
        assertTrue("All blueprints should contain bp1", all.contains(bp1));
        assertTrue("All blueprints should contain bp2", all.contains(bp2));
        assertTrue("All blueprints should contain bp3", all.contains(bp3));
    }
}
