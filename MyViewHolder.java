package com.baimingze369gmail.uw_sublet.mListView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baimingze369gmail.uw_sublet.R;

/**
 * Created by Robert on 2016-06-17.
 */
public class MyViewHolder {
    TextView Address;
    TextView Price;
    TextView From;
    TextView To;
    ImageView image;


    public MyViewHolder(View itemView) {
        Address = (TextView) itemView.findViewById(R.id.address);
        Price = (TextView) itemView.findViewById(R.id.price);
        From = (TextView) itemView.findViewById(R.id.from);
        To = (TextView) itemView.findViewById(R.id.to);
        image = (ImageView) itemView.findViewById(R.id.roomImage);
    }
}
