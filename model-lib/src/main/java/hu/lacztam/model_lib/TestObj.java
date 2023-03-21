package hu.lacztam.model_lib;

import java.io.Serializable;

public class TestObj implements Serializable {
    private String password;
    private Long userId;

    public TestObj() {
    }

    public TestObj(String password, Long userId) {
        this.password = password;
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TestObj{" +
                "password='" + password + '\'' +
                ", userId=" + userId +
                '}';
    }
}
