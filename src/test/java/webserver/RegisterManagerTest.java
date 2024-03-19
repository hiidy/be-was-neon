package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

class RegisterManagerTest {

    private HttpRequest getHttpRequest;
    private HttpRequest postHttpRequest;

    @BeforeEach
    void setUp() throws IOException {
        String getHttpRequestString =
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1\n"
                +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n"
                +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36\n"
                +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: en-US,en;q=0.9\n" +
                "\n";

        String postHttpRequestString =
            "POST /create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Content-Length: 64\n" + // 바디 길이 설정
                "\n" +
                "userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234"; // POST 요청 바디

        BufferedReader getReader = new BufferedReader(new StringReader(getHttpRequestString));
        BufferedReader postReader = new BufferedReader(new StringReader(postHttpRequestString));
        getHttpRequest = new HttpRequest(getReader);
        postHttpRequest = new HttpRequest(postReader);
    }

    @Test
    @DisplayName("GET 요청일 때 userId 정보가 Register Manager에 잘 저장되는지 확인하는 테스트")
    void getUserIdFromGetRequest() {
        HttpResponse httpResponse = RegisterManager.registerResponse(getHttpRequest);
        assertThat(RegisterManager.getRegisterInformation().get("userId")).isEqualTo("jayden");
    }

    @Test
    @DisplayName("GET 요청일 때 nickName 정보가 Register Manager에 잘 저장되는지 확인하는 테스트")
    void getNicknameFromGetRequest() {
        HttpResponse httpResponse = RegisterManager.registerResponse(getHttpRequest);
        assertThat(RegisterManager.getRegisterInformation().get("nickname")).isEqualTo("제이든");
    }

    @Test
    @DisplayName("POST 요청일 때 nickName 정보가 Register Manager에 잘 저장되는지 확인하는 테스트")
    void getUserIdFromPostRequest() {
        HttpResponse httpResponse = RegisterManager.registerResponse(postHttpRequest);
        assertThat(RegisterManager.getRegisterInformation().get("userId")).isEqualTo("jayden");
    }

    @Test
    @DisplayName("GET 요청일 때 nickName 정보가 Register Manager에 잘 저장되는지 확인하는 테스트")
    void getNicknameFromPostRequest() {
        HttpResponse httpResponse = RegisterManager.registerResponse(postHttpRequest);
        assertThat(RegisterManager.getRegisterInformation().get("nickname")).isEqualTo("제이든");
    }

    @Test
    @DisplayName("회원가입 Request를보내고 User 객체가 잘 만들어지는지 테스트")
    void testCreateUserFromQueryParms() {
        HttpResponse httpResponse = RegisterManager.registerResponse(postHttpRequest);
        User user = new User(RegisterManager.getRegisterInformation().get("userId"),
            RegisterManager.getRegisterInformation().get("nickname"),
            RegisterManager.getRegisterInformation().get("password"));
        assertThat(user.getUserId()).isEqualTo("jayden");
    }
}