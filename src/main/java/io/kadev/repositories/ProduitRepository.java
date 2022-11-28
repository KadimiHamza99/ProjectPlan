package io.kadev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kadev.models.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>{

}
