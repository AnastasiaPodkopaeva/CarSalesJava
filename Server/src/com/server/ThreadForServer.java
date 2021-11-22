package com.server;

import com.SQLsupport.DBClass.*;
import com.SQLsupport.strategies.SelectableCar;
import com.SQLsupport.strategies.*;
import com.SQLsupport.DBConnection;
import com.SQLsupport.strategies.Updatable;
import com.SQLsupport.strategies.selectable.*;
import com.SQLsupport.strategies.selectablecars.SelectAllCars;
import com.SQLsupport.strategies.selectablecars.SelectOneCar;
import com.SQLsupport.strategies.selectablecars.SelectCarsByManufacturer;
import com.SQLsupport.strategies.updatable.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class ThreadForServer implements Runnable{
    private Socket client;
    private ObjectInputStream input_stream;
    private ObjectOutputStream output_stream;
    private static int allClientCount = 0;
    private int currentClient;
    private DBConnection dbConnection;

    public ThreadForServer(ServerSocket serverSocket, DBConnection dbConnection){

        try {
            client=serverSocket.accept();
            allClientCount++;
            currentClient=allClientCount;
            System.out.println("client №" +currentClient+" connected");
            input_stream = new ObjectInputStream(client.getInputStream());
            output_stream = new ObjectOutputStream(client.getOutputStream());
            this.dbConnection=dbConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Release() throws IOException, NullPointerException {
        input_stream.close();
        output_stream.close();
        client.close();
    }

    private void closeThread(){
        try {
            System.out.println("client №" + currentClient + " disconnected");
            allClientCount--;
            this.Release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(true){
            try{
                String clientChoice=(String)input_stream.readObject();
                String dataFromClient=(String)input_stream.readObject();

                Updatable sqlUpdate=null;
                switch (clientChoice) {
                    case "registration" -> sqlUpdate = new AddUser();
                    case "add review" -> sqlUpdate=new AddReview();
                    case "add mark to manufacturer" -> sqlUpdate=new AddMark();
                    case "add to basket" -> sqlUpdate=new AddToBasket();
                    case "delete one purchase" -> sqlUpdate=new DeleteOnePurchase();
                    case "delete all purchases" -> sqlUpdate=new DeleteAllPurchases();
                    case "buy one car" -> sqlUpdate=new EditUserMoney();
                    case "buy all cars" -> sqlUpdate=new EditUserMoney();
                    case "edit user" -> sqlUpdate=new EditUser();
                    case "add money"->sqlUpdate=new EditUserMoney();
                    case "delete one rebate"->sqlUpdate=new DeleteOneRebate();
                    case "edit role user"->sqlUpdate=new EditRoleUser();
                    case "create car"->sqlUpdate=new CreateCar();
                    case "delete one car"->sqlUpdate=new DeleteOneCar();
                    case "edit car count"->sqlUpdate=new EditCarCount();
                    case "create manufacturer"->sqlUpdate=new AddManufacturer();
                    case "delete one manufacturer"->sqlUpdate=new DeleteOneManufacturer();
                    case "exit" -> {
                        closeThread();
                        return;
                    }
                }
                if(sqlUpdate!=null){
                    sqlUpdate.getData(dataFromClient);
                    boolean res = sqlUpdate.executeUpdate(dbConnection.getMyConnection());
                    output_stream.writeObject(res);
                    continue;
                }

                //all selects of cars
                SelectableCar sqlSelectCar=null;
                switch (clientChoice) {
                    case "select all cars" -> sqlSelectCar = new SelectAllCars();
                    case "select one car" -> sqlSelectCar = new SelectOneCar();
                    case "select by manufacturer" -> sqlSelectCar = new SelectCarsByManufacturer();
                }
                if(sqlSelectCar!=null){
                    sqlSelectCar.getData(dataFromClient);
                    Vector<Car> car = sqlSelectCar.executeSelect(dbConnection.getMyConnection());
                    output_stream.writeObject(car);
                    continue;
                }


                //check all select requests
                switch (clientChoice) {
                    case "signIn" -> {
                        var sqlSelect = new SelectUser();
                        sqlSelect.getData(dataFromClient);
                        Vector<User> user = sqlSelect.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(user);
                    }
                    case "select all manufacturer" -> {
                        var sqlSelect1 = new SelectAllManufacturers();
                        Vector<Manufacturer> manufacturers = sqlSelect1.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(manufacturers);
                    }
                    case "select all reviews" ->{
                        var sqlSelect2=new SelectReviewsForCar();
                        sqlSelect2.getData(dataFromClient);
                        Vector<Review> reviews = sqlSelect2.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(reviews);
                    }
                    case "select all purchases"->{
                        var sqlSelect3=new SelectAllPurchases();
                        sqlSelect3.getData(dataFromClient);
                        Vector<Purchase> purchases = sqlSelect3.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(purchases);
                    }
                    case "print basket"->{
                        var sqlSelect3=new PrintBasket();
                        sqlSelect3.getData(dataFromClient);
                        String filePath = sqlSelect3.execute(dbConnection.getMyConnection());
                        output_stream.writeObject(filePath);
                    }
                    case "select data for pie chart"->{
                        var sqlSelect3=new SelectDataForPieChart();
                        Vector<InformationForPieChart> informationForPieCharts = sqlSelect3.execute(dbConnection.getMyConnection());
                        output_stream.writeObject(informationForPieCharts);
                    }
                    case "select all faq"->{
                        var sqlSelect3=new SelectAllFAQ();
                        Vector<Faq> faqs = sqlSelect3.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(faqs);
                    }
                    case "select all rebates"->{
                        var sqlSelect3=new SelectAllRebates();
                        sqlSelect3.getData(dataFromClient);
                        Vector<Rebate> rebates = sqlSelect3.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(rebates);
                    }
                    case "select all users"->{
                        var sqlSelect3=new SelectAllUsers();
                        sqlSelect3.getData(dataFromClient);
                        Vector<User> users = sqlSelect3.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(users);
                    }
                }
            }
            catch ( SocketException e) {
                System.out.println("client №"+currentClient+" break connection");
                closeThread();
                return;
            }
            catch (IOException | ClassNotFoundException  e){
                e.printStackTrace();
            }

        }
    }
}
