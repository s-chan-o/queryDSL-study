package study.querydsl.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import study.querydsl.domain.menu.entity.Menu;
import study.querydsl.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();
}
