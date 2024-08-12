package bajoobang.bajoobang_spring.repository;

import bajoobang.bajoobang_spring.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bajoobang.bajoobang_spring.domain.File;

import java.util.List;

@Repository
public interface BalpoomFileRepository extends JpaRepository<File, Long> {
    List<File> findByRequest(Request request);
}
