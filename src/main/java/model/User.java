package model;

public class User {

    private final String userId;
    private final String password;
    private final String nickName;

    public User(String userId, String password, String nickName) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }


    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", nickName=" + nickName + "]";
    }
}
