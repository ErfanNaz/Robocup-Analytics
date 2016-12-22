package de.zentoo.robocupanalytics.parser;

import de.zentoo.robocupanalytics.entity.PlayerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Erfan Nazmehr on 06.07.15.
 */
public class PlayerTypeParser extends Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerTypeParser.class);

    @Override
    void parse() throws ParseException {
        if(scanner == null){
            throw new ParseException("no file or inputstream initializied");
        }
        Date startTime = new Date();
        LOGGER.info("start parsing for player_type");
        while (scanner.hasNextLine() && !isInterrupted()) {
            if (scanner.hasNext("\\(player_type")) {
                scanner.next();
                if(scanner.hasNext("\\(id")){
                    PlayerType playerType = new PlayerType();
                    scanner.next();
                    playerType.setId(Integer.parseInt(scanner.next().replace(")(player_speed_max", "")));
                    playerType.setPlayer_speed_max(Double.parseDouble(scanner.next().replace(")(stamina_inc_max", "")));
                    playerType.setStamina_inc_max(Double.parseDouble(scanner.next().replace(")(player_decay", "")));
                    playerType.setPlayer_decay(Double.parseDouble(scanner.next().replace(")(inertia_moment", "")));
                    playerType.setInertia_moment(Double.parseDouble(scanner.next().replace(")(dash_power_rate", "")));
                    playerType.setDash_power_rate(Double.parseDouble(scanner.next().replace(")(player_size", "")));
                    playerType.setPlayer_size(Double.parseDouble(scanner.next().replace(")(kickable_margin", "")));
                    playerType.setKickable_margin(Double.parseDouble(scanner.next().replace(")(kick_rand", "")));
                    playerType.setKick_rand(Double.parseDouble(scanner.next().replace(")(extra_stamina", "")));
                    playerType.setExtra_stamina(Double.parseDouble(scanner.next().replace(")(effort_max", "")));
                    playerType.setEffort_max(Double.parseDouble(scanner.next().replace(")(effort_min", "")));
                    playerType.setEffort_min(Double.parseDouble(scanner.next().replace(")(kick_power_rate", "")));
                    playerType.setKick_power_rate(Double.parseDouble(scanner.next().replace(")(foul_detect_probability", "")));
                    playerType.setFoul_detect_probability(Double.parseDouble(scanner.next().replace(")(catchable_area_l_stretch", "")));
                    playerType.setCatchable_area_l_stretch(Double.parseDouble(scanner.next().replace("))", "")));
                    broadCastPlayerType(playerType);
                }
            } else if (scanner.hasNext("\\(msg")){
                this.interrupt();
            }  else {
                scanner.nextLine();
            }
        }
        Date endTime = new Date();
        double parseTime = endTime.getTime() - startTime.getTime();
        LOGGER.info("finished parsing File for player_type in {} seconds", parseTime/1000);
    }

}
