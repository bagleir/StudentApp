import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {

    public static void Builtin(String filename, Student student) throws IOException{
        File file = new File(filename);
        FileUtils.writeStringToFile(file,"test",StandardCharsets.UTF_8);
    }
}
