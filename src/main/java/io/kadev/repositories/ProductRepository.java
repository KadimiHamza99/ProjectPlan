package io.kadev.repositories;


import io.kadev.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query("SELECT t FROM Product t WHERE t.project.id = ?1 AND t.id = ?2")
    Optional<Product> findByIdAndProjectId(Long project, Long product);
}
