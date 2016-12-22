package de.zentoo.robocupanalytics;

import de.zentoo.robocupanalytics.event.action.ApplicationAction;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);
    private static final Context context = DefaultContext.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("RoboCup Analytics");
        stage.setScene(scene);
        stage.setMinHeight(630);
        stage.setMinWidth(1200);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        context.getListenerHandler().setAction(new ApplicationAction() {
            @Override
            public Event getEvent() {
                return Event.STOP;
            }
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
