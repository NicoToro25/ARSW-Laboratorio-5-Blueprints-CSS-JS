package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RedundancyFilter implements BlueprintsFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filtered = new ArrayList<>();
        Point prevPoint = null;

        for (Point point : bp.getPoints()) {
            if (prevPoint == null || !(point.getX() == prevPoint.getX() && point.getY() == prevPoint.getY())) {
                filtered.add(point);
            }
            prevPoint = point;
        }

        return new Blueprint(bp.getAuthor(), bp.getName(), filtered.toArray(new Point[0]));
    }

}
