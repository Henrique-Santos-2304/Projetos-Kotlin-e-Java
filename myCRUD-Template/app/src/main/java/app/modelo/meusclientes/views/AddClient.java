package app.modelo.meusclientes.views;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.R;
import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.controller.ClientController;
import app.modelo.meusclientes.model.Client;


public class AddClient extends Fragment {

    View view;
    List<EditText> listInput;

    TextView txtTitle;

    EditText inputName;
    EditText inputTel;
    EditText inputEmail;
    EditText inputCep;
    EditText inputPublicPlace;
    EditText inputNumber;
    EditText inputDistrict;
    EditText inputCity;
    EditText inputState;

    CheckBox checkTermsOfUse;
    Button btnCancel;
    Button btnSave;

    public AddClient() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_add_client, container, false);

        initComponentsLayouts();
        handleEventButtons();

        return view;
    }

    private void initComponentsLayouts() {
        txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.title_add_new_client);

         inputName = view.findViewById(R.id.inputName);
         inputTel = view.findViewById(R.id.inputTel);
         inputEmail = view.findViewById(R.id.inputEmail);
         inputCep = view.findViewById(R.id.inputCep);
         inputPublicPlace = view.findViewById(R.id.inputPublicPlace);
         inputNumber = view.findViewById(R.id.inputNumber);
         inputDistrict = view.findViewById(R.id.inputDistrict);
         inputCity = view.findViewById(R.id.inputCity);
         inputState = view.findViewById(R.id.inputState);

         checkTermsOfUse = view.findViewById(R.id.checkTermsOfUse);
         btnCancel = view.findViewById(R.id.btnCancel);
         btnSave = view.findViewById(R.id.btnSave);

         listInput = new ArrayList<>();

         listInput.add(inputName);
         listInput.add(inputTel);
         listInput.add(inputEmail);
         listInput.add(inputCep);
         listInput.add(inputPublicPlace);
         listInput.add(inputNumber);
         listInput.add(inputDistrict);
         listInput.add(inputCity);
         listInput.add(inputState);

    }

    private void handleEventButtons(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client = new Client();
                ClientController clientController = new ClientController(getContext());
                boolean isNotEmptyDataForm = true;

                // Checa se todos os inputs não estão vazios
                for (EditText input: listInput) {
                    if(TextUtils.isEmpty(input.getText())){
                        isNotEmptyDataForm= false;
                        input.setError("Campo Obrigatório");
                    }
                }
                if(!checkTermsOfUse.isChecked()){
                    isNotEmptyDataForm= false;
                    checkTermsOfUse.setError("Campo Obrigatório");
                }

                if(isNotEmptyDataForm) {
                    //Popula o objeto com os dados recebidos
                    client.setName(inputName.getText().toString());
                    client.setTelephone(inputTel.getText().toString());
                    client.setEmail(inputEmail.getText().toString());
                    client.setCep(Integer.parseInt(inputCep.getText().toString()));
                    client.setPublicPlace(inputPublicPlace.getText().toString());
                    client.setNumber(inputNumber.getText().toString());
                    client.setDistrict(inputDistrict.getText().toString());
                    client.setState(inputState.getText().toString());
                    client.setTermsOfUse(checkTermsOfUse.isChecked());

                    if (clientController.includeData(client)) {
                        Toast.makeText(getContext(), "Dados Salvos com sucesso", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Não foi possível salvar os dados", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    Log.i(AppUtil.TAG, "Existem campos vazios");
                }
            }
        });
    }

}
