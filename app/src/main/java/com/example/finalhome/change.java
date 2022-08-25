package com.example.finalhome;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class change extends AppCompatActivity implements View.OnClickListener
{
    Button modify,done;
    EditText e1,e2,e3,e4,e5,e6;
    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        modify=findViewById(R.id.modify);
        done=findViewById(R.id.donemodify);

        e1=findViewById(R.id.idmodify);

        e2=findViewById(R.id.namemodify);
        e3=findViewById(R.id.sexmodify);
        e4=findViewById(R.id.salarymodify);
        e5=findViewById(R.id.totalsalarymodify);
        e6=findViewById(R.id.ratemodify);


        e2.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        e4.setVisibility(View.INVISIBLE);
        e5.setVisibility(View.INVISIBLE);
        e6.setVisibility(View.INVISIBLE);

        e4.setKeyListener ( DigitsKeyListener.getInstance ( "0123456789" ) );
        e5.setKeyListener ( DigitsKeyListener.getInstance ( "0123456789" ) );
        e6.setKeyListener ( DigitsKeyListener.getInstance ( "0123456789" ) );


        modify.setOnClickListener(this);
        done.setOnClickListener(this);

        done.setVisibility(View.INVISIBLE);

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
    public void onClick(View view)
    {
        int cheeck=0;
        int sexx=1;
        NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
        NotificationCompat.Builder note = null;
        int check=0;
        if(view.getId()==R.id.modify)
        {
            if(e1.length()>0)
            {


                Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                while (c.moveToNext())
                {
                    if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                    {
                        check=1;
                    }
                }

                if(check==1)
                {
                    e2.setVisibility(View.VISIBLE);
                    e3.setVisibility(View.VISIBLE);
                    e4.setVisibility(View.VISIBLE);
                    e5.setVisibility(View.VISIBLE);
                    e6.setVisibility(View.VISIBLE);
                    done.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"id is found",Toast.LENGTH_LONG).show();

                    e1.setVisibility(View.INVISIBLE);
                }
                else if(check==0)
                {
                    Toast.makeText(this,"id "+e1.getText().toString()+": is not found ",Toast.LENGTH_LONG).show();
                }
                check=0;
            }
            else
                Toast.makeText(this,"enter id :(",Toast.LENGTH_LONG).show();

        }

        if(view.getId()==R.id.donemodify)
        {
            if(e2.length()>0||e3.length()>0||e4.length()>0||e5.length()>0||e6.length()>0)
            {
                if(e2.length()>0)
                {
                    Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                    while (c.moveToNext())
                    {
                        if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                        {
                            String NAME="Update emp Set name ='";
                            NAME+=e2.getText().toString();
                            NAME+="' where ID=";
                            NAME+=e1.getText().toString();
                            NAME+=";";
                            Intent i = new Intent (this,MyIntentService.class);
                            i.setAction ( sql.MODIFY );
                            i.putExtra (sql.KEY_VALUE_MODIFY,NAME);
                            startService ( i );
                            cheeck=1;

                            break;
                        }
                    }

                }


                if(e3.length()>0)
                {

                    String sex=e3.getText().toString().trim();
                    Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                    while (c.moveToNext())
                    {
                        if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                        {
                            if(sex.contentEquals("male")||sex.contentEquals("female"))
                            {
                                String SEX="Update emp Set sex ='";
                                SEX+=e3.getText().toString();
                                SEX+="' where ID=";
                                SEX+=e1.getText().toString();
                                SEX+=";";
                                Intent i = new Intent (this,MyIntentService.class);
                                i.setAction ( sql.MODIFY );
                                i.putExtra (sql.KEY_VALUE_MODIFY,SEX);
                                startService ( i );
                                cheeck=1;
                            }
                            else
                            {
                                Toast.makeText(this,"just male or female ",Toast.LENGTH_LONG).show();
                                sexx=0;
                            }

                            break;
                        }
                    }

                }

                if(e4.length()>0)
                {
                    Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                    while (c.moveToNext())
                    {
                        if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                        {
                            String SALARY="Update emp Set salary ='";
                            SALARY+=e4.getText().toString();
                            SALARY+="' where ID=";
                            SALARY+=e1.getText().toString();
                            SALARY+=";";
                            Intent i = new Intent (this,MyIntentService.class);
                            i.setAction ( sql.MODIFY );
                            i.putExtra (sql.KEY_VALUE_MODIFY,SALARY);
                            startService ( i );
                            cheeck=1;
                            break;
                        }
                    }

                }

                if(e5.length()>0)
                {
                    Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                    while (c.moveToNext())
                    {
                        if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                        {
                            String TOTALSALARY="Update emp Set totalSalary ='";
                            TOTALSALARY+=e5.getText().toString();
                            TOTALSALARY+="' where ID=";
                            TOTALSALARY+=e1.getText().toString();
                            TOTALSALARY+=";";
                            Intent i = new Intent (this,MyIntentService.class);
                            i.setAction ( sql.MODIFY );
                            i.putExtra (sql.KEY_VALUE_MODIFY,TOTALSALARY);
                            startService ( i );
                            cheeck=1;

                            sql.DB.execSQL("Update emp Set totalSalary ='"+e5.getText().toString()+"' where ID="+e1.getText()+";");
                            break;
                        }
                    }

                }

                if(e6.length()>0)
                {
                    Cursor c=sql.DB.rawQuery("select * from emp ;",null);
                    while (c.moveToNext())
                    {
                        if(c.getInt(1)==Integer.parseInt(e1.getText().toString()))
                        {
                            String RATE="Update emp Set rate ='";
                            RATE+=e6.getText().toString();
                            RATE+="' where ID=";
                            RATE+=e1.getText().toString();
                            RATE+=";";
                            Intent i = new Intent (this,MyIntentService.class);
                            i.setAction ( sql.MODIFY );
                            i.putExtra (sql.KEY_VALUE_MODIFY,RATE);
                            startService ( i );
                            cheeck=1;
                            break;
                        }
                    }

                }

                e2.setVisibility(View.INVISIBLE);
                e3.setVisibility(View.INVISIBLE);
                e4.setVisibility(View.INVISIBLE);
                e5.setVisibility(View.INVISIBLE);
                e6.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                e6.setText("");


                e1.setVisibility(View.VISIBLE);

                if(cheeck==1&&sexx==1)
                {
                    createChannel();

                    note = new NotificationCompat.Builder ( this,ChannelID )
                            .setAutoCancel ( true )
                            .setOngoing ( false )
                            .setContentTitle ( "Service" )
                            .setSubText ( "Note" )
                            .setContentText ( "Modify is Done...." )
                            .setColor ( Color.BLUE )
                            .setColorized ( true )
                            .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                            .setShowWhen ( true )
                            .setSmallIcon ( R.drawable.ic_baseline_change_circle_24 );


                    man.notify (++NoteId, note.build ());
                }
                if(cheeck==1&&sexx==0)
                {
                    createChannel();

                    note = new NotificationCompat.Builder ( this,ChannelID )
                            .setAutoCancel ( true )
                            .setOngoing ( false )
                            .setContentTitle ( "Service" )
                            .setSubText ( "Note" )
                            .setContentText ( "Modify is Done but sex male or female just...." )
                            .setColor ( Color.BLUE )
                            .setColorized ( true )
                            .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                            .setShowWhen ( true )
                            .setSmallIcon ( R.drawable.ic_baseline_change_circle_24 );


                    man.notify (++NoteId, note.build ());
                }

                cheeck=0;

            }
            else
                Toast.makeText(this,"not change enter any thing",Toast.LENGTH_LONG).show();
        }
    }
}
