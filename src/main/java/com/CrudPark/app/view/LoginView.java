package com.CrudPark.app.view;

import com.CrudPark.app.controller.OperatorController;
import com.CrudPark.app.domain.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginView {

    public static Operator showLogin() {
        OperatorController controller = new OperatorController();
        final Operator[] opHolder = new Operator[1];

        do {
            // --- Crear ventana ---
            JDialog dialog = new JDialog((Frame) null, "Inicio de Sesión - CrudPark", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(500, 350);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new BorderLayout());

            // --- Imagen de fondo escalable ---
            ImageIcon bgIcon = new ImageIcon("src/main/resources/crudpark.png");
            Image scaledImg = bgIcon.getImage().getScaledInstance(dialog.getWidth(), dialog.getHeight(), Image.SCALE_SMOOTH);
            JLabel background = new JLabel(new ImageIcon(scaledImg));
            background.setLayout(new BorderLayout());
            dialog.setContentPane(background);

            // --- Panel transparente encima ---
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel lblTitle = new JLabel("Inicio de Sesión");
            lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel lblEmail = new JLabel("Correo:");
            lblEmail.setForeground(Color.WHITE);
            JTextField txtEmail = new JTextField(20);

            JLabel lblPassword = new JLabel("Contraseña:");
            lblPassword.setForeground(Color.WHITE);
            JPasswordField txtPassword = new JPasswordField(20);

            // --- Botones con estilo ---
            JButton btnLogin = new JButton("Iniciar sesión");
            btnLogin.setBackground(new Color(0, 123, 255));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFocusPainted(false);

            JButton btnCancel = new JButton("Cancelar");
            btnCancel.setBackground(new Color(220, 53, 69));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);

            // --- Diseño en el panel ---
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            panel.add(lblTitle, gbc);

            gbc.gridwidth = 1;
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(lblEmail, gbc);
            gbc.gridx = 1;
            panel.add(txtEmail, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(lblPassword, gbc);
            gbc.gridx = 1;
            panel.add(txtPassword, gbc);

            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(btnLogin);
            buttonPanel.add(btnCancel);
            panel.add(buttonPanel, gbc);

            background.add(panel, BorderLayout.CENTER);

            // --- Eventos ---
            final boolean[] loginSuccess = {false};

            Runnable tryLogin = () -> {
                String email = txtEmail.getText().trim();
                String password = new String(txtPassword.getPassword());

                Operator temp = controller.login(email, password);
                if (temp != null) {
                    JOptionPane.showMessageDialog(dialog, "Bienvenido " + temp.getName());
                    opHolder[0] = temp;
                    loginSuccess[0] = true;
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Credenciales inválidas o usuario inactivo");
                }
            };

            btnLogin.addActionListener(e -> tryLogin.run());
            btnCancel.addActionListener(e -> dialog.dispose());

            // --- Presionar Enter ---
            KeyListener enterKeyListener = new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        tryLogin.run();
                    }
                }
                @Override public void keyReleased(KeyEvent e) {}
                @Override public void keyTyped(KeyEvent e) {}
            };
            txtEmail.addKeyListener(enterKeyListener);
            txtPassword.addKeyListener(enterKeyListener);

            dialog.setVisible(true);

            if (opHolder[0] != null || !dialog.isDisplayable()) break;

        } while (opHolder[0] == null);

        return opHolder[0];
    }
}
