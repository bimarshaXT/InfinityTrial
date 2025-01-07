package com.infinitytrailapp.view;

import com.infinitytrailapp.algorithm.Binarysort;
import com.infinitytrailapp.algorithm.InsertionSort;
import com.infinitytrailapp.algorithm.MergeSort;
import com.infinitytrailapp.algorithm.SelectionSort;
import com.infinitytrailapp.controller.CandidateQueueProcessor;
import com.infinitytrailapp.controller.ValidationUtil;
import com.infinitytrailapp.model.CandidateModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bimarsha
 */
public class InfinityTrail extends javax.swing.JFrame {

    private List<CandidateModel> CandidateList;
    private List<CandidateModel> originalCandidateList;
    private java.awt.CardLayout cardLayout;
    private final Color errorColor = new Color(255, 51, 0);
    private final Color yellowColor = new Color(0, 0, 0);
    private CandidateQueueProcessor queueProcessor;

    /**
     * Creates new form CollegeApp
     */
    public InfinityTrail() {
        initComponents();
        initializeLayout(); // Set up CardLayout and add screens
        queueProcessor = new CandidateQueueProcessor((DefaultTableModel) tblResults.getModel());
        initializeData(); // Initialize student data and table
        startProgress(); // Show loading screen and initiate progress

        initializeTrialQueue();
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a confirmation dialog
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to clear the form?",
                        "Confirm Clear",
                        JOptionPane.YES_NO_OPTION
                );

                // If the user clicks "Yes", clear the form
                if (response == JOptionPane.YES_OPTION) {
                    clearCandidateForm(); // Call the method to clear the form
                }
            }
        });
        btnAscending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String selectedItem = (String) ComboSort.getSelectedItem();
                if ("Candidate No".equals(selectedItem)) {
                    CandidateList = SelectionSort.selectionSort(CandidateList, true);
                    loadListToTable(CandidateList); // Refresh the table
                } else if ("Name".equals(selectedItem)) {
                    MergeSort.sortNames(CandidateList, true);
                    loadListToTable(CandidateList);
                } else if ("Date Of Birth".equals(selectedItem)) {
                    InsertionSort.insertionSortDate(CandidateList, true);
                    loadListToTable(CandidateList);
                }
            }
        });

        btnDescending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String selectedItem = (String) ComboSort.getSelectedItem();
                if ("Candidate No".equals(selectedItem)) {
                    CandidateList = SelectionSort.selectionSort(CandidateList, false);
                    loadListToTable(CandidateList); // Refresh the table
                } else if ("Name".equals(selectedItem)) {
                    MergeSort.sortNames(CandidateList, false);
                    loadListToTable(CandidateList);
                } else if ("Date Of Birth".equals(selectedItem)) {
                    InsertionSort.insertionSortDate(CandidateList, false);
                    loadListToTable(CandidateList);
                }
            }
        });

        btnSearch.addActionListener(evt -> {
            String searchValue = txtBinarySearch.getText().trim();
            String searchCriteria = (String) jComboBox1.getSelectedItem();

            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value to search.");
                return;
            }

            CandidateModel result = null;

            switch (searchCriteria) {
                case "Candidate ID":
                    try {
                        int candidateNo = Integer.parseInt(searchValue);
                        SelectionSort.selectionSort(CandidateList, true); // Ensure sorted list
                        result = Binarysort.binarySearchByCandidateNo(candidateNo, CandidateList, 0, CandidateList.size() - 1);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid Candidate ID format.");
                        return;
                    }
                    break;

                case "Name":
                    MergeSort.sortNames(CandidateList, true); // Ensure sorted list
                    result = Binarysort.binarySearchByName(searchValue, CandidateList, 0, CandidateList.size() - 1);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Please select a valid search criterion.");
                    return;
            }

            if (result != null) {
                // Display all information about the found candidate
                JOptionPane.showMessageDialog(
                        null,
                        "Candidate Found:\n"
                        + "Candidate No: " + result.getCandidateNo() + "\n"
                        + "Name: " + result.getName() + "\n"
                        + "Contact: " + result.getContact() + "\n"
                        + "Category: " + result.getCategory() + "\n"
                        + "Type: " + result.getType() + "\n"
                        + "Citizenship No: " + result.getCitizenshipNo() + "\n"
                        + "Date of Birth: " + result.getDateOfBirth(),
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(null, "Candidate Not Found.", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnReset.addActionListener(evt -> {
            // Reload the table with the original candidate list
            CandidateList = new LinkedList<>(originalCandidateList);
            loadListToTable(CandidateList);
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMainScreen = new javax.swing.JPanel();
        pnlMainBar = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        lbllogo = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tabPaneMain = new javax.swing.JTabbedPane();
        pnlHome = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblimageHome = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblMainBarSlogan = new javax.swing.JLabel();
        pnlCandidateList = new javax.swing.JPanel();
        spTblStudent = new javax.swing.JScrollPane();
        tblCandidate = new javax.swing.JTable();
        lblTblCandidateTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtFldContact = new javax.swing.JTextField();
        txtFldName = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        ComboCategory = new javax.swing.JComboBox<>();
        ComboType = new javax.swing.JComboBox<>();
        txtCandidateNo = new javax.swing.JTextField();
        lblErrorMsgCategory = new javax.swing.JLabel();
        lblErrorMsgType = new javax.swing.JLabel();
        lblErrorMsgCandidateId = new javax.swing.JLabel();
        lblErrorMsgName = new javax.swing.JLabel();
        lblErrorMsgContact = new javax.swing.JLabel();
        lblErrorMsgCitizenshipNo = new javax.swing.JLabel();
        txtFldCitizenshipNo = new javax.swing.JTextField();
        lblErrorMsgDOB = new javax.swing.JLabel();
        txtFldDOB = new javax.swing.JTextField();
        ComboSort = new javax.swing.JComboBox<>();
        btnAscending = new javax.swing.JButton();
        btnDescending = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        txtBinarySearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        pnlAboutUs = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        pnlAdminControl = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Results = new javax.swing.JPanel();
        spTblStudent1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        btnStartTrial = new javax.swing.JButton();
        pnlLoginScreen = new javax.swing.JPanel();
        pnlLoginLeft = new javax.swing.JPanel();
        lblLoginForgotPwd = new javax.swing.JLabel();
        lblLoginError = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtFldLoginUsername = new javax.swing.JTextField();
        pwdFldLogin = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblLogInError = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnlLoadingScreen = new javax.swing.JPanel();
        pgBarSplashScreen = new javax.swing.JProgressBar(0,100);
        lblLoading = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        pnlMainScreen.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainScreen.setMaximumSize(new java.awt.Dimension(1130, 514));
        pnlMainScreen.setMinimumSize(new java.awt.Dimension(1130, 514));
        pnlMainScreen.setPreferredSize(new java.awt.Dimension(1130, 514));

        pnlMainBar.setBackground(new java.awt.Color(8, 31, 92));
        pnlMainBar.setForeground(new java.awt.Color(255, 255, 255));

        btnLogout.setBackground(new java.awt.Color(255, 0, 0));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Log out");
        btnLogout.setBorder(new javax.swing.border.MatteBorder(null));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lbllogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/logosmall.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 51, 51));
        jLabel27.setText("ONE LICENSE, INFINITE POSSIBILITIES");

        javax.swing.GroupLayout pnlMainBarLayout = new javax.swing.GroupLayout(pnlMainBar);
        pnlMainBar.setLayout(pnlMainBarLayout);
        pnlMainBarLayout.setHorizontalGroup(
            pnlMainBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainBarLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbllogo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        pnlMainBarLayout.setVerticalGroup(
            pnlMainBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbllogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabPaneMain.setBackground(new java.awt.Color(255, 204, 204));
        tabPaneMain.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        pnlHome.setBackground(new java.awt.Color(255, 249, 240));
        pnlHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Century", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Welcome to Infinity Trial! ");

        jPanel3.setBackground(new java.awt.Color(237, 241, 246));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(8, 31, 92)));

        jLabel4.setBackground(new java.awt.Color(237, 241, 246));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("<html>At Infinty Trail, we are committed to providing a hassle-free experience for all your driving license needs. <br> <br>Whether you're a first-time applicant or looking to update your license, ");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        jLabel5.setText("WE'VE GOT YOU COVERED!!");

        lblimageHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/license11.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 0, 24)); // NOI18N
        jLabel2.setText(" Your journey to the open road starts here!");

        jButton1.setBackground(new java.awt.Color(0, 51, 102));
        jButton1.setForeground(new java.awt.Color(242, 240, 222));
        jButton1.setText("Register Now!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 86, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblimageHome)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblimageHome, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        lblMainBarSlogan.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        lblMainBarSlogan.setForeground(new java.awt.Color(255, 51, 51));
        lblMainBarSlogan.setText("Drive Your Dreams: Fast, Easy, Reliable License Services!");

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMainBarSlogan, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMainBarSlogan, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        tabPaneMain.addTab("Home", pnlHome);

        pnlCandidateList.setBackground(new java.awt.Color(255, 249, 240));
        pnlCandidateList.setPreferredSize(new java.awt.Dimension(1400, 1000));

        tblCandidate.setBackground(new java.awt.Color(204, 204, 204));
        tblCandidate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Candidate No.", "Full Name", "Contact", "Applied Category", "Type", "Citizenship Number", "Date Of Birth"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCandidate.setGridColor(new java.awt.Color(0, 0, 0));
        tblCandidate.setSelectionBackground(new java.awt.Color(0, 0, 0));
        tblCandidate.setSelectionForeground(new java.awt.Color(234, 192, 32));
        tblCandidate.setShowGrid(true);
        tblCandidate.getTableHeader().setReorderingAllowed(false);
        spTblStudent.setViewportView(tblCandidate);
        if (tblCandidate.getColumnModel().getColumnCount() > 0) {
            tblCandidate.getColumnModel().getColumn(0).setResizable(false);
            tblCandidate.getColumnModel().getColumn(1).setResizable(false);
            tblCandidate.getColumnModel().getColumn(2).setResizable(false);
            tblCandidate.getColumnModel().getColumn(3).setResizable(false);
            tblCandidate.getColumnModel().getColumn(4).setResizable(false);
            tblCandidate.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblCandidate.getColumnModel().getColumn(5).setResizable(false);
            tblCandidate.getColumnModel().getColumn(6).setResizable(false);
        }

        lblTblCandidateTitle.setBackground(new java.awt.Color(204, 204, 255));
        lblTblCandidateTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTblCandidateTitle.setText("Candidate Information");
        lblTblCandidateTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        jPanel2.setBackground(new java.awt.Color(247, 242, 235));

        txtFldContact.setBackground(new java.awt.Color(255, 249, 240));
        txtFldContact.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));
        txtFldContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldContactActionPerformed(evt);
            }
        });

        txtFldName.setBackground(new java.awt.Color(255, 249, 240));
        txtFldName.setBorder(javax.swing.BorderFactory.createTitledBorder("Name"));
        txtFldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldNameActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");

        btnDelete.setText("Delete");

        btnClear.setText("Clear");

        btnAdd.setText("Add");

        ComboCategory.setBackground(new java.awt.Color(255, 249, 240));
        ComboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A (Bike, Scooter)", "B (Car, Jeep, Delivery Van)", "K (Scooter)" }));
        ComboCategory.setBorder(javax.swing.BorderFactory.createTitledBorder("Category"));
        ComboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCategoryActionPerformed(evt);
            }
        });

        ComboType.setBackground(new java.awt.Color(255, 249, 240));
        ComboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New License", "Add Category", "Retrial"}));
        ComboType.setBorder(javax.swing.BorderFactory.createTitledBorder("Type"));
        ComboType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTypeActionPerformed(evt);
            }
        });

        txtCandidateNo.setBackground(new java.awt.Color(255, 249, 240));
        txtCandidateNo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Candidate Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        txtCandidateNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCandidateNoActionPerformed(evt);
            }
        });

        lblErrorMsgCategory.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgCategory.setText(".");

        lblErrorMsgType.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgType.setText(".");

        lblErrorMsgCandidateId.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgCandidateId.setText(".");

        lblErrorMsgName.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgName.setText(".");

        lblErrorMsgContact.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgContact.setText(".");

        lblErrorMsgCitizenshipNo.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgCitizenshipNo.setText(".");

        txtFldCitizenshipNo.setBackground(new java.awt.Color(255, 249, 240));
        txtFldCitizenshipNo.setBorder(javax.swing.BorderFactory.createTitledBorder("Citizenship Number"));
        txtFldCitizenshipNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldCitizenshipNoActionPerformed(evt);
            }
        });

        lblErrorMsgDOB.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorMsgDOB.setText(".");

        txtFldDOB.setBackground(new java.awt.Color(255, 249, 240));
        txtFldDOB.setText("YYYY-MM-DD");
        txtFldDOB.setBorder(javax.swing.BorderFactory.createTitledBorder("Date of Birth"));
        txtFldDOB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldDOBActionPerformed(evt);
            }
        });

        ComboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By: ", "Candidate No", "Name", "Date Of Birth"}));

        btnAscending.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/arrow2.png"))); // NOI18N
        btnAscending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAscendingActionPerformed(evt);
            }
        });

        btnDescending.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/arrowdown1.png"))); // NOI18N

        btnReset.setText("Reset");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCandidateNo, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(txtFldName)
                    .addComponent(txtFldContact)
                    .addComponent(lblErrorMsgName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblErrorMsgCandidateId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblErrorMsgContact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(124, 124, 124)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboType, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorMsgCitizenshipNo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFldCitizenshipNo, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorMsgType, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorMsgCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(lblErrorMsgDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFldDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(107, 107, 107)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdd)
                            .addComponent(btnClear)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete))
                        .addGap(0, 21, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAscending)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDescending, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblErrorMsgCandidateId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCandidateNo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorMsgName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFldName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblErrorMsgContact)
                .addGap(0, 0, 0)
                .addComponent(txtFldContact, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblErrorMsgType)
                    .addComponent(lblErrorMsgDOB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear))
                            .addComponent(txtFldDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(ComboSort))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ComboType, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorMsgCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(lblErrorMsgCitizenshipNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFldCitizenshipNo, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                        .addGap(214, 214, 214))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDescending, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAscending, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        txtBinarySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBinarySearchActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Search By:", "Name", "Candidate ID"}));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCandidateListLayout = new javax.swing.GroupLayout(pnlCandidateList);
        pnlCandidateList.setLayout(pnlCandidateListLayout);
        pnlCandidateListLayout.setHorizontalGroup(
            pnlCandidateListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCandidateListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(pnlCandidateListLayout.createSequentialGroup()
                .addGroup(pnlCandidateListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCandidateListLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lblTblCandidateTitle)
                        .addGap(245, 245, 245)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(txtBinarySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnSearch))
                    .addGroup(pnlCandidateListLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(spTblStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 968, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCandidateListLayout.setVerticalGroup(
            pnlCandidateListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCandidateListLayout.createSequentialGroup()
                .addGroup(pnlCandidateListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCandidateListLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblTblCandidateTitle))
                    .addGroup(pnlCandidateListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlCandidateListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBinarySearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spTblStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabPaneMain.addTab("Admin Control", pnlCandidateList);
        pnlCandidateList.getAccessibleContext().setAccessibleName("");

        pnlAboutUs.setBackground(new java.awt.Color(186, 214, 235));
        pnlAboutUs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(234, 192, 32)));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/logoabitbig.png"))); // NOI18N

        jLabel20.setBackground(new java.awt.Color(8, 31, 92));
        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(8, 31, 92));
        jLabel20.setText("INFINITY TRIAL");

        jLabel21.setFont(new java.awt.Font("Segoe UI Variable", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("<html>Infinity Trial was established in 2005 and has since been dedicated to revolutionizing the driving license application process. <br>Over the years, it has streamlined data validation, simplified registrations, and introduced transparent progress tracking. <br>By continually evolving, Infinity Trial has become a trusted platform for applicants <br>and authorities, ensuring a smooth, efficient, and hassle-free experience.");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/trialcenter123.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(8, 31, 92));
        jLabel24.setText("Contact us at:");

        jLabel25.setText("<html>www.infinitytrial.com<br> or<br>infinitytrial@gmail.com");

        jLabel22.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(8, 31, 92));
        jLabel22.setText("WE ARE LOCATED AT:");

        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("<html>BALKOT, BHAKTAPUR<br>near Hanumante River");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout pnlAboutUsLayout = new javax.swing.GroupLayout(pnlAboutUs);
        pnlAboutUs.setLayout(pnlAboutUsLayout);
        pnlAboutUsLayout.setHorizontalGroup(
            pnlAboutUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAboutUsLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel19)
                .addGap(53, 53, 53)
                .addGroup(pnlAboutUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlAboutUsLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(171, 171, 171))
        );
        pnlAboutUsLayout.setVerticalGroup(
            pnlAboutUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAboutUsLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlAboutUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addGroup(pnlAboutUsLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(pnlAboutUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(327, 327, 327))
        );

        tabPaneMain.addTab("About Us", pnlAboutUs);

        pnlAdminControl.setBackground(new java.awt.Color(255, 249, 240));
        pnlAdminControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(234, 192, 32)));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/cartrial1.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/biketrial1.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/scootertrial1.png"))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(0, 51, 51));
        jLabel15.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); // NOI18N
        jLabel15.setText("CARS!!!");

        jLabel16.setBackground(new java.awt.Color(0, 51, 51));
        jLabel16.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); // NOI18N
        jLabel16.setText("Bikes!!!");

        jLabel17.setBackground(new java.awt.Color(0, 51, 51));
        jLabel17.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); // NOI18N
        jLabel17.setText("AND SCOOTERSS!!!");

        jLabel18.setBackground(new java.awt.Color(0, 51, 51));
        jLabel18.setFont(new java.awt.Font("Showcard Gothic", 0, 18)); // NOI18N
        jLabel18.setText("TAKE YOUR CHANCE NOW!");

        javax.swing.GroupLayout pnlAdminControlLayout = new javax.swing.GroupLayout(pnlAdminControl);
        pnlAdminControl.setLayout(pnlAdminControlLayout);
        pnlAdminControlLayout.setHorizontalGroup(
            pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminControlLayout.createSequentialGroup()
                .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlAdminControlLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdminControlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)))
                        .addGap(195, 195, 195)
                        .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGap(403, 403, 403)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(283, Short.MAX_VALUE))
        );
        pnlAdminControlLayout.setVerticalGroup(
            pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminControlLayout.createSequentialGroup()
                .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlAdminControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel13))
                    .addGroup(pnlAdminControlLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        tabPaneMain.addTab("Our Services", pnlAdminControl);

        Results.setBackground(new java.awt.Color(255, 249, 240));

        tblResults.setBackground(new java.awt.Color(204, 204, 204));
        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Candidate No.", "Full Name", "Contact", "Applied Category", "Type", "Citizenship No.", "Date Of Birth", "Status", "License No."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblResults.setGridColor(new java.awt.Color(0, 0, 0));
        tblResults.setSelectionBackground(new java.awt.Color(0, 0, 0));
        tblResults.setSelectionForeground(new java.awt.Color(234, 192, 32));
        tblResults.setShowGrid(true);
        tblResults.getTableHeader().setReorderingAllowed(false);
        spTblStudent1.setViewportView(tblResults);
        if (tblResults.getColumnModel().getColumnCount() > 0) {
            tblResults.getColumnModel().getColumn(0).setResizable(false);
            tblResults.getColumnModel().getColumn(1).setResizable(false);
            tblResults.getColumnModel().getColumn(2).setResizable(false);
            tblResults.getColumnModel().getColumn(3).setResizable(false);
            tblResults.getColumnModel().getColumn(4).setResizable(false);
            tblResults.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblResults.getColumnModel().getColumn(5).setResizable(false);
            tblResults.getColumnModel().getColumn(6).setResizable(false);
            tblResults.getColumnModel().getColumn(7).setResizable(false);
            tblResults.getColumnModel().getColumn(8).setResizable(false);
        }

        btnStartTrial.setText("Start Trial");

        javax.swing.GroupLayout ResultsLayout = new javax.swing.GroupLayout(Results);
        Results.setLayout(ResultsLayout);
        ResultsLayout.setHorizontalGroup(
            ResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTblStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, 1101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStartTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        ResultsLayout.setVerticalGroup(
            ResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResultsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnStartTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(spTblStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(371, Short.MAX_VALUE))
        );

        tabPaneMain.addTab("Result", Results);

        javax.swing.GroupLayout pnlMainScreenLayout = new javax.swing.GroupLayout(pnlMainScreen);
        pnlMainScreen.setLayout(pnlMainScreenLayout);
        pnlMainScreenLayout.setHorizontalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMainBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneMain)
                .addContainerGap())
        );
        pnlMainScreenLayout.setVerticalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainScreenLayout.createSequentialGroup()
                .addComponent(pnlMainBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
        );

        pnlLoginScreen.setBackground(new java.awt.Color(8, 31, 92));
        pnlLoginScreen.setMaximumSize(new java.awt.Dimension(1130, 514));
        pnlLoginScreen.setMinimumSize(new java.awt.Dimension(1130, 514));
        pnlLoginScreen.setPreferredSize(new java.awt.Dimension(1130, 514));

        pnlLoginLeft.setBackground(new java.awt.Color(102, 102, 102));
        pnlLoginLeft.setMaximumSize(new java.awt.Dimension(570, 514));
        pnlLoginLeft.setMinimumSize(new java.awt.Dimension(570, 514));
        pnlLoginLeft.setPreferredSize(new java.awt.Dimension(1130, 514));

        lblLoginForgotPwd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoginForgotPwd.setForeground(new java.awt.Color(51, 0, 255));
        lblLoginForgotPwd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoginForgotPwd.setText("Forgot your password?");

        javax.swing.GroupLayout pnlLoginLeftLayout = new javax.swing.GroupLayout(pnlLoginLeft);
        pnlLoginLeft.setLayout(pnlLoginLeftLayout);
        pnlLoginLeftLayout.setHorizontalGroup(
            pnlLoginLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLeftLayout.createSequentialGroup()
                .addGap(2134, 2134, 2134)
                .addComponent(lblLoginForgotPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3659, Short.MAX_VALUE))
        );
        pnlLoginLeftLayout.setVerticalGroup(
            pnlLoginLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLeftLayout.createSequentialGroup()
                .addContainerGap(164, Short.MAX_VALUE)
                .addComponent(lblLoginForgotPwd)
                .addGap(492, 492, 492))
        );

        lblLoginError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoginError.setForeground(new java.awt.Color(255, 0, 0));
        lblLoginError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 249, 240));

        jPanel4.setBackground(new java.awt.Color(255, 249, 240));

        txtFldLoginUsername.setBackground(new java.awt.Color(255, 249, 240));
        txtFldLoginUsername.setText("admin");
        txtFldLoginUsername.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2), "Username", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 204, 204))); // NOI18N
        txtFldLoginUsername.setCaretColor(new java.awt.Color(51, 153, 255));
        txtFldLoginUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldLoginUsernameActionPerformed(evt);
            }
        });

        pwdFldLogin.setBackground(new java.awt.Color(255, 249, 240));
        pwdFldLogin.setForeground(new java.awt.Color(8, 31, 92));
        pwdFldLogin.setText("admin");
        pwdFldLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2), "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 204, 204))); // NOI18N
        pwdFldLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwdFldLoginActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(179, 235, 242));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(0, 0, 204));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 204));
        jLabel11.setText("Forgot your Password?");

        lblLogInError.setForeground(new java.awt.Color(255, 0, 0));
        lblLogInError.setText(".");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addComponent(lblLogInError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pwdFldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(117, 117, 117))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblLogInError)
                .addGap(18, 18, 18)
                .addComponent(txtFldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pwdFldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jLabel9.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(45, 74, 88));
        jLabel9.setText("YOUR WAY TO LICENSING");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel9)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/oklogo.png"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(137, 207, 240));
        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(137, 207, 240));
        jLabel7.setText("INFINITY TRIAL");

        jLabel8.setBackground(new java.awt.Color(255, 0, 0));
        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 44, 44));
        jLabel8.setText("ONE LICENSE, INFINITE POSSIBILITIES");

        javax.swing.GroupLayout pnlLoginScreenLayout = new javax.swing.GroupLayout(pnlLoginScreen);
        pnlLoginScreen.setLayout(pnlLoginScreenLayout);
        pnlLoginScreenLayout.setHorizontalGroup(
            pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                .addGap(6585, 6585, 6585)
                .addComponent(lblLoginError, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
                .addGap(781, 781, 781))
            .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                .addGroup(pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addGroup(pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(121, 121, 121))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginScreenLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(299, 299, 299)
                .addComponent(pnlLoginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 6746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoginScreenLayout.setVerticalGroup(
            pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                .addGroup(pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlLoginLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
                    .addGroup(pnlLoginScreenLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLoginError))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1130, 514));
        setResizable(false);
        setSize(new java.awt.Dimension(1130, 514));

        pnlLoadingScreen.setBackground(new java.awt.Color(255, 249, 240));
        pnlLoadingScreen.setPreferredSize(new java.awt.Dimension(1130, 514));

        pgBarSplashScreen.setStringPainted(true);
        pgBarSplashScreen.setForeground(new java.awt.Color(0, 0, 0));
        pgBarSplashScreen.setBorder(null);
        pgBarSplashScreen.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        lblLoading.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(153, 204, 255));
        lblLoading.setText("Loading...");
        lblLoading.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel1.setBackground(new java.awt.Color(8, 31, 92));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/infinitytrailapp/resources/gif_3final.gif"))); // NOI18N

        javax.swing.GroupLayout pnlLoadingScreenLayout = new javax.swing.GroupLayout(pnlLoadingScreen);
        pnlLoadingScreen.setLayout(pnlLoadingScreenLayout);
        pnlLoadingScreenLayout.setHorizontalGroup(
            pnlLoadingScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadingScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoadingScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoadingScreenLayout.createSequentialGroup()
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pgBarSplashScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoadingScreenLayout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        pnlLoadingScreenLayout.setVerticalGroup(
            pnlLoadingScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadingScreenLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgBarSplashScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLoadingScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLoadingScreen, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// Method to set up the CardLayout and add panels

    private void initializeLayout() {
        cardLayout = new java.awt.CardLayout();
        getContentPane().setLayout(cardLayout);

        // Add panels with unique identifiers
        getContentPane().add(pnlLoadingScreen, "LoadingScreen");
        getContentPane().add(pnlLoginScreen, "LoginScreen");
        getContentPane().add(pnlMainScreen, "MainScreen");

        // Start with the loading screen
        cardLayout.show(getContentPane(), "LoadingScreen");
    }

    // Method to initialize data, including student list and table
    private void initializeData() {
        CandidateList = new LinkedList<>();

        // Manually adding candidates using the registerCandidate method
        registerCandidate(new CandidateModel(1001, "Sita Sharma", "9812345678", "A (Bike, Scooter)", "New License", "12-345-6", "1995-05-14"));
        registerCandidate(new CandidateModel(1002, "Ram Thapa", "9823456789", "B (Car, Jeep, Delivery Van)", "New License", "23-4-567", "1990-08-25"));
        registerCandidate(new CandidateModel(1003, "Krishna Bhandari", "9811122233", "A (Bike, Scooter)", "Add Category", "34-56-78", "1988-12-15"));
        registerCandidate(new CandidateModel(1004, "Laxmi Adhikari", "9822233445", "K (Scooter)", "New License", "4-5678-9", "1993-07-20"));
        registerCandidate(new CandidateModel(1005, "Gopal Shrestha", "9813344556", "B (Car, Jeep, Delivery Van)", "Retrial", "5-67-890", "1985-01-10"));
        registerCandidate(new CandidateModel(1006, "Sunita Raut", "9824455667", "A (Bike, Scooter)", "New License", "678-90-1", "1997-11-18"));
        registerCandidate(new CandidateModel(1007, "Manoj Karki", "9815566778", "K (Scooter)", "Add Category", "78-90-12", "1992-04-30"));
        registerCandidate(new CandidateModel(1008, "Pramila Gurung", "9826677889", "B (Car, Jeep, Delivery Van)", "New License", "8-90-123", "1989-09-05"));
        registerCandidate(new CandidateModel(1009, "Kishor Mahato", "9817788990", "A (Bike, Scooter)", "Retrial", "9-0-1234", "1996-06-11"));
        registerCandidate(new CandidateModel(1010, "Anita Lama", "9828899001", "K (Scooter)", "New License", "01-23-45", "1994-03-22"));

        originalCandidateList = new LinkedList<>(CandidateList);

        for (CandidateModel candidate : CandidateList) {
            queueProcessor.enqueueCandidate(candidate);
        }
    }

    // Method to simulate loading progress
    private void startProgress() {
        // Set custom color for the progress bar (Steel Blue)
        pgBarSplashScreen.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        pgBarSplashScreen.setForeground(new Color(70, 130, 180)); // Steel Blue
        pgBarSplashScreen.setBackground(new Color(240, 248, 255)); // Optional: Alice Blue (background)
        pgBarSplashScreen.setStringPainted(false); // Ensure no percentage or text is displayed

        javax.swing.SwingWorker<Void, Integer> worker = new javax.swing.SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Fill the progress bar in larger chunks (e.g., 10% increments)
                for (int i = 0; i <= 100; i += 10) { // Increment by 10% each time
                    Thread.sleep(300); // Delay to make chunks more distinct
                    publish(i); // Publish the progress
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                pgBarSplashScreen.setValue(progress); // Set the progress bar value
            }

            @Override
            protected void done() {
                loadScreen("LoginScreen"); // Switch to the login screen
            }
        };
        worker.execute();
    }

    // Method to add student data and populate the table
    private void registerCandidate(CandidateModel candidate) {
        CandidateList.add(candidate); // Add candidate to the list

        // Get the table model
        DefaultTableModel model = (DefaultTableModel) tblCandidate.getModel();

        // Add a new row with the candidate's details
        model.addRow(new Object[]{
            candidate.getCandidateNo(), // Candidate ID
            candidate.getName(), // Name
            candidate.getContact(), // Contact
            candidate.getCategory(), // Category
            candidate.getType(), // Type
            candidate.getCitizenshipNo(), // Citizenship No
            candidate.getDateOfBirth() // Date of Birth
        });
    }

    private void initializeTrialQueue() {

        btnStartTrial.addActionListener(evt -> {
            queueProcessor.startTrial();
            JOptionPane.showMessageDialog(null, "Trial process completed.");
        });
    }

    // Method to switch screens
    private void loadScreen(String screenName) {
        cardLayout.show(getContentPane(), screenName);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // Get the username and password input
        String username = txtFldLoginUsername.getText();
        String password = new String(pwdFldLogin.getPassword());

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            lblLogInError.setText("Please enter your username and password.");
        } // Check if username and password are incorrect
        else if (!username.equals("admin") || !password.equals("admin")) {
            lblLogInError.setText("Username and password mismatch.");
        } // If credentials are correct, proceed to load the main screen
        else {
            lblLogInError.setText(""); // Clear any previous error messages
            loadScreen("MainScreen"); // Load the main screen
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        pwdFldLogin.setText("");
        txtFldLoginUsername.setText("");
        loadScreen("LoginScreen"); // Load the main screen
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void ComboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboCategoryActionPerformed

    private void txtFldLoginUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldLoginUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldLoginUsernameActionPerformed

    private void ComboTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboTypeActionPerformed

    private void txtFldContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldContactActionPerformed

    private void txtCandidateNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCandidateNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCandidateNoActionPerformed

    private void txtFldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtFldCitizenshipNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldCitizenshipNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldCitizenshipNoActionPerformed

    private void txtFldDOBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldDOBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldDOBActionPerformed

    private void pwdFldLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwdFldLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwdFldLoginActionPerformed

    private void btnAscendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAscendingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAscendingActionPerformed

    private void txtBinarySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBinarySearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBinarySearchActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void clearInputs() {
        txtFldName.setText("");
        txtCandidateNo.setText("");
        ComboCategory.setSelectedItem(null);
        txtFldContact.setText("");
        ComboType.setSelectedItem(null);
    }

    // Add action listeners as inner classes
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        boolean isValid = true;

        // Validate Candidate ID
        String candidateNo = txtCandidateNo.getText().trim();
        if (ValidationUtil.isNullOrEmpty(candidateNo)) {
            lblErrorMsgCandidateId.setText("Candidate ID cannot be empty.");
            txtCandidateNo.setBorder(createTitledBorder(errorColor, "Candidate ID"));
            isValid = false;
        } else if (!candidateNo.matches("\\d+")) {
            lblErrorMsgCandidateId.setText("Candidate ID must be numeric.");
            txtCandidateNo.setBorder(createTitledBorder(errorColor, "Candidate ID"));
            isValid = false;
        } else if (candidateNo.length() < 4 || candidateNo.length() > 5) {
            lblErrorMsgCandidateId.setText("Candidate ID must be 4 to 5 digits long.");
            txtCandidateNo.setBorder(createTitledBorder(errorColor, "Candidate ID"));
            isValid = false;
        } else if (!candidateNo.startsWith("1")) {
            lblErrorMsgCandidateId.setText("Candidate ID must start with '1'.");
            txtCandidateNo.setBorder(createTitledBorder(errorColor, "Candidate ID"));
            isValid = false;
        } else {
            lblErrorMsgCandidateId.setText("");
            txtCandidateNo.setBorder(createTitledBorder(yellowColor, "Candidate ID"));
        }

        // Validate Name
        String name = txtFldName.getText().trim();
        if (ValidationUtil.isNullOrEmpty(name)) {
            lblErrorMsgName.setText("Name cannot be empty.");
            txtFldName.setBorder(createTitledBorder(errorColor, "Name"));
            isValid = false;
        } else if (name.chars().filter(ch -> ch == ' ').count() < 1) {
            lblErrorMsgName.setText("Name must have at least one space.");
            txtFldName.setBorder(createTitledBorder(errorColor, "Name"));
            isValid = false;
        } else if (name.chars().filter(ch -> ch == ' ').count() > 3) {
            lblErrorMsgName.setText("Name cannot have more than three spaces.");
            txtFldName.setBorder(createTitledBorder(errorColor, "Name"));
            isValid = false;
        } else if (name.matches(".*\\d.*")) {
            lblErrorMsgName.setText("Name cannot contain numbers.");
            txtFldName.setBorder(createTitledBorder(errorColor, "Name"));
            isValid = false;
        } else if (!name.matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$")) {
            lblErrorMsgName.setText("Each word must start with a capital letter.");
            txtFldName.setBorder(createTitledBorder(errorColor, "Name"));
            isValid = false;
        } else {
            lblErrorMsgName.setText("");
            txtFldName.setBorder(createTitledBorder(yellowColor, "Name"));
        }

        // Validate Contact
        String contact = txtFldContact.getText().trim();
        if (ValidationUtil.isNullOrEmpty(contact)) {
            lblErrorMsgContact.setText("Contact number cannot be empty.");
            txtFldContact.setBorder(createTitledBorder(errorColor, "Contact"));
            isValid = false;
        } else if (!contact.startsWith("98") && !contact.startsWith("97")) {
            lblErrorMsgContact.setText("Contact must start with '98' or '97'.");
            txtFldContact.setBorder(createTitledBorder(errorColor, "Contact"));
            isValid = false;
        } else if (contact.length() != 10) {
            lblErrorMsgContact.setText("Contact number must be 10 digits long.");
            txtFldContact.setBorder(createTitledBorder(errorColor, "Contact"));
            isValid = false;
        } else if (!ValidationUtil.isValidContact(contact)) {
            lblErrorMsgContact.setText("Contact number is already used.");
            txtFldContact.setBorder(createTitledBorder(errorColor, "Contact"));
            isValid = false;
        } else {
            lblErrorMsgContact.setText("");
            txtFldContact.setBorder(createTitledBorder(yellowColor, "Contact"));
        }

        // Validate Date of Birth
        String dob = txtFldDOB.getText().trim();
        if (ValidationUtil.isNullOrEmpty(dob)) {
            lblErrorMsgDOB.setText("Date of Birth cannot be empty.");
            txtFldDOB.setBorder(createTitledBorder(errorColor, "Date of Birth"));
            isValid = false;
        } else if (!ValidationUtil.isValidDateOfBirth(dob)) {
            lblErrorMsgDOB.setText("Date of Birth must be in yyyy-mm-dd format.");
            txtFldDOB.setBorder(createTitledBorder(errorColor, "Date of Birth"));
            isValid = false;
        } else {
            // Calculate age
            LocalDate birthDate = LocalDate.parse(dob);
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthDate, currentDate).getYears();

            if (age < 18) {
                lblErrorMsgDOB.setText("Age must be at least 18.");
                txtFldDOB.setBorder(createTitledBorder(errorColor, "Date of Birth"));
                isValid = false;
            } else if (age > 60) {
                lblErrorMsgDOB.setText("Age must not exceed 60.");
                txtFldDOB.setBorder(createTitledBorder(errorColor, "Date of Birth"));
                isValid = false;
            } else {
                lblErrorMsgDOB.setText("");
                txtFldDOB.setBorder(createTitledBorder(yellowColor, "Date of Birth"));
            }
        }

        // Validate Citizenship Number
        String citizenshipNo = txtFldCitizenshipNo.getText().trim();
        if (ValidationUtil.isNullOrEmpty(citizenshipNo)) {
            lblErrorMsgCitizenshipNo.setText("Citizenship Number cannot be empty.");
            txtFldCitizenshipNo.setBorder(createTitledBorder(errorColor, "Citizenship Number"));
            isValid = false;
        } else if (!ValidationUtil.isValidCitizenshipNo(citizenshipNo)) {
            lblErrorMsgCitizenshipNo.setText("Citizenship Number must be numeric with 3-4 hyphens.");
            txtFldCitizenshipNo.setBorder(createTitledBorder(errorColor, "Citizenship Number"));
            isValid = false;
        } else {
            lblErrorMsgCitizenshipNo.setText("");
            txtFldCitizenshipNo.setBorder(createTitledBorder(yellowColor, "Citizenship Number"));
        }

        // If all validations pass
        if (isValid) {
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to add this candidate?",
                    "Confirm Addition", JOptionPane.YES_NO_OPTION);

            if (confirmation != JOptionPane.YES_OPTION) {
                return; // Exit if the user cancels
            }

            CandidateModel newCandidate = new CandidateModel(
                    Integer.parseInt(candidateNo),
                    name,
                    contact,
                    (String) ComboCategory.getSelectedItem(),
                    (String) ComboType.getSelectedItem(),
                    citizenshipNo,
                    dob
            );

            if (checkDuplicateCandidate(newCandidate)) {
                txtCandidateNo.setBorder(createTitledBorder(errorColor, "Candidate ID"));
                showDialogBox("Candidate ID already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            } else {
                CandidateList.add(newCandidate);
                originalCandidateList.add(newCandidate);
                queueProcessor.enqueueCandidate(newCandidate);
                clearCandidateForm();
                loadListToTable(CandidateList);
                txtCandidateNo.setBorder(createTitledBorder(yellowColor, "Candidate ID"));
                showDialogBox("Candidate added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void loadListToTable(List<CandidateModel> candidateList) {
        DefaultTableModel model = (DefaultTableModel) tblCandidate.getModel();
        model.setRowCount(0); // Clear existing rows

        // Populate the table with candidate data
        candidateList.forEach(candidate -> model.addRow(new Object[]{
            candidate.getCandidateNo(),
            candidate.getName(),
            candidate.getContact(),
            candidate.getCategory(),
            candidate.getType(),
            candidate.getCitizenshipNo(), // Citizenship No
            candidate.getDateOfBirth() // Date of Birth
        }));
    }

    private boolean validateField(JTextField textField, String fieldName, JLabel errorLbl, String errorMsg, Color errorColor, Color successColor, boolean isValidFormat) {
        if (ValidationUtil.isNullOrEmpty(textField.getText())) {
            textField.setBorder(createTitledBorder(errorColor, fieldName));
            errorLbl.setText("Field cannot be empty!");
            errorLbl.setVisible(true);
            return false;
        } else if (!isValidFormat) {
            textField.setBorder(createTitledBorder(errorColor, fieldName));
            errorLbl.setText(errorMsg);
            errorLbl.setVisible(true);
            return false;
        } else {
            textField.setBorder(createTitledBorder(successColor, fieldName));
            errorLbl.setVisible(false);
            return true;
        }
    }

    private javax.swing.border.TitledBorder createTitledBorder(Color color, String title) {
        return javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(color, 2),
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 12),
                color
        );
    }

    private void showDialogBox(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void clearCandidateForm() {
        txtCandidateNo.setText("");
        txtFldName.setText("");
        txtFldContact.setText("");
        ComboCategory.setSelectedItem("");
        ComboType.setSelectedItem("");
        txtFldDOB.setText("");
        txtFldCitizenshipNo.setText("");
    }

    private boolean checkDuplicateCandidate(CandidateModel candidate) {
        return CandidateList.stream()
                .anyMatch(existingCandidate -> existingCandidate.getCandidateNo() == candidate.getCandidateNo());
    }

 private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
    boolean isCandidateNoValid = true;
    boolean isDOBValid = true;
    boolean isContactValid = true;
    boolean isCitizenshipNoValid = true;

    // Fetch input fields
    String candidateNo = txtCandidateNo.getText().trim();
    String dob = txtFldDOB.getText().trim();
    String contact = txtFldContact.getText().trim();
    String citizenshipNo = txtFldCitizenshipNo.getText().trim();

    // Validate CandidateNo
    if (ValidationUtil.isNullOrEmpty(candidateNo) || !ValidationUtil.isValidCandidateNo(candidateNo)) {
        lblErrorMsgCandidateId.setText(" ");
        isCandidateNoValid = false;
    } else {
        lblErrorMsgCandidateId.setText("");
    }

    // Validate DOB
    if (ValidationUtil.isNullOrEmpty(dob) || !ValidationUtil.isValidDateOfBirth(dob)) {
        lblErrorMsgDOB.setText("Invalid Date of Birth.");
        isDOBValid = false;
    } else {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        if (age < 18) {
            lblErrorMsgDOB.setText("Age must be at least 18.");
            isDOBValid = false;
        } else if (age > 60) {
            lblErrorMsgDOB.setText("Age must not exceed 60.");
            isDOBValid = false;
        } else {
            lblErrorMsgDOB.setText("");
        }
    }

    // Validate Contact
    if (ValidationUtil.isNullOrEmpty(contact) || !ValidationUtil.isValidContact(contact)) {
        lblErrorMsgContact.setText("Invalid Contact Number. ");
        isContactValid = false;
    } else {
        lblErrorMsgContact.setText("");
    }

    // Validate CitizenshipNo
    if (ValidationUtil.isNullOrEmpty(citizenshipNo) || !ValidationUtil.isValidCitizenshipNo(citizenshipNo)) {
        lblErrorMsgCitizenshipNo.setText("Invalid Citizenship Number.");
        isCitizenshipNoValid = false;
    } else {
        lblErrorMsgCitizenshipNo.setText("");
    }

    // Stop if any validation fails
    if (!isCandidateNoValid || !isDOBValid || !isContactValid || !isCitizenshipNoValid) {
        return; // Exit early if validation fails
    }

    // Perform Candidate Lookup
    CandidateModel candidateToUpdate = CandidateList.stream()
            .filter(candidate -> candidate.getCandidateNo() == Integer.parseInt(candidateNo))
            .findFirst()
            .orElse(null);

    // Detailed error handling for candidate lookup
    if (candidateToUpdate == null) {
        lblErrorMsgCandidateId.setText("Candidate not found");
        return;
    }

    if (!dob.equals(candidateToUpdate.getDateOfBirth())) {
        lblErrorMsgDOB.setText("Date of Birth does not match the record.");
        return;
    }

    if (!contact.equals(candidateToUpdate.getContact())) {
        lblErrorMsgContact.setText("Contact does not match the record.");
        return;
    }

    if (!citizenshipNo.equals(candidateToUpdate.getCitizenshipNo())) {
        lblErrorMsgCitizenshipNo.setText("Citizenship Number does not match the record.");
        return;
    }

    // Validate Other Fields
    String category = (String) ComboCategory.getSelectedItem();
    if (!ValidationUtil.isValidCategory(category)) {
        lblErrorMsgCategory.setText("Please select a valid category.");
    } else {
        lblErrorMsgCategory.setText("");
    }

    String type = (String) ComboType.getSelectedItem();
    if (!ValidationUtil.isValidType(type)) {
        lblErrorMsgType.setText("Please select a valid type.");
    } else {
        lblErrorMsgType.setText("");
    }

    // Confirm Update
    int confirmation = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to update the candidate's information?",
            "Confirm Update", JOptionPane.YES_NO_OPTION);

    if (confirmation != JOptionPane.YES_OPTION) {
        return;
    }

    // Update Candidate Fields
    candidateToUpdate.setCategory(category);
    candidateToUpdate.setType(type);

    // Refresh Table
    loadListToTable(CandidateList);
    JOptionPane.showMessageDialog(this, "Candidate updated successfully.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
}



    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        // Check if the candidate number text field is filled
        String candidateNoInput = txtCandidateNo.getText().trim();

        if (!ValidationUtil.isNullOrEmpty(candidateNoInput)) {
            // User entered a candidate number
            if (!candidateNoInput.matches("\\d+")) {
                lblErrorMsgCandidateId.setText("Candidate ID must be numeric.");
                return;
            }

            int candidateNo = Integer.parseInt(candidateNoInput);

            // Check if the candidate exists in the list
            CandidateModel candidateToDelete = null;
            for (CandidateModel candidate : CandidateList) {
                if (candidate.getCandidateNo() == candidateNo) {
                    candidateToDelete = candidate;
                    break;
                }
            }

            if (candidateToDelete == null) {
                // Candidate not found
                lblErrorMsgCandidateId.setText("Candidate with the entered ID does not exist.");
            } else {
                // Confirm the delete operation
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete the candidate with ID: " + candidateNo + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    // Remove the candidate from the list
                    CandidateList.remove(candidateToDelete);

                    // Refresh the table
                    loadListToTable(CandidateList);

                    // Show success message
                    lblErrorMsgCandidateId.setText("Candidate deleted successfully.");

                    // Clear the text field
                    txtCandidateNo.setText("");
                }
            }
        } else {
            // No candidate number entered, proceed with table row deletion
            int selectedRow = tblCandidate.getSelectedRow();

            if (selectedRow == -1) {
                // No row selected, show an error message
                lblErrorMsgCandidateId.setText("Please select a candidate to delete.");
            } else {
                // Confirm the delete operation
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete the selected candidate?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    // Remove the candidate from the list
                    CandidateList.remove(selectedRow);

                    // Refresh the table
                    loadListToTable(CandidateList);

                    // Show success message
                    lblErrorMsgCandidateId.setText("Candidate deleted successfully.");
                }
            }
        }
    }

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
            java.util.logging.Logger.getLogger(InfinityTrail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfinityTrail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfinityTrail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfinityTrail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        InfinityTrail app = new InfinityTrail();


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            app.setVisible(true);
        });

        app.startProgress();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCategory;
    private javax.swing.JComboBox<String> ComboSort;
    private javax.swing.JComboBox<String> ComboType;
    private javax.swing.JPanel Results;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAscending;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDescending;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnStartTrial;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblErrorMsgCandidateId;
    private javax.swing.JLabel lblErrorMsgCategory;
    private javax.swing.JLabel lblErrorMsgCitizenshipNo;
    private javax.swing.JLabel lblErrorMsgContact;
    private javax.swing.JLabel lblErrorMsgDOB;
    private javax.swing.JLabel lblErrorMsgName;
    private javax.swing.JLabel lblErrorMsgType;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JLabel lblLogInError;
    private javax.swing.JLabel lblLoginError;
    private javax.swing.JLabel lblLoginForgotPwd;
    private javax.swing.JLabel lblMainBarSlogan;
    private javax.swing.JLabel lblTblCandidateTitle;
    private javax.swing.JLabel lblimageHome;
    private javax.swing.JLabel lbllogo;
    private javax.swing.JProgressBar pgBarSplashScreen;
    private javax.swing.JPanel pnlAboutUs;
    private javax.swing.JPanel pnlAdminControl;
    private javax.swing.JPanel pnlCandidateList;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlLoadingScreen;
    private javax.swing.JPanel pnlLoginLeft;
    private javax.swing.JPanel pnlLoginScreen;
    private javax.swing.JPanel pnlMainBar;
    private javax.swing.JPanel pnlMainScreen;
    private javax.swing.JPasswordField pwdFldLogin;
    private javax.swing.JScrollPane spTblStudent;
    private javax.swing.JScrollPane spTblStudent1;
    private javax.swing.JTabbedPane tabPaneMain;
    private javax.swing.JTable tblCandidate;
    private javax.swing.JTable tblResults;
    private javax.swing.JTextField txtBinarySearch;
    private javax.swing.JTextField txtCandidateNo;
    private javax.swing.JTextField txtFldCitizenshipNo;
    private javax.swing.JTextField txtFldContact;
    private javax.swing.JTextField txtFldDOB;
    private javax.swing.JTextField txtFldLoginUsername;
    private javax.swing.JTextField txtFldName;
    // End of variables declaration//GEN-END:variables

}