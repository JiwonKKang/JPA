package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCond memberSearchCond);

    Page<MemberTeamDto> searchSimple(MemberSearchCond memberSearchCond, Pageable pageable);

    Page<MemberTeamDto> searchComplex(MemberSearchCond memberSearchCond, Pageable pageable);

}
