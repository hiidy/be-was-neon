package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseStatusLineTest {

    @Test
    @DisplayName("Response의 StatusLine이 옳바른 메시지를 던지는지 테스트")
    void getHttpStatusLineMessage() {

        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(
            HttpVersion.HTTP11, HttpStatus.OK);

        assertThat(httpResponseStatusLine.getHttpStatusLineMessage()).isEqualTo(
            "HTTP/1.1 200 OK");

    }
}