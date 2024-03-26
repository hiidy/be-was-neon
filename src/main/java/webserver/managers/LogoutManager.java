package webserver.managers;

import java.io.File;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.session.SessionStore;
import webserver.utils.HttpMessageUtils;

public class LogoutManager {

    private static final String MAIN_INDEX_PATH = "/index.html";
    private static final String sourceRelativePath = "src/main/resources/static";

    public HttpResponse logoutResponse(HttpRequest httpRequest) {

        File file = new File(sourceRelativePath + MAIN_INDEX_PATH);
        byte[] body = HttpMessageUtils.readByteFromFile(file);

        String cookies = httpRequest.getHttpRequestHeader().getValue("Cookie").trim();
        String sessionId = findSessionIdFromCookies(cookies);

        SessionStore.removeSession(sessionId);

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.FOUND);
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader().setLocation(
            MAIN_INDEX_PATH);
        HttpResponseBody httpResponseBody = new HttpResponseBody(body);

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);
    }


    public String findSessionIdFromCookies(String cookies) {
        String[] cookiePairs = cookies.split(";");
        for (String pair : cookiePairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2 && keyValue[0].trim().equals("sid")) {
                return keyValue[1].trim();
            }
        }
        return "";
    }
}
