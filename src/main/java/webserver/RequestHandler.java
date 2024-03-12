package webserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader inputRequest = new BufferedReader(new InputStreamReader(in));
            String requestLine = readRequestLine(inputRequest);
            String path = extractPathFromRequestLine(requestLine);

            File file = new File("src/main/resources/static" + path);

            if (file.isDirectory()) {
                file = new File(file, "index.html");
            }

            HttpRequest httpRequest = new HttpRequest(requestLine);
            DataOutputStream dos = new DataOutputStream(out);

            if (path.startsWith("/create")) {
                User user = new User(httpRequest.getValue("userID"),
                    httpRequest.getValue("nickName"), httpRequest.getValue("password"));
                response302(dos);
                return;
            }

            byte[] body = readByteFromFile(file);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302(DataOutputStream dos) {
        String redirectURL = "/index.html";
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND\r\n");
            dos.writeBytes("Location: " + redirectURL + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String readRequestLine(BufferedReader httpRequest) throws IOException {
        StringBuilder requestBuilder = new StringBuilder();
        String line;

        if ((line = httpRequest.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append('\n');
            logger.debug("Request Line: {}", line);
        }

        return requestBuilder.toString();
    }

    public String extractPathFromRequestLine(String httpMessage) {
        String[] httpMessages = httpMessage.split("\n");
        String[] requestLine = httpMessages[0].split(" ");
        return requestLine[1];
    }

    public byte[] readByteFromFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(fileInputStream.readAllBytes());
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
