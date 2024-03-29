package webserver.managers;

import db.Database;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import org.slf4j.Logger;
import webserver.request.HttpRequest;
import webserver.response.ContentType;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.session.SessionStore;
import webserver.utils.HttpMessageUtils;

public class UserListManager {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserListManager.class);

    private static final String sourceRelativePath = "src/main/resources/static";
    private static final String USER_TABLE_HTML = "<tr><td>%d</td><td>%s</td><td>%s</td><tr/>";
    private static final String USER_LIST_PLACEHOLDER = "%userList%";

    public HttpResponse userListResponse(HttpRequest httpRequest) {
        String requestURI = httpRequest.getHttpRequestStartLine().getRequestURI();

        File file = new File(sourceRelativePath + requestURI);
        byte[] body = HttpMessageUtils.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.OK);
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setContentType(ContentType.findContentType(requestURI))
            .setContentLength(body.length);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        if (!hasSession(httpRequest)) {
            HttpResponse httpResponse = new HttpResponse(httpResponseStatusLine, httpResponseHeader,
                httpResponseBody);
            httpResponse.sendRedirect(HttpStatus.FOUND, "/login/index.html");
            return httpResponse;
        }

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader,
            addUserList(httpResponseBody));
    }

    private HttpResponseBody addUserList(HttpResponseBody httpResponseBody) {
        StringBuilder userListHTML = new StringBuilder();

        List<User> users = Database.findAll();

        for (int i = 0; i < users.size(); i++) {
            userListHTML.append(
                USER_TABLE_HTML.formatted(i + 1, users.get(i).getUserId(),
                    users.get(i).getNickName()));
        }

        return new HttpResponseBody(
            replaceUserListPlaceholder(httpResponseBody.getHttpResponseBodyMessage(),
                userListHTML.toString()).getBytes());
    }

    private String replaceUserListPlaceholder(String body, String userListHTML) {
        Pattern pattern = Pattern.compile(USER_LIST_PLACEHOLDER);
        Matcher matcher = pattern.matcher(body);
        logger.debug("userListHTML: {}", userListHTML);
        return matcher.replaceFirst(userListHTML);
    }

    private boolean hasSession(HttpRequest httpRequest) {
        if (httpRequest.getHttpRequestHeader().getHeaders().containsKey("Cookie")) {
            String userSID = httpRequest.getHttpRequestHeader().getHeaders().get("Cookie")
                .split("=")[1];
            return SessionStore.getSessions().entrySet().stream()
                .anyMatch(entry -> entry.getKey().equals(userSID));
        }
        return false;
    }

}
