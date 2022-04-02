package app.modelo.meusclientes.controller;

import java.util.List;

public interface ICrud<T> {
    // Incluir
    public boolean includeData(T obj);

    // Altera
    public boolean modifyData(T obj);

    // Deletar
    public boolean deleteData(int id);

    // Listar
    public List<T> showDatas();
}
