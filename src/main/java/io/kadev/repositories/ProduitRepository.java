package io.kadev.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kadev.models.Produit;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Long>{
}
