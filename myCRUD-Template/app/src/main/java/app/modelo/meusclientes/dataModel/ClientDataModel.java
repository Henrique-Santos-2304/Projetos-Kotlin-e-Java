package app.modelo.meusclientes.dataModel;

import android.util.Log;

import app.modelo.meusclientes.api.AppUtil;

public class ClientDataModel {
    public static final String TABLE = "client";

    // Chaves da Tabela
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TELEPHONE = "telephone";
    public static final String EMAIl = "email";
    public static final String CEP = "cep";
    public static final String PUBLIC_PLACE = "publicPlace";
    public static final String NUMBER = "number";
    public static final String DISTRICT = "district";
    public static final String STATE = "state";
    public static final String TERMS_OF_USE = "termsOfUse";


    //Query
    public static String queryCreateTable = "";

    public static String createTable(){
        Log.i(AppUtil.TAG, " Create Table ");

        queryCreateTable += "CREATE TABLE "+ TABLE +" (";
        queryCreateTable += ID+" integer primary key autoincrement, ";
        queryCreateTable += NAME+" text, ";
        queryCreateTable += TELEPHONE+" text, ";
        queryCreateTable += EMAIl+" text, ";
        queryCreateTable += CEP+" integer, ";
        queryCreateTable += PUBLIC_PLACE+" text, ";
        queryCreateTable += NUMBER+" text, ";
        queryCreateTable += DISTRICT+" text, ";
        queryCreateTable += STATE+" text, ";
        queryCreateTable += TERMS_OF_USE+" integer ";
        queryCreateTable += ")";

        Log.i(AppUtil.TAG, "query: "+ queryCreateTable);
        return queryCreateTable;
    }
}
