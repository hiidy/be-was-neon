package webserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadingFiles {

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

}
