package webserver.response;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseBodyTest {

    @Test
    @DisplayName("Response의 Body의 길이를 반환하는지 테스트")
    void getResponseBodyLength() {
        HttpResponseBody httpResponseBody = new HttpResponseBody("Hello, World!");
        Assertions.assertThat(httpResponseBody.getResponseBodyLength()).isEqualTo(13);
    }
}