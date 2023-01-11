package com.example.epapp_demo.model.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.epapp_demo.model.local.modul.CartDetails;
import com.example.epapp_demo.model.local.modul.Cart;
import com.example.epapp_demo.model.local.modul.Food;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static ArrayList<Cart> giohang = new ArrayList<>();
    SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, "giohang", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table giohang(GioHangId integer primary key autoincrement, " +
                "DonHangId text, MonAnId text, SoLuong integer, UserId text)";
        db.execSQL(sql);
        sql = "create table MonAn(MonAnId text primary key, " +
                "NameMonAn text, GiaMonAn integer, HinhAnhMonAn text, cuaHangID text)";
        db.execSQL(sql);
    }

    public void insertGH(Cart gh) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DonHangId", gh.getDonHangID());
        values.put("MonAnId", gh.getMonAnID());
        values.put("SoLuong", gh.getSoLuong());
        values.put("UserId", gh.getUserID());
        db.insert("giohang", null, values);
    }

    public void insertMonAn(Food food) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MonAnId", food.getMonAnID());
        values.put("NameMonAn", food.getNameMonAn());
        values.put("GiaMonAn", food.getGiaMonAn());
        values.put("HinhAnhMonAn", food.getHinhAnhMonAn());
        values.put("cuaHangID", food.getStoreID());
        db.insert("MonAn", null, values);
    }

    public void delete(String id) {
        db = this.getWritableDatabase();
        db.delete("giohang", "MonAnId=?", new String[]{id});
        db.delete("MonAn", "MonAnId=?", new String[]{id});
    }


    public ArrayList<CartDetails> listGioHang() {
        ArrayList<CartDetails> list = new ArrayList<>();
        db = this.getReadableDatabase();
        String sql = "Select g.GioHangId, g.MonAnId, m.NameMonAn, m.GiaMonAn, g.SoLuong,m.cuaHangID, m.HinhAnhMonAn, g.UserId " +
                " from giohang g inner join MonAn m on g.MonAnId=m.MonAnId";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            CartDetails ctgh = new CartDetails(cs.getInt(0), cs.getString(1), cs.getString(2)
                    , cs.getInt(3), cs.getInt(4),cs.getString(5), cs.getString(6), cs.getString(7));
            list.add(ctgh);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
