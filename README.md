# 🗄️ SISTEMA DE GESTIÓN DE BASE DE DATOS

Sistema completo de gestión de base de datos desarrollado en Java con MySQL que implementa operaciones CRUD, vistas, procedimientos almacenados y consultas especiales para la administración de clientes, pedidos y ventas.

---

## 📋 TABLA DE CONTENIDOS

- [Características](#-características)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#️-configuración)
- [Estructura del Sistema](#-estructura-del-sistema)
- [Funcionalidades](#-funcionalidades)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [Arquitectura de Base de Datos](#-arquitectura-de-base-de-datos)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Contribuir](#-contribuir)
- [Licencia](#-licencia)

---

## ✨ CARACTERÍSTICAS

### 🔍 **Vistas de Base de Datos**
- Agrupación de clientes por ventas totales (GROUP BY)
- Relaciones entre pedidos, clientes y productos (JOIN)
- Ordenamiento de pedidos por fecha (ORDER BY)
- Combinación de clientes y proveedores (UNION)
- Reportes detallados de ventas y clientes

### ⚙️ Procedimientos Almacenados
- Cálculo automático de ventas por día
- Reportes detallados de ventas diarias
- Filtrado de clientes por trimestre
- Gestión de clientes vigentes
- Inserción automatizada de nuevos clientes

### 🔎 Consultas Especiales
- Listado de clientes con sus pedidos
- Filtrado de pedidos por estado (Pagado/Pendiente)
- Vista completa del historial de un cliente

### 📝 Operaciones CRUD Completas
- CREATE: Insertar nuevos clientes con ID autogenerado
- READ: Consultar lista completa de clientes
- UPDATE: Actualizar información de clientes existentes
- DELETE: Eliminar clientes con validaciones de integridad

---

## 🛠️ REQUISITOS PREVIOS

Antes de comenzar, asegúrate de tener instalado:

- Java JDK 8 o superior
- MySQL Server 5.7 o superior
- MySQL Connector/J (JDBC Driver)
- Un IDE como IntelliJ IDEA, Eclipse o NetBeans (opcional)

---

## 📥 INSTALACIÓN

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/sistema-gestion-bd.git
cd sistema-gestion-bd
```

### 2. Configurar MySQL

Ejecuta los siguientes comandos en MySQL:

```sql
CREATE DATABASE avanceproyfinal_uml;
USE avanceproyfinal_uml;

-- Crear las tablas necesarias
-- (Incluir aquí el script SQL de creación de tablas)
```

### 3. Agregar el Conector JDBC

Descarga el MySQL Connector/J desde: https://dev.mysql.com/downloads/connector/j/

Agrégalo a tu proyecto:
- IntelliJ: File → Project Structure → Libraries → Add
- Eclipse: Right-click proyecto → Build Path → Add External JARs
- NetBeans: Right-click Libraries → Add JAR/Folder

### 4. Compilar y Ejecutar

```bash
javac App.java
java App
```

---

## ⚙️ CONFIGURACIÓN

### Parámetros de Conexión

Edita las constantes en la clase `App.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/avanceproyfinal_uml";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña_aqui";
```

Parámetros:
- URL: Ruta de conexión a tu base de datos
- USER: Usuario de MySQL
- PASSWORD: Contraseña de MySQL

---

## 🏗️ ESTRUCTURA DEL SISTEMA

```
sistema-gestion-bd/
│
├── App.java                    # Clase principal
├── README.md                   # Este archivo
└── sql/
    ├── schema.sql             # Estructura de la base de datos
    ├── views.sql              # Definición de vistas
    └── procedures.sql         # Procedimientos almacenados
```

### Estructura de la Base de Datos

Tablas Principales:
- `clientes` - Información de clientes
- `pedidos` - Registro de pedidos
- `detalle_pedido` - Detalles de productos por pedido
- `productos` - Catálogo de productos
- `proveedores` - Información de proveedores

---

## 🎯 FUNCIONALIDADES

### 📊 VISTAS DE BASE DE DATOS

#### 1. Vista GroupBy - Clientes por Total de Ventas
```sql
SELECT * FROM `1.groupby`
```
**Descripción:** Muestra cada cliente con el total acumulado de sus ventas.

**Salida:**
```
Cliente                  | Total Ventas
----------------------------------------------
Juan Pérez               | $15,450.50
María García             | $12,300.00
```

---

#### 2. Vista Join - Pedidos con Clientes y Productos
```sql
SELECT * FROM `1.join` LIMIT 20
```
**Descripción:** Combina información de pedidos, clientes y productos en una sola vista.

**Salida:**
```
ID_Pedido | Cliente        | ID_Producto | Cantidad | Precio    | Subtotal  | Fecha
---------------------------------------------------------------------------------------
PED001    | Juan Pérez     | PROD001     | 2        | $500.00   | $1,000.00 | 2025-10-28
```

---

#### 3. Vista OrderBy - Pedidos Ordenados por Fecha
```sql
SELECT * FROM `1.orderby` LIMIT 20
```
**Descripción:** Lista de pedidos ordenados cronológicamente.

---

#### 4. Vista Union - Clientes y Proveedores
```sql
SELECT * FROM `1.union`
```
**Descripción:** Combina la lista de clientes y proveedores en una sola consulta.

---

#### 5. Vista Reporte de Ventas y Clientes
```sql
SELECT * FROM vista_reporte_ventas_clientes LIMIT 20
```
**Descripción:** Reporte consolidado con ID de cliente, nombre, fecha de venta y monto total.

---

### ⚡ PROCEDIMIENTOS ALMACENADOS

#### 1. Calcular_Ventas_Dia
```sql
CALL Calcular_Ventas_Dia('2025-10-28')
```
**Descripción:** Calcula el total de ventas de un día específico y muestra el desglose de pedidos.

**Entrada:** Fecha en formato YYYY-MM-DD

**Salida:**
- Total de ventas del día
- Desglose de cada pedido (ID, Cliente, Monto)

---

#### 2. CalVentasDiariasConFecha
```sql
CALL CalVentasDiariasConFecha('2025-10-28')
```
**Descripción:** Muestra el detalle completo de ventas por día incluyendo productos y cantidades.

---

#### 3. Reporte_Clientes_1er_Trimestre
```sql
CALL Reporte_Clientes_1er_Trimestre()
```
**Descripción:** Lista clientes que realizaron pedidos en el primer trimestre (Octubre-Diciembre).

---

#### 4. sp_reporte_clientes_vigentes_q4
```sql
CALL sp_reporte_clientes_vigentes_q4()
```
**Descripción:** Reporta clientes con pedidos vigentes (Pagado o Pendiente) en el cuarto trimestre.

---

#### 5. Agregar_Nuevo_Cliente
```sql
CALL Agregar_Nuevo_Cliente('Juan Pérez', 'juan@email.com')
```
**Descripción:** Inserta un nuevo cliente en la base de datos mediante procedimiento almacenado.

**Parámetros:**
- Nombre del cliente
- Correo electrónico

---

### 🔍 CONSULTAS ESPECIALES

#### 1. Clientes con sus Pedidos
**Descripción:** Muestra todos los clientes con estadísticas de sus pedidos.

**Información mostrada:**
- ID del cliente
- Nombre completo
- Email
- Total de pedidos realizados
- Monto total gastado

---

#### 2. Pedidos por Estado
**Descripción:** Filtra pedidos según su estado de pago.

**Opciones:**
1. Ver todos los pedidos
2. Ver solo pedidos PAGADOS (✓)
3. Ver solo pedidos PENDIENTES (⏳)

**Incluye resumen con:**
- Cantidad de pedidos por estado
- Monto total por estado
- Total general

---

#### 3. Detalle Completo de un Cliente
**Descripción:** Vista completa del perfil de un cliente específico.

**Información mostrada:**
- Datos personales (Nombre, Email, Teléfono, Dirección)
- Historial completo de pedidos
- Estadísticas (Total pedidos, Total gastado, Promedio)

---

### 📝 OPERACIONES CRUD

#### CREATE - Insertar Cliente
**Función:** `realizarCRUD_InsertarCliente()`

**Datos requeridos:**
- Nombre del cliente
- Apellido paterno
- Correo electrónico
- Teléfono (10 dígitos)
- Dirección

**Características:**
- ✅ Generación automática de ID único
- ✅ Validación de datos
- ✅ Confirmación de inserción exitosa

---

#### READ - Consultar Clientes
**Función:** `realizarCRUD_LeerClientes()`

**Muestra:**
- ID del cliente
- Nombre completo
- Correo electrónico
- Lista ordenada alfabéticamente

---

#### UPDATE - Actualizar Cliente
**Función:** `realizarCRUD_ActualizarCliente()`

**Permite modificar:**
- Nombre
- Apellido paterno
- Correo electrónico
- Teléfono
- Dirección

**Características:**
- ✅ Validación de existencia del cliente
- ✅ Confirmación de actualización

---

#### DELETE - Eliminar Cliente
**Función:** `realizarCRUD_EliminarCliente()`

**Características:**
- ✅ Validación de existencia del cliente
- ✅ Verificación de integridad referencial
- ⚠️ Prevención de eliminación si tiene pedidos asociados
- 💡 Sugerencias alternativas si no se puede eliminar

**Validaciones:**
1. Verifica si el cliente existe
2. Verifica si tiene pedidos asociados
3. Muestra mensaje de error descriptivo si no se puede eliminar
4. Sugiere opciones alternativas

---

## 💡 EJEMPLOS DE USO

### Ejemplo 1: Consultar Ventas de un Día Específico

```java
// Ejecutar desde el menú opción 6
// Ingresa: 2025-10-28
// Resultado: Total de ventas y desglose por pedido
```

### Ejemplo 2: Agregar un Nuevo Cliente

```java
// Ejecutar desde el menú opción 14
// Ingresa los datos:
Nombre: Carlos
Apellido: Ramírez
Email: carlos@email.com
Teléfono: 8123456789
Dirección: Calle Principal #123

// Resultado: Cliente insertado con ID autogenerado
```

### Ejemplo 3: Ver Pedidos Pendientes

```java
// Ejecutar desde el menú opción 12
// Selecciona: 3 (Pedidos Pendientes)
// Resultado: Lista de todos los pedidos con estado "Pendiente"
```

---

## 🗃️ ARQUITECTURA DE BASE DE DATOS

### Modelo Relacional

```
CLIENTES (1) ----< (N) PEDIDOS (1) ----< (N) DETALLE_PEDIDO (N) >---- (1) PRODUCTOS
```

### Campos Principales

**Tabla: clientes**
```sql
- Id_Cliente (PK)
- Nombre_Cliente
- Ap_Paterno
- Email_Cliente
- Telefono_Cliente
- Direccion_Cliente
```

**Tabla: pedidos**
```sql
- Id_Pedido (PK)
- Id_Cliente (FK)
- Fecha_Pedido
- Total_Pedido
- Estado_Pedido (Pagado/Pendiente)
```

**Tabla: detalle_pedido**
```sql
- Id_Detalle (PK)
- Id_Pedido (FK)
- Id_Producto (FK)
- Cantidad
- Precio_Unitario
- Subtotal
```

---

## 🛡️ MANEJO DE ERRORES

El sistema incluye manejo robusto de errores:

### Errores de Conexión
```java
catch (SQLException e) {
    System.err.println("Error de conexión: " + e.getMessage());
}
```

### Validaciones de Integridad
- ✅ Verificación de claves foráneas
- ✅ Validación de datos de entrada
- ✅ Mensajes descriptivos de error
- ✅ Sugerencias de solución

---

## 🔧 TECNOLOGÍAS UTILIZADAS

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 8+ | Lenguaje principal |
| MySQL | 5.7+ | Base de datos |
| JDBC | 8.0+ | Conector MySQL |
| Scanner | Nativa | Entrada de usuario |

---

## 📈 CARACTERÍSTICAS TÉCNICAS

### Conexiones
- ✅ Connection pooling implícito
- ✅ Try-with-resources para auto-cierre
- ✅ Manejo de múltiples ResultSets

### Seguridad
- ✅ PreparedStatements (prevención de SQL Injection)
- ✅ Validación de entrada de usuario
- ✅ Manejo seguro de excepciones

### Rendimiento
- ✅ LIMIT en consultas grandes
- ✅ Índices en claves primarias
- ✅ Consultas optimizadas con JOINs

---

## 🤝 CONTRIBUIR

¡Las contribuciones son bienvenidas!

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/NuevaCaracteristica`)
3. Commit tus cambios (`git commit -m 'Agregar nueva característica'`)
4. Push a la rama (`git push origin feature/NuevaCaracteristica`)
5. Abre un Pull Request

---

## 📞 SOPORTE

Si encuentras algún problema o tienes preguntas:

- 📧 Email: soporte@ejemplo.com
- 🐛 Issues: [GitHub Issues](https://github.com/tu-usuario/sistema-gestion-bd/issues)
- 📖 Wiki: [Documentación completa](https://github.com/tu-usuario/sistema-gestion-bd/wiki)

---

## 📄 LICENCIA

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.

---

## 👥 AUTORES

- **Cesar** - *Desarrollo y diseño de base de datos*
- **Daniel** - *Desarrollo y implementación de funcionalidades*

---

## 📊 ESTADÍSTICAS DEL PROYECTO

- **Líneas de código:** ~800
- **Funciones:** 17+
- **Vistas de BD:** 5
- **Procedimientos:** 5
- **Consultas especiales:** 3

---
