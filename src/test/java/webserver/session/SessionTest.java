package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {

    User user;
    User getUser;

    @BeforeEach
    void setUp() {
        user = new User("jayden", "1234", "제이든");
    }

    @Test
    @DisplayName("user 세션이 정상적으로 생성되는지 테스트")
    void getAttribute() {
        Session session = new Session("user");
        session.setAttribute("user", user);

        assertThat(session.getAttribute("user")).isEqualTo(user);

    }

}