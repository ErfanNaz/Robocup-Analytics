package de.zentoo.robocupanalytics.plugin;

import de.zentoo.robocupanalytics.Context;
import javafx.scene.Parent;

/**
 * Created by Erfan on 14.02.15.
 */

public interface Pluginload {

    public void init(Context context);

    public Parent getTabRoot();

    public String getTabName();

}
