package com.example.rafa.practica3pmdmfragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentoDetalles extends Fragment {

    private Contacto dato;
    private View fragmentView;
    private TextView tv1, tv2, tv3;

    public FragmentoDetalles() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_fragmento_detalles, container, false);
        tv1 = (TextView) fragmentView.findViewById(R.id.tvNombre);
        tv2 = (TextView) fragmentView.findViewById(R.id.tvTel1);
        tv3 = (TextView) fragmentView.findViewById(R.id.tvTel2);
        return fragmentView;
    }

    public void setDato(Contacto dato){
        this.dato = dato;
        if(dato.getTelefonos().size() > 1) {
            tv1.setText(dato.getNombre());
            tv2.setText(dato.getTelefono(0));
            tv3.setText(dato.getTelefono(1));
        }else if (dato.getTelefonos().size() <= 1){
            tv1.setText(dato.getNombre());
            tv2.setText(dato.getTelefono(0));
            tv3.setText("");
        }
    }
}
