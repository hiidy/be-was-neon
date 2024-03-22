package webserver;

import db.Database;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.session.Session;
import webserver.session.SessionStore;
import webserver.utils.HttpMessageUtils;

public class LoginManager {

    private static final Logger logger = LoggerFactory.getLogger(LoginManager.class);
    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String REGISTER_PATH = "/registration/index.html";
    private static final String LOGIN_PATH = "/login/index.html";
    private static final String MAIN_INDEX_PATH = "/index.html";
    private static final Map<String, String> loginInformation = new HashMap<>();
    private static final SessionStore sessionStore = new SessionStore();

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

        logger.debug("User ID {} success", userId);
        Session session = new Session(createSession());
        sessionStore.addSession(session, userId);
        Cookie cookie = new Cookie(session.getSessionId());
        cookie.setPath("/");
        return successLogin(MAIN_INDEX_PATH, cookie);
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

    private HttpResponse successLogin(String redirectPath, Cookie cookie) {

        File file = new File(sourceRelativePath + redirectPath);
        byte[] body = HttpMessageUtils.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.FOUND);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setLocation(redirectPath).setCookie(cookie);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }

    private String createSession() {
        return UUID.randomUUID().toString();
    }


}
