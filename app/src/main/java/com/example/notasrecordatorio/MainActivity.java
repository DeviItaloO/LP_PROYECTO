package com.example.notasrecordatorio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notasrecordatorio.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            Log.d("MainActivity"," en el create");

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                    //validacion para que solo exista una instancia de notas
                    if (navController.getCurrentDestination() != null &&
                        navController.getCurrentDestination().getId() == R.id.nav_notas) {
                        return;
                    }
                    //en caso se crea mas instancias, las elimina y libera la pila para evitar duplicados
                    /*navController.popBackStack(R.id.nav_home, true);*/
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
                    // fecha actual
                    Bundle bundle = new Bundle();
                    bundle.putString("fechaSeleccionada", fechaActual);
                    navController.navigate(R.id.nav_notas, bundle);
                }
            });
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home,
                    R.id.nav_notas,
                    R.id.nav_slideshow,
                    R.id.nav_usuarios,
                    R.id.nav_listado,
                    R.id.nav_recordatorio)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            // Manejo del NavigationView
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Manejo la selección de elementos
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        navController.navigate(R.id.nav_home);
                    } else if (id == R.id.nav_notas) {
                        navController.navigate(R.id.nav_notas);
                    } else if (id == R.id.nav_slideshow) {
                        navController.navigate(R.id.nav_slideshow);
                    } else if(id == R.id.nav_usuarios){
                        navController.navigate(R.id.nav_usuarios);
                    }else if(id == R.id.nav_listado){
                        navController.navigate(R.id.nav_listado);
                    }else if(id == R.id.nav_recordatorio){
                        navController.navigate(R.id.nav_recordatorio);
                    }else{
                        return false;
                    }
                    // Cerrar el Drawer después de seleccionar
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

            editarTextViewNombre();
            editarTextViewCorreo();

        }catch (Exception e){
            Log.e("MainActivity", "Error en onCreate:", e);
            Toast.makeText(this, "Error al iniciar la aplicación", Toast.LENGTH_LONG).show();
        }
    }
    //setea el nombre del usuario en el header
    private void editarTextViewNombre() {
        try{
            Intent intent = getIntent();
            String nombre = intent.getStringExtra("nombre");

            NavigationView navView = findViewById(R.id.nav_view);
            View headerView = navView.getHeaderView(0);

            TextView TextViewNombre = headerView.findViewById(R.id.textView_Nombre);
            TextViewNombre.setText("Bienvenido : " + nombre);
        }catch (Exception e){
            Log.e("MainActivity ","editarTextViewNombre-> Error al configurar el nombre:", e);
        }

    }

    //setea el nombre del usuario en el header
    private void editarTextViewCorreo() {
        try {
            Intent intent = getIntent();
            String correo = intent.getStringExtra("correo");

            NavigationView navView = findViewById(R.id.nav_view);
            View headerView = navView.getHeaderView(0);

            TextView textViewCorreo = headerView.findViewById(R.id.textView);
            if (correo != null) {
                textViewCorreo.setText(correo);
            }
        } catch (Exception e) {
            Log.e("MainActivity ", "editarTextViewCorreo-> Error al configurar el correo:", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}