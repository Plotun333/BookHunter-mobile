package oliver.bookhunter.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.Objects;

import oliver.bookhunter.Connect.ConnectAction;
import oliver.bookhunter.MainActivity;
import oliver.bookhunter.R;


/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
public class SignInActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText user = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);
        SharedPreferences userPreferences = Objects.requireNonNull(this.getSharedPreferences("credentials", android.content.Context.MODE_PRIVATE));
        user.setText(userPreferences.getString("user", ""));
        password.setText(userPreferences.getString("password", ""));

        Button login = findViewById(R.id.btn_login);
        login.setVisibility(View.VISIBLE);
        login.setOnClickListener(v -> {
            login.setVisibility(View.INVISIBLE);
            SharedPreferences.Editor edit = userPreferences.edit();
            edit.putString("user", user.getText().toString());
            edit.putString("password", password.getText().toString());
            edit.apply();

            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            ConnectAction runnable = (result, context) -> {
                if(result == null){
                    oliver.bookhunter.Connect.Connect.Alert("Error", "Oops something went wrong. Check your internet connection", context,  android.R.drawable.ic_dialog_alert);
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }else {
                    try {
                        if (result.get("success").equals("true")) {
                            startActivity(new Intent(context, MainActivity.class));
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            findViewById(R.id.alertText).setVisibility(View.VISIBLE);
                            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                user.setBackgroundTintList(colorStateList);
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                password.setBackgroundTintList(colorStateList);
                            }
                            login.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        oliver.bookhunter.Connect.Connect.Alert("Error", "Oops something went wrong. Try again.", context,  android.R.drawable.ic_dialog_alert);
                        progressBar.setVisibility(View.INVISIBLE);
                        login.setVisibility(View.VISIBLE);
                    }
                }
            };

            new oliver.bookhunter.Connect.Connect(this, runnable,"login","").execute(user.getText().toString(), password.getText().toString());




        });

    }
}