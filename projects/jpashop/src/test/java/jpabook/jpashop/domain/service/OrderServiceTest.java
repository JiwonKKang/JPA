package jpabook.jpashop.domain.service;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void orderItemTest() throws Exception {

        //given
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("청주", "봉명동", "2300-1"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book); // book의 id값을 생성 -> 밑에서 파라미터값으로 넣어줘야 하기때문에
        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(getOrder.getMember(), member);
        assertEquals(getOrder.getOrderItems().stream().map(OrderItem::getItem).findFirst().get(), book);
        assertEquals(8, book.getStockQuantity());

    }

    @Test
    public void orderCancelTest() throws Exception {

        //given
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("청주", "봉명동", "2300-1"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book); // book의 id값을 생성 -> 밑에서 파라미터값으로 넣어줘야 하기때문에
        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);
        //then
        assertThat(orderRepository.findOne(orderId).getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);



    }

    @Test
    public void stockQuantityOverTest() throws Exception {

        //given
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("청주", "봉명동", "2300-1"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book); // book의 id값을 생성 -> 밑에서 파라미터값으로 넣어줘야 하기때문에

        //when
        int orderCount = 11;

        //then
        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class);

    }
}