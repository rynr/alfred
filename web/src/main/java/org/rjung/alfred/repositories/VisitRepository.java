package org.rjung.alfred.repositories;

import org.rjung.alfred.objects.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitRepository extends JpaRepository<Visit, Long> {

	@Query(value = "SELECT DISTINCT v.email, v.name, v.company FROM Visit v WHERE v.email LIKE ?1%")
	Page<Visit> findUsersByEmail(String email, Pageable page);
}
