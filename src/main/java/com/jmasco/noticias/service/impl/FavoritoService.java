package com.jmasco.noticias.service.impl;

import com.jmasco.noticias.dto.Noticia;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoritoService {

    public List<Noticia> getFavoritos(int page, int size, String sort);

    public Noticia getFavoritoById(int id);

    public Noticia createFavorito(Noticia noticia);

    public void deleteFavorito(int id);

    public Noticia getFavoritoByTitle(int page, int size, String sort, String title);
}
