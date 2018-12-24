package com.example.finalproject.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mRegister;
    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mToolbar;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        initUI();
    }

    private void initUI() {

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_button);
        actionbar.setTitle("Sign in");

        mRegister = findViewById(R.id.login_register_btn);
        mLogin = findViewById(R.id.login_login_btn);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);

        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.login_login_btn):
                validateAndSignIn(mEmail.getText().toString(),
                        mPassword.getText().toString());
                break;
            case (R.id.login_register_btn):
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
        }
    }

    private void validateAndSignIn(String email, String password) {
        if (isValid(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: " + task.getResult());
                                Toast.makeText(LoginActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            Log.d(TAG, "onComplete: " + task.getException());
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }
}
