package edu.jferliccreighton.fragmentexampleapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import edu.jferliccreighton.fragmentexampleapp.R;

public class SecondFragment extends Fragment {

    private MainViewModel mViewModel;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        Button button = (Button) view.findViewById(R.id.button);
        final TextView text = (TextView) view.findViewById(R.id.message);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String finalText = "";
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry: allEntries.entrySet() ) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            finalText += entry.getKey() + " " + entry.getValue().toString() + "\n";
        }
        text.setText(finalText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}
