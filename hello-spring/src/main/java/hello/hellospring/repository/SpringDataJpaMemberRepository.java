package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // findBy는 예약어 같은 놈
    // And Or 등도 있음
    @Override
    Optional<Member> findByName(String name);
}
