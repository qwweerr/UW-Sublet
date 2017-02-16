package com.baimingze369gmail.uw_sublet.mListView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.mPicasso.PicassoClient;
import com.squareup.picasso.Picasso;
import com.baimingze369gmail.uw_sublet.R;
import java.util.ArrayList;

/**
 * Created by Robert on 2016-06-17.
 */
public class CustomerAdapter extends BaseAdapter{

    Context c;
    ArrayList<Room> rooms;
    LayoutInflater inflater;

    public CustomerAdapter(Context c, ArrayList<Room> rooms) {
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
            convertView = inflater.inflate(R.layout.model,parent,false);
        }

        MyViewHolder holder = new MyViewHolder(convertView);
        //Bind data
        holder.Address.setText(rooms.get(position).getAddress());
        holder.Price.setText(rooms.get(position).getPrice());
        holder.From.setText(rooms.get(position).getStrat_Time());
        holder.To.setText(rooms.get(position).getEnd_Time());
        if ( rooms.get(position).getPics() != null) {
            PicassoClient.downloadImage(c, rooms.get(position).getPics().get(0), holder.image);
        }else {
            PicassoClient.downloadImage(c, null, holder.image);
        }
        return convertView;
    }
}
