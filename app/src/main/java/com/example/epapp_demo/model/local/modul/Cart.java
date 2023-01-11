package com.example.epapp_demo.model.local.modul;

import java.io.Serializable;

public class Cart implements Serializable {
    private int gioHangId;
    private String DonHangID;
    private String MonAnID;
//    private String NameMonAn;
    private int SoLuong;
    private String UserID;

//    private int Gia;
//    private String HinhAnhGioHang;

    public int getGioHangId() {
        return gioHangId;
    }

    public void setGioHangId(int gioHangId) {
        this.gioHangId = gioHangId;
    }


    public String getMonAnID() {
        return MonAnID;
    }

    public void setMonAnID(String monAnID) {
        MonAnID = monAnID;
    }

//    public String getNameMonAn() {
//        return NameMonAn;
//    }
//
//    public void setNameMonAn(String nameMonAn) {
//        NameMonAn = nameMonAn;
//    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

//    public int getGia() {
//        return Gia;
//    }
//
//    public void setGia(int gia) {
//        Gia = gia;
//    }
//
//    public String getHinhAnhGioHang() {
//        return HinhAnhGioHang;
//    }
//
//    public void setHinhAnhGioHang(String hinhAnhGioHang) {
//        HinhAnhGioHang = hinhAnhGioHang;
//    }


    public String getDonHangID() {
        return DonHangID;
    }

    public void setDonHangID(String donHangID) {
        DonHangID = donHangID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Cart() {
    }

    public Cart(String DonHangID, String monAnID, int soLuong,String userID) {
        this.DonHangID = DonHangID;
        MonAnID = monAnID;
        SoLuong = soLuong;
        UserID = userID;
    }

    public Cart(int gioHangId, String DonHangID, String monAnID, int soLuong, String userID) {
        this.gioHangId = gioHangId;
        this.DonHangID = DonHangID;
        MonAnID = monAnID;
        SoLuong = soLuong;
        UserID = userID;
    }

}
