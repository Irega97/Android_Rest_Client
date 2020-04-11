package edu.upc.dsa.recyclerviewexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackActivity extends AppCompatActivity {
private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_track);

        //Crear retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Llamamos a servicios que hemos definido en la API
        api = retrofit.create(API.class);

        //Recuperamos datos que nos pasa el intent al pulsar el track (en MyAdapter)
        //para que nos muestre los detalles del track
        final Intent i = getIntent();
        final Intent edit_intent = new Intent(TrackActivity.this, EditTrack.class);

        //Inicializamos TextViews
        final TextView idTV = findViewById(R.id.track_id);
        final TextView titleTV = findViewById(R.id.track_title);
        final TextView singerTV = findViewById(R.id.track_singer);

        //Añadimos info que nos ha pasado a los textviews correspondientes
        idTV.setText(i.getStringExtra("id"));
        titleTV.setText(i.getStringExtra("title"));
        singerTV.setText(i.getStringExtra("singer"));

        //Definimos los botones
        Button edit_btn = findViewById(R.id.update_btn);
        Button del_btn = findViewById(R.id.delete_btn);

        //Boton editar
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pasamos info del track para poder editarla
                edit_intent.putExtra("id", idTV.getText().toString());
                edit_intent.putExtra("title", titleTV.getText().toString());
                edit_intent.putExtra("singer", singerTV.getText().toString());
                startActivity(edit_intent);
            }
        });

        //Boton borrar
        //Aparece cuadro de dialogo para confirmar la accion de borrar
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pop-up de confirmación
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(TrackActivity.this)
                        //Titulo, mensaje y botones del pop-up
                        .setTitle("Borrar")
                        .setMessage("Seguro que quieres borrar el track?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTrack(idTV.getText().toString());
                                Intent intent_main = new Intent (TrackActivity.this, MainActivity.class);
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
        });
    }

    public void deleteTrack(String id){
        Call<Void> call = api.deleteTrack(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Track borrado", Toast.LENGTH_SHORT).show();
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
