package com.SQLsupport.strategies.selectable;

import com.SQLsupport.DBClass.InformationForPieChart;
import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;

public class SelectDataForPieChart implements Requestable {
    @Override
    public void getData(String data) {

    }

    public Vector<InformationForPieChart> execute(Connection conn){

        ResultSet res;
        Vector<InformationForPieChart> informationForPieCharts=new Vector<>();

        int max=getManufCount(conn);
        int currentNumber=1;
        String name="";
        int count=0;

        String sql1="SELECT "+MANUFACTURER_SCHEMA+"."+MANUFACTURER_NAME+","+
                " sum("+CAR_SCHEMA+"."+CAR_COUNT+") FROM "+DB_NAME+"."+CAR_SCHEMA+
                " INNER JOIN "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                " ON "+CAR_SCHEMA+"."+CAR_MANUFACTURER+"="+MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+
                " WHERE "+MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+"=?";

        while(currentNumber<max+1){
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setInt(1,currentNumber);
                res = prepStmt.executeQuery();
                if(res.next()){
                    name=res.getString(1);
                    count= res.getInt(2);
                    informationForPieCharts.add(new InformationForPieChart(name,count));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            currentNumber++;
        }

        return informationForPieCharts;
    }

    public int getManufCount(Connection conn){
        ResultSet res;
        String sql1="SELECT COUNT(*) FROM "+DB_NAME+"."+MANUFACTURER_SCHEMA;
        try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
            res = prepStmt.executeQuery();
            if(res.next()) return res.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
