package com.SQLsupport.strategies.selectablecars;

import com.SQLsupport.DBClass.Car;
import com.SQLsupport.strategies.SelectableCar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;
import static java.lang.Integer.parseInt;

public class SelectOneCar implements SelectableCar {
    String nameCar;

    @Override
    public void getData(String data) {
        nameCar = data;
    }

    @Override
    public Vector<Car> executeSelect(Connection conn){
        int count=1;
        ResultSet res=null;
        Vector<Car> cars =null;

        try{
            String sql1="SELECT " +
                    CAR_SCHEMA+"." +CAR_ID+", "+
                    CAR_SCHEMA+"." +CAR_NAME+", "+
                    CAR_SCHEMA+"." +CAR_TYPE+", "+
                    CAR_SCHEMA+"." +CAR_COST+", "+
                    CAR_SCHEMA+"." +CAR_COUNT+", "+
                    MANUFACTURER_SCHEMA+"." +MANUFACTURER_NAME+
                    " FROM "+DB_NAME+"."+CAR_SCHEMA+
                    " INNER JOIN "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                    " ON "+CAR_SCHEMA+"."+CAR_MANUFACTURER+"="+
                    MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+
                    " AND "+CAR_SCHEMA+"."+CAR_NAME+"=?"+
                    " ORDER BY "+CAR_SCHEMA+"."+CAR_ID;

            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setString(1, nameCar);
                res = prepStmt.executeQuery();
                cars = new Vector<>();
                while(res.next()){
                    count=1;
                    int id = res.getInt(count++);
                    String name = res.getString(count++);
                    String type= res.getString(count++);
                    int cost= res.getInt(count++);
                    int count1= res.getInt(count++);
                    String id_manufacturer= res.getString(count++);
                    cars.add(new Car(id,name,type,cost,count1,id_manufacturer));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return cars;
    }
}
