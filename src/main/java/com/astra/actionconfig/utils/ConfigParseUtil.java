package com.astra.actionconfig.utils;

import com.alibaba.fastjson2.JSON;
import com.astra.actionconfig.config.MainConfig;
import com.astra.actionconfig.config.State;
import com.astra.actionconfig.config.data.Image;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ConfigParseUtil {
    private ConfigParseUtil() {
    }

    public static void parseConfig() {
        StopWatch sw = new StopWatch();
        sw.start();
        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/fileyf1.json");
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("parseConfig error, {}", ex.getMessage());
        }

        MainConfig mainConfig = JSON.parseObject(sb.toString(), MainConfig.class);

        List<State> states = mainConfig.getStates();
        states.forEach(state -> {
            Image image = state.getImage();
            if (Objects.nonNull(image) && StringUtils.isNotBlank(image.getPhoto())) {
                image.setPhoto("");
            }
        });

        sw.stop();
        log.info("[MainConfig entity]---, cost {}ms", sw.getTotalTimeMillis());
    }

    public static void main(String[] args) {
        parseConfig();
    }
}
