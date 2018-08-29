package util;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

public class ReflectObject {
    private static String propertiesFileName;
    private static Map<String, Object> objectPool;

    public static String getPropertiesFileName() {
        return propertiesFileName;
    }

    public static void setPropertiesFileName(String propertiesFileName) {
        ReflectObject.propertiesFileName = propertiesFileName;
    }

    public static Object getObject(String key) {
        if (objectPool.isEmpty()) {
            try (
                    FileInputStream fis = new FileInputStream(propertiesFileName)
            ) {
                Properties props = new Properties();
                props.load(fis);
                for (String name : props.stringPropertyNames()) {
                    objectPool.put(name, CreatObject(props.getProperty(name)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objectPool.get(key);
    }

    public static Object CreatObject(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName(className);
        return clazz.newInstance();
    }

}
