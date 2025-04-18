package com.astra.actionconfig.utils;


import com.alibaba.fastjson2.JSON;
import com.astra.actionconfig.config.Sport;
import com.astra.actionconfig.config.SportState;
import com.astra.actionconfig.config.data.PngImage;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ConfigParseUtil {
    private ConfigParseUtil() {
    }

    /***
     * 解析json
     * @param is
     * @param flag 若为false, 将photo置空
     * @return
     */
    public static Sport parseConfig(InputStream is, boolean flag) {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("parseConfig error, {}", ex.getMessage());
            return null;
        }

        Sport sport = JSON.parseObject(sb.toString(), Sport.class);
        if(flag) return sport;

        List<SportState> states = sport.getStates();
        states.forEach(state -> {
            PngImage image = state.getImage();
            if (Objects.nonNull(image) && StringUtils.isNotBlank(image.getPhoto())) {
                image.setPhoto("");
            }
        });

        return sport;
    }

    /*public static Sport parseConfig(InputStream is, boolean flag) {
        StringBuilder sb = new StringBuilder();
        Sport sport;
        try {
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new Jdk8Module());
            sport = mapper.readValue(sb.toString(), Sport.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("parseConfig error, {}", ex.getMessage());
            return null;
        }
        if(flag) return sport;

        List<SportState> states = sport.getStates();
        states.forEach(state -> {
            PngImage image = state.getImage();
            if (Objects.nonNull(image) && StringUtils.isNotBlank(image.getPhoto())) {
                image.setPhoto("");
            }
        });

        return sport;
    }*/


    public static void parseConfig() {
        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/12.json");
        Sport sport = parseConfig(is, true);

        log.info("[MainConfig entity]---, {}", sport.states.stream().filter( s -> s.id == 7).findFirst().get().objects);
    }

    public static void main(String[] args) throws IOException {
//        parseConfig();
//        InputStream is = ConfigParseUtil.class.getResourceAsStream("/configjson/12.json");
//        Sport sport = parseConfig(is, false);
//        System.out.println(11);
        System.out.println(String.format("%s", System.getProperty("user.home")));
        String destinationDirectory = System.getProperty("user.home") + "/uni_learn/phoenix/uni_sport/priv/static/sports_small";
        // Get a list of all the JSON files in the source directory
        File[] jsonFiles = new File(System.getProperty("user.home") + "/uni_learn/phoenix/uni_sport/priv/static/sports").listFiles((dir, name) -> name.endsWith(".json"));
        // Loop through each JSON file and write it to the destination directory
        for (File jsonFile : jsonFiles) {
            Sport sport = parseConfig(new FileInputStream(jsonFile), false);
            ;
            Files.write(Paths.get(destinationDirectory + "/" + jsonFile.getName()), JSON.toJSONBytes(sport));
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(, sport);
        }
    }
}
