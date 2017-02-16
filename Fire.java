package com.baimingze369gmail.uw_sublet.mFireBase;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.EditActivity;
import com.baimingze369gmail.uw_sublet.MainActivity;
import com.baimingze369gmail.uw_sublet.PostNewActivity;
import com.baimingze369gmail.uw_sublet.R;
import com.baimingze369gmail.uw_sublet.mListView.CustomerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Robert on 2016-06-17.
 */


public class Fire {
    Context c;
    public ArrayList<Room> rooms = new ArrayList<>();
    CustomerAdapter adapter;
    ListView lv;
    //realtime database
    FirebaseDatabase database;
    DatabaseReference firebase;

    //Firebase storage
    FirebaseStorage storage;
    StorageReference storageRef;

    String DB_URL;
    public Uri uri;

    public ArrayList<String> uriList;
    private String user_uid;



    int sort_from = 0;
    int sort_to = 0;


    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }



    public Uri getUri() {
        return uri;
    }



    public Fire(Context c, String DB_URL, ListView lv) {
        this.c = c;
        this.DB_URL = DB_URL;
        this.lv = lv;

        uriList = new  ArrayList<String>();

        //initial real time database
        database = FirebaseDatabase.getInstance();
        firebase = database.getReference();
        //initial firebase storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://uw-sublet.appspot.com");
//        storageRef = storage.getReference();



    }

    public int getSort_from() {
        return sort_from;
    }

    public void setSort_from(int sort_from) {
        this.sort_from = sort_from;
    }

    public int getSort_to() {
        return sort_to;
    }

    public void setSort_to(int sort_to) {
        this.sort_to = sort_to;
    }

    //save data
    public void saveOline(String Address, String Price,String Start_time,String End_time){
        Room r = new Room();
        r.setAddress(Address);
        r.setPrice(Price);
        r.setStrat_Time(Start_time);
        r.setEnd_Time(End_time);
        ArrayList<String> pic = new ArrayList<>();
        pic.add(new String("https://firebasestorage.googleapis.com/v0/b/uw-sublet.appspot.com/o/images%2F1125669865?alt=media&token=1f010e18-d08c-46fd-ba6b-b94fe6ce3a3f"));
        pic.add(new String("123"));
        pic.add(new String("123"));
        r.setPics(pic);


        firebase.child("Rooms").push().setValue(r);

    }

    //save a room to DB
    public void save_room(Room room){
        firebase.child("Rooms").push().setValue(room);
    }

    public void saveImg(Uri file){
//        Uri file = Uri.fromFile(new File("drawable://mountain"));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                PostNewActivity.addone();
                EditActivity.addone();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                uri = downloadUrl;
                uriList.add(String.valueOf(uri));
                PostNewActivity.addone();
                EditActivity.addone();
                Log.d("onSuccess","onSuccess");
            }
        });
    }

    //retrieveing
    public void refreshData(){
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchUpdate(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchUpdate(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void fetchUpdate(DataSnapshot dataSnapshot){
        rooms.clear();
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if (user_uid == null){

                Room r = new Room();
                r.roomid = (ds.getKey());
                r.setAddress(ds.getValue(Room.class).getAddress());
                r.setPrice(ds.getValue(Room.class).getPrice());
                r.setStrat_Time(ds.getValue(Room.class).getStrat_Time());
                r.setEnd_Time(ds.getValue(Room.class).getEnd_Time());
                r.setPics(ds.getValue(Room.class).getPics());
                r.setPost_code(ds.getValue(Room.class).getPost_code());
                r.setPhone_Number(ds.getValue(Room.class).getPhone_Number());
                r.setDescription(ds.getValue(Room.class).getDescription());
                r.setType(ds.getValue(Room.class).getType());
                r.setEmail(ds.getValue(Room.class).getEmail());
                r.setWashroom(ds.getValue(Room.class).isWashroom());
                r.setParking_Available(ds.getValue(Room.class).isParking_Available());
                r.setPet_Friendly(ds.getValue(Room.class).isPet_Friendly());
                r.setFurnished(ds.getValue(Room.class).isFurnished());
                if ((sort_from == 0) || (sort_to == 0)){
                    rooms.add(0,r);
                }
                else{
                    if (((ds.getValue(Room.class).getPrice()) != "") &&
                            (Integer.parseInt((ds.getValue(Room.class).getPrice())) >= sort_from) &&
                            (Integer.parseInt((ds.getValue(Room.class).getPrice())) <= sort_to)) {
                        rooms.add(0,r);

                    }
                }

            }
            else{
                if(Objects.equals(user_uid, ds.getValue(Room.class).getUid())){
                    Room r = new Room();
                    r.roomid = ds.getKey();
                    r.setAddress(ds.getValue(Room.class).getAddress());
                    r.setPrice(ds.getValue(Room.class).getPrice());
                    r.setStrat_Time(ds.getValue(Room.class).getStrat_Time());
                    r.setEnd_Time(ds.getValue(Room.class).getEnd_Time());
                    r.setPics(ds.getValue(Room.class).getPics());
                    r.setPost_code(ds.getValue(Room.class).getPost_code());
                    r.setPhone_Number(ds.getValue(Room.class).getPhone_Number());
                    r.setDescription(ds.getValue(Room.class).getDescription());
                    r.setType(ds.getValue(Room.class).getType());
                    r.setEmail(ds.getValue(Room.class).getEmail());
                    r.setWashroom(ds.getValue(Room.class).isWashroom());
                    r.setParking_Available(ds.getValue(Room.class).isParking_Available());
                    r.setPet_Friendly(ds.getValue(Room.class).isPet_Friendly());
                    r.setFurnished(ds.getValue(Room.class).isFurnished());
                    if ((sort_from == 0) || (sort_to == 0)){
                        rooms.add(0,r);
                    }
                    else{
                        if ((Integer.parseInt((ds.getValue(Room.class).getPrice())) >= sort_from) &&
                                (Integer.parseInt((ds.getValue(Room.class).getPrice())) <= sort_to)) {
                            rooms.add(0,r);

                        }
                    }
                }
            }




        }

        if(rooms.size() > 0){
            adapter = new CustomerAdapter(c,rooms);
            lv.setAdapter(adapter);
        }

    }

    public void delete_room(String url){
        Log.d("12345", url);
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Rooms/"+url);
        ref.removeValue();
    }

}
