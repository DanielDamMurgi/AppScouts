package com.scouts.appscouts.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.scouts.appscouts.R;
import com.scouts.appscouts.common.BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etNombre, etApellidos, etClave, etRepClave;
    private Button btRegistro;
    private boolean datosOk = true;
    private String email, nombre, apellidos, clave, repClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();

        findView();
        events();
    }

    private void findView() {
        etEmail = findViewById(R.id.editTextRegistoEmail);
        etNombre = findViewById(R.id.editTextRegistroNombre);
        etApellidos = findViewById(R.id.editTextRegistroApellidos);
        etClave = findViewById(R.id.editTextRegistroContrasena);
        etRepClave = findViewById(R.id.editTextRegistroRepContrasena);
        btRegistro = findViewById(R.id.buttonRegistro);
    }

    private void events() {
        btRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegistro:
                email = etEmail.getText().toString().trim();
                nombre = etNombre.getText().toString().trim();
                apellidos = etApellidos.getText().toString().trim();
                clave = etClave.getText().toString().trim();
                repClave = etRepClave.getText().toString().trim();
                //TODO: insertar campos texto en resources
                if (email.isEmpty()) {
                    datosOk = false;
                    etEmail.setError("Se requiere el email");
                    break;
                } else if (nombre.isEmpty()) {
                    datosOk = false;
                    etNombre.setError("Se requiere el nombre");
                    break;
                } else if (apellidos.isEmpty()) {
                    datosOk = false;
                    etApellidos.setError("Se requieren los apellidos");
                    break;
                } else if (!validarContraseña()) {
                    datosOk = false;
                    break;
                }else if(!validarEmail(email)){
                    datosOk = false;
                    etEmail.setError("Email no válido");
                    break;
                }

                if (datosOk){
                    String insert = "INSERT INTO usuario (email, nombre, apellidos, password, validado, id_grupo) "
                            + "VALUES ('" + email + "', '" + nombre + "', '"+apellidos+"','" + clave + "', 1, 1);";

                    new RegistrarUsuario(insert).execute();

                    RegistroActivity.super.finish();
                }
                break;
        }
    }

    private boolean validarContraseña() {
        if (!clave.isEmpty()) {
            if (clave.length() < 6) {
                //etClave.setError(getResources().getString(R.string.clave_min_caracteres));
                return false;
            } else {
                if (clave.equals(repClave)) {
                    return true;
                } else {
                    //etClave.setError(getResources().getString(R.string.clave_no_coinciden));
                    return false;
                }
            }
        } else {
            etClave.setError("Se requiere la contraseña");
            return false;
        }

    }//FIN VALIDAR CONTRASEÑA

    private boolean validarEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public class RegistrarUsuario extends AsyncTask<Object, Object, Integer> {

        String consulta;
        Connection connection;
        Statement statement;

        public RegistrarUsuario(String consulta) {
            this.consulta = consulta;

        }

        @Override
        protected Integer doInBackground(Object... objects) {
            int result = 0;
            try {
                connection = DriverManager.getConnection(BBDD.getDIRECCION(), BBDD.getUSUARIO(), BBDD.getCONTRASEÑA());
                statement = connection.createStatement();
                publishProgress();

                result = statement.executeUpdate(consulta);

            } catch (SQLException e) {
                //showToast(getResources().getString(R.string.usuario_no_insertar));
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer resultSet) {
            super.onPostExecute(resultSet);

            if (resultSet == 1) {
                //showToast(getResources().getString(R.string.usuario_registrado));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            } else {
                //showToast(getResources().getString(R.string.usuario_no_registrado));
            }

            try {
                connection.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //dialog.dismiss();
        }
    }//Fin AsynTack

    private void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
