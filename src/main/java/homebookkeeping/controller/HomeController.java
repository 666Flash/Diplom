package homebookkeeping.controller;

import homebookkeeping.book.Bookkeeping;
import homebookkeeping.consumers.HomeConsumers;
import homebookkeeping.need.Need;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;

import static homebookkeeping.JavaEeDiplomApplication.*;
import static homebookkeeping.book.Bookkeeping.*;
import static homebookkeeping.consumers.HomeConsumers.*;
import static homebookkeeping.need.Need.*;

@RestController
public class HomeController {
    private String login = "";
    private int changeResident = 0;

    @PostMapping("entrance")
    public String entrance(String name, String pasp, HttpServletRequest reg) {
        System.out.println();
//        System.out.println("name="+ name);
//        System.out.println("pasp="+ pasp);

        int rezults = 0;
        try {
            rezults = searchNamePas(name, pasp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String rezult = "";
        if (rezults == 1) {
            rezult = getBaseUrl(reg) + "admin.html";
            login = name;
        } else {
            if (rezults == 2) {
                rezult = getBaseUrl(reg) + "user.html";
                login = name;
            } else {
                rezult = getBaseUrl(reg);
            }
        }
//        System.out.println("rezults="+ rezults);
//        System.out.println("rezult="+ rezult);
        return rezult;
    }

    private String getBaseUrl(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" +
                req.getServerPort() + "/";
    }


    @PostMapping("budget")
    public String budget(HttpServletRequest reg) {
        int rezultBudget = 0;
        try {
            rezultBudget = sumBudget() - sumApprover();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("rezultBudget="+rezultBudget);
         return String.valueOf(rezultBudget);
    }

    @PostMapping("contribution")
    public String contribution(HttpServletRequest reg) {
//        System.out.println();
//        System.out.println("login="+login);
        int rezultContribution = 0;
        try {
            rezultContribution = sumContribution(login) - sumApproverLogs(idName(login));
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("rezultContribution="+rezultContribution);
        return String.valueOf(rezultContribution);
    }

    @GetMapping("tovcomplid")
    public HashMap<Integer, HashMap> tovcomplid() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statistics(1, login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("tovnew")
    public HashMap<Integer, HashMap> tovnew() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statistics(0, login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("tovcomplidadmin")
    public HashMap<Integer, HashMap> tovcomplidadmin() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statisticsadmin(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("tovnewadmin")
    public HashMap<Integer, HashMap> tovnewadmin() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statisticsadmin(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("changes")
    public void changes(int idapprove) {
        try {
            if (admins(login) == 1)
                try {
                    updateNeed(idapprove, idName(login));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deletin")
    public void deletin(int iddeletes) {
//        System.out.println("iddeletes="+iddeletes);
        try {
            if (admins(login) == 1)
                try {
                    deletesNeed(iddeletes);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("residentadmin")
    public HashMap<Integer, HashMap> residentadmin() {
        changeResident = 0;
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statisticsresident();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("deletinresident")
    public void deletinresident(int iddeletes) {
//        System.out.println("iddeletes="+iddeletes);
        try {
            if (admins(login) == 1 && idName(login) != iddeletes)
                try {
                    deletesResident(iddeletes);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addresident")
    public void addresident(String residentname, String residentpas, int residentrights, int residentprior) {
//        System.out.println("residentname="+residentname);
//        System.out.println("residentpas="+residentpas);
//        System.out.println("residentrights="+residentrights);
//        System.out.println("residentprior="+residentprior);
        HomeConsumers homeconsumerss = new HomeConsumers();

        try {
            if (admins(login) == 1 && idName(residentname) == 0)
                try {
                    homeconsumerss.addHomeConsumers(maxId("homeconsumers"), residentname, residentpas, residentrights, residentprior);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("changeresident")
    public String changeresident(int changeid, HttpServletRequest reg) {
//        System.out.println();
//        System.out.println("changeid="+ changeid);

        int rezults = 0;
        try {
            rezults = searchId("homeconsumers", changeid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String rezult = getBaseUrl(reg);
        try {
            if (rezults != 0 && idName(login) != rezults && admins(login) == 1) {
                rezult = getBaseUrl(reg) + "changeresident.html";
                changeResident = rezults;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("rezults="+ rezults);
//        System.out.println("rezult="+ rezult);
        return rezult;
    }

    @GetMapping("residentchan")
    public HashMap<Integer, HashMap> residentchan() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statisticsresidentchan(changeResident);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("chanresident")
    public void chanresident(String residentpas, int residentrights, int residentprior) {
//        System.out.println("residentpas="+residentpas);
//        System.out.println("residentrights="+residentrights);
//        System.out.println("residentprior="+residentprior);
        HomeConsumers homeconsumerss = new HomeConsumers();

        try {
            if (admins(login) == 1)
                try {
                    homeconsumerss.changeresHomeConsumers(changeResident, residentpas, residentrights, residentprior);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("deletinprod")
    public void deletinprod(int iddeletes) {
//        System.out.println("iddeletes="+iddeletes);
        try {
            deletesProd(iddeletes, idName(login));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addproduct")
    public void addproduct(String productname, int productcost, String productcoment, String productinitiator) {
//        System.out.println("productname="+productname);
//        System.out.println("productcost="+productcost);
//        System.out.println("productcoment="+productcoment);
//        System.out.println("productinitiator="+productinitiator);

        Need zak = new Need();
        try {
            if (idNeedName(productname) == 0 && admins(login) == 1)
                try {
                    zak.addNeed(maxId("need"), productname, productcost, productcoment, idName(productinitiator), 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addproductuser")
    public void addproductuser(String productname, int productcost, String productcoment) {
//        System.out.println("productname="+productname);
//        System.out.println("productcost="+productcost);
//        System.out.println("productcoment="+productcoment);
//        System.out.println("productinitiator="+productinitiator);

        Need zak = new Need();
        try {
            if (idNeedName(productname) == 0)
                try {
                    zak.addNeed(maxId("need"), productname, productcost, productcoment, idName(login), 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("moneycomplid")
    public HashMap<Integer, HashMap> moneycomplid() {
        try {
//            System.out.println("statistics="+statistics(0, login));
            return statisticsmoney();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("deletinmoney")
    public void deletinmoney(int iddeletes) {
//        System.out.println("iddeletes="+iddeletes);
        try {
            if (admins(login) == 1)
                try {
                    deletesMoney(iddeletes);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("addmoney")
    public void addmoney(int moneycost, String moneyinitiator) {
//        System.out.println("moneycost="+moneycost);
//        System.out.println("moneyinitiator="+moneyinitiator);

        Bookkeeping bookkeepings = new Bookkeeping();
        try {
            if (idName(moneyinitiator) != 0 && admins(login) == 1)
                try {
                    bookkeepings.addBookkeeping(maxId("bookkeeping"), moneycost, idName(moneyinitiator));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("output")
    public String output(HttpServletRequest reg) {
        login = "";
        String rezult = getBaseUrl(reg);
        return rezult;
    }
}
