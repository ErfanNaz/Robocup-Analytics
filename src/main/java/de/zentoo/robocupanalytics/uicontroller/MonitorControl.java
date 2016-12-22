package de.zentoo.robocupanalytics.uicontroller;

import de.zentoo.robocupanalytics.DefaultContext;
import de.zentoo.robocupanalytics.event.action.ControlAction;
import de.zentoo.robocupanalytics.event.action.ShowAction;
import de.zentoo.robocupanalytics.event.listener.ControlActionListener;
import de.zentoo.robocupanalytics.event.listener.ShowActionListener;
import de.zentoo.robocupanalytics.event.ListenerHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MonitorControl implements Initializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorControl.class);

    private DefaultContext context = DefaultContext.getInstance();

    private List<ControlActionListener> listenerList = new ArrayList<>();

    private ListenerHandler listenerHandler = null;

    private boolean isPlay = false;

    @FXML
    private Button button_backback;
    @FXML
    private Button button_back;
    @FXML
    private Button button_play_stop;
    @FXML
    private Button button_forward;
    @FXML
    private Button button_forwardforward;
    @FXML
    private TextField textField_time;

    @FXML
    private void stop_click(ActionEvent event) {
        if(isPlay){
            listenerHandler.setAction(new ControlAction() {
                @Override
                public Event getEvent() {
                    return Event.STOP;
                }
            });
        } else {
            listenerHandler.setAction(new ControlAction() {
                @Override
                public Event getEvent() {
                    return Event.PLAY;
                }
            });
        }
    }

    @FXML
    private void fast_back(ActionEvent event) {
        listenerHandler.setAction(new ControlAction() {
            @Override
            public Event getEvent() {
                return Event.FASTBACK;
            }
        });
    }

    @FXML
    private void fast_forward(ActionEvent event) {
        listenerHandler.setAction(new ControlAction() {
            @Override
            public Event getEvent() {
                return Event.FASTFORWARD;
            }
        });
    }

    @FXML
    private void step_forward(ActionEvent event) {
        listenerHandler.setAction(new ControlAction() {
            @Override
            public Event getEvent() {
                return Event.FORWARD;
            }
        });
    }

    @FXML
    private void step_backward(ActionEvent event) {
        listenerHandler.setAction(new ControlAction() {
            @Override
            public Event getEvent() {
                return Event.BACK;
            }
        });
    }

    @FXML
    private void jumpTo(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            listenerHandler.setAction(new ControlAction() {
                @Override
                public Event getEvent() {
                    return Event.JUMPTO;
                }

                @Override
                public int getTime() {
                    Integer time = Integer.valueOf(textField_time.getText());
                    if (time == null) time = 0;
                    return time;
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*InputStream fontStream = Propertieloader.getResourceAsStream("styles/fontawesome-webfont.ttf");
        FontAwesome fontAwesome = new FontAwesome(fontStream);
        int fontsize = 18;
        Glyph glyph = fontAwesome.create('\uf049').size(fontsize); //GEAR
        button_backback.setGraphic(glyph);
        button_backback.setText("");
        glyph = fontAwesome.create('\uf04a').size(fontsize);
        button_back.setGraphic(glyph);
        button_back.setText("");
        glyph = fontAwesome.create('\uf04d').size(fontsize);
        button_play_stop.setGraphic(glyph);
        button_play_stop.setText("");
        glyph = fontAwesome.create('\uf04e').size(fontsize);
        button_forward.setGraphic(glyph);
        button_forward.setText("");
        glyph = fontAwesome.create('\uf050').size(fontsize);
        button_forwardforward.setGraphic(glyph);
        button_forwardforward.setText("");*/
        listenerHandler = context.getListenerHandler();
        listenerHandler.addListener(new ShowActionListener() {
            @Override
            public void onAction(ShowAction action) {
                textField_time.setText(String.valueOf(action.getShow().getTime()));
            }
        });
        listenerHandler.addListener(new ControlActionListener() {
            @Override
            public void onAction(ControlAction action) {
                if(action.getEvent() == ControlAction.Event.STOP){
//                    button_play_stop.setText("[▶]︎");
                    button_play_stop.setStyle("-fx-text-fill: darkblue");
                    isPlay = false;
                } else if (action.getEvent() == ControlAction.Event.PLAY) {
//                    button_play_stop.setText("▶︎");
                    button_play_stop.setStyle("-fx-text-fill: red");
                    isPlay = true;
                }
            }
        });
    }

}
