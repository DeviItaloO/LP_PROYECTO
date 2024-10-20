package com.example.notasrecordatorio;

import com.example.notasrecordatorio.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notasrecordatorio.ui.notas.Nota;
import com.google.android.material.snackbar.Snackbar;
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

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<Nota> listaDeNotas;
    private CalendarView calendarView;
    private EditText inputNota;
    private Button btnGuardarNota;
    private String fechaSeleccionada;

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

                    navController.navigate(R.id.nav_notas);
                }
            });
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_notas, R.id.nav_slideshow)
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
                    } else {
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
            Log.e("MainActivity", "Error during onCreate:", e);
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
        //Aun no lo utilizo
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Aun no lo utilizo
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la lista de notas
        listaDeNotas = new ArrayList<>();

        // Enlazamos el CalendarView, el EditText y el botón
        calendarView = findViewById(R.id.calendarView);
        inputNota = findViewById(R.id.inputNota);
        btnGuardarNota = findViewById(R.id.btnGuardarNota);

        // Cuando el usuario seleccione una fecha en el calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fechaSeleccionada = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

        // Cuando el usuario haga clic en "Guardar Nota"
        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contenidoNota = inputNota.getText().toString();
                if (!contenidoNota.isEmpty() && fechaSeleccionada != null) {
                    agregarNota(fechaSeleccionada, contenidoNota);
                    inputNota.setText(""); // Limpiar el campo de texto después de guardar
                    mostrarNotas(); // Mostrar todas las notas almacenadas
                }
            }
        });
    }

    // Método para agregar una nota a la lista
    private void agregarNota(String fecha, String contenido) {
        Nota nuevaNota = new Nota(fecha, contenido);
        listaDeNotas.add(nuevaNota);
        Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
    }

    // Método para mostrar todas las notas almacenadas
    private void mostrarNotas() {
        for (Nota nota : listaDeNotas) {
            System.out.println(nota);
        }
    }

    // Método para actualizar una nota
    private void actualizarNota(String fecha, String nuevoContenido) {
        for (Nota nota : listaDeNotas) {
            if (nota.getFecha().equals(fecha)) {
                nota.setContenido(nuevoContenido);
                Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    // Método para eliminar una nota
    private void eliminarNota(String fecha) {
        Iterator<Nota> iterator = listaDeNotas.iterator();
        while (iterator.hasNext()) {
            Nota nota = iterator.next();
            if (nota.getFecha().equals(fecha)) {
                iterator.remove();
                Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}