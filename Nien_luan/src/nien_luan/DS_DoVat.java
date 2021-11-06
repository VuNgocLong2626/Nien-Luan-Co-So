/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nien_luan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author longs
 */
public class DS_DoVat extends javax.swing.JFrame {

    /**
     * Creates new form DS_DoVat
     */
    public DS_DoVat() {
        initComponents();
        this.setLocation(960 - this.getSize().width / 2, 300);
        this.showDoVat();
        System.out.println(this.labelLoai.getText());
        if(Integer.parseInt(this.labelLoai.getText()) == 1 || Integer.parseInt(this.labelLoai.getText()) == 3){
            
            this.jsSoLuong.setEnabled(false);
        }
    }
    
    public DS_DoVat(int idbalo, String mso, Float kluong, int loai) {
        initComponents();
        this.setLocation(960 - this.getSize().width / 2, 300);
        this.idbalo = idbalo;
        this.mso = mso;
        this.kluong = kluong;
        this.loai = loai;
        labelMaBalo.setText(mso);
        labelKLuong.setText(kluong.toString());
        labelLoai.setText(""+loai);
        this.showDoVat();
        this.stt = this.soDoVat();
        this.re(true);
        System.out.println(this.loai);
        if(this.loai == 1 || this.loai == 3){
            
            this.jsSoLuong.setEnabled(false);
        }
    }
    
    private int idbalo;
    private String mso;
    private Float kluong;
    private int loai;
    private int stt ;
    private int tt ;//id đo vat
    //Chức Năng của from
    public void themDV(){
        try {
            ClsDatabase.open();
            String sql = new String("insert into dovat(idbalo, tendovat, khoiluong, giatri, soluong, stt) values (?,?,?,?,?,?)");
            PreparedStatement ps = ClsDatabase.con.prepareCall(sql);
            
            this.stt++;
            ps.setInt(1, idbalo);
            ps.setString(2, txtTenDV.getText());
            ps.setFloat(3, Float.parseFloat(txtKLuong.getText()));
            ps.setFloat(4, Float.parseFloat(txtGiaTri.getText()));
            ps.setInt(5, checksoluong());
            ps.setInt(6, this.stt);
            
            ps.executeUpdate();
            ClsDatabase.close();
            JOptionPane.showMessageDialog(this, "Thêm do vat thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Check đầu vào của số lướng
    public int checksoluong(){
        int i = (int)jsSoLuong.getValue();
        if(this.loai == 1)
            i = 0;
        if(this.loai == 3)
            i = 1;
        return i;
    }
    
    
    //Hiển thị tất cả đồ vật
    public void showDoVat(){
        DefaultTableModel tblModel = (DefaultTableModel) tableDVat.getModel();
        tblModel.setRowCount(0);//reset view jtable
        try {
            ClsDatabase.open();
            Statement s = ClsDatabase.con.createStatement();
            ResultSet rs = s.executeQuery("select * from dovat where idbalo = " + idbalo);
            while (rs.next()) {
                String stt = rs.getString(7);
                String tendv = rs.getString(3);
                String kluong = rs.getString(4);
                String giatri = rs.getString(5);
                String soluong = rs.getString(6);
                String id = rs.getString(1);

                String Data[] = {stt, tendv, kluong, giatri, soluong, id};
                tblModel.addRow(Data);
            }
            ClsDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Kiểm tra loại để cho ra số lượng
    public int sLuong(){
        int re = 0;
        if(this.loai == 2)
            re = (int)jsSoLuong.getValue();
        if(this.loai == 3)
            re = 1;
        return re;
    }
    
    //Cập Nhật Đồ Vật
    public void capNhatDoVat(){
        try {
            ClsDatabase.open();
            String url = "update dovat set tendovat = ?, khoiluong = ?, giatri = ?, soluong = ? where id = ? ";
            PreparedStatement ps = ClsDatabase.con.prepareStatement(url);
            
            ps.setString(1, txtTenDV.getText());
            ps.setFloat(2, Float.parseFloat(txtKLuong.getText()));
            ps.setFloat(3, Float.parseFloat(txtGiaTri.getText()));
            ps.setInt(4, checksoluong());
            ps.setInt(5, tt);
            
            ps.executeUpdate();
            ClsDatabase.close();
            JOptionPane.showMessageDialog(this, "Cập nhật do vat thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Xoa Đồ vật
    public void xoaDoVat(){
        try {
            ClsDatabase.open();
            String url = "delete from dovat where id = ?";
            PreparedStatement ps = ClsDatabase.con.prepareStatement(url);
            
            ps.setInt(1, this.tt);
            ps.executeUpdate();
            this.stt -= 1;
            
            ClsDatabase.close();
            JOptionPane.showMessageDialog(this, "Xóa đồ vật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Số lượng đồ vật
    public int soDoVat(){
        int i = 0;
        try {
            ClsDatabase.open();
            Statement s = ClsDatabase.con.createStatement();
            ResultSet rs = s.executeQuery("select count(*) from dovat where idbalo = " + this.idbalo);
            if (rs.next()) {
                i = rs.getInt(1);
            }
            else
                i = 0; 
            ClsDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    //Trạng thái button
    public void re(boolean k){
        btnThem.setEnabled(k);
        btnCNhat.setEnabled(!k);
        btnXoa.setEnabled(!k);
    }
    
    //reset from
    public void refrom(){
        txtGiaTri.setText("");
        txtKLuong.setText("");
        txtTenDV.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelMaBalo = new javax.swing.JLabel();
        labelKLuong = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDVat = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        labelLoai = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTenDV = new javax.swing.JTextField();
        txtKLuong = new javax.swing.JTextField();
        txtGiaTri = new javax.swing.JTextField();
        jsSoLuong = new javax.swing.JSpinner();
        btnThem = new javax.swing.JButton();
        btnCNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Danh Sách Đồ Vâjt");
        setSize(new java.awt.Dimension(850, 550));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Danh Sách Đồ Vật");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Mã số balo :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tổng khối lượng :");

        labelMaBalo.setText("None");

        labelKLuong.setText("None");

        tableDVat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên đồ vật", "Khối lương", "Giá trị", "Số lượng", "id Đồ vật"
            }
        ));
        tableDVat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDVatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableDVat);

        labelLoai.setText("None");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Loại Balo :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tên đồ vật :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Khối lượng :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Giá Trị :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Số lương :");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCNhat.setText("Cập nhật");
        btnCNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCNhatActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnQuayLai.setText("Quay Lại ");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(labelMaBalo))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(labelKLuong))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(27, 27, 27)
                                    .addComponent(labelLoai))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtKLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jsSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnCNhat)
                .addGap(47, 47, 47)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnQuayLai)
                .addContainerGap(257, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelMaBalo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelKLuong))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLoai)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtKLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jsSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnCNhat)
                    .addComponent(btnXoa)
                    .addComponent(btnQuayLai))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.themDV();
        this.showDoVat();
        this.refrom();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        try {
            ClsDatabase.open();
            String url = "update balo set  soluongdv = ? where id = ? ";
            PreparedStatement ps = ClsDatabase.con.prepareStatement(url);
            
            ps.setInt(1, this.stt);
            ps.setInt(2, idbalo);
            
            ps.executeUpdate();
            ClsDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setVisible(false);
        DS_Balo b = new DS_Balo();
        b.setVisible(true);
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void tableDVatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDVatMouseClicked
        int row = tableDVat.getSelectedRow();
        TableModel model = tableDVat.getModel();
        
        this.txtTenDV.setText(model.getValueAt(row, 1).toString());
        this.txtKLuong.setText(model.getValueAt(row, 2).toString());
        this.txtGiaTri.setText(model.getValueAt(row, 3).toString());
        this.jsSoLuong.setValue(Integer.parseInt(model.getValueAt(row, 4).toString()));
        this.tt = Integer.parseInt(model.getValueAt(row, 5).toString());
        this.re(false);
//        System.out.println(tt);
    }//GEN-LAST:event_tableDVatMouseClicked

    private void btnCNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCNhatActionPerformed
        // TODO add your handling code here:
        this.capNhatDoVat();
        this.showDoVat();
        this.re(true);
        this.refrom();
    }//GEN-LAST:event_btnCNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.xoaDoVat();
        this.showDoVat();
        this.re(true);
        this.refrom();
    }//GEN-LAST:event_btnXoaActionPerformed

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
            java.util.logging.Logger.getLogger(DS_DoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DS_DoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DS_DoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DS_DoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DS_DoVat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCNhat;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner jsSoLuong;
    private javax.swing.JLabel labelKLuong;
    private javax.swing.JLabel labelLoai;
    private javax.swing.JLabel labelMaBalo;
    private javax.swing.JTable tableDVat;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtKLuong;
    private javax.swing.JTextField txtTenDV;
    // End of variables declaration//GEN-END:variables
}
