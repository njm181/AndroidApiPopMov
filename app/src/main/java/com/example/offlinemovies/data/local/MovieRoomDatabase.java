package com.example.offlinemovies.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.offlinemovies.data.local.dao.MovieDAO;
import com.example.offlinemovies.data.local.entity.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public  abstract class MovieRoomDatabase extends RoomDatabase {

    //declarar objeto DAO

    public abstract MovieDAO getMovieDao();


}
