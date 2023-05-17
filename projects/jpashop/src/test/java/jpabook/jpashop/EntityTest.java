package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Slf4j
public class EntityTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void member() throws Exception {

        //given
        Member member = new Member();
        Order order = new Order();
        Order order1 = new Order();

        //when
        order.setMember(member);
        order1.setMember(member);

        //then
        em.persist(member);

        Assertions.assertTrue(em.find(Member.class, member.getId()).getOrders().contains(order));
        Assertions.assertEquals(em.find(Order.class, order.getId()).getMember().getId(), member.getId());

    }
}
