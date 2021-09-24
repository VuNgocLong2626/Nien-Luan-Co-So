/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Balo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 * 
 * 
 */
public class PhuongPhapGiai extends javax.swing.JFrame {

    private String tenThuatToan;
    static String ttt = new String();
    static Balo balo;
    private DefaultTableModel model;
    private double tongKLBLChua, tongGTBLChua, khoiLuongConLai;
    private ArrayList <DoVat> listDoVat;
    private Connection conn;
    
    private String tenTTSS = new String();
    
    public PhuongPhapGiai() {
        initComponents();
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("PHƯƠNG PHÁP GIẢI");
        
        KetNoiCSDL ketNoiCSDL = new KetNoiCSDL();
        conn = ketNoiCSDL.conn;
        
        ganGTriThuatToan();
    }

    public void ganGTriThuatToan() {
        jRThamAn.setActionCommand("thaman");
        jRNhanhCan.setActionCommand("nhanhcan");
        jRQuyHoachDong.setActionCommand("quyhoachdong");
    }
    
    public void chonThuatToan() {
        if (jRThamAn.isSelected()) {
            tenThuatToan = new String(jRThamAn.getActionCommand());
        } else if (jRNhanhCan.isSelected()) {
            tenThuatToan = new String(jRNhanhCan.getActionCommand());
        } else {
            tenThuatToan = new String(jRQuyHoachDong.getActionCommand());
        }
    }
    public void showThongTinBalo() {
        jLMaSoBL.setText(balo.getMaSo());
        jLTongKhoiLuong.setText(""+ balo.getKhoiLuongBL());
        jLLoai.setText(""+balo.getLoai());
        jLTongSoLuongDV.setText(""+balo.getSoLuong());
    }
    
    public void showDVSauKhiTinh(Balo bl) {
        bl = bl.sapXepSTT(bl);
        ArrayList<DoVat> listDV = bl.getListDV();
        model = (DefaultTableModel) jTListDoVat.getModel();
        model.setRowCount(0);
        int i;
        for (i = 0; i < listDV.size(); i++) {
            if (bl.getLoai() == 1) {
                model.addRow(new Object[] {
                    listDV.get(i).getSTT(), listDV.get(i).getTen(), listDV.get(i).getKLuong(), listDV.get(i).getGTri(), "-",
                    listDV.get(i).getDGia(), listDV.get(i).getPAn()
                });
            } else {
                model.addRow(new Object[] {
                    listDV.get(i).getSTT(), listDV.get(i).getTen(), listDV.get(i).getKLuong(), listDV.get(i).getGTri(), listDV.get(i).getSoLuong(),
                    listDV.get(i).getDGia(), listDV.get(i).getPAn()
                });
            }
            tongKLBLChua += listDV.get(i).getKLuong() * listDV.get(i).getPAn();
            tongGTBLChua += listDV.get(i).getGTri() * listDV.get(i).getPAn();
        }
       
        khoiLuongConLai = bl.getKhoiLuongBL() - tongKLBLChua;
        jLTongKLuongBaloChua.setText("" + tongKLBLChua);
        jLTongGTriChua.setText("" + tongGTBLChua);
        jLTongKLuongConLai.setText("" + khoiLuongConLai);
        
    }
    
    private int demDV;
    public void inDV(DoVat dv) {
        model = (DefaultTableModel) jTListDoVat.getModel();
        if (balo.getLoai() == 1) {
            model.addRow(new Object[] {
                demDV++ , dv.getTen(), dv.getKLuong(), dv.getGTri(), "-"
            });
        } else {
            model.addRow(new Object[] {
                demDV++ , dv.getTen(), dv.getKLuong(), dv.getGTri(), dv.getSoLuong()
            });
        }
    }
    
    public void showListDVFromCSDL(int _idBL) {
        listDoVat = new ArrayList<>();
        model = (DefaultTableModel) jTListDoVat.getModel();
        model.setRowCount(0);
        demDV = 1;
        listDoVat = new ArrayList<>();
        try {
            String sql = new String("select * from dovat where idbalo=?");
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, _idBL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DoVat dv = new DoVat();
               
                dv.setId(rs.getInt("id"));
                dv.setSTT(rs.getInt("stt"));
                dv.setTen(rs.getString("tendovat"));
                dv.setKLuong(rs.getDouble("khoiluong"));
                dv.setGTri(rs.getDouble("giatri"));
                dv.setSoLuong(rs.getInt("soluong"));
               
                listDoVat.add(dv);
                inDV(dv);
            }
        } catch (Exception e) {
            System.out.print("Lỗi hàm showListDVFromCSDL: " + e + "\n");
        }
    }
    private int flag = 0;
    
    public void giaiBalo() {
        chonThuatToan();
        if (jRThamAn.isSelected()) {
            ttt = new String("Tham ăn");
        } else if (jRNhanhCan.isSelected()) {
            ttt = new String("Nhánh cận");
        } else {
            ttt = new String("Quy hoạch động");
        }
        
        Balo baloTemp = new Balo(balo);
        if (tenThuatToan.equals("thaman")) {
            baloTemp.thamAn(baloTemp);
        } else if (tenThuatToan.equals("nhanhcan")) {
            baloTemp.ttNhanhCan(baloTemp);
        } else {
            baloTemp.quyHoachDong(baloTemp);
        }
        showDVSauKhiTinh(baloTemp);
        
    }
    
    public void ghiDuLieu(Balo bl, String duongdan) {
        try {
            File tenfile = new File(duongdan);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tenfile), "UTF8"));
            ArrayList <DoVat> dsDV = new ArrayList<>(bl.getListDV());
            if (flag == 0) {
                out.append("Thuật toán: " +  ttt).append("\r\n");
            } else {
                out.append("Thuật toán tối ưu: " +  tenTTSS).append("\r\n");
            }
            out.append("Loại balo: " + bl.getLoai()).append("\r\n");
            out.append("Khối lượng balo: " + bl.getKhoiLuongBL()).append("\r\n");
            
            if (bl.getLoai() == 1) {
                out.append("STT \t Khối lượng \t Giá trị \t Đơn giá \t Phương án \t  Tên").append("\r\n");
                for (DoVat dv : dsDV) {
                    double dgia = (double)Math.round(dv.getDGia()*1000)/1000;
                    out.append(dv.getSTT()  + " \t  " + dv.getKLuong() + " \t\t " + dv.getGTri() + " \t \t   " + dgia + " \t \t " + dv.getPAn() + "\t\t " + dv.getTen()).append("\r\n");
                }
            } else {
                out.append("STT \t Khối lượng \t Giá trị \t Đơn giá \t Số lượng \t Phương án \t  Tên").append("\r\n");
                for (DoVat dv : dsDV) {
                    double dgia = (double)Math.round(dv.getDGia()*1000)/1000;
                    out.append(dv.getSTT()  + " \t  " + dv.getKLuong() + " \t\t " + dv.getGTri() + " \t \t  " + dgia + " \t\t " + dv.getSoLuong() + " \t\t  " + dv.getPAn() + "\t\t " + dv.getTen()).append("\r\n");
                }
            }
            out.flush();
            out.close();

            JOptionPane.showMessageDialog(this, "Xuất file kết quả thành công!!!");
            this.setVisible(false);

        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(this, "Sorry! Đã xảy ra lỗi! Vui lòng quay lại sau!");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Sorry! Đã xảy ra lỗi! Vui lòng quay lại sau!");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sorry! Đã xảy ra lỗi! Vui lòng quay lại sau!");
            System.out.println(e.getMessage());
        }
    }
    
    public double max(double a, double b, double c) {
        if (a >= b) {
            if (a > c) {
                return a;
            } else {
                return c;
            }
        } else {
            if (b > c) {
                return b;
            } else {
                return c;
            }
        }
    }
    
    public void soSanhChonTT() {
        Balo BL_TA = new Balo();
        double TGT_TA = 0, TGT_NC = 0, TGT_QHD = 0;
        BL_TA = balo.thamAn(balo);
        for (int i = 0; i < balo.getSoLuong(); i++) {
            TGT_TA += BL_TA.getListDV().get(i).getPAn() * BL_TA.getListDV().get(i).getGTri();
        }
        
        Balo BL_NC = new Balo();
        BL_NC = balo.ttNhanhCan(balo);
        for (int i = 0; i < balo.getSoLuong(); i++) {
            TGT_NC += BL_NC.getListDV().get(i).getPAn() * BL_NC.getListDV().get(i).getGTri();
        }
        
        Balo BL_QHD = new Balo();
        BL_QHD = balo.quyHoachDong(balo);
        for (int i = 0; i < balo.getSoLuong(); i++) {
            TGT_QHD += BL_QHD.getListDV().get(i).getPAn() * BL_QHD.getListDV().get(i).getGTri();
        }
        
        System.out.print("\n\nTGT TA = " + TGT_TA);
        System.out.print("\n\nTGT NC = " + TGT_NC);
        System.out.print("\n\nTGT NC = " + TGT_QHD);
        
        double kq = max(TGT_TA, TGT_NC, TGT_QHD);
        if (kq == TGT_TA) {
            BL_TA = BL_TA.thamAn(balo);
            showDVSauKhiTinh(BL_TA);
            tenTTSS = new String("Tham ăn");
        } else if (kq == TGT_NC) {
            BL_NC = BL_NC.ttNhanhCan(balo);
            showDVSauKhiTinh(BL_NC);
            tenTTSS = new String("Nhánh cận");
        } else {
            BL_QHD = BL_QHD.quyHoachDong(balo);
            showDVSauKhiTinh(BL_QHD);
            tenTTSS = new String("Quy hoạch động");
        }
        tenTTSS = new String("");
        if (kq == TGT_TA) {
            tenTTSS = "Tham ăn";
        }
        
        if (kq == TGT_NC) {
            tenTTSS = tenTTSS + " Nhánh cận";
        }
        
        if (kq == TGT_QHD) {
            tenTTSS = tenTTSS + " Quy hoạch động";
        }
        jLThuatToanToiUu.setText("Thuật toán tối ưu: " + tenTTSS);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLTongSoLuongDV = new javax.swing.JLabel();
        jLTongKhoiLuong = new javax.swing.JLabel();
        jLMaSoBL = new javax.swing.JLabel();
        jLLoai = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTListDoVat = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBTrangChu = new javax.swing.JButton();
        jBThoat = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRThamAn = new javax.swing.JRadioButton();
        jRNhanhCan = new javax.swing.JRadioButton();
        jRQuyHoachDong = new javax.swing.JRadioButton();
        jBChon = new javax.swing.JButton();
        jBXuatFile = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLTongKLuongConLai = new javax.swing.JLabel();
        jLTongGTriChua = new javax.swing.JLabel();
        jLTongKLuongBaloChua = new javax.swing.JLabel();
        jBTTToiUu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLThuatToanToiUu = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("BALO");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(640, 130, 41, 19);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Loại:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(640, 220, 29, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Mã số:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(640, 160, 40, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tổng khối lượng:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(640, 190, 105, 17);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Tổng số đồ vật:");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(640, 250, 99, 17);

        jLTongSoLuongDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLTongSoLuongDV.setText("so do vat");
        jPanel2.add(jLTongSoLuongDV);
        jLTongSoLuongDV.setBounds(780, 250, 170, 17);

        jLTongKhoiLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLTongKhoiLuong.setText("khoi luong");
        jPanel2.add(jLTongKhoiLuong);
        jLTongKhoiLuong.setBounds(780, 190, 170, 17);

        jLMaSoBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLMaSoBL.setText("maso");
        jPanel2.add(jLMaSoBL);
        jLMaSoBL.setBounds(780, 160, 190, 17);

        jLLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLLoai.setText("loai");
        jPanel2.add(jLLoai);
        jLLoai.setBounds(780, 220, 170, 17);

        jTListDoVat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTListDoVat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên đồ vật", "Khối lượng", "Giá trị", "Số lượng", "Đơn Giá", "Phương án"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTListDoVat);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 350, 960, 150);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("THUẬT TOÁN VÀ GIẢI BÀI TOÁN");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(260, 50, 520, 37);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("KẾT QUẢ");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 320, 210, 22);

        jBTrangChu.setBackground(new java.awt.Color(155, 151, 84));
        jBTrangChu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBTrangChu.setText("TRANG CHỦ");
        jBTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTrangChuActionPerformed(evt);
            }
        });
        jPanel2.add(jBTrangChu);
        jBTrangChu.setBounds(430, 220, 110, 25);

        jBThoat.setBackground(new java.awt.Color(155, 151, 84));
        jBThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBThoat.setText("THOÁT");
        jBThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBThoatActionPerformed(evt);
            }
        });
        jPanel2.add(jBThoat);
        jBThoat.setBounds(430, 260, 110, 25);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/logo_DHCT.png"))); // NOI18N
        jPanel2.add(logo);
        logo.setBounds(30, 20, 100, 100);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("CHỌN THUẬT TOÁN");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(170, 130, 200, 19);

        buttonGroup1.add(jRThamAn);
        jRThamAn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRThamAn.setSelected(true);
        jRThamAn.setText("Tham ăn");
        jPanel2.add(jRThamAn);
        jRThamAn.setBounds(170, 170, 83, 25);

        buttonGroup1.add(jRNhanhCan);
        jRNhanhCan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRNhanhCan.setText("Nhánh cận");
        jPanel2.add(jRNhanhCan);
        jRNhanhCan.setBounds(270, 170, 95, 25);

        buttonGroup1.add(jRQuyHoachDong);
        jRQuyHoachDong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRQuyHoachDong.setText("Quy hoạch động");
        jPanel2.add(jRQuyHoachDong);
        jRQuyHoachDong.setBounds(380, 170, 133, 25);

        jBChon.setBackground(new java.awt.Color(155, 151, 84));
        jBChon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBChon.setText("CHỌN");
        jBChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChonActionPerformed(evt);
            }
        });
        jPanel2.add(jBChon);
        jBChon.setBounds(170, 220, 110, 25);

        jBXuatFile.setBackground(new java.awt.Color(155, 151, 84));
        jBXuatFile.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBXuatFile.setText("XUẤT FILE");
        jBXuatFile.setEnabled(false);
        jBXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXuatFileActionPerformed(evt);
            }
        });
        jPanel2.add(jBXuatFile);
        jBXuatFile.setBounds(300, 220, 110, 25);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tổng khối lượng đồ vật balo chứa:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 510, 213, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tổng giá trị đồ vật:");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(20, 540, 118, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Khối lượng đồ vật còn lại:");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(20, 570, 156, 30);

        jLTongKLuongConLai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(jLTongKLuongConLai);
        jLTongKLuongConLai.setBounds(290, 570, 460, 30);

        jLTongGTriChua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(jLTongGTriChua);
        jLTongGTriChua.setBounds(290, 540, 470, 30);

        jLTongKLuongBaloChua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(jLTongKLuongBaloChua);
        jLTongKLuongBaloChua.setBounds(290, 510, 490, 30);

        jBTTToiUu.setBackground(new java.awt.Color(155, 151, 84));
        jBTTToiUu.setText("CHỌN THUẬT TOÁN TỐI ƯU");
        jBTTToiUu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTTToiUuActionPerformed(evt);
            }
        });
        jPanel2.add(jBTTToiUu);
        jBTTToiUu.setBounds(170, 260, 240, 25);
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(-80, 310, 1140, 2);

        jLThuatToanToiUu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(jLThuatToanToiUu);
        jLThuatToanToiUu.setBounds(140, 320, 360, 30);

        jPanel1.setBackground(new java.awt.Color(160, 160, 160));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel1);
        jPanel1.setBounds(550, 110, 1, 200);

        background.setBackground(new java.awt.Color(153, 0, 102));
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/background.png"))); // NOI18N
        background.setMaximumSize(new java.awt.Dimension(1300, 650));
        background.setMinimumSize(new java.awt.Dimension(1300, 650));
        jPanel2.add(background);
        background.setBounds(0, -20, 1000, 730);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTrangChuActionPerformed
        GiaoDienChinh gd = new GiaoDienChinh();
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        dsBL.bienDSBL = 0;
        
        this.setVisible(false);
        gd.setVisible(true);
        
    }//GEN-LAST:event_jBTrangChuActionPerformed

    private void jBThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBThoatActionPerformed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jBThoatActionPerformed

    private void jBChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChonActionPerformed
       flag = 0;
        giaiBalo();
        jScrollPane1.setVisible(true);
        
        tongKLBLChua = 0;
        tongGTBLChua = 0; 
        khoiLuongConLai = 0;
        jBXuatFile.setEnabled(true);
        balo.inDV();
        jLThuatToanToiUu.setText("Thuật toán được chọn: " + ttt);
        
    }//GEN-LAST:event_jBChonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        if (dsBL.bienDSBL == 1) {
            balo = new Balo(dsBL.balo); 
        } else {
            DanhSachThemDoVat nhapDL = new DanhSachThemDoVat();
            balo = new Balo(nhapDL.balo);
        }
        showListDVFromCSDL(balo.getId());
        balo.setListDV(listDoVat);
        balo.setSoLuong(listDoVat.size());
        showThongTinBalo();
    }//GEN-LAST:event_formWindowOpened

    private void jBXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXuatFileActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showSaveDialog(null);
        String tenfile = new String(jFileChooser.getSelectedFile().getPath());
        
        ghiDuLieu(balo, tenfile);
        this.setVisible(true);
    }//GEN-LAST:event_jBXuatFileActionPerformed

    private void jBTTToiUuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTTToiUuActionPerformed
        flag = 1;
        soSanhChonTT();
        tongKLBLChua = 0;
        tongGTBLChua = 0; 
        khoiLuongConLai = 0;
        jBXuatFile.setEnabled(true);
       
    }//GEN-LAST:event_jBTTToiUuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PhuongPhapGiai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhuongPhapGiai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhuongPhapGiai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhuongPhapGiai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhuongPhapGiai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBChon;
    private javax.swing.JButton jBTTToiUu;
    private javax.swing.JButton jBThoat;
    private javax.swing.JButton jBTrangChu;
    private javax.swing.JButton jBXuatFile;
    private javax.swing.JLabel jLLoai;
    private javax.swing.JLabel jLMaSoBL;
    private javax.swing.JLabel jLThuatToanToiUu;
    private javax.swing.JLabel jLTongGTriChua;
    private javax.swing.JLabel jLTongKLuongBaloChua;
    private javax.swing.JLabel jLTongKLuongConLai;
    private javax.swing.JLabel jLTongKhoiLuong;
    private javax.swing.JLabel jLTongSoLuongDV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRNhanhCan;
    private javax.swing.JRadioButton jRQuyHoachDong;
    private javax.swing.JRadioButton jRThamAn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTListDoVat;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
