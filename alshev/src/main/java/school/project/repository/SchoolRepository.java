package school.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.project.entity.SchoolEntity;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
}
