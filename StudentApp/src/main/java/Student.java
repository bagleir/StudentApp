public class Student {

    private int ID;
    private String date_of_birth;
    private String name;
    private String email;
    private ListGrades grades;

    public int GetID(){
        return this.ID;
    }

    public String GetDate(){
        return this.date_of_birth;
    }

    public String GetName(){
        return this.name;
    }

    public String GetEmail(){
        return this.email;
    }

    public void GetGrade(int i){
        this.grades.GetGrade(i);
    }

    public void AddGrade(StudentGrades<Double> grade){
        this.grades.AddGrade(grade);
    }

    public void RemoveGrade(StudentGrades<Double> grade){
        this.grades.RemoveGrade(grade);
    }

    public int NumberGrade(){
        return grades.NumberGrade();
    }

    public StudentGrades<Double> GetI(int i){
        return grades.GetI(i);
    }

    public Student(int ID, String name, String date_of_birth, String email){
        this.ID = ID;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.name = name;
        this.grades = new ListGrades();
    }
}
