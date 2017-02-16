package com.baimingze369gmail.uw_sublet.mPicasso;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.baimingze369gmail.uw_sublet.R;
/**
 * Created by Robert on 2016-06-17.
 */

//used for img downloading
public class PicassoClient {

    public static void downloadImage(Context c, String description, ImageView image){
        if(description != null && description.length() > 0){
//            Picasso.with(c).load(description).placeholder(R.drawable.em).into(image);
            Picasso.with(c).load(description).fit().centerCrop().placeholder(R.drawable.download).into(image);
        }else{
            Picasso.with(c).load(R.drawable.download).fit().centerCrop().into(image);
        }
    }

}
