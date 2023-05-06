public class StudentGrades<G> {
    private G Grade;

    public G GetGrade(){
        return this.Grade;
    }

    public StudentGrades(G grade){
        this.Grade = grade;
    }
}
