package edu.jferliccreighton.fragmentexampleapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

import edu.jferliccreighton.fragmentexampleapp.ui.main.MainFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainFragment.ClickHandle {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        Button clearButton = this.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearButton:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Delete Users")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.clear();
                                editor.commit();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                dialog.show();
                break;
        }

    }

    @Override
    public void handleClickSeeList() {
        CharSequence text = "Input has been submitted!";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(this, text, duration).show();
    }

    @Override
    public void handleLogin() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private Boolean isValid(String user, String pass) {
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry: allEntries.entrySet() ) {
            if (user.equalsIgnoreCase(entry.getKey()) && pass.equalsIgnoreCase(entry.getValue().toString())) {
                return true;
            }
        }
        return false;
    }
}
