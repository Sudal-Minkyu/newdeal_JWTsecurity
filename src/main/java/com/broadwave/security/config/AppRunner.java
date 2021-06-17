package com.broadwave.security.config;

import com.broadwave.security.account.Account;
import com.broadwave.security.account.AccountRepository;
import com.broadwave.security.account.AccountRole;
import com.broadwave.security.teams.Team;
import com.broadwave.security.teams.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 최초실행시 테스트할 계정임시로 만들기
    @Override
    public void run(ApplicationArguments args) {

        // 부서저장
        Team team = Team.builder()
                .teamcode("T00001")
                .teamname("시스템관리자")
                .remark("뉴딜 시스템관리 부서생성")
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .build();
        Optional<Team> teamValue = teamRepository.findByTeamcode("T00001");
        if(teamValue.isPresent()) {
            log.info("시스템 Team 존재");
        }else{
            log.info("시스템 Team 생성");
            teamRepository.save(team);
        }

        // 관리자저장
        Account account = Account.builder()
                .userid("admin")
                .username("관리자")
                .email("admin@mail.com")
                .password(passwordEncoder.encode("123789"))
                .insertDateTime(LocalDateTime.now())
                .insert_id("system")
                .role(AccountRole.ROLE_ADMIN)
                .team(teamValue.get())
                .build();
        Optional<Account> adminAccount = accountRepository.findByUsername(account.getUsername());
        if(adminAccount.isPresent()) {
            log.info("관리자 admin 존재");
        }else{
            log.info("관리자 admin 생성");
            accountRepository.save(account);
        }

    }

}
