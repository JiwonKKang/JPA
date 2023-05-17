package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    // 연관관계 메서드는 양방향 연관관계일 때 사용한다.
    // 연관관계 메서드는 핵심적인 비즈니스 로직을 구현할 때 사용한다.
    // 연관관계 메서드는 엔티티를 변경할 때 연관된 필드를 같이 변경해주는 편리함을 제공한다.

    public void setMember(Member member) { // 주문한 회원 정보를 설정한다.
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) { // 주문 상품을 추가한다.
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) { // 배송 정보를 설정한다.
        this.delivery = delivery;
        delivery.setOrder(this);
    }



}