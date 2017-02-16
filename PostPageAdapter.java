package com.baimingze369gmail.uw_sublet.mListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.R;
import com.baimingze369gmail.uw_sublet.mPicasso.PicassoClient;

import java.util.ArrayList;

/**
 * Created by Robert on 2016-06-21.
 */
public class PostPageAdapter extends BaseAdapter {
    Context c;
    ArrayList<Room> rooms;
    LayoutInflater inflater;

    public PostPageAdapter(Context c, ArrayList<Room> rooms) {
        this.c = c;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        //guess this part connect to model file
        if(convertView == null){
            convertView = inflater.inflate(R.layout.postmodel,parent,false);
        }


        MyPostPageViewHolder holder = new MyPostPageViewHolder(convertView);
        //Bind data
        holder.Address.setText(rooms.get(position).getAddress());
        holder.Post_code.setText(rooms.get(position).getPost_code());
        holder.Price.setText(rooms.get(position).getPrice());
        holder.From.setText(rooms.get(position).getStrat_Time());

        System.out.println(rooms.get(position).getEnd_Time());
        holder.To.setText(rooms.get(position).getEnd_Time());

        holder.Email.setText(rooms.get(position).getEmail());
        holder.Description.setText(rooms.get(position).getDescription());
        holder.Phone_Number.setText(rooms.get(position).getPhone_Number());
        holder.Type.setText(rooms.get(position).getType());
        holder.Furnished.setChecked(rooms.get(position).isFurnished());
        holder.Parking_Available.setChecked(rooms.get(position).isParking_Available());
        holder.Pet_Friendly.setChecked(rooms.get(position).isPet_Friendly());
        holder.Washroom.setChecked(rooms.get(position).isWashroom());

        return convertView;
    }

}
