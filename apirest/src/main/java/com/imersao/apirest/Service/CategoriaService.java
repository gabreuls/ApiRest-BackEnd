package com.imersao.apirest.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.imersao.apirest.Domain.Categoria.Categoria;
import com.imersao.apirest.Domain.Categoria.CategoriaRequestDTO;

@Service
public class CategoriaService {

    public ResponseEntity<?> createCategoria(CategoriaRequestDTO data) {
        if (data.titulo() == null || data.titulo().isEmpty()) {
            return ResponseEntity.status(400).body("O campo 'título' é obrigatório.");
        }
        if (data.cor() == null || data.cor().isEmpty()) {
            return ResponseEntity.status(400).body("O campo 'cor' é obrigatório.");
        }
        Categoria novaCat = new Categoria();
        novaCat.setTitulo(data.titulo());
        novaCat.setCor(data.cor());

        return ResponseEntity.status(201).body(novaCat);
    }
}
