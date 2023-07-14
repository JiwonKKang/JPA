package study.datajpa.entity;

import lombok.Getter;

@Getter
public class UserNameOnlyDto {

    private final String username;

    public UserNameOnlyDto(String username) {
        this.username = username;
    }
}
