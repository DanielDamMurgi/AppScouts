package com.scouts.appscouts.LiveData;

import android.arch.lifecycle.ViewModel;

import com.scouts.appscouts.common.Usuario;

public class LiveData extends ViewModel {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        LiveData.usuario = usuario;
    }
}
