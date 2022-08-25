package com.example.finalhome;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class delete extends AppCompatActivity implements View.OnClickListener {

    Button del;
    EditText ID;
    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        del=findViewById(R.id.haha);
        ID=findViewById(R.id.iddel);

        del.setOnClickListener(this);

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
        NotificationCompat.Builder note = null;
        int check=0;
        if(view.getId()==R.id.haha)
        {
            if(ID.length()>0)
            {
                Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                while (c.moveToNext())
                {
                    if(c.getInt(1)==Integer.parseInt(ID.getText().toString()))
                    {
                        check=1;
                        System.out.println(check);
                    }
                }

                if(check==1)
                {
                    String s ="delete from emp where id =";
                    s+=ID.getText().toString();
                    s+=";";

                    Intent i = new Intent (this,MyIntentService.class);
                    i.setAction ( sql.DELETE );
                    i.putExtra (sql.KEY_VALUE_DELETE,s);
                    startService ( i );


                    ID.setText("");
                    createChannel();

                    note = new NotificationCompat.Builder ( this,ChannelID )
                            .setAutoCancel ( true )
                            .setOngoing ( false )
                            .setContentTitle ( "Service" )
                            .setSubText ( "Note" )
                            .setContentText ( "Delete is Done...." )
                            .setColor ( Color.BLUE )
                            .setColorized ( true )
                            .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                            .setShowWhen ( true )
                            .setSmallIcon ( R.drawable.ic_baseline_delete_forever_24 );


                    man.notify (++NoteId, note.build ());

                }
                else if(check==0)
                {
                    Toast.makeText(this,"id "+ID.getText().toString()+": is not found ",Toast.LENGTH_LONG).show();
                }
                check=0;
            }
            else
                Toast.makeText(this,"enter id :)",Toast.LENGTH_LONG).show();



        }
    }
}