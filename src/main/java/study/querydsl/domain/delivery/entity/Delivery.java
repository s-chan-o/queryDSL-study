package study.querydsl.domain.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import study.querydsl.domain.order.entity.Order;
import study.querydsl.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private User rider;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime assignedAt;
    private LocalDateTime completedAt;

    public enum Status {
        READY, PICKED_UP, DELIVERED
    }
}
