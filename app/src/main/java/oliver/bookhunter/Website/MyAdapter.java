package oliver.bookhunter.Website;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import oliver.bookhunter.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ItemData> itemsData;
    @SuppressLint("StaticFieldLeak")
    public static Context context;



    MyAdapter(List<ItemData> itemsData, Context context) {
        this.itemsData = itemsData;
        MyAdapter.context = context;
    }

    // Create new views (invoked by the layout manager)


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.webistetextview, null);

        // create ViewHolder

        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImageUrl());


    }



    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTitle;
        ImageButton imgViewIcon;


        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);


            txtViewTitle = itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_icon);



        }
    }



    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
    private void delete(String delete){
        String file_name = "bookhunter_file";
        this.notifyDataSetChanged();

        try {
            String Message;
            final FileInputStream fileinput = context.openFileInput(file_name);
            InputStreamReader inputStreamReader = new InputStreamReader(fileinput);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            while (((Message = bufferedReader.readLine()) != null)) {
                stringBuffer.append(Message).append("\n");

            }
            final BufferedReader bufReader = new BufferedReader(new StringReader(stringBuffer.toString()));
            String line;
            FileOutputStream fileoutput = context.openFileOutput(file_name, Context.MODE_PRIVATE);
            while ((line = bufReader.readLine()) != null) {
                Log.d("text", line);
                Log.d("dele",delete);


                if (line.equals(delete)) {
                    Log.d("True", "true");
                } else {
                    line += '\n';
                    fileoutput.write(line.getBytes());
                }


                fileoutput.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }








    }

}
