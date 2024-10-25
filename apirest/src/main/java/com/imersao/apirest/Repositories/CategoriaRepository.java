package com.imersao.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imersao.apirest.Domain.Categoria.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
