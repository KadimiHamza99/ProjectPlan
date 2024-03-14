package io.kadev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kadev.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
