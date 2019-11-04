package com.example.placetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placetracker.domain.SuccessResponse;
import com.example.placetracker.domain.User;
import com.example.placetracker.retrofit.ApiClient;
import com.example.placetracker.retrofit.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    ApiInterface apiInterface;

    EditText _nameText;
    EditText _addressText;
    EditText _emailText ;
    EditText _mobileText ;
    EditText _passwordText ;
    EditText _reEnterPasswordText ;
    Button _signupButton ;
    TextView _loginLink ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        _nameText = findViewById(R.id.input_name);
        _addressText = findViewById(R.id.input_address);
        _emailText = findViewById(R.id.input_email);
        _mobileText = findViewById(R.id.input_mobile);
        _passwordText = findViewById(R.id.input_password);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        signuprest(email, address, mobile, password, name);

    }

    public void signuprest(String email, String address, String mobileNumber, String password, String fullname) {

        User user = new User(email, address, mobileNumber, password, fullname);

        Call<SuccessResponse> postCall = apiInterface.signup(user);
        postCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                Log.e(TAG, "onResponse: " + response.body() );

                final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                onSignupSuccess();
                                // onSignupFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

                _signupButton.setEnabled(true);
            }
        });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent i = new Intent(this,
                LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}