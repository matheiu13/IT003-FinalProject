/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.driver.OracleDriver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;



/**
 *
 * @author ORACLE_12C
 */
public class basicinfoboard extends javax.swing.JFrame {

    //Database Credentials for non-VM
    static final String USER = "system"; //Database Username
    static final String PASS = "123456"; //Your Account Password
    static final String DATABASE = "CDB"; //Database Name
    static final String SERVER_IP = "192.168.100.12"; //Your Database Server IP (run ipconfig in cmd)
    static final String PORT = "1521";
    static final String DB_URL = "jdbc:oracle:thin:@" + SERVER_IP + ":" + PORT + ":" + DATABASE;

    //Database Credentials (VM)
//    static final String USER = "ora2";
//    static final String PASS = "ora2";
//    static final String DATABASE = "pdborcl";
//    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/"+DATABASE;
////    
    //Create a connection object
    Connection conn = null;
    Statement stmt = null;

    /**
     * Creates new form
     */
    public basicinfoboard() {
        initComponents();
        this.setLocationRelativeTo(null);
        BarangayTableUpdate();

        //Family Update
        FamilyTableUpdate();

        //Health Update
        HealthTableUpdate();

        //Health dropdown
//        HealthDropDown();
        //Vaccine Update
        VaccineTableUpdate();

        //dropdown function
        TableDropDown();

        //vaccine dropdown
        VaccineTableDropDown();

        //covid record dropdown
        CovidRecordDropDown();

        //Covid Record Table
        CovidRecordUpdate();

        //contact record dropdown
        ContactRecordDropDown();

        //Contact Record Table
        ContactTableUpdate();

        //run all charts
        showPieChart();
        showLineChart();
        showBarChart();
        showHistogram();
    }
    
    class PrintResult implements Printable {
    final Component comp;

    public PrintResult(Component comp){
        this.comp = comp;
    }

    @Override
    public int print(Graphics g, PageFormat format, int page_index) 
            throws PrinterException {
        if (page_index > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        // get the bounds of the component
        Dimension dim = comp.getSize();
        double cHeight = dim.getHeight();
        double cWidth = dim.getWidth();

        // get the bounds of the printable area
        double pHeight = format.getImageableHeight();
        double pWidth = format.getImageableWidth();

        double pXStart = format.getImageableX();
        double pYStart = format.getImageableY();

        double xRatio = pWidth / cWidth;
        double yRatio = pHeight / cHeight;


        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pXStart, pYStart);
        g2.scale(xRatio, yRatio);
        comp.paint(g2);

        return Printable.PAGE_EXISTS;
    }
}

     public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "Infection Curve" , new Double( 20 ) );  
      barDataset.setValue( "Infection Rate" , new Double( 20 ) );   
      barDataset.setValue( "Trust Rating" , new Double( 5 ) );    
      barDataset.setValue( "Vaccine Rating" , new Double( 10 ) );  
      barDataset.setValue( "Distance" , new Double( 35 ) ); 
      
      //create chart
       JFreeChart piechart = ChartFactory.createPieChart("Family Safety",barDataset, false,true,false);//explain
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("Infection Curve", new Color(255,255,102));
        piePlot.setSectionPaint("Infection Rate", new Color(102,255,102));
        piePlot.setSectionPaint("Trust Rating", new Color(255,102,153));
        piePlot.setSectionPaint("Vaccine Rating", new Color(0,204,204));
        piePlot.setSectionPaint("Distance", new Color(204,255,255));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        pieChart.removeAll();
        pieChart.add(barChartPanel, BorderLayout.CENTER);
        pieChart.validate();
    }
    
    public void showLineChart(){
        //create dataset for the graph
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Covid Report","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
       // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.white);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(24, 28, 82);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        lineChart.removeAll();
        lineChart.add(lineChartPanel, BorderLayout.CENTER);
        lineChart.validate();
    }
    
    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart chart = ChartFactory.createBarChart("Vaccine Rate","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(24, 28, 82);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        barChart.removeAll();
        barChart.add(barpChartPanel, BorderLayout.CENTER);
        barChart.validate();
        
        
    }
    
    public void showHistogram(){
        
         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
        
         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
            XYPlot plot= chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
        histogram.removeAll();
        histogram.add(barpChartPanel2, BorderLayout.CENTER);
        histogram.validate();
    }

    public void TableDropDown() {
        System.out.println("Dropdown is running");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT family_id from family_info");
            ResultSet rs = ps.executeQuery();

//            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
//            tm.setRowCount(0);
            while (rs.next()) {
                String name = rs.getString("family_id");
                jComboBox1.addItem(name);

//                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    public void VaccineTableDropDown() {
        System.out.println("Dropdown is running");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT vaccine_id from resident_info");
            ResultSet rs = ps.executeQuery();

//            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
//            tm.setRowCount(0);
            while (rs.next()) {
                String name = rs.getString("vaccine_id");

//                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    //================================TABLE FOR BARANGAY=================================
    public void BarangayTableUpdate() {
        System.out.println("Update table function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from resident_info ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("barangay_id"),
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("birth_date"),
                    rs.getString("age"),
                    rs.getString("address"),
                    rs.getString("contact_no"),
                    rs.getString("occupation"),
                    rs.getString("family_id"),
                    rs.getString("covid_id"),
                    rs.getString("health_id"),
                    rs.getString("vaccine_id"),
                    rs.getString("tracking_id")};

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    public void BarangayTableClear() {

        System.out.println("Barangay table cleared");
        barangayID.setText("");
        lastName.setText("");
        firstName.setText("");
        birthDate.setText("");
        age.setText("");
        address.setText("");
        contactNumber.setText("");
        occupation.setText("");
        jComboBox1.getModel().setSelectedItem("N/A");
        covidID.setText("");
        healthID.setText("");
        vaccineID.setText("");
        trackingID.setText("");
        tableID.setText("");
    }

    //================================END TABLE=================================
    //================================TABLE FOR FAMILY=================================
    public void FamilyTableUpdate() {
        System.out.println("Family update table function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from family_info ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) familyInfoList.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("family_id"),
                    rs.getString("fa_vaccinated"),
                    rs.getString("fa_ineligible_vaccine"),
                    rs.getString("fa_adults"),
                    rs.getString("fa_seniors"),
                    rs.getString("fa_minors"),
                    rs.getString("fa_infected")
                };

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    public void FamilyTableClear() {
        family_tableID.setText("");
        family_familyID.setText("");
        family_vaccinated.setText("");
        family_ineligibleVaccine.setText("");
        family_adults.setText("");
        family_seniors.setText("");
        family_minors.setText("");
        family_infected.setText("");
    }

    //================================END TABLE=================================
    //================================TABLE FOR HEALTH=================================
    public void HealthTableUpdate() {
        System.out.println("Update table function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from health_info ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) HealthInfoList.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("health_id"),
                    rs.getString("philhealth_id"),
                    rs.getString("pwd_id"),
                    rs.getString("comorbidities"),};

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    public void HealthTableClear() {

        System.out.println("Health table cleared");

        family_tableID1.setText("");
        health_id.setText("");
        family_vaccinated1.setText("");
        family_ineligibleVaccine1.setText("");
        family_adults1.setText("");

    }

//    public void HealthDropDown() {
//        System.out.println("Dropdown is running");
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            PreparedStatement ps = conn.prepareStatement("SELECT health_id from health_info");
//            ResultSet rs = ps.executeQuery();
//
////            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
////            tm.setRowCount(0);
//            while (rs.next()) {
//                String name = rs.getString("health_id");
//                jComboBox2.addItem(name);
//
////                tm.addRow(row);
//            }
//            conn.close();
//
//        } catch (SQLException se) {
//            System.out.println(se);
//        }
//    }
    //================================END TABLE=================================
    //================================TABLE FOR VACCINE=================================
    public void VaccineTableUpdate() {
        System.out.println("Update table function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from vaccine_record ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) VaccineInfoList.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("vaccine_id"),
                    rs.getString("first_dose_date"),
                    rs.getString("second_dose_date"),
                    rs.getString("vaccine_brand"),};

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    public void VaccineTableClear() {

        vaccine_firstdose.setText("");
        vaccine_seconddose.setText("");
        vaccine_vbrand.setText("");
        covidinfo_id.setText("");
        vaccine_id.setText("");
    }
    //================================END TABLE=================================

    //=============================COVID RECORD TABLE===========================
    public void CovidRecordUpdate() {
        System.out.println("Covid Record update table function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from covid_record ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) covidRecordTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("covid_id"),
                    rs.getString("covid_condition"),
                    rs.getString("status"),
                    rs.getString("vaccine_status"),};

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void CovidRecordClear() {
        System.out.println("Covid Record Form cleared");
        covid_id_record_pk.setText("");
        covid_id.setText("");

        covidConditionField.getModel().setSelectedItem("NO INFO");
        covidStatusField.getModel().setSelectedItem("NO INFO");
        covidVaccineField.getModel().setSelectedItem("NO INFO");

    }

    public void CovidRecordDropDown() {
        System.out.println("Dropdown is running");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT covid_id from covid_record");
            ResultSet rs = ps.executeQuery();

//            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
//            tm.setRowCount(0);
            while (rs.next()) {
                String name = rs.getString("covid_id");

//                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }

    //================================END TABLE=================================
    //=============================CONTACT RECORD TABLE===========================
    public void ContactTableUpdate() {
        System.out.println("Contact Table update function called");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from contact_table ORDER BY id");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) contactTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("tracking_id"),
                    rs.getString("from_barangay"),
                    rs.getString("close_contacts"),};

                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void ContactTableClear() {
        System.out.println("Contact Table cleared");
        tracking_id_contact_pk.setText("");
        tracking_id.setText("");
        barangay_resident_bool.getModel().setSelectedItem("NO INFO");
        close_contacts_table.setText("");
    }

    public void ContactRecordDropDown() {
        System.out.println("Dropdown is running");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT tracking_id from contact_table");
            ResultSet rs = ps.executeQuery();

//            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
//            tm.setRowCount(0);
            while (rs.next()) {
                String name = rs.getString("tracking_id");
//                tracking_id_contact_one.addItem(name);

//                tm.addRow(row);
            }
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }

    }
    //================================END TABLE===================================

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        ContentHolder = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        HomePage = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        HOME = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        HOME1 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        HOME2 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        HOME3 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        HOME4 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        HOME5 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        createbtn2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        pieChart = new javax.swing.JPanel();
        lineChart = new javax.swing.JPanel();
        barChart = new javax.swing.JPanel();
        histogram = new javax.swing.JPanel();
        createbtn3 = new javax.swing.JButton();
        BarangayPage = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        basicinfolist = new javax.swing.JTable();
        createbtn = new javax.swing.JButton();
        updatebtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        refreshbtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        lastName = new javax.swing.JTextField();
        firstName = new javax.swing.JTextField();
        birthDate = new javax.swing.JTextField();
        age = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        contactNumber = new javax.swing.JTextField();
        covidID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        occupation = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        barangayID = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        healthID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        trackingID = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        tableID = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        vaccineID = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        updatebtn1 = new javax.swing.JButton();
        FamilyPage = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        family_vaccinated = new javax.swing.JTextField();
        family_ineligibleVaccine = new javax.swing.JTextField();
        family_adults = new javax.swing.JTextField();
        family_seniors = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        family_minors = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        family_infected = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        family_familyID = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        family_tableID = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        familyInfoList = new javax.swing.JTable();
        refreshFamily = new javax.swing.JButton();
        createbtn1 = new javax.swing.JButton();
        updatebtn2 = new javax.swing.JButton();
        updatebtn3 = new javax.swing.JButton();
        deletebtn1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        family_vaccinated1 = new javax.swing.JTextField();
        family_ineligibleVaccine1 = new javax.swing.JTextField();
        family_adults1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        family_tableID1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        health_id = new javax.swing.JTextField();
        updatebtn4 = new javax.swing.JButton();
        updatebtn5 = new javax.swing.JButton();
        deletebtn2 = new javax.swing.JButton();
        refreshFamily1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        HealthInfoList = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        vaccine_firstdose = new javax.swing.JTextField();
        vaccine_seconddose = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        vaccine_vbrand = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        covidinfo_id = new javax.swing.JTextField();
        vaccine_id = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        refreshFamily2 = new javax.swing.JButton();
        updatebtn6 = new javax.swing.JButton();
        updatebtn7 = new javax.swing.JButton();
        deletebtn3 = new javax.swing.JButton();
        vaccine_search = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        VaccineInfoList = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        covid_id_record_pk = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        covid_id = new javax.swing.JTextField();
        covidStatusField = new javax.swing.JComboBox<>();
        covidConditionField = new javax.swing.JComboBox<>();
        covidVaccineField = new javax.swing.JComboBox<>();
        refreshCovidRecordBtn = new javax.swing.JButton();
        CovidSearch = new javax.swing.JTextField();
        covidRecordUpdateBtn = new javax.swing.JButton();
        clearCovidRecordBtn = new javax.swing.JButton();
        deleteBtnCovidRecord = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        covidRecordTable = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        tracking_id_contact_pk = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        close_contacts_table = new javax.swing.JTextField();
        tracking_id = new javax.swing.JTextField();
        barangay_resident_bool = new javax.swing.JComboBox<>();
        contactSearch = new javax.swing.JTextField();
        refreshContactBtn = new javax.swing.JButton();
        updateContactBtn = new javax.swing.JButton();
        contactClearBtn = new javax.swing.JButton();
        deleteContact = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        contactTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ContentHolder.setBackground(new java.awt.Color(48, 48, 48));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        HomePage.setBackground(new java.awt.Color(26, 34, 57));

        jPanel8.setBackground(new java.awt.Color(39, 46, 72));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Program");
        jPanel8.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 190, 50));

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Covid-19");
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 170, 50));

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Database");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 190, 50));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 10, 110));

        HOME.setBackground(new java.awt.Color(42, 53, 92));
        HOME.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HOME.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HOMEMouseClicked(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts.png"))); // NOI18N

        jLabel52.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("CONTACT");

        javax.swing.GroupLayout HOMELayout = new javax.swing.GroupLayout(HOME);
        HOME.setLayout(HOMELayout);
        HOMELayout.setHorizontalGroup(
            HOMELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOMELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        HOMELayout.setVerticalGroup(
            HOMELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.add(HOME, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 280, 50));

        HOME1.setBackground(new java.awt.Color(42, 53, 92));
        HOME1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/house-black-silhouette-without-door.png"))); // NOI18N

        jLabel54.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("HOME");

        javax.swing.GroupLayout HOME1Layout = new javax.swing.GroupLayout(HOME1);
        HOME1.setLayout(HOME1Layout);
        HOME1Layout.setHorizontalGroup(
            HOME1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOME1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HOME1Layout.setVerticalGroup(
            HOME1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel8.add(HOME1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 280, 50));

        HOME2.setBackground(new java.awt.Color(42, 53, 92));
        HOME2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HOME2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HOME2MouseClicked(evt);
            }
        });

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/house.png"))); // NOI18N

        jLabel56.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("BARANGAY");

        javax.swing.GroupLayout HOME2Layout = new javax.swing.GroupLayout(HOME2);
        HOME2.setLayout(HOME2Layout);
        HOME2Layout.setHorizontalGroup(
            HOME2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOME2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        HOME2Layout.setVerticalGroup(
            HOME2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel8.add(HOME2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 280, 50));

        HOME3.setBackground(new java.awt.Color(42, 53, 92));
        HOME3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HOME3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HOME3MouseClicked(evt);
            }
        });

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/family-silhouette.png"))); // NOI18N

        jLabel58.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("FAMILY");

        javax.swing.GroupLayout HOME3Layout = new javax.swing.GroupLayout(HOME3);
        HOME3.setLayout(HOME3Layout);
        HOME3Layout.setHorizontalGroup(
            HOME3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOME3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HOME3Layout.setVerticalGroup(
            HOME3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel8.add(HOME3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 280, 50));

        HOME4.setBackground(new java.awt.Color(42, 53, 92));
        HOME4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HOME4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HOME4MouseClicked(evt);
            }
        });

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cardiogram.png"))); // NOI18N

        jLabel60.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("HEALTH");

        javax.swing.GroupLayout HOME4Layout = new javax.swing.GroupLayout(HOME4);
        HOME4.setLayout(HOME4Layout);
        HOME4Layout.setHorizontalGroup(
            HOME4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOME4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HOME4Layout.setVerticalGroup(
            HOME4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel8.add(HOME4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 280, 50));

        HOME5.setBackground(new java.awt.Color(42, 53, 92));
        HOME5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HOME5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HOME5MouseClicked(evt);
            }
        });

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vaccine.png"))); // NOI18N

        jLabel62.setFont(new java.awt.Font("Nirmala UI", 0, 24)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("VACCINE");

        javax.swing.GroupLayout HOME5Layout = new javax.swing.GroupLayout(HOME5);
        HOME5.setLayout(HOME5Layout);
        HOME5Layout.setHorizontalGroup(
            HOME5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HOME5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        HOME5Layout.setVerticalGroup(
            HOME5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel8.add(HOME5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 280, 50));

        createbtn2.setBackground(new java.awt.Color(153, 0, 153));
        createbtn2.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        createbtn2.setForeground(new java.awt.Color(255, 255, 255));
        createbtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pen.png"))); // NOI18N
        createbtn2.setText("PRINT");
        createbtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createbtn2ActionPerformed(evt);
            }
        });
        jPanel8.add(createbtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 570, -1, -1));

        jPanel10.setBackground(new java.awt.Color(39, 46, 72));

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Residents:");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("100");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(20, 20, 20))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jPanel11.setBackground(new java.awt.Color(39, 46, 72));

        jLabel31.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Family:");

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/family-silhouette.png"))); // NOI18N

        jLabel44.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("24");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jPanel12.setBackground(new java.awt.Color(39, 46, 72));

        jLabel45.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Cases:");

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/virus.png"))); // NOI18N

        jLabel47.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("41");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        jPanel13.setBackground(new java.awt.Color(39, 46, 72));

        jLabel48.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Vaccinated:");

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vaccine.png"))); // NOI18N

        jLabel50.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("20");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        pieChart.setBackground(new java.awt.Color(39, 46, 72));
        pieChart.setForeground(new java.awt.Color(255, 255, 255));
        pieChart.setLayout(new java.awt.BorderLayout());

        lineChart.setBackground(new java.awt.Color(39, 46, 72));
        lineChart.setForeground(new java.awt.Color(255, 255, 255));
        lineChart.setLayout(new java.awt.BorderLayout());

        barChart.setBackground(new java.awt.Color(39, 46, 72));
        barChart.setForeground(new java.awt.Color(255, 255, 255));
        barChart.setLayout(new java.awt.BorderLayout());

        histogram.setBackground(new java.awt.Color(39, 46, 72));
        histogram.setForeground(new java.awt.Color(255, 255, 255));
        histogram.setLayout(new java.awt.BorderLayout());

        createbtn3.setBackground(new java.awt.Color(153, 0, 153));
        createbtn3.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        createbtn3.setForeground(new java.awt.Color(255, 255, 255));
        createbtn3.setText("HOW?");
        createbtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createbtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createbtn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HomePageLayout = new javax.swing.GroupLayout(HomePage);
        HomePage.setLayout(HomePageLayout);
        HomePageLayout.setHorizontalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barChart, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageLayout.createSequentialGroup()
                        .addComponent(histogram, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createbtn3))
                    .addGroup(HomePageLayout.createSequentialGroup()
                        .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HomePageLayout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        HomePageLayout.setVerticalGroup(
            HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(HomePageLayout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(HomePageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(HomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barChart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(histogram, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePageLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createbtn3)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Home Page", HomePage);

        BarangayPage.setBackground(new java.awt.Color(36, 36, 36));

        basicinfolist.setAutoCreateRowSorter(true);
        basicinfolist.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        basicinfolist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Barangay ID", "Last name", "First name", "Birth date", "Age", "Address", "Contact no", "Occupation", "Family ID", "Covid ID", "Health ID", "Vaccine ID", "Tracking ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        basicinfolist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                basicinfolistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(basicinfolist);

        createbtn.setBackground(new java.awt.Color(153, 0, 153));
        createbtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        createbtn.setForeground(new java.awt.Color(255, 255, 255));
        createbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pen.png"))); // NOI18N
        createbtn.setText("Create");
        createbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createbtnActionPerformed(evt);
            }
        });

        updatebtn.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        updatebtn.setText("Update");
        updatebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtnActionPerformed(evt);
            }
        });

        deletebtn.setBackground(new java.awt.Color(204, 0, 51));
        deletebtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deletebtn.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        refreshbtn.setBackground(new java.awt.Color(153, 0, 153));
        refreshbtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshbtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshbtn.setText("Refresh");
        refreshbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbtnActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(16, 26, 51));

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Birth Date: MM/DD/YYYY");

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Age:");

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Covid ID:");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Family ID:");

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Address:");

        text.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        text.setForeground(new java.awt.Color(255, 255, 255));
        text.setText("Contact No.:");

        lastName.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        lastName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        firstName.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        firstName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        birthDate.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        birthDate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        birthDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthDateActionPerformed(evt);
            }
        });

        age.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        age.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageActionPerformed(evt);
            }
        });

        address.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        address.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });

        contactNumber.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        contactNumber.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        covidID.setEditable(false);
        covidID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Last Name:");

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("First Name:");

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Occupation:");

        occupation.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        occupation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Barangay ID:");

        barangayID.setEditable(false);
        barangayID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Health ID:");

        healthID.setEditable(false);
        healthID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("RESIDENT RECORD");

        jLabel27.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Vaccine ID:");

        trackingID.setEditable(false);
        trackingID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Tracking ID:");

        tableID.setEditable(false);
        tableID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        tableID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableIDActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Table ID:");

        jComboBox1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(birthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(barangayID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(covidID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(healthID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vaccineID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(occupation, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(tableID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(trackingID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(text)
                        .addComponent(contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel4)
                        .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barangayID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(covidID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(healthID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vaccineID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(occupation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trackingID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(text)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(166, Short.MAX_VALUE)))
        );

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        updatebtn1.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn1.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        updatebtn1.setText("Clear");
        updatebtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BarangayPageLayout = new javax.swing.GroupLayout(BarangayPage);
        BarangayPage.setLayout(BarangayPageLayout);
        BarangayPageLayout.setHorizontalGroup(
            BarangayPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarangayPageLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(BarangayPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BarangayPageLayout.createSequentialGroup()
                        .addComponent(createbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarangayPageLayout.createSequentialGroup()
                        .addComponent(refreshbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE))
                .addContainerGap())
        );
        BarangayPageLayout.setVerticalGroup(
            BarangayPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarangayPageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BarangayPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(BarangayPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Barangay Resident Database", BarangayPage);

        FamilyPage.setBackground(new java.awt.Color(36, 36, 36));

        jPanel3.setBackground(new java.awt.Color(16, 26, 51));

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Adults:");

        jLabel19.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Seniors:");

        family_vaccinated.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_vaccinated.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        family_ineligibleVaccine.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_ineligibleVaccine.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        family_adults.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_adults.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        family_adults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                family_adultsActionPerformed(evt);
            }
        });

        family_seniors.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_seniors.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Vaccinated:");

        jLabel21.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Ineligible Vaccine:");

        jLabel22.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Minors:");

        family_minors.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_minors.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Infected:");

        family_infected.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Family ID:");

        family_familyID.setEditable(false);
        family_familyID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("FAMILY RECORD");

        family_tableID.setEditable(false);
        family_tableID.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Table ID:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(family_seniors, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(family_adults, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel23)
                        .addComponent(family_minors, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addComponent(family_infected))
                    .addComponent(jLabel26))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(family_tableID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(family_familyID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(41, 41, 41))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel20)
                        .addComponent(family_vaccinated, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(family_ineligibleVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_familyID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_tableID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_adults, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_seniors, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_minors, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_infected, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addComponent(jLabel20)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(family_vaccinated, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel21)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(family_ineligibleVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(400, Short.MAX_VALUE)))
        );

        familyInfoList.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        familyInfoList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Family ID", "Vaccinated", "Ineligible", "Adults", "Seniors", "Minors", "Infected"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        familyInfoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                familyInfoListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(familyInfoList);

        refreshFamily.setBackground(new java.awt.Color(153, 0, 153));
        refreshFamily.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshFamily.setForeground(new java.awt.Color(255, 255, 255));
        refreshFamily.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshFamily.setText("Refresh");
        refreshFamily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFamilyActionPerformed(evt);
            }
        });

        createbtn1.setBackground(new java.awt.Color(153, 0, 153));
        createbtn1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        createbtn1.setForeground(new java.awt.Color(255, 255, 255));
        createbtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pen.png"))); // NOI18N
        createbtn1.setText("Create");
        createbtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createbtn1ActionPerformed(evt);
            }
        });

        updatebtn2.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn2.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn2.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        updatebtn2.setText("Update");
        updatebtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn2ActionPerformed(evt);
            }
        });

        updatebtn3.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn3.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn3.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        updatebtn3.setText("Clear");
        updatebtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn3ActionPerformed(evt);
            }
        });

        deletebtn1.setBackground(new java.awt.Color(204, 0, 51));
        deletebtn1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deletebtn1.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deletebtn1.setText("Delete");
        deletebtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FamilyPageLayout = new javax.swing.GroupLayout(FamilyPage);
        FamilyPage.setLayout(FamilyPageLayout);
        FamilyPageLayout.setHorizontalGroup(
            FamilyPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FamilyPageLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(FamilyPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FamilyPageLayout.createSequentialGroup()
                        .addComponent(refreshFamily, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 782, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(FamilyPageLayout.createSequentialGroup()
                        .addComponent(createbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deletebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        FamilyPageLayout.setVerticalGroup(
            FamilyPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FamilyPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refreshFamily, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(FamilyPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Family Database", FamilyPage);

        jPanel1.setBackground(new java.awt.Color(36, 36, 36));

        jPanel4.setBackground(new java.awt.Color(16, 26, 51));

        jLabel30.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Commorbidities:");

        family_vaccinated1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_vaccinated1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        family_vaccinated1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                family_vaccinated1ActionPerformed(evt);
            }
        });

        family_ineligibleVaccine1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_ineligibleVaccine1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        family_adults1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        family_adults1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        family_adults1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                family_adults1ActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Philhealth ID:");

        jLabel33.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("PWD ID:");

        jLabel36.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Health ID:");

        jLabel37.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("HEALTH RECORD");

        family_tableID1.setEditable(false);
        family_tableID1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Table ID:");

        health_id.setEditable(false);
        health_id.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        health_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                health_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(health_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(family_tableID1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel36))))
                        .addGap(41, 41, 41))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(family_adults1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addContainerGap(138, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(family_vaccinated1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)
                        .addComponent(family_ineligibleVaccine1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(health_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_tableID1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(family_adults1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addComponent(jLabel32)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(family_vaccinated1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel33)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(family_ineligibleVaccine1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(400, Short.MAX_VALUE)))
        );

        updatebtn4.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn4.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn4.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        updatebtn4.setText("Update");
        updatebtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn4ActionPerformed(evt);
            }
        });

        updatebtn5.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn5.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn5.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        updatebtn5.setText("Clear");
        updatebtn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn5ActionPerformed(evt);
            }
        });

        deletebtn2.setBackground(new java.awt.Color(204, 0, 51));
        deletebtn2.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deletebtn2.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deletebtn2.setText("Delete");
        deletebtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletebtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtn2ActionPerformed(evt);
            }
        });

        refreshFamily1.setBackground(new java.awt.Color(153, 0, 153));
        refreshFamily1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshFamily1.setForeground(new java.awt.Color(255, 255, 255));
        refreshFamily1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshFamily1.setText("Refresh");
        refreshFamily1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFamily1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        HealthInfoList.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        HealthInfoList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Health ID", "Philhealth ID", "PWD ID", "Commorbities"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HealthInfoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HealthInfoListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(HealthInfoList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(updatebtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                        .addComponent(deletebtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(refreshFamily1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshFamily1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updatebtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Health Database", jPanel1);

        jPanel6.setBackground(new java.awt.Color(16, 26, 51));
        jPanel6.setPreferredSize(new java.awt.Dimension(359, 607));

        jLabel39.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("VACCINE RECORD");

        jLabel34.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("2nd Dose Date:");

        jLabel35.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Vaccine ID:");

        jLabel40.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("1st Dose Date:");

        vaccine_firstdose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaccine_firstdoseActionPerformed(evt);
            }
        });

        vaccine_seconddose.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        vaccine_seconddose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaccine_seconddoseActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Vaccine Brand:");

        vaccine_vbrand.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        vaccine_vbrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaccine_vbrandActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Table ID:");

        covidinfo_id.setEditable(false);
        covidinfo_id.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        covidinfo_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                covidinfo_idActionPerformed(evt);
            }
        });

        vaccine_id.setEditable(false);
        vaccine_id.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        vaccine_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaccine_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vaccine_seconddose, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vaccine_firstdose, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vaccine_vbrand, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(covidinfo_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vaccine_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vaccine_firstdose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vaccine_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(covidinfo_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vaccine_seconddose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vaccine_vbrand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(343, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(36, 36, 36));

        refreshFamily2.setBackground(new java.awt.Color(153, 0, 153));
        refreshFamily2.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshFamily2.setForeground(new java.awt.Color(255, 255, 255));
        refreshFamily2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshFamily2.setText("Refresh");
        refreshFamily2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFamily2ActionPerformed(evt);
            }
        });

        updatebtn6.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn6.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn6.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        updatebtn6.setText("Update");
        updatebtn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn6ActionPerformed(evt);
            }
        });

        updatebtn7.setBackground(new java.awt.Color(153, 0, 153));
        updatebtn7.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updatebtn7.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        updatebtn7.setText("Clear");
        updatebtn7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebtn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtn7ActionPerformed(evt);
            }
        });

        deletebtn3.setBackground(new java.awt.Color(204, 0, 51));
        deletebtn3.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deletebtn3.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deletebtn3.setText("Delete");
        deletebtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletebtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtn3ActionPerformed(evt);
            }
        });

        vaccine_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaccine_searchActionPerformed(evt);
            }
        });
        vaccine_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vaccine_searchKeyReleased(evt);
            }
        });

        VaccineInfoList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Covid ID", "1st Dose Date:", "2nd Dose Date:", "Vaccine Brand"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VaccineInfoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VaccineInfoListMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(VaccineInfoList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(refreshFamily2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(vaccine_search, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(updatebtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatebtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(446, 446, 446)
                        .addComponent(deletebtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vaccine_search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshFamily2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletebtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Vaccine Record", jPanel5);

        jPanel16.setBackground(new java.awt.Color(36, 36, 36));

        jPanel15.setBackground(new java.awt.Color(16, 26, 51));

        jLabel63.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("COVID RECORD");

        jLabel64.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Covid ID:");

        covid_id_record_pk.setEditable(false);
        covid_id_record_pk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                covid_id_record_pkActionPerformed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Covid Condition:");

        jLabel66.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Status:");

        jLabel67.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Vaccine Status:");

        jLabel68.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Table ID:");

        covid_id.setEditable(false);
        covid_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                covid_idActionPerformed(evt);
            }
        });

        covidStatusField.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        covidStatusField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO INFO", "Infected", "Recovered", "Deceased" }));

        covidConditionField.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        covidConditionField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO INFO", "Negative", "Positive" }));

        covidVaccineField.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        covidVaccineField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO INFO", "Unvaccinated", "1st dose", "2nd dose" }));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(covidVaccineField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(covidStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(covidConditionField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(covid_id_record_pk, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(covid_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel68))))
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(covid_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(covidConditionField))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jLabel68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(covid_id_record_pk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(covidStatusField))
                .addGap(18, 18, 18)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(covidVaccineField)
                .addGap(342, 342, 342))
        );

        refreshCovidRecordBtn.setBackground(new java.awt.Color(153, 0, 153));
        refreshCovidRecordBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshCovidRecordBtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshCovidRecordBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshCovidRecordBtn.setText("Refresh");
        refreshCovidRecordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshCovidRecordBtnActionPerformed(evt);
            }
        });

        CovidSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CovidSearchActionPerformed(evt);
            }
        });
        CovidSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CovidSearchKeyReleased(evt);
            }
        });

        covidRecordUpdateBtn.setBackground(new java.awt.Color(153, 0, 153));
        covidRecordUpdateBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        covidRecordUpdateBtn.setForeground(new java.awt.Color(255, 255, 255));
        covidRecordUpdateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        covidRecordUpdateBtn.setText("Update");
        covidRecordUpdateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        covidRecordUpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                covidRecordUpdateBtnActionPerformed(evt);
            }
        });

        clearCovidRecordBtn.setBackground(new java.awt.Color(153, 0, 153));
        clearCovidRecordBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        clearCovidRecordBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearCovidRecordBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        clearCovidRecordBtn.setText("Clear");
        clearCovidRecordBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearCovidRecordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearCovidRecordBtnActionPerformed(evt);
            }
        });

        deleteBtnCovidRecord.setBackground(new java.awt.Color(204, 0, 51));
        deleteBtnCovidRecord.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deleteBtnCovidRecord.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtnCovidRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deleteBtnCovidRecord.setText("Delete");
        deleteBtnCovidRecord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBtnCovidRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnCovidRecordActionPerformed(evt);
            }
        });

        covidRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Covid ID", "Covid Condition", "Status", "Vaccine Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        covidRecordTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                covidRecordTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(covidRecordTable);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(covidRecordUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearCovidRecordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteBtnCovidRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(refreshCovidRecordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CovidSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CovidSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshCovidRecordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane5)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtnCovidRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(covidRecordUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearCovidRecordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Covid Record", jPanel14);

        jPanel18.setBackground(new java.awt.Color(36, 36, 36));

        jPanel19.setBackground(new java.awt.Color(16, 26, 51));

        jLabel69.setFont(new java.awt.Font("Nirmala UI", 0, 36)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("CONTACT RECORD");

        jLabel70.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Tracking ID:");

        jLabel71.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Table ID:");

        tracking_id_contact_pk.setEditable(false);
        tracking_id_contact_pk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracking_id_contact_pkActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Infectious:");

        jLabel73.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Close Contacts:");

        close_contacts_table.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        close_contacts_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_contacts_tableActionPerformed(evt);
            }
        });

        tracking_id.setEditable(false);
        tracking_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracking_idActionPerformed(evt);
            }
        });

        barangay_resident_bool.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        barangay_resident_bool.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO INFO", "True", "False" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(barangay_resident_bool, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tracking_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel69)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(close_contacts_table, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tracking_id_contact_pk, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tracking_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barangay_resident_bool))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tracking_id_contact_pk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(close_contacts_table, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contactSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactSearchActionPerformed(evt);
            }
        });
        contactSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactSearchKeyReleased(evt);
            }
        });

        refreshContactBtn.setBackground(new java.awt.Color(153, 0, 153));
        refreshContactBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        refreshContactBtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshContactBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh (1).png"))); // NOI18N
        refreshContactBtn.setText("Refresh");
        refreshContactBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshContactBtnActionPerformed(evt);
            }
        });

        updateContactBtn.setBackground(new java.awt.Color(153, 0, 153));
        updateContactBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        updateContactBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateContactBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        updateContactBtn.setText("Update");
        updateContactBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateContactBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateContactBtnActionPerformed(evt);
            }
        });

        contactClearBtn.setBackground(new java.awt.Color(153, 0, 153));
        contactClearBtn.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        contactClearBtn.setForeground(new java.awt.Color(255, 255, 255));
        contactClearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rubber (1).png"))); // NOI18N
        contactClearBtn.setText("Clear");
        contactClearBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contactClearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactClearBtnActionPerformed(evt);
            }
        });

        deleteContact.setBackground(new java.awt.Color(204, 0, 51));
        deleteContact.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        deleteContact.setForeground(new java.awt.Color(255, 255, 255));
        deleteContact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash.png"))); // NOI18N
        deleteContact.setText("Delete");
        deleteContact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteContactActionPerformed(evt);
            }
        });

        contactTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Tracking ID", "Infectious", "Close Contacts"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contactTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(contactTable);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(refreshContactBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(contactSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(updateContactBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(contactClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteContact, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshContactBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteContact, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateContactBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Contact Record", jPanel17);

        javax.swing.GroupLayout ContentHolderLayout = new javax.swing.GroupLayout(ContentHolder);
        ContentHolder.setLayout(ContentHolderLayout);
        ContentHolderLayout.setHorizontalGroup(
            ContentHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        ContentHolderLayout.setVerticalGroup(
            ContentHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ContentHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ContentHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createbtnActionPerformed
        // TODO add your handling code here:

        if (lastName.getText().isEmpty() == true || firstName.getText().isEmpty() || birthDate.getText().isEmpty()
                || age.getText().isEmpty() || address.getText().isEmpty() || contactNumber.getText().isEmpty() || occupation.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {

            String lastNameSQL = lastName.getText();
            String firstNameSQL = firstName.getText();
            String birthDateSQL = birthDate.getText();
            String ageSQL = age.getText();
            String addressSQL = address.getText();
            String contactNumberSQL = contactNumber.getText();
            String occupationSQL = occupation.getText();
            String familyIDSQL = String.valueOf(jComboBox1.getSelectedItem());

            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String sql = "INSERT INTO resident_info (last_name, first_name, birth_date, age, address, contact_no, occupation, family_id) "
                        + "VALUES ('" + lastNameSQL + "', '" + firstNameSQL + "', '" + birthDateSQL + "', '" + ageSQL + "', '" + addressSQL + "', '" + contactNumberSQL + "', '" + occupationSQL + "', '" + familyIDSQL + "')";
                String sql2 = "INSERT INTO health_info (health_id) SELECT health_id from resident_info where contact_no='" + contactNumberSQL + "'";
                String sql3 = "INSERT INTO vaccine_record (vaccine_id) SELECT vaccine_id from resident_info where contact_no='" + contactNumberSQL + "'";
                String sql4 = "INSERT INTO covid_record (covid_id) SELECT covid_id from resident_info where contact_no='" + contactNumberSQL + "'";
                String sql5 = "INSERT INTO contact_table (tracking_id) SELECT tracking_id from resident_info where contact_no='" + contactNumberSQL + "'";
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql2);
                stmt.executeUpdate(sql3);
                stmt.executeUpdate(sql4);
                stmt.executeUpdate(sql5);
                jOptionPane1.showMessageDialog(null, "Record Inserted Successfully");

                System.out.println("Record inserted");

                BarangayTableUpdate();
                HealthTableUpdate();
                VaccineTableUpdate();
                CovidRecordUpdate();
                ContactTableUpdate();
                
                BarangayTableClear();

                conn.close();
            } catch (SQLException se) {
                System.out.println(se);
            }
        }
    }//GEN-LAST:event_createbtnActionPerformed

    private void updatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtnActionPerformed
        // TODO add your handling code here:

        if (lastName.getText().isEmpty() == true || firstName.getText().isEmpty() || birthDate.getText().isEmpty()
                || age.getText().isEmpty() || address.getText().isEmpty() || contactNumber.getText().isEmpty() || occupation.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {

            int tableIDSQL = Integer.parseInt(tableID.getText());
            String lastNameSQL = lastName.getText();
            String firstNameSQL = firstName.getText();
            String birthDateSQL = birthDate.getText();
            String ageSQL = age.getText();
            String addressSQL = address.getText();
            String contactNumberSQL = contactNumber.getText();
            String occupationSQL = occupation.getText();
            String familyIDSQL = String.valueOf(jComboBox1.getSelectedItem());

            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String sql = "UPDATE resident_info "
                        + "set last_name='" + lastNameSQL + "', first_name='" + firstNameSQL + "', birth_date='" + birthDateSQL + "', "
                        + "age='" + ageSQL + "', address='" + addressSQL + "', contact_no='" + contactNumberSQL + "', occupation='" + occupationSQL + "', family_id='" + familyIDSQL + "'  "
                        + "WHERE id= " + tableIDSQL;
                stmt.executeUpdate(sql);
                jOptionPane1.showMessageDialog(null, "Record Updated Successfully");

                System.out.println("Record updated");

                BarangayTableUpdate();
                BarangayTableClear();

                conn.close();
            } catch (SQLException se) {
                System.out.println(se);
            }

        }

    }//GEN-LAST:event_updatebtnActionPerformed

    private void basicinfolistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_basicinfolistMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
        int selectedRowIndex = basicinfolist.getSelectedRow();
        tableID.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        barangayID.setText(tm.getValueAt(selectedRowIndex, 1).toString());
        lastName.setText(tm.getValueAt(selectedRowIndex, 2).toString());
        firstName.setText(tm.getValueAt(selectedRowIndex, 3).toString());
        birthDate.setText(tm.getValueAt(selectedRowIndex, 4).toString());
        age.setText(tm.getValueAt(selectedRowIndex, 5).toString());
        address.setText(tm.getValueAt(selectedRowIndex, 6).toString());
        contactNumber.setText(tm.getValueAt(selectedRowIndex, 7).toString());
        occupation.setText(tm.getValueAt(selectedRowIndex, 8).toString());

        covidID.setText(tm.getValueAt(selectedRowIndex, 10).toString());
        healthID.setText(tm.getValueAt(selectedRowIndex, 11).toString());
        vaccineID.setText(tm.getValueAt(selectedRowIndex, 12).toString());
        trackingID.setText(tm.getValueAt(selectedRowIndex, 13).toString());

        jComboBox1.getModel().setSelectedItem(tm.getValueAt(selectedRowIndex, 9));


    }//GEN-LAST:event_basicinfolistMouseClicked

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
        int tableIDSQL = Integer.parseInt(tableID.getText());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE resident_info WHERE id= " + tableIDSQL;
            int dialogResult = jOptionPane1.showConfirmDialog(null, "This record will be permanently deleted. Are you sure?");
            if (dialogResult == jOptionPane1.YES_OPTION) {
                stmt.executeUpdate(sql);
                BarangayTableUpdate();
                jOptionPane1.showMessageDialog(null, "Record Deleted Successfully");
                System.out.println("Record deleted");
                BarangayTableClear();
            }

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deletebtnActionPerformed

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        // TODO add your handling code here:
        BarangayTableUpdate();
        TableDropDown();
        VaccineTableDropDown();
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageActionPerformed

    private void birthDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthDateActionPerformed

    private void addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressActionPerformed

    private void family_adultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_family_adultsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_family_adultsActionPerformed

    private void updatebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn1ActionPerformed
        // TODO add your handling code here:
        BarangayTableClear();
    }//GEN-LAST:event_updatebtn1ActionPerformed

    private void tableIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableIDActionPerformed

    private void familyInfoListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_familyInfoListMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) familyInfoList.getModel();
        int selectedRowIndex = familyInfoList.getSelectedRow();
        family_tableID.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        family_familyID.setText(tm.getValueAt(selectedRowIndex, 1).toString());
        family_vaccinated.setText(tm.getValueAt(selectedRowIndex, 2).toString());
        family_ineligibleVaccine.setText(tm.getValueAt(selectedRowIndex, 3).toString());
        family_adults.setText(tm.getValueAt(selectedRowIndex, 4).toString());
        family_seniors.setText(tm.getValueAt(selectedRowIndex, 5).toString());
        family_minors.setText(tm.getValueAt(selectedRowIndex, 6).toString());
        family_infected.setText(tm.getValueAt(selectedRowIndex, 7).toString());
    }//GEN-LAST:event_familyInfoListMouseClicked

    private void refreshFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFamilyActionPerformed
        // TODO add your handling code here:
        FamilyTableClear();
    }//GEN-LAST:event_refreshFamilyActionPerformed

    private void createbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createbtn1ActionPerformed
        // TODO add your handling code here:

        if (family_vaccinated.getText().isEmpty() == true || family_ineligibleVaccine.getText().isEmpty() || family_adults.getText().isEmpty()
                || family_seniors.getText().isEmpty() || family_minors.getText().isEmpty() || family_infected.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {

            String familyVaccinatedSQL = family_vaccinated.getText();
            String familyIneligibleVaccineSQL = family_ineligibleVaccine.getText();
            String familyAdultsSQL = family_adults.getText();
            String familySeniorsSQL = family_seniors.getText();
            String familyMinorsSQL = family_minors.getText();
            String familyInfectedSQL = family_infected.getText();

            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String sql = "INSERT INTO family_info (fa_vaccinated, fa_ineligible_vaccine, fa_adults, fa_seniors, fa_minors, fa_infected) "
                        + "VALUES ('" + familyVaccinatedSQL + "', '" + familyIneligibleVaccineSQL + "', '" + familyAdultsSQL + "', '" + familySeniorsSQL + "', '" + familyMinorsSQL + "', '" + familyInfectedSQL + "')";
                stmt.executeUpdate(sql);
                jOptionPane1.showMessageDialog(null, "Record Inserted Successfully");

                System.out.println("Record inserted");

                FamilyTableUpdate();
                FamilyTableClear();

                conn.close();
            } catch (SQLException se) {
                System.out.println(se);
            }
        }
    }//GEN-LAST:event_createbtn1ActionPerformed

    private void updatebtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn2ActionPerformed
        // TODO add your handling code here:

        if (family_vaccinated.getText().isEmpty() == true || family_ineligibleVaccine.getText().isEmpty() || family_adults.getText().isEmpty()
                || family_seniors.getText().isEmpty() || family_minors.getText().isEmpty() || family_infected.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {

            int tableIDSQL = Integer.parseInt(family_tableID.getText());
            String familyVaccinatedSQL = family_vaccinated.getText();
            String familyIneligibleVaccineSQL = family_ineligibleVaccine.getText();
            String familyAdultsSQL = family_adults.getText();
            String familySeniorsSQL = family_seniors.getText();
            String familyMinorsSQL = family_minors.getText();
            String familyInfectedSQL = family_infected.getText();

            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String sql = "UPDATE family_info "
                        + "set fa_vaccinated='" + familyVaccinatedSQL + "', fa_ineligible_vaccine='" + familyIneligibleVaccineSQL + "', fa_adults='" + familyAdultsSQL + "', "
                        + "fa_seniors='" + familySeniorsSQL + "', fa_minors='" + familyMinorsSQL + "', fa_infected='" + familyInfectedSQL + "'  "
                        + "WHERE id= " + tableIDSQL;
                stmt.executeUpdate(sql);
                jOptionPane1.showMessageDialog(null, "Record Updated Successfully");

                System.out.println("Record inserted");

                FamilyTableUpdate();
                FamilyTableClear();

                conn.close();
            } catch (SQLException se) {
                System.out.println(se);
            }
        }

    }//GEN-LAST:event_updatebtn2ActionPerformed

    private void updatebtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn3ActionPerformed
        // TODO add your handling code here:
        FamilyTableClear();
    }//GEN-LAST:event_updatebtn3ActionPerformed

    private void deletebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtn1ActionPerformed
        // TODO add your handling code here:
        int tableIDSQL = Integer.parseInt(family_tableID.getText());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE family_info WHERE id= " + tableIDSQL;
            int dialogResult = jOptionPane1.showConfirmDialog(null, "This record will be permanently deleted. Are you sure?");
            if (dialogResult == jOptionPane1.YES_OPTION) {
                stmt.executeUpdate(sql);
                FamilyTableUpdate();
                jOptionPane1.showMessageDialog(null, "Record Deleted Successfully");
                System.out.println("Record deleted");
                FamilyTableClear();
            }

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deletebtn1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    private void updatebtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn4ActionPerformed
        // TODO add your handling code here:
        if (family_vaccinated1.getText().isEmpty() == true || family_ineligibleVaccine1.getText().isEmpty() || family_adults1.getText().isEmpty()
                || family_tableID1.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {
        
        String phid = family_vaccinated1.getText();
        String pwid = family_ineligibleVaccine1.getText();
        String com = family_adults1.getText();
        String idn = family_tableID1.getText();
        int idno = Integer.parseInt(idn);
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "UPDATE health_info set philhealth_id='" + phid + "', pwd_id='" + pwid + "', comorbidities='" + com + "' WHERE id=" + idno;
            stmt.executeUpdate(sql);
            jOptionPane1.showMessageDialog(null, "Updated records successfully");
            HealthTableUpdate();
            HealthTableClear();
//            HealthDropDown();
            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
        }
    }//GEN-LAST:event_updatebtn4ActionPerformed

    private void updatebtn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn5ActionPerformed
        // TODO add your handling code here:
        HealthTableClear();
    }//GEN-LAST:event_updatebtn5ActionPerformed

    private void deletebtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtn2ActionPerformed
        // TODO add your handling code here:
        String idn = family_tableID1.getText();
        int idno = Integer.parseInt(idn);

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE from health_info WHERE id=" + idno;
            stmt.executeUpdate(sql);
            int dialogResult = jOptionPane1.showConfirmDialog(null, "Would you like to delete this record?");
            if (dialogResult == jOptionPane1.YES_OPTION) {

                stmt.executeUpdate(sql);
                jOptionPane1.showMessageDialog(null, "Deleted successfully");
            }
            HealthTableUpdate();
            HealthTableClear();
//            HealthDropDown();

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deletebtn2ActionPerformed

    private void refreshFamily1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFamily1ActionPerformed
        // TODO add your handling code here:
        HealthTableUpdate();
//        HealthDropDown();
    }//GEN-LAST:event_refreshFamily1ActionPerformed

    private void HealthInfoListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HealthInfoListMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) HealthInfoList.getModel();
        int selectedRowIndex = HealthInfoList.getSelectedRow();
        family_tableID1.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        health_id.setText(tm.getValueAt(selectedRowIndex, 1).toString());

        family_vaccinated1.setText(tm.getValueAt(selectedRowIndex, 2).toString());
        family_ineligibleVaccine1.setText(tm.getValueAt(selectedRowIndex, 3).toString());
        family_adults1.setText(tm.getValueAt(selectedRowIndex, 4).toString());


    }//GEN-LAST:event_HealthInfoListMouseClicked

    private void family_adults1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_family_adults1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_family_adults1ActionPerformed

    private void refreshFamily2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFamily2ActionPerformed
        // TODO add your handling code here:
        VaccineTableUpdate();
        VaccineTableDropDown();
    }//GEN-LAST:event_refreshFamily2ActionPerformed

    private void updatebtn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn6ActionPerformed
         if (vaccine_firstdose.getText().isEmpty() == true || vaccine_seconddose.getText().isEmpty() || vaccine_vbrand.getText().isEmpty()) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {
        
        int id = Integer.parseInt(covidinfo_id.getText());
        String first_dose = vaccine_firstdose.getText();
        String second_dose = vaccine_seconddose.getText();
        String vaccine_brand = vaccine_vbrand.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "UPDATE vaccine_record set first_dose_date='" + first_dose + "', second_dose_date='" + second_dose + "', vaccine_brand='" + vaccine_brand + "' WHERE id = " + id;
            stmt.executeUpdate(sql);
            jOptionPane1.showMessageDialog(null, "Record Updated Successfully");
            System.out.println("Record inserted");

            VaccineTableUpdate();
            VaccineTableDropDown();
            VaccineTableClear();

            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
         }
    }//GEN-LAST:event_updatebtn6ActionPerformed

    private void updatebtn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtn7ActionPerformed
        // TODO add your handling code here:
        VaccineTableClear();
    }//GEN-LAST:event_updatebtn7ActionPerformed

    private void deletebtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtn3ActionPerformed
        int vaccineIDSQL = Integer.parseInt(covidinfo_id.getText());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE vaccine_record WHERE id= " + vaccineIDSQL;
            int dialogResult = jOptionPane1.showConfirmDialog(null, "This record will be permanently deleted. Are you sure?");
            if (dialogResult == jOptionPane1.YES_OPTION) {
                stmt.executeUpdate(sql);
                BarangayTableUpdate();
                jOptionPane1.showMessageDialog(null, "Record Deleted Successfully");
                System.out.println("Record deleted");
                VaccineTableUpdate();
                VaccineTableClear();
            }

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deletebtn3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jTextField2ActionPerformed

    private void vaccine_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vaccine_searchKeyReleased
        String search = vaccine_search.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from vaccine_record WHERE vaccine_id like '%" + search + "%'");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) VaccineInfoList.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("vaccine_id"),
                    rs.getString("first_dose_date"),
                    rs.getString("second_dose_date"),
                    rs.getString("vaccine_brand")};
                tm.addRow(row);
            }
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_vaccine_searchKeyReleased

    private void covidinfo_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_covidinfo_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_covidinfo_idActionPerformed

    private void vaccine_seconddoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaccine_seconddoseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vaccine_seconddoseActionPerformed

    private void vaccine_vbrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaccine_vbrandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vaccine_vbrandActionPerformed

    private void family_vaccinated1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_family_vaccinated1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_family_vaccinated1ActionPerformed

    private void vaccine_firstdoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaccine_firstdoseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vaccine_firstdoseActionPerformed

    private void vaccine_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaccine_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vaccine_searchActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        String search = jTextField2.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from health_info WHERE health_id like '%" + search + "%'");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) HealthInfoList.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("health_id"),
                    rs.getString("philhealth_id"),
                    rs.getString("pwd_id"),
                    rs.getString("comorbidities")};
                tm.addRow(row);
            }
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void HOME2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOME2MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
//        HOME2.setBackground( Color.BLUE );
    }//GEN-LAST:event_HOME2MouseClicked

    private void HOME3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOME3MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_HOME3MouseClicked

    private void HOME4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOME4MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_HOME4MouseClicked

    private void HOME5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOME5MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_HOME5MouseClicked

    private void covid_id_record_pkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_covid_id_record_pkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_covid_id_record_pkActionPerformed

    private void refreshCovidRecordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshCovidRecordBtnActionPerformed
        CovidRecordClear();
        CovidRecordUpdate();
        CovidRecordDropDown();
    }//GEN-LAST:event_refreshCovidRecordBtnActionPerformed

    private void CovidSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CovidSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CovidSearchActionPerformed

    private void CovidSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CovidSearchKeyReleased
        String search = CovidSearch.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from covid_record WHERE covid_id like '%" + search + "%'");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) covidRecordTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getString("id"),
                    rs.getString("covid_id"),
                    rs.getString("covid_condition"),
                    rs.getString("status"),
                    rs.getString("vaccine_status")};

                tm.addRow(row);
            }
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_CovidSearchKeyReleased

    private void covidRecordUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_covidRecordUpdateBtnActionPerformed
        // TODO add your handling code here:
       
        int Id = Integer.parseInt(covid_id_record_pk.getText());

        String covidCondition = String.valueOf(covidConditionField.getSelectedItem());
        String Status = String.valueOf(covidStatusField.getSelectedItem());
        String vaccineStatus = String.valueOf(covidVaccineField.getSelectedItem());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "UPDATE covid_record " + "set covid_condition='" + covidCondition + "', status='" + Status + "', vaccine_status='" + vaccineStatus + "' " + "WHERE id= " + Id;

            stmt.executeUpdate(sql);
            jOptionPane1.showMessageDialog(null, "Record Updated Successfully");

            System.out.println("Record updated");

            CovidRecordUpdate();
            CovidRecordDropDown();
            CovidRecordClear();

            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_covidRecordUpdateBtnActionPerformed

    private void clearCovidRecordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearCovidRecordBtnActionPerformed
        // TODO add your handling code here:
        CovidRecordClear();
    }//GEN-LAST:event_clearCovidRecordBtnActionPerformed

    private void deleteBtnCovidRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnCovidRecordActionPerformed
        // TODO add your handling code here:
        int Id = Integer.parseInt(covid_id_record_pk.getText());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE covid_record WHERE id= " + Id;
            int dialogResult = jOptionPane1.showConfirmDialog(null, "This record will be permanently deleted. Are you sure?");
            if (dialogResult == jOptionPane1.YES_OPTION) {
                stmt.executeUpdate(sql);
                BarangayTableUpdate();
                jOptionPane1.showMessageDialog(null, "Record Deleted Successfully");
                System.out.println("Record deleted");
                CovidRecordUpdate();
                CovidRecordDropDown();
                CovidRecordClear();
            }

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deleteBtnCovidRecordActionPerformed

    private void tracking_id_contact_pkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tracking_id_contact_pkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tracking_id_contact_pkActionPerformed

    private void close_contacts_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_contacts_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_close_contacts_tableActionPerformed

    private void contactSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactSearchActionPerformed

    private void contactSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactSearchKeyReleased
        // TODO add your handling code here:
        String search = contactSearch.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from contact_table WHERE tracking_id like '%" + search + "%'");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) contactTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getString("id"),
                    rs.getString("tracking_id"),
                    rs.getString("from_barangay"),
                    rs.getString("close_contacts")};
                tm.addRow(row);
            }
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_contactSearchKeyReleased

    private void refreshContactBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshContactBtnActionPerformed
        // TODO add your handling code here:
        ContactTableUpdate();
        ContactRecordDropDown();
        ContactTableClear();
    }//GEN-LAST:event_refreshContactBtnActionPerformed

    private void updateContactBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateContactBtnActionPerformed
        // TODO add your handling code here
        if (close_contacts_table.getText().isEmpty() == true) {
            jOptionPane1.showMessageDialog(null, "Please fill in all the fields.");
        } else {
        
        int trackingId = Integer.parseInt(tracking_id_contact_pk.getText());
        String fromBarangay = String.valueOf(barangay_resident_bool.getSelectedItem());
        String closeContacts = close_contacts_table.getText();

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "UPDATE contact_table " + "set from_barangay='" + fromBarangay + "', close_contacts='" + closeContacts + "' " + "WHERE id= " + trackingId;

            stmt.executeUpdate(sql);
            jOptionPane1.showMessageDialog(null, "Record Updated Successfully");

            System.out.println("Record updated");

            ContactTableUpdate();
            ContactRecordDropDown();
            ContactTableClear();

            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
        }
    }//GEN-LAST:event_updateContactBtnActionPerformed

    private void contactClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactClearBtnActionPerformed
        // TODO add your handling code here:
        ContactTableClear();
    }//GEN-LAST:event_contactClearBtnActionPerformed

    private void deleteContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteContactActionPerformed
        // TODO add your handling code here:
        int trackingId = Integer.parseInt(tracking_id_contact_pk.getText());

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE contact_table WHERE id= " + trackingId;
            int dialogResult = jOptionPane1.showConfirmDialog(null, "This record will be permanently deleted. Are you sure?");
            if (dialogResult == jOptionPane1.YES_OPTION) {
                stmt.executeUpdate(sql);
                BarangayTableUpdate();
                jOptionPane1.showMessageDialog(null, "Record Deleted Successfully");
                System.out.println("Record deleted");
                ContactTableUpdate();
                ContactRecordDropDown();
                ContactTableClear();
            }

            conn.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_deleteContactActionPerformed

    private void contactTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) contactTable.getModel();
        int selectedRowIndex = contactTable.getSelectedRow();
        tracking_id_contact_pk.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        tracking_id.setText(tm.getValueAt(selectedRowIndex, 1).toString());
        barangay_resident_bool.getModel().setSelectedItem(tm.getValueAt(selectedRowIndex, 2));
        close_contacts_table.setText(tm.getValueAt(selectedRowIndex, 3).toString());
    }//GEN-LAST:event_contactTableMouseClicked

    private void HOMEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOMEMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_HOMEMouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        String search = jTextField1.getText();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * from resident_info WHERE last_name like '%" + search + "%'");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) basicinfolist.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object row[] = {
                    rs.getInt("id"),
                    rs.getString("barangay_id"),
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("birth_date"),
                    rs.getString("age"),
                    rs.getString("address"),
                    rs.getString("contact_no"),
                    rs.getString("occupation"),
                    rs.getString("family_id"),
                    rs.getString("covid_id"),
                    rs.getString("health_id"),
                    rs.getString("vaccine_id"),
                    rs.getString("tracking_id")
                };
                tm.addRow(row);
            }
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void covidRecordTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_covidRecordTableMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) covidRecordTable.getModel();
        int selectedRowIndex = covidRecordTable.getSelectedRow();
        covid_id_record_pk.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        covid_id.setText(tm.getValueAt(selectedRowIndex, 1).toString());

        covidConditionField.getModel().setSelectedItem(tm.getValueAt(selectedRowIndex, 2));
        covidStatusField.getModel().setSelectedItem(tm.getValueAt(selectedRowIndex, 3));
        covidVaccineField.getModel().setSelectedItem(tm.getValueAt(selectedRowIndex, 4));

    }//GEN-LAST:event_covidRecordTableMouseClicked

    private void VaccineInfoListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VaccineInfoListMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tm = (DefaultTableModel) VaccineInfoList.getModel();
        int selectedRowIndex = VaccineInfoList.getSelectedRow();
        covidinfo_id.setText(tm.getValueAt(selectedRowIndex, 0).toString());
        vaccine_id.setText(tm.getValueAt(selectedRowIndex, 1).toString());

        vaccine_firstdose.setText(tm.getValueAt(selectedRowIndex, 2).toString());
        vaccine_seconddose.setText(tm.getValueAt(selectedRowIndex, 3).toString());
        vaccine_vbrand.setText(tm.getValueAt(selectedRowIndex, 4).toString());
    }//GEN-LAST:event_VaccineInfoListMouseClicked

    private void health_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_health_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_health_idActionPerformed

    private void vaccine_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaccine_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vaccine_idActionPerformed

    private void covid_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_covid_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_covid_idActionPerformed

    private void tracking_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tracking_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tracking_idActionPerformed

    private void createbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createbtn2ActionPerformed
        // TODO add your handling code here:
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat preformat = pjob.defaultPage();
        preformat.setOrientation(PageFormat.LANDSCAPE);
        PageFormat postformat = pjob.pageDialog(preformat);
        //If user does not hit cancel then print.
        if (preformat != postformat) {
            //Set print component
            pjob.setPrintable(new PrintResult(this), postformat);
            if (pjob.printDialog()) {
                try{
                pjob.print();
                }catch(Exception e){
                    System.out.print("Error: " + e);
                }
            }
        }
    
    }//GEN-LAST:event_createbtn2ActionPerformed

    private void createbtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createbtn3ActionPerformed
        // TODO add your handling code here:
        new howto().setVisible(true);
    }//GEN-LAST:event_createbtn3ActionPerformed

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
                if ("Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(basicinfoboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(basicinfoboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(basicinfoboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(basicinfoboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new basicinfoboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarangayPage;
    private javax.swing.JPanel ContentHolder;
    private javax.swing.JTextField CovidSearch;
    private javax.swing.JPanel FamilyPage;
    private javax.swing.JPanel HOME;
    private javax.swing.JPanel HOME1;
    private javax.swing.JPanel HOME2;
    private javax.swing.JPanel HOME3;
    private javax.swing.JPanel HOME4;
    private javax.swing.JPanel HOME5;
    private javax.swing.JTable HealthInfoList;
    private javax.swing.JPanel HomePage;
    private javax.swing.JTable VaccineInfoList;
    private javax.swing.JTextField address;
    private javax.swing.JTextField age;
    private javax.swing.JPanel barChart;
    private javax.swing.JTextField barangayID;
    private javax.swing.JComboBox<String> barangay_resident_bool;
    private javax.swing.JTable basicinfolist;
    private javax.swing.JTextField birthDate;
    private javax.swing.JButton clearCovidRecordBtn;
    private javax.swing.JTextField close_contacts_table;
    private javax.swing.JButton contactClearBtn;
    private javax.swing.JTextField contactNumber;
    private javax.swing.JTextField contactSearch;
    private javax.swing.JTable contactTable;
    private javax.swing.JComboBox<String> covidConditionField;
    private javax.swing.JTextField covidID;
    private javax.swing.JTable covidRecordTable;
    private javax.swing.JButton covidRecordUpdateBtn;
    private javax.swing.JComboBox<String> covidStatusField;
    private javax.swing.JComboBox<String> covidVaccineField;
    private javax.swing.JTextField covid_id;
    private javax.swing.JTextField covid_id_record_pk;
    private javax.swing.JTextField covidinfo_id;
    private javax.swing.JButton createbtn;
    private javax.swing.JButton createbtn1;
    private javax.swing.JButton createbtn2;
    private javax.swing.JButton createbtn3;
    private javax.swing.JButton deleteBtnCovidRecord;
    private javax.swing.JButton deleteContact;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton deletebtn1;
    private javax.swing.JButton deletebtn2;
    private javax.swing.JButton deletebtn3;
    private javax.swing.JTable familyInfoList;
    private javax.swing.JTextField family_adults;
    private javax.swing.JTextField family_adults1;
    private javax.swing.JTextField family_familyID;
    private javax.swing.JTextField family_ineligibleVaccine;
    private javax.swing.JTextField family_ineligibleVaccine1;
    private javax.swing.JTextField family_infected;
    private javax.swing.JTextField family_minors;
    private javax.swing.JTextField family_seniors;
    private javax.swing.JTextField family_tableID;
    private javax.swing.JTextField family_tableID1;
    private javax.swing.JTextField family_vaccinated;
    private javax.swing.JTextField family_vaccinated1;
    private javax.swing.JTextField firstName;
    private javax.swing.JTextField healthID;
    private javax.swing.JTextField health_id;
    private javax.swing.JPanel histogram;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField lastName;
    private javax.swing.JPanel lineChart;
    private javax.swing.JTextField occupation;
    private javax.swing.JPanel pieChart;
    private javax.swing.JButton refreshContactBtn;
    private javax.swing.JButton refreshCovidRecordBtn;
    private javax.swing.JButton refreshFamily;
    private javax.swing.JButton refreshFamily1;
    private javax.swing.JButton refreshFamily2;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JTextField tableID;
    private javax.swing.JLabel text;
    private javax.swing.JTextField trackingID;
    private javax.swing.JTextField tracking_id;
    private javax.swing.JTextField tracking_id_contact_pk;
    private javax.swing.JButton updateContactBtn;
    private javax.swing.JButton updatebtn;
    private javax.swing.JButton updatebtn1;
    private javax.swing.JButton updatebtn2;
    private javax.swing.JButton updatebtn3;
    private javax.swing.JButton updatebtn4;
    private javax.swing.JButton updatebtn5;
    private javax.swing.JButton updatebtn6;
    private javax.swing.JButton updatebtn7;
    private javax.swing.JTextField vaccineID;
    private javax.swing.JTextField vaccine_firstdose;
    private javax.swing.JTextField vaccine_id;
    private javax.swing.JTextField vaccine_search;
    private javax.swing.JTextField vaccine_seconddose;
    private javax.swing.JTextField vaccine_vbrand;
    // End of variables declaration//GEN-END:variables
}
