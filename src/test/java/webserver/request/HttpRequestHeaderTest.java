package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestHeaderTest {

    private HttpRequestHeader httpRequestHeader;

    @BeforeEach
    void setUp() {
        httpRequestHeader = new HttpRequestHeader("Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Accept: */*\r\n"
            + "User-Agent: Mozilla/5.0\r\n"
            + "Accept-Encoding: gzip, deflate, br\r\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n"
            + "Cookie: _gat_gtag_UA_123456789_1=1\r\n"
            + "Content-Length: 0\r\n"
            + "\r\n");
    }


    @Test
    @DisplayName("header를 파싱하고 key값이 8개가 저장되었는지 확인하는 테스트")
    void parseHeaderAndVerifyKeysCount() {
        assertThat(httpRequestHeader.getHeaders().keySet().size()).isEqualTo(8);
    }

    @Test
    @DisplayName("header를 파싱하고 value가 총 8개가 저장되었는지 확인하는 테스트")
    void parseHeaderAndVerifyValuesCount() {
        assertThat(httpRequestHeader.getHeaders().values().size()).isEqualTo(8);
    }

    @Test
    @DisplayName("header를 파싱하고 Content-Length에 0 value가 저장되었는지 테스트")
    void parseContentLength() {
        assertThat(httpRequestHeader.getValue("Content-Length")).isEqualTo("0");
    }
}