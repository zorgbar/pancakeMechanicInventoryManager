    package com.example.pancakemechanic.views;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.pancakemechanic.Admin.AdminAddProduct;
    import com.example.pancakemechanic.DatabaseHelper;
    import com.example.pancakemechanic.MainActivity;
    import com.example.pancakemechanic.R;
    import com.example.pancakemechanic.RegisterActivity;
    import com.example.pancakemechanic.Chef.ChefActivity;

    public class LoginActivity extends AppCompatActivity {

        EditText mTextUsername;
        EditText mTextPassword;
        Button mButtonLogin;
        TextView mTextViewRegister;
        DatabaseHelper db;
        Spinner mrole;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            db = new DatabaseHelper(this);
            mTextUsername = (EditText) findViewById(R.id.edittext_username);
            mTextPassword = (EditText) findViewById(R.id.edittext_password);
            mButtonLogin = (Button) findViewById(R.id.button_login);
            mTextViewRegister = (TextView) findViewById(R.id.textview_register);
            mrole = (Spinner) findViewById(R.id.spinner_role2);

            mTextViewRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            });

            mButtonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = mTextUsername.getText().toString().trim();
                    String password = mTextPassword.getText().toString().trim();
                    String role = mrole.getSelectedItem().toString().trim();

                    Boolean res = db.checkUser(user, password, role);
                    if (res) {
                        if (role.equals("Admin")) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent moveToAdd = new Intent(LoginActivity.this, AdminAddProduct.class);
                            startActivity(moveToAdd);
                        } else if (role.equals("Teller")) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent moveToAdd = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(moveToAdd);
                        } else if (role.equals("Chef")) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent moveToChef = new Intent(LoginActivity.this, ChefActivity.class);
                            startActivity(moveToChef);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Role", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }