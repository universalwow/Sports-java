package com.astra.actionconfig.config.ruler;

import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.landmarkd.CoordinateAxis;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.ruler.landmarkd.SatisfyScore;
import com.astra.actionconfig.config.ruler.observationitem.ExtremeDirection;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.Range;

public class ComplexRule {

    public static Landmark initLandmark(boolean isRelativeToExtremeDirection, ExtremeDirection extremeDirection, Landmark fromLandmark,
                                        StateTime toStateTime) {

        Landmark toLandmark = new Landmark(fromLandmark.landmarkType, new Point3F(0.,0.,0.));

        if (isRelativeToExtremeDirection) {
            switch (extremeDirection) {
                case MinX:
                    toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX;
                    break;
                case MinY:
                    toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY;

                    break;
                case MaxX:

                    toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX;

                    break;
                case MaxY:
                    toLandmark.position = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY;
                    System.out.println(String.format("max y %s/%s", toLandmark.position.x, toLandmark.position.y));
                    break;
                case MinX_MinY:
                    toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX.x;
                    toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY.y;

                    break;
                case MinX_MaxY:
                    toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minX.x;
                    toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY.y;

                    break;
                case MaxX_MinY:
                    toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX.x;
                    toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).minY.y;

                    break;
                case MaxX_MaxY:
                    toLandmark.position.x = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxX.x;
                    toLandmark.position.y = toStateTime.dynamicPoseMaps.get(toLandmark.landmarkType).maxY.y;

                    break;
            }

        }else {
            toLandmark = toLandmark.landmarkType.landmark(toStateTime.poseMap);
        }

        return toLandmark;
    }

    public static LandmarkSegment initLandmarkSegment(boolean isRelativeToExtremeDirection, ExtremeDirection extremeDirection,
                                                      LandmarkSegment fromLandmarkSegment, StateTime toStateTime) {
        LandmarkSegment toSegment = new LandmarkSegment(
                new Landmark(fromLandmarkSegment.startLandmark.landmarkType, new Point3F(0.,0.,0.)),
                new Landmark(fromLandmarkSegment.endLandmark.landmarkType, new Point3F(0.,0.,0.))
        );


        if (isRelativeToExtremeDirection) {
            switch (extremeDirection) {

                case MinX:
                    toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minX;
                    toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minX;

                    break;
                case MinY:

                    toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minY;
                    toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minY;

                    break;
                case MaxX:
                    toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxX;
                    toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxX;

                    break;
                case MaxY:
                    toSegment.startLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxY;
                    toSegment.endLandmark.position = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxY;

                    break;
                case MinX_MinY:

                    toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minX.x;
                    toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minX.x;

                    toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minY.y;
                    toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minY.y;

                    break;
                case MinX_MaxY:

                    toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minX.x;
                    toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minX.x;

                    toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxY.y;
                    toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxY.y;

                    break;
                case MaxX_MinY:
                    toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxX.x;
                    toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxX.x;

                    toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).minY.y;
                    toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).minY.y;

                    break;
                case MaxX_MaxY:
                    toSegment.startLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxX.x;
                    toSegment.endLandmark.position.x = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxX.x;

                    toSegment.startLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.startLandmark.landmarkType).maxY.y;
                    toSegment.endLandmark.position.y = toStateTime.dynamicPoseMaps.get(toSegment.endLandmark.landmarkType).maxY.y;

                    break;
            }

        }else {
            toSegment = toSegment.landmarkTypeSegment().landmarkSegment(toStateTime.poseMap);
        }

        return toSegment;

    }

    public static double initBound(CoordinateAxis fromAxis, CoordinateAxis toAxis, LandmarkSegment fromSegment, LandmarkSegment toSegment) {
        double ratio = 0.0;
        if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.X)) {
            ratio = fromSegment.distanceXWithDirection() / toSegment.distanceX();
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.Y)) {

            ratio = fromSegment.distanceXWithDirection() / toSegment.distanceY();
        } else if (fromAxis.equals(CoordinateAxis.X) && toAxis.equals(CoordinateAxis.XY)) {
            ratio = fromSegment.distanceXWithDirection() / toSegment.distance();

        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.X)) {
            ratio = fromSegment.distanceYWithDirection() / toSegment.distanceX();
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.Y)) {
            ratio = fromSegment.distanceYWithDirection() / toSegment.distanceY();
        } else if (fromAxis.equals(CoordinateAxis.Y) && toAxis.equals(CoordinateAxis.XY)) {
            ratio = fromSegment.distanceYWithDirection() / toSegment.distance();

        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.X)) {
            ratio = fromSegment.distance() / toSegment.distanceX();
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.Y)) {
            ratio = fromSegment.distance() / toSegment.distanceY();
        } else if (fromAxis.equals(CoordinateAxis.XY) && toAxis.equals(CoordinateAxis.XY)) {
            ratio = fromSegment.distance() / toSegment.distance();
        }
        return ratio;
    }

    public static SatisfyScore satisfyWithDirection(
            CoordinateAxis fromAxis,
            CoordinateAxis toAxis,
            Range<Double> range,
            LandmarkSegment fromSegment,
            LandmarkSegment toSegment
    ) {

        double ratio = initBound(fromAxis, toAxis, fromSegment, toSegment);

        return new SatisfyScore(range.contains(ratio), ratio);
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
