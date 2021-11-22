package com.SQLsupport.strategies.updatable;

import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class EditCarCount implements Updatable {
    private int id, newCount;
    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split( " ");
        id=Integer.parseInt(dataFromClient[0]);
        newCount =Integer.parseInt(dataFromClient[1]);
    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        try{
            String sql1="UPDATE "+DB_NAME+"."+CAR_SCHEMA+
                    " SET "+CAR_COUNT+" = "+CAR_COUNT+" +?"+
                    " WHERE "+CAR_ID+" =?";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setInt(count++,newCount);
                prepStmt.setInt(count++,id);
                return prepStmt.executeUpdate() > 0;
            }
        }catch(SQLException e){

        }
        return false;
    }
}
