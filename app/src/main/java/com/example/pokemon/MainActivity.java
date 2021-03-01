package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pokemon.Model.Pokemon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static TextView TV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TV = findViewById(R.id.textView);
        TV.setTextSize(30);
        ExampleThread thread = new ExampleThread();
        thread.start();

    }

    public class ExampleThread extends Thread{
        @Override
        public void run() {
            super.run();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokeApi service = retrofit.create(PokeApi.class);
            Call<PokemonRes> PRC = service.obtainPokemonData();

            PRC.enqueue(new Callback<PokemonRes>() {
                @Override
                public void onResponse(Call<PokemonRes> call, Response<PokemonRes> response) {

                    if (response.isSuccessful()) {
                        PokemonRes pokemonRes = response.body();
                        System.out.println("output"+pokemonRes.getResults());
                        ArrayList<Pokemon> list = pokemonRes.getResults();

                        for(Pokemon lists:list)
                        {
                            String content="";
                            content+= lists.getName()+"\n";
                            content+= lists.getUrl()+"\n\n";

                            TV.append(content);
                        }

//                        TV.setText(list.toString());

                        System.out.println(list.toString());
                    }
                    else
                        System.out.println("Response Failed");
                }

                @Override
                public void onFailure(Call<PokemonRes> call, Throwable t) {

                    System.out.println("API CAll failed");
                }
            });
        }
    }



}