import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;

public class App {

    public static ArrayList<Student> allStudent = new ArrayList<>();  // ArrayList to store all student objects

    public static String Createfile(Student student) {
        // Create a file path based on the student's name with spaces replaced by underscores
        String filename = "file/Builtin" + student.GetName().replaceAll(" ", "_") + ".txt";
        return filename;
    }

    public static void Builtin(String filename, Student student) throws IOException {
        File file = new File(filename);
        String s = "";
        s = s + Integer.valueOf(student.GetID()) + ", " + student.GetName() + ", " + student.GetEmail() + ", " + student.GetDate();
        s = s + "\n";
        for (int i = 0; i < student.NumberGrade(); i++) {
            s = s + "Grade " + (i + 1) + " : " + student.GetI(i).GetGrade() + "\n";
        }
        FileUtils.writeStringToFile(file, s, StandardCharsets.UTF_8);
    }

    public static Student ReadFileStudent(String filename) throws IOException {
        String fileContent = FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8);
        String name = "";
        String email = "";
        String date = "";
        String ID = "";
        String currentgrade = "";
        int i = 0;

        // Parsing the file content to extract student information
        while (i < fileContent.length() && fileContent.charAt(i) != ',') {
            ID = ID + fileContent.charAt(i);
            i = i + 1;
        }
        i = i + 2;

        while (i < fileContent.length() && fileContent.charAt(i) != ',') {
            name = name + fileContent.charAt(i);
            i = i + 1;
        }
        i = i + 2;

        while (i < fileContent.length() && fileContent.charAt(i) != ',') {
            email = email + fileContent.charAt(i);
            i = i + 1;
        }
        i = i + 2;

        while (i < fileContent.length() && fileContent.charAt(i) != '\n') {
            date = date + fileContent.charAt(i);
            i = i + 1;
        }

        if (i >= fileContent.length()) {
            throw new IOException("Invalid File");
        }

        i = i + 11;

        if (!AddStudent.Is_Valid(name, ID, date, email)) {
            throw new IOException("Invalid File");
        }

        int ID1 = Integer.parseInt(ID);

        if (ID1 == 0) {
            ID1 = AddStudent.Pick_ID();
        }

        Student student = new Student(ID1, name, date, email);
        allStudent.add(student);

        // Extracting and adding the grades to the student
        while (i < fileContent.length()) {
            currentgrade = "";
            while (i < fileContent.length() && fileContent.charAt(i) != '\n') {
                currentgrade = currentgrade + fileContent.charAt(i);
                i = i + 1;
            }
            i = i + 11;
            Double d = Double.parseDouble(currentgrade);
            StudentGrades gr = new StudentGrades(d);
            student.AddGrade(gr);
        }
        return student;
    }

    public static void ReadStudentsAdd(String filename) throws IOException {
        String fileContent = FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8);
        String name = "";
        String email = "";
        String date = "";
        String ID = "";
        int i = 0;

        // Parsing the file content to extract student information
        while (i < fileContent.length()) {
            while (i < fileContent.length() && fileContent.charAt(i) != ' ') {
                ID = ID + fileContent.charAt(i);
                i = i + 1;
            }
            i = i + 1;

            while (i < fileContent.length() && fileContent.charAt(i) != ' ') {
                name = name + fileContent.charAt(i);
                i = i + 1;
            }
            i = i + 1;

            while (i < fileContent.length() && fileContent.charAt(i) != ' ') {
                date = date + fileContent.charAt(i);
                i = i + 1;
            }
            i = i + 1;

            if (i >= fileContent.length()) {
                throw new IOException("Invalid File");
            }

            while (i < fileContent.length() && fileContent.charAt(i) != '\n') {
                email = email + fileContent.charAt(i);
                i = i + 1;
            }
            i = i + 1;

            if (!AddStudent.Is_Valid(name, ID, date, email)) {
                throw new IOException("Invalid File");
            }

            int id = Integer.parseInt(ID);

            if (id == 0) {
                id = AddStudent.Pick_ID();
            }

            Student s = new Student(id, name, date, email);
            App.allStudent.add(s);

            name = "";
            email = "";
            date = "";
            ID = "";
        }
    }

    public static void generateStudentInfoFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("file/student_info.txt"))) {
            for (Student student : allStudent) {
                writer.println("Student's ID : " + student.GetID());
                writer.println("Name : " + student.GetName());
                writer.println("Date : " + student.GetDate());
                writer.println("Email : " + student.GetEmail());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}