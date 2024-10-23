package com.example.notasrecordatorio.ui.usuario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.widget.Toast;

import com.example.notasrecordatorio.Enum.BaseUrlEnum;
import com.example.notasrecordatorio.network.cliente.ApiClient;
import com.example.notasrecordatorio.network.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EliminadoFragment extends DialogFragment {
    private Long id;

    public static EliminadoFragment newInstance(Long id) {
        try{
            EliminadoFragment eliminadoFragment = new EliminadoFragment();
            Bundle args = new Bundle();
            args.putLong("id", id);
            eliminadoFragment.setArguments(args);
            return eliminadoFragment;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try{
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

            alertBuilder.setTitle("Eliminar Usuario")
                    .setMessage("¿Estas seguro de eliminar este usuario?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getArguments() != null) {
                                id = getArguments().getLong("id");
                            }
                            eliminarUsuario(id);

                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //no hara nada solo cierra
                        }
                    });
            return alertBuilder.create();
        }catch (Exception e){
            return new AlertDialog.Builder(getActivity()).create();//dialgo por defecto xd
        }
    }

    private void eliminarUsuario(Long id) {
        try{
            ApiService apiService = ApiClient.getRetrofit(BaseUrlEnum.BASE_URL_USUARIO).create(ApiService.class);
            Call<Void> call = apiService.eliminarUsuario(id);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (isAdded()){
                        if (response.isSuccessful()) {
                            Toast.makeText(requireContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                            dismiss();
                            //Navigation.findNavController(requireView()).navigate(R.id.nav_listado);
                        } else {
                            Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.d("EliminadoFragment", "Response: " + response.message().toString());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                        Log.d("EliminadoFragment", "Response: " + t);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(requireContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }
}