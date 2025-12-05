# Mey's Boutique

## ¿Qué es Mey’s Boutique?  
Mey’s Boutique es una aplicación de escritorio desarrollada en Java (JavaFX + MySQL) que permite gestionar una tienda de ropa — incluyendo compras, proveedores, transacciones, productos, clientes y usuarios.  
La aplicación facilita registrar compras, actualizar stock, manejar transacciones, proveedores y reportes, ideal para pequeñas y medianas tiendas.

![Java](https://img.shields.io/badge/Java-17-orange?logo=oracle)
![JavaFX](https://img.shields.io/badge/JavaFX-Framework-blue?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-0468d7?logo=apachemaven)
![Git](https://img.shields.io/badge/Git-Control%20de%20versiones-f14e32?logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-Repo-000000?logo=github)


## Funcionalidades principales  
- Control de usuarios y sesión.
  <img width="923" height="622" alt="register" src="https://github.com/user-attachments/assets/8f42afbd-f7b0-4e9c-a581-75af09a6a38b" />
  <img width="463" height="705" alt="login" src="https://github.com/user-attachments/assets/d68fa924-8beb-4905-a2cd-88e16b42a4a0" />

- Interfaz gráfica amigable (JavaFX).
  <img width="1205" height="701" alt="inicio" src="https://github.com/user-attachments/assets/24f706ea-0a86-4941-bd18-2639b5653007" />
  
- Registro, actualización y eliminación de compras.
    <img width="1201" height="704" alt="sales" src="https://github.com/user-attachments/assets/5d2df851-e7df-4db9-a5a5-a581fd6e1652" />

- Gestión de productos, proveedores, usuarios y transacciones.
  <img width="1199" height="705" alt="Products" src="https://github.com/user-attachments/assets/e86002eb-a5fc-451d-a9d9-844e732d14e7" />
  <img width="1202" height="703" alt="providers" src="https://github.com/user-attachments/assets/e2df637d-f57d-4d10-9ca9-4e9a1639c019" />

- Registro de ventas.
  <img width="1201" height="704" alt="sales" src="https://github.com/user-attachments/assets/cf609219-2ac1-4083-ac07-3af1a8cbbcf4" />


## Requisitos

- Java 17+.  
- JDK instalado.  
- MySQL (o cualquier base compatible) con las tablas necesarias creadas: `tablaCompra`, `tablaProducto`, `tablaProveedor`, `tablaUsuario`, `tablaTransaccion`, etc.  
- Conexión configurada en `DatabaseUtil`.
- IDE compatible con Java IntelliJ, Eclipse, VS Code + extensión Java, etc.  

## Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/ElvisGutierrez/MeysBoutique.git
cd MeysBoutique

# 2. Configurar la base de datos
#    - Crear base de datos en MySQL
#    - Crear las tablas necesarias con sus estructuras (productos, proveedor, transacciones, compras, usuarios, etc.)
#    - Ajustar los datos de conexión en DatabaseUtil.java (host, usuario, contraseña, nombre BD)

# 3. Importar el proyecto en tu IDE

# 4. Ejecutar la aplicación

# 5. Ejecutar la clase principal en tu IDE
