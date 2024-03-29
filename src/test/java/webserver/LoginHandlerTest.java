package webserver;

import db.Database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.LoginHandler;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

class LoginHandlerTest {

    private Database db;
    private HttpRequest wrongIdRequest;
    private HttpRequest wrongPasswordRequest;

    @BeforeEach
    void setUp() throws IOException {
        db = new Database();
        User user1 = new User("user1", "1234", "유저1");
        User user2 = new User("user2", "2345", "유저2");
        User user3 = new User("user3", "4556", "유저3");
        db.addUser(user1);
        db.addUser(user2);
        db.addUser(user3);

        String wrongId = """
            POST /login HTTP/1.1
            Host: localhost:8080
            Connection: keep-alive
            Content-Type: application/x-www-form-urlencoded
            Content-Length: 64
                        
            userId=user4&password=1234&nickname=유저4
            """;

        String wrongPassword = """
            POST /login HTTP/1.1
            Host: localhost:8080
            Connection: keep-alive
            Content-Type: application/x-www-form-urlencoded
            Content-Length: 64
                        
            userId=user1&password=2345&nickname=유저1
            """;

        BufferedReader loginReader = new BufferedReader(new StringReader(wrongId));
        wrongIdRequest = new HttpRequest(loginReader);
        BufferedReader loginReader2 = new BufferedReader(new StringReader(wrongPassword));
        wrongPasswordRequest = new HttpRequest(loginReader2);
    }

    @Test
    @DisplayName("없는 userId로 로그인 했을 때 회원가입 페이지로 redirect 되는지 테스트")
    void testNoneExistingUserId() {
        LoginHandler loginHandler = new LoginHandler();
        String userId = "user4";

        loginHandler.loginResponse(wrongIdRequest);
        HttpResponse response = loginHandler.loginResponse(wrongIdRequest);
        Assertions.assertThat(response.getHttpResponseHeader().getHeaderValue("Location"))
            .isEqualTo("/registration/index.html");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 했을 때 로그인 페이지로 redirect 되는지 테스트")
    void testWrongPassword() {
        LoginHandler loginHandler = new LoginHandler();
        String userId = "user1";
        String password = "2345";

        loginHandler.loginResponse(wrongPasswordRequest);
        HttpResponse response = loginHandler.loginResponse(wrongPasswordRequest);
        Assertions.assertThat(response.getHttpResponseHeader().getHeaderValue("Location"))
            .isEqualTo("/login/index.html");
    }
}