package org.ryan;

import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @Author Ryan Yuan
 * @Description
 * @Create 2025-03-30 22:29
 */
public class JsonPathTest {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/temp.json");
        if (Files.exists(path)) {
            // 读取文件内容
            byte[] bytes = Files.readAllBytes(path);
            String content = new String(bytes);

            Object read = JsonPath.read(content, "$.store.book[0].author");
            System.out.println(read);
        } else {
            System.out.println("文件未找到");
        }
    }

}


