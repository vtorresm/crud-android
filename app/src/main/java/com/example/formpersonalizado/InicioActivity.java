package com.example.formpersonalizado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.formpersonalizado.entidades.Persona;
import com.example.formpersonalizado.modelo.DAOPersona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    DAOPersona daoPersona = new DAOPersona(this);
    List<Persona> listaPersonas = new ArrayList<>();
    AdaptadorPersonalizado adaptadorPersonalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        asignarReferencias();
        daoPersona.abrirBD();
        mostrarPersonas();
    }

    private void mostrarPersonas(){
        listaPersonas = daoPersona.getPersona();
        adaptadorPersonalizado = new AdaptadorPersonalizado(InicioActivity.this, listaPersonas);
        recyclerView.setAdapter(adaptadorPersonalizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));
    }

    private void asignarReferencias() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}