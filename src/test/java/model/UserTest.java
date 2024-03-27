package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("유저 객체가 직접 패스워드를 검증할 수 있는지 테스트")
    void validatePassword() {
        User user = new User("user1", "1234", "유저1");
        assertThat(user.validatePassword("1234")).isTrue();
    }
}