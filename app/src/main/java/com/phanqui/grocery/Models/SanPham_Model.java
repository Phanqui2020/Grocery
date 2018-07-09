package com.phanqui.grocery.Models;

import java.io.Serializable;

public class SanPham_Model implements Serializable{
    public int Id;
    public String Title;
    public int IdLoaiSanPham;
    public int Quantity;
    public int Price;
    public String ImgUrl;
    public int Status;
    public String NhaSX;
    public int SoLuotMua;
    public int PriceSave;

    public SanPham_Model(int id, String title, int idLoaiSanPham, int quantity, int price, String imgUrl, int status, String nhaSX, int soLuotMua, int priceSave) {
        Id = id;
        Title = title;
        IdLoaiSanPham = idLoaiSanPham;
        Quantity = quantity;
        Price = price;
        ImgUrl = imgUrl;
        Status = status;
        NhaSX = nhaSX;
        SoLuotMua = soLuotMua;
        PriceSave = priceSave;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIdLoaiSanPham() {
        return IdLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        IdLoaiSanPham = idLoaiSanPham;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status  = status;
    }

    public String getNhaSX() {
        return NhaSX;
    }

    public void setNhaSX(String nhaSX) {
        NhaSX = nhaSX;
    }

    public int getSoLuotMua() {
        return SoLuotMua;
    }

    public void setSoLuotMua(int soLuotMua) {
        SoLuotMua = soLuotMua;
    }

    public int getPriceSave() {
        return PriceSave;
    }

    public void setPriceSave(int priceSave) {
        PriceSave = priceSave;
    }
}
