import java.util.*;

public class ListGrades {
    private List<StudentGrades<Double>> gradelist;

    public ListGrades(){
        this.gradelist = new ArrayList<>();
    }

    public void AddGrade(StudentGrades<Double> grade){
        this.gradelist.add(grade);
    }

    public void RemoveGrade(StudentGrades<Double> grade){
        this.gradelist.remove(grade);
    }

    public int NumberGrade(){
        return this.gradelist.size();
    }

    public StudentGrades<Double> GetI(int i){
        return this.gradelist.get(i);
    }

    public StudentGrades<Double> GetGrade(int i){
        return gradelist.get(i);
    }
}
