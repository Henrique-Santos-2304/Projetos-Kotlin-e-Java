package app.modelo.meusclientes.dataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.modelo.meusclientes.api.AppUtil;
import app.modelo.meusclientes.dataModel.ClientDataModel;
import app.modelo.meusclientes.model.Client;

public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "APP Meu Crud";
    public static final int DB_VERSION = 1;
    SQLiteDatabase db;

    public AppDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        Log.d(AppUtil.TAG, "AppDataBase: Criando Banco de Dados");
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        //Criando Banco de dados
        db.execSQL(ClientDataModel.createTable());
        Log.i(AppUtil.TAG,"Dados Cliente:  " + ClientDataModel.createTable() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Método paraincluir dados no banco de dados
    public boolean insert(String table, ContentValues dataObject) {
        db = getWritableDatabase();
        boolean returnState= false;

        try{
            returnState =  db.insert(table,null,dataObject) > 0;
        }catch(Exception err){
            Log.e(AppUtil.TAG, "Error ao salvar dados: "+ err);
        }

        return returnState;
    }

    //Método para deletar  dados no banco de dados
    public boolean deleteDataBd(String table, int id) {
        db = getWritableDatabase();
        boolean returnState= false;

        try{
            final String[]  idFormatted = new String[]{String.valueOf(id)};

            returnState =  db.delete(table,"id = ?",idFormatted ) > 0;

        }catch(Exception err){
            Log.e(AppUtil.TAG, "Error ao salvar dados: " + err);
        }

        return returnState;
    }

    //Método para atualizar  dados no banco de dados
    public boolean insertDataBd(String table, ContentValues dataObject) {
        db = getWritableDatabase();
        boolean returnState= false;

        try{
            final String[]  idFormatted = new String[]{String.valueOf(dataObject.get("id"))};
            returnState =  db.update(table,dataObject,"id = ?",idFormatted ) > 0;

        }catch(Exception err){
            Log.e(AppUtil.TAG, "Error ao salvar dados: " + err);
        }

        return returnState;
    }

    public List<Client> getAllClientes(String tabela) {

        db = getWritableDatabase();

        List<Client> clientes = new ArrayList<>();
        String sql = "SELECT * FROM " + tabela;

        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    Client newCliente = new Client();

                    newCliente.setId(cursor.getInt(
                            cursor.getColumnIndexOrThrow(ClientDataModel.ID)));
                    newCliente.setName(cursor.getString(cursor.getColumnIndexOrThrow(ClientDataModel.NAME)));
                    newCliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ClientDataModel.EMAIl)));

                    clientes.add(newCliente);
                }
                cursor.close();
            }
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro ao listar os dados " + err);
        }
        return clientes;

    }
}
