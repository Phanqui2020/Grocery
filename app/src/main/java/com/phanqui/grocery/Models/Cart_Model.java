package com.phanqui.grocery.Models;

public class Cart_Model {
    public int idsanpham;
    public String tensanpham;
    public long gia;
    public long giaKM;
    public String hinhanh;
    public int soluong;

    public Cart_Model(int idsanpham, String tensanpham, long gia, long giaKM, String hinhanh, int soluong) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.giaKM = giaKM;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public long getGiaKM() {
        return giaKM;
    }

    public void setGiaKM(long giaKM) {
        this.giaKM = giaKM;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}