import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AssistantCache {

    HashMap<String, ArrayList<String>> assistantCache;

    public AssistantCache (){
        assistantCache = new HashMap<>();
        File assistant = new File ("assistantCache.ser");
        try {
            assistant.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void retrieveHashmaps () {
        try
        {
            FileInputStream fis = new FileInputStream("assistantCache.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            assistantCache = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (EOFException ignored){
            // all g fam
            return;
        } catch(IOException ioe)
        {
            ioe.printStackTrace();
            return;
        } catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("Deserialized HashMap..");

    }

    public void saveHashmaps () {
        try {
            FileOutputStream assistantCacheFile = new FileOutputStream("assistantCache.ser");
            ObjectOutputStream assistantOOS = new ObjectOutputStream(assistantCacheFile);
            assistantOOS.writeObject(assistantCache);
            assistantOOS.close();
            assistantCacheFile.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
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
