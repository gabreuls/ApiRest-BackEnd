package com.imersao.apirest.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imersao.apirest.Domain.Video.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{
    List<Video> findByCategoriaId(Long categoriaId);
    List<Video> findByTituloContains(String titulo);
}
