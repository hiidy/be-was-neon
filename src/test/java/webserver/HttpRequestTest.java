package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}