package casebook.handlers.dynamic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BaseHandler {
    private static final String VIEWS_FULL_PATH = System.getProperty("user.dir") + "\\src\\org\\softuni\\main\\resources\\templates\\";

    public BaseHandler() {
    }

    protected String getView(String viewName) {
        List<String> viewContent = null;
        try {
            viewContent = Files.readAllLines(Paths.get(VIEWS_FULL_PATH + viewName + ".html"));
            return String.join("", viewContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
