package com.example.offlinemovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.offlinemovies.data.MovieRepository;
import com.example.offlinemovies.data.local.entity.MovieEntity;
import com.example.offlinemovies.data.network.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {

    //objeto para almacenar las movies que obtenga del repository
    private final LiveData<Resource<List<MovieEntity>>> popularMovies;//Resource: ya que puede ser de la db local o de la api la data obtenida

    //instancia del repository
    private MovieRepository movieRepository;

    public MovieViewModel(){
        movieRepository = new MovieRepository(); //inicializo el repository
        popularMovies = movieRepository.getPopuparMovies();//cargo la lista de peliculas
    }

    //devolver las peliculas
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        return popularMovies;
    }
}
