package com.astra.actionconfig.config;

import com.astra.actionconfig.config.data.Point2F;

import java.util.List;

public interface SportListener {
    void onReady(String sportId, int areaIndex);
    void onEnd(String sportId, int areaIndex);

    void warning(String sportId, int areaIndex, String warning);

    List<Point2F> initArea(String sportId, int areaIndex);
}
