package com.example.rohitranjan.vehiclepayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectTable extends AppCompatActivity{
    Button button,addTable;
    Spinner spinnerTable;
    ListView listViewTable;
    tableListAdapter adapter;
    DatabaseReference databaseReference;
    public static final String TABLE_DATABASE_PATH = "table";
    List<table> tableListNum;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_table);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listViewTable = findViewById(R.id.listViewImage);
        databaseReference = FirebaseDatabase.getInstance().getReference("table");

        button = findViewById(R.id.orderNow);
        addTable = findViewById(R.id.tableNum);
        spinnerTable = findViewById(R.id.spinner);

        tableListNum = new ArrayList<>();

        //show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading image....");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                tableListNum.clear();
                //fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //Imageupload class require default constructor
                    table img = snapshot.getValue(table.class);
                    tableListNum.add(img);
                }

                //Init adapter
                adapter = new tableListAdapter(SelectTable.this, R.layout.list_table, tableListNum);
                //set adapter for list view
                listViewTable.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTableNumber();
            }
        });

        listViewTable.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                table tableNmb = tableListNum.get(i);

                showUpdateDialog(tableNmb.getTableId());
                return false;
            }
        });
    }
    private void showUpdateDialog(final String itemId){
        Toast.makeText(this, itemId, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final  View dialogView = inflater.inflate(R.layout.custom_popup, null);

        dialogBuilder.setView(dialogView);

        final Button ButtonDelete = dialogView.findViewById(R.id.txtDelete);
        final Button ButtonClose = dialogView.findViewById(R.id.txtClose);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(itemId);
                alertDialog.dismiss();
            }
        });
        ButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void deleteUser(String itemId){
        DatabaseReference drItem = FirebaseDatabase.getInstance().getReference(TABLE_DATABASE_PATH).child(itemId);
        drItem.removeValue();

        Toast.makeText(this, "Cart item is deleted", Toast.LENGTH_SHORT).show();
    }


    private void addTableNumber(){
        String tableNum = spinnerTable.getSelectedItem().toString();

        String id = databaseReference.push().getKey();

        table tab = new table(id,tableNum);
        databaseReference.child(id).setValue(tab);
        Toast.makeText(this, "Table Added", Toast.LENGTH_SHORT).show();
    }


    public void tableToOrder(View view) {
        

        Toast.makeText(this, "Your order card is created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        startActivity(new Intent(this,CartPage.class));
        return true;
    }
}
