package com.SQLsupport.strategies.selectable;

import com.SQLsupport.DBClass.Rebate;
import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;

public class SelectAllRebates implements Requestable {
    private int id_user;
    @Override
    public void getData(String data) {
        id_user=Integer.parseInt(data);
    }

    public Vector<Rebate> executeSelect(Connection conn) {
        int count = 1;
        ResultSet res = null;
        Vector<Rebate> rebates = null;
        String sql1="";

        try {
            sql1 = "SELECT " +
                    REBATE_SCHEMA + "." + REBATE_ID + ", " +
                    MANUFACTURER_SCHEMA + "." + MANUFACTURER_NAME + ", " +
                    REBATE_SCHEMA + "." + REBATE_PERCENT +
                    " FROM " + DB_NAME + "." + REBATE_SCHEMA +
                    " INNER JOIN " + DB_NAME + "." + USER_SCHEMA +
                    " ON " + REBATE_SCHEMA + "." + REBATE_USER+ "=" + USER_SCHEMA + "." + USER_ID +
                    " INNER JOIN " + DB_NAME + "." + MANUFACTURER_SCHEMA +
                    " ON " + REBATE_SCHEMA + "." + REBATE_MANUFACTURER + "=" + MANUFACTURER_SCHEMA + "." + MANUFACTURER_ID +
                    " WHERE " + REBATE_SCHEMA + "." + REBATE_USER + "=? ORDER BY " + REBATE_ID;
            try (PreparedStatement prepStmt = conn.prepareStatement(sql1)) {
                prepStmt.setInt(1, id_user);
                res = prepStmt.executeQuery();
                rebates = new Vector<>();
                while (res.next()) {
                    count = 1;
                    int id_rebate = res.getInt(count++);
                    String manufName = res.getString(count++);
                    int percent = res.getInt(count++);
                    rebates.add(new Rebate(id_rebate, percent, manufName));
                }
            }
        } catch (SQLException e) {
            System.out.println(sql1);
            e.printStackTrace();
        }
        return rebates;
    }
}
