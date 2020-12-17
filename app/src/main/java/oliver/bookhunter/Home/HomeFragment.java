 package oliver.bookhunter.Home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import oliver.bookhunter.Adapter.Adapter;
import oliver.bookhunter.Adapter.Item;
import oliver.bookhunter.Connect.Connect;
import oliver.bookhunter.Connect.ConnectAction;
import oliver.bookhunter.R;

public class HomeFragment extends Fragment {
    public static  List<String> allWebsites;
    static float p = 0;
    static String currentWebsite;

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button search = view.findViewById(R.id.Hunt);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewHome);
        List<Item> find = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        ProgressBar progressBarSearch = view.findViewById(R.id.progressBarSearch);

        ConnectAction runnable1 = (result, context) -> {

            List<String> allWebsites = new ArrayList<>();

            Iterator<String> keys;
            if (result != null) {
                keys = result.keys();
                while (keys.hasNext()) {
                    try {
                        allWebsites.add((String) result.get(keys.next()));
                    } catch (JSONException e) {
                        Toast.makeText(context, "Oops something went wrong", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
            progressBarSearch.setVisibility(View.INVISIBLE);
            search.setVisibility(View.VISIBLE);
            HomeFragment.allWebsites = allWebsites;
        };
        SharedPreferences userPreferences = Objects.requireNonNull(Objects.requireNonNull(getContext()).getSharedPreferences("credentials", android.content.Context.MODE_PRIVATE));

        ConnectAction runnable2 = (result2, context2) -> {
                Iterator<String> keys2;
                if (result2 != null) {
                    keys2 = result2.keys();

                    while (keys2.hasNext()) {
                        try {
                            String s =(String) result2.get(keys2.next());
                            Log.d("error",s);
                            find.add((new Item(s,currentWebsite)));
                        } catch (JSONException e) {
                            Toast.makeText(context2, "Oops something went wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                }
                    p++;
                    int progress = (int) ((p/allWebsites.size())*100);
                    progressBar.setProgress(progress);
                    recyclerView.setAdapter(new Adapter(find, view, false, getContext(), userPreferences, "Pass"));
                    if(p==allWebsites.size()){
                        search.setVisibility(View.VISIBLE);
                        progressBarSearch.setVisibility(View.INVISIBLE);
                    }
        };

        search.setOnClickListener(v -> {
            if (allWebsites!=null) {
                progressBarSearch.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                for(String s: allWebsites){
                    currentWebsite = s;
                    new Connect(getContext(), runnable2,"find",s.replace("\\","~")).execute(userPreferences.getString("user", ""), userPreferences.getString("password", ""));
                }
            }else{
                Toast.makeText(getContext(),"Wait until the loading process is finished",Toast.LENGTH_LONG).show();
            }
        });
        new Connect(getContext(), runnable1,"getAllWebsites","").execute(userPreferences.getString("user", ""), userPreferences.getString("password", ""));



        return view;
    }



}
