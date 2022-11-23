package com.astra.actionconfig.config.ruler.landmarkd;

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

        if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.X)) {
            range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.Y)) {
            range.contains(fromSegment.distanceXWithDirection() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.XY)) {
            range.contains(fromSegment.distanceXWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.X)) {
            range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.Y)) {
            range.contains(fromSegment.distanceYWithDirection() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.XY)) {
            range.contains(fromSegment.distanceYWithDirection() / toSegment.distance());

        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.X)) {
            range.contains(fromSegment.distance() / toSegment.distanceX());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.Y)) {
            range.contains(fromSegment.distance() / toSegment.distanceY());
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.XY)) {
            range.contains(fromSegment.distance() / toSegment.distance());
        }


        return false;
    }
}
