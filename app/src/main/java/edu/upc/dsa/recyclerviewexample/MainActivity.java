package edu.upc.dsa.recyclerviewexample;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtenemos recycler view
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        //Para optimizar y que vaya mas fluido
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //String que se visualizan. Aqui es donde implementariamos lista Retrofit
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }

        // define an adapter
        mAdapter = new MyAdapter(input, MainActivity.this);
        recyclerView.setAdapter(mAdapter);
    }
}