package com.SQLsupport.strategies;

import com.SQLsupport.strategies.Requestable;

import java.sql.Connection;

public interface Updatable extends Requestable {
    boolean executeUpdate(Connection conn);
}
