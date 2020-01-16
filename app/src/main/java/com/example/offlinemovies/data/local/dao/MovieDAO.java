package com.example.offlinemovies.data.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.offlinemovies.data.local.entity.MovieEntity;

import java.util.List;
//consultas para la db
@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movies")
    LiveData<List<MovieEntity>> loadMovies();

    //guardar las peliculas que vienen de la api a la db para cuando no tenga conexion pueda verlas
    //onConflict = OnConflictStrategy.REPLACE: si quiero guardar una Movie que ya esta guardada, se reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieEntity> movieEntityList);
}


