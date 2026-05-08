package transporte.ui;

import transporte.servicio.Empresa;
import transporte.modelo.Viaje;
import transporte.modelo.Bus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private Empresa empresa;
    
    private JTable tablaViajes;
    private DefaultTableModel modeloTabla;
    
    private JTable tablaBuses;
    private DefaultTableModel modeloBuses;
    
    private CardLayout cardLayout;
    private JPanel panelCentral; 
    
    private JButton crearBotonMenu(String texto) {

        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);

        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        // efecto
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(52, 58, 64));
                boton.setOpaque(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(33, 37, 41));
                boton.setOpaque(true);
            }
        });

        return boton;
    }
    
    public VentanaPrincipal(Empresa empresa) {
        this.empresa = empresa;

        setTitle("Sistema de Transporte");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        crearMenuLateral();
        crearPanelPrincipal();

        setVisible(true);
    }

    // ================= PANEL IZQUIERDO =================

    private void crearMenuLateral() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(180, 0));
        panel.setBackground(new Color(33, 37, 41)); // gris oscuro

        // Título
        JLabel titulo = new JLabel("TRANSPORTE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        panel.add(titulo);

        // Botones
        JButton btnInicio = crearBotonMenu("Inicio");
        JButton btnBuses = crearBotonMenu("Autobuses");
        JButton btnConductores = crearBotonMenu("Conductores");
        JButton btnViajes = crearBotonMenu("Viajes");

        panel.add(btnInicio);
        panel.add(btnBuses);
        panel.add(btnConductores);
        panel.add(btnViajes);

        add(panel, BorderLayout.WEST);

        // EVENTOS - CONECTAR NAVEGACIÓN
        btnInicio.addActionListener(e -> {
            cargarInicio();
            cardLayout.show(panelCentral, "INICIO");
        });

        btnBuses.addActionListener(e -> {
            cargarBuses();
            cardLayout.show(panelCentral, "BUSES");
        });

        btnViajes.addActionListener(e -> {
            cargarTabla();
            cardLayout.show(panelCentral, "VIAJES");
        });
        
        btnConductores.addActionListener(e -> {
            cargarConductores();
            cardLayout.show(panelCentral, "CONDUCTORES");
        });
    }
    
    // ================= PANEL CENTRAL =================

    private void crearPanelPrincipal() {

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelCentral.add(crearPanelInicio(), "INICIO");
        panelCentral.add(crearPanelViajes(), "VIAJES");
        panelCentral.add(crearPanelBuses(), "BUSES");

        add(panelCentral, BorderLayout.CENTER);

        cardLayout.show(panelCentral, "INICIO");
    }
    
    private JPanel crearPanelInicio() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // Título
        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(titulo, BorderLayout.NORTH);

        // Contenedor principal
        JPanel contenido = new JPanel(new GridLayout(2, 2, 15, 15));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contenido.setBackground(new Color(245, 245, 245));

        // Tarjetas
        contenido.add(crearTarjeta("Viajes Hoy", "0"));
        contenido.add(crearTarjeta("Buses Activos", "0"));
        contenido.add(crearTarjeta("Conductores Disponibles", "0"));
        contenido.add(crearProximosViajes());

        panel.add(contenido, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel crearPanelViajes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // MODELO TABLA
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Destino");
        modeloTabla.addColumn("Horario");
        modeloTabla.addColumn("Precio");

        tablaViajes = new JTable(modeloTabla);

        // ESTILO TABLA
        tablaViajes.setRowHeight(25);
        tablaViajes.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tablaViajes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaViajes.getTableHeader().setBackground(new Color(30, 30, 30));
        tablaViajes.getTableHeader().setForeground(Color.WHITE);

        tablaViajes.setSelectionBackground(new Color(100, 150, 255));
        tablaViajes.setGridColor(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(tablaViajes);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // CONTENEDOR (espaciado)
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(new Color(245, 245, 245));
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        contenedorTabla.add(scroll, BorderLayout.CENTER);

        panel.add(contenedorTabla, BorderLayout.CENTER);

        // BOTONES 
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        // IZQUIERDA (botones normales)
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        izquierda.setBackground(new Color(245, 245, 245));

        JButton btnFiltrar = crearBotonAccion("Ver por destino");
        JButton btnVer = crearBotonAccion("Ver Detalle");
        JButton btnEditar = crearBotonAccion("Editar");
        JButton btnEliminar = crearBotonAccion("Eliminar");

        izquierda.add(btnFiltrar);
        izquierda.add(btnVer);
        izquierda.add(btnEditar);
        izquierda.add(btnEliminar);

        // DERECHA (botón flotante)
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        derecha.setBackground(new Color(245, 245, 245));

        JButton btnAgregar = crearBotonFlotante("+");
        btnAgregar.setToolTipText("Agregar viaje");

        derecha.add(btnAgregar);

        // unir
        panelBotones.add(izquierda, BorderLayout.WEST);
        panelBotones.add(derecha, BorderLayout.EAST);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // TÍTULO
        JLabel titulo = new JLabel("Gestión de Viajes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(titulo, BorderLayout.NORTH);

        // CARGA DATOS
        cargarTabla();

        // EVENTOS
        btnAgregar.addActionListener(e -> agregarViaje());
        btnFiltrar.addActionListener(e -> filtrarPorDestino());
        btnVer.addActionListener(e -> verDetalle());
        btnEliminar.addActionListener(e -> eliminarViaje());
        btnEditar.addActionListener(e -> editarViaje());

        return panel;
    }

    private JPanel crearPanelBuses() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        modeloBuses = new DefaultTableModel();
        modeloBuses.addColumn("Patente");
        modeloBuses.addColumn("Capacidad");

        tablaBuses = new JTable(modeloBuses);

        // Estilo tabla
        tablaBuses.setRowHeight(25);
        tablaBuses.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tablaBuses.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaBuses.getTableHeader().setBackground(new Color(30, 30, 30));
        tablaBuses.getTableHeader().setForeground(Color.WHITE);

        tablaBuses.setSelectionBackground(new Color(100, 150, 255));
        tablaBuses.setSelectionForeground(Color.WHITE);
        tablaBuses.setGridColor(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(tablaBuses);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // Título
        JLabel titulo = new JLabel("Gestión de Autobuses");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(new Color(245, 245, 245));
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        contenedorTabla.add(scroll, BorderLayout.CENTER);

        panel.add(contenedorTabla, BorderLayout.CENTER);

        // BOTÓN FLOTANTE
        JPanel contenedorInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        contenedorInferior.setBackground(new Color(245, 245, 245));

        JButton btnAgregarBus = crearBotonFlotante("+");
        btnAgregarBus.setToolTipText("Agregar nuevo bus");

        contenedorInferior.add(btnAgregarBus);
        panel.add(contenedorInferior, BorderLayout.SOUTH);

        cargarBuses();

        btnAgregarBus.addActionListener(e -> agregarBus());

        return panel;
    }
    
    private JPanel crearPanelConductores() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Conductores");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);
    
        return panel;
    }
    
    private JPanel crearTarjeta(String titulo, String valor) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 22));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }
    
    private JPanel crearProximosViajes() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Próximos Viajes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextArea area = new JTextArea();
        area.setEditable(false);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        return panel;
    }

    // ================= MÉTODOS =================

    private void cargarInicio() {

    }
    
    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        for (Viaje v : empresa.getViajes()) {
            modeloTabla.addRow(new Object[]{
                    v.getId(),
                    v.getDestino(),
                    v.getHorario(),
                    v.getPrecioPasaje()
            });
        }
    }
    
    private void cargarBuses() {
        modeloBuses.setRowCount(0);

        for (Bus bus : empresa.getBuses().values()) {
            modeloBuses.addRow(new Object[]{
                    bus.getPatente(),
                    bus.getCapacidad()
            });
        }
    }

    private void cargarConductores() {
        panelCentral.add(crearPanelConductores(), "CONDUCTORES");
    }
    
    private void agregarViaje() {
        try {
            String idStr = JOptionPane.showInputDialog("ID:");
            String destino = JOptionPane.showInputDialog("Destino:");
            String horario = JOptionPane.showInputDialog("Horario:");
            String precioStr = JOptionPane.showInputDialog("Precio:");

            int id = Integer.parseInt(idStr);
            double precio = Double.parseDouble(precioStr);

            // necesitas un bus existente
            String patente = JOptionPane.showInputDialog("Patente Bus:");
            Bus bus = empresa.buscarBus(patente);

            if (bus == null) {
                JOptionPane.showMessageDialog(this, "Bus no encontrado");
                return;
            }

            empresa.agregarViaje(
                    new transporte.modelo.Viaje(id, destino, horario, 0, precio, bus)
            );

            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void agregarBus() {
        try {
            String patente = JOptionPane.showInputDialog("Patente:");
            String capStr = JOptionPane.showInputDialog("Capacidad:");

            int capacidad = Integer.parseInt(capStr);

            empresa.agregarBus(new Bus(patente, capacidad));

            cargarBuses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void eliminarViaje() {
        int fila = tablaViajes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            empresa.eliminarViaje(id);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Viaje eliminado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void editarViaje() {
        int fila = tablaViajes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            String nuevoDestino = JOptionPane.showInputDialog("Nuevo destino:");
            String costoStr = JOptionPane.showInputDialog("Nuevo costo:");
            String precioStr = JOptionPane.showInputDialog("Nuevo precio:");

            double costo = Double.parseDouble(costoStr);
            double precio = Double.parseDouble(precioStr);

            empresa.editarViaje(id, nuevoDestino, costo, precio);

            cargarTabla();

            JOptionPane.showMessageDialog(this, "Viaje editado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void filtrarPorDestino() {
        String destino = JOptionPane.showInputDialog("Ingrese destino:");

        modeloTabla.setRowCount(0);

        if (empresa.getViajesPorDestino().containsKey(destino)) {
            for (Viaje v : empresa.getViajesPorDestino().get(destino)) {
                modeloTabla.addRow(new Object[]{
                        v.getId(),
                        v.getDestino(),
                        v.getHorario(),
                        v.getPrecioPasaje()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay viajes a ese destino");
        }
    }
    
    private void verDetalle() {
        int fila = tablaViajes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un viaje");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            Viaje v = empresa.buscarViaje(id);

            String info =
                    "ID: " + v.getId() + "\n" +
                    "Destino: " + v.getDestino() + "\n" +
                    "Horario: " + v.getHorario() + "\n" +
                    "Precio: " + v.getPrecioPasaje();

            JOptionPane.showMessageDialog(this, info);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    // ================= BOTONES =================
    
    private JButton crearBotonFlotante(String texto) {

        JButton boton = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Color (hover o normal)
                if (getModel().isArmed()) {
                    g2.setColor(new Color(50, 50, 50)); // más claro al hacer click
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(70, 70, 70)); // hover
                } else {
                    g2.setColor(Color.BLACK); // normal
                }

                // círculo
                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
               
            }
        };

        boton.setPreferredSize(new Dimension(55, 55));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        boton.setForeground(Color.WHITE);

        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setOpaque(false);

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
    
    private JButton crearBotonAccion(String texto) {

        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);

        boton.setOpaque(true);
        boton.setBackground(Color.BLACK);
        boton.setForeground(Color.WHITE);

        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(50, 50, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.BLACK);
            }
        });

        return boton;
    }
    
}