package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.filter.impl.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;


public class FilteredTests {

    @Test
    public void testRedundancyFilter() {
        Blueprint bp1 = new Blueprint("isa", "plano1", new Point[]{new Point(1, 1), new Point(1, 1), new Point(2, 2), new Point(2, 2), new Point(3, 3)});

        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp1);

        assertEquals(3, filtered.getPoints().size());
    }



    @Test
    public void testSubsamplingFilter() {
        Blueprint bp1 = new Blueprint("isa", "plano1", new Point[]{new Point( 0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)});


        SubsamplingFilter sf = new SubsamplingFilter();
        Blueprint filtered = sf.filter(bp1);

        assertEquals(2, filtered.getPoints().size());

        Point p0 = filtered.getPoints().get(0);
        Point p1 = filtered.getPoints().get(1);

        assertTrue(p0.getX() == 0 && p0.getY() == 0);
        assertTrue(p1.getX() == 2 && p1.getY() == 2);
    }
}
