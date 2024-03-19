package webserver;

import java.io.File;
import webserver.request.HttpRequest;
import webserver.response.ContentType;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseBody;
import webserver.response.HttpResponseHeader;
import webserver.response.HttpResponseStatusLine;
import webserver.response.HttpStatus;
import webserver.response.HttpVersion;
import webserver.utils.HttpMessageUtils;

public class IndexManager {

    private static final String sourceRelativePath = "src/main/resources/static";

    public static HttpResponse indexResponse(HttpRequest httpRequest) {
        String requestURI = httpRequest.getHttpRequestStartLine().getRequestURI();

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

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader, httpResponseBody);

    }
}
