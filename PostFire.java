package com.baimingze369gmail.uw_sublet.mFireBase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ScrollView;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.mListView.CustomerAdapter;
import com.baimingze369gmail.uw_sublet.mListView.PostPageAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * Created by Robert on 2016-06-21.
 */
public class PostFire {
    Context c;
    ArrayList<Room> rooms = new ArrayList<>();
    PostPageAdapter adapter;
    ListView lv;
    Bundle data;
    //realtime database
    FirebaseDatabase database;
    DatabaseReference firebase;

    String DB_URL;


    public PostFire(Context c, String url, ListView lv, Bundle data){
        this.c = c;
        this.DB_URL = url;
        this.lv = lv;
        this.data = data;
    }


    public void fetchUpdate(){
        Room r = new Room();
        r.setAddress(this.data.getString("Address"));
        r.setPrice(this.data.getString("Price"));
        r.setType(this.data.getString("type"));
        r.setStrat_Time(this.data.getString("from"));
        r.setEnd_Time(this.data.getString("to"));
        System.out.println(r.getEnd_Time());
        r.setPics(this.data.getStringArrayList("pics"));
        r.setDescription(this.data.getString("description"));
        r.setEmail(this.data.getString("email"));
        r.setPhone_Number(this.data.getString("number"));
        r.setWashroom(this.data.getBoolean("washroom"));
        r.setFurnished(this.data.getBoolean("furnish"));
        r.setPet_Friendly(this.data.getBoolean("pet"));
        r.setParking_Available(this.data.getBoolean("parkAvail"));
        r.setPost_code(this.data.getString("post"));
        r.roomid=this.data.getString("roomid");

        rooms.add(r);
        if(rooms.size() > 0){
            adapter = new PostPageAdapter(c,rooms);
            lv.setAdapter(adapter);
        }

    }
}
