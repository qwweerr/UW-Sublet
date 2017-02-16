package com.baimingze369gmail.uw_sublet;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baimingze369gmail.uw_sublet.Data.Room;
import com.baimingze369gmail.uw_sublet.mFireBase.Fire;
import com.baimingze369gmail.uw_sublet.mListView.PostPageAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.baimingze369gmail.uw_sublet.R.drawable.em;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
    {
    boolean viewall = true;
    static final String TAG = "CallCamera";

    final static String DB_URL = "https://uw-sublet.firebaseio.com/";
        int sort_from,sort_to;
    EditText price_from, price_to;
    Button OkButton;
    Button cameraButton;
    ListView lv;
    ArrayList<String> titles = new ArrayList<>();
    static Fire fire;
    //-----------google login
    private static final String googleTAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    //private TextView mDetailTextView;
    private ProgressDialog mProgressDialog;
    public static FirebaseUser cur_user;
    //----------google login

    String url = null;
//////////////////////////////////////////////
    private static ListView mDrawerList;
    private static ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

//////////////////////////////////////////////

//////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lv);
        fire = new Fire(this,DB_URL,lv);
        fire.refreshData();
        sort_from = 0;
        sort_to = 0;

        final PostPageAdapter postAdapter = new PostPageAdapter(this,fire.rooms);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                if (viewall) {
                    Room room = (Room) postAdapter.getItem(position);
                    Intent i = new Intent(MainActivity.this, PostPageActivity.class);
                    i.putExtra("Address", room.getAddress());
                    i.putExtra("Price", room.getPrice());
                    i.putExtra("from", room.getStrat_Time());
                    i.putExtra("to", room.getEnd_Time());
                    i.putExtra("type", room.getType());
                    i.putExtra("name", room.getContact_Name());
                    i.putExtra("email", room.getEmail());
                    i.putExtra("description", room.getDescription());
                    i.putExtra("number", room.getPhone_Number());
                    i.putExtra("furnish", room.isFurnished());
                    i.putExtra("parkAvail", room.isParking_Available());
                    i.putExtra("pet", room.isPet_Friendly());
                    i.putExtra("washroom", room.isWashroom());
                    i.putStringArrayListExtra("pics", room.getPics());
                    i.putExtra("post", room.getPost_code());
                    i.putExtra("roomid", room.roomid);
                    startActivity(i);
                }else{
                    Room room = (Room) postAdapter.getItem(position);
                    Intent i = new Intent(MainActivity.this, EditActivity.class);
                    i.putExtra("Address", room.getAddress());
                    i.putExtra("Price", room.getPrice());
                    i.putExtra("from", room.getStrat_Time());
                    i.putExtra("to", room.getEnd_Time());
                    i.putExtra("type", room.getType());
                    i.putExtra("name", room.getContact_Name());
                    i.putExtra("email", room.getEmail());
                    i.putExtra("description", room.getDescription());
                    i.putExtra("number", room.getPhone_Number());
                    i.putExtra("furnish", room.isFurnished());
                    i.putExtra("parkAvail", room.isParking_Available());
                    i.putExtra("pet", room.isPet_Friendly());
                    i.putExtra("washroom", room.isWashroom());
                    i.putStringArrayListExtra("pics", room.getPics());
                    i.putExtra("post", room.getPost_code());
                    i.putExtra("roomid", room.roomid);
                    startActivity(i);
                }



            }
        });

        //--------------google login
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode("37487413464-5nvv38h3a8sam2j8n21eshhrmromfj8r.apps.googleusercontent.com", false)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                cur_user = firebaseAuth.getCurrentUser();
                if (cur_user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + cur_user.getUid());
                } else {
                    // User is signed out
                    cur_user = null;
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(cur_user);
                // [END_EXCLUDE]
            }
        };

        //--------------google login

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });*/
//////////////////////////////////////////////////////////////
        mDrawerList = (ListView)findViewById(R.id.navList);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        addDrawerItems(false);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


/////////////////////////////////////////////////////////////
    }
    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
/////////////////////
    public void addDrawerItems(boolean Yes) {
        if(Yes) {
            String[] osArray = {cur_user.getDisplayName() + "/Logout", "All Rooms","Post My Room", "Posted", " View in Map", "Search"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
            mDrawerList.setAdapter(mAdapter);

            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            signOut();
                            break;
                        case 1:
                            show_all();
                            viewall = true;
                            setTitle("UW_Sublet");
                            break;
                        case 2:
                            //the post button
//                            showDialog();
                            post_new();

                            break;
                        case 3:
                            //The MY Post button

                            show_posted();
                            viewall = false;
                            setTitle("My Posted Rooms");
                            break;
                        case 4:
                            open_map();
                            break;
                        case 5:
                            showDialog();
                            break;
                        default:
                    }
                }
            });
        }else if(!Yes){
            String[] osArray = {"Login", "View in Map", "Search"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
            mDrawerList.setAdapter(mAdapter);

            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            signIn();
                            break;
                        case 1:
                            open_map();
                            break;
                        case 2:
                            showDialog();
                            break;
                        default:
                    }
                }
            });
        }
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.post_icon);
        ab.setDisplayHomeAsUpEnabled(true);
}
////////////////////////

    private void showDialog(){
        final Dialog d = new Dialog(this);
        d.setTitle("Post your room");
        d.setContentView(R.layout.dialoglayout);

        price_from = (EditText) d.findViewById(R.id.from);
        price_to = (EditText) d.findViewById(R.id.to);
        OkButton = (Button) d.findViewById(R.id.ok);
        final Context context = this;


        OkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sort_from = Integer.parseInt(price_from.getText().toString());
                sort_to = Integer.parseInt(price_to.getText().toString());
                sort_fire();
                d.dismiss();

            }
        });

        d.show();
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle - maxwell
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //////////////////////////////////

        return super.onOptionsItemSelected(item);
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        cur_user = null;
                        updateUI(null);
                    }
                });
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
            mProgressDialog.hide();
        }
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            addDrawerItems(true);
        } else {
            addDrawerItems(false);
        }

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        setupDrawer();
    }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            // An unresolvable error has occurred and Google APIs (including Sign-In) will not
            // be available.
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
            Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
        }


        //post new room
        private void post_new(){
            Intent i = new Intent(MainActivity.this,PostNewActivity.class);
            startActivity(i);
        }




        private void show_posted(){
            fire.setUser_uid(cur_user.getUid());

            fire.refreshData();
        }
        private void show_all(){
            fire.setUser_uid(null);
            sort_from  = 0;
            sort_to = 0;
            fire.setSort_from(sort_from);
            fire.setSort_to(sort_to);

            fire.refreshData();
        }

        private void sort_fire(){
            fire.setSort_from(sort_from);
            fire.setSort_to(sort_to);

            fire.refreshData();
        }


        private void open_map()
        {
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            ArrayList<String> addrs = new ArrayList<String>(fire.rooms.size());
            for(int j=0; j<this.fire.rooms.size(); j++){
                addrs.add(j,fire.rooms.get(j).getAddress());
            }
            i.putStringArrayListExtra("addrs", addrs);
            startActivity(i);
        }
    }






