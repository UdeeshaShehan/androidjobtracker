package com.placetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.placetracker.domain.AuthTokenResponse;
import com.placetracker.domain.Login;
import com.placetracker.retrofit.ApiClient;
import com.placetracker.retrofit.ApiInterface;
import com.placetracker.utility.Session;
import com.placetracker.utility.SessionObject;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    ApiInterface apiInterface;

    EditText _mobileNumberText;
    EditText _passwordText ;
    Button _loginButton ;
    TextView _signupLink;
    MaterialBetterSpinner materialBetterSpinner ;

    String[] SPINNER_DATA = {"Sinhala","Engliah"};

    private Session session;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        session = new Session(getBaseContext());
        if (session.getmobileNumber() != null && !"".equals(session.getmobileNumber()))
            login(session.getmobileNumber(), session.getpassword());

        setContentView(R.layout.activity_login);
        Typeface font = Typeface.createFromAsset(getAssets(), "malithi.TTF");

        materialBetterSpinner = findViewById(R.id.material_spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        materialBetterSpinner.setAdapter(adapter);
        materialBetterSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                session.setLanguage(materialBetterSpinner.getText().toString());
                Log.d(TAG, materialBetterSpinner.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });

        _mobileNumberText = findViewById(R.id.input_mobileNumber);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        if (session.getLanguage().equals("Sinhala") ) {
            _signupLink.setTypeface(font);
            _loginButton.setTypeface(font);
            TextInputLayout mobile = findViewById(R.id.mobileNumber);
            mobile.setTypeface(font);
            mobile.setHint("oqrl;k wxlh");
            TextInputLayout password = findViewById(R.id.password);
            password.setTypeface(font);
            password.setHint("uqr moh");
            _loginButton.setText("we;+,qjkak");
            _signupLink.setText(".sKqulA fkdue;skus yokak");
        }

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        String email = _mobileNumberText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        login(email, password);
    }

    private void login(final String mobileNumber, final String password) {
        Login login = new Login(mobileNumber, password);

        Call<AuthTokenResponse> postCall = apiInterface.login(login);
        postCall.enqueue(new Callback<AuthTokenResponse>() {
            @Override
            public void onResponse(Call<AuthTokenResponse> call, final Response<AuthTokenResponse> response) {
                try {
                    Log.e(TAG, "onResponse: " + response.body());

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    final Handler handler = new android.os.Handler();
                    handler.postDelayed(
                            new Runnable() {
                                public void run() {
                                    try {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        SessionObject.getInstance().setToken("");
                                        //response.body().getToken());
                                        SessionObject.getInstance().setUsername(response.body().getUsername());
                                        //session.setemail(email);
                                        session.setmobileNumber(mobileNumber);
                                        session.setpassword(password);
                                        SessionObject.getInstance().setMobileNumber(mobileNumber);
                                        SessionObject.getInstance().setMentorMobileNumber(response.body().getMentorMobileNumber());
                                        SessionObject.getInstance().setOrganization(response.body().getOrganization());
                                        SessionObject.getInstance().setId(response.body().getId());
                                        SessionObject.getInstance().setRole(response.body().getRole());
                                        onLoginSuccess();
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            }, 10);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthTokenResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

                _loginButton.setEnabled(true);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(this,
                MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = _mobileNumberText.getText().toString();
        String password = _passwordText.getText().toString();

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileNumberText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileNumberText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
