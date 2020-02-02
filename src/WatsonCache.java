import java.util.ArrayList;
import java.util.HashMap;

public class WatsonCache {

    HashMap<String, ArrayList<String>> watsonCache;

    public WatsonCache (){
        watsonCache = new HashMap<>();
    }

    public void put (String key, String value){
        ArrayList<String> valueList = watsonCache.get(key);
        if (valueList == null){
            valueList = new ArrayList<>();
        }
        if (!valueList.contains(value)) {
            valueList.add(value);
        }
        watsonCache.put(key, valueList);
    }

    public ArrayList<String> getURL (String key){
        return watsonCache.get(key);
    }

    public ArrayList<String> containsKey (String key){
        ArrayList<String> defaultValue = new ArrayList<>();
        defaultValue.add("No Value for this Key");
        return watsonCache.getOrDefault(key, defaultValue);
    }

    public ArrayList<String> remove (String key){
        return watsonCache.remove(key);
    }

}
