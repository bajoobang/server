package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.Order;
import bajoobang.bajoobang_spring.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // member_id와 request_id로 Order 엔티티를 찾는 메서드
    Order findByMemberIdAndRequestRequestId(Long memberId, Long requestId);

    Order findByRequest(Request request);
}
