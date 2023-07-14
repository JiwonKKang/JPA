package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void given_when_then() throws Exception {

        //Given
        Member member = new Member("memberA");
        //When
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findById(savedMember.getId()).get();
        //Then
        assertThat(savedMember.getId()).isEqualTo(findMember.getId());

    }

    @Test
    public void given1_when_then() throws Exception {

        //Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //When
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //Then

    }

    @Test
    public void given3_when_then() throws Exception {

        //Given
        memberJpaRepository.save(new Member("member1", 20));
        memberJpaRepository.save(new Member("member2", 20));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 20));
        memberJpaRepository.save(new Member("member5", 20));
        int age = 20;
        int offset = 0;
        int limit = 3;
        //When
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);
        //Then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);

    }

}