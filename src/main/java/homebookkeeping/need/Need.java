package homebookkeeping.need;

import homebookkeeping.book.Bookkeeping;
import homebookkeeping.consumers.HomeConsumers;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.*;

import static homebookkeeping.JavaEeDiplomApplication.*;

@Entity
@Table(name = "need")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // or @GeneratedValue
    @Column(name = "idneed")
    private Long id;

    @Column(name = "needName")
    private String name;

    @Column(name = "needPrice")
    private int price;

    @Column(name = "needComent")
    private String coment;

    @ManyToOne
    @JoinColumn(name = "needCreator")
    private HomeConsumers needCreatorid;

    @Column(name = "needWhenCreator")
    private DateTimeException whenCreator;

    @Column(name = "needPriority")
    private int priority;

    @ManyToOne
    @JoinColumn(name = "needApprover")
    private Bookkeeping needApproverid;

    @Column(name = "needWhenApprover")
    private DateTimeException whenApprover;


    public void addNeed(int idneeds, String needNames, int needPrices, String needComents, int needCreators, int needPrioritys) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO need ( idneed, need_name, need_price, need_coment, need_creator, need_when_creator, need_priority) " +
              "VALUES(?, ?, ?, ?, ?, SYSDATE(), ?)");
        try {
            ps.setInt(1, idneeds);
            ps.setString(2, needNames);
            ps.setInt(3, needPrices);
            ps.setString(4, needComents);
            ps.setInt(5, needCreators);
            ps.setInt(6, needPrioritys);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE

        } finally {
            ps.close();
        }
    }

    public Need() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getComent() {
        return coment;
    }

    public HomeConsumers getNeedCreatorid() {
        return needCreatorid;
    }

    public DateTimeException getWhenCreator() {
        return whenCreator;
    }

    public int getPriority() {
        return priority;
    }

    public Bookkeeping getNeedApproverid() {
        return needApproverid;
    }

    public DateTimeException getWhenApprover() {
        return whenApprover;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public void setNeedCreatorid(HomeConsumers needCreatorid) {
        this.needCreatorid = needCreatorid;
    }

    public void setWhenCreator(DateTimeException whenCreator) {
        this.whenCreator = whenCreator;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setNeedApproverid(Bookkeeping needApproverid) {
        this.needApproverid = needApproverid;
    }

    public void setWhenApprover(DateTimeException whenApprover) {
        this.whenApprover = whenApprover;
    }


    public static int sumApprover() throws SQLException {
        String test = "";
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT SUM(need_price) FROM need where need_approver != NULL or need_approver > 0");
        try {
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
//        System.out.println("sumApprover()="+idv);
        return idv;
    }

    public static int sumApproverLogs(int loggs) throws SQLException {
        String test = "";
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT SUM(need_price) FROM need " +
                "where (need_approver != NULL or need_approver > 0) and need_creator = ?");
        try {
            ps.setInt(1, loggs);
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
//        System.out.println("sumApprover()="+idv);
        return idv;
    }

    public static HashMap<Integer,HashMap> statistics(int approver, String name) throws SQLException {
        HashMap<Integer,HashMap> info = new HashMap<>();

        PreparedStatement ps;
//        System.out.println("approver="+approver);

        if (approver == 0) {
            ps = conn.prepareStatement("SELECT idneed, need_name, need_coment, need_price FROM need, homeconsumers " +
                    "where homeconsumers_name = ? and idhomeconsumers = need_creator and need_approver is NULL");
        } else {
            ps = conn.prepareStatement("SELECT idneed, need_name, need_coment, need_price FROM need, homeconsumers " +
                    "where homeconsumers_name = ? and idhomeconsumers = need_creator and (need_approver != NULL or need_approver > 0)");
        }
        try {
            ps.setString(1, name);
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
                        infost.put("product", rs.getString(3));
                        infost.put("cost", rs.getString(4));
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

    public static HashMap<Integer,HashMap> statisticsadmin(int approver) throws SQLException {
        HashMap<Integer,HashMap> info = new HashMap<>();

        PreparedStatement ps;
        if (approver == 0) {
            ps = conn.prepareStatement("SELECT idneed, need_name, need_coment, need_price, " +
                    " homeconsumers_name as creators, need_when_creator, '' as approvers, need_when_approver " +
                    " FROM need, homeconsumers " +
                    " where idhomeconsumers = need_creator and need_approver is NULL");
        } else {
            ps = conn.prepareStatement("SELECT idneed, need_name, need_coment, need_price, " +
                    "   creator.homeconsumers_name as creators, need_when_creator, approver.homeconsumers_name as approvers, need_when_approver " +
                    " FROM need, homeconsumers as creator, homeconsumers as approver " +
                    " where creator.idhomeconsumers = need_creator and approver.idhomeconsumers = need_approver and (need_approver != NULL or need_approver > 0)");
        }
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
                        infost.put("product", rs.getString(3));
                        infost.put("cost", rs.getString(4));
                        infost.put("consumers", rs.getString(5));
                        infost.put("whencreator", rs.getString(6));
                        infost.put("approver", rs.getString(7));
                        infost.put("whenapprover", rs.getString(8));
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

    public static void updateNeed(int idapprove, int idnames) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE need set need_approver = ?, need_when_approver = SYSDATE() where idneed = ?");
        try {
            ps.setInt(1, idnames);
            ps.setInt(2, idapprove);
//            ps.setString(2, names);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }


    public static void deletesNeed(int iddeletes) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM need where idneed = ? and need_approver is NULL ");
        try {
            ps.setInt(1, iddeletes);
//            ps.setString(2, names);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public static void deletesProd(int iddeletes, int idnames) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM need where idneed = ? and need_creator = ? and need_approver is NULL ");
        try {
            ps.setInt(1, iddeletes);
            ps.setInt(2, idnames);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public static int idNeedName(String name) throws SQLException {
        int idv = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT idneed FROM need " +
                "where need_name = ?");
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
}
