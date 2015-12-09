package com.example.rafa.practica3pmdmfragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundo_layout);

        int id = getIntent().getExtras().getInt("nombre");
        String dato1 = getIntent().getExtras().getString("nombre");
        String dato2 = getIntent().getExtras().getString("telefono1");
        String dato3 = getIntent().getExtras().getString("telefono2");
        ArrayList<String> a = new ArrayList<>();
        a.add(dato2);
        a.add(dato3);
        Contacto nuevo = new Contacto(id,dato1,a);
        FragmentoDetalles fragmento = (FragmentoDetalles) getFragmentManager().findFragmentById(R.id.fragDetalles);
        fragmento.setDato(nuevo);
    }
}
