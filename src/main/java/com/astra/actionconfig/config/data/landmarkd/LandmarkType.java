package com.astra.actionconfig.config.data.landmarkd;

import com.astra.actionconfig.config.data.Point3F;

import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum LandmarkType {
    Nose,
    LeftEyeOuter, LeftEye, LeftEyeInner,
    RightEyeOuter, RightEye, RightEyeInner,
    LeftEar, RightEar,
    MouthLeft, MouthRight,
    LeftShoulder, RightShoulder,
    LeftHip, RightHip,
    LeftElbow, RightElbow,
    LeftWrist, RightWrist,
    LeftThumb, RightThumb,
    LeftIndexFinger, RightIndexFinger,
    LeftPinkyFinger, RightPinkyFinger,
    LeftKnee, RightKnee,
    LeftAnkle, RightAnkle,
    LeftHeel, RightHeel,
    LeftToe, RightToe,
    None;

    public Landmark landmark(Map<LandmarkType, Point3F> poseMap) {
        Point3F point = poseMap.get(this);
        return new Landmark(this, point);
    }

    public static LandmarkType indexToLandmarkType(int index) {
        switch (index) {
            case 0:
                return LandmarkType.Nose;
            case 1:
                return LandmarkType.RightEyeInner;
            case 2:
                return LandmarkType.RightEye;
            case 3:
                return LandmarkType.RightEyeOuter;
            case 4:
                return LandmarkType.LeftEyeInner;
            case 5:
                return LandmarkType.LeftEye;
            case 6:
                return LandmarkType.LeftEyeOuter;
            case 7:
                return LandmarkType.RightEar;
            case 8:
                return LandmarkType.LeftEar;
            case 9:
                return LandmarkType.MouthRight;
            case 10:
                return LandmarkType.MouthLeft;
            case 11:
                return LandmarkType.RightShoulder;
            case 12:
                return LandmarkType.LeftShoulder;
            case 13:
                return LandmarkType.RightElbow;
            case 14:
                return LandmarkType.LeftElbow;
            case 15:
                return LandmarkType.RightWrist;
            case 16:
                return LandmarkType.LeftWrist;
            case 17:
                return LandmarkType.RightPinkyFinger;
            case 18:
                return LandmarkType.LeftPinkyFinger;
            case 19:
                return LandmarkType.RightIndexFinger;
            case 20:
                return LandmarkType.LeftIndexFinger;
            case 21:
                return LandmarkType.RightThumb;
            case 22:
                return LandmarkType.LeftThumb;
            case 23:
                return LandmarkType.RightHip;

            case 24:
                return LandmarkType.LeftHip;
            case 25:
                return LandmarkType.RightKnee;
            case 26:
                return LandmarkType.LeftKnee;
            case 27:
                return LandmarkType.RightAnkle;
            case 28:
                return LandmarkType.LeftAnkle;
            case 29:
                return LandmarkType.RightHeel;
            case 30:
                return LandmarkType.LeftHeel;
            case 31:
                return LandmarkType.RightToe;
            case 32:
                return LandmarkType.LeftToe;
            default:
                return LandmarkType.None;
        }
    }


    public static int landmarkTypeToIndex(LandmarkType landmarkType) {
        switch (landmarkType) {
            case Nose:
                return 0;
            case RightEyeInner:
                return 1;
            case RightEye:
                return 2;
            case RightEyeOuter:
                return 3;
            case LeftEyeInner:
                return 4;
            case LeftEye:
                return 5;
            case LeftEyeOuter:
                return 6;
            case RightEar:
                return 7;
            case LeftEar:
                return 8;
            case MouthRight:
                return 9;
            case MouthLeft:
                return 10;
            case RightShoulder:
                return 11;
            case LeftShoulder:
                return 12;
            case RightElbow:
                return 13;
            case LeftElbow:
                return 14;
            case RightWrist:
                return 15;
            case LeftWrist:
                return 16;
            case RightPinkyFinger:
                return 17;
            case LeftPinkyFinger:
                return 18;
            case RightIndexFinger:
                return 19;
            case LeftIndexFinger:
                return 20;
            case RightThumb:
                return 21;
            case LeftThumb:
                return 22;
            case RightHip:
                return 23;

            case LeftHip:
                return 24;
            case RightKnee:
                return 25;
            case LeftKnee:
                return 26;
            case RightAnkle:
                return 27;
            case LeftAnkle:
                return 28;
            case RightHeel:
                return 29;
            case LeftHeel:
                return 30;
            case RightToe:
                return 31;
            case LeftToe:
                return 32;
            default:
                return 33;
        }
    }

    public static Map<LandmarkType, Point3F> poseMap(List<Map<String,Double>> landmarks) {
        Map<LandmarkType, Point3F> poseMap = new EnumMap<LandmarkType, Point3F>(LandmarkType.class);
        for (int i = 0; i < landmarks.size(); i++) {
            LandmarkType landmarkType = indexToLandmarkType(i);
            poseMap.put(landmarkType, new Point3F(landmarks.get(i).get("x"),
                    landmarks.get(i).get("y"),
                    landmarks.get(i).get("z")));
            if (landmarkType == LandmarkType.LeftShoulder || landmarkType == LandmarkType.RightShoulder) {
                System.out.println(String.format("landmark %s -> %s/%s", landmarkType, landmarks.get(i).get("x"),landmarks.get(i).get("y")));
            }
        }

        return poseMap;

    }
}
