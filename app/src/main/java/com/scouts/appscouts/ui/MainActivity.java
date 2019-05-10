package com.scouts.appscouts.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scouts.appscouts.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        events();

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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniciarSesion() {
        boolean datosOk = true;
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty()){
            etEmail.setError("Se requiere el email");
            datosOk = false;
        }else if (password.isEmpty()){
            etPassword.setError("Se requiere la contraseña");
            datosOk = false;
        }

        if (datosOk){
            // TODO implementar inicio BBDD
            Intent i = new Intent(this, PanelActivity.class);
            startActivity(i);
        }

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
}
