package jsonFiles;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ObjectProcessingJSON {
    // Сохраняем содержимое обьекта в файл
    public static void save(String pathToJsonFile, Object object) throws IOException {
        FileWriter writer = new FileWriter(pathToJsonFile);
        String json = JSON.toJSONString(object);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    // Загружаем новый обьект из сохраненной версии
    public static Object load(String pathToJsonFile, Object object) throws FileNotFoundException {
        String json = new Scanner(new File(pathToJsonFile)).useDelimiter("\\Z").next();
        Object jsonObject;
        jsonObject = JSON.parseObject(json, object.getClass());
        return jsonObject;
    }

}
