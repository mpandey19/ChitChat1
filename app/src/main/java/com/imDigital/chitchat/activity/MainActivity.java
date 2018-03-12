package com.imDigital.chitchat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.imDigital.chitchat.R;

import com.imDigital.chitchat.adapter.AdapterChannels;

import com.imDigital.chitchat.interfaces.OnItemClick;
import com.imDigital.chitchat.preferences.AppPreferences;

import java.util.ArrayList;


import com.google.firebase.storage.StorageReference;

import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DatabaseError;



public class MainActivity extends AppCompatActivity implements OnItemClick, View.OnClickListener {

    private LinearLayout parent, notification;
    RecyclerView recyclerview_Channels;
    LinearLayoutManager layoutManager;
    AdapterChannels mAdapter;
    ArrayList<Channel> channelsArrayList;
    ProgressDialog pd;
    //ResNewLoginSignup bean;

    private static final String TAG = "MainActivity";
    public  String GROUP_CHANNELS = "channels";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private ImageView mAddMessageImageView;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ChannelMessage, RecyclerView.ViewHolder>
            mFirebaseAdapter;

    private String Group_ID = "1";

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private LinearLayoutManager mLinearLayout;

    private DatabaseReference mRootRef;
    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;

    private static final int GALLERY_PICK = 1;

    // Storage Firebase
    private StorageReference mImageStorage;


    //New Solution
    private int itemPos = 0;

    private String mLastKey = "";
    private String mPrevKey = "";
    ArrayList<UserGroupInformation> userGroupInformationArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent = (LinearLayout) toolbar.findViewById(R.id.parent);
        notification = (LinearLayout) toolbar.findViewById(R.id.profile);
        notification.setVisibility(View.VISIBLE);
        notification.setOnClickListener(this);
        setSupportActionBar(toolbar);
        setToolbarTitle("Dashbord");
        channelsArrayList = new ArrayList<>();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        initView();
        initVar();
    }

    public void setToolbarTitle(String feature){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        if (parent.getChildCount()>0){
            parent.removeAllViews();
        }
        /*ImageView iv = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        iv.setLayoutParams(lp);
        iv.setId(5551);
        iv.setImageResource(R.drawable.icon_logo_header);*/

        TextView tv = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        lp1.setMargins(0,3,0,3);
        tv.setLayoutParams(lp1);
        tv.setText(feature);
        tv.setTextSize(14);
        tv.setTextColor(getResources().getColor(R.color.white));
        /*Typeface tf = Typeface.createFromAsset(getAssets(), "font/Raleway-Light.ttf");
        tv.setTypeface(tf);
        parent.addView(iv);*/
        parent.addView(tv);
    }

    private void initView() {

        pd = new ProgressDialog(this);

        mAdapter = new AdapterChannels(this, channelsArrayList);

        recyclerview_Channels = (RecyclerView) findViewById(R.id.recyclerview_threads);

        mLinearLayout = new GridLayoutManager(this,2);


        recyclerview_Channels.setLayoutManager(mLinearLayout);

        recyclerview_Channels.setAdapter(mAdapter);
        loadMessages();

        //loadGroups();
    }

    private void initVar() {

    }

    private void loadMessages() {

        DatabaseReference messageRef = mRootRef.child("Groups").child("1").child("channels");

        Query messageQuery = messageRef.limitToLast(10);


        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Channel message = dataSnapshot.getValue(Channel.class);

                itemPos++;

                if(itemPos == 1){

                    String messageKey = dataSnapshot.getKey();

                    mLastKey = messageKey;
                    mPrevKey = messageKey;

                }

                channelsArrayList.add(message);
                mAdapter.notifyDataSetChanged();

                recyclerview_Channels.scrollToPosition(channelsArrayList.size() - 1);

                //mRefreshLayout.setRefreshing(false);
                //mRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    private void loadGroups() {


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userGroupInformationArrayList = new ArrayList<>();
        DatabaseReference messageRef1 = FirebaseDatabase.getInstance().getReference("users").child(uid).child("groups");

        messageRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                java.util.Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                while((iterator.hasNext())){
                    UserGroupInformation value = iterator.next().getValue(UserGroupInformation.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query messageQuery1 = messageRef1.limitToLast(10);


        /*messageQuery1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                UserGroupInformation groupDetail = dataSnapshot.getValue(UserGroupInformation.class);



                userGroupInformationArrayList.add(groupDetail);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
*/

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile:
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(View item_view, int position) {
        Intent intent = new Intent(this, ActivityChannel.class);
        intent.putExtra("thread", channelsArrayList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClickAction(String tuser_status, int position) {
        hitFollowUnfollowAPI(tuser_status, channelsArrayList.get(position).getId());
    }

    public void hitFollowUnfollowAPI(String tuser_status, String tid){
        String newStatus = "";
        if (tuser_status.equals("0")){
            newStatus = "1";
        }
        else if(tuser_status.equals("1")){
            newStatus = "0";
        }

        //ReqFollowBean reqFollowBean = new ReqFollowBean();
        //reqFollowBean.tuser_status = newStatus;
        //reqFollowBean.tid = tid;
        //reqFollowBean.uid = String.valueOf(AppPreferences.INSTANCE.getUserId());

    }
}
