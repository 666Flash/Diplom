package homebookkeeping.book;

import homebookkeeping.need.Need;

import javax.persistence.*;
import java.sql.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static homebookkeeping.JavaEeDiplomApplication.*;

@Entity
@Table(name = "bookkeeping")
public class Bookkeeping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // or @GeneratedValue
    @Column(name = "idbookkeeping")
    private Long id;

    @Column(name = "bookkeepingMoney")
    private int money;

    @Column(name = "bookkeepingMiner")
    private int miner;

    @Column(name = "bookkeepingWhen")
    private DateTimeException when;

    @OneToMany(mappedBy = "needApproverid", cascade = CascadeType.ALL)
    private List<Need> needApproverT = new ArrayList<>();

    public Bookkeeping(int moneys, int miners, DateTimeException whens) {
        this.money = moneys;
        this.miner = miners;
        this.when = whens;
    }

    public Bookkeeping() {
    }

    public void addBookkeeping(int ids, int moneys, int miners) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO bookkeeping ( idbookkeeping, bookkeeping_money, bookkeeping_miner, bookkeeping_when) VALUES(?, ?, ?, SYSDATE())");
        try {
            ps.setInt(1, ids);
            ps.setInt(2, moneys);
            ps.setInt(3, miners);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE

        } finally {
            ps.close();
        }
    }

    public Long getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public int getMiner() {
        return miner;
    }

    public DateTimeException getWhen() {
        return when;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setMiner(int miner) {
        this.miner = miner;
    }

    public void setWhen(DateTimeException when) {
        this.when = when;
    }


    public static int sumBudget() throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT SUM(bookkeeping_money) FROM bookkeeping");
        try {
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

    public static int sumContribution(String name) throws SQLException {
        String test = "";
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT SUM(bookkeeping_money) FROM bookkeeping, homeconsumers " +
                "where idhomeconsumers = bookkeeping_miner and homeconsumers_name = ?");
        try {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            try {
                while (rs.next()) {
//                    System.out.println(Integer.parseInt(rs.getString(1)));
                    test = rs.getString(1);
                    if (test != null)
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

    public static HashMap<Integer, HashMap> statisticsmoney() throws SQLException {
        HashMap<Integer,HashMap> info = new HashMap<>();

        PreparedStatement ps = conn.prepareStatement("SELECT idbookkeeping, bookkeeping_money, homeconsumers_name, bookkeeping_when " +
                "FROM bookkeeping, homeconsumers where bookkeeping_miner = idhomeconsumers");
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
                        infost.put("cost", rs.getString(2));
                        infost.put("depositor", rs.getString(3));
                        infost.put("whencreator", rs.getString(4));
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

    public static void deletesMoney(int iddeletes) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM bookkeeping where idbookkeeping = ?");
        try {
            ps.setInt(1, iddeletes);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }
}
