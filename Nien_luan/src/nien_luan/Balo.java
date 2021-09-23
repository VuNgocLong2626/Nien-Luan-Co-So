/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nien_luan;

import java.util.Scanner;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 *
 * @author longs
 */
public class Balo {
    private int maso;
    private float khoiluong;
    private int loai;
    private int soluongdv;
    private DoVat[] dv;
    private int max = 99;
    
    public Balo(){
        maso = 0;
        khoiluong = 0;
        loai = 0;
        soluongdv = max;
        dv = new DoVat[soluongdv];
        for (int i = 0; i < soluongdv; i++){
            dv[i] = new DoVat();
        }
    }
    
    public Balo(int maso,float  khoiluong,int loai,int soluongdv, DoVat d[]){
        this.maso = maso;
        this.khoiluong = khoiluong;
        this.loai = loai;
        this.soluongdv = soluongdv;
        dv = new DoVat[soluongdv];
        for (int i = 0 ;i <  soluongdv; i++){
            dv[i] = new DoVat(d[i]);
        }
    }
    
    public int getMaso() {
        return maso;
    }

    public void setMaso(int maso) {
        this.maso = maso;
    }

    public float getKhoiluong() {
        return khoiluong;
    }

    public void setKhoiluong(float khoiluong) {
        this.khoiluong = khoiluong;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
    
    public void input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ma so : ");
        this.maso = sc.nextInt();
        System.out.println("Khoi Luong Balo : ");
        this.khoiluong = sc.nextFloat();
        System.out.println("Loai : ");
        this.loai = sc.nextInt();
        System.out.println("So luong Do Vat : ");
        this.soluongdv = sc.nextInt();
        for(int i = 0; i < this.soluongdv ; i++){
            this.dv[i].inputDV();
        }
    }
    
    public void inputSQL(int maso,float khoiluong,int loai){
        this.maso = maso;
        this.khoiluong = khoiluong;
        this.loai = loai;
    }
    
    public String output(){
        String to = "Ma so : "+this.maso+"  Khoi Luong : "+this.khoiluong+" Loai : "+this.loai+"    So luong dv : "+this.soluongdv+"\n";
        for(int i = 0; i  < this.soluongdv ; i++){
            to += dv[i].toString()+"\n";
        }
        return to;
    }
    
    public void swap(DoVat d1,DoVat d2){
        DoVat temp = new DoVat(d1);
        d1.SetDoVat(d2);
        d2.SetDoVat(temp);
    }
    
    public void TinhDonGia(DoVat DV[],int n){
        for(int i = 0;i < n; i++){
            DV[i].setDongia(DV[i].getGiatri()/DV[i].getKhoiluong());
        }
    }
    
    public void SapXep(DoVat DV[],int n){
        for(int i = 0; i < n;i++){
            for(int j = i+1; j < n; j++ ){
                if(DV[i].getDongia() < DV[j].getDongia())
                    swap(DV[i],DV[j]);
            }
        }
    }
    
    public int min (int a, int b) {
        if (a < b) {
            return a;
        } 
        return b;
    }
   /*Thuật Thoán Tham Ăn*/ 
    public void Greedy(){
        int phuongan;
        for(int i = 0; i < soluongdv; i++){
            if(loai == 1){
                phuongan = (int)(khoiluong/dv[i].getKhoiluong());
            }
            else{
                phuongan = min((int)(khoiluong/dv[i].getKhoiluong()), dv[i].getSoluong());
            }
            dv[i].setPhuongan(phuongan);
            khoiluong -= phuongan*dv[i].getKhoiluong();
        }
    }
    /*Thuật Toán Nhanh Cận*/
    private double tongGT, GiaLNTT, cantren, V/*Trọng lượng còn thể bỏ vào trong ba lô*/;
    private int x[];
    
    public void TaoNutGoc(){
        tongGT = 0.0;
        GiaLNTT = 0.0;
        V = khoiluong;
        cantren = dv[0].getDongia() * this.khoiluong;
        x = new int[soluongdv];
    }
    
    public void CapNhatGLNTT(){
        if(tongGT > GiaLNTT){
            GiaLNTT = tongGT;
            for(int i = 0; i < soluongdv; i++){
                dv[i].setPhuongan(this.x[i]);
            }
        }
    }
    
    public void BranchAndBound(int i){
        System.out.println(dv[i].outputDV());
        int th; //Số Trường Hợp
        if(loai == 1){
            th = (int)(V/dv[i].getKhoiluong());
        }
        else{
            th = min((int)(V/dv[i].getKhoiluong()), dv[i].getSoluong());
        }
        
        for(int j = th; j >= 0; j--){
            tongGT += j * dv[i].getGiatri();
            V -=  j * dv[i].getKhoiluong();
            if(i+1 < soluongdv){
                cantren = (double)(tongGT + dv[i+1].getDongia()*V);
            }
            
            if(cantren > GiaLNTT){
                this.x[i] = j;
                if(V == 0 || i == soluongdv - 1){//Trường Hợp Trọng lượng 0 hoặc Hết đồ Vật
                    CapNhatGLNTT();
                }
                else{
                    BranchAndBound(i+1);
                }
            }
            
            x[i] = 0;
            tongGT -= j * dv[i].getGiatri();
            V +=  j * dv[i].getKhoiluong();
        }
    }
    
    /*Thuật Toán Quy Hoạch Động*/
    private double BangF[][];
    private int BangX[][];
    
    public void TaoBang(){
        int xk, yk, k, V, Xmax, Wnguyen = (int)khoiluong;
        double Fmax, Wle = khoiluong - Wnguyen;
        BangF = new double[soluongdv][Wnguyen+1];
        BangX = new int[soluongdv][Wnguyen+1];
        
        for(V = 0; V <= Wnguyen; V++){//Dòng Đầu tiên của bảng
            double v = V + Wle;
            if(loai == 1){
                BangX[0][V] = (int)(v/dv[0].getKhoiluong());
            }
            else{
                BangX[0][V] = min((int)(v/dv[0].getKhoiluong()), dv[0].getSoluong());
            }
            BangF[0][V] = BangX[0][V] * dv[0].getGiatri();
        }
        
        if(soluongdv > 1){
            for(k = 1; k < soluongdv; k++){
                for(V = 0; V <= Wnguyen; V++){
                    Fmax = BangF[k-1][V];
                    Xmax = 0;
                    double v = V + Wle;
                    
                    if(loai == 1){
                        yk = (int)(v/dv[k].getKhoiluong());
                    }
                    else{
                        yk = min((int)(v/dv[k].getKhoiluong()), dv[k].getSoluong());
                    }
                    
                    for(xk = 1; xk <= yk; xk++){
                        if(BangF[k-1][(int)(v-xk*dv[k].getKhoiluong())] + xk*dv[k].getGiatri() > Fmax){
                            Fmax = BangF[k-1][(int)(v-xk*dv[k].getKhoiluong())] + xk*dv[k].getGiatri();
                            Xmax = xk;
                        }
                    }
                    BangF[k][V] = Fmax;
                    BangX[k][V] = Xmax;
                }
            }
        }
        //In Bảng Ra xem thử
        System.out.println("\n");
        for(int i = 0; i<soluongdv; i++){
            for(int j = 0; j<=Wnguyen; j++){
                System.out.print(BangF[i][j] + "|" + BangX[i][j] + "    ");
            }
            System.out.print("\n");
        }
    }
    
    public void TraBang(){
        int k, Wnguyen = (int)khoiluong;
        double Wle = khoiluong - Wnguyen;
        for(k = soluongdv - 1; k >= 0; k--){
            double v = Wnguyen + Wle;
            dv[k].setPhuongan(BangX[k][Wnguyen]);
            v -= BangX[k][Wnguyen] * dv[k].getKhoiluong();
            if(v > 0){
                Wnguyen = (int)v;
                Wle = v - Wnguyen;
            }
            else{
                Wnguyen = 0;
            }
        }
    }
    
//    public static void main(String[] args) {
//        
////        DoVat[] d = new DoVat[3];
////        d[0] = new DoVat(0,"chao",5,15,0,1,0,0);
////        d[1] = new DoVat(1,"gao",10,10,0,1,1,0);
////        d[2] = new DoVat(2,"com",10,40,0,1,2,0);
////        System.out.println(d[1].toString());
////        System.out.println(d[2].toString());
////        System.out.println("------------------------------");
////        Balo k = new Balo();
////        System.out.println("------------------------------");
////        for(int i = 0 ; i < 3 ;i++){
////            System.out.println(d[i].toString());
////        }
////        System.out.println("--------------- Tinh Don Gia ---------------");
////        k.TinhDonGia(d, 3);
////        for(int i = 0 ; i < 3 ;i++){
////            System.out.println(d[i].toString());
////        }
////        System.out.println("--------------- Sap Xep ---------------");
////        k.SapXep(d, 3);
////        k.Greedy();
////        for(int i = 0 ;i < 3 ;i++){
////            System.out.println(d[i].toString());
////        }
//
//        Balo b = new Balo();
//        b.input();
//        System.out.println("--------------- Tinh Don Gia ---------------");
//        b.TinhDonGia(b.dv, b.soluongdv);
//        System.out.println(b.output());
////        System.out.println("--------------- Sap Xep ---------------");
////        b.SapXep(b.dv, b.soluongdv);
////        System.out.println(b.output());
//        System.out.println("--------------- Phuong an ---------------");
//        b.TaoBang();
//        b.TraBang();
////        b.TaoNutGoc();
////        b.BranchAndBound(0);
//        System.out.println(b.output());
//    }
}
