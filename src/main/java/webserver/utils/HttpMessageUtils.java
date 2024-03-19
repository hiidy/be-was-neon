package webserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpMessageUtils {

    public static byte[] readByteFromFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(bytes);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseQueryString(String bodyMessage, Map<String, String> queryMap) {

        String[] pairs = bodyMessage.split("&");

        for (String pair : pairs) {

            String[] keyValue = pair.split("=");

            String key = keyValue[0];
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

            queryMap.put(key, value);
        }
    }

}
