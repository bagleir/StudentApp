public class Student {

    private int ID;
    private String date_of_birth;
    private String name;
    private String email;

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

    public Student(int ID, String name, String date_of_birth, String email){
        this.ID = ID;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.name = name;
    }
}
