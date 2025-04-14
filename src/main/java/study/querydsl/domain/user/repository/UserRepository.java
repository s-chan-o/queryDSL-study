package study.querydsl.domain.user.repository;

import org.springframework.data.repository.CrudRepository;
import study.querydsl.domain.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
