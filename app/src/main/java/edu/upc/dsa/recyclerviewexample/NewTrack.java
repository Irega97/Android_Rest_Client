package edu.upc.dsa.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewTrack extends AppCompatActivity {
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_track);

        //Inicializamos elementos layout
        final EditText id = findViewById(R.id.track_id);
        final EditText title = findViewById(R.id.track_title);
        final EditText singer = findViewById(R.id.track_singer);
        Button add_btn = findViewById(R.id.add_button);

        //Crear retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos texto
                String id_text = id.getText().toString();
                String title_text = title.getText().toString();
                String singer_text = singer.getText().toString();

                //Si estan vacios, salta Toast
                if(id_text.equals("") || title_text.equals("") || singer_text.equals("")){
                    Toast.makeText(NewTrack.this, "Campos vacíos", Toast.LENGTH_SHORT).show();
                }
                //Si estan llenos, añade track y vuelve a MainActivity
                else{
                    Track track = new Track(id_text,title_text,singer_text);
                    addTrack(track);
                    Intent i = new Intent(NewTrack.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    //FUNCION AÑADIR TRACK
    //Llama a la funcion addTrack de la api, encola la peticion y segun el codigo de respuesta muestra un Toast
    public void addTrack(Track t){
        Call<Track> trackCall = api.addTrack(t);

        trackCall.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Track añadido", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error al acceder a la API", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
