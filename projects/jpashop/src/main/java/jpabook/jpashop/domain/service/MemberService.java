package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) { // 회원가입
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public List<Member> findMembers() { // 회원목록 조회
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) { // 회원 단건 조회
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) { // 회원 중복 검증
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
    }

}
