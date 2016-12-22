package de.zentoo.robocupanalytics.parser;

import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Erfan Nazmehr
 */
public class ShowParser extends Parser{

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowParser.class);

    @Override
    public void parse() throws ParseException {
        if(scanner == null){
            throw new ParseException("no file or inputstream initializied");
        }
        int time = 0;
        Date startTime = new Date();
        String tmp;
        boolean hasNextPlayer = false;
        LOGGER.info("start parsing for shows");
        while (scanner.hasNextLine() && !isInterrupted()) {
            if (scanner.hasNext("\\(show")) {
                tmp = scanner.next();
                time = scanner.nextInt();

                Ball ball = new Ball();
                ball.setTime(time);
                ArrayList<Player> playerList = new ArrayList<>();

                Show show = new Show(time,ball,playerList);

                if(scanner.hasNext("\\(\\(b\\)")){
                    tmp = scanner.next();
                    ball.setX(Double.parseDouble(scanner.next()));
                    ball.setY(Double.parseDouble(scanner.next()));
                    ball.setVx(Double.parseDouble(scanner.next()));
                    ball.setVy(Double.parseDouble(scanner.next().replace(")", "")));

                    hasNextPlayer = scanner.hasNext("\\(\\((l|r)+");
                    while(hasNextPlayer){
                        Player player = new Player();
                        tmp = scanner.next();
                        char side = tmp.charAt(tmp.length()-1);
                        int unum = Integer.parseInt(scanner.next().replace(")", ""));

                        player.setSide(side);
                        player.setUnum(unum);
                        player.setType(scanner.nextInt());
                        player.setState(scanner.next());
                        player.setX(Double.parseDouble(scanner.next()));
                        player.setY(Double.parseDouble(scanner.next()));
                        player.setVx(Double.parseDouble(scanner.next()));
                        player.setVy(Double.parseDouble(scanner.next()));
                        player.setBody(Double.parseDouble(scanner.next()));
                        player.setNeck(Double.parseDouble(scanner.next()));
                        
                        if(!scanner.hasNext("\\(v")){
                            player.setPoint_x(Double.parseDouble(scanner.next()));
                            player.setPoint_y(Double.parseDouble(scanner.next()));
                        }

                        tmp = scanner.next();
                        player.setView_quality(scanner.next().charAt(0));
                        player.setView_width(Double.parseDouble(scanner.next().replace(")", "")));

                        
                        if(scanner.hasNext("\\(s")){
                            tmp = scanner.next();
                            player.setInfo_stamina(Double.parseDouble(scanner.next()));
                            player.setInfo_effort(Double.parseDouble(scanner.next()));
                            player.setInfo_recovery(Double.parseDouble(scanner.next()));
                            if(!scanner.hasNext("\\("))
                                player.setInfo_capacity(Double.parseDouble(scanner.next().replace(")", "")));
                        }
                        
                        if(scanner.hasNext("\\(f")){
                            tmp = scanner.next();
                            player.setFocus_side(scanner.next().charAt(0));
                            player.setFocus_unum(Integer.parseInt(scanner.next().replace(")", "")));
                        }
                        
                        if(scanner.hasNext("\\(c")){
                            tmp = scanner.next();
                            player.setCount_kick(scanner.nextInt());
                            player.setCount_dash(scanner.nextInt());
                            player.setCount_turn(scanner.nextInt());
                            player.setCount_catch(scanner.nextInt());
                            player.setCount_move(scanner.nextInt());
                            player.setCount_turn_neck(scanner.nextInt());
                            player.setCount_change_view(scanner.nextInt());
                            player.setCount_say(scanner.nextInt());
                            player.setCount_tackle(scanner.nextInt());
                            player.setCount_pointto(scanner.nextInt());
                            player.setCount_attentionto(Integer.parseInt(scanner.next().replace(")", "")));
                        }
                        
                        hasNextPlayer = scanner.hasNext("\\(\\((l|r)+");

                        player.setShow(show);
                        playerList.add(player);
                    }

                } else {
                    throw new ParseException(String.format("ball informationen missing on show number: %d",time));
                }

                broadCastShow(new Show(time,ball,playerList));

            } else {
                scanner.nextLine();
            }
        }
        Date endTime = new Date();
        double parseTime = endTime.getTime() - startTime.getTime();
        LOGGER.info("finished parsing File for shows in {} seconds", parseTime/1000);
    }

}
