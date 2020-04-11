package edu.upc.dsa.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditTrack extends AppCompatActivity {
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_track);

        //Crear retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Llamamos a servicios que hemos definido en la API
        api = retrofit.create(API.class);

        //Declaramos intent para recuperar parametros y volver al Main
        final Intent i = getIntent();
        final Intent main_intent = new Intent(EditTrack.this, MainActivity.class);

        //Definimos botones y editores de texto
        Button update = (Button) findViewById(R.id.update_btn);
        final EditText id_text = findViewById(R.id.track_id);
        final EditText title_text = findViewById(R.id.track_title);
        final EditText singer_text = findViewById(R.id.track_singer);

        id_text.setText(i.getStringExtra("id"));
        title_text.setText(i.getStringExtra("title"));
        singer_text.setText(i.getStringExtra("singer"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newID = id_text.getText().toString();
                String newTitle = title_text.getText().toString();
                String newSinger = singer_text.getText().toString();

                Track updatedTrack = new Track (newID,newTitle,newSinger);
                updateTrack(updatedTrack);
            }
        });

    }

    public void updateTrack(Track t){
        Call<Void> call = api.updateTrack(t);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
