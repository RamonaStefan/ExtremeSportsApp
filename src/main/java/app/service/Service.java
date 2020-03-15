package app.service;

import app.dao.Dao;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class Service<T> {
    private final Dao<T> dao;

    public Service(Dao<T> genericDao, ObjectMapper objectMapper) {
        this.dao = genericDao;
    }

    public Map<String, T> getAllInfo() throws IOException {
        Map<String, T> map = (Map<String, T>) dao.selectAllLoc();
        return map;
    }

    public Map<String, T> getAll(Class<T> clazz) throws IOException {
        Map<String, T> map = (Map<String, T>) dao.selectAll(clazz);
        return map;
    }

    public int add (Class<T> clazz, String nume, int foreignKey) {
        int index = dao.add(clazz, nume, foreignKey);
        return index;
    }

    public void addList(ArrayList sports, int foreignKey) {
        dao.addListSports(sports, foreignKey);
    }

    public void modify(Class<T> clazz, int index, String value, String col) {
        dao.modify(clazz, index, value, col);
    }

    public void modifyList(ArrayList sports, ArrayList oldSports, int foreignKey) {
        dao.modifyListSports(sports, oldSports, foreignKey);
    }

    public void del(String loc, String reg, String tara) {
        dao.del(loc, reg, tara);
    }

    public int getKey(Map<String, String> map, String nume){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(nume)) {
                return Integer.parseInt(entry.getKey());
            }
        }
        return -1;
    }


}

