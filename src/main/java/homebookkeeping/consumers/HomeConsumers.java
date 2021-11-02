package homebookkeeping.consumers;


import homebookkeeping.need.Need;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import static homebookkeeping.JavaEeDiplomApplication.*;

@Entity
@Table(name = "homeconsumers")
public class HomeConsumers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // or @GeneratedValue
    @Column(name = "idhomeconsumers")
    private Long id;

    @Column(name = "homeconsumersName")
    private String name;

    @Column(name = "homeconsumersPasvord")
    private String pasvord;

    @Column(name = "homeconsumersAdmin")
    private int admin;

    @Column(name = "homeconsumersPriority")
    private int priority;

    @OneToMany(mappedBy = "needCreatorid", cascade = CascadeType.ALL)
    private List<Need> needCreatorK = new ArrayList<>();


    public HomeConsumers(Long ids, String names, String pasvords, int admins, int prioritys) {
        this.id = ids;
        this.name = names;
        this.pasvord = pasvords;
        this.admin = admins;
        this.priority = prioritys;
    }

    public HomeConsumers() {
    }

    public void addHomeConsumers(int ids, String names, String pasvords, int admins, int prioritys) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO homeconsumers ( idhomeconsumers, homeconsumers_name, homeconsumers_pasvord, homeconsumers_admin, homeconsumers_priority) " +
                        "VALUES(?, ?, ?, ?, ?)");
//        "INSERT INTO claint ( idclaint, claintName) VALUES((select MAX(id)+1 from claint), ?)");
        try {
            ps.setInt(1, ids);
            ps.setString(2, names);
            ps.setString(3, pasvords);
            ps.setInt(4, admins);
            ps.setInt(5, prioritys);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE

        } finally {
            ps.close();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasvord() {
        return pasvord;
    }

    public int getAdmin() {
        return admin;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasvord(String pasvord) {
        this.pasvord = pasvord;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public void changeresHomeConsumers(int ids, String pasvords, int admins, int prioritys) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE homeconsumers set homeconsumers_pasvord = ?, homeconsumers_admin = ?, homeconsumers_priority = ? " +
                        "where idhomeconsumers = ?");
//        "INSERT INTO claint ( idclaint, claintName) VALUES((select MAX(id)+1 from claint), ?)");
        try {
            ps.setString(1, pasvords);
            ps.setInt(2, admins);
            ps.setInt(3, prioritys);
            ps.setInt(4, ids);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE

        } finally {
            ps.close();
        }
    }


    public static int searchNamePas(String name, String pasp) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT homeconsumers_admin FROM homeconsumers " +
                "where homeconsumers_name = ? and homeconsumers_pasvord = ?");
        try {
            ps.setString(1, name);
            ps.setString(2, pasp);
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

    public static int idName(String name) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT idhomeconsumers FROM homeconsumers " +
                "where homeconsumers_name = ?");
        try {
            ps.setString(1, name);
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

    public static int admins(String name) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT homeconsumers_admin FROM homeconsumers " +
                "where homeconsumers_name = ?");
        try {
            ps.setString(1, name);
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

    public static HashMap<Integer, HashMap> statisticsresident() throws SQLException {
        HashMap<Integer,HashMap> info = new HashMap<>();

        PreparedStatement ps = conn.prepareStatement("SELECT idhomeconsumers, homeconsumers_name, homeconsumers_admin, homeconsumers_priority " +
                "FROM homeconsumers ");
        try {
//            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            int i=0;
            while (rs.next()){
                i++;
            }
//            System.out.println("iiii="+i);

            if (i > 0)
                try {
                    ResultSetMetaData md = rs.getMetaData();

                    for (int s = 1; s <= i; s++) {
                        HashMap<String,String> infost = new HashMap<>();
                        if (s == 1) {
                            rs.first();
                        } else if (s == i) {
                            rs.last();
                        } else {
                            rs.next();
                        }

                        infost.put("number", rs.getString(1));
                        infost.put("name", rs.getString(2));
                        infost.put("admin", rs.getString(3));
                        infost.put("priority", rs.getString(4));
                        info.put(s, infost);
                    }

//                    Set<Map.Entry<Integer, HashMap>> infos=info.entrySet();
//                    for(Map.Entry<Integer, HashMap> infose:infos){
//                        System.out.println(infose.getKey()+"\t"+infose.getValue());
//                    }
//                    System.out.println();
                } finally {
                    rs.close(); // rs can't be null according to the docs
                }
        } finally {
            ps.close();
        }
        return info;
    }

    public static HashMap<Integer, HashMap> statisticsresidentchan(int idres) throws SQLException {
        HashMap<Integer,HashMap> info = new HashMap<>();

        PreparedStatement ps = conn.prepareStatement("SELECT idhomeconsumers, homeconsumers_name, homeconsumers_admin, homeconsumers_priority " +
                "FROM homeconsumers where idhomeconsumers = ?");
        try {
            ps.setInt(1, idres);
            ResultSet rs = ps.executeQuery();
            int i=0;
            while (rs.next()){
                i++;
            }
//            System.out.println("iiii="+i);

            if (i > 0)
                try {
                    ResultSetMetaData md = rs.getMetaData();

                    for (int s = 1; s <= i; s++) {
                        HashMap<String,String> infost = new HashMap<>();
                        if (s == 1) {
                            rs.first();
                        } else if (s == i) {
                            rs.last();
                        } else {
                            rs.next();
                        }

                        infost.put("number", rs.getString(1));
                        infost.put("name", rs.getString(2));
                        infost.put("admin", rs.getString(3));
                        infost.put("priority", rs.getString(4));
                        info.put(s, infost);
                    }

//                    Set<Map.Entry<Integer, HashMap>> infos=info.entrySet();
//                    for(Map.Entry<Integer, HashMap> infose:infos){
//                        System.out.println(infose.getKey()+"\t"+infose.getValue());
//                    }
//                    System.out.println();
                } finally {
                    rs.close(); // rs can't be null according to the docs
                }
        } finally {
            ps.close();
        }
        return info;
    }

    public static void deletesResident(int iddeletes) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement(
                "DELETE FROM need where need_creator = ? or need_approver = ?");
        try {
            ps.setInt(1, iddeletes);
            ps.setInt(2, iddeletes);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(
                "DELETE FROM bookkeeping where bookkeeping_miner = ?");
        try {
            ps.setInt(1, iddeletes);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(
            "DELETE FROM homeconsumers where idhomeconsumers = ?");
        try {
            ps.setInt(1, iddeletes);
//            ps.setString(2, names);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }
}