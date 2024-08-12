package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bajoobang.bajoobang_spring.domain.PlusRequest;

import java.util.List;

@Repository
public interface PlusRequestRepository extends JpaRepository<PlusRequest, Long> {

    PlusRequest save(PlusRequest plusRequest);

    List<PlusRequest> findByRequest(Request request);
}

