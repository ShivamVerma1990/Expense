package com.candroid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.candroid.myapplication.Activity.AboutActivity;
import com.candroid.myapplication.Activity.HomeActivity;
import com.candroid.myapplication.Activity.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.View;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class DashActivity extends AppCompatActivity implements android.view.View.OnClickListener {
Toolbar toolbar;
    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private EditText editTextQty;

    private FirebaseFirestore db;

FirebaseAuth mAuth;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        sharedPreferences = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        setToolbar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView.setSelectedItemId(R.id.DashBoard);
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);
        editTextQty = findViewById(R.id.edittext_qty);

        findViewById(R.id.button_save).setOnClickListener(this);
bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.DashBoard:

                return true;

            case R.id.Home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.About:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                 overridePendingTransition(0, 0);
                return true;

        }

        return false;
    }
});
    }





    public  void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DASHBOARD");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(DashActivity.this, MainActivity.class);
                startActivity(intent);
                sharedPreferences.edit().clear().apply();
                finish();


                return super.onOptionsItemSelected(item);

        }

        return true;

    }

    private boolean validateInputs(String name, String brand, String desc, String price, String qty) {
        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            editTextBrand.setError("Brand required");
            editTextBrand.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editTextDesc.setError("Description required");
            editTextDesc.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            editTextPrice.setError("Price required");
            editTextPrice.requestFocus();
            return true;
        }

        if (qty.isEmpty()) {
            editTextQty.setError("Quantity required");
            editTextQty.requestFocus();
            return true;
        }
        return false;
    }



    @Override
    public void onClick(android.view.View v) {


            String name = editTextName.getText().toString().trim();
            String brand = editTextBrand.getText().toString().trim();
            String desc = editTextDesc.getText().toString().trim();
            String price = editTextPrice.getText().toString().trim();
            String qty = editTextQty.getText().toString().trim();

            if (!validateInputs(name, brand, desc, price, qty)) {

                CollectionReference dbProducts = db.collection("products");

                Product product = new Product(
                        name,
                        brand,
                        desc,
                        Double.parseDouble(price),
                        Integer.parseInt(qty)
                );

                dbProducts.add(product)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(DashActivity.this, "Product Added", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DashActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            }

    }
}



