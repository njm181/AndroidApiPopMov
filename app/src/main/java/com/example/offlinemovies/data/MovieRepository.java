package com.example.offlinemovies.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.offlinemovies.app.MyApp;
import com.example.offlinemovies.data.local.MovieRoomDatabase;
import com.example.offlinemovies.data.local.dao.MovieDAO;
import com.example.offlinemovies.data.local.entity.MovieEntity;
import com.example.offlinemovies.data.network.NetworkBoundResource;
import com.example.offlinemovies.data.network.Resource;
import com.example.offlinemovies.data.remote.ApiConstants;
import com.example.offlinemovies.data.remote.MovieApiService;
import com.example.offlinemovies.data.remote.RequestInterceptor;
import com.example.offlinemovies.data.remote.model.MoviesResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//para compartir con el web service y la bd local
public class MovieRepository {

    private final MovieApiService movieApiService; //connect to api

    private final MovieDAO movieDAO; // connect to database

    public MovieRepository() {
        //DB
        //datos locales a traves de Room
        MovieRoomDatabase movieRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(), //ccontexto
                MovieRoomDatabase.class, //la clase que genera la base de datos
                "db_movie" //nombre de la base de datos
        ).build();

        movieDAO = movieRoomDatabase.getMovieDao(); //acceso a las consultas de la entidad MoviesEntity de la base de datos local

        //API
        //RequestInterceptor: incluir en la url de la peticion el token/api key que autoriza
        //al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();//cliente al que nos vmaos a conectar
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());//agrego mi interceptor
        OkHttpClient cliente = okHttpClientBuilder.build();//client http creado para vincular a las peticiones y agregar el intercptor

        //Remote > retrofit connection
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //initialize the service
        movieApiService = retrofit.create(MovieApiService.class);
    }

    public LiveData<Resource<List<MovieEntity>>> getPopuparMovies(){
        //NetworkBoundResource: Se encarga de verificar si hay o no conexion a internet para saber que fuente de datos usar para mostrar
        //el primer tipo que indico es el tipo que devuelve Room(db local) y el segundo el que devuelve la api con retrofit
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(){

            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                //guarda en la base de datos local la list ade peliculas que traigo en el primer llamado a la api
                movieDAO.saveMovies(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                //devuelve los datos que dispongamos en la base de datos local Room, si no hay conexion a internet se usa esto
                System.out.println("ESTAS EN LA DATA DE LA BASE DE DATOS");
                return movieDAO.loadMovies();
            }

            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                //si hay conexion y acceso a internet, realiza la llamada a la api y obtengo los datos de la api remota
                System.out.println("ESTAS EN LA DATA DE LA API");
                return movieApiService.loadPopularMovies();
            }
        }.getAsLiveData();
    }

}
