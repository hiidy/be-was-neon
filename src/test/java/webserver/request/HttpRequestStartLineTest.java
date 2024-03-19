package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestStartLineTest {

    private HttpRequestStartLine httpRequestStartLine;

    @BeforeEach
    void setUp() {
        httpRequestStartLine = new HttpRequestStartLine("GET /registration HTTP/1.1");
    }

    @Test
    @DisplayName("RequestLine으로부터 Method를 잘 파싱하는지 테스트")
    void getMethodFromRequestLine() {
        assertThat(httpRequestStartLine.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("RequestLine으로부터 URI를 잘 파싱하는지 테스트")
    void getURIFromRequestLine() {
        assertThat(httpRequestStartLine.getRequestURI()).isEqualTo("/registration");
    }

    @Test
    @DisplayName("query parameter가 있을 때 path를 파싱하는 테스트")
    void extractPathFromURI() {
        httpRequestStartLine = new HttpRequestStartLine(
            "GET /create?userId=jayden&nickname=%EC%A0%9C%EC%9D%B4%EB%93%A0&password=1234 HTTP/1.1");
        assertThat(httpRequestStartLine.getRequestURI()).isEqualTo("/create");
    }

}

