package com.imersao.apirest.Domain.Categoria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.imersao.apirest.Domain.Video.Video;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "categoria")
@Entity(name = "Categoria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String cor;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "categoria")
    private List<Video> videos;

    public void updateCategoria(CategoriaRequestDTO newDados) {
        if(newDados.titulo() != null) {
            this.titulo = newDados.titulo();
        }

        if(newDados.cor() != null) {
            this.cor = newDados.cor();
        }
    }
}
