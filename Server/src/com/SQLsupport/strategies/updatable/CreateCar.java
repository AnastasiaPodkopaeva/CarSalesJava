package com.SQLsupport.strategies.updatable;

import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class CreateCar implements Updatable {
    private String name, manuf, type;
    private int cost, number;

    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split("@@@");
        int count=0;
        name=dataFromClient[count++];
        type=dataFromClient[count++];
        cost=Integer.parseInt(dataFromClient[count++]);
        number=Integer.parseInt(dataFromClient[count++]);
        manuf=dataFromClient[count++];
    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        try{
            String sql1="INSERT INTO "+DB_NAME+"."+CAR_SCHEMA+" ("+
                    CAR_NAME+","+
                    CAR_TYPE+","+
                    CAR_COST+","+
                    CAR_COUNT+","+
                    CAR_MANUFACTURER+")"+
                    " VALUE (?,?,?,?,"+
                    "(SELECT "+MANUFACTURER_SCHEMA+"."+MANUFACTURER_ID+
                    " FROM "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                    " WHERE "+MANUFACTURER_SCHEMA+"."+MANUFACTURER_NAME+"=?"+" ))";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setString(count++, name);
                prepStmt.setString(count++, type);
                prepStmt.setInt(count++, cost);
                prepStmt.setInt(count++, number);
                prepStmt.setString(count++, manuf);
                return prepStmt.executeUpdate() > 0;
            }
        }catch(SQLException e){

        }
        return false;
    }
}
