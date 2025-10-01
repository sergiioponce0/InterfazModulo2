# 📋 GestorUsuario (Java Swing)

Aplicación de escritorio en **Java Swing** que implementa un **gestor de usuarios** con un formulario de entrada de datos, área de notas y panel de previsualización en pestañas.  
El diseño está inspirado en la paleta de **Amazon** (naranja y grises oscuros).

---

## 🚀 Características

- **Cabecera (Header)** con logo cargado dinámicamente desde una URL usando `SwingWorker`.
- **Menú lateral (Sidebar)** con navegación a secciones: `Dashboard`, `Usuarios`, `Informes`, `Ajustes`, `Ayuda`.
- **Formulario central** con:
  - Campo de nombre
  - Campo de email
  - Selección de rol (`Admin`, `Editor`, `Viewer`)
  - Checkbox de estado activo
  - Área de texto para notas con scroll
- **Panel de previsualización**:
  - Pestaña **Resumen** con área de texto de solo lectura
  - Pestaña **Logs** (vacía por defecto)
- **Botonera inferior**:
  - **Cancelar** (oscuro)
  - **Limpiar** (restablece los campos)
  - **Guardar** (muestra `JOptionPane` confirmando el guardado)

GestorUsuario (JFrame) [BorderLayout]
│
├── HeaderPanel (JPanel - FlowLayout, NORTH)
│   └── JLabel (logoLabel) [icon desde URL]
│
├── MainContentPanel (JPanel - BorderLayout, CENTER)
│   │
│   ├── SidebarPanel (JPanel - GridLayout(5,1), WEST)
│   │   ├── JLabel "Dashboard"
│   │   ├── JLabel "Usuarios" [resaltado en naranja]
│   │   ├── JLabel "Informes"
│   │   ├── JLabel "Ajustes"
│   │   └── JLabel "Ayuda"
│   │
│   └── FormAndPreviewPanel (JPanel - GridLayout(1,2), CENTER)
│       │
│       ├── FormPanel (JPanel - GridBagLayout)
│       │   ├── JLabel "Nombre:" + JTextField
│       │   ├── JLabel "Email:" + JTextField
│       │   ├── JLabel "Rol:" + JComboBox
│       │   ├── JLabel "Activo:" + JCheckBox
│       │   ├── JLabel "Notas:" + JScrollPane -> JTextArea
│       │
│       └── PreviewPanel (JPanel - BorderLayout)
│           ├── JLabel "Previsualización" (NORTH)
│           └── JTabbedPane (CENTER)
│               ├── Tab "Resumen" -> JScrollPane -> JTextArea
│               └── Tab "Logs" -> JPanel
│
└── ButtonPanel (JPanel - FlowLayout, SOUTH)
    ├── JButton "Cancelar"
    ├── JButton "Limpiar"
    └── JButton "Guardar"


##CAPTURAS


