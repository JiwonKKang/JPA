package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        Long joinId = memberService.join(member);

        //then
        Assertions.assertThat(memberRepository.findOne(joinId)).isEqualTo(member);

    }

    @Test
    public void validateDuplicateTest() throws Exception {

        //given
        Member member1 = new Member();
        Member member2 = new Member();
        //when
        member1.setName("kang");
        member2.setName("kang");

        memberService.join(member1);

        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }
}