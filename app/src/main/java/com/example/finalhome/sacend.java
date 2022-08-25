package com.example.finalhome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class sacend extends AppCompatActivity  implements View.OnClickListener{
    Button b1,b2,b3,b4,b5;
    String LINK;
    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacend);

        b1=findViewById(R.id.insert);
        b2=findViewById(R.id.haha);
        b3=findViewById(R.id.change);
        b4=findViewById(R.id.search);
        b5=findViewById(R.id.show);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);



        Intent i = new Intent (this,MyIntentService.class);
        i.setAction ( sql.OPEN );
        startService ( i );


    }

    private void createChannel()
    {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel x;
            x=new NotificationChannel (ChannelID,"My  Hi Channel with you"  , NotificationManager.IMPORTANCE_HIGH);

            NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );

            man.createNotificationChannel ( x );


        }



    }

    private int NoteId =1;

    @Override
    public void onClick(View view) {
        NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
        final NotificationCompat.Builder[] note = {null};

        if(view.getId()==R.id.insert)
        {
            Intent i1 = new Intent(getApplicationContext(), insert.class);
            startActivity(i1);
        }
        if (view.getId() == R.id.haha) {
            Intent i2 = new Intent(getApplicationContext(), delete.class);
            startActivity(i2);
        }

        if(view.getId()==R.id.change)
        {
            Intent i3 = new Intent(getApplicationContext(), change.class);
            startActivity(i3);
        }

        if(view.getId()==R.id.search)
        {
            Intent i4= new Intent(getApplicationContext(),search.class);
            startActivity(i4);
        }

        if(view.getId()==R.id.show)
        {

            Intent i = new Intent (this,MyIntentService.class);
            i.setAction ( sql.KEY_VALUE_SHOW );
            startService ( i );


            BroadcastReceiver broad= new BroadcastReceiver () {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction ().equals (sql.KEY_VALUE_STRING1 )&&intent.getIntExtra("check1",1)==1)
                    {
                        LINK = intent.getStringExtra ( sql.KEY_VALUE_STRING_BUFFER1 );
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(true);
                        builder.setTitle("My DataBase ");
                        builder.setMessage(LINK);
                        builder.show();

                        note[0] = new NotificationCompat.Builder ( context,ChannelID )
                                .setAutoCancel ( true )
                                .setOngoing ( false )
                                .setContentTitle ( "Service" )
                                .setSubText ( "Note" )
                                .setContentText ( "Taple Display is Done...." )
                                .setColor ( Color.BLUE )
                                .setColorized ( true )
                                .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                                .setShowWhen ( true )
                                .setSmallIcon ( R.drawable.slideshow );


                        man.notify (++NoteId, note[0].build ());

                    }
                    if(intent.getIntExtra("check1",1)==0)
                    {
                        Toast.makeText(context,"Sorry but Table is Empty :(",Toast.LENGTH_LONG).show();
                    }


                }
            };
            IntentFilter y= new IntentFilter ();
            y.addAction ( sql.KEY_VALUE_STRING1 );
            registerReceiver ( broad,  y);


            createChannel();




        }




    }

}