package dao;

import connection.DBConnection;
import entity.KQXS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class LoadDataDAO {
    Connection connection;
    DBConnection dbConnection = new DBConnection();
    PreparedStatement ps;
    ResultSet rs;

    public List<KQXS> mergeRows(List<KQXS> kqxsList) {
        List<KQXS> mergedList = new ArrayList<>();

        // Iterate through the input list
        for (KQXS hang : kqxsList) {
            boolean merged = false;

            // Check if the mergedList already contains an entry with the same award
            for (KQXS mergedHang : mergedList) {
                if (mergedHang.getAward().equals(hang.getAward())) {
                    // If yes, merge the numbers and update the merged entry
                    String mergedNumbers = mergedHang.getNumber() + " - " + hang.getNumber();
                    mergedHang.setNumber(mergedNumbers);
                    merged = true;
                    break;
                }
            }


            // If no existing entry with the same award, add a new entry to the mergedList
            if (!merged) {
                mergedList.add(new KQXS(hang));
            }
        }

        return mergedList;
    }

    public List<KQXS> mergeAndSetNumber(List<KQXS> inputList) {
        List<KQXS> resultList = new ArrayList<>();

        // Iterate through the input list using a for loop
        for (KQXS currentHang : inputList) {
            boolean merged = false;

            // Iterate through the result list to check for existing entries to merge
            for (KQXS mergedHang : resultList) {
                if (mergedHang.getProvince().equals(currentHang.getProvince()) &&
                        mergedHang.getAward().equals(currentHang.getAward())) {
                    // Merge setnumber
                    String existingNumber = mergedHang.getNumber();
                    String newNumber = existingNumber + " " + currentHang.getNumber();
                    mergedHang.setNumber(newNumber);
                    merged = true;
                    break;
                }
            }

            // If not merged, add a new entry to the result list
            if (!merged) {
                resultList.add(new KQXS(currentHang));
            }
        }

        return resultList;
    }

    public List<String> getProvinces(List<KQXS> kqxsList) {
        Set<String> uniqueProvinces = new HashSet<>();
        for (KQXS kqxs : kqxsList) {
            uniqueProvinces.add(kqxs.getProvince());
        }
        return new ArrayList<>(uniqueProvinces);
    }

    public List<List<KQXS>> groupByAward(List<KQXS> kqxsList) {
        // Sử dụng Map để nhóm các KQXS theo giải thưởng
        Map<String, List<KQXS>> awardMap = new HashMap<>();

        // Tách các KQXS vào các danh sách tương ứng với giải thưởng
        for (KQXS kqxs : kqxsList) {
            String award = kqxs.getAward();
            List<KQXS> awardList = awardMap.getOrDefault(award, new ArrayList<>());
            awardList.add(kqxs);
            awardMap.put(award, awardList);
        }

        // Chuyển Map thành List<List<KQXS>>
        List<List<KQXS>> result = new ArrayList<>(awardMap.values());

        return result;
    }

    public List<List<KQXS>> splitByProvince(List<KQXS> kqxsList) {
        // Sử dụng Map để phân loại theo tỉnh
        Map<String, List<KQXS>> provinceMap = new HashMap<>();

        // Duyệt qua danh sách KQXS và phân loại theo tỉnh
        for (KQXS kqxs : kqxsList) {
            String province = kqxs.getProvince();

            // Nếu tỉnh chưa có trong Map, tạo danh sách mới cho tỉnh đó
            provinceMap.putIfAbsent(province, new ArrayList<>());

            // Thêm KQXS vào danh sách của tỉnh tương ứng
            provinceMap.get(province).add(kqxs);
        }

        // Chuyển Map thành List<List<KQXS>>
        List<List<KQXS>> result = new ArrayList<>(provinceMap.values());

        return result;
    }

    public List<KQXS> loadDataNewestMB() {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM ketquaxoso_mart kq WHERE " +
                "kq.date = ( SELECT MAX( kq1.date ) FROM ketquaxoso_mart kq1 WHERE kq1.tenMien = 'Miền Bắc' ) " +
                "AND kq.tenMien = 'Miền Bắc';";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }

            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<KQXS> loadDataNewestMBByDate(String date) {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM `ketquaxoso_mart` WHERE ketquaxoso_mart.date = ? " +
                "AND ketquaxoso_mart.tenMien = 'Miền Bắc'";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }

            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<KQXS> loadDataNewestMT() {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM ketquaxoso_mart kq WHERE " +
                "kq.date = ( SELECT MAX( kq1.date ) FROM ketquaxoso_mart kq1 WHERE kq1.tenMien = 'Miền Trung' ) " +
                "AND kq.tenMien = 'Miền Trung';";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }
            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<KQXS> loadDataNewestMTByDate(String date) {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM `ketquaxoso_mart` WHERE ketquaxoso_mart.date = ? " +
                "AND ketquaxoso_mart.tenMien = 'Miền Trung'";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }

            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<KQXS> loadDataNewestMN() {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM ketquaxoso_mart kq WHERE " +
                "kq.date = ( SELECT MAX( kq1.date ) FROM ketquaxoso_mart kq1 WHERE kq1.tenMien = 'Miền Trung' ) " +
                "AND kq.tenMien = 'Miền Nam';";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }
            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<KQXS> loadDataNewestMNByDate(String date) {
        List<KQXS> result = new ArrayList<>();
        String query = "SELECT * FROM `ketquaxoso_mart` WHERE ketquaxoso_mart.date = ? " +
                "AND ketquaxoso_mart.tenMien = 'Miền Nam'";
        try {
            connection = dbConnection.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                KQXS kq = new KQXS();
                kq.setDate(rs.getString(2));
                kq.setRegion(rs.getString(3));
                kq.setProvince(rs.getString(4));
                kq.setAward(rs.getString(5));
                kq.setNumber(rs.getString(6));
                result.add(kq);
            }

            rs.close();
            ps.close();
            dbConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {

        List<KQXS> listMB = new LoadDataDAO().loadDataNewestMB();
        List<KQXS> listMT = new LoadDataDAO().loadDataNewestMT();
        List<KQXS> listMTMerged = new LoadDataDAO().mergeAndSetNumber(listMT);
        List<List<KQXS>> listGrouped = new LoadDataDAO().groupByAward(listMTMerged);
        List<List<KQXS>> listGroupedByPro = new LoadDataDAO().splitByProvince(listMTMerged);

//        System.out.println(listMTMerged);
//        System.out.println(listGroupedByPro);
//        System.out.println(listGrouped);
        System.out.println();

        System.out.println(new LoadDataDAO().loadDataNewestMBByDate("2023-12-15").isEmpty());

//        System.out.println(new LoadDataDAO().getProvinces(new LoadDataDAO().mergeAndSetNumber(listMT)));

    }

}
