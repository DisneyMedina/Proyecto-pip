package com.cibertec.pe.Grupo07.repository;

import com.cibertec.pe.Grupo07.model.TipoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoPrestamoRepository extends JpaRepository<TipoPrestamo, Integer> {

   public abstract   List<TipoPrestamo> findByOrderByDescripcionAsc();


}
