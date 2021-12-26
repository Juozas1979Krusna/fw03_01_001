
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
public class Accounts extends JFrame {
    private JButton getAccountButton,
            insertAccountButton,
            deleteAccountButton,
            updateAccountButton,
            nextButton,
            previousButton,
            lastButton,
            firstButton;
    private JList
            accountNumberList;
    private JTextField accountIDText,
            usernameText,
            passwordText,
            tsText;
    private JLabel accountIDLbl;
    private JLabel accountUsLbl;
    private JLabel accountPwLbl;
    private JLabel accountTsLbl;
    private JTextArea errorText;
    private Connection connection;
    private Statement statement;
    private ResultSet rs;
    public Accounts() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to find and load driver");
            System.exit(1);
        }
    }
    private void loadAccounts() {
        Vector v = new Vector();
        try {
            rs = statement.executeQuery("SELECT * FROM acc");
            while(rs.next()) {
                v.addElement(rs.getString("acc_id"));
            }
        } catch(SQLException e) {
            displaySQLErrors(e);
        }
        accountNumberList.setListData(v);
    }
    private void buildGUI() {
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        accountNumberList = new JList();
        loadAccounts();
        accountNumberList.setVisibleRowCount(2);
        JScrollPane accountNumberListScrollPane =
                new JScrollPane(accountNumberList);
        getAccountButton = new JButton("Get Account");
        getAccountButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            rs.first();
                            while (rs.next()) {
                                if (rs.getString("acc_id").equals(accountNumberList.getSelectedValue()))
                                    break;
                            }
                            if (!rs.isAfterLast()) {
                                accountIDText.setText(rs.getString("acc_id"));
                                usernameText.setText(rs.getString("username"));
                                passwordText.setText(rs.getString("password"));
                                tsText.setText(rs.getString("ts"));
                            }
                        } catch(SQLException selectException) {
                            displaySQLErrors(selectException);
                        }
                    }
                }
        );
//Do Insert Account Button
        insertAccountButton = new JButton("Insert Account");
        insertAccountButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement statement = connection.createStatement();
                            int i = statement.executeUpdate("INSERT INTO acc VALUES(" + accountIDText.getText() + ", " + "'" + usernameText.getText() + "', " +
                                    "'" + passwordText.getText() + "', " + "now())");
                            errorText.append("Inserted " + i
                                    + " rows successfully\n");
                            accountNumberList.removeAll();
                            loadAccounts();
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do Delete Account Button
        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement statement = connection.createStatement();
                            int i = statement.executeUpdate("DELETE FROM acc WHERE acc_id = " +accountNumberList.getSelectedValue());
                            errorText.append("Deleted " + i + " rows successfully\n");
                            accountNumberList.removeAll();
                            loadAccounts();
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do Update Account Button
        updateAccountButton = new JButton("Update Account");
        updateAccountButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement statement = connection.createStatement();
                            int i = statement.executeUpdate("UPDATE acc " +
                                    "SET username='" + usernameText.getText() + "', "
                                    + "password='" + passwordText.getText() + "', "
                                    + "ts = now() " + "WHERE acc_id = "
                                    + accountNumberList.getSelectedValue());
                            errorText.append("Updated " + i + " rows successfully\n");
                            accountNumberList.removeAll();
                            loadAccounts();
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do Next Button
        nextButton = new JButton(">");
        nextButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!rs.isLast()) {
                                rs.next();
                                accountIDText.setText(rs.getString("acc_id"));
                                usernameText.setText(rs.getString("username"));
                                passwordText.setText(rs.getString("password"));
                                tsText.setText(rs.getString("ts"));
                            }
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do Next Button
        previousButton = new JButton("<");
        previousButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!rs.isFirst()) {
                                rs.previous();
                                accountIDText.setText(rs.getString("acc_id"));
                                usernameText.setText(rs.getString("username"));
                                passwordText.setText(rs.getString("password"));
                                tsText.setText(rs.getString("ts"));
                            }
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do last Button
        lastButton = new JButton(">|");
        lastButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            rs.last();
                            accountIDText.setText(rs.getString("acc_id"));
                            usernameText.setText(rs.getString("username"));
                            passwordText.setText(rs.getString("password"));
                            tsText.setText(rs.getString("ts"));
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
//Do first Button
        firstButton = new JButton("|<");
        firstButton.addActionListener (
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            rs.first();
                            accountIDText.setText(rs.getString("acc_id"));
                            usernameText.setText(rs.getString("username"));
                            passwordText.setText(rs.getString("password"));
                            tsText.setText(rs.getString("ts"));
                        } catch(SQLException insertException) {
                            displaySQLErrors(insertException);
                        }
                    }
                }
        );
        JPanel first = new JPanel(new GridLayout(5,1));
        first.add(accountNumberListScrollPane);
        first.add(getAccountButton);
        first.add(insertAccountButton);
        first.add(deleteAccountButton);
        first.add(updateAccountButton);
        accountIDLbl = new JLabel("Record ID");
        accountUsLbl = new JLabel("Username");
        accountPwLbl = new JLabel("Password");
        accountTsLbl = new JLabel("Timestamp");
        accountIDText = new JTextField(15);
        usernameText = new JTextField(15);
        passwordText = new JTextField(15);
        tsText = new JTextField(15);
        errorText = new JTextArea(10, 25);
        errorText.setEditable(false);
        JPanel second = new JPanel();
        second.setLayout(new GridLayout(6,1));
        second.add(accountIDLbl);
        second.add(accountIDText);
        second.add(accountUsLbl);
        second.add(usernameText);
        second.add(accountPwLbl);
        second.add(passwordText);
        second.add(accountTsLbl);
        second.add(tsText);
        JPanel third = new JPanel();
        third.add(new JScrollPane(errorText));
        JPanel fourth = new JPanel();
        fourth.add(firstButton);
        fourth.add(previousButton);
        fourth.add(nextButton);
        fourth.add(lastButton);
        JPanel fifth = new JPanel();
        c.add(first);
        c.add(second);
        c.add(third);
        c.add(fourth);
        c.add(fifth);
        setSize(500,500);
        show();
    }
    public void connectToDB() {
        try {
            /**/
            String JdbcURL = "jdbc:mysql://localhost:3306/accounts?" + "serverTimezone=UTC";
            String Username = "root";
            String password = "";
            Connection con = null;

            connection = DriverManager.getConnection(JdbcURL, Username, password);
            /**/
            statement = connection.createStatement();
            statement.setMaxRows(5);
            statement.setFetchSize(2);
        } catch(SQLException connectException) {
            System.out.println(connectException.getMessage());
            System.out.println(connectException.getSQLState());
            System.out.println(connectException.getErrorCode());
            System.exit(1);
        }
    }
    private void displaySQLErrors(SQLException e) {
        errorText.append("SQLException: " + e.getMessage() + "\n\n");
        errorText.append("SQLState:" + e.getSQLState() + "\n\n");
        errorText.append("VendorError: " + e.getErrorCode()+ "\n\n");
    }
    private void init() {
        connectToDB();
    }
    public static void main(String[] args) {
        Accounts accounts = new Accounts();
        accounts.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        accounts.init();
        accounts.buildGUI();
    }
}

