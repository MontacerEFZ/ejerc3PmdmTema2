package montacer.elfazazi.ejerc3pmdmtema2.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "alumnos")
public class Alumno {

    @DatabaseField(columnName = "id_alumno", generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nombre;

    @DatabaseField(canBeNull = false)
    private String apellidos;

    @DatabaseField(canBeNull = false)
    private float nota1;

    @DatabaseField(canBeNull = false)
    private float nota2;

    @DatabaseField(canBeNull = false)
    private float nota3;

    @DatabaseField(canBeNull = false)
    private float media;


    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, float nota1, float nota2, float nota3) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        calcularMedia();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
        calcularMedia();
    }

    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
        calcularMedia();
    }

    public float getNota3() {
        return nota3;
    }

    public void setNota3(float nota3) {
        this.nota3 = nota3;
        calcularMedia();
    }

    public float getMedia() {
        return media;
    }

    private void calcularMedia(){
        media = (nota1+nota2+nota3)/3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                ", nota3=" + nota3 +
                ", total=" + media +
                '}';
    }
}
