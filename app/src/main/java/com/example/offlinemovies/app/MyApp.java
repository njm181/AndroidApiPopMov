package com.example.offlinemovies.app;

import android.app.Application;
import android.content.Context;

//Definir el contexto a nivel de la aplicacion, para cuando no este en un Activity y no pueda definir
//el context, entonces utilizo esta Class para obtener referencia al context de la app
public class MyApp extends Application {
    private static MyApp instance; //objeto vinculado al Manifest, para que cuando inicie la app se instancie este objeto

    public static MyApp getInstance(){
        return instance;
    }

    //Context: referencia al espacio de memoria donde estoy trabajando, en donde estan las variables/objetos que estoy trabajando
    public static Context getContext(){
        return instance;
    }

    //inicializar instance
    @Override
    public void onCreate() {
        instance = this;//MyApp Class
        super.onCreate();
    }
}
