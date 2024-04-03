package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieTest {

    private Cookie cookie;


    @BeforeEach
    void setUp() {
        cookie = new Cookie("sid", "12345");
    }


    @Test
    @DisplayName("쿠키클래스의 attribute를 set하는 기능 테스트")
    void setAttribute() {
        cookie.setAttribute("name", "jayden");
        assertThat(cookie.getAttributeValue("name")).isEqualTo("jayden");
    }

    @Test
    @DisplayName("쿠키의 path 특성이 잘 설정되는지 테스트")
    void setPath() {
        cookie.setPath("/");
        assertThat(cookie.getAttributeValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키의 domain 특성이 잘 설정되는지 테스트")
    void setDomain() {
        cookie.setDomain("http://localhost:8080/");
        assertThat(cookie.getAttributeValue("Domain")).isEqualTo("http://localhost:8080/");
    }

    @Test
    @DisplayName("쿠키의 Max-Age를 1초로 입력할 때 특성이 잘 설정되는지 테스트")
    void setMaxAge() {
        cookie.setMaxAge(1);
        assertThat(cookie.getAttributeValue("Max-Age")).isEqualTo("1");
    }

    @Test
    void getCookies() {
    }


}