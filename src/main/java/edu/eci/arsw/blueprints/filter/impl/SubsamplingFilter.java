package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubsamplingFilter implements BlueprintsFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filtered = new ArrayList<>();
        List<Point> points = bp.getPoints();

        for (int i = 0; i < points.size(); i+=2 ) {
            filtered.add(points.get(i));
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered.toArray(new Point[0]));
    }
}
