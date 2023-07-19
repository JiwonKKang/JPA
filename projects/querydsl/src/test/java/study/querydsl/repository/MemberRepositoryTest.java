package study.querydsl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        em.persist(new Member("user1", 10, teamA));
        em.persist(new Member("user2", 20, teamA));
        em.persist(new Member("user3", 30, teamB));
    }

    @Test
    public void givenMemberSearchCond_whenSearchMember_thenReturnResult() throws Exception {

        //Given
        MemberSearchCond memberSearchCond = new MemberSearchCond();
        memberSearchCond.setTeamName("teamA");
        memberSearchCond.setAgeLoe(30);
        memberSearchCond.setAgeGoe(1);

        //When
        List<MemberTeamDto> memberTeamDtos = memberRepository.search(memberSearchCond);
        //Then
        for (MemberTeamDto memberTeamDto : memberTeamDtos) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
    }

}
