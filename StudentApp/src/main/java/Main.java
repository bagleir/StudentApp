import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");
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
    }
}