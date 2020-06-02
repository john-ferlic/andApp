package edu.jferliccreighton.fragmentexampleapp.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import edu.jferliccreighton.fragmentexampleapp.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    private ClickHandle mCallback;
    private EditText username;
    private EditText password;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;



    public interface ClickHandle {
        public void handleClickSeeList();
        public void handleLogin();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        Button SwitchFragmentButton = (Button) view.findViewById(R.id.button);
        Button ChangeTextButton = (Button) view.findViewById(R.id.ChangeTextButton);
        Button LoginButton = (Button) view.findViewById(R.id.login);
        LoginButton.setOnClickListener(this);
        SwitchFragmentButton.setOnClickListener(this);
        ChangeTextButton.setOnClickListener(this);
        username = (EditText) view.findViewById(R.id.editTextUsername);
        password = (EditText) view.findViewById(R.id.editTextPassword);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        return view;
    }

    public String getUsername(){
        return username.getText().toString();
    }

    public String getPassword(){
        return password.getText().toString();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ClickHandle) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ClickEventHandler");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
                        .replace(R.id.container, SecondFragment.newInstance())
                        .commitNow();
                break;
            case R.id.ChangeTextButton:
                if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Enter username and password";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    break;
                } else {
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    editor.putString(user, pass);
                    editor.commit();
                    mCallback.handleClickSeeList();
                    break;
                }
            case R.id.login:
                boolean loggedIn = false;
                Map<String, ?> allEntries = sharedPref.getAll();
                for (Map.Entry<String, ?> entry: allEntries.entrySet() ) {
                    if (getUsername().equals(entry.getKey()) && getPassword().equals(entry.getValue().toString())) {
                        loggedIn = true;
                        break;
                    }
                }
                if (loggedIn){
                    mCallback.handleLogin();
                    Log.d("YEs", "Logged in");
                }
                else {
                    Log.d("NOPe", "Not correct");
                }

        }
    }

}



