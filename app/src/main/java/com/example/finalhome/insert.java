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
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class insert extends AppCompatActivity implements View.OnClickListener{

    Button d;
    EditText e1,e2,e3,e4,e5,e6;
    private static final  String ChannelID= " My first Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);




        e1=findViewById(R.id.name);
        e2=findViewById(R.id.id);
        e3=findViewById(R.id.sex);
        e4=findViewById(R.id.salary);
        e5=findViewById(R.id.ts);
        e6=findViewById(R.id.Rate);

        d=findViewById(R.id.done);
        d.setOnClickListener(this);
        e1.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyz"));
        e3.setKeyListener ( DigitsKeyListener.getInstance ( "abcdefghijklmnopqrstuvwxyz" ) );
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

        if(view.getId()==R.id.done)
        {
            if(e1.length()>0&&e2.length()>0&&e3.length()>0&&e4.length()>0&&e5.length()>0&&e6.length()>0)
            {

                Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                while (c.moveToNext())
                {
                    if(c.getInt(1)==Integer.parseInt(e2.getText().toString()))
                    {
                        check=1;
                    }
                }

                if(check==0)
                {
                    String sex=e3.getText().toString().trim();
                    if(sex.contentEquals("male")||sex.contentEquals("female"))
                    {

                        String s="insert into emp values('";
                        s+=e1.getText().toString();
                        s+="',";
                        s+=e2.getText().toString();
                        s+=",'";
                        s+=e3.getText().toString();
                        s+="',";
                        s+=e4.getText().toString();
                        s+=',';
                        s+=e5.getText().toString();
                        s+=',';
                        s+=e6.getText().toString();
                        s+=");";

                        Intent i = new Intent (this,MyIntentService.class);
                        i.setAction ( sql.INSERT );
                        i.putExtra (sql.KEY_VALUE_INSERT,s);
                        startService ( i );


                        createChannel();

                        note = new NotificationCompat.Builder ( this,ChannelID )
                                .setAutoCancel ( true )
                                .setOngoing ( false )
                                .setContentTitle ( "Service" )
                                .setSubText ( "Note" )
                                .setContentText ( "Insert is Done...." )
                                .setColor ( Color.BLUE )
                                .setColorized ( true )
                                .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                                .setShowWhen ( true )
                                .setSmallIcon ( R.drawable.insert_chart );


                        man.notify (++NoteId, note.build ());





                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                        e5.setText("");
                        e6.setText("");

                    }
                    else
                    {

                        Toast.makeText(this,"sex : male or female just :( ",Toast.LENGTH_LONG).show();
                    }


                }
                else if(check==1)
                {

                    Toast.makeText(this,"id "+e2.getText().toString()+": is alredy exist ",Toast.LENGTH_LONG).show();
                }
                check=0;

            }
            else
            {

                Toast.makeText(this,"enter all digits :(",Toast.LENGTH_LONG).show();
            }






        }


    }

}















