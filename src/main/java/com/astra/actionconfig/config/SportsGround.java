package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.Observation;
import com.astra.actionconfig.config.data.Point2F;
import com.astra.actionconfig.config.data.Point3F;
import com.astra.actionconfig.config.data.Warning;
import com.astra.actionconfig.config.data.landmarkd.LandmarkType;
import com.astra.actionconfig.utils.ConfigParseUtil;
import com.google.common.collect.Lists;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SportsGround {
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
        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/fileyf1.json");
        Sport sport = ConfigParseUtil.parseConfig(is, true);
        addSporter(sport);
    }

    public void play(Map<LandmarkType, Point3F> poseMap, List<Observation> objects, Point2F frameSize, Double currentTime) {
        for (int i = 0; i < sporters.size(); i++) {
            Sporter sporter = sporters.get(i);
            sporter.play(poseMap, objects, frameSize, currentTime);
            sporter.delayWarnings.forEach( warning -> {
                if (!warnings.contains(warning)) {
                    warnings.add((Warning) warning);
                }
            }

            );

            sporter.noDelayWarnings.forEach( warning -> {
                        if (!warnings.contains(warning)) {
                            warnings.add((Warning) warning);
                        }
                        sporter.noDelayWarnings.remove(warning);
                    }

            );



        }

    }


}
