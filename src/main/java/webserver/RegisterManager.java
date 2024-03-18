package webserver;

import java.io.File;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.utils.ReadingFiles;

public class RegisterManager {

    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String REDIRECT_PATH = "/index.html";

    public static HttpResponse registerResponse(HttpRequest httpRequest) {

        String requestURI = httpRequest.getHttpRequestStartLine().getRequestURI();
        File file = new File(sourceRelativePath + REDIRECT_PATH);

        byte[] body = ReadingFiles.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.FOUND);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setLocation(REDIRECT_PATH);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        User user = new User(httpRequest.getHttpRequestStartLine().getValue("userID"),
            httpRequest.getHttpRequestStartLine().getValue("nickName"),
            httpRequest.getHttpRequestStartLine().getValue("password"));

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }
}
