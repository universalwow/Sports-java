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

        
//        poseMap.put(LandmarkType.RightElbow, new Point3F(378.3071279525757, 538.8972854614258, -0.001577141508460045)));
//        poseMap.put(LandmarkType.LeftPinkyFinger, new Point3F(431.5408229827881, 664.5702362060547, -0.03371106386184693)));
//
//        poseMap.put(LandmarkType.RightEyeInner, new Point3F(397.4977111816406, 323.02207946777344, -0.03414697349071503)));
//        poseMap.put(LandmarkType.RightEye, new Point3F(397.5790786743164, 323.7246322631836, -0.034150737524032596)));
//        poseMap.put(LandmarkType.RightHip, new Point3F(389.1557836532593, 638.6297607421875, 0.0038138996809720994)));
//        poseMap.put(LandmarkType.LeftEar, new Point3F(408.07878971099854, 340.3202819824219, -0.03102298974990845)));
//        poseMap.put(LandmarkType.LeftEye, new Point3F(400.27570724487305, 324.9848175048828, -0.03646494150161743)));
//        poseMap.put(LandmarkType.LeftShoulder, new Point3F(429.22884464263916, 436.7615509033203, -0.023721055686473848)));
//        poseMap.put(LandmarkType.RightEar, new Point3F(402.61613845825195, 340.5696105957031, -0.0200445294380188)));
//        poseMap.put(LandmarkType.MouthLeft, new Point3F(397.0344829559326, 358.6964416503906, -0.0336983323097229)));
//        poseMap.put(LandmarkType.MouthRight, new Point3F(394.40385818481445, 358.2015609741211, -0.030552729964256287)));
//        poseMap.put(LandmarkType.LeftElbow, new Point3F(434.60652351379395, 546.9325256347656, -0.02147688567638397)));
//        poseMap.put(LandmarkType.RightAnkle, new Point3F(386.75737380981445, 931.1382293701172, 0.029906556010246277)));
//        poseMap.put(LandmarkType.RightEyeOuter, new Point3F(397.7031469345093, 324.5520782470703, -0.03415963649749756)));
//        poseMap.put(LandmarkType.LeftToe, new Point3F(407.6274061203003, 975.7364654541016, 0.00890396535396576)));
//        poseMap.put(LandmarkType.LeftEyeOuter, new Point3F(401.63994312286377, 326.2969970703125, -0.03647010624408722)));
//        poseMap.put(LandmarkType.RightThumb, new Point3F(375.04315853118896, 648.7305450439453, -0.013498300313949585)));
//        poseMap.put(LandmarkType.LeftEyeInner, new Point3F(399.02227878570557, 323.7372589111328, -0.03645811975002289)));
//        poseMap.put(LandmarkType.LeftThumb, new Point3F(426.3264799118042, 651.1165618896484, -0.03127295672893524)));
//        poseMap.put(LandmarkType.Nose, new Point3F(394.22258377075195, 336.38065338134766, -0.03614669144153595)));
//        poseMap.put(LandmarkType.LeftKnee, new Point3F(409.9720001220703, 802.2218322753906, 4.3038260191679E-4)));
//        poseMap.put(LandmarkType.RightToe, new Point3F(377.7550220489502, 968.3370971679688, 0.016007739305496215)));
//        poseMap.put(LandmarkType.LeftIndexFinger, new Point3F(428.61305236816406, 670.2887725830078, -0.037187105417251586)));
//        poseMap.put(LandmarkType.LeftWrist, new Point3F(431.99469566345215, 638.8069534301758, -0.029819202423095704)));
//        poseMap.put(LandmarkType.LeftAnkle, new Point3F(405.9341812133789, 932.8451538085938, 0.024083712697029115)));
//        poseMap.put(LandmarkType.LeftHeel, new Point3F(404.71362590789795, 945.9996032714844, 0.025400498509407045)));
//        poseMap.put(LandmarkType.RightHeel, new Point3F(388.3156728744507, 947.2075653076172, 0.031264898180961606)));
//        poseMap.put(LandmarkType.RightShoulder, new Point3F(384.8539924621582, 433.49117279052734, -0.010223744809627533)));
//        poseMap.put(LandmarkType.RightPinkyFinger, new Point3F(370.4822015762329, 656.8244934082031, -0.014342816174030304)));
//        poseMap.put(LandmarkType.RightWrist, new Point3F(373.9418649673462, 631.3254547119141, -0.011365249007940292)));
//        poseMap.put(LandmarkType.RightIndexFinger, new Point3F(372.6864194869995, 658.4451293945312, -0.01889760047197342)));
//        poseMap.put(LandmarkType.LeftHip, new Point3F(411.9555473327637, 645.8351898193359, -0.0038031991571187973)));
//        poseMap.put(LandmarkType.RightKnee, new Point3F(388.5319662094116, 791.5168762207031, 0.007666853070259094)));

        double currentTime = System.currentTimeMillis() / 1000;
        Point2F frameSize = new Point2F(720, 1280);
        List<Observation> objects = Lists.newArrayList();

        SportsGround ground = new SportsGround();
        ground.addSporter();
        ground.play(poseMap, objects, frameSize, currentTime);

        System.out.println("stop...............");
    }

}
