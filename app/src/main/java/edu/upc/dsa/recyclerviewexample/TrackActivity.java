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

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_track);

        Intent i = getIntent();
        TextView idTV = findViewById(R.id.track_id);
        TextView titleTV = findViewById(R.id.track_title);
        TextView singerTV = findViewById(R.id.track_singer);

        idTV.setText(i.getStringExtra("id"));
        titleTV.setText(i.getStringExtra("title"));
        singerTV.setText(i.getStringExtra("singer"));

        Button edit_btn = findViewById(R.id.update_btn);
        Button del_btn = findViewById(R.id.delete_btn);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrackActivity.this, EditTrack.class);
                startActivity(i);
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(TrackActivity.this)
                        .setTitle("Borrar")
                        .setMessage("Seguro que quieres borrar el track?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
}
