package edu.upc.dsa.recyclerviewexample;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //Aqui hay que declarar las cosas que queremos que se muestren en pantalla
    //Añadimos la actividad porque cambiaremos a otra
    //Los inicializamos en el constructor
    private List<String> values;
    Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        //Constructor ViewHolder
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    //Funciones para modificar lista valores
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Constructor del adaptador, le pasamos lista tracks y actividad
    public MyAdapter(List<String> myDataset, Activity activity) {
        values = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view (RECETA PARA CARGAR LAYOUT)
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        // Creamos y retornamos el ViewHolder (RECETA)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    //Para que funcione el scroll correctamente
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        //Creamos holder para que las posiciones sean dinamicas
        final ViewHolder vh = holder;
        //Asignamos nombre
        holder.txtHeader.setText(name);
        //Asignamos evento
        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Usamos posicion holder para que se vayan actualizando a medida que borras posiciones
                //remove(vh.getAdapterPosition());

                //Abrimos actividad info track al pinchar en un track
                Intent i = new Intent(activity.getApplication(), TrackActivity.class);
                activity.startActivity(i);
            }
        });

        holder.txtFooter.setText("Footer: " + name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}