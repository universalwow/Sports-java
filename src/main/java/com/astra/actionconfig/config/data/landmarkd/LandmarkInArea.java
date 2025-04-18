package com.astra.actionconfig.config.data.landmarkd;

import com.alibaba.fastjson2.JSON;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.ruler.UniPolygon;
import lombok.Data;

import java.awt.geom.Area;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class LandmarkInArea {
    public String id;
    public Point2F[] area = new Point2F[4];
    public String areaId;
    public Point2F imageSize; // x=1920,y=1080
    public Landmark landmark;
    public Warning warning;


    public Point2F[] path(Point2F frameSize) {
//        GeneralPath path = new GeneralPath();
        Point2F[] area = new Point2F[4];

        if (imageSize.x == frameSize.x && imageSize.y == frameSize.y) {
            area = this.area.clone();
        } else {
            area =
                    Arrays.stream(this.area).map(point ->
                        new Point2F(
                                point.x/imageSize.x * frameSize.x,
                                point.y/imageSize.y * frameSize.y
                        )
                    ).collect(Collectors.toList()).toArray(new Point2F[this.area.length]);

        }
//        for (int i = 0; i < area.length; i++) {
//            if (i == 0) {
//                path.moveTo(area[i].x, area[i].y);
//            } else {
//                path.lineTo(area[i].x, area[i].y);
//            }
//        }
//
//        path.lineTo(area[0].x, area[0].y);
//        path.closePath();
//        return path;
        return area;

    }

    public boolean satisfy(Map<LandmarkType, Point3F> poseMap, Point2F frameSize) {
        Point3F landmarkPoint = poseMap.get(landmark.landmarkType);

        if (landmarkPoint.isEmpty()) {
            return false;
        }

        Point2F[] path = this.path(frameSize);
        System.out.println(Arrays.stream(path).map(Object::toString).toArray(String[]::new));

        System.out.println(String.format("landmark type %s  %s %s/%s - %s",
                JSON.toJSONString(path),
                landmark.landmarkType.toString(), landmarkPoint.x, landmarkPoint.y, UniPolygon.pointInPolygon(path,  landmarkPoint.point2F())));
        return UniPolygon.pointInPolygon(path, landmarkPoint.point2F());
//        return path.contains(landmarkPoint.x, landmarkPoint.y);
    }
}
