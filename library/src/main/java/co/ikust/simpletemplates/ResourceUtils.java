package co.ikust.simpletemplates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ivan on 30/01/15.
 */
public class ResourceUtils {

    public static String readFile(String path) {
        InputStreamReader reader = new InputStreamReader(ResourceUtils.class.getResourceAsStream(path));

        BufferedReader bufferedReader = new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();
        String line = null;
        do {
            try {
                if(line != null) {
                    builder.append("\n");
                }

                line = bufferedReader.readLine();

                if(line != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        } while(line != null);

        return builder.toString();
    }
}
