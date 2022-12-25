package io.kadev.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.kadev.models.Produit;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Long>{
}
