package com.example.offlinemovies.data.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        //tomamos la request antes de enviarla y le concanteno mas info, en este caso para concatenar la api key a la consulta

        Request originalRequest = chain.request();//capturo la peticion que se queria enviar al servidor

        HttpUrl originalHttpUrl = originalRequest.url(); // capturo la url a donde iba a enviar la peticion

        HttpUrl newUrl = originalHttpUrl.newBuilder() //tomamos la url y construyo una nueva
            .addQueryParameter("api_key", ApiConstants.API_KEY) //addQueryParameter: parametros que viajan por la url
                //api_key: name parameter, value: API_KEY
                .build();

        //montar el request para invocar esta nueva url
        Request requestFinal = originalRequest.newBuilder() //new request a partir del original
                .url(newUrl)
                .build();

        return chain.proceed(requestFinal);//chain: cadena interceptada, se le indica que proceda la peticion con la requestFinal

    }


}
