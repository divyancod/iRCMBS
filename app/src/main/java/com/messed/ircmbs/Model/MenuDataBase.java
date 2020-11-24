package com.messed.ircmbs.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.messed.ircmbs.Model.MenuList;

import java.util.ArrayList;
import java.util.List;
/*
 * Created By MrMessedUp(Divyanshu Verma)
 * */
public class MenuDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "MyMenu";
    private static final String TABLE_NAME = "items";

    public MenuDataBase(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);//--database name = MENU
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+ TABLE_NAME +"(Sno int(10),MenuItem varchar(100),Price varchar(50),Rating varchar(10),time varchar(10),curravail varchar(10),itemdesc varchar(200))";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public void addMenu(List<MenuList> menuLists)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<menuLists.size();i++)
        {
            values.put("Sno",i);
            values.put("MenuItem",menuLists.get(i).getItems());
            values.put("Price",menuLists.get(i).getPrice());
            values.put("Rating",menuLists.get(i).getRating());
            values.put("time",menuLists.get(i).getItemtime());
            values.put("curravail",menuLists.get(i).getCurravail());
            values.put("itemdesc",menuLists.get(i).getItemdesc());
            db.insert(TABLE_NAME,"0",values);
        }
        db.close();
    }
    public List<MenuList> getItem(ArrayList<String> items)
    {
        List<MenuList> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String query="";
        for(int i=0;i<items.size();i++)
        {
            query="select * from "+TABLE_NAME+" where Sno='"+items.get(i)+"'";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    MenuList nob=new MenuList();
                    nob.setItems(cursor.getString(1));
                    nob.setPrice(cursor.getString(2));
                    data.add(nob);
                } while (cursor.moveToNext());
            }
        }
        return data;
    }
    public List<MenuList> getAllItems()
    {
        List<MenuList> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String query="select * from "+TABLE_NAME;
        cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do {
                {
                    MenuList nob=new MenuList();
                    nob.setItems(cursor.getString(1));
                    nob.setPrice(cursor.getString(2));
                    nob.setRating(cursor.getString(3));
                    nob.setItemtime(cursor.getString(4));
                    nob.setCurravail(cursor.getString(5));
                    nob.setItemdesc(cursor.getString(6));
                    data.add(nob);
                }
            }while(cursor.moveToNext());
        }
        db.close();
        return data;
    }
    public void deleteTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
        db.close();
    }
}
