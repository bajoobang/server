package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bajoobang.bajoobang_spring.domain.Likey;

import java.util.List;

@Repository
public interface LikeyRepository extends JpaRepository<Likey, Long> {

    List<Likey> findByMember(Member member);
}
