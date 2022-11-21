package com.astra.actionconfig.utils;

import com.alibaba.fastjson.JSONObject;
import com.astra.actionconfig.config.Sport;
import com.astra.actionconfig.config.SportState;
import com.astra.actionconfig.config.data.PngImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

        Sport sport = JSONObject.parseObject(sb.toString(), Sport.class);

        List<SportState> states = sport.getStates();
        states.forEach(state -> {
            PngImage image = state.getImage();
            if (Objects.nonNull(image) && StringUtils.isNotBlank(image.getPhoto())) {
                image.setPhoto("");
            }
        });

        log.info("[MainConfig entity]---, {}", sport.states.stream().filter( s -> s.id == 7).findFirst().get().objects);
    }

    public static void main(String[] args) {
        parseConfig();
    }
}
