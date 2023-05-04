import java.util.*;

public class ListGrades {
    private List<StudentGrades<Double>> gradelist;

    public ListGrades(){
        this.gradelist = new ArrayList<>();
    }

    public void AddGrade(StudentGrades<Double> grade){
        this.gradelist.add(grade);
    }

    public StudentGrades<Double>GetGrade(int i){
        return gradelist.get(i);
    }
}
