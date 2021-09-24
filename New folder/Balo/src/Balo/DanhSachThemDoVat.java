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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 * 
 * 
 * 
 */
public class DanhSachThemDoVat extends javax.swing.JFrame {

    static Balo balo;
    private int chonDong = 0, dem = 1;
    private Connection conn;
    private DoVat dvatTemp;
    private ArrayList <DoVat> listDoVat;
    private DefaultTableModel model;
    
    
    
    
    public DanhSachThemDoVat() {
        initComponents();
        this.setSize(1000, 650);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("DANH SÁCH - THÊM ĐỒ VẬT");
        
        KetNoiCSDL ketNoiCSDL = new KetNoiCSDL();
        conn = ketNoiCSDL.conn;
    }

    public void kiemTraRong() {
        if (jTTenDV.getText().equals("") || jTKhoiLuong.getText().equals("") || jTGiaTri.getText().equals("")) {
            jBThemDV.setEnabled(false);
        } else {
            if (chonDong != 1) {
                jBThemDV.setEnabled(true);
            }
        }
    }
    
    public boolean kTraKLuongDV(double kl, double klBL) {
        if (kl <= klBL) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Khối lượng đồ vật phải nhỏ hơn hoặc bẳng với khối lượng balo.");
            return false;
        }
    }
    
    public void xacDinhLoaiBL() {
        if (balo.getLoai() == 1 || balo.getLoai() == 3) {
            System.out.print(balo.getLoai());
            jSSoLuong.setEnabled(false);
        }
    }
    
    public void setRong() {
        jTTenDV.setText(null);
        jTKhoiLuong.setText(null);
        jTGiaTri.setText(null);
        jSSoLuong.setValue(1);
    }
    
    public void datLaiDVCu (DoVat dvT, int loaiBL) {
        jTTenDV.setText(dvT.getTen());
        jTKhoiLuong.setText("" + dvT.getKLuong());
        jTGiaTri.setText("" + dvT.getGTri());
        if (loaiBL == 2) {
            jSSoLuong.setValue(dvT.getSoLuong());
        }
    }
    public boolean kiemTraTenDV(String tenDV, int idBL, int daChon, String tenDVCu) {
        if (daChon == 1) { // đã chọn một dòng trong bảng để cập nhật
            if (xoaKhoangTrangDu(tenDV).equals(tenDVCu)) {
                return true;
            }
        
            try {
                String sql1 = new String("select * from dovat where tendovat=? and idbalo=?");
                PreparedStatement preStmt1 = conn.prepareStatement(sql1);

                preStmt1.setString(1, tenDV);
                preStmt1.setInt(2, idBL);

                ResultSet rs = preStmt1.executeQuery();
                while (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Đồ vật đã có trong balo! Vui lòng nhập tên khác!");

                    return false;
                }
            } catch (Exception ex) {
                System.out.print("Sorry! Lỗi hàm kiemTraTenDV: " + ex);
            }
        }
        return true;
    }
    
    public boolean kiemTraTenDVKhiThem(String tenDV, int idBL) {
        try {
            
            System.out.print("Tên đồ vật đang test: " + tenDV + "\n");
            String sql1 = new String("select * from dovat where tendovat=? and idbalo=?");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);
            
            preStmt1.setString(1, tenDV);
            preStmt1.setInt(2, idBL);
           
            ResultSet rs = preStmt1.executeQuery();
            while (rs.next()) {
                JOptionPane.showMessageDialog(this, "Đồ vật đã có trong balo! Vui lòng nhập tên khác!");
                
                return false;
            }
        } catch (Exception ex) {
            System.out.print("Sorry! Lỗi hàm kiemTraTenDV: " + ex);
        }
        return true;
    }
    
    
    private int stt = 1;
    public DoVat layDuLieuDV() {
        DoVat dv = new DoVat();
        dv.setTen(xoaKhoangTrangDu(jTTenDV.getText()));
        dv.setGTri(Double.parseDouble(jTGiaTri.getText()));
        dv.setKLuong(Double.parseDouble(jTKhoiLuong.getText()));
        dv.setPAn(0);
        dv.setDGia(0.0);
        dv.setSTT(stt++);
        if (balo.getLoai() == 1) {
            dv.setSoLuong(-1);
        } else {
            dv.setSoLuong((int) jSSoLuong.getValue());
        }
        listDoVat.add(dv);
        return dv;
    }
    
    public void showDV(DoVat dv) {
        
        model = (DefaultTableModel) jTListDV.getModel();
        if (balo.getLoai() == 1) {
            model.addRow(new Object[] {
                dem++ , dv.getTen(), dv.getKLuong(), dv.getGTri(), "-"
            });
        } else {
            model.addRow(new Object[] {
                dem++ , dv.getTen(), dv.getKLuong(), dv.getGTri(), dv.getSoLuong()
            });
        }
    }
    
    public void insertDataDoVat(DoVat dv) {
        try {
            String sql1 = new String("INSERT INTO dovat(stt, tendovat, khoiluong, giatri, soluong, idbalo) VALUES (?,?,?,?,?,?)");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);
            
            preStmt1.setInt(1, dv.getSTT());
            preStmt1.setString(2, dv.getTen());
            preStmt1.setDouble(3, dv.getKLuong());
            preStmt1.setDouble(4, dv.getGTri());
            preStmt1.setInt(5, dv.getSoLuong());
            preStmt1.setInt(6, balo.getId());
            preStmt1.execute();
            
            dem = 1;
            getDataDoVat(balo.getId());
            System.out.print("\nSo61 lượng dv sau khi insert: " + listDoVat.size());
            updateSoLuongDV(listDoVat.size(), balo.getId());
            
            JOptionPane.showMessageDialog(this, "Thêm đồ vật thành công!!!");
        } catch (Exception ex) {
            System.out.print("Sorry! Không thể chèn dữ liệu!!!" + ex + "\n");
        }
    }
    
    public int getIdDoVat(int idBL, String tenDV) {
        try {
            String sql = new String("select id from dovat where tendovat=? and idbalo=?");
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, tenDV);
            preStmt.setInt(2, balo.getId());
            ResultSet rs = preStmt.executeQuery();
            rs.next();
            return rs.getInt("id");
            
        } catch (Exception e) {
            System.out.print("Không lấy được id balo" + e + "\n");
        }
        return 0;
    }
    
    public void getDataDoVat(int idBalo) {
        stt = 1;
        try {
            
            String sql = new String("select * from dovat where idbalo=?");
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, idBalo);
            ResultSet rs = s.executeQuery();
            model = (DefaultTableModel) jTListDV.getModel();
            model.setRowCount(0);
            listDoVat = new ArrayList<>();
            while (rs.next()) {
                DoVat dv = new DoVat();
                dv.setSTT(stt++);
                dv.setId(rs.getInt("id"));
                dv.setTen(rs.getString("tendovat"));
                dv.setKLuong(rs.getDouble("khoiluong"));
                dv.setGTri(rs.getDouble("giatri"));
                dv.setSoLuong(rs.getInt("soluong"));
                
                listDoVat.add(dv);
                
                showDV(dv);
            }
            
        } catch (Exception e) {
            System.out.print("Lỗi hàm getDataDoVat: " + e);
        }
    }
    
    public void updateDataDoVat(int id_dv) {
        try {
            String sql1 = new String("update dovat set tendovat = ?, khoiluong = ?, giatri = ?, soluong = ? where id = ?");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);
            
            preStmt1.setString(1, xoaKhoangTrangDu(jTTenDV.getText()));
            preStmt1.setDouble(2, Double.parseDouble(jTKhoiLuong.getText()));
            preStmt1.setDouble(3, Double.parseDouble(jTGiaTri.getText()));
            if (balo.getLoai() == 1) {
                preStmt1.setInt(4, -1);
            } else {
                preStmt1.setInt(4, (int) jSSoLuong.getValue());
            }
            
            preStmt1.setInt(5, id_dv);
            preStmt1.executeUpdate();
            dem = 1;
            getDataDoVat(balo.getId());
            
            setRong();  
            jBDatRong.setEnabled(true);
            chonDong = 0;
            
            
            JOptionPane.showMessageDialog(this, "Cập nhật đồ vật thành công!!!");
        } catch (Exception ex) {
            System.out.print("Sorry! Không thể cập nhật đồ vật!!!" + ex + "\n");
        }
    }
    
    public void capNhatSTTDV(ArrayList<DoVat> list) {
        
        for (int i = 0; i < list.size(); i++) {
            try {
                String sql = new String("update dovat set stt=? where id=?");
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, i + 1);
                stmt.setInt(2, list.get(i).getId());
                
                stmt.executeUpdate();
            } catch (Exception e) {
                System.out.print("Lỗi hàm capNhatSTT: " + e + "\n");
            }
        }
    }
    
    public void deleteDataDoVat(int idDV) {
        try {
            String sql1 = new String("delete from dovat where id=?");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);
            
            preStmt1.setInt(1, idDV);
            preStmt1.executeUpdate();
            
            dem = 1;
            getDataDoVat(balo.getId());
            
            capNhatSTTDV(listDoVat);
            
            balo.setSoLuong(listDoVat.size());
            updateSoLuongDV(listDoVat.size(), balo.getId());
            
            setRong();
            jBDatRong.setEnabled(true);
            chonDong = 0;
            
            JOptionPane.showMessageDialog(this, "Xóa đồ vật thành công!!!");
        } catch (Exception ex) {
            System.out.print("Sorry! Không thể xóa đồ vật!!!" + ex + "\n");
        }
    }
    
    public void updateSoLuongDV(int sluong, int idBL) {
        try {
            String sql1 = new String("update balo set soluongdv = ? where id = ?");
            PreparedStatement preStmt1 = conn.prepareStatement(sql1);
            
            preStmt1.setInt(1, sluong);
            preStmt1.setInt(2, idBL);
            preStmt1.executeUpdate();
            System.out.print("Cập nhật số lượng đồ vật thành công!\n");
        } catch (Exception ex) {
            System.out.print("Sorry! Không thể cập nhật đồ vật!!!" + ex + "\n");
        }
    }
    
    public String xoaKhoangTrangDu(String t) {
        return t.trim().replaceAll(" +", " ");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBDatRong = new javax.swing.JButton();
        jBThemDV = new javax.swing.JButton();
        jBCapNhat = new javax.swing.JButton();
        jBXoa = new javax.swing.JButton();
        jSSoLuong = new javax.swing.JSpinner();
        jTGiaTri = new javax.swing.JTextField();
        jTKhoiLuong = new javax.swing.JTextField();
        jTTenDV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTListDV = new javax.swing.JTable();
        jLDanhSachDV = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBTroLai = new javax.swing.JButton();
        jBChonThuatToan = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        jBTrangChu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLMaSoBL = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLKLBL = new javax.swing.JLabel();
        jBHuy = new javax.swing.JButton();
        backGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1005, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(null);

        jBDatRong.setBackground(new java.awt.Color(155, 151, 84));
        jBDatRong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBDatRong.setText("ĐẶT RỖNG");
        jBDatRong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDatRongActionPerformed(evt);
            }
        });
        jPanel1.add(jBDatRong);
        jBDatRong.setBounds(350, 370, 110, 25);

        jBThemDV.setBackground(new java.awt.Color(155, 151, 84));
        jBThemDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBThemDV.setText("THÊM");
        jBThemDV.setEnabled(false);
        jBThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBThemDVActionPerformed(evt);
            }
        });
        jPanel1.add(jBThemDV);
        jBThemDV.setBounds(350, 330, 110, 25);

        jBCapNhat.setBackground(new java.awt.Color(155, 151, 84));
        jBCapNhat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBCapNhat.setText("CẬP NHẬT");
        jBCapNhat.setEnabled(false);
        jBCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCapNhatActionPerformed(evt);
            }
        });
        jPanel1.add(jBCapNhat);
        jBCapNhat.setBounds(230, 330, 110, 25);

        jBXoa.setBackground(new java.awt.Color(155, 151, 84));
        jBXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBXoa.setText("XÓA");
        jBXoa.setEnabled(false);
        jBXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXoaActionPerformed(evt);
            }
        });
        jPanel1.add(jBXoa);
        jBXoa.setBounds(230, 370, 110, 25);

        jSSoLuong.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        jPanel1.add(jSSoLuong);
        jSSoLuong.setBounds(430, 280, 190, 30);

        jTGiaTri.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTGiaTriCaretUpdate(evt);
            }
        });
        jPanel1.add(jTGiaTri);
        jTGiaTri.setBounds(430, 240, 250, 30);

        jTKhoiLuong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTKhoiLuongCaretUpdate(evt);
            }
        });
        jPanel1.add(jTKhoiLuong);
        jTKhoiLuong.setBounds(430, 200, 250, 30);

        jTTenDV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTTenDVCaretUpdate(evt);
            }
        });
        jPanel1.add(jTTenDV);
        jTTenDV.setBounds(430, 160, 250, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tên đồ vật:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(300, 170, 79, 17);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Khối lượng:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(300, 210, 80, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Giá trị:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(300, 250, 45, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Số lượng:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(300, 290, 67, 17);

        jTListDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTListDV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên đồ vật", "Khối lượng", "Giá trị", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTListDV.setToolTipText("");
        jTListDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTListDVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTListDV);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 450, 960, 140);

        jLDanhSachDV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLDanhSachDV.setText("DANH SÁCH ĐỒ VẬT");
        jPanel1.add(jLDanhSachDV);
        jLDanhSachDV.setBounds(10, 420, 330, 22);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("DANH SÁCH - THÊM ĐỒ VẬT");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(310, 30, 500, 37);

        jBTroLai.setBackground(new java.awt.Color(155, 151, 84));
        jBTroLai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBTroLai.setText("TRỞ LẠI");
        jBTroLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTroLaiActionPerformed(evt);
            }
        });
        jPanel1.add(jBTroLai);
        jBTroLai.setBounds(640, 370, 110, 25);

        jBChonThuatToan.setBackground(new java.awt.Color(155, 151, 84));
        jBChonThuatToan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBChonThuatToan.setText("CHỌN THUẬT TOÁN");
        jBChonThuatToan.setEnabled(false);
        jBChonThuatToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChonThuatToanActionPerformed(evt);
            }
        });
        jPanel1.add(jBChonThuatToan);
        jBChonThuatToan.setBounds(470, 370, 160, 25);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/logo_DHCT.png"))); // NOI18N
        jPanel1.add(logo);
        logo.setBounds(40, 30, 100, 100);

        jBTrangChu.setBackground(new java.awt.Color(155, 151, 84));
        jBTrangChu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBTrangChu.setText("TRANG CHỦ");
        jBTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTrangChuActionPerformed(evt);
            }
        });
        jPanel1.add(jBTrangChu);
        jBTrangChu.setBounds(470, 330, 160, 25);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(0, 410, 1030, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã số balo:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(300, 90, 100, 17);

        jLMaSoBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLMaSoBL.setText("ms");
        jPanel1.add(jLMaSoBL);
        jLMaSoBL.setBounds(430, 90, 80, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Khối lượng balo:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(300, 130, 120, 17);

        jLKLBL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLKLBL.setText("kl");
        jPanel1.add(jLKLBL);
        jLKLBL.setBounds(430, 130, 80, 17);

        jBHuy.setBackground(new java.awt.Color(155, 151, 84));
        jBHuy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBHuy.setText("HỦY");
        jBHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHuyActionPerformed(evt);
            }
        });
        jPanel1.add(jBHuy);
        jBHuy.setBounds(640, 330, 110, 25);

        backGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Balo/background.png"))); // NOI18N
        jPanel1.add(backGround);
        backGround.setBounds(-10, -10, 1070, 720);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1005, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTGiaTriCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTGiaTriCaretUpdate
        kiemTraRong();
    }//GEN-LAST:event_jTGiaTriCaretUpdate

    private void jTKhoiLuongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTKhoiLuongCaretUpdate
        kiemTraRong();
    }//GEN-LAST:event_jTKhoiLuongCaretUpdate

    private void jTTenDVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTTenDVCaretUpdate
        kiemTraRong();
    }//GEN-LAST:event_jTTenDVCaretUpdate

    private void jBDatRongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDatRongActionPerformed
        setRong();
    }//GEN-LAST:event_jBDatRongActionPerformed

    private void jBThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBThemDVActionPerformed
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        if (dsBL.kiemTraLaSo(jTGiaTri.getText()) && dsBL.kiemTraLaSo(jTKhoiLuong.getText())) {
            String tenDV = dsBL.xoaKhoangTrangDu(jTTenDV.getText());
            if (kiemTraTenDVKhiThem(tenDV, balo.getId())) {
                if (kTraKLuongDV(Double.parseDouble(jTKhoiLuong.getText()), balo.getKhoiLuongBL())) {
                    DoVat dv = new DoVat(layDuLieuDV());
                    insertDataDoVat(dv);

                    jLDanhSachDV.setVisible(true);
                    int idDV = getIdDoVat(balo.getId(), jTTenDV.getText());
                    System.out.print("id Balo: " + balo.getId() + " Ten: " +  jTTenDV.getText() + "id DV: " + idDV);
                    listDoVat.get(listDoVat.size() - 1).setId(idDV);
                    
                    updateSoLuongDV(listDoVat.size(), balo.getId());
                    setRong();
                    jBChonThuatToan.setEnabled(true);
                    jBCapNhat.setEnabled(false);
                    jBXoa.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_jBThemDVActionPerformed

    private void jBCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCapNhatActionPerformed

        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        if (dsBL.kiemTraLaSo(jTKhoiLuong.getText()) && dsBL.kiemTraLaSo(jTGiaTri.getText())) {
            if (kiemTraTenDV(jTTenDV.getText(), balo.getId(), chonDong, dvatTemp.getTen())) {
                if (kTraKLuongDV(Double.parseDouble(jTKhoiLuong.getText()), balo.getKhoiLuongBL())) {
                    System.out.print("Chỉ số dv: " + jTListDV.getSelectedRow() + "\n Id đồ vật: " + listDoVat.get(jTListDV.getSelectedRow()).getId());

                    updateDataDoVat(listDoVat.get(jTListDV.getSelectedRow()).getId());

                    jBChonThuatToan.setEnabled(true);
                    jBCapNhat.setEnabled(false);
                    jBXoa.setEnabled(false);
                    
                }
            }
            
            
        }

    }//GEN-LAST:event_jBCapNhatActionPerformed

    private void jBXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXoaActionPerformed
        deleteDataDoVat(listDoVat.get(jTListDV.getSelectedRow()).getId());
        jBChonThuatToan.setEnabled(true);
        System.out.print("\nSize của listDoVat là: " + listDoVat.size() + "\nSo61 lượng dv: " + balo.getSoLuong() + "\n");
    }//GEN-LAST:event_jBXoaActionPerformed

    private void jBChonThuatToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChonThuatToanActionPerformed
        
        balo.setSoLuong(listDoVat.size());
        updateSoLuongDV(listDoVat.size(), balo.getId());
        System.out.print("\nSố lượng DV (tiếp tục) = " + balo.getSoLuong());
        this.setVisible(false);
        PhuongPhapGiai tt = new PhuongPhapGiai();
        tt.setVisible(true);
    }//GEN-LAST:event_jBChonThuatToanActionPerformed

    private void jTListDVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTListDVMouseClicked

        jTListDV.setSelectionBackground(new Color(42,97,252));
        
        model = (DefaultTableModel) jTListDV.getModel();
        String t = new String((String) model.getValueAt(jTListDV.getSelectedRow(), 1));
        Double kl = ((Double) model.getValueAt(jTListDV.getSelectedRow(), 2));
        Double gt = ((Double) model.getValueAt(jTListDV.getSelectedRow(), 3));

        jTTenDV.setText(t);
        jTKhoiLuong.setText(""+kl);
        jTGiaTri.setText(""+gt);

        dvatTemp = new DoVat();
        if (balo.getLoai() == 2) {
            int sl = (int) model.getValueAt(jTListDV.getSelectedRow(), 4);
            jSSoLuong.setValue(sl);
            dvatTemp.setSoLuong(sl);
        }

        dvatTemp.setTen(t);
        dvatTemp.setKLuong(kl);
        dvatTemp.setGTri(gt);

        chonDong = 1;
        jBThemDV.setEnabled(false);
        jBDatRong.setEnabled(false);
        jBCapNhat.setEnabled(true);
        jBXoa.setEnabled(true);
        jBChonThuatToan.setEnabled(false);
    }//GEN-LAST:event_jTListDVMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
       
        balo = new Balo(dsBL.balo);
        jLDanhSachDV.setVisible(true);
        System.out.print("\nLoại balo (Từ danh sách bl): " + balo.getLoai() + "\n");
        jBChonThuatToan.setEnabled(true); 

        if (balo.getSoLuong() == 0) {
            jBChonThuatToan.setEnabled(false);
        }
        
        jLMaSoBL.setText(balo.getMaSo());
        jLKLBL.setText("" + balo.getKhoiLuongBL());
        listDoVat = new ArrayList<>();
        getDataDoVat(balo.getId());
        xacDinhLoaiBL();
    }//GEN-LAST:event_formWindowOpened

    private void jBTroLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTroLaiActionPerformed
        this.setVisible(false);
        DanhSachThemBalo dsBL = new DanhSachThemBalo();
        
        dsBL.setVisible(true);
        dsBL.bienDSBL = 0;
        
        
    }//GEN-LAST:event_jBTroLaiActionPerformed

    private void jBTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTrangChuActionPerformed
        GiaoDienChinh gd = new GiaoDienChinh();
        this.setVisible(false);
        gd.setVisible(true);
    }//GEN-LAST:event_jBTrangChuActionPerformed

    private void jBHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHuyActionPerformed
        chonDong = 0;
        setRong();
        jTListDV.setRowSelectionAllowed(false);
        
        jBCapNhat.setEnabled(false);
        jBXoa.setEnabled(false);
        jBChonThuatToan.setEnabled(true);
        jBDatRong.setEnabled(true);
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
            java.util.logging.Logger.getLogger(DanhSachThemDoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemDoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemDoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhSachThemDoVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachThemDoVat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backGround;
    private javax.swing.JButton jBCapNhat;
    private javax.swing.JButton jBChonThuatToan;
    private javax.swing.JButton jBDatRong;
    private javax.swing.JButton jBHuy;
    private javax.swing.JButton jBThemDV;
    private javax.swing.JButton jBTrangChu;
    private javax.swing.JButton jBTroLai;
    private javax.swing.JButton jBXoa;
    private javax.swing.JLabel jLDanhSachDV;
    private javax.swing.JLabel jLKLBL;
    private javax.swing.JLabel jLMaSoBL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSSoLuong;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTGiaTri;
    private javax.swing.JTextField jTKhoiLuong;
    private javax.swing.JTable jTListDV;
    private javax.swing.JTextField jTTenDV;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
