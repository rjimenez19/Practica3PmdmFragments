package com.example.rafa.practica3pmdmfragments;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Contacto> {

    private Context contexto;
    private int recurso;
    private ArrayList<Contacto> objeto;
    private LayoutInflater inflador;

    static class ViewHolder {
        public TextView tv1;
        public ImageView iv;
    }

    public Adaptador(Context context, int recurso, ArrayList<Contacto> objeto){
        super(context, recurso, objeto);
        this.contexto = context;
        this.recurso = recurso;
        this.objeto = objeto;
        this.inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder gv = new ViewHolder();
        if(convertView==null){
            convertView = inflador.inflate(recurso, null);
            TextView tv = (TextView) convertView.findViewById(R.id.nombre);
            gv.tv1 = tv;

            ImageView iv = (ImageView) convertView.findViewById(R.id.foto);
            gv.iv = iv;
            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }
        gv.iv.setTag(position);
        gv.tv1.setText(objeto.get(position).getNombre());

        return convertView;
    }

    public boolean borrar(int position) {
        try {
            objeto.remove(position);
            this.notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}
