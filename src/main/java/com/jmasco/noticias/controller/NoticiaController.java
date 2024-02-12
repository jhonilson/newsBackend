package com.jmasco.noticias.controller;

import com.jmasco.noticias.dto.NewsResponse;
import com.jmasco.noticias.dto.Noticia;
import com.jmasco.noticias.service.impl.NoticiaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NoticiaController {

    private static  final String URL = "https://api.spaceflightnewsapi.net/v4/articles";
    @Autowired
    NoticiaServiceImpl noticiaService;

    @GetMapping("/noticias")
    public NewsResponse getNoticias(@RequestParam(name = "limit", defaultValue = "10") int limit,
                                    @RequestParam(name = "ordering", defaultValue = "published_at") String ordering,
                                    @RequestParam(name = "offset", defaultValue = "1") int offset) {

        String url = URL + "?limit=" + limit + "&offset=" + offset + "&ordering=" + ordering;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NewsResponse> responseEntity = restTemplate.getForEntity(url, NewsResponse.class);
        return responseEntity.getBody();
    }

    @GetMapping("/noticias/byTitle")
    public NewsResponse getNoticiaByTitle(@RequestParam(name = "limit", defaultValue = "10") int limit,
                                    @RequestParam(name = "ordering", defaultValue = "published_at") String ordering,
                                    @RequestParam(name = "offset", defaultValue = "1") int offset,
                                    @RequestParam String search){

        String url = URL + "?limit=" + limit + "&offset=" + offset + "&ordering=" + ordering + "&search=" + search;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NewsResponse> responseEntity = restTemplate.getForEntity(url, NewsResponse.class);
        return responseEntity.getBody();
    }

    @GetMapping("/noticias/{id}")
    public Noticia getNoticiaById(@PathVariable int id){
        // URL del servicio
        String url = "https://api.spaceflightnewsapi.net/v3/articles/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Noticia> responseEntity = restTemplate.getForEntity(url, Noticia.class);
        return responseEntity.getBody();
    }


    @GetMapping("/favoritos")
    public List<Noticia> getFavoritos(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size,
                                     @RequestParam(name = "sort", defaultValue = "agregadoAt") String sort){

        return noticiaService.getFavoritos(page, size, sort);
    }

    @GetMapping("/favoritos/{id}")
    public Noticia getFavoritoById(@PathVariable int id){
        return noticiaService.getFavoritoById(id);
    }

    @GetMapping("/favoritos/title/{title}")
    public Noticia getFavoritoByTitle(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                      @RequestParam(name = "sort", defaultValue = "agregadoAt") String sort,
                                      @PathVariable String title){
        return noticiaService.getFavoritoByTitle(page, size, sort, title);
    }

    @PostMapping("/favoritos")
    public ResponseEntity<Noticia> createNoticia(@RequestBody Noticia noticia){
        return new ResponseEntity<>(noticiaService.createFavorito(noticia), HttpStatus.CREATED);
    }

    @DeleteMapping("/favoritos/{id}")
    public ResponseEntity<Void> deleteNoticia(@PathVariable int id){
        noticiaService.deleteFavorito(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
