package com.example.rafa.practica3pmdmfragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentoListView extends Fragment implements OnFragmentoInteraccionListener{

    private String valor;
    private ImageButton bt;
    private EditText ed1, ed2, ed3;
    private Context contexto;
    private ArrayList<Contacto> lista;
    private View fragmentView;
    private ListView lv;
    private Adaptador ad;
    private OnFragmentoInteraccionListener listener;

    public FragmentoListView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_fragmento_list_view, container, false);
        bt = (ImageButton) fragmentView.findViewById(R.id.imageButton);

        if(savedInstanceState != null){
            lista = (ArrayList)savedInstanceState.getSerializable("valor");
        }else{
            ListaTelefonos x = new ListaTelefonos(getActivity());
            lista = x.getGestion();

            for(Contacto aux:lista){
                aux.setTelefonos((ArrayList<String>) x.getListaTelefonos(getActivity(),aux.getId()));
            }

            Collections.sort(lista);
        }

        init();
        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("valor", lista);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentoInteraccionListener){
            listener = (OnFragmentoInteraccionListener) context;
        }else{
            throw new ClassCastException("Solo acepto OnFragmentoInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnFragmentoInteraccionListener){
            listener = (OnFragmentoInteraccionListener) activity;
        }else{
            throw new ClassCastException("Solo acepto OnFragmentoInteractionListener");
        }
    }

    private void init(){

        lv = (ListView) fragmentView.findViewById(R.id.lvLista);
        ad = new Adaptador(getActivity(),R.layout.item,lista);
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onInteraccion(lista.get(position));
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                añadir();
            }
        });
    }

    @Override
    public void onInteraccion(Contacto datos) {

        FragmentoDetalles fragmento = (FragmentoDetalles) getFragmentManager().findFragmentById(R.id.fragDetalles);
        if (fragmento == null || !fragmento.isInLayout()) {
            //Vertical
            Intent i = new Intent(getActivity(),SecondActivity.class);
            if(datos.getTelefonos().size() > 1) {
                i.putExtra("id",datos.getId());
                i.putExtra("nombre",datos.getNombre());
                i.putExtra("telefono1",datos.getTelefono(0));
                i.putExtra("telefono2", datos.getTelefono(1));
            }else if (datos.getTelefonos().size() <= 1){
                i.putExtra("id",datos.getId());
                i.putExtra("nombre",datos.getNombre());
                i.putExtra("telefono1",datos.getTelefono(0));
            }
            startActivity(i);
        }else{
            //Horizontal
            fragmento.setDato(datos);
        }
    }

    public void añadir(){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Añadir");
        LayoutInflater inflater= LayoutInflater.from(getActivity());

        final View vista = inflater.inflate(R.layout.anadir, null);

        alert.setView(vista);

        alert.setPositiveButton(R.string.añadir,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        ArrayList<String> listaT;
                        long id = lista.size() - 1;

                        ed1 = (EditText) vista.findViewById(R.id.nombreAn);
                        ed2 = (EditText) vista.findViewById(R.id.telefonoAn);
                        ed3 = (EditText) vista.findViewById(R.id.telefono2An);

                        String nombre = ed1.getText().toString();
                        listaT = new ArrayList<>();
                        listaT.add(ed2.getText().toString());
                        listaT.add(ed3.getText().toString());

                        Contacto nuevo = new Contacto(id, nombre, listaT);

                        lista.add(nuevo);
                        Collections.sort(lista, new OrdenarLista());

                        ad.notifyDataSetChanged();
                    }
                });

        alert.setNegativeButton(R.string.dial_atras, null);
        alert.show();
    }
}
