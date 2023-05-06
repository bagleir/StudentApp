public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");
        Student gerard = new Student(10,"Jean","13/08/1999","toto@gmail.com");

        for (int i = 1; i <= 5; i++) {
            String s = gerard.GetEmail();
            System.out.println(s);
            System.out.println("i = " + i);
        }
    }
}