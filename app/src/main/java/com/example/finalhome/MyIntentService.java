package com.example.finalhome;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {

        System.out.println("IntentService--> calls OnCreate");
        sql.DB=openOrCreateDatabase ( "qData",MODE_PRIVATE,null );
        sql.DB.execSQL (" create table if not exists emp (name char,id int , sex char , salary int , totalSalary int ,rate int , primary key(id))");
        super.onCreate ();
    }

    @Override
    public void onDestroy() {
        System.out.println("IntentService--> calls onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("IntentService--> calls onHandleIntent");

        if (intent.getAction ().equals ( sql.INSERT )){
            String S= intent.getStringExtra ( sql.KEY_VALUE_INSERT );
            sql.DB.execSQL(S);





        }

        if (intent.getAction ().equals ( sql.DELETE )){
            String S= intent.getStringExtra ( sql.KEY_VALUE_DELETE );
            sql.DB.execSQL(S);
        }

        if (intent.getAction ().equals ( sql.MODIFY )){
            String S= intent.getStringExtra ( sql.KEY_VALUE_MODIFY );
            sql.DB.execSQL(S);
        }

        if (intent.getAction ().equals ( sql.SEARCH )) {
            String S = intent.getStringExtra(sql.KEY_VALUE_SEARCH);
            System.out.println("IntentService--> calls search");

            int check=1;
            boolean x=false;
            if (S.length() > 0) {
                Cursor c = sql.DB.rawQuery("select * from emp ;", null);
                if (c.getCount() == 0) {
                    check=0;
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (c.moveToNext()) {
                    int b=c.getInt(1);
                    if ( b== Integer.parseInt(S)) {
                        x=true;
                        buffer.append("Name :" + c.getString(0) + "\n");
                        buffer.append("ID :" + b + "\n");
                        buffer.append("Sex :" + c.getString(2) + "\n");
                        buffer.append("Salary :" + c.getInt(3) + "\n");
                        buffer.append("TotalSalary :" + c.getInt(4) + "\n");
                        buffer.append("Rate :" + c.getInt(5) + "\n");
                    }
                }


                Intent in = new Intent();
                in.setAction(sql.KEY_VALUE_STRING);
                in.putExtra("check",x);
                in.putExtra("checkint", check);
                in.putExtra(sql.KEY_VALUE_STRING_BUFFER, buffer.toString());
                sendBroadcast(in);
                x=false;

            }


        }




        if (intent.getAction ().equals ( sql.KEY_VALUE_SHOW )){
            int check1=0;
            Cursor res=sql.DB.rawQuery("select * from emp ;",null);
            if (res.getCount() == 0) {
                check1=0;
                Intent in = new Intent();
                in.setAction(sql.KEY_VALUE_STRING1);
                in.putExtra("check1",check1);
                sendBroadcast(in);
                return;
            }
            StringBuffer BUFF = new StringBuffer();
            BUFF.append("\n");
            while (res.moveToNext()) {
                BUFF.append("Name :" + res.getString(0)+"\n");
                BUFF.append("ID :" + res.getInt(1) +"\n");
                BUFF.append("Sex :" + res.getString(2)+"\n");
                BUFF.append("Salary :"+res.getInt(3) + "\n");
                BUFF.append("TotalSalary :"+res.getInt(4) +"\n");
                BUFF.append("Rate :"+res.getInt(5) +"\n"+ "--------------------------------------------------\n");
                check1=1;
            }



            Intent in = new Intent();
            in.setAction(sql.KEY_VALUE_STRING1);
            in.putExtra("check1",check1);
            in.putExtra(sql.KEY_VALUE_STRING_BUFFER1, BUFF.toString());
            sendBroadcast(in);


        }

        if (intent.getAction ().equals ( sql.OPEN )){
            System.out.println("open page:)");
        }


    }

}





