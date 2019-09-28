package com.example.rohitranjan.vehiclepayment;

public class table {

    String tableId;
    String tableItemNumber;

    public table(String tableId, String tableItemNumber) {
        this.tableId = tableId;
        this.tableItemNumber = tableItemNumber;
    }

    public table() {
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableItemNumber() {
        return tableItemNumber;
    }
}
