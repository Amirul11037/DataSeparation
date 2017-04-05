package com.example.amiru.dataseperation.FreeWordAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amiru.dataseperation.Constant.PlaceDetailsConstant;
import com.example.amiru.dataseperation.R;

import java.util.ArrayList;

/**
 * Created by amiru on 15/03/2017.
 */

public class RecycleViewAdapter_Restaurant_Search_Results
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PlaceDetailsConstant> restaurant_List = null;
    private Context context;

    public RecycleViewAdapter_Restaurant_Search_Results
            (Context context, ArrayList<PlaceDetailsConstant> restaurant_List){
        this.restaurant_List = restaurant_List;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.free_word_search_cardview, null);
        RecycleViewHolder_Restaurant_Search_Results holder =
                new RecycleViewHolder_Restaurant_Search_Results(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecycleViewHolder_Restaurant_Search_Results myHolder =
                (RecycleViewHolder_Restaurant_Search_Results) holder;

        PlaceDetailsConstant current=restaurant_List.get(position);

        myHolder.restaurant_name.setText(current.place_name);
        myHolder.restaurant_location.setText(current.place_address);

        Log.d("Get Data Name", current.place_name);
        Log.d("Get Data Address", current.place_address);
        Log.d("Position", String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return this.restaurant_List.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class RecycleViewHolder_Restaurant_Search_Results extends RecyclerView.ViewHolder {

        public TextView restaurant_name,restaurant_location,restaurant_distance;
        public ImageView restaurant_photo, restaurant_icon;


        public RecycleViewHolder_Restaurant_Search_Results(View itemView) {
            super(itemView);

            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            restaurant_location = (TextView) itemView.findViewById(R.id.restaurant_location);
            restaurant_photo = (ImageView) itemView.findViewById(R.id.restaurant_photo);

        }
    }

}
