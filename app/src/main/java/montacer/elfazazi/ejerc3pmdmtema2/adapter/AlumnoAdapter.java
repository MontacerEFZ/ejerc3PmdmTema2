package montacer.elfazazi.ejerc3pmdmtema2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import montacer.elfazazi.ejerc3pmdmtema2.R;
import montacer.elfazazi.ejerc3pmdmtema2.configuracion.Configuracion;
import montacer.elfazazi.ejerc3pmdmtema2.helpers.AlumnosHelper;
import montacer.elfazazi.ejerc3pmdmtema2.modelos.Alumno;

public class AlumnoAdapter  extends RecyclerView.Adapter<AlumnoAdapter.AlumnoVH>{
    private Context context;
    private List<Alumno> objects;
    private int resource;
    private AlumnosHelper helper;
    private Dao<Alumno, Integer> daoAlumnos;

    public AlumnoAdapter(Context context, List<Alumno> objects, int resource) {
        this.context = context;
        this.objects = objects;
        this.resource = resource;

        helper = new AlumnosHelper(context, Configuracion.BD_NAME, null, Configuracion.BD_VERSION);
        if (helper != null){
            try {
                daoAlumnos = helper.getDaoAlumnos();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @NonNull
    @Override
    public AlumnoAdapter.AlumnoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View alumnoView = LayoutInflater.from(context).inflate(resource, null);

        alumnoView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        return new AlumnoVH(alumnoView);    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoAdapter.AlumnoVH holder, int position) {
        Alumno alumno = objects.get(position);

        holder.lbNombre.setText(alumno.getNombre());
        holder.lbApellidos.setText(alumno.getApellidos());
        holder.lbNota1.setText(String.valueOf(alumno.getNota1()));
        holder.lbNota2.setText(String.valueOf(alumno.getNota2()));
        holder.lbNota3.setText(String.valueOf(alumno.getNota3()));

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEditar(holder.getAdapterPosition()).show();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarBorrar(holder.getAdapterPosition()).show();
            }
        });
    }

    private AlertDialog confirmarEditar(int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("modificar alumno");
        builder.setCancelable(false);

        View productView = LayoutInflater.from(context).inflate(R.layout.add_alumnoo, null);
        EditText txtNombre = productView.findViewById(R.id.txtNombreAddAlumno);
        EditText txtApellidos = productView.findViewById(R.id.txtApellidosAddAlumno);
        EditText txtNota1 = productView.findViewById(R.id.txtNota1AddAlumno);
        EditText txtNota2 = productView.findViewById(R.id.txtNota2AddAlumno);
        EditText txtNota3 = productView.findViewById(R.id.txtNota3AddAlumno);
        builder.setView(productView);

        Alumno alumno = objects.get(posicion);
        txtNombre.setText(alumno.getNombre());
        txtApellidos.setText(alumno.getApellidos());
        txtNota1.setText(String.valueOf(alumno.getNota1()));
        txtNota2.setText(String.valueOf(alumno.getNota2()));
        txtNota3.setText(String.valueOf(alumno.getNota3()));

        builder.setNegativeButton("Cancelar", null);

        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtNombre.getText().toString().isEmpty() && !txtApellidos.getText().toString().isEmpty()
                        && !txtNota1.getText().toString().isEmpty() && !txtNota2.getText().toString().isEmpty()
                        && !txtNota3.getText().toString().isEmpty()){

                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setApellidos(txtApellidos.getText().toString());
                    alumno.setNota1(Float.parseFloat(txtNota1.getText().toString()));
                    alumno.setNota2(Float.parseFloat(txtNota2.getText().toString()));
                    alumno.setNota3(Float.parseFloat(txtNota3.getText().toString()));
                    notifyItemChanged(posicion);
                    try {
                        daoAlumnos.update(alumno);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return builder.create();
    }

    private AlertDialog confirmarBorrar(int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Estas seguro?");
        builder.setCancelable(false);

        builder.setNegativeButton("Cancelar", null);

        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    daoAlumnos.deleteById(objects.get(posicion).getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });

        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class AlumnoVH extends RecyclerView.ViewHolder {

        TextView lbNombre, lbApellidos, lbNota1, lbNota2, lbNota3;
        ImageButton imgEdit, imgDelete;
        public AlumnoVH(@NonNull View itemView) {
            super(itemView);

            lbNombre =itemView.findViewById(R.id.lbNombreViewAlumno);
            lbApellidos =itemView.findViewById(R.id.lbApellidosViewAlumno);
            lbNota1 =itemView.findViewById(R.id.lbNota1ViewAlumno);
            lbNota2 =itemView.findViewById(R.id.lbNota2ViewAlumno);
            lbNota3 =itemView.findViewById(R.id.lbNota3ViewAlumno);
            imgEdit =itemView.findViewById(R.id.imgEditViewAlumno);
            imgDelete =itemView.findViewById(R.id.imgDeleteViewAlumno);
        }
    }
}
