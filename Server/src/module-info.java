module Server {
    requires java.sql;
    requires mysql.connector.java;
    exports com.SQLsupport;
    exports com.SQLsupport.strategies;
    exports com.SQLsupport.DBClass;
    exports com.SQLsupport.strategies.updatable;
    exports com.SQLsupport.strategies.selectablecars;
    exports com.SQLsupport.strategies.selectable;
}