package com.example.finalhome;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class search extends AppCompatActivity implements View.OnClickListener {

    Button go;
    EditText idserch;
    String l;
    private static final  String ChannelID= " My first Channel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        go=findViewById(R.id.go);
        idserch=findViewById(R.id.idsearch);

        go.setOnClickListener(this);

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


        if (view.getId() == R.id.go) {

            final int[] c = {0};

            if(idserch.length()>0)
            {
                Intent i = new Intent (this,MyIntentService.class);
                i.setAction ( sql.SEARCH );
                i.putExtra (sql.KEY_VALUE_SEARCH,idserch.getText().toString());
                startService ( i );

                    BroadcastReceiver broad= new BroadcastReceiver () {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getAction ().equals (sql.KEY_VALUE_STRING )&&intent.getBooleanExtra("check",false)==true)
                            {
                                l = intent.getStringExtra ( sql.KEY_VALUE_STRING_BUFFER );
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);
                                builder.setTitle("My DataBase ");
                                builder.setMessage(l);
                                builder.show();

                                note[0] = new NotificationCompat.Builder ( context,ChannelID )
                                        .setAutoCancel ( true )
                                        .setOngoing ( false )
                                        .setContentTitle ( "Service" )
                                        .setSubText ( "Note" )
                                        .setContentText ( "Search is Done...." )
                                        .setColor ( Color.BLUE )
                                        .setColorized ( true )
                                        .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                                        .setShowWhen ( true )
                                        .setSmallIcon ( R.drawable.search_24 );

                                man.notify (++NoteId, note[0].build ());

                            }
                            else if (intent.getBooleanExtra("check",false)==false)
                            {
                                Toast.makeText(context, "id " + idserch.getText().toString() + ": is not found ", Toast.LENGTH_LONG).show();
                            }
                            else if(intent.getIntExtra("checkint",1)==0)
                            {
                                Toast.makeText(context, "Table is Empty", Toast.LENGTH_LONG).show();
                            }


                        }

                    };
                    IntentFilter y= new IntentFilter ();
                    y.addAction ( sql.KEY_VALUE_STRING );
                    registerReceiver ( broad,  y);

                    idserch.setText("");


            }
            else
                Toast.makeText(this, "enter id:(", Toast.LENGTH_LONG).show();








        }

    }
}


