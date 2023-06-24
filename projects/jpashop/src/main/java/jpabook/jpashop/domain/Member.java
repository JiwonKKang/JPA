package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @CollectionTable(name = "favorite_food",
            joinColumns = @JoinColumn(name = "member_id"))
    @ElementCollection
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)//읽기 전용, 연관관계의 주인은 order 테이블의 member 필드.
    private List<Order> orders = new ArrayList<>();

}
