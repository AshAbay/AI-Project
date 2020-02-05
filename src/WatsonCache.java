import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WatsonCache {

    HashMap<String, ArrayList<String>> watsonCache;

    public WatsonCache () throws IOException {
        watsonCache = new HashMap<>();
        File watson = new File ("watsonCache.ser");
        watson.createNewFile();
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

    public void retrieveHashmaps () {
        try
        {
            FileInputStream fis = new FileInputStream("watsonCache.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            watsonCache = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (EOFException ignored) {
            // all g fam
            return;
        } catch(IOException ioe) {
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
            FileOutputStream watsonCacheFile = new FileOutputStream("watsonCache.ser");
            ObjectOutputStream watsonOOS = new ObjectOutputStream(watsonCacheFile);
            watsonOOS.writeObject(watsonCache);
            watsonOOS.close();
            watsonCacheFile.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
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
