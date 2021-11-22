package com.SQLsupport.strategies.updatable;

import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class AddManufacturer implements Updatable {
    String country, name, email;
    int rating, number;


    @Override
    public void getData(String data) {
        int i=0;
        String[] dataFromClient=data.split("@@@");
        name=dataFromClient[i++];
        country=dataFromClient[i++];
        email=dataFromClient[i++];
        rating=Integer.parseInt(dataFromClient[i++]);
        number=Integer.parseInt(dataFromClient[i++]);

    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        try{
            String sql1="INSERT INTO "+DB_NAME+"."+MANUFACTURER_SCHEMA+" ("+
                    MANUFACTURER_NAME+","+
                    MANUFACTURER_COUNTRY+","+
                    MANUFACTURER_EMAIL+","+
                    MANUFACTURER_RATING+","+
                    MANUFACTURER_NUMBER+")"+
                    " VALUE (?,?,?,?,?);";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setString(count++, name);
                prepStmt.setString(count++, country);
                prepStmt.setString(count++, email);
                prepStmt.setInt(count++, rating);
                prepStmt.setInt(count++, number);
                return prepStmt.executeUpdate() > 0;
            }
        }catch(SQLException e){

        }
        return false;
    }
}
