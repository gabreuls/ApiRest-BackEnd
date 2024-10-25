package com.imersao.apirest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.imersao.apirest.Domain.Categoria.Categoria;
import com.imersao.apirest.Domain.Video.Video;
import com.imersao.apirest.Domain.Video.VideoRequestDTO;
import com.imersao.apirest.Repositories.CategoriaRepository;
import com.imersao.apirest.Repositories.VideoRepository;

@Service
public class VideoService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    VideoRepository videoRepository;

    private static final Long ID_CATEGORIA_PADRAO = 1L;

    public ResponseEntity<?> createVideo(VideoRequestDTO data) {
        if (data.titulo() == null || data.titulo().isEmpty()) {
            return ResponseEntity.status(400).body("O campo 'título' é obrigatório.");
        }
        if (data.descricao() == null || data.descricao().isEmpty()) {
            return ResponseEntity.status(400).body("O campo 'descrição' é obrigatório.");
        }
        if (data.url() == null || data.url().isEmpty()) {
            return ResponseEntity.status(400).body("O campo 'url' é obrigatório.");
        }

        Video novoVideo = new Video();
        novoVideo.setTitulo(data.titulo());
        novoVideo.setDescricao(data.descricao());
        novoVideo.setUrl(data.url());

        if(novoVideo.getCategoria() == null) {
            Categoria catPadrao = categoriaRepository.findById(ID_CATEGORIA_PADRAO)
            .orElseThrow(() -> new RuntimeException("Categoria Padrão não encontrada!"));
            novoVideo.setCategoria(catPadrao);
        }

        return ResponseEntity.status(201).body(novoVideo);
    }

    public List<Video> showVideoByCategoria(Long categoriaId) {
        return videoRepository.findByCategoriaId(categoriaId);
    }

    public List<Video> findVideoByTitulo(String titulo) {
        return videoRepository.findByTituloContains(titulo);
    }
}
