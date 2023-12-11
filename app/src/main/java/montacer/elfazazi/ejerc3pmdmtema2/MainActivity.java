package montacer.elfazazi.ejerc3pmdmtema2;

import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import montacer.elfazazi.ejerc3pmdmtema2.adapter.AlumnoAdapter;
import montacer.elfazazi.ejerc3pmdmtema2.configuracion.Configuracion;
import montacer.elfazazi.ejerc3pmdmtema2.databinding.ActivityMainBinding;
import montacer.elfazazi.ejerc3pmdmtema2.helpers.AlumnosHelper;
import montacer.elfazazi.ejerc3pmdmtema2.modelos.Alumno;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Alumno> listaAlumnos;
    private AlumnosHelper helper;
    private Dao<Alumno, Integer> daoAlumnos;
    private AlumnoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaAlumnos = new ArrayList<>();

       adapter = new AlumnoAdapter(MainActivity.this, listaAlumnos, R.layout.view_alumno);
        layoutManager = new LinearLayoutManager(MainActivity.this);


        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        helper = new AlumnosHelper(this, Configuracion.BD_NAME, null, Configuracion.BD_VERSION);
        if (helper != null){
            try {
                daoAlumnos = helper.getDaoAlumnos();
                listaAlumnos.addAll(daoAlumnos.queryForAll());
              adapter.notifyItemRangeInserted(0, listaAlumnos.size());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearAlumno().show();
            }
        });
    }

    private AlertDialog crearAlumno(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Crear alumno");
        builder.setCancelable(false);

        View productView = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_alumnoo, null);

        EditText txtNombre = productView.findViewById(R.id.txtNombreAddAlumno);
        EditText txtApellidos = productView.findViewById(R.id.txtApellidosAddAlumno);
        EditText txtNota1 = productView.findViewById(R.id.txtNota1AddAlumno);
        EditText txtNota2 = productView.findViewById(R.id.txtNota2AddAlumno);
        EditText txtNota3 = productView.findViewById(R.id.txtNota3AddAlumno);

        builder.setView(productView);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtNombre.getText().toString().isEmpty() || txtApellidos.getText().toString().isEmpty()
                        || txtNota1.getText().toString().isEmpty() || txtNota2.getText().toString().isEmpty()
                        || txtNota3.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    Alumno alumno = new Alumno(
                            txtNombre.getText().toString(),
                            txtApellidos.getText().toString(),
                            Float.parseFloat(txtNota1.getText().toString()),
                            Float.parseFloat(txtNota2.getText().toString()),
                            Float.parseFloat(txtNota3.getText().toString())
                    );
                    listaAlumnos.add(alumno);
                    adapter.notifyItemInserted(listaAlumnos.size()-1);
                    try {
                        daoAlumnos.create(alumno);
                        int id = daoAlumnos.extractId(alumno);
                        alumno.setId(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        return builder.create();
    }
}