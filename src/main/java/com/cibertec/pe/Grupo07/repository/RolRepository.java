package com.cibertec.pe.Grupo07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}