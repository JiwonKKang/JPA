package jpabook.jpashop.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> OrderV2() {
        List<OrderDto> dtos = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
        return new Result<>(dtos.size(), dtos);
    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        private OrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemDto> orderItems) {
            this.orderId = orderId;
            this.name = name;
            this.orderDate = orderDate;
            this.orderStatus = orderStatus;
            this.address = address;
            this.orderItems = orderItems;
        }

        public static OrderDto from(Order order) {
            return new OrderDto(
                    order.getId(),
                    order.getMember().getName(),
                    order.getOrderDate(),
                    order.getStatus(),
                    order.getDelivery().getAddress(),
                    order.getOrderItems().stream()
                            .map(OrderItemDto::from)
                            .collect(Collectors.toList())
            );
        }
    }

    @Data
    static class OrderItemDto {
        private Long id;
        private Long itemId;
        private Long orderId;
        private int orderPrice;
        private int count;

        public OrderItemDto(Long id, Long orderId, Long itemId, int orderPrice, int count) {
            this.id = id;
            this.itemId = itemId;
            this.orderId = orderId;
            this.orderPrice = orderPrice;
            this.count = count;
        }

        public static OrderItemDto from(OrderItem orderItem) {
            return new OrderItemDto(
                    orderItem.getId(),
                    orderItem.getItem().getId(),
                    orderItem.getOrder().getId(),
                    orderItem.getOrderPrice(),
                    orderItem.getCount()
            );
        }
    }

    @Data
    static class ItemDto {

    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

}
