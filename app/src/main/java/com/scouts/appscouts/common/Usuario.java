package com.scouts.appscouts.common;

public class Usuario {
    private int id;
    private String email;
    private String nombre;
    private String apellidos;
    private String password;
    private boolean validado;

    public Usuario() {
    }

    public Usuario(int id, String email, String nombre, String apellidos, String password, boolean validado) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.validado = validado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
}

//rayper#wheels
