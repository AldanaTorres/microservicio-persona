package com.jhipster.persona.repository;

import com.jhipster.persona.domain.Libro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Libro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(value = "select distinct libro from Libro libro left join fetch libro.autores",
        countQuery = "select count(distinct libro) from Libro libro")
    Page<Libro> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct libro from Libro libro left join fetch libro.autores")
    List<Libro> findAllWithEagerRelationships();

    @Query("select libro from Libro libro left join fetch libro.autores where libro.id =:id")
    Optional<Libro> findOneWithEagerRelationships(@Param("id") Long id);
}
