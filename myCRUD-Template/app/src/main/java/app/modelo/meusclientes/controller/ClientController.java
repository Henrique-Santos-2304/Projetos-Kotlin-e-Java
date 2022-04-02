package app.modelo.meusclientes.controller;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.dataModel.ClientDataModel;
import app.modelo.meusclientes.dataSource.AppDataBase;
import app.modelo.meusclientes.model.Client;

public class ClientController extends AppDataBase implements ICrud<Client> {
    public ClientController(Context context) {
        super(context);
        Log.d(AppUtil.TAG, "ClienteController: Conectado");
    }

    @Override
    public boolean includeData(@NonNull Client obj) {
        ContentValues dataOfThis = new ContentValues();
        //Salvando os dados recebidos no objeto do Contexto
        dataOfThis.put(ClientDataModel.NAME, obj.getName());
        dataOfThis.put(ClientDataModel.TELEPHONE, obj.getTelephone());
        dataOfThis.put(ClientDataModel.EMAIl, obj.getEmail());
        dataOfThis.put(ClientDataModel.CEP, obj.getCep());
        dataOfThis.put(ClientDataModel.PUBLIC_PLACE, obj.getPublicPlace());
        dataOfThis.put(ClientDataModel.NUMBER, obj.getNumber());
        dataOfThis.put(ClientDataModel.DISTRICT, obj.getDistrict());
        dataOfThis.put(ClientDataModel.STATE, obj.getState());
        dataOfThis.put(ClientDataModel.TERMS_OF_USE, obj.isTermsOfUse());

        return insert(ClientDataModel.TABLE, dataOfThis);
    }

    @Override
    public boolean modifyData(@NonNull Client obj) {
        ContentValues dataOfThis = new ContentValues();

        dataOfThis.put(ClientDataModel.NAME, obj.getName());
        dataOfThis.put(ClientDataModel.TELEPHONE, obj.getTelephone());
        dataOfThis.put(ClientDataModel.EMAIl, obj.getEmail());
        dataOfThis.put(ClientDataModel.CEP, obj.getCep());
        dataOfThis.put(ClientDataModel.PUBLIC_PLACE, obj.getPublicPlace());
        dataOfThis.put(ClientDataModel.NUMBER, obj.getNumber());
        dataOfThis.put(ClientDataModel.DISTRICT, obj.getDistrict());
        dataOfThis.put(ClientDataModel.STATE, obj.getState());
        dataOfThis.put(ClientDataModel.TERMS_OF_USE, obj.isTermsOfUse());

        return insertDataBd(ClientDataModel.TABLE, dataOfThis);
    }

    @Override
    public boolean deleteData(int obj) {return  deleteDataBd(ClientDataModel.TABLE, obj);}

    @Override
    public List<Client> showDatas() {
        return getAllClientes(ClientDataModel.TABLE);
    }

    public List<String> catchAndFormatDataForList(){
        List<String> clientList = new ArrayList<>();
        List<Client> clients = showDatas();


        for (Client client: clients) {
            String userTxt = client.getId() + ":  "+client.getName();
            clientList.add(userTxt);
            Log.i(AppUtil.TAG, "Lista: "+ client.getName());
        }
        return clientList;
    }

}
