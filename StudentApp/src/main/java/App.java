import org.apache.commons.io.FileUtils;

public class App {

    public void Builtin(String filename, Student student){
        FileUtils.writeStringToFile(filename,"test","UTF-8");
    }
}
