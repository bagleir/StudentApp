import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {

    public static String Createfile(Student student){
        String filename = "Builtin"+student.GetName() + ".txt";
        return filename;
    }

    public static void Builtin(String filename, Student student) throws IOException{
        File file = new File(filename);
        String s = "";
        s = s + student.GetName() + ", " + student.GetEmail() + ", " + student.GetDate();
        s = s+ "\n";
        for(int i = 0;i<student.NumberGrade();i++){
            s = s + "Grade "+ i + " : " + student.GetI(i).GetGrade() + "\n";
        }
        FileUtils.writeStringToFile(file,s,StandardCharsets.UTF_8);
    }
}
