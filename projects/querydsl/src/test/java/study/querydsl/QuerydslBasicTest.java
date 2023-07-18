package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

@SpringBootTest
@Transactional
@Commit
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

//    @Test
//    public void sort() throws Exception {
//
//        //Given
//        em.persist(new Member(null, 100));
//        em.persist(new Member("member5", 100));
//        em.persist(new Member("member6", 100));
//        //When
//        List<Member> res = queryFactory
//                .selectFrom(member)
//                .where(member.age.eq(100))
//                .orderBy(member.age.desc(), member.username.asc().nullsLast())
//                .fetch();
//        //Then
//        assertThat(res.get(0).getUsername()).isEqualTo("member5");
//        assertThat(res.get(1).getUsername()).isEqualTo("member6");
//        assertThat(res.get(2).getUsername()).isNull();
//    }
//
//    @Test
//    public void aggregation() throws Exception {
//
//        //Given
//
//
//        //When
//        List<Tuple> res = queryFactory
//                .select(
//                        member.count(),
//                        member.age.sum(),
//                        member.age.avg(),
//                        member.age.max(),
//                        member.age.min()
//                )
//                .from(member)
//                .fetch();
//        //Then
//        Tuple tuple = res.get(0);
//        assertThat(tuple.get(member.count())).isEqualTo(4);
//        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
//    }
//
//    /**
//     *팀의 이름과 각 팀의 평균 연령을 구해라.
//     */
//    @Test
//    public void group() throws Exception {
//
//        //Given
//        List<Tuple> res = queryFactory
//                .select(team.name, member.age.avg())
//                .from(member)
//                .join(member.team, team)
//                .groupBy(team.name)
//                .fetch();
//        //When
//        Tuple teamA = res.get(0);
//        Tuple teamB = res.get(1);
//        //Then
//        assertThat(teamA.get(team.name)).isEqualTo("teamA");
//        assertThat(teamB.get(team.name)).isEqualTo("teamB");
//        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
//        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
//    }
//
//    @Test
//    public void join() throws Exception {
//
//        //Given
//        List<Member> res = queryFactory
//                .selectFrom(member)
//                .leftJoin(member.team, team)
//                .where(team.name.eq("teamA"))
//                .fetch();
//        //When
//
//        //Then
//        assertThat(res)
//                .extracting("username")
//                .containsExactly("member1", "member2");
//    }
//
//
//    /*
//    *연관관계가 없는 조인(세타조인)*/
//    @Test
//    public void theta_join() throws Exception {
//
//        //Given
//        em.persist(new Member("teamA"));
//        em.persist(new Member("teamB"));
//        //When
//        List<Member> res = queryFactory
//                .select(member)
//                .from(member, team)
//                .where(member.username.eq(team.name))
//                .fetch();
//
//        //Then
//        assertThat(res)
//                .extracting("username")
//                .containsExactly("teamA", "teamB");
//
//    }
//
//    @Test
//    public void join_on() throws Exception {
//
//        //Given
//        List<Tuple> res = queryFactory
//                .select(member, team)
//                .from(member)
//                .leftJoin(member.team, team).on(team.name.eq("teamA"))
//                .fetch();
//        //When
//
//        //Then
//        for (Tuple re : res) {
//            System.out.println("re = " + re);
//        }
//    }
//
//    @Test
//    public void join_on_no_relation() throws Exception {
//
//        //Given
//        List<Tuple> res = queryFactory
//                .select(member, team)
//                .from(member)
//                .leftJoin(team).on(member.username.eq(team.name))
//                .fetch();
//        //When
//
//        //Then
//        for (Tuple re : res) {
//            System.out.println("re = " + re);
//        }
//    }
//
//    @PersistenceUnit
//    EntityManagerFactory emf;
//
////    @Test
////    public void fetch() throws Exception {
////        em.flush();
////        em.clear();
////        //Given
////        Member findMember = queryFactory
////                .selectFrom(member)
////                .join(member.team, team)
////                .where(member.username.eq("member1"))
////                .fetchOne();
////        //When
////        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
////        //Then
////        assertThat(loaded).as("페치조인 미적용").isFalse();
////    }
//
//    /***
//     * 나이가 가장많은 회원 조회
//     */
//    @Test
//    public void sub_query() throws Exception {
//
//        QMember ms = new QMember("ms");
//        //Given
//        List<Member> res = queryFactory
//                .selectFrom(member)
//                .where(member.age.goe(
//                        JPAExpressions
//                        .select(ms.age.avg())
//                        .from(ms))
//                ).fetch();
//        //When
//
//        //Then
//        assertThat(res).extracting("age").containsExactly(30,40);
//    }
//
//    @Test
//    public void selectSub() throws Exception {
//        QMember ms = new QMember("ms");
//        //Given
//        List<Tuple> res = queryFactory
//                .select(member.username,
//                        JPAExpressions
//                                .select(ms.age.avg())
//                                .from(ms))
//                .from(member)
//                .fetch();
//        //When
//
//        //Then
//    }
//
//    @Test
//    public void dto() throws Exception {
//
//        QMember ms = new QMember("ms");
//
//        //Given
//        List<UserDto> res = queryFactory
//                .select(Projections.fields(UserDto.class,
//                        member.username.as("name"),
//                        ExpressionUtils.as(JPAExpressions
//                                .select(ms.age.avg().intValue())
//                                .from(ms), "age")
//                ))
//                .from(member)
//                .fetch();
//        //When
//
//        //Then
//        for (UserDto re : res) {
//            System.out.println("re = " + re);
//        }
//    }

    @Test
    public void query_projection() throws Exception {

        //Given
        List<MemberDto> res = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();
        //When

        //Then
        for (MemberDto re : res) {
            System.out.println("re = " + re);
        }
    }

    @Test
    public void givenCond_whenSearch_thenGetResult() throws Exception {

        //Given
        String nameCond = "member1";
        Integer ageCond = null;
        //When
        List<Member> members = searchMember1(nameCond, ageCond);
        //Then
        assertThat(members.size()).isEqualTo(1);

    }

    private List<Member> searchMember1(String nameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();

        if (nameCond != null) {
            builder.and(member.username.eq(nameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void givenCond_whenSearch_thenGetResult2() throws Exception {

        //Given
        String nameCond = "member1";
        Integer ageCond = 10;
        //When
        List<Member> members = searchMember2(nameCond, ageCond);
        //Then
        assertThat(members.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String nameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(nameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String nameCond) {
        return nameCond == null ? null : member.username.eq(nameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond == null ? null : member.age.eq(ageCond);
    }

    @Test
    public void givenQuery_whenUpdate_thenUpdateAll() throws Exception {

        //Given
        long res = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();
        //When

        //Then

        assertThat(res).isEqualTo(2);

        Member findMember = em.find(Member.class, 3L);
        System.out.println("findMember = " + findMember.getUsername());

    }
}
