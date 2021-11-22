package com.SQLsupport.strategies.updatable;

import com.SQLsupport.strategies.Updatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.SQLsupport.Constants.*;

public class EditUserMoney implements Updatable {

    private int id_user, new_money, user_money;

    @Override
    public void getData(String data) {
        String[] dataFromClient=data.split(" ");
        id_user=Integer.parseInt(dataFromClient[0]);
        user_money=Integer.parseInt(dataFromClient[1]);
        new_money =Integer.parseInt(dataFromClient[2]);
    }

    @Override
    public boolean executeUpdate(Connection conn) {

        //if we try buy product we'll get new money with minus
        //if we try add money we'll get new money with plus
        if(user_money< -1*new_money)
            return false;

        String  sql1="UPDATE "+DB_NAME+"."+USER_SCHEMA+
                " SET "+USER_MONEY+"="+USER_MONEY+"+?"+
                " WHERE "+USER_ID+"=?;";
        try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
            prepStmt.setInt(1, new_money);
            prepStmt.setInt(2,id_user);
            return prepStmt.executeUpdate()>0;
        }
        catch (SQLException e){

        }
        return false;
    }
}
