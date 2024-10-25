package com.imersao.apirest.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imersao.apirest.Domain.Categoria.Categoria;
import com.imersao.apirest.Domain.Categoria.CategoriaRequestDTO;
import com.imersao.apirest.Domain.Video.Video;
import com.imersao.apirest.Repositories.CategoriaRepository;
//import com.imersao.apirest.Repositories.VideoRepository;
import com.imersao.apirest.Service.CategoriaService;
import com.imersao.apirest.Service.VideoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired 
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<?> createCategoria(@RequestBody CategoriaRequestDTO body) {
        ResponseEntity<?> response = this.categoriaService.createCategoria(body);
        if(response.getStatusCode().is4xxClientError()) {
            return response;
        }
        Categoria novaCat = (Categoria) response.getBody();
        this.categoriaRepository.save(novaCat);
        return ResponseEntity.status(201).body(novaCat);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<String> showCategoriaForId(@PathVariable Long id) {
        Optional<Categoria> catOpt = categoriaRepository.findById(id);
        if(catOpt.isPresent()) {
            Categoria categoria = catOpt.get();
            return ResponseEntity.ok(String.format("Categoria{id=%d, titulo='%s', cor='%s'}", 
            categoria.getId(), categoria.getTitulo(), categoria.getCor()));
        } else {
            return ResponseEntity.status(404).body("Categoria não encontrada!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> showAllCategoria() {
        var listaCategorias = categoriaRepository.findAll();
        return ResponseEntity.ok(listaCategorias);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoriaRequestDTO> updateCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dados) {
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada!"));
        categoria.updateCategoria(dados);
        categoriaRepository.save(categoria);

        return ResponseEntity.ok(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deteleCategoria(@PathVariable Long id) {
        var categoria = categoriaRepository.findById(id);
        if(categoria.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok("Categoria deletada!");
        } else {
            return ResponseEntity.status(404).body("Categoria não encontrada!");
        }
    }

    @GetMapping("/{categoriaId}/videos")
    @Transactional
    public ResponseEntity<List<Video>> showVideoByCategoria(@PathVariable Long categoriaId) {
        List<Video> videos = videoService.showVideoByCategoria(categoriaId);
        if(videos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(videos);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Categoria>> showCategoriasPage(@RequestParam int page) {
        int pageSize = 5;
        PageRequest pageble = PageRequest.of(page, pageSize);
        Page<Categoria> listaCatgorias = categoriaRepository.findAll(pageble);
        return ResponseEntity.ok(listaCatgorias.getContent());
    }

}