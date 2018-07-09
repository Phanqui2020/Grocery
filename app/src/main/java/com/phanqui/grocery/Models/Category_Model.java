package com.phanqui.grocery.Models;

/**
 * Created by falcon on 07/03/2018.
 */

public class Category_Model {
    public int Id;
    public String TenLoaiSanPham;

    public Category_Model(int id, String tenLoaiSanPham) {
        Id = id;
        TenLoaiSanPham = tenLoaiSanPham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaiSanPham() {
        return TenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        TenLoaiSanPham = tenLoaiSanPham;
    }
}
