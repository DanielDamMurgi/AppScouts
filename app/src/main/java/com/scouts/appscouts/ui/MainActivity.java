package com.scouts.appscouts.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.scouts.appscouts.LiveData.LiveData;
import com.scouts.appscouts.R;
import com.scouts.appscouts.common.BBDD;
import com.scouts.appscouts.common.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.fabric.sdk.android.Fabric;
// TODO: REVISAR CLASE
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btLogin;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());



        findView();
        events();

        comprobarInicio();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_registro) {
            Intent i = new Intent(this, RegistroActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniciarSesion() {
        boolean datosOk = true;
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        //TODO: añadir campos texto a resources
        if (email.isEmpty()){
            etEmail.setError("Se requiere el email");
            datosOk = false;
        }else if (password.isEmpty()){
            etPassword.setError("Se requiere la contraseña");
            datosOk = false;
        }

        if (datosOk){
            String consulta = "select * from usuario where email='"+email+"' and password='"+password+"'";
            new LoginUsuario(consulta).execute();
        }

    }

    private void comprobarInicio(){
        // TODO: Comprobar si el usuario tiene la sesion iniciada al entrar
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                iniciarSesion();
                break;
        }
    }

    private void findView(){
        etEmail = findViewById(R.id.editTextloginEmail);
        etPassword = findViewById(R.id.editTextLoginPassword);
        btLogin = findViewById(R.id.buttonLogin);
    }

    private void events(){
        btLogin.setOnClickListener(this);
    }

    public class LoginUsuario extends AsyncTask<Void,Void, ResultSet> {
        String consulta;
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        public LoginUsuario(String consulta){
            this.consulta = consulta;
        }

        @Override
        protected ResultSet doInBackground(Void... voids) {
            try {
                connection = DriverManager.getConnection(BBDD.getDIRECCION(), BBDD.getUSUARIO(), BBDD.getCONTRASEÑA());
                statement = connection.createStatement();
                publishProgress();
                resultSet = statement.executeQuery(consulta);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultSet;
        }// FIN doInBackGround

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String email = resultSet.getString(2);
                    String nombre = resultSet.getString(3);
                    String apellidos = resultSet.getString(4);
                    String clave = resultSet.getString(5);
                    int validado = resultSet.getInt(6);
                    boolean valid = false;
                    if (validado == 1){
                        valid = true;
                    }
                    usuario = new Usuario(id, email, nombre, apellidos, clave, valid);
                }
                connection.close();
                statement.cancel();
                this.resultSet.close();

                if (usuario == null){
                    //Toast.makeText(getApplicationContext(),getResources().getString(R.string.credenciales_incorrectas),Toast.LENGTH_LONG).show();
                }else{
                    if (usuario.isValidado()){
                        //login = true;
                        LiveData.setUsuario(usuario);
                        //MainActivity.ActualizarEstado(login,getApplicationContext());
                        Intent i = new Intent(getApplication(), PanelActivity.class);
                        startActivity(i);
                        finish();
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//FIN onPostExecute
    }// FIN CLASE LoginUsuario
}
