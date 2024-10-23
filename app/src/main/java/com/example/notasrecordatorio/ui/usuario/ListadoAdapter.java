package com.example.notasrecordatorio.ui.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import com.example.notasrecordatorio.network.interfaz.usuario.IUsuarioActualizar;
import com.example.notasrecordatorio.network.interfaz.usuario.IUsuarioEliminar;

import java.util.List;
import android.widget.Button;

public class ListadoAdapter extends RecyclerView.Adapter<ListadoAdapter.UsuarioViewHolder> {

    private static List<UsuarioDTO> usuarios;
    private static IUsuarioActualizar actualizar;
    private static IUsuarioEliminar eliminar;

    public ListadoAdapter(List<UsuarioDTO> usuarios)  {
        this.usuarios = usuarios;
    }
    public void setActualizar(IUsuarioActualizar actualizar) {
        this.actualizar = actualizar;
    }
    public void setEliminar(IUsuarioEliminar eliminar) {
        this.eliminar = eliminar;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        try{
            UsuarioDTO usuario = usuarios.get(position);
            holder.nombre_text_view.setText("Nombre: " + usuario.getNombre());
            holder.email_text_view.setText("Correo: " + usuario.getEmail());
            holder.fechaCreacion_text_view.setText("Fecha de Creación: " + usuario.getFechaCreacion());

            if (usuario.getNotas() != null && !usuario.getNotas().isEmpty()) {
                String estado = usuario.getNotas().get(0).getEstado();
                switch (estado) {
                    case "verde":
                        holder.estado_view.setBackgroundColor(ContextCompat.getColor(holder.estado_view.getContext(), R.color.verde));
                        break;
                    case "amarillo":
                        holder.estado_view.setBackgroundColor(ContextCompat.getColor(holder.estado_view.getContext(), R.color.amarillo));
                        break;
                    case "rojo":
                        holder.estado_view.setBackgroundColor(ContextCompat.getColor(holder.estado_view.getContext(), R.color.rojo));
                        break;
                    default:
                        holder.estado_view.setBackgroundColor(ContextCompat.getColor(holder.estado_view.getContext(), R.color.gris));
                        break;
                }
            } else {
                holder.estado_view.setBackgroundColor(ContextCompat.getColor(holder.estado_view.getContext(), R.color.gris));
            }
            holder.nota_text_view.setText("Cantidad de notas: " + usuario.getNotas().size());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_text_view, email_text_view, nota_text_view, fechaCreacion_text_view;
        View estado_view;
        Button editarButton, eliminarButton;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            try{
                nombre_text_view = itemView.findViewById(R.id.tv_item_nombre);
                email_text_view = itemView.findViewById(R.id.tv_item_email);
                fechaCreacion_text_view = itemView.findViewById(R.id.tv_item_fechaCreacion);
                nota_text_view = itemView.findViewById(R.id.tv_item_nota);
                estado_view = itemView.findViewById(R.id.v_item_estado);
                editarButton = itemView.findViewById(R.id.btn_editar);
                eliminarButton = itemView.findViewById(R.id.btn_eliminar);

                editarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            UsuarioDTO usuario = usuarios.get(position);
                            if (actualizar != null) {
                                actualizar.actualizar(usuario);
                            }
                        }
                    }
                });
                eliminarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            eliminar.eliminar(usuarios.get(position).getId());
                        }
                    }
                });
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
