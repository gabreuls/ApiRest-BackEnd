package com.imersao.apirest.Domain.Video;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.imersao.apirest.Domain.Categoria.Categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "video")
@Entity(name = "Video")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    @NotNull
    private String url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

    public void updateVideo(VideoRequestDTO newData) {
        if(newData.titulo() != null) {
            this.titulo = newData.titulo();
        }

        if(newData.descricao() != null) {
            this.descricao = newData.descricao();
        }

        if(newData.url() != null) {
            this.url = newData.url();
        }
    }
}
