package montacer.elfazazi.ejerc3pmdmtema2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.j256.ormlite.dao.Dao;

import java.util.List;

import montacer.elfazazi.ejerc3pmdmtema2.helpers.AlumnosHelper;
import montacer.elfazazi.ejerc3pmdmtema2.modelos.Alumno;

public class AlumnoAdapter  extends RecyclerView.Adapter<AlumnoAdapter.AlumnoVH>{
    private Context context;
    private List<Alumno> objects;
    private int resource;
    private AlumnosHelper helper;
    private Dao<Alumno, Integer> daoProductos;


    @NonNull
    @Override
    public AlumnoAdapter.AlumnoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoAdapter.AlumnoVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class AlumnoVH extends RecyclerView.ViewHolder {
        public AlumnoVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
