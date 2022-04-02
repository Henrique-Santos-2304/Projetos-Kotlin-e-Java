package app.modelo.meusclientes.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.modelo.meusclientes.R;

import app.modelo.meusclientes.controller.ClientController;



public class ListShowClients extends Fragment {
    View view;
    ArrayAdapter<String> clientAdapter;

    public ListShowClients() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_list_show_client, container, false);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        ListView listClient = view.findViewById(R.id.listShowClients);
        EditText inputSearchClient = view.findViewById(R.id.inputSearchClient);

        txtTitulo.setText(R.string.nav_header_title);
        ClientController clientController = new ClientController(getContext());

        List<String> dataClients = clientController.catchAndFormatDataForList(); //Busca os cliente no bd
        //Cria o adaptador para a listagem
        clientAdapter = new ArrayAdapter<>(getContext(), R.layout.list_clients, R.id.profileClient,dataClients);
        listClient.setAdapter(clientAdapter);

        //Filtro de pesquisa
        inputSearchClient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListShowClients.this.clientAdapter.getFilter().filter(charSequence);
                Log.i("add_ListView", "beforeTextChanged: "+charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        return view;
    }


}
