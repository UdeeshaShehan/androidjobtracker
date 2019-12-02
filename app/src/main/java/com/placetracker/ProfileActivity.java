package com.placetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.placetracker.domain.SuccessResponse;
import com.placetracker.domain.User;
import com.placetracker.retrofit.ApiClient;
import com.placetracker.retrofit.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    ApiInterface apiInterface;

    EditText _nameText;
    EditText _addressText;
    EditText _emailText ;
    EditText _mobileText ;
    EditText _passwordText ;
    EditText _reEnterPasswordText ;
    Button _profile_update_Button;
    Button _profile_exit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);
        String id =  (String) getIntent().getExtras().get("id");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        _nameText = findViewById(R.id.input_name2);
        _addressText = findViewById(R.id.input_address2);
        _emailText = findViewById(R.id.input_email2);
        _mobileText = findViewById(R.id.input_mobile2);
        _passwordText = findViewById(R.id.input_password2);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword2);
        _profile_update_Button = findViewById(R.id.btn_profile_update);
        _profile_exit = findViewById(R.id.btn_exit);

        Call<User> getCall = apiInterface.getuserById(id);
        getCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                _nameText.setText(user.getFullname());
                _addressText.setText(user.getAddress());
                _emailText.setText(user.getEmail());
                _mobileText.setText(user.getMobileNumber());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to load", Toast.LENGTH_LONG).show();
            }
        });

        _profile_update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        _profile_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void updateUser() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _profile_update_Button.setEnabled(false);

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();


        resetUser(email, address, mobile, password, name);

    }

    public void resetUser(String email, String address, String mobileNumber, String password, String fullname) {

        User user = new User(email, address, mobileNumber, password, fullname);

        Call<SuccessResponse> postCall = apiInterface.updateprofile(user);
        postCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                Log.e(TAG, "onResponse: " + response.body() );

                final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Update Account...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                onUpdateSuccess();
                                // onSignupFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_LONG).show();

                _profile_update_Button.setEnabled(true);
            }
        });

    }


    public void onUpdateSuccess() {
        _profile_update_Button.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent i = new Intent(this,
                MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_LONG).show();

        _profile_update_Button.setEnabled(true);
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
