/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Balo;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class DanhSachThemBalo extends javax.swing.JFrame {

    private Connection conn;
    private ArrayList <DoVat> listDoVat;
    private ArrayList <Balo> listBalo;
    static Balo balo;
    private Balo baloTemp;
    private DefaultTableModel modelBalo;
    private int dongChonBL = 0, stt;
    static int bienDSBL = 0;
    
    public DanhSachThemBalo() {
        initComponents();
        this.setSize(1000, 650);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("DANH SÁCH - THÊM BALO");
        
        KetNoiCSDL ketNoiCSDL = new KetNoiCSDL();
        conn = ketNoiCSDL.conn;
        
        jRBalo1.setActionCommand("1");
        jRBalo2.setActionCommand("2");
        jRBalo3.setActionCommand("3");
    }

    public void showBalo(Balo bl) {
        modelBalo = (DefaultTableModel) jTListBalo.getModel();
        modelBalo.addRow(new Object[] {
            stt++ , bl.getMaSo(), bl.getKhoiLuongBL(), bl.getLoai(), bl.getSoLuong()
        });
    }
    
    public void showListBalo() {
        listBalo = new ArrayList<>();
        modelBalo = (DefaultTableModel) jTListBalo.getModel();
        modelBalo.setRowCount(0);
        try {
            Statement s = conn.createStatement();
            String sql = new String("select * from balo");
            ResultSet rs = s.executeQuery(sql);
            stt = 1;
            while (rs.next()) {
                Balo bl = new Balo();
                bl.setId(rs.getInt("id"));
                bl.setMaSo(rs.getString("maso"));
                bl.setKhoiLuongBL(rs.getDouble("tongkhoiluong"));
                bl.setSoLuong(rs.getInt("soluongdv"));
                bl.setLoai(rs.getInt("loai"));
                showBalo(bl);
                listBalo.add(bl);
            }
        } catch (Exception e) {
            System.out.print("Lỗi hàm showListBalo: " + e + "\n");
        }
    }
    
    public void setEnabledRadio(boolean t) {
        jRBalo1.setEnabled(t);
        jRBalo2.setEnabled(t);
        jRBalo3.setEnabled(t);
    }
    
    public void setRong() {
        jTMaSoBL.setText(null);
        jTTongKL.setText(null);
        jLSoLuongDV.setText(null);
        jRBalo1.setSelected(true);
        setEnabledRadio(true);
    }
    
    public void setEnabledButton(boolean t) {
        jBCapNhatBL.setEnabled(t);
        jBXoaBL.setEnabled(t);
        jBDatLai.setEnabled(t);
        jBThemDV.setEnabled(t);
        jBHuy.setEnabled(t);
        jBChonThuatToan.setEnabled(t);
    }
    
    public void kiemTraRong() {
        if (!jTMaSoBL.getText().equals("") && !jTTongKL.getText().equals("")) {
            jBThemBalo.setEnabled(true);
        } else {
            jBThemBalo.setEnabled(false);
        }
    }
    
    public boolean kTraKLuongBLVoiDV(int _idBL) {
        try {
            String sql = new String("select * from dovat where idbalo=?");
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, _idBL);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                if (Double.parseDouble(jTTongKL.getText()) < rs.getDouble("khoiluong")) {
                    JOptionPane.showMessageDialog(this, "Khối lượng balo cập nhật đã lớn hơn khối lượng đồ vật của balo này!");
                    return false;// khối lượng balo đã lớn hơn khối lượng của dồ vật
                }
            }
        } catch (Exception e) {
            System.out.print("Lỗi hàm kTraKLuongBLVoiDV : " + e + "\n");
        }
        return true;
    }
    
    
    public boolean kiemTraMaSoBL(String ms) {
        if (dongChonBL == 1) {
            if (ms.equals(baloTemp.getMaSo())) {
                return true;
            } 
            try {
                String sql1 = new String("select * from balo where maso=?");
                PreparedStatement preStmt1 = conn.prepareStatement(sql1);
                preStmt1.setString(1, ms);
                ResultSet rs = preStmt1.executeQuery();
                while (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Mã số balo đã tồn tại! Vui lòng nhập mã khác!");
                    setEnabledButton(true);
                    return false;
                }
            } catch (Exception ex) {
                System.out.print("Lỗi hàm kiemTraMaSoBL: " + ex);
            }
        }
        return true;
    }
    
    public boolean kiemTraLaSo(String kl) {
        Pattern p = Pattern.compile("^[0-9.]+$");
        if (p.matcher(kl).find()) {
            int dem = 0;
            for (int i = 0; i < kl.length(); i++) {
                if (kl.charAt(i) == '.') {
                    dem++;
                    if (dem > 1) {
                        JOptionPane.showMessageDialog(this, "Khối lượng hoặc giá trị đã dư dấu chấm thập phân! \n");
                        return false;
                    }
                }
            }
            if (Double.parseDouble(kl) == 0) {
                JOptionPane.showMessageDialog(this, "Khối lượng và giá trị phải lớn hơn 0!");
                return false;
            }
            return true;
        }
        JOptionPane.showMessageDialog(this, "Khối lượng và giá trị phải là số! \n");
        return false;
    }
    
    public void layDuLieuBL( ) {

        int l;
        if (jRBalo1.isSelected()) {
            l = Integer.parseInt(jRBalo1.getActionCommand());
        } else if (jRBalo2.isSelected()) {
            l = Integer.parseInt(jRBalo2.getActionCommand());
        } else{
            l = Integer.parseInt(jRBalo3.getActionCommand());
        }
        balo = new Balo();
        balo.setLoai(l);
        balo.setKhoiLuongBL(Double.parseDouble(jTTongKL.getText()));
        balo.setMaSo(jTMaSoBL.getText());
    }
    
    public void updateBalo() {
        try {
            String sqlUpdate = new String("update balo set maso=?, tongkhoiluong=? where id=?");
            PreparedStatement preStmt1 = conn.prepareStatement(sqlUpdate);
            preStmt1.setString(1, xoaKhoangTrangDu(jTMaSoBL.getText()));
            preStmt1.setDouble(2, Double.parseDouble(jTTongKL.getText()));
            preStmt1.setInt(3, baloTemp.getId());

            preStmt1.executeUpdate();

            showListBalo();
            JOptionPane.showMessageDialog(this, "Cập nhật balo thành công!");
            setRong();
        } catch (Exception e) {
            System.out.print("Lỗi hàm updateBalo: " + e + "\n");
        }
    }
    
    public void deleteDoVatCuaBalo(Balo bl) {
        try {
            String sql = new String("delete from dovat where idbalo=?");
            PreparedStatement preStmt = conn.prepareStatement(sql);
            
            preStmt.setInt(1, bl.getId());
            preStmt.executeUpdate();
        } catch (Exception e) {
            System.out.print("Lỗi hàm deleteDoVat: " + e + "\n");
        }
    }
    
    public void deleteBalo() {
        try {
            deleteDoVatCuaBalo(baloTemp);
            
            String sql = new String("delete from balo where id=?");
            PreparedStatement preStmt = conn.prepareStatement(sql);
            
            preStmt.setInt(1, baloTemp.getId());
            preStmt.executeUpdate();
            
            showListBalo();
            
            setRong();
            JOptionPane.showMessageDialog(this, "Xóa balo thành công!");
        } catch (Exception e) {
            System.out.print("Lỗi hàm deleteBalo: " + e + "\n");
        }
    }
    
    public String xoaKhoangTrangDu(String t) {
        return t.trim().replaceAll(" +", " ");
    }
    
    public void insertDataBalo() {
        try {
            String sql1 = new String("INSERT INTO balo (maso, tongkhoiluong, loai, soluongdv) VALUES (?,?,?,?)");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);

            preStmt1.setString(1, xoaKhoangTrangDu(balo.getMaSo()));
            preStmt1.setDouble(2, balo.getKhoiLuongBL());
            preStmt1.setInt(3, balo.getLoai());
            preStmt1.setInt(4, 0);
            preStmt1.execute();
            System.out.print("Insert balo successfully");
        } catch (Exception ex) {
            System.out.print("Lỗi hàm insertDataBalo: " + ex + "\n");
        }
        
    }
     
    public int getIdBL() {
        try {
            String sql = new String("select id from balo where maso=?");
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, balo.getMaSo());
            ResultSet rs = preStmt.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (Exception e) {
            System.out.print("Lỗi hàm getIdBL: " + e + "\n");
        }
        return 0;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBThemDV = new javax.swing.JButton();
        jBChonThuatToan = new javax.swing.JButton();
        jBDatLai = new javax.swing.JButton();
        jBXoaBL = new javax.swing.JButton();
        jBHuy = new javax.swing.JButton();
        jBThemBalo = new javax.swing.JButton();
        jBCapNhatBL = new javax.swing.JButton();
        jScrollPaneListBalo = new javax.swing.JScrollPane();
        jTListBalo = new javax.swing.JTable();
        jBTroVe = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jTMaSoBL = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTTongKL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLSoLuongDV = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jRBalo1 = new javax.swing.JRadioButton();
        jRBalo2 = new javax.swing.JRadioButton();
        jRBalo3 = new javax.swing.JRadioButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("DANH SÁCH - THÊM BALO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(310, 60, 420, 40);

        jBThemDV.setBackground(new java.awt.Color(155, 151, 84));
        jBThemDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBThemDV.setText("THÊM ĐỒ VẬT");
        jBThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBThemDVActionPerformed(evt);
            }
        });
        jPanel1.add(jBThemDV);
        jBThemDV.setBounds(460, 340, 170, 25);

        jBChonThuatToan.setBackground(new java.awt.Color(155, 151, 84));
        jBChonThuatToan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBChonThuatToan.setText("CHỌN THUẬT TOÁN");
        jBChonThuatToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChonThuatToanActionPerformed(evt);
            }
        });
        jPanel1.add(jBChonThuatToan);
        jBChonThuatToan.setBounds(460, 300, 170, 25);

        jBDatLai.setBackground(new java.awt.Color(155, 151, 84));
        jBDatLai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBDatLai.setText("ĐẶT LẠI");
        jBDatLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDatLaiActionPerformed(evt);
            }
        });
        jPanel1.add(jBDatLai);
        jBDatLai.setBounds(340, 340, 110, 25);

        jBXoaBL.setBackground(new java.awt.Color(155, 151, 84));
        jBXoaBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBXoaBL.setText("XÓA");
        jBXoaBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXoaBLActionPerformed(evt);
            }
        });
        jPanel1.add(jBXoaBL);
        jBXoaBL.setBounds(340, 300, 110, 25);

        jBHuy.setBackground(new java.awt.Color(155, 151, 84));
        jBHuy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBHuy.setText("HỦY");
        jBHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHuyActionPerformed(evt);
            }
        });
        jPanel1.add(jBHuy);
        jBHuy.setBounds(640, 300, 110, 25);

        jBThemBalo.setBackground(new java.awt.Color(155, 151, 84));
        jBThemBalo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBThemBalo.setText("THÊM BALO");
        jBThemBalo.setEnabled(false);
        jBThemBalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBThemBaloActionPerformed(evt);
            }
        });
        jPanel1.add(jBThemBalo);
        jBThemBalo.setBounds(220, 340, 110, 25);

        jBCapNhatBL.setBackground(new java.awt.Color(155, 151, 84));
        jBCapNhatBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBCapNhatBL.setText("CẬP NHẬT");
        jBCapNhatBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCapNhatBLActionPerformed(evt);
            }
        });
        jPanel1.add(jBCapNhatBL);
        jBCapNhatBL.setBounds(220, 300, 110, 25);

        jTListBalo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTListBalo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MÃ SỐ", "KHỐI LƯỢNG", "LOẠI", "SỐ ĐỒ VẬT"
            }
        ));
        jTListBalo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTListBaloMouseClicked(evt);
            }
        });
        jScrollPaneListBalo.setViewportView(jTListBalo);

        jPanel1.add(jScrollPaneListBalo);
        jScrollPaneListBalo.setBounds(10, 440, 960, 150);

        jBTroVe.setBackground(new java.awt.Color(155, 151, 84));
        jBTroVe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBTroVe.setText("TRỞ VỀ");
        jBTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTroVeActionPerformed(evt);
            }
        });
        jPanel1.add(jBTroVe);
        jBTroVe.setBounds(640, 340, 110, 25);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/logo_DHCT.png"))); // NOI18N
        jPanel1.add(logo);
        logo.setBounds(30, 20, 100, 110);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(-20, 390, 1050, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("DANH SÁCH BALO");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 410, 270, 22);

        jTMaSoBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTMaSoBL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTMaSoBLCaretUpdate(evt);
            }
        });
        jPanel1.add(jTMaSoBL);
        jTMaSoBL.setBounds(410, 130, 290, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã số balo:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(280, 140, 80, 17);

        jTTongKL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTTongKL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTTongKLCaretUpdate(evt);
            }
        });
        jPanel1.add(jTTongKL);
        jTTongKL.setBounds(410, 170, 290, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tổng khối lượng:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(280, 180, 120, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Số đồ vật:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(280, 250, 90, 20);

        jLSoLuongDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(jLSoLuongDV);
        jLSoLuongDV.setBounds(410, 250, 190, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Loại:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(280, 210, 40, 30);

        buttonGroup1.add(jRBalo1);
        jRBalo1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRBalo1.setSelected(true);
        jRBalo1.setText("Loại 1");
        jPanel1.add(jRBalo1);
        jRBalo1.setBounds(410, 210, 80, 25);

        buttonGroup1.add(jRBalo2);
        jRBalo2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRBalo2.setText("Loại 2");
        jPanel1.add(jRBalo2);
        jRBalo2.setBounds(500, 210, 80, 25);

        buttonGroup1.add(jRBalo3);
        jRBalo3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRBalo3.setText("Loại 3");
        jPanel1.add(jRBalo3);
        jRBalo3.setBounds(600, 210, 100, 25);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/background.png"))); // NOI18N
        jPanel1.add(background);
        background.setBounds(0, -20, 1320, 690);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTListBaloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTListBaloMouseClicked
        jTListBalo.setRowSelectionAllowed(true);
        if (jTListBalo.getSelectedRowCount() == 1) {
            setEnabledButton(true);
            setEnabledRadio(false);
            jBThemBalo.setEnabled(false);
            
            dongChonBL = 1;
            jTListBalo.setSelectionBackground(new Color(42,97,252));
            baloTemp = new Balo(listBalo.get(jTListBalo.getSelectedRow()));
            
            jTMaSoBL.setText(baloTemp.getMaSo());
            jTTongKL.setText("" + baloTemp.getKhoiLuongBL());
            
            if (baloTemp.getLoai() == 3) {
                jRBalo3.setSelected(true);
            } else if (baloTemp.getLoai() == 2) {
                jRBalo2.setSelected(true);
            } else {
                jRBalo1.setSelected(true);
            }
            jLSoLuongDV.setText("" + baloTemp.getSoLuong());
            
        } else {
            if (jTListBalo.getRowCount() == 0) { // getRowCount() trả về số dòng trong bảng
                JOptionPane.showMessageDialog(this, "Bảng rỗng!");
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng!");
            }
        }
    }//GEN-LAST:event_jTListBaloMouseClicked

    private void jBTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTroVeActionPerformed
        GiaoDienChinh gd = new GiaoDienChinh();
        this.setVisible(false);
        gd.setVisible(true);
        
    }//GEN-LAST:event_jBTroVeActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        showListBalo();
        setEnabledButton(false);
        
    }//GEN-LAST:event_formWindowOpened

    private void jBThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBThemDVActionPerformed
        DanhSachThemDoVat nhapDL = new DanhSachThemDoVat();
        this.setVisible(false);
        nhapDL.setVisible(true);
        balo = new Balo(listBalo.get(jTListBalo.getSelectedRow()));
        bienDSBL = 1;
        
    }//GEN-LAST:event_jBThemDVActionPerformed

    private void jBChonThuatToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChonThuatToanActionPerformed
        this.setVisible(false);
        PhuongPhapGiai thuatToan = new PhuongPhapGiai();
        thuatToan.setVisible(true);
        balo = new Balo(listBalo.get(jTListBalo.getSelectedRow()));
        bienDSBL = 1;
    }//GEN-LAST:event_jBChonThuatToanActionPerformed

    private void jBDatLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDatLaiActionPerformed
        jTMaSoBL.setText(baloTemp.getMaSo());
        jTTongKL.setText(""+baloTemp.getKhoiLuongBL());
        
    }//GEN-LAST:event_jBDatLaiActionPerformed

    private void jBXoaBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXoaBLActionPerformed
        deleteBalo();
        setEnabledButton(false);
        dongChonBL = 0;
    }//GEN-LAST:event_jBXoaBLActionPerformed

    private void jBCapNhatBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCapNhatBLActionPerformed
        String t = new String(xoaKhoangTrangDu(jTMaSoBL.getText()));
        if(jTMaSoBL.getText().equals("") || jTTongKL.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin!");
        } else {
            if (kiemTraLaSo(jTTongKL.getText()) && kiemTraMaSoBL(t) && kTraKLuongBLVoiDV(baloTemp.getId())) {
                updateBalo();
                setEnabledButton(false);
                dongChonBL = 0;
            }
        }
    }//GEN-LAST:event_jBCapNhatBLActionPerformed

    private void jBThemBaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBThemBaloActionPerformed
        if (kiemTraLaSo(jTTongKL.getText())) {
            layDuLieuBL();
            dongChonBL = 0;
            if (kiemTraMaSoBL(balo.getMaSo())) {
                insertDataBalo();
                balo.setId(getIdBL());
                modelBalo.setRowCount(0);
                listBalo.add(balo);
                showListBalo();
                setEnabledButton(false);
                setRong();
            }
        }
    }//GEN-LAST:event_jBThemBaloActionPerformed

    private void jTMaSoBLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTMaSoBLCaretUpdate
        if(dongChonBL == 0) {
            kiemTraRong();
        }
    }//GEN-LAST:event_jTMaSoBLCaretUpdate

    private void jTTongKLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTTongKLCaretUpdate
        if(dongChonBL == 0) {
            kiemTraRong();
        }
    }//GEN-LAST:event_jTTongKLCaretUpdate

    private void jBHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHuyActionPerformed
        setRong();
        setEnabledButton(false);
        dongChonBL = 0;
        
        jTListBalo.setRowSelectionAllowed(false);
    }//GEN-LAST:event_jBHuyActionPerformed

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
            java.util.logging.Logger.getLogger(DanhSachThemBalo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemBalo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemBalo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemBalo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachThemBalo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBCapNhatBL;
    private javax.swing.JButton jBChonThuatToan;
    private javax.swing.JButton jBDatLai;
    private javax.swing.JButton jBHuy;
    private javax.swing.JButton jBThemBalo;
    private javax.swing.JButton jBThemDV;
    private javax.swing.JButton jBTroVe;
    private javax.swing.JButton jBXoaBL;
    private javax.swing.JLabel jLSoLuongDV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRBalo1;
    private javax.swing.JRadioButton jRBalo2;
    private javax.swing.JRadioButton jRBalo3;
    private javax.swing.JScrollPane jScrollPaneListBalo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTListBalo;
    private javax.swing.JTextField jTMaSoBL;
    private javax.swing.JTextField jTTongKL;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
