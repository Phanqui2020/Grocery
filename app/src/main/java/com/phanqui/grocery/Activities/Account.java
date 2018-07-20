package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

public class Account extends AppCompatActivity {

    TextView edtNameAccount, edtAdrAccount, edtEmailAccount;
    ImageView imgAccount;
    TextView txtdoimk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        addControls();
        addEvents();
    }

    private void addEvents() {
        txtdoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
    }


    private void addControls() {
        edtAdrAccount = findViewById(R.id.edtAdrAccount);
        edtAdrAccount.setText(SharedPrefManager.getInstance(Account.this).getAddress());
        edtEmailAccount = findViewById(R.id.edtEmailAccount);
        edtEmailAccount.setText(SharedPrefManager.getInstance(Account.this).getEmail());
        edtNameAccount = findViewById(R.id.edtNameAccount);
        edtNameAccount.setText(SharedPrefManager.getInstance(Account.this).getUsername());
        txtdoimk = findViewById(R.id.txtdoimk);

    }
}
