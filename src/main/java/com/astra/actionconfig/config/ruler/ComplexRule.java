package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.landmarkd.CoordinateAxis;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import org.apache.commons.lang3.Range;

public class ComplexRule {
    public static boolean satisfyWithDirection(
            CoordinateAxis fromAxis,
            CoordinateAxis toAxis,
            Range<Double> range,
            LandmarkSegment fromSegment,
            LandmarkSegment toSegment
    ) {
        boolean satisfy = false;
        if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.X)) {
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.Y)) {
            System.out.println(String.format("distance %s/%s -> %s/%s", range.getMinimum(), range.getMaximum(), fromSegment.distanceXWithDirection(), toSegment.distanceY()));
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.X)) {
            System.out.println(String.format("distance %s/%s -> %s/%s", range.getMinimum(), range.getMaximum(), fromSegment.distanceYWithDirection(), toSegment.distanceX()));
            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.Y)) {
            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.X)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.Y)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distance());
        }


        return satisfy;
    }

    public static boolean satisfyWithDirection2(
            CoordinateAxis fromAxis,
            CoordinateAxis toAxis,
            Range<Double> range,
            LandmarkSegment fromSegment,
            LandmarkSegment toSegment
    ) {
        boolean satisfy = false;
        if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.X)) {
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceXWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.Y)) {
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceYWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distanceXWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.X)) {

            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceXWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.Y)) {
            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceYWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distanceYWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.X)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distanceXWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.Y)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distanceYWithDirection());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.XY)) {
            satisfy = range.contains(fromSegment.distance() / toSegment.distance());
        }


        return satisfy;
    }

    public static boolean satisfyWithDirectionToObject(CoordinateAxis fromAxis,
                                               Range<Double> range,
                                               LandmarkSegment fromSegment, double relativeTo) {

        switch (fromAxis) {

            case X:
                return range.contains(
                  fromSegment.distanceXWithDirection()/relativeTo
                );

            case Y:

                return range.contains(fromSegment.distanceYWithDirection()/relativeTo);
            case XY:

                return  range.contains(fromSegment.distance()/relativeTo);
        }

        return false;
    }
}
