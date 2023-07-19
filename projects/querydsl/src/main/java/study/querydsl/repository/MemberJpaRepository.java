package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Member> findAll_Query() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<MemberTeamDto> searchMember(MemberSearchCond cond) {

        BooleanBuilder builder = new BooleanBuilder();

        if (hasText(cond.getUsername())) {
            builder.and(member.username.eq(cond.getUsername()));
        }

        if (hasText(cond.getTeamName())) {
            builder.and(team.name.eq(cond.getTeamName()));
        }

        if (cond.getAgeGoe() != null) {
            builder.and(member.age.goe(cond.getAgeGoe()));
        }

        if (cond.getAgeLoe() != null) {
            builder.and(member.age.loe(cond.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();
    }
}
