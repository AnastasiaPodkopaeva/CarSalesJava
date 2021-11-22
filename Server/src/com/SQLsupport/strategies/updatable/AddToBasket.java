package com.SQLsupport.strategies.updatable;

import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class AddToBasket implements Updatable {

    private int id_user, id_product;

    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split(" ");
        id_user=Integer.parseInt(dataFromClient[0]);
        id_product=Integer.parseInt(dataFromClient[1]);
    }

    @Override
    public boolean executeUpdate(Connection conn) {
        int count=1;
        try{
            String sql1="INSERT INTO "+DB_NAME+"."+PURCHASE_SCHEMA+" ("+
                    PURCHASE_USER+","+ PURCHASE_CAR+")"+" VALUE (?,?)";
            try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                prepStmt.setInt(count++, id_user);
                prepStmt.setInt(count++, id_product);
                return prepStmt.executeUpdate() > 0;
            }
        }catch(SQLException e){

        }
        return false;
    }
}
