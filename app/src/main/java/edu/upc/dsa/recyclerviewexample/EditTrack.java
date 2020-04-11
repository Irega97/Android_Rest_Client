package edu.upc.dsa.recyclerviewexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

        //Definimos botones y editores de texto
        final Button update = findViewById(R.id.update_btn);
        final EditText id = findViewById(R.id.track_id);
        final EditText title = findViewById(R.id.track_title);
        final EditText singer = findViewById(R.id.track_singer);

        id.setText(i.getStringExtra("id"));
        title.setText(i.getStringExtra("title"));
        singer.setText(i.getStringExtra("singer"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_text = id.getText().toString();
                final String title_text = title.getText().toString();
                final String singer_text = singer.getText().toString();

                if(id_text.equals("")||title_text.equals("")||singer_text.equals("")){
                    Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Pop-up de confirmación
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(EditTrack.this)
                            //Titulo, mensaje y botones del pop-up
                            .setTitle("Actualizar")
                            .setMessage("Seguro que quieres actualizar el track?")
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Track t = new Track(id_text,title_text,singer_text);
                                    updateTrack(t);
                                    Intent intent_main = new Intent (EditTrack.this, MainActivity.class);
                                    startActivity(intent_main);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG);
                                    toast.show();
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                    myQuittingDialogBox.create();
                    myQuittingDialogBox.show();
                }

            }
        });
    }

    public void updateTrack(Track t){
        Call<Void> call = api.updateTrack(t);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Track editado", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error al acceder a la API", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
