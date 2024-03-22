package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Cookie;

class HttpResponseHeaderTest {

    @Test
    @DisplayName("Response의 Header가 옳바른 메시지를 던지는지 테스트")
    void getHttpResponseHeaderMessage() {
        String body = "Hello, World!";
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.setLocation("/index.html")
            .setContentType(ContentType.HTML)
            .setContentLength(body.length());

        String expected = "Location: /index.html\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: 13";
        assertThat(httpResponseHeader.getHttpResponseHeaderMessage()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Response의 Header에 Set Cookie가 옳바르게 추가되는지 테스트")
    void testSetCookie() {
        Cookie cookie = new Cookie();
        cookie.setSessionID("1234").setPath("/");
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.setCookie(cookie);

        String expected = "Set-Cookie: Path=/; sid=1234";
        assertThat(httpResponseHeader.getHttpResponseHeaderMessage()).isEqualTo(expected);
    }
}