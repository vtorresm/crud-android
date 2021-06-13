package com.example.formpersonalizado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formpersonalizado.entidades.Persona;
import com.example.formpersonalizado.modelo.DAOPersona;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.MyViewHolder> {

    private Context context;
    private List<Persona> listaPersonas = new ArrayList<>();

    public AdaptadorPersonalizado(Context context, List<Persona> listaPersonas){
        this.context = context;
        this.listaPersonas = listaPersonas;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.listar, parent, false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.MyViewHolder holder, int position) {
        holder.fila_dni.setText(listaPersonas.get(position).getDni()+"");
        holder.fila_nombre.setText(listaPersonas.get(position).getNombres()+" "+listaPersonas.get(position).getApellidos());
        holder.fila_correo.setText(listaPersonas.get(position).getCorreo()+"");
        holder.fila_edad.setText(listaPersonas.get(position).getEdad()+"");
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new
                        Intent(context, MainActivity.class);
                intent.putExtra("id", listaPersonas.get(position).getId()+"");
                intent.putExtra("dni", listaPersonas.get(position).getDni()+"");
                intent.putExtra("nombres", listaPersonas.get(position).getNombres()+"");
                intent.putExtra("apellidos", listaPersonas.get(position).getApellidos()+"");
                intent.putExtra("correo", listaPersonas.get(position).getCorreo()+"");
                intent.putExtra("edad", listaPersonas.get(position).getEdad()+"");
                context.startActivity(intent);
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Desea eliminar?");
                ventana.setMessage("Desea eliminar a la persona "+listaPersonas.get(position).getNombres());
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DAOPersona daoPersona = new DAOPersona(context);
                        daoPersona.abrirBD();
                        String mensaje = daoPersona.eliminarPersona(listaPersonas.get(position).getId());
                        AlertDialog.Builder v = new AlertDialog.Builder(context);
                        v.setTitle("Mensaje Informativo");
                        v.setMessage(mensaje);
                        v.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, InicioActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        v.create().show();
                    }
                });

                ventana.setNegativeButton("NO", null);
                ventana.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fila_dni, fila_nombre, fila_correo, fila_edad;
        ImageButton btnEditar, btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fila_dni = itemView.findViewById(R.id.fila_dni);
            fila_nombre = itemView.findViewById(R.id.fila_nombre);
            fila_correo = itemView.findViewById(R.id.fila_correo);
            fila_edad = itemView.findViewById(R.id.fila_edad);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
