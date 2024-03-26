package webserver.managers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
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

public class IndexManager {

    private static final String sourceRelativePath = "src/main/resources/static";

    public HttpResponse indexResponse(HttpRequest httpRequest) {
        String requestURI = httpRequest.getHttpRequestStartLine().getRequestURI();

        if (requestURI.equals("/index.html")) {
            if (hasSession(httpRequest)) {
                requestURI = "/main/index.html";
            }
        }

        File file = new File(sourceRelativePath + requestURI);
        if (file.isDirectory()) {
            file = new File(sourceRelativePath + requestURI + "/" + "index.html");
        }

        byte[] body = HttpMessageUtils.readByteFromFile(file);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.OK);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader()
            .setContentType(ContentType.findContentType(requestURI))
            .setContentLength(body.length);

        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        if (requestURI.equals("/main/index.html")) {
            httpResponseBody = addAccountName(httpResponseBody, findUserId(
                httpRequest.getHttpRequestHeader().getHeaders().get("Cookie").split("=")[1]));
        }

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);

    }

    private String findUserId(String sessionId) {
        return SessionStore.getSessions().entrySet().stream()
            .filter(entry -> entry.getKey().equals(sessionId))
            .map(entry -> entry.getValue().getAttribute("user"))
            .map(user -> ((User) user).getUserId())
            .findFirst()
            .orElse("");
    }


    private HttpResponseBody addAccountName(HttpResponseBody httpResponseBody, String userId) {
        String bodyMessage = new String(httpResponseBody.getHttpResponseBodyMessage());
        Pattern pattern = Pattern.compile("%account%");
        Matcher matcher = pattern.matcher(bodyMessage);
        return new HttpResponseBody(matcher.replaceAll(userId).getBytes());
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
