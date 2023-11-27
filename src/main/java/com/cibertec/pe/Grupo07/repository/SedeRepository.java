package com.cibertec.pe.Grupo07.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Sede;
@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer>{
	public abstract List<Sede> findByOrderByNombreAsc();
}
