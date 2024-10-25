package com.imersao.apirest.Domain.Video;

import com.imersao.apirest.Domain.Categoria.Categoria;

public record VideoRequestDTO(String titulo, String descricao, String url, Categoria categoriaId) {

}
