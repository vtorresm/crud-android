package com.example.formpersonalizado.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.formpersonalizado.entidades.Persona;
import com.example.formpersonalizado.util.Constantes;
import com.example.formpersonalizado.util.PersonaBD;

import java.util.ArrayList;
import java.util.List;

public class DAOPersona {
    PersonaBD personaBD;
    SQLiteDatabase db;
    private Context context;

    public DAOPersona(Context context){
        this.context = context;
        this.personaBD = new PersonaBD(context);
    }

    public void abrirBD(){
        db = personaBD.getWritableDatabase();
    }

    public String registrarPersona(Persona persona){
        String mensaje = "";
        try {
            ContentValues values = new ContentValues();
            values.put("dni", persona.getDni());
            values.put("nombres", persona.getNombres());
            values.put("apellidos", persona.getApellidos());
            values.put("correo", persona.getCorreo());
            values.put("edad", persona.getEdad());
            long resultado = db.insert(Constantes.NOMBRE_TABLA, null, values);

            if (resultado == -1){
                mensaje = "Error al insertar";
            }else{
                mensaje = "Se registró correctamente";
            }
        }catch (Exception e){
            Log.d("=>",e.getMessage());
        }
        return mensaje;
    }

    public String modificarPersona(Persona persona){
        String mensaje = "";

        try{
            ContentValues values = new ContentValues();
            values.put("dni", persona.getDni());
            values.put("nombres", persona.getNombres());
            values.put("apellidos", persona.getApellidos());
            values.put("correo", persona.getCorreo());
            values.put("edad", persona.getEdad());
            long resultado = db.update(Constantes.NOMBRE_TABLA, values, "id="+persona.getId(), null);

            if (resultado == -1){
                mensaje = "Error al actualizar";
            }else{
                mensaje = "Se actualizó correctamente";
            }

        }catch (Exception e){
            Log.d("=>",e.getMessage());
        }
        return mensaje;
    }

    public String eliminarPersona(int id){
        String mensaje = "";
        try{
            long resultado = db.delete(Constantes.NOMBRE_TABLA, "id="+id, null);

            if (resultado == -1){
                mensaje = "Error al eliminar";
            }else{
                mensaje = "Se elimino correctamente";
            }

        }catch (Exception e){
            Log.d("=>",e.getMessage());
        }
        return mensaje;
    }

    public List<Persona> getPersona(){

        List<Persona> listarPersonas = new ArrayList<>();

        try {
            Cursor c = db.rawQuery("SELECT * FROM "+Constantes.NOMBRE_TABLA, null);

            while (c.moveToNext()){
                listarPersonas.add(new Persona(
                   c.getInt(0),
                   c.getString(1),
                   c.getString(2),
                   c.getString(3),
                   c.getString(4),
                   c.getInt(5)));
            }

        } catch (Exception e){
            Log.d("=>",e.getMessage());
        }

        return listarPersonas;

    }
}
