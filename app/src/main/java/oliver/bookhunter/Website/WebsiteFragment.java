package oliver.bookhunter.Website;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import oliver.bookhunter.R;

public class WebsiteFragment extends Fragment {
    private View mDemoView;
    private TextView mDemoTextView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDemoView = inflater.inflate(R.layout.fragment_website, container, false);


        return mDemoView;
    }
}
