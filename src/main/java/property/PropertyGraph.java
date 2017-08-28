package property;

import java.util.HashMap;
import property.ShortPath.*;

/**
 * Created by xupeng5 on 2017/8/27.
 */
public class PropertyGraph {

    public static String findVertices(String conditions) {
        HashMap<String, String> keyValue = new HashMap<>();
        String[] conditionArray = conditions.split(",");
        for(String condition:conditionArray) {
            if(condition.contains("=")){
                int index = condition.lastIndexOf("=");
                keyValue.put(condition.substring(0, index), condition.substring(index+1));
            }
        }

        return FindVertices.getVertices(keyValue);
        return FindVertices.getVertices(keyValue);
    }

    public static double getShortPath(String source, String to) {
        ShortPath shortPath = new ShortPath();
//      return shortPath.getTheOptimalWeight(source, to);
        Double distance = shortPath.getTheOptimalWeight(source, to);
        if(Double.valueOf(65535).equals(distance)) {
            distance = -1.0;
        }
        return distance;
    }
    public static void main(String[] args) {
        System.out.println(getShortPath("2", "1"));
        System.out.println(findVertices("age=27,name=tt"))
    }
}
