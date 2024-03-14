package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.request.HttpRequestLine;

class HttpRequestLineTest {

    private HttpRequestLine httpRequestLine;

    @BeforeEach
    void setUp() {
        httpRequestLine = new HttpRequestLine("GET /registration HTTP/1.1");
    }

    @Test
    @DisplayName("RequestLine으로부터 Method를 잘 파싱하는지 테스트")
    void getMethodFromRequestLine() {
        assertThat(httpRequestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("RequestLine으로부터 URI를 잘 파싱하는지 테스트")
    void getURIFromRequestLine() {
        assertThat(httpRequestLine.getRequestURI()).isEqualTo("/registration");
    }

    @Test
    @DisplayName("query parameter가 있을 때 path를 파싱하는 테스트")
    void extractPathFromURI() {
        httpRequestLine = new HttpRequestLine(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        assertThat(httpRequestLine.getRequestURI()).isEqualTo("/create");
    }


    @ParameterizedTest
    @CsvSource({"userId, jayden", "nickname, 제이든", "password, 1234"})
    @DisplayName("query parameter가 있을 때 query를 파싱하는 테스트")
    void extractQueryFromURI(String key, String value) {
        httpRequestLine = new HttpRequestLine(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        Map<String, String> queries = httpRequestLine.getQueries();
        assertThat(queries.get(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("HttpRequest객체로부터 파라미터를 불러와서 User 객체가 잘 만들어지는지 테스트")
    void testCreateUserFromQueryParms() {
        httpRequestLine = new HttpRequestLine(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        User user = new User(httpRequestLine.getValue("userId"),
            httpRequestLine.getValue("nickName"), httpRequestLine.getValue("password"));
        assertThat(user.getUserId()).isEqualTo("jayden");
    }
}

