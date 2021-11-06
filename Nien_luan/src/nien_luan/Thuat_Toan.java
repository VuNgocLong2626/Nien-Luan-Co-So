/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nien_luan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author HP
 */
public class Thuat_Toan extends javax.swing.JFrame {

    /**
     * Creates new form Thuat_Toan
     */
    public Thuat_Toan() {
        initComponents();
        this.setLocation(960 - this.getSize().width / 2, 300);
    }

    public Thuat_Toan(String maso, float khoiluong, int loai, int soluongdv, int idbalo) {
        initComponents();
        this.rdThamAn.setSelected(true);
        this.setLocation(960 - this.getSize().width / 2, 300);
        this.idbalo = idbalo;
        this.khoiLuongMacDinh = khoiluong;
        balo = new Balo();
        balo.inputSQL(maso, khoiluong, loai, soluongdv);
        this.addDovat();//Them thong tin do vat vao balo
        balo.TinhDonGia(balo.getDv(), soluongdv);
        balo.SapXep(balo.getDv(), soluongdv);
        this.themBalo(maso, khoiluong, loai, soluongdv);
        this.showDoVat();
        
        //System.out.println(balo.output());
        
    }
    
    private int idbalo;
    private Balo balo;
    private float khoiLuongMacDinh;
    //Chức Năng của from
    public void addDovat(){
        try {
            ClsDatabase.open();
            Statement s = ClsDatabase.con.createStatement();
            ResultSet rs = s.executeQuery("select * from dovat where idbalo = " + idbalo);
            int i = 0;
            while (rs.next()) {
                int stt = rs.getInt(7);
                String tendv = rs.getString(3);
                float khoiluong = rs.getFloat(4);
                float giatri = rs.getFloat(5);
                int soluong = rs.getInt(6);
                int id = rs.getInt(1);
                balo.getDVSQL(i, stt, tendv, khoiluong, giatri, soluong, id);
                i++;
            }
            ClsDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //them thong tin của balo của from
    public void themBalo(String maso, float khoiluong, int loai, int soluongdv){
        labelMaSo.setText(maso);
        labelTongKhoiLuong.setText(khoiluong+"");
        labelLoai.setText(loai+"");
        labelSoDoVat.setText(soluongdv+"");
    }
    
    //Hiển thị tất cả đồ vật
    public void showDoVat(){
        DefaultTableModel tblModel = (DefaultTableModel) tableDoVat.getModel();
        tblModel.setRowCount(0);//reset view jtable
        try {
            for (int i = 0; i < balo.getSoluongdv(); i++) {
                String stt = String.valueOf(balo.getDv()[i].getStt());
                String tendv = balo.getDv()[i].getTen();
                String kluong = String.valueOf(balo.getDv()[i].getKhoiluong());;
                String giatri = String.valueOf(balo.getDv()[i].getGiatri());;
                String soluong = String.valueOf(balo.getDv()[i].getSoluong());;
                String dongia = String.valueOf(balo.getDv()[i].getDongia());;
                String phuongan = String.valueOf(balo.getDv()[i].getPhuongan());;

                String Data[] = {stt, tendv, kluong, giatri, soluong, dongia, phuongan};
                tblModel.addRow(Data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Ten Thuat toan
    public String GetTenThuatToan(){
        String kq = "Quy Hoach Dong";
        if(rdThamAn.isSelected())
            kq = "Tham An";
        if(rdNhanhCan.isSelected())
            kq = "Nhanh Can";
        return kq;
    }
    
    //in file 
    public void inFile()throws InterruptedException {
        WritableWorkbook wworkbook;
        try {
            JFileChooser excelFile = new JFileChooser();
            excelFile.showSaveDialog(null);
            String str = excelFile.getSelectedFile().toString()+".xls";
            wworkbook = Workbook.createWorkbook(new File(str));
            //Sheet name
            WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
            int row = 0, col = 0;
            //                     col row
            Label label =new Label(0, 0,"Ma so");
            wsheet.addCell(label);
            label =new Label(1, 0,labelMaSo.getText());
            wsheet.addCell(label);
            label =new Label(0, 1,"Tong khoi luong");
            wsheet.addCell(label);
            label =new Label(1, 1,labelTongKhoiLuong.getText());
            wsheet.addCell(label);
            label =new Label(0, 2,"Loai");
            wsheet.addCell(label);
            label =new Label(1, 2,labelLoai.getText());
            wsheet.addCell(label);
            label =new Label(0, 3,"Tong so do vat");
            wsheet.addCell(label);
            label =new Label(1, 3,labelSoDoVat.getText());
            wsheet.addCell(label);
            label =new Label(0, 4,"Thuat toan");
            wsheet.addCell(label);
            label =new Label(1, 4, this.GetTenThuatToan());
            wsheet.addCell(label);
            label =new Label(0, 6,"Ket qua");
            wsheet.addCell(label);
            balo.SapXepSTT(balo.getDv(), Integer.parseInt(labelSoDoVat.getText()));
            
            if(Integer.parseInt(labelLoai.getText()) == 1){
                label =new Label(0, 7,"STT");
                wsheet.addCell(label);
                label =new Label(1, 7,"Te Do vat");
                wsheet.addCell(label);
                label =new Label(2, 7,"Khoi Luong");
                wsheet.addCell(label);
                label =new Label(3, 7,"Gia Tri");
                wsheet.addCell(label);
                label =new Label(4, 7,"Don Gia");
                wsheet.addCell(label);
                label =new Label(5, 7,"Phuong An");
                wsheet.addCell(label);
                int i = 0;
                for(row=8; row < Integer.parseInt(labelSoDoVat.getText()) + 8; row++){
                    label =new Label(0, row, balo.getDv()[i].getStt()+"");
                    wsheet.addCell(label);
                    label =new Label(1, row, balo.getDv()[i].getTen()+"");
                    wsheet.addCell(label);
                    label =new Label(2, row, balo.getDv()[i].getKhoiluong()+"");
                    wsheet.addCell(label);
                    label =new Label(3, row, balo.getDv()[i].getGiatri()+"");
                    wsheet.addCell(label);
                    label =new Label(4, row, balo.getDv()[i].getDongia()+"");
                    wsheet.addCell(label);
                    label =new Label(5, row, balo.getDv()[i].getPhuongan()+"");
                    wsheet.addCell(label);
                    i++;
                }
            }else{
                label =new Label(0, 7,"STT");
                wsheet.addCell(label);
                label =new Label(1, 7,"Te Do vat");
                wsheet.addCell(label);
                label =new Label(2, 7,"Khoi Luong");
                wsheet.addCell(label);
                label =new Label(3, 7,"Gia Tri");
                wsheet.addCell(label);
                label =new Label(4, 7,"Don Gia");
                wsheet.addCell(label);
                label =new Label(5, 7,"So luong");
                wsheet.addCell(label);
                label =new Label(6, 7,"Phuong An");
                wsheet.addCell(label);
                int i = 0;
                for(row=8; row < Integer.parseInt(labelSoDoVat.getText()) + 8; row++){
                    label =new Label(0, row, balo.getDv()[i].getStt()+"");
                    wsheet.addCell(label);
                    label =new Label(1, row, balo.getDv()[i].getTen()+"");
                    wsheet.addCell(label);
                    label =new Label(2, row, balo.getDv()[i].getKhoiluong()+"");
                    wsheet.addCell(label);
                    label =new Label(3, row, balo.getDv()[i].getGiatri()+"");
                    wsheet.addCell(label);
                    label =new Label(4, row, balo.getDv()[i].getDongia()+"");
                    wsheet.addCell(label);
                    label =new Label(5, row, balo.getDv()[i].getPhuongan()+"");
                    wsheet.addCell(label);
                    label =new Label(6, row, balo.getDv()[i].getSoluong()+"");
                    wsheet.addCell(label);
                    i++;
                }
            }
            row = Integer.parseInt(labelSoDoVat.getText()) + 8;
            label =new Label(0, ++row,"Khoi luong chua duoc");
            wsheet.addCell(label);
            label =new Label(1, row, this.labelChuaDuoc.getText());
            wsheet.addCell(label);
            label =new Label(0, ++row,"Tong gia tri");
            wsheet.addCell(label);
            label =new Label(1, row, this.labelGiaTri.getText());
            wsheet.addCell(label);
            label =new Label(0, ++row,"Khoi Luong du");
            wsheet.addCell(label);
            label =new Label(1, row, this.labelKLDu.getText());
            wsheet.addCell(label);
            
            wworkbook.write();
            wworkbook.close();
            balo.SapXep(balo.getDv(), Integer.parseInt(labelSoDoVat.getText()));
        } catch (Exception e) {
            System.out.println(e);
	}
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
        jLabel10 = new javax.swing.JLabel();
        btnThucHien = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        rdNhanhCan = new javax.swing.JRadioButton();
        rdThamAn = new javax.swing.JRadioButton();
        rdQuyHoachDong = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        labelMaSo = new javax.swing.JLabel();
        labelTongKhoiLuong = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelLoai = new javax.swing.JLabel();
        labelSoDoVat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDoVat = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelChuaDuoc = new javax.swing.JLabel();
        labelGiaTri = new javax.swing.JLabel();
        labelKLDu = new javax.swing.JLabel();
        btnSoSanh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thuật Toán");
        setSize(new java.awt.Dimension(850, 550));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Thuật Toán");

        btnThucHien.setText("Thực hiện");
        btnThucHien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThucHienActionPerformed(evt);
            }
        });

        jButton2.setText("Xuất file");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Chọn thuật toán :");

        buttonGroup1.add(rdNhanhCan);
        rdNhanhCan.setText("Nhánh cận");

        buttonGroup1.add(rdThamAn);
        rdThamAn.setText("Tham ăn");

        buttonGroup1.add(rdQuyHoachDong);
        rdQuyHoachDong.setText("Quy hoạch động");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Thông tin Balo :");

        labelMaSo.setText("None");

        labelTongKhoiLuong.setText("None");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Mã số :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tổng khối lượng :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Loại :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Tổng số đồ vật :");

        labelLoai.setText("None");

        labelSoDoVat.setText("None");

        tableDoVat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên đồ vật", "Khôi lương", "Giá trị", "Số lượng", "Đơn giá", "Phương án"
            }
        ));
        jScrollPane1.setViewportView(tableDoVat);

        jLabel1.setText("Tống khối lượng balo chứa được :");

        jLabel4.setText("Tổng giá trị :");

        jLabel9.setText("Khối lượng còn dư :");

        labelChuaDuoc.setText("     ");

        labelGiaTri.setText("     ");

        labelKLDu.setText("       ");

        btnSoSanh.setText("So Sánh Thuật Toán");
        btnSoSanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoSanhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(284, 284, 284))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(labelGiaTri))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(labelChuaDuoc))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(labelKLDu))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThucHien)
                                    .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnSoSanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(131, 131, 131)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelMaSo))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(labelLoai))
                                                .addComponent(jLabel6))
                                            .addComponent(jLabel8))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelTongKhoiLuong)
                                            .addComponent(labelSoDoVat)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdThamAn)
                                .addGap(18, 18, 18)
                                .addComponent(rdNhanhCan)
                                .addGap(18, 18, 18)
                                .addComponent(rdQuyHoachDong)))))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdNhanhCan)
                    .addComponent(rdThamAn)
                    .addComponent(rdQuyHoachDong)
                    .addComponent(labelMaSo)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThucHien)
                    .addComponent(jButton2)
                    .addComponent(labelTongKhoiLuong)
                    .addComponent(jLabel6)
                    .addComponent(btnThoat))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnQuayLai)
                        .addComponent(btnSoSanh))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(labelLoai)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelSoDoVat))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelChuaDuoc))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelGiaTri))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(labelKLDu))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        this.setVisible(false);
        DS_Balo b = new DS_Balo();
        b.setVisible(true);
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnThucHienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThucHienActionPerformed
        balo.setKhoiluong(khoiLuongMacDinh);
        if(rdThamAn.isSelected())
            balo.Greedy();
        if(rdNhanhCan.isSelected()){
            balo.TaoNutGoc();
            balo.BranchAndBound(0);
        }
        if(rdQuyHoachDong.isSelected()){
            balo.TaoBang();
            balo.TraBang();
        }
        labelChuaDuoc.setText(balo.khoiLuongCoTrongBalo()+"");
        labelGiaTri.setText(balo.tongGiaTri()+"");
        labelKLDu.setText(khoiLuongMacDinh - balo.khoiLuongCoTrongBalo() + "");
        this.showDoVat();
    }//GEN-LAST:event_btnThucHienActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            this.inFile();
        } catch (InterruptedException ex) {
            Logger.getLogger(Thuat_Toan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSoSanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoSanhActionPerformed
        // TODO add your handling code here:
//        this.setVisible(false);
        Tat_Ca_Thuat_Toan b = new Tat_Ca_Thuat_Toan(balo,khoiLuongMacDinh);
        b.setVisible(true);
    }//GEN-LAST:event_btnSoSanhActionPerformed

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
            java.util.logging.Logger.getLogger(Thuat_Toan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Thuat_Toan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Thuat_Toan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Thuat_Toan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Thuat_Toan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnSoSanh;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnThucHien;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelChuaDuoc;
    private javax.swing.JLabel labelGiaTri;
    private javax.swing.JLabel labelKLDu;
    private javax.swing.JLabel labelLoai;
    private javax.swing.JLabel labelMaSo;
    private javax.swing.JLabel labelSoDoVat;
    private javax.swing.JLabel labelTongKhoiLuong;
    private javax.swing.JRadioButton rdNhanhCan;
    private javax.swing.JRadioButton rdQuyHoachDong;
    private javax.swing.JRadioButton rdThamAn;
    private javax.swing.JTable tableDoVat;
    // End of variables declaration//GEN-END:variables
}
