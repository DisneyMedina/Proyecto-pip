package com.cibertec.pe.Grupo07.repository;

import com.cibertec.pe.Grupo07.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BancoRepository extends JpaRepository<Banco, Long> {

    @Query("SELECT b FROM Banco b WHERE b.estado = 1")
    public abstract List<Banco> listarBanco();
}
