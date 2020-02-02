import java.util.ArrayList;
import java.util.HashMap;

public class AssistantCache {

    HashMap<String, ArrayList<String>> assistantCache;

    public AssistantCache (){
        assistantCache = new HashMap<>();
    }

    public void put (String key, String value){
        ArrayList<String> valueList = assistantCache.get(key);
        if (valueList == null){
            valueList = new ArrayList<>();
        }
        if (!valueList.contains(value)) {
            valueList.add(value);
        }
        assistantCache.put(key, valueList);
    }

    public ArrayList<String> getURL (String key){
        return assistantCache.get(key);
    }

    public ArrayList<String> containsKey (String key){
        ArrayList<String> defaultValue = new ArrayList<>();
        defaultValue.add("No Value for this Key");
        return assistantCache.getOrDefault(key, defaultValue);
    }

    public ArrayList<String> remove (String key){
        return assistantCache.remove(key);
    }

}
