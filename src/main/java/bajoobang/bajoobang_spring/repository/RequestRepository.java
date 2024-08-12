package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.Member;
import bajoobang.bajoobang_spring.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import bajoobang.bajoobang_spring.domain.House;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByMember(Member member);

    @Query("select r.house from Request r where r.requestId = :requestId")
    House findHouseByRequestId(Long requestId);

    List<Request> findByStatus(String status);
}
