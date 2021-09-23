/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nien_luan;

import java.util.Scanner;

/**
 *
 * @author longs
 */
public class DoVat {

    private int stt;
    private String ten;
    private float khoiluong;
    private float giatri;
    private float dongia;
    private int soluong;
    private int idDV;
    private int phuongan;
    
    public DoVat(){
        stt = 0;
        ten = new String();
        khoiluong = 0;
        giatri = 0;
        dongia = 0;
        soluong = 0;
        idDV = 0;
        phuongan = 0;
    }
    
    public DoVat(int stt,String ten,float khoiluong,float giatri,float dongia,int soluong,int idDV, int phuongan){
        this.stt = stt;
        this.ten = new String(ten);
        this.khoiluong = khoiluong;
        this.giatri = giatri;
        this.dongia = dongia;
        this.soluong = soluong;
        this.idDV = idDV;
        this.phuongan = phuongan;
    }
    
    public DoVat(DoVat d){
        this.stt = d.getStt();
        this.ten = new String(d.getTen());
        this.khoiluong = d.getKhoiluong();
        this.giatri = d.getGiatri();
        this.dongia = d.getDongia();
        this.soluong = d.getSoluong();
        this.idDV = d.getIdDV();
        this.phuongan = d.getPhuongan();
    }
    
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getKhoiluong() {
        return khoiluong;
    }

    public void setKhoiluong(float khoiluong) {
        this.khoiluong = khoiluong;
    }

    public float getGiatri() {
        return giatri;
    }

    public void setGiatri(float giatri) {
        this.giatri = giatri;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getIdDV() {
        return idDV;
    }

    public void setIdDV(int idDV) {
        this.idDV = idDV;
    }
    
    public int getPhuongan() {
        return phuongan;
    }

    public void setPhuongan(int phuongan) {
        this.phuongan = phuongan;
    }
    
    public void SetDoVat(DoVat d){
        this.stt = d.getStt();
        this.ten = new String(d.getTen());
        this.khoiluong = d.getKhoiluong();
        this.giatri = d.getGiatri();
        this.dongia = d.getDongia();
        this.soluong = d.getSoluong();
        this.idDV = d.getIdDV();
        this.phuongan = d.getPhuongan();
    }
    
    public void inputDV(){
        Scanner sc = new Scanner(System.in);
        System.out.println("STT : ");
        this.stt = sc.nextInt();
        sc.nextLine();
        System.out.println("Ten : ");
        this.ten = sc.nextLine();
        System.out.println("Khoi Luong : ");
        this.khoiluong = sc.nextFloat();
        System.out.println("Gia Tri : ");
        this.giatri = sc.nextFloat();
        System.out.println("So Luong : ");
        this.soluong = sc.nextInt();
        System.out.println("id Do Vat : ");
        this.idDV = sc.nextInt();
    }
    
    public String outputDV(){
        return "STT : "+this.stt+"  Ten : "+this.ten+"  Khoi Luong : "+this.khoiluong+" Gia tri : "+this.giatri+"   Don Gia : "+this.dongia+"   So luong : "+this.soluong+"    Phuong An : "+this.phuongan+" id Do Vat : "+this.idDV;
    }

    @Override
    public String toString() {
        return "STT : "+this.stt+"  Ten : "+this.ten+"  Khoi Luong : "+this.khoiluong+"   Don Gia : "+this.dongia+" Gia tri : "+this.giatri+"   So luong : "+this.soluong+"    Phuong An : "+this.phuongan+" id Do Vat : "+this.idDV;
    }
    
//    public static void main(String[] args) {
//        DoVat d = new DoVat();
//        d.input();
//        System.out.println(d.toString());
//    }
}
