import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.Scanner;

public class App {

    // --- Parámetros de Conexión ---
    private static final String URL = "jdbc:mysql://localhost:3306/avanceproyfinal_uml";
    private static final String USER = "root";
    private static final String PASSWORD = "11111111";
    // -----------------------------

    public static void main(String[] args) {
        // Cargar el driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC de MySQL.");
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conexión a la base de datos establecida.");
            
            App appInstance = new App();
            
            int opcion;
            do {
                mostrarMenu();
                System.out.print("Seleccione una opción: ");
                
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    switch (opcion) {
                        case 1:
                            appInstance.ejecutarVista_GroupBy(conn);
                            break;
                        case 2:
                            appInstance.ejecutarVista_Join(conn);
                            break;
                        case 3:
                            appInstance.ejecutarVista_OrderBy(conn);
                            break;
                        case 4:
                            appInstance.ejecutarVista_Union(conn);
                            break;
                        case 5:
                            appInstance.ejecutarVista_Reporte_Ventas_Clientes(conn);
                            break;
                        case 6:
                            appInstance.ejecutarProcedimiento_CalcularVentasDia(conn, scanner);
                            break;
                        case 7:
                            appInstance.ejecutarProcedimiento_CalVentasDiariasConFecha(conn, scanner);
                            break;
                        case 8:
                            appInstance.ejecutarProcedimiento_ReporteClientes1erTrimestre(conn);
                            break;
                        case 9:
                            appInstance.ejecutarProcedimiento_ReporteClientesVigentesQ4(conn);
                            break;
                        case 10:
                            appInstance.ejecutarProcedimiento_AgregarNuevoCliente(conn, scanner);
                            break;
                        case 11:
                            appInstance.consultarClientesConPedidos(conn);
                            break;
                        case 12:
                            appInstance.consultarPedidosPorEstado(conn, scanner);
                            break;
                        case 13:
                            appInstance.consultarDetalleCompletoCliente(conn, scanner);
                            break;
                        case 14:
                            appInstance.realizarCRUD_InsertarCliente(conn, scanner);
                            break;
                        case 15:
                            appInstance.realizarCRUD_LeerClientes(conn);
                            break;
                        case 16:
                            appInstance.realizarCRUD_ActualizarCliente(conn, scanner);
                            break;
                        case 17:
                            appInstance.realizarCRUD_EliminarCliente(conn, scanner);
                            break;
                        case 0:
                            System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                } else {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.next(); 
                    opcion = -1;
                }
            } while (opcion != 0);

        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          SISTEMA DE GESTIÓN DE BASE DE DATOS              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n--- VISTAS DE BASE DE DATOS ---");
        System.out.println("1. Vista: GroupBy - Clientes por Total de Ventas");
        System.out.println("2. Vista: Join - Pedidos con Clientes y Productos");
        System.out.println("3. Vista: OrderBy - Pedidos Ordenados por Fecha");
        System.out.println("4. Vista: Union - Clientes y Proveedores");
        System.out.println("5. Vista: Reporte de Ventas y Clientes");
        System.out.println("\n--- PROCEDIMIENTOS ALMACENADOS ---");
        System.out.println("6. Procedimiento: Calcular Ventas por Día");
        System.out.println("7. Procedimiento: Ventas Diarias con Fecha (Detallado)");
        System.out.println("8. Procedimiento: Reporte Clientes 1er Trimestre (Oct-Dic)");
        System.out.println("9. Procedimiento: Reporte Clientes Vigentes Q4");
        System.out.println("10. Procedimiento: Agregar Nuevo Cliente");
        System.out.println("\n--- CONSULTAS ESPECIALES ---");
        System.out.println("11. Consultar: Clientes con sus Pedidos");
        System.out.println("12. Consultar: Pedidos por Estado (Pagado/Pendiente)");
        System.out.println("13. Consultar: Detalle Completo de un Cliente");
        System.out.println("\n--- OPERACIONES CRUD ---");
        System.out.println("14. CRUD: Crear/Insertar un nuevo cliente");
        System.out.println("15. CRUD: Leer/Consultar todos los clientes");
        System.out.println("16. CRUD: Actualizar datos de un cliente");
        System.out.println("17. CRUD: Eliminar un cliente");
        System.out.println("\n0. Salir");
        System.out.println("════════════════════════════════════════════════════════════");
    }

    // ====================================================================
    // --- VISTAS ---
    // ====================================================================

    // Vista 1: GroupBy
    public void ejecutarVista_GroupBy(Connection conn) {
        String sql = "SELECT * FROM `1.groupby`";
        System.out.println("\n--- Vista 1: GroupBy - Clientes por Total de Ventas ---");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Cliente                  | Total Ventas");
            System.out.println("----------------------------------------------");

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-24s | $%-11.2f%n",
                    rs.getString("Cliente"),
                    rs.getDouble("Total_Ventas")
                );
            }
            
            if (!hayResultados) {
                System.out.println("No hay datos disponibles.");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la vista: " + e.getMessage());
        }
    }

    // Vista 2: Join
    public void ejecutarVista_Join(Connection conn) {
        String sql = "SELECT * FROM `1.join` LIMIT 20";
        System.out.println("\n--- Vista 2: Join - Pedidos con Clientes y Productos ---");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ID_Pedido | Cliente              | ID_Producto | Cantidad | Precio     | Subtotal   | Fecha");
            System.out.println("---------------------------------------------------------------------------------------------------");

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-9s | %-20s | %-11s | %-8d | $%-9.2f | $%-9.2f | %s%n",
                    rs.getString("Id_Pedido"),
                    rs.getString("Cliente"),
                    rs.getString("Id_Producto"),
                    rs.getInt("Cantidad"),
                    rs.getDouble("Precio_Unitario"),
                    rs.getDouble("Subtotal"),
                    rs.getDate("Fecha").toString()
                );
            }
            
            if (!hayResultados) {
                System.out.println("No hay datos disponibles.");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la vista: " + e.getMessage());
        }
    }

    // Vista 3: OrderBy
    public void ejecutarVista_OrderBy(Connection conn) {
        String sql = "SELECT * FROM `1.orderby` LIMIT 20";
        System.out.println("\n--- Vista 3: OrderBy - Pedidos Ordenados por Fecha ---");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ID_Pedido | ID_Cliente | Fecha Pedido | Total Pedido");
            System.out.println("------------------------------------------------------------");

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-9s | %-10s | %-12s | $%-11.2f%n",
                    rs.getString("Id_Pedido"),
                    rs.getString("Id_Cliente"),
                    rs.getDate("Fecha_Pedido").toString(),
                    rs.getDouble("Total_Pedido")
                );
            }
            
            if (!hayResultados) {
                System.out.println("No hay datos disponibles.");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la vista: " + e.getMessage());
        }
    }

    // Vista 4: Union
    public void ejecutarVista_Union(Connection conn) {
        String sql = "SELECT * FROM `1.union`";
        System.out.println("\n--- Vista 4: Union - Clientes y Proveedores ---");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Nombre                   | Tipo");
            System.out.println("--------------------------------------------");

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-24s | %s%n",
                    rs.getString("Nombre"),
                    rs.getString("Tipo")
                );
            }
            
            if (!hayResultados) {
                System.out.println("No hay datos disponibles.");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la vista: " + e.getMessage());
        }
    }

    // Vista 5: Reporte de Ventas y Clientes
    public void ejecutarVista_Reporte_Ventas_Clientes(Connection conn) {
        String sql = "SELECT * FROM vista_reporte_ventas_clientes LIMIT 20";
        System.out.println("\n--- Vista 5: Reporte de Ventas y Clientes ---");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ID_Cliente | Nombre Cliente           | Fecha Venta  | Monto Total");
            System.out.println("-----------------------------------------------------------------------");

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-10s | %-24s | %-12s | $%-11.2f%n",
                    rs.getString("id_cliente"),
                    rs.getString("nombre_cliente"),
                    rs.getDate("fecha_venta").toString(),
                    rs.getDouble("monto_total")
                );
            }
            
            if (!hayResultados) {
                System.out.println("No hay datos disponibles.");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la vista: " + e.getMessage());
        }
    }

    // ====================================================================
    // --- PROCEDIMIENTOS ALMACENADOS ---
    // ====================================================================

    // Procedimiento 1: Calcular_Ventas_Dia
    public void ejecutarProcedimiento_CalcularVentasDia(Connection conn, Scanner scanner) {
        System.out.println("\n--- Procedimiento: Calcular_Ventas_Dia ---");
        System.out.print("Ingrese la fecha (YYYY-MM-DD, ej: 2025-10-28): ");
        String fechaStr = scanner.nextLine();
        
        String sql = "{CALL Calcular_Ventas_Dia(?)}"; 
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, fechaStr);
            cstmt.execute();

            // Primer ResultSet: Total de ventas
            try (ResultSet rsTotal = cstmt.getResultSet()) {
                if (rsTotal != null && rsTotal.next()) {
                    double totalVentas = rsTotal.getDouble(1); 
                    System.out.printf("Total de Ventas para el %s: $%.2f%n", fechaStr, totalVentas);
                } else {
                    System.out.println("No se obtuvieron ventas para esa fecha.");
                }
            }

            // Segundo ResultSet: Desglose
            if (cstmt.getMoreResults()) {
                try (ResultSet rsDesglose = cstmt.getResultSet()) {
                    if (rsDesglose != null) {
                        System.out.println("\nDesglose de Pedidos:");
                        System.out.println("ID_Venta  | ID_Cliente | Monto Total | Fecha Venta");
                        System.out.println("--------------------------------------------------------");
                        boolean hayDesglose = false;
                        while (rsDesglose.next()) {
                            hayDesglose = true;
                            System.out.printf("%-9s | %-10s | $%-10.2f | %-12s%n",
                                rsDesglose.getString("id_venta"),
                                rsDesglose.getString("id_cliente"),
                                rsDesglose.getDouble("monto_total"),
                                rsDesglose.getDate("fecha_venta").toString()
                            );
                        }
                        if (!hayDesglose) {
                            System.out.println("Sin detalles de pedidos.");
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        }
    }

    // Procedimiento 2: CalVentasDiariasConFecha
    public void ejecutarProcedimiento_CalVentasDiariasConFecha(Connection conn, Scanner scanner) {
        System.out.println("\n--- Procedimiento: Ventas Diarias con Fecha ---");
        System.out.print("Ingrese la fecha (YYYY-MM-DD, ej: 2025-10-28): ");
        String fechaStr = scanner.nextLine();
        
        String sql = "{CALL CalVentasDiariasConFecha(?)}"; 
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, fechaStr);
            cstmt.execute();

            try (ResultSet rs = cstmt.getResultSet()) {
                if (rs != null) {
                    System.out.println("\nDetalle de Ventas:");
                    System.out.println("ID_Pedido | Cliente              | Producto             | Cantidad | Subtotal   | Fecha");
                    System.out.println("--------------------------------------------------------------------------------------------");
                    boolean hayResultados = false;
                    while (rs.next()) {
                        hayResultados = true;
                        System.out.printf("%-9s | %-20s | %-20s | %-8d | $%-9.2f | %s%n",
                            rs.getString("Id_Pedido"),
                            rs.getString("Cliente"),
                            rs.getString("Producto"),
                            rs.getInt("Cantidad"),
                            rs.getDouble("Subtotal"),
                            rs.getDate("Fecha").toString()
                        );
                    }
                    if (!hayResultados) {
                        System.out.println("No se encontraron ventas para esa fecha.");
                    }

                    // Obtener el total del segundo ResultSet
                    if (cstmt.getMoreResults()) {
                        try (ResultSet rsTotal = cstmt.getResultSet()) {
                            if (rsTotal != null && rsTotal.next()) {
                                System.out.printf("\nTotal del Día: $%.2f%n", rsTotal.getDouble(1));
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        }
    }

    // Procedimiento 3: Reporte_Clientes_1er_Trimestre
    public void ejecutarProcedimiento_ReporteClientes1erTrimestre(Connection conn) {
        System.out.println("\n--- Procedimiento: Reporte Clientes 1er Trimestre (Oct-Dic) ---");
        
        String sql = "{CALL Reporte_Clientes_1er_Trimestre()}";
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.execute();

            try (ResultSet rs = cstmt.getResultSet()) {
                if (rs != null) {
                    System.out.println("\nClientes con Pedidos en Oct-Dic:");
                    System.out.println("ID_Cliente | Nombre Cliente           | Correo Electrónico");
                    System.out.println("--------------------------------------------------------------------");
                    boolean hayResultados = false;
                    while (rs.next()) {
                        hayResultados = true;
                        System.out.printf("%-10s | %-24s | %s%n",
                            rs.getString("id_cliente"),
                            rs.getString("nombre_cliente"),
                            rs.getString("correo_electronico")
                        );
                    }
                    if (!hayResultados) {
                        System.out.println("No se encontraron clientes con pedidos en ese trimestre.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        }
    }

    // Procedimiento 4: sp_reporte_clientes_vigentes_q4
    public void ejecutarProcedimiento_ReporteClientesVigentesQ4(Connection conn) {
        System.out.println("\n--- Procedimiento: Reporte Clientes Vigentes Q4 ---");
        
        String sql = "{CALL sp_reporte_clientes_vigentes_q4()}";
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.execute();

            try (ResultSet rs = cstmt.getResultSet()) {
                if (rs != null) {
                    System.out.println("\nClientes Vigentes Q4 (Pagado/Pendiente):");
                    System.out.println("ID_Cliente | Nombre Cliente           | Ap_Paterno");
                    System.out.println("--------------------------------------------------------------------");
                    boolean hayResultados = false;
                    while (rs.next()) {
                        hayResultados = true;
                        System.out.printf("%-10s | %-24s | %s%n",
                            rs.getString("Id_Cliente"),
                            rs.getString("Nombre_Cliente"),
                            rs.getString("Ap_Paterno")
                        );
                    }
                    if (!hayResultados) {
                        System.out.println("No se encontraron clientes vigentes.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        }
    }
    
    // Procedimiento 5: Agregar_Nuevo_Cliente
    public void ejecutarProcedimiento_AgregarNuevoCliente(Connection conn, Scanner scanner) {
        System.out.println("\n--- Procedimiento: Agregar_Nuevo_Cliente ---");
        System.out.print("Ingrese el nombre del nuevo cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String email = scanner.nextLine();

        String sql = "{CALL Agregar_Nuevo_Cliente(?, ?)}"; 
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, nombre);
            cstmt.setString(2, email);
            
            cstmt.execute();
            
            try (ResultSet rs = cstmt.getResultSet()) {
                if (rs != null && rs.next()) {
                    System.out.println("Resultado: " + rs.getString(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    // ====================================================================
    // --- CONSULTAS ESPECIALES ---
    // ====================================================================

    // Consulta 1: Clientes con sus Pedidos
    public void consultarClientesConPedidos(Connection conn) {
        System.out.println("\n--- Consulta: Clientes con sus Pedidos ---");
        String sql = "SELECT c.Id_Cliente, c.Nombre_Cliente, c.Ap_Paterno, c.Email_Cliente, " +
                     "COUNT(p.Id_Pedido) as Total_Pedidos, " +
                     "COALESCE(SUM(p.Total_Pedido), 0) as Total_Gastado " +
                     "FROM clientes c " +
                     "LEFT JOIN pedidos p ON c.Id_Cliente = p.Id_Cliente " +
                     "GROUP BY c.Id_Cliente, c.Nombre_Cliente, c.Ap_Paterno, c.Email_Cliente " +
                     "ORDER BY Total_Pedidos DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ID Cliente | Nombre Completo              | Email                        | Total Pedidos | Total Gastado");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                String nombreCompleto = rs.getString("Nombre_Cliente") + " " + rs.getString("Ap_Paterno");
                System.out.printf("%-10s | %-28s | %-28s | %-13d | $%-12.2f%n", 
                    rs.getString("Id_Cliente"),
                    nombreCompleto,
                    rs.getString("Email_Cliente"),
                    rs.getInt("Total_Pedidos"),
                    rs.getDouble("Total_Gastado")
                );
            }
            if (!found) {
                System.out.println("No se encontraron clientes.");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar clientes con pedidos: " + e.getMessage());
        }
    }

    // Consulta 2: Pedidos por Estado (Pagado/Pendiente)
    public void consultarPedidosPorEstado(Connection conn, Scanner scanner) {
        System.out.println("\n--- Consulta: Pedidos por Estado ---");
        System.out.println("1. Ver todos los pedidos");
        System.out.println("2. Ver solo pedidos PAGADOS");
        System.out.println("3. Ver solo pedidos PENDIENTES");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        String filtroEstado = "";
        if (opcion == 2) {
            filtroEstado = " WHERE p.Estado_Pedido = 'Pagado'";
        } else if (opcion == 3) {
            filtroEstado = " WHERE p.Estado_Pedido = 'Pendiente'";
        }
        
        String sql = "SELECT p.Id_Pedido, " +
                     "CONCAT(c.Nombre_Cliente, ' ', c.Ap_Paterno) as Cliente, " +
                     "p.Fecha_Pedido, p.Total_Pedido, p.Estado_Pedido " +
                     "FROM pedidos p " +
                     "INNER JOIN clientes c ON p.Id_Cliente = c.Id_Cliente" +
                     filtroEstado +
                     " ORDER BY p.Fecha_Pedido DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID Pedido | Cliente                      | Fecha Pedido | Total Pedido | Estado");
            System.out.println("-------------------------------------------------------------------------------------------");
            
            boolean found = false;
            int totalPagados = 0;
            int totalPendientes = 0;
            double montoPagado = 0;
            double montoPendiente = 0;
            
            while (rs.next()) {
                found = true;
                String estado = rs.getString("Estado_Pedido");
                String simbolo = estado.equals("Pagado") ? "✓" : "⏳";
                
                System.out.printf("%-9s | %-28s | %-12s | $%-11.2f | %s %s%n",
                    rs.getString("Id_Pedido"),
                    rs.getString("Cliente"),
                    rs.getDate("Fecha_Pedido").toString(),
                    rs.getDouble("Total_Pedido"),
                    simbolo,
                    estado
                );
                
                if (estado.equals("Pagado")) {
                    totalPagados++;
                    montoPagado += rs.getDouble("Total_Pedido");
                } else {
                    totalPendientes++;
                    montoPendiente += rs.getDouble("Total_Pedido");
                }
            }
            
            if (!found) {
                System.out.println("No se encontraron pedidos.");
            } else {
                System.out.println("\n--- RESUMEN ---");
                System.out.printf("✓ Pedidos PAGADOS: %d (Total: $%.2f)%n", totalPagados, montoPagado);
                System.out.printf("⏳ Pedidos PENDIENTES: %d (Total: $%.2f)%n", totalPendientes, montoPendiente);
                System.out.printf("TOTAL GENERAL: %d pedidos ($%.2f)%n", totalPagados + totalPendientes, montoPagado + montoPendiente);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar pedidos: " + e.getMessage());
        }
    }

    // Consulta 3: Detalle Completo de un Cliente
    public void consultarDetalleCompletoCliente(Connection conn, Scanner scanner) {
        System.out.println("\n--- Consulta: Detalle Completo de un Cliente ---");
        System.out.print("Ingrese el ID del Cliente: ");
        String idCliente = scanner.nextLine();
        
        // Información del cliente
        String sqlCliente = "SELECT * FROM clientes WHERE Id_Cliente = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCliente)) {
            pstmt.setString(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                System.out.println("║              INFORMACIÓN DEL CLIENTE                       ║");
                System.out.println("╚════════════════════════════════════════════════════════════╝");
                System.out.println("ID: " + rs.getString("Id_Cliente"));
                System.out.println("Nombre: " + rs.getString("Nombre_Cliente") + " " + rs.getString("Ap_Paterno"));
                System.out.println("Email: " + rs.getString("Email_Cliente"));
                System.out.println("Teléfono: " + rs.getString("Telefono_Cliente"));
                System.out.println("Dirección: " + rs.getString("Direccion_Cliente"));
                
                // Pedidos del cliente
                String sqlPedidos = "SELECT p.Id_Pedido, p.Fecha_Pedido, p.Total_Pedido, p.Estado_Pedido, " +
                                   "COUNT(dp.Id_Detalle) as Total_Productos " +
                                   "FROM pedidos p " +
                                   "LEFT JOIN detalle_pedido dp ON p.Id_Pedido = dp.Id_Pedido " +
                                   "WHERE p.Id_Cliente = ? " +
                                   "GROUP BY p.Id_Pedido, p.Fecha_Pedido, p.Total_Pedido, p.Estado_Pedido " +
                                   "ORDER BY p.Fecha_Pedido DESC";
                
                try (PreparedStatement pstmtPedidos = conn.prepareStatement(sqlPedidos)) {
                    pstmtPedidos.setString(1, idCliente);
                    ResultSet rsPedidos = pstmtPedidos.executeQuery();
                    
                    System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                    System.out.println("║                 HISTORIAL DE PEDIDOS                       ║");
                    System.out.println("╚════════════════════════════════════════════════════════════╝");
                    System.out.println("ID Pedido | Fecha        | Total        | Productos | Estado");
                    System.out.println("----------------------------------------------------------------");
                    
                    int totalPedidos = 0;
                    double totalGastado = 0;
                    
                    while (rsPedidos.next()) {
                        totalPedidos++;
                        double total = rsPedidos.getDouble("Total_Pedido");
                        totalGastado += total;
                        
                        String estado = rsPedidos.getString("Estado_Pedido");
                        String simbolo = estado.equals("Pagado") ? "✓" : "⏳";
                        
                        System.out.printf("%-9s | %-12s | $%-10.2f | %-9d | %s %s%n",
                            rsPedidos.getString("Id_Pedido"),
                            rsPedidos.getDate("Fecha_Pedido").toString(),
                            total,
                            rsPedidos.getInt("Total_Productos"),
                            simbolo,
                            estado
                        );
                    }
                    
                    if (totalPedidos == 0) {
                        System.out.println("Este cliente no tiene pedidos registrados.");
                    } else {
                        System.out.println("\n--- RESUMEN DEL CLIENTE ---");
                        System.out.printf("Total de Pedidos: %d%n", totalPedidos);
                        System.out.printf("Total Gastado: $%.2f%n", totalGastado);
                        System.out.printf("Promedio por Pedido: $%.2f%n", totalGastado / totalPedidos);
                    }
                }
                
            } else {
                System.out.println("✗ No se encontró ningún cliente con ID: " + idCliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar detalle del cliente: " + e.getMessage());
        }
    }

    // ====================================================================
    // --- OPERACIONES CRUD ---
    // ====================================================================

    // CRUD: Crear (Insertar)
    public void realizarCRUD_InsertarCliente(Connection conn, Scanner scanner) {
        System.out.println("\n--- CRUD: Insertar Nuevo Cliente ---");
        System.out.print("Nombre del Cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido Paterno: ");
        String apellido = scanner.nextLine();
        System.out.print("Correo Electrónico: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono (10 dígitos): ");
        String telefono = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        // Generar el nuevo ID
        String sqlMaxId = "SELECT CONCAT('CLIEN', LPAD(CAST(SUBSTRING(MAX(Id_Cliente), 6) AS UNSIGNED) + 1, 4, '0')) AS nuevo_id FROM clientes";
        String nuevoId = null;
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlMaxId)) {
            if (rs.next()) {
                nuevoId = rs.getString("nuevo_id");
            }
        } catch (SQLException e) {
            System.err.println("Error al generar ID: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO clientes (Id_Cliente, Nombre_Cliente, Ap_Paterno, Email_Cliente, Telefono_Cliente, Direccion_Cliente) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoId);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, email);
            pstmt.setString(5, telefono);
            pstmt.setString(6, direccion);
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✓ Cliente insertado exitosamente con ID: " + nuevoId);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    // CRUD: Leer (Consultar)
    public void realizarCRUD_LeerClientes(Connection conn) {
        System.out.println("\n--- CRUD: Lista de Clientes ---");
        String sql = "SELECT Id_Cliente, Nombre_Cliente, Email_Cliente FROM clientes ORDER BY Id_Cliente";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ID Cliente | Nombre Cliente           | Correo Electrónico");
            System.out.println("--------------------------------------------------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10s | %-24s | %s%n", 
                    rs.getString("Id_Cliente"),
                    rs.getString("Nombre_Cliente"),
                    rs.getString("Email_Cliente")
                );
            }
            if (!found) {
                System.out.println("No se encontraron clientes.");
            }
        } catch (SQLException e) {
            System.err.println("Error al leer clientes: " + e.getMessage());
        }
    }

    // CRUD: Actualizar
    public void realizarCRUD_ActualizarCliente(Connection conn, Scanner scanner) {
        System.out.println("\n--- CRUD: Actualizar Cliente ---");
        System.out.print("ID del Cliente a actualizar: ");
        String id = scanner.nextLine();

        System.out.print("Nuevo Nombre del Cliente: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo Apellido Paterno: ");
        String nuevoApellido = scanner.nextLine();
        System.out.print("Nuevo Correo Electrónico: ");
        String nuevoEmail = scanner.nextLine();
        System.out.print("Nuevo Teléfono: ");
        String nuevoTelefono = scanner.nextLine();
        System.out.print("Nueva Dirección: ");
        String nuevaDireccion = scanner.nextLine();

        String sql = "UPDATE clientes SET Nombre_Cliente = ?, Ap_Paterno = ?, Email_Cliente = ?, Telefono_Cliente = ?, Direccion_Cliente = ? WHERE Id_Cliente = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoApellido);
            pstmt.setString(3, nuevoEmail);
            pstmt.setString(4, nuevoTelefono);
            pstmt.setString(5, nuevaDireccion);
            pstmt.setString(6, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✓ Cliente con ID " + id + " actualizado exitosamente.");
            } else {
                System.out.println("✗ No se encontró ningún cliente con ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    // CRUD: Eliminar - VERSIÓN CORREGIDA
    public void realizarCRUD_EliminarCliente(Connection conn, Scanner scanner) {
        System.out.println("\n--- CRUD: Eliminar Cliente ---");
        System.out.print("ID del Cliente a eliminar: ");
        String id = scanner.nextLine();

        try {
            // Verificar si el cliente existe
            String sqlVerificarCliente = "SELECT COUNT(*) as total FROM clientes WHERE Id_Cliente = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVerificarCliente)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt("total") == 0) {
                    System.out.println("✗ No se encontró ningún cliente con ID " + id);
                    return;
                }
            }

            // Verificar si tiene pedidos
            String sqlVerificarPedidos = "SELECT COUNT(*) as total FROM pedidos WHERE Id_Cliente = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVerificarPedidos)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt("total") > 0) {
                    int numPedidos = rs.getInt("total");
                    System.out.println("⚠️  ERROR: Este cliente tiene " + numPedidos + " pedido(s) asociado(s).");
                    System.out.println("⚠️  No se puede eliminar un cliente con pedidos activos por restricción de integridad referencial.");
                    System.out.println("\nOpciones sugeridas:");
                    System.out.println("  1. Eliminar primero los pedidos del cliente manualmente");
                    System.out.println("  2. Implementar una eliminación en cascada en la base de datos");
                    System.out.println("  3. Usar un campo 'estado' para marcar clientes como inactivos");
                    return;
                }
            }

            // Si no tiene pedidos, eliminar el cliente
            String sql = "DELETE FROM clientes WHERE Id_Cliente = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id);
                
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("✓ Cliente con ID " + id + " eliminado exitosamente.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            if (e.getMessage().contains("foreign key constraint")) {
                System.err.println("Detalle: El cliente tiene relaciones con otras tablas que impiden su eliminación.");
            }
        }
    }
}