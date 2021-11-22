package com.SQLsupport.strategies;

import com.SQLsupport.DBClass.Car;

import java.sql.Connection;
import java.util.Vector;

public interface SelectableCar extends Requestable {
    Vector<Car> executeSelect(Connection conn);
}
