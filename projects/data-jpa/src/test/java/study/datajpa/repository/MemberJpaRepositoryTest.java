package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

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

}