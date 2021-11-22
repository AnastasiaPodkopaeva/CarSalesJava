package com.SQLsupport.strategies;

import com.SQLsupport.DBClass.Purchase;
import com.SQLsupport.strategies.selectable.SelectAllPurchases;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

public class PrintBasket implements Requestable {

    SelectAllPurchases selectAllPurchases=null;
    private int user_id;
    public PrintBasket(){
        selectAllPurchases=new SelectAllPurchases();
    }

    @Override
    public void getData(String data) {
        selectAllPurchases.getData(data);
        user_id=selectAllPurchases.getId();
    }

    public String execute(Connection conn){
        String path="file-"+user_id+".txt";
        Vector<Purchase> purchases = selectAllPurchases.executeSelect(conn);
        try(FileWriter writer = new FileWriter(path, false))
        {
            for (var purchase:purchases)
                purchase.print(writer);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return path;
    }
}
