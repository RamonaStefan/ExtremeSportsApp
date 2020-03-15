package app.dao;

import app.model.Localitate;
import app.model.Regiune;
import app.model.SportExtrem;
import app.model.Tara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class giDao<T> {
    private final Logger logger = LoggerFactory.getLogger(Dao.class);

    private final Connection connection;
    private static final Map<Class, String> TABLES = new HashMap<>();

    static {
        TABLES.put(Localitate.class, "localitate");
        TABLES.put(Regiune.class, "regiune");
        TABLES.put(Tara.class, "tara");
        TABLES.put(SportExtrem.class, "sport");
    }

    public Dao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Dao(Connection connection) {
        this.connection = connection;
    }

    public Map<String, Localitate> selectAllLoc() {
        final Map<String, Localitate> all = new HashMap<>();
        final String query = "SELECT * FROM " + TABLES.get(Localitate.class) + ", " + TABLES.get(Tara.class) + "," + TABLES.get(Regiune.class) + ","
                + TABLES.get(SportExtrem.class) + " WHERE tara.id = regiune.id_tara AND regiune.id = localitate.id_regiune AND sport.id_localitate = localitate.id";

        try (final Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String numeLoc = rs.getString(2);
                String numeTara = rs.getString(5);
                String numeReg = rs.getString(7);
                String numeSport = rs.getString(10);
                String perStart = rs.getString(11);
                String perFin = rs.getString(12);
                Double cost = rs.getDouble(13);
                String key = numeTara + numeReg + numeLoc;
                if (all.containsKey(key)) {
                    ArrayList sports = all.get(key).getSport();
                    sports.add(new SportExtrem(numeSport, perStart, perFin, cost));
                } else {
                    ArrayList<SportExtrem> sports = new ArrayList<>();
                    sports.add(new SportExtrem(numeSport, perStart, perFin, cost));
                    all.put(key, new Localitate(numeTara, numeReg, numeLoc, sports));
                }
            }
        } catch (SQLException e) {
            logger.error("Query {} failed because {}", query, e.getMessage());
        }
        return all;
    }

    public Map<String, String> selectAll(Class<T> clazz) {
        final Map<String, String> all = new HashMap<>();
        final String query = "SELECT * FROM " + TABLES.get(clazz);

        try (final Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                all.put(String.valueOf(rs.getInt(1)), rs.getString(2));
            }
        } catch (SQLException e) {
            logger.error("Query {} failed because {}", query, e.getMessage());
        }
        return all;
    }

    public void del(String loc, String reg, String tara) {
        final String queryGet = "SELECT localitate.id FROM " + TABLES.get(Localitate.class) + "," + TABLES.get(Tara.class) + "," + TABLES.get(Regiune.class) +
                " WHERE tara.id = regiune.id_tara AND regiune.id = localitate.id_regiune AND regiune.nume='" + reg + "' AND tara.nume='" + tara + "' AND localitate.nume='" + loc + "'";
        int id = -1;
        try (final Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(queryGet);
            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Query {} failed because {}", queryGet, e.getMessage());
        }

        final String queryDel = "DELETE FROM " + TABLES.get(Localitate.class) + " WHERE id=" + id;

        try (final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(queryDel);
        } catch (SQLException e) {
            logger.error("Query {} failed because {}", queryDel, e.getMessage());
        }
    }

    public int add(Class<T> clazz, String nume, int foreignKey) {
        final String queryAdd;
        final String queryKey;
        int id = -1;
        if (foreignKey == -1) {
            queryAdd = "INSERT INTO " + TABLES.get(clazz) + " (`nume`) " + " VALUES ('" + nume + "')";
            queryKey = "SELECT MAX(id) FROM " + TABLES.get(clazz);
            try (final Statement stmt = connection.createStatement()) {
                final ResultSet rs = stmt.executeQuery(queryKey);
                while (rs.next()) {
                    id = rs.getInt(1) + 1;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            queryKey = "SELECT MAX(id) FROM " + TABLES.get(clazz);
            try (final Statement stmt = connection.createStatement()) {
                final ResultSet rs = stmt.executeQuery(queryKey);
                while (rs.next()) {
                    id = rs.getInt(1) + 1;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            queryAdd = "INSERT INTO " + TABLES.get(clazz) + " VALUES (" + id + ",'" + nume + "', " + foreignKey + ")";
        }

        try (final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(queryAdd);
        } catch (SQLException e) {
            logger.error("Query failed because {}", e.getMessage());
        }
        return id;
    }

    public void addListSports(ArrayList<SportExtrem> sports, int foreignKey) {
        for (int i = 0; i < sports.size(); i++) {
            SportExtrem sportExtrem = sports.get(i);
            String queryKey = "SELECT MAX(id) FROM " + TABLES.get(SportExtrem.class);
            int id = -1;
            try (final Statement stmt = connection.createStatement()) {
                final ResultSet rs = stmt.executeQuery(queryKey);
                while (rs.next()) {
                    id = rs.getInt(1) + 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String queryAdd = "INSERT INTO " + TABLES.get(SportExtrem.class) + " VALUES (" + id + ",'" + sportExtrem.getNumeSport()
                    + "','" + sportExtrem.getStartDate() + "','" + sportExtrem.getEndDate() + "'," + sportExtrem.getCostMediuZi() + ","
                    + foreignKey + ")";
            try (final Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(queryAdd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void modify(Class<T> clazz, int index, String value, String col) {
        final Map<String, String> all = new HashMap<>();
        final String query = "UPDATE " + TABLES.get(clazz) + " SET " + col + "='" + value + "' WHERE id=" + index;

        try (final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Query {} failed because {}", query, e.getMessage());
        }
    }

    public void modifyListSports(ArrayList<SportExtrem> sports, ArrayList<SportExtrem> oldSports, int foreignKey) {
        for (int i = 0; i < sports.size(); i++) {
            SportExtrem sportExtrem = sports.get(i);
            String queryUpdate = "";
            String queryKey;
            int id = -1;
            if (i < oldSports.size()) {
                queryKey= "SELECT id FROM " + TABLES.get(SportExtrem.class) + " WHERE id_localitate=" + foreignKey + " AND nume='" + oldSports.get(i).getNumeSport() + "'";
                try (final Statement stmt = connection.createStatement()) {
                    final ResultSet rs = stmt.executeQuery(queryKey);
                    while (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                id = add((Class<T>) SportExtrem.class, "", -1);
            }

            queryUpdate = "UPDATE " + TABLES.get(SportExtrem.class) + " SET ";
            if (sportExtrem.getNumeSport() != null) {
                queryUpdate += "nume='" + sportExtrem.getNumeSport() + "'";
            }
            if (sportExtrem.getStartDate() != null) {
                queryUpdate += ", perioada_start='" + sportExtrem.getStartDate() + "'";
            }
            if (sportExtrem.getEndDate() != null) {
                queryUpdate += ", perioada_stop='" + sportExtrem.getEndDate() + "'";
            }
            if (sportExtrem.getCostMediuZi() != 0) {
                queryUpdate += ", cost_zi=" + sportExtrem.getCostMediuZi();
            }
            queryUpdate += " WHERE id=" + id;


            try (final Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(queryUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
