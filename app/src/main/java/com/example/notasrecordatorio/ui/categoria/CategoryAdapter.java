package com.example.notasrecordatorio.ui.categoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.network.dto.CategoriaDTO;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoriaDTO> categories;

    public CategoryAdapter(List<CategoriaDTO> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoriaDTO category = categories.get(position);
        holder.categoryNameTextView.setText(category.getNombre());
        // ... (mostrar otros datos de la categoría) ...
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategories(List<CategoriaDTO> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView); // Asegúrate de que este ID coincida con tu layout item_categoria.xml
            // ... (inicializar otros views del item) ...
        }
    }
}