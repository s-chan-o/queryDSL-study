package study.querydsl.domain.menu.entity;

import jakarta.persistence.*;
import lombok.*;
import study.querydsl.domain.store.entity.Store;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
}
