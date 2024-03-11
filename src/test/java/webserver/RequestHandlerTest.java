package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHandlerTest {

    @Test
    @DisplayName("RequestLine에서 요청 경로 추출 테스트")
    void testExtractPathFromRequestLine() {

        String httpMessage = "GET /index.html HTTP/1.1\nHost: localhost:8080\n";

        String expectedPath = "/index.html";

        RequestHandler requestHandler = new RequestHandler(null);
        assertThat(requestHandler.extractPathFromRequestLine(httpMessage))
            .isEqualTo(expectedPath);
    }

    @Test
    @DisplayName("readByteFromFile함수가 readallBytes와 같은 기능을 하는지 테스트")
    void testReadByteTest() throws IOException {

        File file = new File("src/main/resources/static/index.html");

        RequestHandler requestHandler = new RequestHandler(null);

        byte[] actualBytes = requestHandler.readByteFromFile(file);

        byte[] expectedBytes = Files.readAllBytes(file.toPath());

        assertThat(actualBytes).isEqualTo(expectedBytes);
    }

}