package app.service;

import app.dao.Dao;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Service<T> {
    private final Dao<T> dao;
    public final HashMap<String,Integer> months = new HashMap<String, Integer>() {
        {
            put("ianuarie", 1);
            put("februarie", 2);
            put("martie", 3);
            put("aprilie", 4);
            put("mai", 5);
            put("iunie", 6);
            put("iulie", 7);
            put("august", 8);
            put("septembrie", 9);
            put("octombrie", 10);
            put("noiembrie", 11);
            put("decembrie", 12);
        }
    };
    public final HashMap<String,Integer> days = new HashMap<String, Integer>() {
        {
            put("ianuarie", 31);
            put("februarie", 28);
            put("martie", 31);
            put("aprilie", 30);
            put("mai", 31);
            put("iunie", 30);
            put("iulie", 31);
            put("august", 31);
            put("septembrie", 30);
            put("octombrie", 31);
            put("noiembrie", 30);
            put("decembrie", 31);
        }
    };

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

    public Integer[] getMonths(String lunaStart, String lunaEnd) {
        int start = months.get(lunaStart);
        int end = months.get(lunaEnd);
        int noMonths =  (start > end? 12 - start + end: end - start) + 1;
        Integer[] month = new Integer[noMonths];
        for (int i = 0 ; i < noMonths; i++) {
            int m = (i + months.get(lunaStart)) % 12;
            if (m == 0){
                month[i] = 12;
            }
            else {
                month[i] = (i + months.get(lunaStart)) % 12;
            }
        }
        return month;
    }


}

