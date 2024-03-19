package webserver;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.utils.HttpMessageUtils;

public class RegisterManager {

    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String REDIRECT_PATH = "/index.html";
    private static final Map<String, String> registerInformation = new HashMap<>();

    public static HttpResponse registerResponse(HttpRequest httpRequest) {

        String requestURI = httpRequest.getHttpRequestStartLine().getRequestURI();
        File file = new File(sourceRelativePath + REDIRECT_PATH);

        byte[] body = HttpMessageUtils.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.FOUND);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setLocation(REDIRECT_PATH);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        if (httpRequest.getHttpRequestStartLine().getMethod().equals("POST")) {
            HttpMessageUtils.parseQueryString(httpRequest.getHttpRequestBody().getBodyMessage(),
                registerInformation);

        }
        if (httpRequest.getHttpRequestStartLine().getMethod().equals("GET")) {
            HttpMessageUtils.parseQueryString(
                httpRequest.getHttpRequestStartLine().getQueryString(),
                registerInformation);
        }
        User user = new User(registerInformation.get("userId"),
            registerInformation.get("nickName"),
            registerInformation.get("password"));

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }

    public static Map<String, String> getRegisterInformation() {
        return Collections.unmodifiableMap(registerInformation);
    }
}
