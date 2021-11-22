package com.SQLsupport.strategies.selectable;

import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static com.SQLsupport.Constants.*;

public class SelectAllManufacturers implements Requestable {

    @Override
    public void getData(String data) {

    }

    public Vector<Manufacturer> executeSelect(Connection conn){
        int count=1;
        ResultSet res=null;
        Vector<Manufacturer> manufacturers=null;

        try{
            String sql1="SELECT * FROM "+DB_NAME+"."+MANUFACTURER_SCHEMA;
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                res = prepStmt.executeQuery();
                manufacturers = new Vector<>();
                while(res.next()){
                    count=1;
                    int id = res.getInt(count++);
                    String name = res.getString(count++);
                    String country= res.getString(count++);
                    String email= res.getString(count++);
                    double rating= res.getDouble(count++);
                    int number_of_ratings= res.getInt(count++);
                    manufacturers.add(new Manufacturer(id,name,country,email,rating,number_of_ratings)) ;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return manufacturers;
    }
}
