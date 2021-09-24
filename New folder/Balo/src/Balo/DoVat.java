/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Balo;

/**
 *
 * @author Lenovo
 */
public class DoVat {
    private String ten;
    private double kluong, gtri, dgia;
    private int pan, stt, sluong, id;
    
    public DoVat() {
        ten = new String();
        kluong = 1.0;
        gtri = 1.0;
        dgia = 1.0;
        pan = 1;
        stt = 1;
        sluong = 1;
        id = 0;
    }
    public DoVat(String t, double kl, double gt, double dg, int pa, int sott, int sl, int _id) {
        ten = new String("t");
        kluong = kl;
        gtri = gt;
        dgia = dg;
        pan = pa;
        stt = sott;
        sluong = sl;
        id = _id;
    }
    public DoVat(DoVat dv) {
        ten = new String(dv.ten);
        kluong = dv.kluong;
        gtri = dv.gtri;
        dgia = dv.dgia;
        pan = dv.pan;
        stt = dv.stt;
        sluong = dv.sluong;
        id = dv.id;
    }
    
    public String getTen() {
       return ten;
    }
    public int getSoLuong() {
        return sluong;
    }
    
    public double getKLuong() {
        return kluong;
    }
    
    public double getGTri() {
        return gtri;
    }
    
    public double getDGia() {
        return dgia;
    }
        
    public int getPAn() {
        return pan;
    }
    public int getSTT() {
        return stt;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int _id) {
        id = _id;
    }
    public void setSoLuong(int sl) {
        sluong = sl;
    }
    
    public void setTen(String t) {
       ten = t;
    }
    
    public void setKLuong(double kl) {
        kluong = kl;
    }
    
    public void setGTri(double gt) {
        gtri = gt;
    }
    
    public void setDGia(double dg) {
        dgia = dg;
    }
        
    public void setPAn(int pa) {
        pan = pa;
    }
    public void setSTT(int STT) {
        stt = STT;
    }
    
    public String inDoVat() {
        return ten + "; " + gtri + "; " + kluong + "; " + gtri + "; " + dgia + "; PA=" + pan + "\n";
    }
    
}
