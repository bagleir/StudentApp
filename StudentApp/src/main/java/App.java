import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;
import java.util.ArrayList;


public class App {

    public static ArrayList<Student> allStudent = new ArrayList<>();

    public static String Createfile(Student student){
        String filename = "Builtin"+student.GetName() + ".txt";
        return filename;
    }

    public static void Builtin(String filename, Student student) throws IOException{
        File file = new File(filename);
        String s = "";
        s = s + Integer.valueOf(student.GetID()) + ", " + student.GetName() + ", " + student.GetEmail() + ", " + student.GetDate();
        s = s+ "\n";
        for(int i = 0;i<student.NumberGrade();i++){
            s = s + "Grade "+ i + " : " + student.GetI(i).GetGrade() + "\n";
        }
        FileUtils.writeStringToFile(file,s,StandardCharsets.UTF_8);
    }

    public static Student ReadFileStudent(String filename) throws IOException{
        String fileContent = FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8);
        String name ="";
        String email ="";
        String date ="";
        String ID = "";
        String currentgrade = "";
        int i =0;

        while(i < fileContent.length() && fileContent.charAt(i) != ','){
            ID = ID + fileContent.charAt(i);
            i = i+1;
        }
        i = i+2;

        while(i < fileContent.length() && fileContent.charAt(i) != ','){
            name = name + fileContent.charAt(i);
            i = i+1;
        }
        i = i+2;
        while(i < fileContent.length() && fileContent.charAt(i) != ','){
            email = email + fileContent.charAt(i);
            i = i+1;
        }
        i = i+2;
        while(i < fileContent.length() && fileContent.charAt(i) != '\n'){
            date = date + fileContent.charAt(i);
            i = i+1;
        }
        if(i >= fileContent.length()){
            throw new IOException("Invalide File");
        }
        i = i+11;
        int ID1 = Integer.parseInt(ID);
        Student student = new Student(ID1,name,date,email);
        while(i<fileContent.length()){
            currentgrade = "";
            while(i < fileContent.length() && fileContent.charAt(i) != '\n'){
                currentgrade = currentgrade + fileContent.charAt(i);
                i = i+1;
            }
            i = i+11;
            Double d = Double.parseDouble(currentgrade);
            StudentGrades gr = new StudentGrades(d);
            student.AddGrade(gr);
        }
        return student;
    }
}
