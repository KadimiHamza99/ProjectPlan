package io.kadev.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.kadev.models.Produit;

import java.util.Optional;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Long>{
    @Query("SELECT t FROM Produit t WHERE t.project.id = ?1 AND t.id = ?2")
    Optional<Produit> findByIdAndProjectId(Long project, Long product);
}
