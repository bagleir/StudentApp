import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main{
    
    public static void main(String[] args){
        // Launch the application by calling the launch() method of MaximizedWindow class
        MaximizedWindow.launch(MaximizedWindow.class, args);
    }
}