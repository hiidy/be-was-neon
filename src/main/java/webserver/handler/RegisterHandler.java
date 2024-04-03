package webserver.handler;

import db.Database;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.utils.HttpMessageUtils;

public class RegisterHandler {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterHandler.class);

    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String REDIRECT_PATH = "/index.html";
    private static final Map<String, String> registerInformation = new HashMap<>();

    public HttpResponse registerResponse(HttpRequest httpRequest) {

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
            registerInformation.get("password"),
            registerInformation.get("nickName"));

        logger.debug("User ID {} success", registerInformation.get("userId"));
        logger.debug("User NickName {} success", registerInformation.get("nickName"));
        logger.debug("User Password {} success", registerInformation.get("password"));
        Database.addUser(user);

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }

    public Map<String, String> getRegisterInformation() {
        return Collections.unmodifiableMap(registerInformation);
    }
}