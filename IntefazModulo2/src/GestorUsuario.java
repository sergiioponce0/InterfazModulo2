import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL; // Necesario para la URL
import javax.imageio.ImageIO; // Necesario para leer la imagen desde la URL

public class GestorUsuario extends JFrame {

    // --- COLORES Y CONSTANTES TEMÁTICOS ---
    private static final Color AMAZON_ORANGE = new Color(255, 153, 0); // Color Naranja Amazon
    private static final Color DARK_HEADER = new Color(35, 47, 62);
    private static final Color DARK_SIDEBAR = new Color(42, 59, 75);
    private static final Color BACKGROUND = Color.WHITE;

    // --- Componentes del Formulario ---
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JCheckBox activeCheckbox;
    private JTextArea notesArea;
    private JTextArea previewArea;

    // --- URL DE EJEMPLO PARA EL LOGO ---
    // *** IMPORTANTE: REEMPLAZA ESTA URL CON LA URL REAL DE TU LOGO ***
    private static final String LOGO_URL = "https://static.vecteezy.com/system/resources/thumbnails/019/136/319/small_2x/amazon-logo-amazon-icon-free-free-vector.jpg";
    public GestorUsuario() {
        // --- CONFIGURACIÓN PRINCIPAL DE LA VENTANA ---
        setTitle("Gestor de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 1. Panel Superior (Header)
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 2. Panel Central (Contenido y Sidebar)
        JPanel mainContentPanel = new JPanel(new BorderLayout());

        // 2a. Sidebar de Navegación (Izquierda)
        JPanel sidebarPanel = createSidebarPanel();
        mainContentPanel.add(sidebarPanel, BorderLayout.WEST);

        // 2b. Formulario y Previsualización (Centro)
        JPanel formAndPreviewPanel = createFormAndPreviewPanel();
        mainContentPanel.add(formAndPreviewPanel, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        // 3. Panel Inferior (Botones)
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // --- MÉTODOS DE CREACIÓN DE PANELES ---

    /** Crea el panel superior e implementa la carga de la imagen desde una URL. */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(DARK_HEADER);
        panel.setPreferredSize(new Dimension(800, 80));

        JLabel logoLabel = new JLabel();
        panel.add(logoLabel);

        // *************** CÓDIGO PARA AÑADIR LA IMAGEN DESDE URL ***************

        // Ejecutamos la carga de la imagen en un hilo de trabajo (SwingWorker) para evitar
        // bloquear la interfaz gráfica mientras se descarga la imagen.
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                // 1. Crear el objeto URL
                URL url = new URL(LOGO_URL);

                // 2. Leer la imagen desde la URL
                Image originalImage = ImageIO.read(url);

                // 3. Escalar la imagen (opcional)
                Image scaledImage = originalImage.getScaledInstance(100, 60, Image.SCALE_SMOOTH);

                return new ImageIcon(scaledImage);
            }

            @Override
            protected void done() {
                try {
                    // Actualizar la interfaz gráfica en el hilo de eventos (EDT)
                    logoLabel.setIcon(get());
                    logoLabel.setText(""); // Quitar el texto si la imagen carga
                } catch (Exception e) {
                    // Manejo de errores (URL incorrecta, error de red, etc.)
                    logoLabel.setText(" [ERROR LOGO] ");
                    logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
                    logoLabel.setForeground(AMAZON_ORANGE);
                    logoLabel.setBorder(BorderFactory.createLineBorder(AMAZON_ORANGE, 2));
                    System.err.println("Error al cargar la imagen desde la URL: " + LOGO_URL);
                    e.printStackTrace();
                }
            }
        }.execute();

        // **********************************************************************

        return panel;
    }

    /** Crea el menú lateral. */
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 0, 15));
        panel.setBackground(DARK_SIDEBAR);
        panel.setPreferredSize(new Dimension(150, 0));
        panel.setBorder(new EmptyBorder(20, 10, 20, 10));

        String[] menuItems = {"Dashboard", "Usuarios", "Informes", "Ajustes", "Ayuda"};
        for (String item : menuItems) {
            JLabel label = new JLabel(item);
            label.setForeground(BACKGROUND);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            if (item.equals("Usuarios")) {
                label.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, AMAZON_ORANGE));
                label.setForeground(AMAZON_ORANGE);
            }
            label.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(label);
        }

        return panel;
    }

    private JPanel createFormAndPreviewPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel formPanel = createFormPanel();
        panel.add(formPanel);

        JPanel previewPanel = createPreviewPanel();
        panel.add(previewPanel);

        return panel;
    }

    /** Crea el panel del formulario, con la corrección del área de Notas. */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // 2. Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // 3. Rol
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] roles = {"Admin", "Editor", "Viewer"};
        roleComboBox = new JComboBox<>(roles);
        panel.add(roleComboBox, gbc);

        // 4. Activo (Checkbox)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panel.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        activeCheckbox = new JCheckBox();
        panel.add(activeCheckbox, gbc);

        // 5. Notas (TextArea) - Espacio vertical asegurado
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panel.add(new JLabel("Notas:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        notesArea = new JTextArea(5, 20);
        notesArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JScrollPane scrollPane = new JScrollPane(notesArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        panel.add(scrollPane, gbc);

        // Restablecer gbc
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1;
        gbc.weighty = 0;

        return panel;
    }

    /** Crea el panel de Previsualización. */
    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        tabbedPane.addTab("Resumen", new JScrollPane(previewArea));
        tabbedPane.addTab("Logs", new JPanel());

        JLabel titleLabel = new JLabel("Previsualización");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);

        return panel;
    }

    /** Implementa ActionListeners y el color naranja de los botones. */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panel.setBackground(Color.LIGHT_GRAY.brighter());

        JButton cancelButton = new JButton("Cancelar");
        JButton cleanButton = new JButton("Limpiar");
        JButton saveButton = new JButton("Guardar");

        // Estilo Naranja Amazon para botones Limpiar y Guardar
        saveButton.setBackground(AMAZON_ORANGE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        cleanButton.setBackground(AMAZON_ORANGE);
        cleanButton.setForeground(Color.WHITE);
        cleanButton.setFocusPainted(false);

        // Botón Cancelar en gris oscuro
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);


        // --- FUNCIONALIDAD DE LOS BOTONES ---

        // 1. Botón Limpiar
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // 2. Botón Guardar (Muestra mensaje de confirmación)
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mensaje de guardado exitoso
                JOptionPane.showMessageDialog(
                        GestorUsuario.this,
                        "¡Usuario '" + nameField.getText() + "' guardado con éxito!",
                        "Guardado Exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        panel.add(cancelButton);
        panel.add(cleanButton);
        panel.add(saveButton);

        return panel;
    }

    /** Método para limpiar todos los campos del formulario. */
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        roleComboBox.setSelectedIndex(0);
        activeCheckbox.setSelected(false);
        notesArea.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorUsuario());
    }
}
