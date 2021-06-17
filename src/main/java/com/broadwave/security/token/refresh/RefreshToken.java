package com.broadwave.security.token.refresh;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name ="bs_team_account_jwt_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name="jwt_username")
    private String username;

    @Column(name="jwt_value")
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshToken(String username, String value) {
        this.username = username;
        this.value = value;
    }
}
