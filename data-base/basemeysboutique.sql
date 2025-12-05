-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-12-2025 a las 01:52:25
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `basemeysboutique`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablacategoria`
--

CREATE TABLE `tablacategoria` (
  `codigoCategoria` int(11) NOT NULL,
  `nombreCategoria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablacategoria`
--

INSERT INTO `tablacategoria` (`codigoCategoria`, `nombreCategoria`) VALUES
(1, 'Ropa'),
(2, 'Calzado'),
(3, 'Accesorios'),
(4, 'Tecnología');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablacliente`
--

CREATE TABLE `tablacliente` (
  `codigoCliente` int(11) NOT NULL,
  `nombreCliente` varchar(100) NOT NULL,
  `apellidoCliente` varchar(100) NOT NULL,
  `DUI` varchar(20) NOT NULL,
  `telefonoCliente` varchar(20) NOT NULL,
  `codigoMunicipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablacliente`
--

INSERT INTO `tablacliente` (`codigoCliente`, `nombreCliente`, `apellidoCliente`, `DUI`, `telefonoCliente`, `codigoMunicipio`) VALUES
(1, 'Carla', 'Mendoza', '01234567-8', '71234567', 1),
(3, 'Andrea', 'Lopez', '92929929-1', '9456-9192', 1),
(4, 'Sofia', 'Ramirez', '55667433-2', '3243-4545', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablacompra`
--

CREATE TABLE `tablacompra` (
  `codigoCompra` int(11) NOT NULL,
  `fechaVenta` date NOT NULL,
  `codigoUsuario` int(11) NOT NULL,
  `codigoTransaccion` int(11) NOT NULL,
  `codigoProducto` int(11) NOT NULL,
  `codigoProveedor` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablacompra`
--

INSERT INTO `tablacompra` (`codigoCompra`, `fechaVenta`, `codigoUsuario`, `codigoTransaccion`, `codigoProducto`, `codigoProveedor`, `cantidad`, `precio`, `total`) VALUES
(2, '2025-12-04', 1, 2, 8, 3, 50, 29.00, 1450.00),
(3, '2025-12-04', 1, 2, 9, 4, 50, 14.00, 700.00),
(4, '2025-12-04', 1, 2, 10, 4, 30, 27.00, 810.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablamunicipio`
--

CREATE TABLE `tablamunicipio` (
  `codigoMunicipio` int(11) NOT NULL,
  `nombreMunicipio` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablamunicipio`
--

INSERT INTO `tablamunicipio` (`codigoMunicipio`, `nombreMunicipio`) VALUES
(1, 'San Salvador'),
(2, 'Soyapango'),
(3, 'Santa Tecla'),
(4, 'Ilobasco'),
(5, 'San Vicente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablaproducto`
--

CREATE TABLE `tablaproducto` (
  `codigoProducto` int(11) NOT NULL,
  `nombreProducto` varchar(100) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `codigoCategoria` int(11) NOT NULL,
  `codigoProveedor` int(11) NOT NULL,
  `stock` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablaproducto`
--

INSERT INTO `tablaproducto` (`codigoProducto`, `nombreProducto`, `descripcion`, `precio`, `codigoCategoria`, `codigoProveedor`, `stock`) VALUES
(8, 'Pantaln Jeans Classic', 'Jeans azul clsico resistente y cmodo', 24.00, 1, 3, 50),
(9, 'Sudadera Hoodie Gris', 'Sudadera con capucha color gris', 14.00, 1, 4, 50),
(10, 'Zapatos Casual Unisex', 'Zapatos cmodos para uso diario', 27.00, 1, 3, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablaproveedor`
--

CREATE TABLE `tablaproveedor` (
  `codigoProveedor` int(11) NOT NULL,
  `nombreProveedor` varchar(100) NOT NULL,
  `nombreEncargado` varchar(100) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `codigoMunicipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablaproveedor`
--

INSERT INTO `tablaproveedor` (`codigoProveedor`, `nombreProveedor`, `nombreEncargado`, `telefono`, `codigoMunicipio`) VALUES
(3, 'Comercial La Central', 'Juan Perez', '2225-7890', 2),
(4, 'Distribuidora El Sol', 'Arturo Castillo', '2260-1122', 3),
(5, 'Suministros Martinez', 'Carlos Martinez', '2278-5634', 5),
(6, 'GlobalTec SA', 'Andres Cabrera', '2299-1120', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablasesionusuario`
--

CREATE TABLE `tablasesionusuario` (
  `codigoSesion` int(11) NOT NULL,
  `codigoUsuario` int(11) NOT NULL,
  `fechaInicio` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablasesionusuario`
--

INSERT INTO `tablasesionusuario` (`codigoSesion`, `codigoUsuario`, `fechaInicio`) VALUES
(3, 2, '2025-12-04 16:58:19');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablatransaccion`
--

CREATE TABLE `tablatransaccion` (
  `codigoTransaccion` int(11) NOT NULL,
  `tipoTransaccion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablatransaccion`
--

INSERT INTO `tablatransaccion` (`codigoTransaccion`, `tipoTransaccion`) VALUES
(1, 'Efectivo'),
(2, 'Tarjeta'),
(3, 'Transferencia'),
(4, 'Crédito');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablausuario`
--

CREATE TABLE `tablausuario` (
  `codigoUsuario` int(11) NOT NULL,
  `primerNombre` varchar(100) NOT NULL,
  `segundoNombre` varchar(50) NOT NULL,
  `tercerNombre` varchar(50) DEFAULT NULL,
  `primerApellido` varchar(100) NOT NULL,
  `segundoApellido` varchar(50) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `apellidoCasado` varchar(50) DEFAULT NULL,
  `telefono` varchar(20) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  `codigoMunicipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablausuario`
--

INSERT INTO `tablausuario` (`codigoUsuario`, `primerNombre`, `segundoNombre`, `tercerNombre`, `primerApellido`, `segundoApellido`, `fechaNacimiento`, `apellidoCasado`, `telefono`, `correo`, `contraseña`, `codigoMunicipio`) VALUES
(1, 'Admin', '', NULL, 'Admin', '', '2000-01-01', NULL, '00000000', 'Admin', 'admin123', 1),
(2, 'Elvis', 'ReyLuis', '', 'Gutierrez', 'Hernandez', '2002-02-13', '', '6048-9898', 'elvisreyluis@gmail.com', '123456', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablaventa`
--

CREATE TABLE `tablaventa` (
  `codigoVenta` int(11) NOT NULL,
  `fechaVenta` date NOT NULL,
  `codigoCliente` int(11) NOT NULL,
  `codigoUsuario` int(11) NOT NULL,
  `codigoTransaccion` int(11) NOT NULL,
  `codigoProducto` int(11) NOT NULL,
  `cantidadProducto` int(11) NOT NULL,
  `totalVenta` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablaventa`
--

INSERT INTO `tablaventa` (`codigoVenta`, `fechaVenta`, `codigoCliente`, `codigoUsuario`, `codigoTransaccion`, `codigoProducto`, `cantidadProducto`, `totalVenta`) VALUES
(3, '2025-12-04', 1, 1, 1, 10, 1, 27.00),
(4, '2025-12-04', 3, 1, 1, 9, 1, 14.00),
(5, '2025-12-04', 4, 1, 2, 8, 2, 4998.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tablacategoria`
--
ALTER TABLE `tablacategoria`
  ADD PRIMARY KEY (`codigoCategoria`);

--
-- Indices de la tabla `tablacliente`
--
ALTER TABLE `tablacliente`
  ADD PRIMARY KEY (`codigoCliente`),
  ADD UNIQUE KEY `DUI` (`DUI`),
  ADD KEY `codigoMunicipio` (`codigoMunicipio`);

--
-- Indices de la tabla `tablacompra`
--
ALTER TABLE `tablacompra`
  ADD PRIMARY KEY (`codigoCompra`),
  ADD KEY `codigoUsuario` (`codigoUsuario`),
  ADD KEY `codigoTransaccion` (`codigoTransaccion`),
  ADD KEY `codigoProducto` (`codigoProducto`),
  ADD KEY `codigoProveedor` (`codigoProveedor`);

--
-- Indices de la tabla `tablamunicipio`
--
ALTER TABLE `tablamunicipio`
  ADD PRIMARY KEY (`codigoMunicipio`);

--
-- Indices de la tabla `tablaproducto`
--
ALTER TABLE `tablaproducto`
  ADD PRIMARY KEY (`codigoProducto`),
  ADD KEY `codigoCategoria` (`codigoCategoria`),
  ADD KEY `codigoProveedor` (`codigoProveedor`);

--
-- Indices de la tabla `tablaproveedor`
--
ALTER TABLE `tablaproveedor`
  ADD PRIMARY KEY (`codigoProveedor`),
  ADD KEY `codigoMunicipio` (`codigoMunicipio`);

--
-- Indices de la tabla `tablasesionusuario`
--
ALTER TABLE `tablasesionusuario`
  ADD PRIMARY KEY (`codigoSesion`);

--
-- Indices de la tabla `tablatransaccion`
--
ALTER TABLE `tablatransaccion`
  ADD PRIMARY KEY (`codigoTransaccion`);

--
-- Indices de la tabla `tablausuario`
--
ALTER TABLE `tablausuario`
  ADD PRIMARY KEY (`codigoUsuario`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `tablaventa`
--
ALTER TABLE `tablaventa`
  ADD PRIMARY KEY (`codigoVenta`),
  ADD KEY `codigoCliente` (`codigoCliente`),
  ADD KEY `codigoUsuario` (`codigoUsuario`),
  ADD KEY `codigoTransaccion` (`codigoTransaccion`),
  ADD KEY `codigoProducto` (`codigoProducto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tablacategoria`
--
ALTER TABLE `tablacategoria`
  MODIFY `codigoCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tablacliente`
--
ALTER TABLE `tablacliente`
  MODIFY `codigoCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tablacompra`
--
ALTER TABLE `tablacompra`
  MODIFY `codigoCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tablamunicipio`
--
ALTER TABLE `tablamunicipio`
  MODIFY `codigoMunicipio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `tablaproducto`
--
ALTER TABLE `tablaproducto`
  MODIFY `codigoProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tablaproveedor`
--
ALTER TABLE `tablaproveedor`
  MODIFY `codigoProveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `tablasesionusuario`
--
ALTER TABLE `tablasesionusuario`
  MODIFY `codigoSesion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tablatransaccion`
--
ALTER TABLE `tablatransaccion`
  MODIFY `codigoTransaccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tablausuario`
--
ALTER TABLE `tablausuario`
  MODIFY `codigoUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tablaventa`
--
ALTER TABLE `tablaventa`
  MODIFY `codigoVenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tablacliente`
--
ALTER TABLE `tablacliente`
  ADD CONSTRAINT `tablacliente_ibfk_1` FOREIGN KEY (`codigoMunicipio`) REFERENCES `tablamunicipio` (`codigoMunicipio`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `tablacompra`
--
ALTER TABLE `tablacompra`
  ADD CONSTRAINT `tablacompra_ibfk_1` FOREIGN KEY (`codigoUsuario`) REFERENCES `tablausuario` (`codigoUsuario`),
  ADD CONSTRAINT `tablacompra_ibfk_2` FOREIGN KEY (`codigoTransaccion`) REFERENCES `tablatransaccion` (`codigoTransaccion`),
  ADD CONSTRAINT `tablacompra_ibfk_3` FOREIGN KEY (`codigoProducto`) REFERENCES `tablaproducto` (`codigoProducto`),
  ADD CONSTRAINT `tablacompra_ibfk_4` FOREIGN KEY (`codigoProveedor`) REFERENCES `tablaproveedor` (`codigoProveedor`);

--
-- Filtros para la tabla `tablaproducto`
--
ALTER TABLE `tablaproducto`
  ADD CONSTRAINT `tablaproducto_ibfk_1` FOREIGN KEY (`codigoCategoria`) REFERENCES `tablacategoria` (`codigoCategoria`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tablaproducto_ibfk_2` FOREIGN KEY (`codigoProveedor`) REFERENCES `tablaproveedor` (`codigoProveedor`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `tablaproveedor`
--
ALTER TABLE `tablaproveedor`
  ADD CONSTRAINT `tablaproveedor_ibfk_1` FOREIGN KEY (`codigoMunicipio`) REFERENCES `tablamunicipio` (`codigoMunicipio`);

--
-- Filtros para la tabla `tablaventa`
--
ALTER TABLE `tablaventa`
  ADD CONSTRAINT `tablaventa_ibfk_1` FOREIGN KEY (`codigoCliente`) REFERENCES `tablacliente` (`codigoCliente`),
  ADD CONSTRAINT `tablaventa_ibfk_2` FOREIGN KEY (`codigoUsuario`) REFERENCES `tablausuario` (`codigoUsuario`),
  ADD CONSTRAINT `tablaventa_ibfk_3` FOREIGN KEY (`codigoTransaccion`) REFERENCES `tablatransaccion` (`codigoTransaccion`),
  ADD CONSTRAINT `tablaventa_ibfk_4` FOREIGN KEY (`codigoProducto`) REFERENCES `tablaproducto` (`codigoProducto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
