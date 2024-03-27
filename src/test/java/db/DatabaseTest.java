package db;

import static org.assertj.core.api.Assertions.assertThat;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("jayden", "1234", "제이든");
    }

    @Test
    @DisplayName("DB에 user객체를 추가하면 저장이 되는지 확인하는 테스트")
    void testAddUser() {
        Database.addUser(user);
        User jayden = Database.findUserById("jayden");
        assertThat(user).isEqualTo(jayden);
    }

    @Test
    @DisplayName("유저를 3명을 더 추가했을 때 DB에 총 4명이 존재하는지 테스트")
    void testFindAll() {
        Database.addUser(new User("user1", "1234", "사용자1"));
        Database.addUser(new User("user2", "1234", "사용자2"));
        Database.addUser(new User("user3", "1234", "사용자3"));

        assertThat(Database.findAll().size()).isEqualTo(4);
    }
}