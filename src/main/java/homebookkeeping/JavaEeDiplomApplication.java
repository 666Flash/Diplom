package homebookkeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mysql.cj.jdbc.ConnectionImpl;

import homebookkeeping.consumers.*;
import homebookkeeping.book.*;
import homebookkeeping.need.*;

import java.sql.*;


@SpringBootApplication
public class JavaEeDiplomApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaEeDiplomApplication.class, args);
        try {
            addDans("ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/homebookkeeping?serverTimezone=Europe/Kiev";
    static final String DB_USER = "Flash";
    static final String DB_PASSWORD = "123456789987654321";
    public static ConnectionImpl conn;


    public static void addDans(String tt) throws SQLException {

        conn = (ConnectionImpl) DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

        deleteHomeBookkeeping("homeconsumers");
        deleteHomeBookkeeping("bookkeeping");
        deleteHomeBookkeeping("need");

        HomeConsumers homeconsumerss = new HomeConsumers();
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Faze", "13579", 1, 1);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Maze", "24680", 1, 1);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Dote", "123", 2, 1);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Son", "321", 2, 1);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Animal", "0", 2, 2);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Haus", "0", 2, 2);
        homeconsumerss.addHomeConsumers(maxId("homeconsumers"), "Car", "0", 2, 2);


        Bookkeeping bookkeepings = new Bookkeeping();
        bookkeepings.addBookkeeping(maxId("bookkeeping"), 10000, 1);
        bookkeepings.addBookkeeping(maxId("bookkeeping"), 16000, 1);
        bookkeepings.addBookkeeping(maxId("bookkeeping"), 13000, 2);
        bookkeepings.addBookkeeping(maxId("bookkeeping"), 3000, 4);
        bookkeepings.addBookkeeping(maxId("bookkeeping"), 6500, 7);


        Need zak = new Need();
        zak.addNeed(maxId("need"), "Жалюзи", 1200, "Для дома", 1, 0);
        zak.addNeed(maxId("need"), "Дрель", 2500, "Для работ по дому", 4, 0);
        zak.addNeed(maxId("need"), "Сковородку", 5000, "Для приготовления стейков", 2, 0);
        zak.addNeed(maxId("need"), "Флобер", 7500, "Для самообороны", 4, 0);

        System.out.println();
        viewHomeBookkeeping("homeconsumers");
        System.out.println();
        viewHomeBookkeeping("bookkeeping");
        System.out.println();
        viewHomeBookkeeping("need");
        System.out.println();
        System.out.println();

    }

    private static void deleteHomeBookkeeping(String nametbl) throws SQLException {
        System.out.println("deleteHomeBookkeeping");

        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM " + nametbl);
        try {
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public static int searchId(String table, int ids) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table + " where id" + table + " = ?");
        try {
            ps.setInt(1, ids);
            ResultSet rs = ps.executeQuery();

            try {
                while (rs.next()) {
//                    System.out.println(Integer.parseInt(rs.getString(1)));
                    idv = Integer.parseInt(rs.getString(1));
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
        return idv;
    }

    private static int searchName(String table, String names) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table + " where " + table + "Name = ?");
        try {
            ps.setString(1, names);
            ResultSet rs = ps.executeQuery();

            try {
                while (rs.next()) {
//                    System.out.println(Integer.parseInt(rs.getString(1)));
                    idv = Integer.parseInt(rs.getString(1));
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
        return idv;
    }

    public static int maxId(String table) throws SQLException {
        String idt = "";
        int idv = 1;
        PreparedStatement ps = conn.prepareStatement("SELECT Max(id" + table + ")+1 FROM " + table);
        try {
            ResultSet rs = ps.executeQuery();

            try {
                while (rs.next()) {
//                    System.out.println(Integer.parseInt(rs.getString(1)));
//                    idv = Integer.parseInt(rs.getString(1));
                    idt = rs.getString(1);
                    if (idt == null) {
                        idv = 1;
                    } else {
                        idv = Integer.parseInt(idt);
                    }
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
        return idv;
    }

    private static void viewHomeBookkeeping(String nametbl) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nametbl);
        try {
            // table of data representing a database result set,
            ResultSet rs = ps.executeQuery();

            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md = rs.getMetaData();

//                for (int i = 1; i <= md.getColumnCount(); i++)
//                    System.out.print(md.getColumnName(i) + "\t\t");
//                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        if (i < md.getColumnCount()) {
                            System.out.print(rs.getString(i) + ": ");
                        } else {
                            System.out.print(rs.getString(i));
                        }
//                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
    }

    private static void viewHomeBookkeepingNeed(int idNeed) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT homeconsumersName, idneed, needName, needComent, needPrice  FROM need, homeconsumers " +
                "where idhomeconsumers = ? and needCreator = idhomeconsumers");
        try {
            // table of data representing a database result set,
            ps.setInt(1, idNeed);
            ResultSet rs = ps.executeQuery();

            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md = rs.getMetaData();

//                for (int i = 1; i <= md.getColumnCount(); i++)
//                    System.out.print(md.getColumnName(i) + "\t\t");
//                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        if (i < md.getColumnCount()) {
                            System.out.print(rs.getString(i) + ": ");
                        } else {
                            System.out.print(rs.getString(i) + " грн");
                        }
//                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
    }
}
