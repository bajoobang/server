package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.BaDream;
import bajoobang.bajoobang_spring.domain.Member;
import bajoobang.bajoobang_spring.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaDreamRepository extends JpaRepository<BaDream, Long> {
    List<BaDream> findByRequestIn(List<Request> requests);
    List<BaDream> findByMember(Member member);
}
