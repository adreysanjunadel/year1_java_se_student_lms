package gui;

import static gui.TeacherHome.subjectMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class StudentClassPayments extends javax.swing.JFrame {

    public static HashMap<String, Integer> teacherMap = new HashMap<>();
    private int tno; // Declare tno as an instance variable
    private int subno; // Declare subno as an instance variable

    model.StudentSession session = model.StudentSession.getInstance();
    String sno = session.getSno();
    String firstName = session.getFirstName();
    String lastName = session.getLastName();

    public StudentClassPayments() {
        initComponents();
        jLabel5.setText(firstName);
        jLabel12.setText(lastName);

        Thread timeThread = new Thread(runnable1);
        timeThread.start();

        loadTeachers();
        loadSubjects();

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedTeacher = jComboBox1.getSelectedItem().toString();
                if (!selectedTeacher.equals("Select")) {
                    tno = teacherMap.get(selectedTeacher); // Assign the value to the instance variable
                    loadTeacherSubjects(tno);
                } else {
                    resetSubjects();
                }
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedSubject = jComboBox2.getSelectedItem().toString();
                subno = subjectMap.get(selectedSubject); // Assign the value to the instance variable

                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
                Date date = new Date();
                String currentMonth = monthFormat.format(date);
                jLabel14.setText(currentMonth);

                try {
                    ResultSet resultSet = MySQL.execute("SELECT `price` FROM `subject` WHERE `subno` = " + subno);
                    if (resultSet.next()) {
                        int price = resultSet.getInt("price");
                        jLabel3.setText(String.valueOf(price));
                    }
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void resetSubjects() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        jComboBox2.setModel(model);
    }

    java.lang.Runnable runnable1 = new java.lang.Runnable() {
        @Override
        public void run() {
            while (true) {
                java.util.Date date1 = new java.util.Date();
                java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("hh:mm:ss a yyyy-MM-dd");
                String stringDate1 = format1.format(date1);
                jLabel13.setText(stringDate1);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void loadTeachers() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `teacher`");
            Vector<String> v = new Vector<>();
            v.add("Select");

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fullName = firstName + " " + lastName;
                v.add(fullName);
                int tno = resultSet.getInt("tno");
                teacherMap.put(fullName, tno);
            }

            DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(v);
            jComboBox1.setModel(model1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeacherSubjects(int tno) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `subject` "
                    + "INNER JOIN `teacher_has_subject` ON `subject`.`subno` = `teacher_has_subject`.`subject_subno` "
                    + "INNER JOIN `teacher` ON `teacher_has_subject`.`teacher_tno` = `teacher`.`tno` "
                    + "WHERE `tno` = '" + tno + "' ");

            Vector<String> v = new Vector<>();
            v.add("Select");

            while (resultSet.next()) {
                String description = resultSet.getString("description");
                v.add(description);
                int subno = resultSet.getInt("subno");
                subjectMap.put(description, subno);
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(v);
            jComboBox2.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSubjects() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `subject`");
            Vector<String> v = new Vector<>();
            v.add("Select");

            while (resultSet.next()) {
                String description = resultSet.getString("description");
                int subno = resultSet.getInt("subno");
                subjectMap.put(description, subno);
            }

            DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(v);
            jComboBox2.setModel(model2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        loadTeachers(); // Reset the teacher combo box
        resetSubjects(); // Reset the subject combo box

        jLabel14.setText(""); // Clear the month label
        jLabel3.setText(""); // Clear the price label
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
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Adyapana");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home-button.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel2.setText("Class Payments");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Subject");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Teacher");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Amount Payable");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("*** EACH PAYMENT CYCLE IS ONCE PER 4 WEEKS");

        jButton2.setText("Pay Now");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/graduation-hat (1).png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("...");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("...");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Month");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("...");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Rs.");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(327, 327, 327))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(306, 306, 306)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel2)
                .addGap(110, 110, 110)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel8)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(125, 125, 125)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addComponent(jButton1))))
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel15))
                .addGap(38, 38, 38)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();

        StudentHome h = new StudentHome();
        h.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String teacher = String.valueOf(jComboBox1.getSelectedItem().toString());
            String subdesc = String.valueOf(jComboBox2.getSelectedItem().toString());
            String month = jLabel14.getText();
            String price = jLabel3.getText();

            if (teacher.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid Teacher", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if (subdesc.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid Subject", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if (month.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Month", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if (price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Price", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int tno = teacherMap.get(teacher); // Get the tno value from the teacherMap
                    int subno = subjectMap.get(subdesc); // Get the subno value from the subjectMap

                    // Check if sno, tno, and subno are valid values by querying the respective tables
                    ResultSet studentResultSet = MySQL.execute("SELECT * FROM `student` WHERE `sno` = '" + sno + "'");
                    ResultSet teacherResultSet = MySQL.execute("SELECT * FROM `teacher` WHERE `tno` = '" + tno + "'");
                    ResultSet subjectResultSet = MySQL.execute("SELECT * FROM `subject` WHERE `subno` = '" + subno + "'");

                    if (studentResultSet.next() && teacherResultSet.next() && subjectResultSet.next()) {
                        // valid foreign keys >> inserting

                        // Check if fees with similar data have been paid previously
                        ResultSet invoiceResultSet = MySQL.execute("SELECT * FROM `invoice` "
                                + "WHERE `sno` = '" + sno + "' "
                                + "AND `tno` = '" + tno + "' "
                                + "AND `subno` = '" + subno + "' "
                                + "AND `month` = '" + month + "' "
                                + "AND `value` = '" + price + "'");

                        if (invoiceResultSet.next()) {
                            // Fees with similar data have been paid previously, show a warning message
                            JOptionPane.showMessageDialog(this, "Fees have already been paid.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            // Fees with similar data have not been paid previously, proceed with the insert
                            // Retrieve the classno based on the selected teacher and subject
                            ResultSet classResultSet = MySQL.execute("SELECT `class`.`classno`,`class`.`subno` "
                                    + "FROM `class` "
                                    + "INNER JOIN `teacher_has_subject` ON `class`.`subno` = `teacher_has_subject`.`subject_subno` "
                                    + "INNER JOIN `teacher` ON `teacher_has_subject`.`teacher_tno` = `teacher`.`tno` "
                                    + "WHERE `teacher`.`tno` = " + tno + " AND `teacher_has_subject`.`subject_subno` = " + subno + " ");
                            if (classResultSet.next()) {
                                int classno = classResultSet.getInt("classno");

                                // Insert into invoice table
                                MySQL.execute("INSERT INTO `invoice`(`sno`, `tno`, `subno`, `month`, `value`) "
                                        + "VALUES('" + sno + "','" + tno + "','" + subno + "','" + month + "','" + price + "')");

                                // Retrieve the classno based on the selected teacher and subject
                                ResultSet stusubrs = MySQL.execute("SELECT `stu`.`classno`,`class`.`subno` "
                                        + "FROM `class` "
                                        + "INNER JOIN `teacher_has_subject` ON `class`.`subno` = `teacher_has_subject`.`subject_subno` "
                                        + "INNER JOIN `teacher` ON `teacher_has_subject`.`teacher_tno` = `teacher`.`tno` "
                                        + "WHERE `teacher`.`tno` = " + tno + " AND `teacher_has_subject`.`subject_subno` = " + subno + " ");

                                // Assign the subject to the student in student_has_subject table
                                try {
                                    MySQL.execute("INSERT INTO `student_has_subject`(`student_sno`, `subno`) "
                                            + "VALUES('" + sno + "', '" + subno + "')");
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(this, "Not Updated", "Subject Status", JOptionPane.WARNING_MESSAGE);
                                }

                                // Assign the class to the student in student_has_class table
                                try {
                                    MySQL.execute("INSERT INTO `student_has_class`(`student_sno`, `classno`, `subno`) "
                                            + "VALUES('" + sno + "', '" + classno + "', '" + subno + "')");
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(this, "Not Updated", "Class Status", JOptionPane.WARNING_MESSAGE);
                                }

                                JOptionPane.showMessageDialog(this, "Success", "Subject Payment Status", JOptionPane.INFORMATION_MESSAGE);

                                try {
                                    // Retrieve the invoice ID for the newly inserted invoice
                                    ResultSet invoiceIdResultSet = MySQL.execute("SELECT LAST_INSERT_ID() AS `id`");
                                    int invoiceId = 0;
                                    if (invoiceIdResultSet.next()) {
                                        invoiceId = invoiceIdResultSet.getInt("id");
                                    }

                                    Date date = new Date();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String stringDate = dateFormat.format(date);

                                    HashMap<String, Object> parameters = new HashMap<>();
                                    parameters.put("Parameter1", stringDate);
                                    parameters.put("Parameter2", invoiceId); // Pass the invoice ID as a parameter

                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana", "root", "[1nTh3Night]");

                                    JasperPrint report = JasperFillManager.fillReport("src/reports/adyapana_invoice.jasper", parameters, connection);
                                    JasperViewer.viewReport(report, false);

                                    connection.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                reset();
                            } else {
                                // No matching class found, show an error message
                                JOptionPane.showMessageDialog(this, "No matching class found for the selected teacher and subject", "Warning", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        // Invalid foreign key value(s), show an error message
                        JOptionPane.showMessageDialog(this, "Invalid Student, Teacher, or Subject", "Warning", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(StudentClassPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentClassPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentClassPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentClassPayments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentClassPayments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
