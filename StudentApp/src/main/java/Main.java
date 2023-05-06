import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");
        Student gerard = new Student(10,"Jean","13/08/1999","toto@gmail.com");
        try {
        // Method call that throws IOException
            App.Builtin("test.txt",gerard);
        } catch (IOException e) {
        // Exception handling code
            e.printStackTrace();
        }
    }
}