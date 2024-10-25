package com.imersao.apirest.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imersao.apirest.Domain.Video.Video;
import com.imersao.apirest.Domain.Video.VideoRequestDTO;
import com.imersao.apirest.Repositories.VideoRepository;
import com.imersao.apirest.Service.VideoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @PostMapping
    public ResponseEntity<?> createVideo(@RequestBody VideoRequestDTO body) {
        ResponseEntity<?> response = this.videoService.createVideo(body);
        if(response.getStatusCode().is4xxClientError()){
            return response;
        }
        Video novoVideo = (Video) response.getBody();
        this.videoRepository.save(novoVideo);
        return ResponseEntity.status(201).body(novoVideo);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<String> showVideoForId(@PathVariable Long id) {
        Optional<Video> videoOpt = videoRepository.findById(id);
        if(videoOpt.isPresent()) {
            Video video = videoOpt.get();
            return ResponseEntity.ok(String.format("Video{id=%d, titulo='%s', descricao='%s', url='%s'}", 
            video.getId(), video.getTitulo(), video.getDescricao(), video.getUrl()));
        } else {
            return ResponseEntity.status(404).body("Vídeo não encontrado!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Video>> showAllVideos() {
        var listaVideos = videoRepository.findAll();
        return ResponseEntity.ok(listaVideos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VideoRequestDTO> updateVideo(@PathVariable Long id, @RequestBody @Valid VideoRequestDTO dados) {
        Video video = videoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Vídeo não encontrado!"));
        video.updateVideo(dados);
        videoRepository.save(video);

        return ResponseEntity.ok(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        var video = videoRepository.findById(id);

        if(video.isPresent()) {
            videoRepository.deleteById(id);
            return ResponseEntity.ok("Vídeo deletado!");
        } else {
            return ResponseEntity.status(404).body("Vídeo não encontrado!");
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Video>> findVideoByTitulo(@RequestParam String titulo) {
        List<Video> videos = videoService.findVideoByTitulo(titulo);
        if(videos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(videos);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Video>> showVideosPage(@RequestParam int page) {
        int pageSize = 5;
        PageRequest pageble = PageRequest.of(page, pageSize);
        Page<Video> listaVideos = videoRepository.findAll(pageble);
        return ResponseEntity.ok(listaVideos.getContent());
    }

    @GetMapping("/free")
    public ResponseEntity<List<Video>> showVideosFree() {
        int videosFree = 10;
        PageRequest listaFree = PageRequest.of(0, videosFree);
        Page<Video> listaVideosFree = videoRepository.findAll(listaFree);
        return ResponseEntity.ok(listaVideosFree.getContent());
    }
}
