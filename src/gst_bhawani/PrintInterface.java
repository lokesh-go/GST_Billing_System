/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gst_bhawani;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */

public class PrintInterface extends javax.swing.JFrame {

    /**
     * Creates new form PrintInterface
     */
    public PrintInterface(String getVal) {
        initComponents();
        // BillLast bl = new BillLast();
      
        getValueIn(getVal);
        
        setDefaultCloseOperation(PrintInterface.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    //    printInterface();
    setResizable(false);
    }
    
    void printInterface()
    {
     
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");
        job.setPrintable(new Printable(){
        
            public int print(Graphics pg,PageFormat pf, int pageNum)
            {
                if(pageNum>0)
                    return Printable.NO_SUCH_PAGE;
                Graphics2D g2 = (Graphics2D)pg;
                g2.translate((int)pf.getImageableX()+5, (int)pf.getImageableY()+5);
                g2.scale(0.55,0.6);
                
                jPanel3.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        
        });
        boolean ok = job.printDialog();
        if(ok)
        {
            try{
                job.print();
            }
            catch(PrinterException ex)
            {
                System.out.println(ex);
            }
        }
    }
    void getValueIn(String invo)
    {
      
        
        
        //Databse Connection start...........................
        PreparedStatement ptmt,ptmt1;
        ResultSet rs,rs1;
        int row=0,v=1,cgs=0,sgs=0,di=0;
        double pdt,cal1,cal2,calt,tcgst=0.0,tsgst=0.0,tm =0.0;
        DefaultTableModel model = (DefaultTableModel) billTable.getModel();
      
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Date date = new Date();
        
        MySqlConnection my = new MySqlConnection();
        try
        {
        ptmt = MySqlConnection.conn.prepareStatement("select * from product_details where Invoice_No=?"); // get last row value from db.
        ptmt1 = MySqlConnection.conn.prepareStatement("select * from bill_entry where Invoice_No=?");
        ptmt.setString(1,invo);
        ptmt1.setString(1,invo);
        rs = ptmt.executeQuery();
        rs1 = ptmt1.executeQuery();
    
        while(rs.next())
        {
          //  model.addRow(new Object[]{rs.getString("Product_Name"),rs.getString("HSN_Code"),rs.getString("Serial_No"),rs.getString("Quantity"),rs.getString("Price"),rs.getString("Total")});
            model.setValueAt(v,row,0);
            model.setValueAt(rs.getString("Product_Name"),row,1);
            model.setValueAt(rs.getString("HSN_Code"),row,2);
            model.setValueAt(rs.getString("Quantity"),row,3);
            model.setValueAt(rs.getString("Price"),row,4);
            pdt = Double.parseDouble(rs.getString("Total"));
            tm += pdt;
           
           while(rs1.next())
           {
            customerNameB.setText(rs1.getString("Customer_Name"));
            addressB.setText(rs1.getString("Address"));
            mobileNoB.setText(rs1.getString("Mobile_No"));
            gstinB.setText(rs1.getString("GSTIN_No"));
            aadharNoB.setText(rs1.getString("Aadhar_No"));
            invoiceNoB.setText(invo);
            dateB.setText(formatter.format(date));
            grandTotal.setText(rs1.getString("Total_Price"));
            cgs = Integer.parseInt(rs1.getString("C_GST"));
            sgs = Integer.parseInt(rs1.getString("S_GST"));
            taxRate.setText(Integer.toString(cgs+sgs));
            
            di = Integer.parseInt(rs1.getString("Discount"));
           }
            model.setValueAt(di,row,9);
            model.setValueAt(cgs,row,5);
            cal1 = pdt * (cgs/100.00);
            tcgst+=cal1;
            model.setValueAt(new DecimalFormat("##.##").format(cal1),row,6);
            model.setValueAt(sgs,row,7);
            cal2 = pdt * (sgs/100.00);
            tsgst += cal2;
            model.setValueAt(new DecimalFormat("##.##").format(cal2),row,8);
            
            
            calt = (pdt+cal1+cal2) - ((pdt+cal1+cal2)*di/100.00);
            model.setValueAt(new DecimalFormat("##.##").format(calt),row,10);
            row++;
            v++;
  //          resu += Double.parseDouble(rs.getString("Total"));
        }
        cgstB.setText(new DecimalFormat("##.##").format(tcgst));
        sgstB.setText(new DecimalFormat("##.##").format(tsgst));
        totalTaxB.setText(new DecimalFormat("##.##").format(tcgst+tsgst));
        taxableAmt.setText(new DecimalFormat("##.##").format(tm));
        
        cgstB.setEditable(false);
        sgstB.setEditable(false);
        totalTaxB.setEditable(false);
        taxableAmt.setEditable(false);
        taxRate.setEditable(false);
        grandTotal.setEditable(false);
        dateB.setEditable(false);
        invoiceNoB.setEditable(false);
        aadharNoB.setEditable(false);
        gstinB.setEditable(false);
        mobileNoB.setEditable(false);
        addressB.setEditable(false);
        customerNameB.setEditable(false);
        billTable.setEnabled(false);
    //           prototalprice.setText(new DecimalFormat("##.##").format(resu));
        
        }
        catch(NumberFormatException | SQLException e)
        {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        grandTotal = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        taxableAmt = new javax.swing.JTextField();
        cgstB = new javax.swing.JTextField();
        sgstB = new javax.swing.JTextField();
        totalTaxB = new javax.swing.JTextField();
        taxRate = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        invoiceNoB = new javax.swing.JTextField();
        mobileNoB = new javax.swing.JTextField();
        gstinB = new javax.swing.JTextField();
        aadharNoB = new javax.swing.JTextField();
        customerNameB = new javax.swing.JTextField();
        dateB = new javax.swing.JTextField();
        addressB = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        jLabel1.setText("BHAWANI COMPUTERS");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, -1, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("GSTIN : 10BQZPK0310H2Z5");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel3.setText("email : bhawanicomputers.kgg@gmail.com");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 400, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Taxable Amt.");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 1020, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel5.setText("Invoice No :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 120, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 1080, 10));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Bank Detail : SBI Bank, A/C No. : 33647300765, IFSC : SBIN0006609");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 1090, -1, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel7.setText("Mobile No :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 120, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel8.setText("GSTIN / UIN :");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel9.setText("Aadhar No :");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 120, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel11.setText("Customer Name :");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 170, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel10.setText("Date :");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, 70, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel12.setText("Place of Supply : ");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, 170, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 21)); // NOI18N
        jLabel13.setText("Address :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 100, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 1080, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 10, 210));

        billTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "S.N.", "Product Name", "HSN Code", "Qty.", "Base Price", "CGST Rate", "CGST Amount", "SGST Rate", "SGST Amount", "Discount", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(billTable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 1060, 570));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1040, 600, 10));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel14.setText("TAX INVOICE");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel15.setText("CGST");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 1020, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel16.setText("SGST");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 1020, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gst_bhawani/images/icons8_Rupee_20px.png"))); // NOI18N
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 970, 20, 40));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel18.setText("3. Subject to 'Khagaria' Jurisdiction only.");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1230, 280, -1));

        grandTotal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        grandTotal.setBorder(null);
        grandTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grandTotalActionPerformed(evt);
            }
        });
        jPanel3.add(grandTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 972, 120, 40));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel19.setText("Total Tax");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 1020, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel20.setText("Grand Total");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 970, 90, 30));

        taxableAmt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        taxableAmt.setBorder(null);
        jPanel3.add(taxableAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 1040, 100, 30));

        cgstB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cgstB.setBorder(null);
        jPanel3.add(cgstB, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 1040, 100, 30));

        sgstB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sgstB.setBorder(null);
        jPanel3.add(sgstB, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 1040, 100, 30));

        totalTaxB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalTaxB.setBorder(null);
        jPanel3.add(totalTaxB, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 1040, 110, 30));

        taxRate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        taxRate.setBorder(null);
        jPanel3.add(taxRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1040, 50, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1260, 1060, 10));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        jLabel21.setText("Bihar (10)");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 290, -1, 30));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1090, 1060, 10));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel22.setText("Tax Rate");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1020, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("Authorised Signatory");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 1230, -1, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel24.setText("E.& O.E.");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1150, 60, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel25.setText("1. Goods once sold will not be taken back.");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1170, 290, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel26.setText("2. Interest @ 18% p.a. will be charged if the payment");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1190, 360, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel27.setText("is not made with in the stipulated time.");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1210, 260, -1));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1120, 1060, 10));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 1120, 10, 140));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel28.setText("Term & Conditions");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1130, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel29.setText("Receiver's Signature :");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 1150, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel30.setText("for");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 1170, -1, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setText("BHAWANI COMPUTERS");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 1170, -1, 30));

        invoiceNoB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        invoiceNoB.setBorder(null);
        jPanel3.add(invoiceNoB, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 190, 270, 30));

        mobileNoB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mobileNoB.setBorder(null);
        mobileNoB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobileNoBActionPerformed(evt);
            }
        });
        jPanel3.add(mobileNoB, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 330, 30));

        gstinB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gstinB.setBorder(null);
        jPanel3.add(gstinB, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 330, 30));

        aadharNoB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        aadharNoB.setBorder(null);
        jPanel3.add(aadharNoB, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 330, 30));

        customerNameB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        customerNameB.setBorder(null);
        customerNameB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerNameBActionPerformed(evt);
            }
        });
        jPanel3.add(customerNameB, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 330, 30));

        dateB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dateB.setBorder(null);
        jPanel3.add(dateB, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 240, 270, 30));

        addressB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        addressB.setBorder(null);
        jPanel3.add(addressB, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 330, 30));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        jLabel32.setText("GAUSHALA ROAD, KHAGARIA");
        jPanel3.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 116, 300, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 1310));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 0, 153));
        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(860, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 37, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1310, 1060, 70));

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1089, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grandTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grandTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grandTotalActionPerformed

    private void customerNameBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerNameBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerNameBActionPerformed

    private void mobileNoBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobileNoBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobileNoBActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        printInterface();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PrintInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String getVal2=null;
                new PrintInterface(getVal2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aadharNoB;
    private javax.swing.JTextField addressB;
    private javax.swing.JTable billTable;
    private javax.swing.JTextField cgstB;
    private javax.swing.JTextField customerNameB;
    private javax.swing.JTextField dateB;
    private javax.swing.JTextField grandTotal;
    private javax.swing.JTextField gstinB;
    private javax.swing.JTextField invoiceNoB;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField mobileNoB;
    private javax.swing.JTextField sgstB;
    private javax.swing.JTextField taxRate;
    private javax.swing.JTextField taxableAmt;
    private javax.swing.JTextField totalTaxB;
    // End of variables declaration//GEN-END:variables
}
