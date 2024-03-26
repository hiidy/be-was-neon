package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.managers.IndexManager;
import webserver.managers.LoginManager;
import webserver.managers.LogoutManager;
import webserver.managers.RegisterManager;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class HttpMessageHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpMessageHandler.class);
    private final Socket connection;

    public HttpMessageHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader inputRequest = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = new HttpRequest(inputRequest);

            HttpResponse httpResponse = selectManager(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);

            String responseMessage = httpResponse.getHttpResponseMessage();
            dos.write(responseMessage.getBytes());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse selectManager(HttpRequest httpRequest) {
        if (httpRequest.getHttpRequestStartLine().getRequestURI().startsWith("/create")) {
            return new RegisterManager().registerResponse(httpRequest);
        } else if (httpRequest.getHttpRequestStartLine().getRequestURI().startsWith("/join")) {
            return new LoginManager().loginResponse(httpRequest);
        } else if (httpRequest.getHttpRequestStartLine().getRequestURI().startsWith("/logout")) {
            return new LogoutManager().logoutResponse(httpRequest);
        }
        return new IndexManager().indexResponse(httpRequest);
    }

}
