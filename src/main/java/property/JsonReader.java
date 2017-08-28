package property;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xupeng5 on 2017/8/26.
 */
public class JsonReader {

    static JSONObject jsonObject = readJson();

    public static JSONObject readJson() {
        InputStream is = Object.class.getResourceAsStream("/PropertyGraph.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        JSONObject jsonObject = JSONObject.fromObject(stringBuilder.toString());
        return jsonObject;
    }
}
