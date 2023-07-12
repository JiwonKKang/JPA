package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.time.temporal.TemporalAccessor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamJpaRepository teamJpaRepository;

    @Test
    public void given_when_then() throws Exception {

        //Given
        Member member = new Member("memberA");
        //When
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        //Then
        assertThat(savedMember.getId()).isEqualTo(findMember.getId());
    }

    @Test
    public void givenMember_whenFindUser_thenSuccess() throws Exception {

        //Given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //When
        List<Member> members = memberRepository.findUser("AAA", 10);
        //Then
        assertThat(members.get(0)).isEqualTo(member1);

    }

    @Test
    public void given1_when_then() throws Exception {

        //Given
        Team teamAQ = new Team("teamAQ");
        Member member = new Member("AAA", 10);
        member.changeTeam(teamAQ);
        //When
        teamJpaRepository.save(teamAQ);
        //Then

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("UserName = " + dto.getUserName());
        }

    }

}