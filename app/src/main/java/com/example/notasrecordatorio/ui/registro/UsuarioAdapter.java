package com.example.notasrecordatorio.ui.registro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.dto.UsuarioDTO;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<UsuarioDTO> usuarios;

    public UsuarioAdapter(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
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
        UsuarioDTO usuario = usuarios.get(position);
        holder.tvNombre.setText(usuario.getNombre());
        holder.tvEmail.setText(usuario.getEmail());
        holder.tvFechaCreacion.setText(usuario.getFechaCreacion());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEmail, tvFechaCreacion;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvFechaCreacion = itemView.findViewById(R.id.tvFechaCreacion);
        }
    }
}
