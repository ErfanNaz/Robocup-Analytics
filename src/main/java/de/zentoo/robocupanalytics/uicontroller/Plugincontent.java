package de.zentoo.robocupanalytics.uicontroller;

import java.net.URL;
import java.util.ResourceBundle;

import de.zentoo.robocupanalytics.DefaultContext;
import de.zentoo.robocupanalytics.plugin.Pluginload;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Plugincontent implements Initializable{

    DefaultContext context;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = DefaultContext.getInstance();
        for(Pluginload plugin: context.getPlugins()){
            String pluginName = plugin.getTabName();
            Parent pluginRoot = plugin.getTabRoot();
            if(pluginName != null && pluginRoot != null) {
                Tab tab = new Tab(pluginName);
                tab.setContent(pluginRoot);
                tabPane.getTabs().add(tab);
            }
        }
    }

}
