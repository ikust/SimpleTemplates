package co.ikust.simpletemplates;

/**
 * Templates utility methods.
 *
 * Created by ivan on 13/01/14.
 */
public class Templates {

    protected static Templates instance;

    public static Templates getInstance() {
        if(instance == null) {
            instance = new Templates();
        }

        return instance;
    }

    public Template read(String path) {
        return new Template(ResourceUtils.readFile(path));
    }
}
