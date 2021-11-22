package com.SQLsupport.strategies.updatable;

import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class AddMark implements Updatable {
    private int mark;
    private String nameOfManufacturer;
    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split("@@@");
        mark=Integer.parseInt(dataFromClient[1]);
        nameOfManufacturer=dataFromClient[0];
    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        ResultSet res=null;
        Manufacturer manufacturer=null;

        try{
            String sql1="SELECT * FROM "+DB_NAME+"."+MANUFACTURER_SCHEMA
                    +" WHERE "+MANUFACTURER_NAME +" = ?";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setString(1,nameOfManufacturer);
                res = prepStmt.executeQuery();
                if(res.next()){
                    int id = res.getInt(count++);
                    String name = res.getString(count++);
                    String country= res.getString(count++);
                    String email= res.getString(count++);
                    double rating= res.getDouble(count++);
                    int number_of_ratings= res.getInt(count++);
                    manufacturer= new Manufacturer(id,name,country,email,rating,number_of_ratings);
                }
            }

            if(manufacturer!=null){
                double rating=manufacturer.getRating();
                int numberOfMarks= manufacturer.getNumber_of_ratings();
                double allMarksSum=rating*numberOfMarks;
                allMarksSum+=mark;
                numberOfMarks++;
                rating=allMarksSum/numberOfMarks;
                rating = Math.round(rating*100)/100.0;
                sql1="UPDATE "+DB_NAME+"."+MANUFACTURER_SCHEMA+
                        " SET "+MANUFACTURER_RATING+"=?,"+MANUFACTURER_NUMBER+"=?"+
                        " WHERE "+MANUFACTURER_ID+"=?;";
                try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                    prepStmt.setDouble(1,rating);
                    prepStmt.setInt(2,numberOfMarks);
                    prepStmt.setInt(3,manufacturer.getId_manufacturer());
                    return prepStmt.executeUpdate()>0;
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
