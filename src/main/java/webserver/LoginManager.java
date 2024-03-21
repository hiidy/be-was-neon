package webserver;

import db.Database;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.utils.HttpMessageUtils;

public class LoginManager {

    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String REGISTER_PATH = "/registration/index.html";
    private static final String LOGIN_PATH = "/login/index.html";
    private static final Map<String, String> loginInformation = new HashMap<>();

    public HttpResponse loginResponse(HttpRequest httpRequest) {

        HttpMessageUtils.parseQueryString(httpRequest.getHttpRequestBody().getBodyMessage(),
            loginInformation);
        String userId = loginInformation.get("userId");
        String password = loginInformation.get("password");

        if (!findUserId(userId)) {
            return redirectPage(REGISTER_PATH);
        }

        if (!isCorrectPassword(userId, password)) {
            return redirectPage(LOGIN_PATH);
        }

        throw new UnsupportedOperationException("not implemented yet");
    }

    private boolean findUserId(String userId) {
        Optional<User> optionalUser = Optional.ofNullable(Database.findUserById(userId));
        return optionalUser.isPresent();
    }

    private boolean isCorrectPassword(String userId, String inputPassword) {
        return Database.findUserById(userId).validatePassword(inputPassword);
    }

    private HttpResponse redirectPage(String redirectPath) {

        File file = new File(sourceRelativePath + redirectPath);
        byte[] body = HttpMessageUtils.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.FOUND);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setLocation(redirectPath);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }


}
