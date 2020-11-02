package cegepst.example.worldnews.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cegepst.example.worldnews.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void startSession(View view) {
        EditText emailInput = findViewById(R.id.emailInput);
        String email = emailInput.getText().toString();
        if (email == null || email.equals("")) {
            Toast.makeText(this, "Veuillez entrer votre adresse electronique avant de continuer", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(email, "email");
        startActivity(intent);
    }
}