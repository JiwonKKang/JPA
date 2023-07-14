package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.entity.UserNameOnlyDto;

import javax.persistence.EntityManager;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
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

    @Autowired
    EntityManager em;

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

//        List<MemberDto> memberDto = memberRepository.findMemberDto();
//        for (MemberDto dto : memberDto) {
//            System.out.println("UserName = " + dto.getUserName());
//        }

    }

    @Test
    public void given3_when_then() throws Exception {

        //Given
        memberRepository.save(new Member("member1", 20));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 20));
        memberRepository.save(new Member("member5", 20));

        int age = 20;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

         //When
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> members = page.getContent();
//        long totalElements = page.getTotalElements();
        //Then
        assertThat(members.size()).isEqualTo(3);
//        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
//        assertThat(totalElements).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
    }

    @Test
    public void given4_when_then() throws Exception {

        //Given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        teamJpaRepository.save(teamA);
        teamJpaRepository.save(teamB);
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.team = " + member.getTeam().getName());
        }

        //When

        //Then
    }

    @Test
    public void given2_when_then() throws Exception {

        //Given
        Team teamA = new Team("teamA");
        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(teamA);
        em.flush();
        em.clear();
        //When

        //Then
        List<UserNameOnlyDto> res = memberRepository.findByUsername("m1");

        for (UserNameOnlyDto re : res) {
            System.out.println("re.getUsername() = " + re.getUsername());
        }

    }

}