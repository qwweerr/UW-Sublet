package com.baimingze369gmail.uw_sublet.mListView;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baimingze369gmail.uw_sublet.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Robert on 2016-06-21.
 */
public class MyPostPageViewHolder{
    TextView Address;     //328 Regina North
    TextView Post_code;   //N2J 0B5
    TextView Price;       // 680
    TextView From;
    TextView To;
    TextView Type;//condo apartment house
    CheckedTextView Furnished;
    CheckedTextView Pet_Friendly;
    CheckedTextView Parking_Available;

    TextView Phone_Number;
    TextView Email;
    CheckedTextView Washroom; //shared or private
    TextView Description; //additional info



   public MyPostPageViewHolder(View itemView){
       Address = (TextView) itemView.findViewById(R.id.addr2);
       Post_code = (TextView) itemView.findViewById(R.id.postal2);
       Type = (TextView) itemView.findViewById(R.id.type2);
       Price = (TextView) itemView.findViewById(R.id.price2);
       From = (TextView) itemView.findViewById(R.id.from2);
       To = (TextView) itemView.findViewById(R.id.to2);
       Description = (TextView) itemView.findViewById(R.id.description2);
       Phone_Number = (TextView) itemView.findViewById(R.id.phone2);
       Email = (TextView) itemView.findViewById(R.id.email2);
       Furnished = (CheckedTextView) itemView.findViewById(R.id.switch11);
       Pet_Friendly = (CheckedTextView) itemView.findViewById(R.id.switch22);
       Parking_Available = (CheckedTextView) itemView.findViewById(R.id.switch33);
       Washroom = (CheckedTextView) itemView.findViewById(R.id.switch44);
   }
}
