package com.example.formpersonalizado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.formpersonalizado.entidades.Persona;
import com.example.formpersonalizado.modelo.DAOPersona;

public class MainActivity extends AppCompatActivity {

    EditText txtDNI, txtNombres, txtApellidos, txtCorreo, txtEdad;
    Button btnGuardar;

    String dni, nombres, apellidos, correo;
    int edad, id;
    boolean registra = true;

    DAOPersona daoPersona = new DAOPersona(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();
        recibirDatos();
    }

    private void recibirDatos() {
        if (getIntent().hasExtra("id")){
            registra = false;
            id = Integer.parseInt(getIntent().getStringExtra("id"));
            dni = getIntent().getStringExtra("dni");
            nombres = getIntent().getStringExtra("nombres");
            apellidos = getIntent().getStringExtra("apellidos");
            correo = getIntent().getStringExtra("correo");
            edad = Integer.parseInt(getIntent().getStringExtra("edad"));

            txtDNI.setText(dni);
            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
            txtCorreo.setText(correo);
            txtEdad.setText(edad+"");
        }
    }

    private void  asignarReferencias(){
        txtDNI       = findViewById(R.id.txtDNI);
        txtNombres   = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreo    = findViewById(R.id.txtCorreo);
        txtEdad      = findViewById(R.id.txtEdad);
        btnGuardar   = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validar()){
                   String mensaje;
                   daoPersona.abrirBD();

                   if (registra == true){
                       Persona p = new Persona(dni,nombres,apellidos,correo,edad);
                       mensaje = daoPersona.registrarPersona(p);
                   }else{
                       Persona p = new Persona(id,dni,nombres,apellidos,correo,edad);
                       mensaje = daoPersona.modificarPersona(p);
                   }

                   AlertDialog.Builder ventana = new AlertDialog.Builder(MainActivity.this);
                   ventana.setTitle("Mensaje Informativo");
                   ventana.setMessage(mensaje);
                   ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                           startActivity(intent);
                       }
                   });
                   ventana.create().show();
                }
            }
        });
    }

    private boolean validar(){
        boolean resultado = true;
        dni = txtDNI.getText().toString();
        nombres = txtNombres.getText().toString();
        apellidos = txtApellidos.getText().toString();
        correo = txtCorreo.getText().toString();

        if (!txtEdad.getText().toString().equals("")){
            edad = Integer.parseInt(txtEdad.getText().toString());
        }

        if (dni.equals("")){
            txtDNI.setError("DNI Obligatorio");
            resultado = false;
        }
        if (nombres.equals("")){
            txtNombres.setError("Nombres Obligatorio");
            resultado = false;
        }
        return resultado;
    }
}