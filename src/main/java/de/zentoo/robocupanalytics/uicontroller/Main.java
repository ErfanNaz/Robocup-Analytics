package de.zentoo.robocupanalytics.uicontroller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import de.zentoo.robocupanalytics.DefaultContext;
import de.zentoo.robocupanalytics.parser.ParserDBHandler;
import de.zentoo.robocupanalytics.util.Propertieloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     *  mainPane.getItems().get(0) is PlaygroundPane
     *  mainPane.getItems().get(1) is PluginPane
     */
    @FXML
    private SplitPane mainPane;

    /**
     *  playgroundPane.getItems().get(0) is Monitor
     *  playgroundPane.getItems().get(1) is PlaygroundControl
     */
    @FXML
    private SplitPane playgroundPane;

    private FileChooser fileChooser;
    private DefaultContext context = DefaultContext.getInstance();
    private ParserDBHandler parserHandler;
    private Map<String,Object> resourceBundle = new HashMap<>();

    @FXML
    private void action_close(ActionEvent event)
    {
        ((Stage) mainPane.getScene().getWindow()).close();
    }
    
    @FXML
    private void action_loadFile(ActionEvent event){
        if(fileChooser == null) buildFileChooser();
        File choosenFile = fileChooser.showOpenDialog(new Stage());
        if(choosenFile == null) return;
        if(parserHandler == null){
            Properties prop = Propertieloader.getInstance().getProperties();
            boolean with_gis = prop.getProperty("database.gis").equals("true");
            parserHandler = new ParserDBHandler(context.getOrm(), with_gis, context.getPlayService());
            parserHandler.setFile(choosenFile);
        } else {
            parserHandler.setFile(choosenFile);
        }
    }

    @FXML
    private void action_reloadPlugins(ActionEvent event){

    }

    private void buildFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Pick Log File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Robocup 2D log file", "*.rcg"));
    }

    public AnchorPane getPlayground() {
        return (AnchorPane) playgroundPane.getItems().get(0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            resourceBundle.put("root",this);
            AnchorPane playgroundAnchor = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/monitor.fxml"), new MainResourceBundle());
            playgroundPane.getItems().set(0, playgroundAnchor);
            playgroundAnchor.setPrefSize(600,400);
            AnchorPane playgroundControlAnchor = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/monitorControl.fxml"));
            playgroundPane.getItems().set(1, playgroundControlAnchor);
            AnchorPane pluginAnchor = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/plugincontent.fxml"));
            mainPane.getItems().set(1, pluginAnchor);
        } catch (IOException ex) {
            LOGGER.error("error while loading the different FXML files", null, ex);
        }
    }

    class MainResourceBundle extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return resourceBundle.get(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(resourceBundle.keySet());
        }
    }
}
