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
        MaximizedWindow.launch(MaximizedWindow.class, args);
    }
    /*public static void main(String[] args) {

        System.out.printf("toto and welcome!");
        Student gerard = new Student(10,"Jean","13/08/1999","toto@gmail.com");
        String filename = App.Createfile(gerard);
        for(Double i =0.0; i<10.0;i += 1.0){
            StudentGrades sg= new StudentGrades(i);
            gerard.AddGrade(sg);
        }
        try {
        // Method call that throws IOException
            App.Builtin(filename,gerard);
        } catch (IOException e) {
        // Exception handling code
            e.printStackTrace();
        }
        Student student = new Student(1,"a","a","a");
        try {
        // Method call that throws IOException
            student = App.ReadFileStudent(filename);
        } catch (IOException e) {
        // Exception handling code
            e.printStackTrace();
        }
        System.out.println(student.GetName() +" "+student.GetEmail() +" "+student.GetID() +" "+student.GetDate() +" "+student.GetI(1).GetGrade() +" "+student.GetI(2).GetGrade() +" ");
    }*/
}