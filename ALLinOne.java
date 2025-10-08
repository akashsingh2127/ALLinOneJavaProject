package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ALLinOne extends JFrame {

    public ALLinOne() {
        setTitle("All-in-One Project Launcher");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);

        JButton shoppingCartBtn = new JButton("Shopping Cart");
        JButton atmBtn = new JButton("ATM Simulation");
        JButton employeeBtn = new JButton("Employee Form");
        JButton calculatorBtn = new JButton("Calculator");
        JButton feedbackBtn = new JButton("Feedback Form");

        shoppingCartBtn.addActionListener(e -> new ShoppingCartSimulation());
        atmBtn.addActionListener(e -> new ATMSimulation());
        employeeBtn.addActionListener(e -> new EmployeeForm());
        calculatorBtn.addActionListener(e -> new CalculatorForm());
        feedbackBtn.addActionListener(e -> new FeedbackForm());

        add(shoppingCartBtn);
        add(atmBtn);
        add(employeeBtn);
        add(calculatorBtn);
        add(feedbackBtn);

        setVisible(true);
    }

    // ----------------- Projects -----------------

    // 1. Shopping Cart
    class ShoppingCartSimulation extends JFrame implements ActionListener {
        JCheckBox cb1, cb2, cb3;
        JButton b;
        JLabel result;

        ShoppingCartSimulation() {
            setTitle("Shopping Cart");
            setLayout(new FlowLayout());
            cb1 = new JCheckBox("Laptop - ₹50000");
            cb2 = new JCheckBox("Phone - ₹30000");
            cb3 = new JCheckBox("Headphones - ₹2000");
            b = new JButton("Generate Bill");
            result = new JLabel("");

            add(cb1); add(cb2); add(cb3); add(b); add(result);
            b.addActionListener(this);

            setSize(300, 200);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            int amount = 0;
            String msg = "Selected Items: ";
            if (cb1.isSelected()) { amount += 50000; msg += "Laptop "; }
            if (cb2.isSelected()) { amount += 30000; msg += "Phone "; }
            if (cb3.isSelected()) { amount += 2000; msg += "Headphones "; }
            msg += " | Total: ₹" + amount;
            result.setText(msg);
        }
    }

    // 2. ATM Simulation
    class ATMSimulation extends JFrame {
        JPasswordField pinField;
        JButton loginButton;
        double balance = 10000;

        ATMSimulation() {
            setTitle("ATM Simulation");
            setLayout(new FlowLayout());
            add(new JLabel("Enter PIN:"));
            pinField = new JPasswordField(10);
            add(pinField);
            loginButton = new JButton("Login");
            add(loginButton);
            loginButton.addActionListener(e -> {
                String pin = new String(pinField.getPassword());
                if (pin.equals("1234")) showATMOptions();
                else JOptionPane.showMessageDialog(this, "Invalid PIN!");
            });

            setSize(300, 200);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        void showATMOptions() {
            getContentPane().removeAll();
            setLayout(new FlowLayout());

            JButton check = new JButton("Check Balance");
            JButton deposit = new JButton("Deposit");
            JButton withdraw = new JButton("Withdraw");

            add(check); add(deposit); add(withdraw);

            check.addActionListener(ae ->
                    JOptionPane.showMessageDialog(this, "Balance: ₹" + balance));

            deposit.addActionListener(ae -> {
                String amt = JOptionPane.showInputDialog("Enter deposit amount:");
                if (amt != null && !amt.isEmpty()) balance += Double.parseDouble(amt);
            });

            withdraw.addActionListener(ae -> {
                String amt = JOptionPane.showInputDialog("Enter withdrawal amount:");
                if (amt != null && !amt.isEmpty()) balance -= Double.parseDouble(amt);
            });

            revalidate();
            repaint();
        }
    }

    // 3. Employee Form
    class EmployeeForm extends JFrame implements ActionListener {
        JTextField nameField, deptField, salaryField;
        JButton addButton;
        JTable table;
        DefaultTableModel model;

        EmployeeForm() {
            setTitle("Employee Form");
            setLayout(new FlowLayout());

            add(new JLabel("Name:")); nameField = new JTextField(10); add(nameField);
            add(new JLabel("Dept:")); deptField = new JTextField(10); add(deptField);
            add(new JLabel("Salary:")); salaryField = new JTextField(10); add(salaryField);

            addButton = new JButton("Add Employee"); add(addButton);
            addButton.addActionListener(this);

            model = new DefaultTableModel(new String[]{"Name", "Dept", "Salary"}, 0);
            table = new JTable(model);
            add(new JScrollPane(table));

            setSize(400, 300);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            model.addRow(new Object[]{nameField.getText(), deptField.getText(), salaryField.getText()});
            nameField.setText(""); deptField.setText(""); salaryField.setText("");
        }
    }

    // 4. Calculator
    class CalculatorForm extends JFrame implements ActionListener {
        JTextField tf;
        String num1 = "", num2 = "", op = "";

        CalculatorForm() {
            setTitle("Calculator");
            setLayout(new BorderLayout());

            tf = new JTextField();
            add(tf, BorderLayout.NORTH);

            JPanel panel = new JPanel(new GridLayout(4, 4));
            String buttons[] = {"7", "8", "9", "/", "4", "5", "6", "*",
                    "1", "2", "3", "-", "0", "=", "C", "+"};
            for (String s : buttons) {
                JButton b = new JButton(s);
                b.addActionListener(this);
                panel.add(b);
            }
            add(panel);

            setSize(250, 300);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (Character.isDigit(s.charAt(0))) {
                if (op.equals("")) num1 += s; else num2 += s;
                tf.setText(num1 + op + num2);
            } else if (s.equals("C")) {
                num1 = num2 = op = ""; tf.setText("");
            } else if (s.equals("=")) {
                if (num1.isEmpty() || num2.isEmpty()) return;
                double n1 = Double.parseDouble(num1), n2 = Double.parseDouble(num2), res = 0;
                switch (op) {
                    case "+": res = n1 + n2; break;
                    case "-": res = n1 - n2; break;
                    case "*": res = n1 * n2; break;
                    case "/": res = n2 != 0 ? n1 / n2 : 0; break;
                }
                tf.setText("" + res); num1 = "" + res; num2 = op = "";
            } else {
                if (!num1.isEmpty() && num2.isEmpty()) op = s;
                tf.setText(num1 + op);
            }
        }
    }

    // 5. Feedback Form
    class FeedbackForm extends JFrame implements ActionListener {
        JRadioButton r1, r2, r3, r4, r5;
        JTextArea comments;
        JButton submit;
        ButtonGroup group;

        FeedbackForm() {
            setTitle("Feedback Form");
            setLayout(new FlowLayout());

            add(new JLabel("Rate our service (1–5):"));
            r1 = new JRadioButton("1"); r2 = new JRadioButton("2"); r3 = new JRadioButton("3");
            r4 = new JRadioButton("4"); r5 = new JRadioButton("5");

            group = new ButtonGroup();
            group.add(r1); group.add(r2); group.add(r3); group.add(r4); group.add(r5);
            add(r1); add(r2); add(r3); add(r4); add(r5);

            add(new JLabel("Comments:"));
            comments = new JTextArea(5, 20);
            add(new JScrollPane(comments));

            submit = new JButton("Submit");
            add(submit);
            submit.addActionListener(this);

            setSize(300, 300);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            String rating = "";
            if (r1.isSelected()) rating = "1";
            else if (r2.isSelected()) rating = "2";
            else if (r3.isSelected()) rating = "3";
            else if (r4.isSelected()) rating = "4";
            else if (r5.isSelected()) rating = "5";

            JOptionPane.showMessageDialog(this,
                    "Thank you for your feedback!\nRating: " + rating +
                            "\nComments: " + comments.getText());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ALLinOne::new);
    }
}
