package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.Landmark;
import com.astra.actionconfig.config.data.landmarkd.LandmarkSegment;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.utils.ConfigParseUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SportsGround {
    private static SportsGround ground;
    public List<Sporter> sporters = Lists.newArrayList();
    public List<Warning> warnings = Lists.newArrayList();


    public void clearWarnings() {
        warnings.clear();
    }

    public void addSporter(Sport sport) {
        Sporter sporter = new Sporter(
                "uni", sport
        );
        sporters = Lists.newArrayList(sporter);
        clearWarnings();
    }

    public void addSporter(String sportPath) {
        File f = new File(sportPath);
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/2F883470-0223-41E1-B7F4-AD000E5337CE_2.0.json");
        Sport sport = ConfigParseUtil.parseConfig(is, true);
        addSporter(sport);
    }

    public void addSporter() {
        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/2F883470-0223-41E1-B7F4-AD000E5337CE_2.0.json");
        Sport sport = ConfigParseUtil.parseConfig(is, true);
        addSporter(sport);
    }

    public double getLandmarkSegmentAngle(Map<LandmarkType, Point3F> poseMap, LandmarkType startLandmarkType, LandmarkType endLandmarkType) {
        System.out.println(poseMap.get(startLandmarkType));
        System.out.println(poseMap.get(endLandmarkType));
        return new LandmarkSegment(
                new Landmark(startLandmarkType, poseMap.get(startLandmarkType)),
                new Landmark(endLandmarkType, poseMap.get(endLandmarkType))).angle();
    }

    public void play(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {

        warnings.clear();
        for (int i = 0; i < sporters.size(); i++) {
            Sporter sporter = sporters.get(i);

            sporter.play(poseMap, objects, frameSize, currentTime);
            sporter.delayWarnings.forEach(warning -> {
                        if (!warnings.contains(warning)) {
                            warnings.add((Warning) warning);
                        }
                    }
            );

            sporter.noDelayWarnings.forEach(warning -> {
                        if (!warnings.contains(warning)) {
                            warnings.add((Warning) warning);
                        }
                        sporter.noDelayWarnings.remove(warning);
                    }
            );

            System.out.println(String.format("当前状态　%s", sporter.sport.findFirstStateByStateId(sporter.currentStateTime.stateId).get().name));
            System.out.println(String.format("当前提示　%s", warnings.toString()));
        }

    }

    public static void main(String[] args) {
        Map<LandmarkType, Point3F> poseMap = new EnumMap<LandmarkType, Point3F>(LandmarkType.class);
            poseMap.put(LandmarkType.Nose, new Point3F(686.6272735595703, 238.76157760620117, -0.03912345767021179));
            poseMap.put(LandmarkType.LeftEyeOuter, new Point3F(659.375, 232.45521068572998, -0.03165897727012634));
            poseMap.put(LandmarkType.LeftEye, new Point3F(667.3157501220703, 232.24196434020996, -0.03166857361793518));
            poseMap.put(LandmarkType.LeftEyeInner, new Point3F(675.0198364257812, 232.0578145980835, -0.0316640317440033));
            poseMap.put(LandmarkType.RightEyeOuter, new Point3F(718.4258270263672, 232.20696687698364, -0.031892251968383786));
            poseMap.put(LandmarkType.RightEye, new Point3F(709.2417907714844, 232.02734470367432, -0.03187034726142883));
            poseMap.put(LandmarkType.RightEyeInner, new Point3F(700.3069305419922, 231.93829536437988, -0.03186238408088684));
            poseMap.put(LandmarkType.LeftEar, new Point3F(648.3651733398438, 235.75934886932373, 0.011883701384067535));
            poseMap.put(LandmarkType.RightEar, new Point3F(731.5071868896484, 236.08887434005737, 0.010955042392015456));
            poseMap.put(LandmarkType.MouthLeft, new Point3F(673.3121490478516, 246.08246326446533, -0.022937148809432983));
            poseMap.put(LandmarkType.MouthRight, new Point3F(702.9414367675781, 246.44795179367065, -0.023247642815113066));
            poseMap.put(LandmarkType.LeftShoulder, new Point3F(585.4024887084961, 280.5237007141113, 0.03438734710216522));
            poseMap.put(LandmarkType.RightShoulder, new Point3F(785.6078338623047, 274.6688461303711, 0.027945509552955626));
            poseMap.put(LandmarkType.LeftHip, new Point3F(604.4620895385742, 391.6925096511841, 6.959165912121534E-4));
            poseMap.put(LandmarkType.RightHip, new Point3F(737.3560333251953, 393.43302726745605, -7.249287329614163E-4));
            poseMap.put(LandmarkType.LeftElbow, new Point3F(413.64952087402344, 275.20288467407227, 0.04820726215839386));
            poseMap.put(LandmarkType.RightElbow, new Point3F(837.8173065185547, 213.4126853942871, 0.021091482043266295));
            poseMap.put(LandmarkType.LeftWrist, new Point3F(255.6826400756836, 270.6194829940796, 0.024369581043720244));
            poseMap.put(LandmarkType.RightWrist, new Point3F(723.9092254638672, 213.94676685333252, 0.03376128375530243));
            poseMap.put(LandmarkType.LeftThumb, new Point3F(223.33953857421875, 266.1522102355957, 0.017245110869407655));
            poseMap.put(LandmarkType.RightThumb, new Point3F(698.9482116699219, 218.7854290008545, 0.03153007030487061));
            poseMap.put(LandmarkType.LeftIndexFinger, new Point3F(210.18630981445312, 262.2996997833252, 0.0031387142837047575));
            poseMap.put(LandmarkType.RightIndexFinger, new Point3F(687.4372100830078, 216.6647243499756, 0.02733932137489319));
            poseMap.put(LandmarkType.LeftPinkyFinger, new Point3F(212.81307220458984, 264.1553592681885, 0.01644105017185211));
            poseMap.put(LandmarkType.RightPinkyFinger, new Point3F(686.3173675537109, 213.717942237854, 0.027665698528289796));
            poseMap.put(LandmarkType.LeftKnee, new Point3F(546.5398406982422, 492.6236915588379, -0.01459345817565918));
            poseMap.put(LandmarkType.RightKnee, new Point3F(800.8425140380859, 495.00497817993164, -0.02018394470214844));
            poseMap.put(LandmarkType.LeftAnkle, new Point3F(479.9602508544922, 591.387734413147, 0.012265679240226746));
            poseMap.put(LandmarkType.RightAnkle, new Point3F(849.0078735351562, 590.541787147522, 0.004604861885309219));
            poseMap.put(LandmarkType.LeftHeel, new Point3F(482.99278259277344, 605.773515701294, 0.012279056757688523));
            poseMap.put(LandmarkType.RightHeel, new Point3F(827.437744140625, 607.5321865081787, 0.004035076126456261));
            poseMap.put(LandmarkType.LeftToe, new Point3F(455.92079162597656, 620.6178045272827, -0.043700456619262695));
            poseMap.put(LandmarkType.RightToe, new Point3F(898.8021087646484, 620.7042789459229, -0.05215098261833191));

        double currentTime = System.currentTimeMillis() / 1000;
        Point2F frameSize = new Point2F(720, 1280);
        List<Observation> objects = Lists.newArrayList();

        SportsGround ground = new SportsGround();
        ground.addSporter("path");
//        ground.addSporter();
        ground.play(poseMap, objects, frameSize, currentTime);

        System.out.println("stop...............");
    }

}
