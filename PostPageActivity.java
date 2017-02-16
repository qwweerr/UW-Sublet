package com.baimingze369gmail.uw_sublet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.mFireBase.Fire;
import com.baimingze369gmail.uw_sublet.mFireBase.PostFire;
import com.baimingze369gmail.uw_sublet.mListView.MyPostPageViewHolder;
import com.baimingze369gmail.uw_sublet.mPicasso.PicassoClient;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.baimingze369gmail.uw_sublet.mPicasso.PicassoClient.downloadImage;


/**
 * Created by Robert on 2016-06-21.
 */
public class PostPageActivity extends AppCompatActivity {
    final static String DB_URL = "https://uw-sublet.firebaseio.com/";
    ListView lv;
    ImageView big;
    PostFire fire;
    Button button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle i = getIntent().getExtras();


        setContentView(R.layout.postpagelayout);
        lv = (ListView) findViewById(R.id.lv01);
        big = (ImageView) findViewById(R.id.bigimage);
        big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                big.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
            }
        });
        if(i.getStringArrayList("pics") != null){
            ArrayList<String> imagelist = i.getStringArrayList("pics");
            LinearLayout imgHolder = (LinearLayout) findViewById(R.id.holder1);
            for (String url : imagelist) {
                final ImageView img = new ImageView(this);
                img.setAdjustViewBounds(true);
                img.setMaxWidth(300);
                img.setMaxHeight(300);
                img.setMinimumHeight(300);
                img.setMinimumWidth(300);
                //img.setScaleType(ImageView.ScaleType.CENTER_CROP);

                img.setPadding(16,16,16,16);
                downloadImage(this,url,img);
                final String myurl = url;
                img.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        big.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                        downloadImage(PostPageActivity.this, myurl, big);
                    }
                });
                imgHolder.addView(img);
            }
        }
        fire = new PostFire(this, DB_URL, lv, i);
        fire.fetchUpdate();

        button = (Button) findViewById(R.id.mapbutton1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String addr = i.getString("Address");
                Intent j = new Intent(PostPageActivity.this, MapsActivity.class);
                ArrayList<String> list = new ArrayList<>(1);
                list.add(addr);
                j.putStringArrayListExtra("addrs",list);
                startActivity(j);
            }
        });

    }


}
