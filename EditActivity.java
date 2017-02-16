package com.baimingze369gmail.uw_sublet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.mFireBase.Fire;
import com.baimingze369gmail.uw_sublet.mFireBase.PostFire;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.baimingze369gmail.uw_sublet.mPicasso.PicassoClient.downloadImage;

/**
 * Created by mingze on 2016/6/28.
 */
public class EditActivity extends AppCompatActivity {
    int uploaded_img = 0;
    private ProgressDialog mProgressDialog;
    ImageView fashionImg;
    private static int RESULT_LOAD_IMAGE = 1;
    Button post_buton;
    Button delete_buton;
    Room r;
    ArrayList<String> uriList;

    EditText address;
    EditText post_code;
    EditText price;
    Spinner type;
    EditText from;
    EditText to;
    CheckedTextView furnished;
    CheckedTextView pet_friendly;
    CheckedTextView parking_available;
    CheckedTextView private_washroom;
    EditText description;
    EditText phone_number;
    EditText email;


    static int n1;
    static int n2;
    boolean post = false;
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    public static void addone(){
        n1++;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlayout);
        n1 = 0;
        n2 = 0;
        post_buton = (Button) findViewById(R.id.feedback_send);
        delete_buton = (Button) findViewById(R.id.feedback_delete);
        Bundle i = getIntent().getExtras();
        //address
        address = (EditText) findViewById(R.id.feedback_addr);
        address.setText(i.getString("Address"));
        //post code
        post_code = (EditText) findViewById(R.id.feedback_postal);
        post_code.setText(i.getString("post"));
        //price
        price = (EditText) findViewById(R.id.feedback_price);
        price.setText(i.getString("Price"));
        //type
        type = (Spinner) findViewById(R.id.feedback_type);
        type.setSelection(getIndex(type, i.getString("type")));
        //from
        from = (EditText) findViewById(R.id.feedback_from);
        from.setText(i.getString("from"));
        //to
        to = (EditText) findViewById(R.id.feedback_to);
        to.setText(i.getString("to"));

        //check box
        furnished = (CheckedTextView) findViewById(R.id.switch1);
        furnished.setChecked(i.getBoolean("furnish"));
        furnished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((CheckedTextView) v).toggle();
            }
        });
        pet_friendly = (CheckedTextView) findViewById(R.id.switch2);
        pet_friendly.setChecked(i.getBoolean("pet"));
        pet_friendly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((CheckedTextView) v).toggle();
            }
        });
        parking_available = (CheckedTextView) findViewById(R.id.switch3);
        parking_available.setChecked(i.getBoolean("parkAvail"));
        parking_available.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((CheckedTextView) v).toggle();
            }
        });
        private_washroom = (CheckedTextView) findViewById(R.id.switch4);
        private_washroom.setChecked(i.getBoolean("washroom"));
        private_washroom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((CheckedTextView) v).toggle();
            }
        });

        //description
        description = (EditText) findViewById(R.id.feedback_message);
        description.setText(i.getString("description"));

        //phone_number
        phone_number = (EditText) findViewById(R.id.feedback_phone);
        phone_number.setText(i.getString("number"));
        //email
        email = (EditText) findViewById(R.id.feedback_email);
        email.setText(i.getString("email"));

        uriList = new  ArrayList<String>();


        fashionImg = (ImageView) findViewById(R.id.imageV1);
        fashionImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(
                        //ACTION_GET_CONTENT
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(i,"Select Picture"), RESULT_LOAD_IMAGE);
                return true;
            }

        });
        ArrayList<String> Pics = i.getStringArrayList("pics");
        LinearLayout imgHolder = (LinearLayout) findViewById(R.id.holder);
        MainActivity.fire.uriList.clear();
        if(Pics != null){
            for (String url : Pics) {
                MainActivity.fire.uriList.add(url);
                ImageView img = new ImageView(this);
                img.setAdjustViewBounds(true);
                img.setMaxWidth(300);
                img.setMaxHeight(300);
                img.setMinimumHeight(300);
                img.setMinimumWidth(300);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);

                img.setPadding(16,16,16,16);
                downloadImage(this ,url, img);
                //img.setBackgroundColor(Integer.parseInt("#000000"));

                imgHolder.addView(img);
            }
        }



        delete_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = getIntent().getExtras();
                MainActivity.fire.delete_room(i.getString("roomid"));
                finish();
            }
        });

        post_buton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!post){
                    //create a new room
                    r = new Room();
                    //address
                    r.setAddress(address.getText().toString());
                    //post code
                    r.setPost_code(post_code.getText().toString());
                    //price
                    r.setPrice(price.getText().toString());
                    //type
                    r.setType(type.getSelectedItem().toString());
                    //from
                    r.setStrat_Time(from.getText().toString());
                    //to
                    r.setEnd_Time(to.getText().toString());

                    //check box
                    r.setFurnished(furnished.isChecked());
                    r.setPet_Friendly(pet_friendly.isChecked());
                    r.setParking_Available(parking_available.isChecked());
                    r.setWashroom(private_washroom.isChecked());

                    //description
                    r.setDescription(description.getText().toString());

                    //phone_number
                    r.setPhone_Number(phone_number.getText().toString());
                    //email
                    r.setEmail(email.getText().toString());

                    uriList =  MainActivity.fire.uriList;

                    r.setPics(uriList);
                    r.setUid(MainActivity.cur_user.getUid());


                    Log.d("n1", String.valueOf(n1));
                    Log.d("n2", String.valueOf(n2));
                    post = true;
                    showProgressDialog();
                    startTimer();
                }





//
//                finish();






            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                if (mClipData.getItemCount() > 0) {
//                    showProgressDialog();
                    //MainActivity.fire.uriList.clear();
                    n2 = mClipData.getItemCount();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        Log.d("ppp", String.valueOf(mClipData.getItemCount()));
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        MainActivity.fire.saveImg(uri);

                        LinearLayout imgHolder = (LinearLayout) findViewById(R.id.holder);

                        ImageView img = new ImageView(this);
                        img.setAdjustViewBounds(true);
                        img.setMaxWidth(300);
                        img.setMaxHeight(300);
                        img.setMinimumHeight(300);
                        img.setMinimumWidth(300);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        //img.setBackgroundColor(Integer.parseInt("#000000"));

                        img.setPadding(16,16,16,16);
                        downloadImage(this ,String.valueOf(uri), img);
                        imgHolder.addView(img);
                    }
//                    r.setPics(uriList);
                    //hideProgressDialog();

                }
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
            mProgressDialog.dismiss();
        }
    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        Log.d("n1", String.valueOf(n1));
                        Log.d("n2", String.valueOf(n2));
                        if ((post) && (n1 == n2)){
                            hideProgressDialog();
                            MainActivity.fire.save_room(r);
                            Bundle i = getIntent().getExtras();
                            MainActivity.fire.delete_room(i.getString("roomid"));
                            n1 = 0;
                            n2 = 0;
                            stopTimer();
                            finish();
                        }
                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 1, 800);
    }
}

