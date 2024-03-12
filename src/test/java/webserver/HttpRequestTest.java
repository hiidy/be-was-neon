package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HttpRequestTest {

    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = new HttpRequest("GET /registration HTTP/1.1");
    }

    @Test
    @DisplayName("RequestLine으로부터 Method를 잘 파싱하는지 테스트")
    void getMethodFromRequestLine() {
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("RequestLine으로부터 URI를 잘 파싱하는지 테스트")
    void getURIFromRequestLine() {
        assertThat(httpRequest.getRequestURI()).isEqualTo("/registration");
    }

    @Test
    @DisplayName("query parameter가 있을 때 path를 파싱하는 테스트")
    void extractPathFromURI() {
        httpRequest = new HttpRequest(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        assertThat(httpRequest.getRequestURI()).isEqualTo("/create");
    }


    @ParameterizedTest
    @CsvSource({"userId, jayden", "nickname, 제이든", "password, 1234"})
    @DisplayName("query parameter가 있을 때 query를 파싱하는 테스트")
    void extractQueryFromURI(String key, String value) {
        httpRequest = new HttpRequest(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        Map<String, String> queries = httpRequest.getQueries();
        assertThat(queries.get(key)).isEqualTo(value);
    }
}

