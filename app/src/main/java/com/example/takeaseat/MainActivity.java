package com.example.takeaseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.takeaseat.ui.MainActivityViewModel;
import com.example.takeaseat.ui.SignInActivity;



public class MainActivity extends AppCompatActivity {
    private EditText messageEditText;
    private TextView messageTextView;
    private TextView welcomeMessage;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.init();
        checkIfSignedIn();
        setContentView(R.layout.activity_main);
        messageEditText = findViewById(R.id.message_editText);
        messageTextView = findViewById(R.id.message_textView);
        welcomeMessage = findViewById(R.id.welcome_message);

        viewModel.getMessage().observe(this, message -> {
            if (message != null)
                messageTextView.setText(message.getBody());
        });
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                String message = "Welcome " + user.getDisplayName();
                welcomeMessage.setText(message);
            } else
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    public void saveData(View v) {
        viewModel.saveMessage(messageEditText.getText().toString());
    }

    public void signOut(View v) {
        viewModel.signOut();
    }
}