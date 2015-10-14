package com.example.docente.apppreferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText etNombre;
    private EditText etApellido;
    private EditText etEmail;
    private EditText etCargo;
    private Button btnSave;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCargo = (EditText) findViewById(R.id.etCargo);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
        Context context = this.getApplicationContext();
        //Instanciamos el objeto SharedPreferences y creamos un fichero Privado bajo el
        //nombre definido con la clave preference_file_key en el fichero string.xml
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Tambien es posible obtener una instancia del objeto SharedPreferences con la siguiente
        // sentencia, esta opcin es usada cuando se sabe que en la aplicacion no se usara mas
        // de un fichero de preferencias:
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        getProfile();
    }
    private void saveProfile() {
    //Capturamos en una variable de tipo String
    String nombre = etNombre.getText().toString();
    String apellido = etApellido.getText().toString();
    String email = etEmail.getText().toString();
    String cargo = etCargo.getText().toString();

    //Instanciamos un objeto del SharedPreferences.Editor
    //el cual nos permite almacenar con su metodo putString
    //los 4 valores del perfil profesional asociandolos a una
    // clave la cual definimos como un string en el fichero strings.xml
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(getString(R.string.nombre_key), nombre);
    editor.putString(getString(R.string.apellido_key), apellido);
    editor.putString(getString(R.string.email_key), email);
    editor.putString(getString(R.string.cargo_key), cargo);

    //NOTA: En el caso de que necesitemos gauirdar un valor numerico podeis usar
    //el metodo putInt en vez del putString.

    //Con el método commit logramos guardar los datos en el fichero
    //de preferncias compartidas de nombre cuyo nombre se defini en
    // el String preference_file_key
    editor.commit();

    //Notificamos la usuario de que se han guardado los datos del perfil correctamente.
    Toast.makeText(getApplicationContext(), getString(R.string.msg_save), Toast.LENGTH_SHORT).show();
}
    //Para obtener los datos previamente guardados
    // simplemente empleamos el método getString()
    // del objeto SharedPreferences
    private void getProfile() {
    String nombre = sharedPref.getString((getString(R.string.nombre_key)), "");
    etNombre.setText(nombre);
    String apellido = sharedPref.getString((getString(R.string.apellido_key)), "");
    etApellido.setText(apellido);
    String email = sharedPref.getString((getString(R.string.email_key)), "");
    etEmail.setText(email);
    String cargo = sharedPref.getString((getString(R.string.cargo_key)), "");
    etCargo.setText(cargo);
}
    private void clearProfile() {
    //Para borrar el registro de algun dato en elfichero compartido
    // sencillamente empleamos el metodo remove(key)
    // del objeto SharedPreferences.Editor
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.remove(getString(R.string.nombre_key));
    editor.remove(getString(R.string.apellido_key));
    editor.remove(getString(R.string.email_key));
    editor.remove(getString(R.string.cargo_key));
    editor.commit();

    //Limpiamos los EditText de la pantalla
    etNombre.setText("");
    etApellido.setText("");
    etEmail.setText("");
    etCargo.setText("");
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            clearProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
