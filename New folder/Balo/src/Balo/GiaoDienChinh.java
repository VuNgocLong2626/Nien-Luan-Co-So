/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Balo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Lenovo
 */
public class GiaoDienChinh extends javax.swing.JFrame {

    
    public GiaoDienChinh() {
        initComponents();
        this.setSize(1000, 650);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Bài Toán BaLo");
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jBThoat = new javax.swing.JButton();
        jBDanhSach = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1027, 640));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setLayout(null);

        jBThoat.setBackground(new java.awt.Color(155, 151, 84));
        jBThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBThoat.setText("THOÁT");
        jBThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBThoatActionPerformed(evt);
            }
        });
        jPanel2.add(jBThoat);
        jBThoat.setBounds(210, 290, 140, 25);

        jBDanhSach.setBackground(new java.awt.Color(155, 151, 84));
        jBDanhSach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBDanhSach.setText("THÊM BALO");
        jBDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDanhSachActionPerformed(evt);
            }
        });
        jPanel2.add(jBDanhSach);
        jBDanhSach.setBounds(210, 240, 140, 25);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("GIẢI BÀI TOÁN BALO");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(120, 180, 330, 37);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/logo_DHCT.png"))); // NOI18N
        jPanel2.add(jLabel12);
        jLabel12.setBounds(30, 20, 110, 110);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("THỰC HIỆN:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(570, 140, 230, 29);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Họ và tên:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(570, 210, 120, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Lớp:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(570, 270, 70, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Khóa:");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(570, 300, 80, 20);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel16.setText("2020 - 2021");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(760, 390, 260, 20);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setText("Niên luận:");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(570, 390, 100, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Đại Học Cần Thơ");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(760, 330, 150, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("44");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(760, 300, 50, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Kỹ Thuật Phần Mềm");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(760, 270, 200, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Bùi Yến Linh");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(760, 210, 120, 20);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setText("Mssv:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(570, 240, 100, 20);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setText("B1805782");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(760, 240, 110, 20);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel17.setText("ỨNG DỤNG THUẬT TOÁN TỐI ƯU");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(40, 140, 500, 37);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel15.setText("Trường:");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(570, 330, 100, 20);

        jPanel1.setBackground(new java.awt.Color(158, 146, 21));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel1);
        jPanel1.setBounds(550, 0, 2, 690);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/image-balo.png"))); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(120, 340, 300, 250);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setText("Học phần:");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(570, 360, 100, 20);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel19.setText("Niên luận cơ sở ngành KTPM");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(760, 360, 260, 20);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel20.setText("Giảng viên hướng dẫn:");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(570, 420, 190, 20);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel21.setText("Trương Thị Thanh Tuyền");
        jPanel2.add(jLabel21);
        jLabel21.setBounds(760, 420, 230, 20);

        background.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/background.png"))); // NOI18N
        background.setAlignmentY(0.0F);
        jPanel2.add(background);
        background.setBounds(0, -70, 1300, 730);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1027, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBThoatActionPerformed
        this.dispose();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }//GEN-LAST:event_jBThoatActionPerformed

    private void jBDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDanhSachActionPerformed
        this.setVisible(false);
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        dsBL.setVisible(true);
    }//GEN-LAST:event_jBDanhSachActionPerformed

    private String tdn, mk;
    static String tenFile;
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            File file = new File("cauhinh.txt");
            String absolutePath = file.getAbsolutePath();
            String filePath1 = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

            File f = new File(filePath1 + "\\cauhinh.txt");
            tenFile = new String(filePath1 + "\\cauhinh.txt");
           
            if (f.exists()) {
                Scanner sc = new Scanner(new File(tenFile));
                if (!sc.hasNext()) {
                    KetNoiCSDL kn = new KetNoiCSDL();
                    kn.setVisible(true);
                } else {
                    tdn = new String(sc.nextLine());
                    try {
                        mk = new String(sc.nextLine());
                    } catch (Exception e) {
                        mk = new String();
                    }
                    KetNoiCSDL kn = new KetNoiCSDL();
                    kn.connect(tdn, mk);
                }
                sc.close();
                jBDanhSach.setEnabled(true);
            } else {
                KetNoiCSDL kn = new KetNoiCSDL();
                kn.setVisible(true);
            }
        } catch (Exception e) {
            System.out.print("\n Lỗi: " + e);
        }
    }//GEN-LAST:event_formWindowOpened

   
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
            java.util.logging.Logger.getLogger(GiaoDienChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiaoDienChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiaoDienChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiaoDienChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiaoDienChinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton jBDanhSach;
    private javax.swing.JButton jBThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
