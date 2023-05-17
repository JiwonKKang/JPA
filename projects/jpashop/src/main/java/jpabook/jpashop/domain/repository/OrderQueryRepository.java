package jpabook.jpashop.domain.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QOrder.*;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<Order> findOrders(OrderSearch cond) {

        return query.select(order)
                .from(order)
                .join(order.member, member)
                .where(memberNameLike(cond.getMemberName()), orderStatusEqual(cond.getOrderStatus()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression orderStatusEqual(OrderStatus orderStatus) {
        if (orderStatus != null) {
            return order.status.eq(orderStatus);
        }
        return null;
    }

    private BooleanExpression memberNameLike(String memberName) {
        if (StringUtils.hasText(memberName)) {
            return order.member.name.like(memberName);
        }
        return null;
    }
}
