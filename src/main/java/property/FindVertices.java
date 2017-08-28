package property;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xupeng5 on 2017/8/26.
 */
public class FindVertices {
    //参数格式为"name=marko",多个条件"name=marko,age=27"
    public static String getVertices(HashMap<String, String> condition) {
        HashMap<String, HashMap<Object, Set>> hashMap = generateMap();
        Set<Integer> set = new HashSet<Integer>();
        Set<Integer> resultSet = new HashSet<Integer>();
        boolean flag = true;
        for(String key:condition.keySet()) {
            if(hashMap.containsKey(key)) {
                HashMap<Object, Set> map = hashMap.get(key);
                String secKey = condition.get(key);
                if(map.containsKey(secKey)) {
                    set = map.get(secKey);
                }
            }
            if(flag) {
                resultSet = set;
                flag = false;
            } else {
                resultSet.retainAll(set);

            }
        }

        JSONArray jsonArray = JsonReader.jsonObject.getJSONArray("vertices");
        JSONArray result = new JSONArray();

        for(Integer id : resultSet) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject vertices = jsonArray.getJSONObject(i);
                if(vertices.getInt("_id") == id) {
                    result.add(vertices);
                }
            }
        }

        return result.toString();
    }

    public static HashMap<String, HashMap<Object, Set>> generateMap() {
        HashMap<String, HashMap<Object, Set>> hashMap = new HashMap<>();
        HashMap<Object, Set> nameMap = new HashMap<>();
        HashMap<Object, Set> ageMap = new HashMap<>();
        HashMap<Object, Set> typeMap = new HashMap<>();
        HashMap<Object, Set> langMap = new HashMap<>();


        try {
            JSONArray vertices = JsonReader.jsonObject.getJSONArray("vertices");
            JSONArray edges = JsonReader.jsonObject.getJSONArray("edges");

            for(int i = 0; i < vertices.size(); i ++) {
                JSONObject inner = vertices.getJSONObject(i);
                nameMap = initMap(inner.getString("name"), nameMap, inner.getInt("_id"));
                if(inner.getString("_type").equals("person")) {
                    ageMap = initMap(String.valueOf(inner.getInt("age")), ageMap, inner.getInt("_id"));
                } else {
                    langMap = initMap(inner.getString("lang"), langMap, inner.getInt("_id"));
                }

                typeMap = initMap(inner.getString("_type"), typeMap, inner.getInt("_id"));
            }

            hashMap.put("name", nameMap);
            hashMap.put("age", ageMap);
            hashMap.put("lang", langMap);
            hashMap.put("_type", typeMap);

        } catch (Exception e) {
            System.out.println(e.getMessage() + "TEST");
        }
        return hashMap;
    }

    public static HashMap<Object, Set> initMap(String name, HashMap<Object, Set> map, int id) {
        Set set;
        if(map.containsKey(name)) {
            set = map.get(name);
        } else {
            set = new HashSet<>();
        }
        set.add(id);
        map.put(name, set);

        return map;
    }
}
