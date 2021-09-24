/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Balo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Lenovo
 */
public class Balo {
    private ArrayList<DoVat> listDoVat;
    private int sluong, loai, id;
    private double kluong;
    private String maSo;
    public Balo() {
        sluong = 1;
        loai = 1;
        kluong = 0.0;
        maSo = new String();
        listDoVat = new ArrayList<>();
        id = 0;
    }
   
    
    public Balo(ArrayList<DoVat> listDV, int sl, String tenN, String mS, int l, double kl, int _id) {
        listDoVat = new ArrayList<>(listDV);
        sluong = sl;
        loai = l;
        kluong = kl;
        maSo = new String(mS);
        id = _id;
    } 

    public Balo(Balo bl) {
        sluong = bl.sluong;
        loai = bl.loai;
        kluong = bl.kluong;
        maSo = new String(bl.maSo);
        listDoVat = new ArrayList<>();
        
        listDoVat = (ArrayList<DoVat>)bl.listDoVat.clone();
        id = bl.id;
    }

    public int getId() {
        return id;
    }
    public int getSoLuong() {
        return sluong;
    }
    
    public String getMaSo() {
        return maSo;
    }
    
    public int getLoai() {
        return loai;
    }
    
    public double getKhoiLuongBL() {
        return kluong;
    }
    
    public ArrayList<DoVat> getListDV() {
        return listDoVat;
    }
    
    public void setListDV(ArrayList<DoVat> listDV) {
        listDoVat = new ArrayList();
        listDoVat = (ArrayList<DoVat>)listDV.clone();
    }
   
    public void setId(int _id) {
        id = _id;
    }
    public void setSoLuong(int sl) {
        sluong = sl;
    }
    
    public void setMaSo(String ms) {
        maSo = ms;
    }
    
    public void setLoai(int l) {
        loai = l;
    }
    
    public void setKhoiLuongBL(double kl) {
        kluong = kl;
    }
   
    
    public Balo tinhDGia(Balo bl) {
        int i;
        for (i = 0; i < bl.getSoLuong(); i++) {
            bl.getListDV().get(i).setDGia(bl.getListDV().get(i).getGTri() / bl.getListDV().get(i).getKLuong());
        }
        return bl;
    }
    
    public Balo sapXepDG(Balo bl) { // phương pháp nổi bọt
        bl = tinhDGia(bl);
        Collections.sort(bl.getListDV(), new Comparator<DoVat>() {
            public int compare(DoVat o1, DoVat o2) {
                return Double.valueOf(o2.getDGia()).compareTo(o1.getDGia());
            }
        });
        
        return bl;
    }
    
    public int min (int a, int b) {
        if (a < b) {
            return a;
        } 
        return b;
    } 
    
    public Balo thamAn(Balo BL) {
        Balo bl = new Balo(BL);
        sapXepDG(bl);
        double kl = bl.getKhoiLuongBL();
        int i;
        for (i = 0; i < bl.getSoLuong(); i++) {
            if (bl.getLoai() == 1) {
                bl.getListDV().get(i).setPAn((int) (kl / bl.getListDV().get(i).getKLuong()));
            } else {
                int pa = min((int)(kl / bl.getListDV().get(i).getKLuong()), bl.getListDV().get(i).getSoLuong());
                bl.getListDV().get(i).setPAn(pa);
            }
            kl -= bl.getListDV().get(i).getPAn() * bl.getListDV().get(i).getKLuong();
        }
        return bl;
    }
   
    private double tongGTri, giaLonNhatTT, canTren;
    private int x[] ;
    public void taoNutGoc(Balo bl){
	tongGTri = 0.0;
	canTren = bl.getListDV().get(0).getDGia()* bl.getKhoiLuongBL();
	giaLonNhatTT = 0.0;
}
    
    public void CapNhatGiaLNTT(Balo bl) {
        if(tongGTri > giaLonNhatTT){
            giaLonNhatTT = tongGTri;
            int i;
            for(i = 0; i < bl.getSoLuong(); i++){
		bl.getListDV().get(i).setPAn(x[i]);
            }
	}
    }
    
    public void nhanhCan(int i){
        int th = 0;
        
        if (baloNC.getLoai() == 1) {
            th = (int) (kluongNC / baloNC.getListDV().get(i).getKLuong());
        } else {
            th = min((int) (kluongNC / baloNC.getListDV().get(i).getKLuong()), baloNC.getListDV().get(i).getSoLuong());
        }
	int j;
	for(j = th; j >= 0; j--){
            tongGTri += j * baloNC.getListDV().get(i).getGTri();
            kluongNC -= j * baloNC.getListDV().get(i).getKLuong();
            if (i + 1 < baloNC.getSoLuong()) {
                canTren = (double) (tongGTri + baloNC.getListDV().get(i + 1).getDGia() * kluongNC);
            }
            
            if(canTren > giaLonNhatTT){
                x[i] = j;
                if(kluongNC == 0 || i == baloNC.getSoLuong() - 1) // neu trong luong con lai = 0 hoac het do vat
                        CapNhatGiaLNTT(baloNC);
                else
                    nhanhCan(i + 1);
            }
            
            x[i] = 0; // chua co phuong an
            tongGTri -= j * baloNC.getListDV().get(i).getGTri(); // tru lai gia tri da cong vao trong for
            kluongNC += j * baloNC.getListDV().get(i).getKLuong(); // cong lai khoi luong da tru ra
	}
       
    }
    Balo baloNC;
    private double kluongNC;
    public Balo ttNhanhCan(Balo bl) {
        baloNC = new Balo(bl);
        kluongNC = baloNC.getKhoiLuongBL();
        sapXepDG(baloNC);
        taoNutGoc(baloNC);
        x = new int[baloNC.getSoLuong()];
        nhanhCan(0);
        System.out.print(bl.getKhoiLuongBL());
        return baloNC;
    }
    
    private double BangF[][];
    private int BangX[][];
    
    public Balo taoBang(Balo bl){
        int xk, yk = 0, k, Xmax, V, kluongTam = (int)bl.getKhoiLuongBL();
        BangF = new double[kluongTam][kluongTam + 2];
        BangX = new int[kluongTam][kluongTam + 2];
        
        double Fmax, kl = bl.getKhoiLuongBL() - kluongTam;
	for(V = 0; V <= kluongTam; V++){
            double v = V + kl;
            
            if (bl.getLoai() == 1) {
                BangX[0][V] = (int) (v / bl.getListDV().get(0).getKLuong());
            } else {
                BangX[0][V] = min((int) (v / bl.getListDV().get(0).getKLuong()), bl.getListDV().get(0).getSoLuong());
            }
            
            BangF[0][V] = BangX[0][V] * bl.getListDV().get(0).getGTri();

	}
        // từ đồ vật thứ 2
	if (bl.getSoLuong() > 1) {
            for(k = 1; k < bl.getSoLuong(); k++){
                for(V = 0; V <= kluongTam; V++){
                    Fmax = BangF[k - 1][V];
                    Xmax = 0;
                    
                    double v = V;
                    if (bl.getLoai() == 1) {
                        yk = (int) (v / bl.getListDV().get(k).getKLuong());
                    } else {
                        yk = min((int) (v / bl.getListDV().get(k).getKLuong()), bl.getListDV().get(k).getSoLuong());
                    }
                    
                    for(xk = 1; xk <= yk; xk++){
                        if(BangF[k - 1][(int)(v - xk * bl.getListDV().get(k).getKLuong())] + xk * bl.getListDV().get(k).getGTri() > Fmax){
                            Fmax = (BangF[k - 1][(int)(v - xk * bl.getListDV().get(k).getKLuong())] + xk * bl.getListDV().get(k).getGTri());
                            Xmax = xk;
                        }
                    }
                    BangF[k][V] = Fmax;
                    BangX[k][V] = Xmax;
                }
            }
        }
        System.out.print("\n");
        for(int i = 0; i < bl.getSoLuong(); i++) {
            for(int j = 0; j <= (int)bl.getKhoiLuongBL(); j++) {
                System.out.print(BangF[i][j] + "|" + BangX[i][j] + "    ");
            }
            System.out.print("\n");
        }
        
        return bl;
    }
   
    public Balo traBang(Balo bl) {
	int k, V = (int)bl.getKhoiLuongBL();
        double kl = bl.getKhoiLuongBL() - V; 
        try {
            for(k = bl.getSoLuong() - 1; k >= 0; k--) {
                double v = V + kl;
                System.out.print("\nv ban đầu:" + v);
                bl.getListDV().get(k).setPAn(BangX[k][V]);
                System.out.print(", Phương án X[:" + k + ", " + V + "] = "  + BangX[k][V]);
                v = v - BangX[k][V] * bl.getListDV().get(k).getKLuong();
                System.out.print(",v tính lại:" + v);
                if (v > 0) {
                    V = (int)v;
                    kl = v - V;
                } else {
                    V = 0;
                }
                System.out.print("\nGiá trị v = " + v + ", V = " + V + ", kl= " + kl + "\n");
            }
        } catch (Exception e) {
            System.out.print("Lỗi: " + e + "\n");
        }
	return bl;
    }
   
    public Balo quyHoachDong(Balo balo) {
        Balo baloQHD = new Balo(balo);
        baloQHD = tinhDGia(baloQHD);
        baloQHD = taoBang(baloQHD);
        baloQHD = traBang(baloQHD);
        return baloQHD;
    }
    
     public Balo sapXepSTT(Balo bl) { // phương pháp nổi bọt

        Collections.sort(bl.getListDV(), new Comparator<DoVat>() {
            public int compare(DoVat o1, DoVat o2) {
                return Integer.valueOf(o1.getSTT()).compareTo(o2.getSTT());
            }
        });
        
        return bl;
    }
    
    public void inDV() {
        for (int i = 0; i < listDoVat.size(); i++) {
            System.out.print(listDoVat.get(i).inDoVat());
        }
    }
    
}
