CREATE DATABASE IF NOT EXISTS  desarrolloSocial;
USE desarrolloSocial;

CREATE TABLE IF NOT EXISTS `instituciones` (
  `idInstitucion` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombreInstitucion` VARCHAR(200) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `telefono` VARCHAR(8) NOT NULL,
  `acercaDe` TEXT,
  `nombreRepresentante` VARCHAR(100) NOT NULL)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;


CREATE TABLE IF NOT EXISTS  `comunidades` (
`idComunidad` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
`nombreComunidad` VARCHAR(100)  NOT NULL,
`municipio` VARCHAR(50) NOT NULL,
`ubicacion` POINT NOT NULL)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;


CREATE TABLE IF NOT EXISTS `colaboradores` (
  `dui` INT(9) NOT NULL PRIMARY KEY,
  `nombresColaborador` VARCHAR(100) NOT NULL,
  `apellidosColaborador` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(8) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `idInstitucion` INT(10) NOT NULL,
  CONSTRAINT `idInstitucion_lbk` FOREIGN KEY (`idInstitucion`) REFERENCES `desarrolloSocial`.`instituciones` (`idInstitucion`) ON UPDATE RESTRICT ON DELETE RESTRICT
  )
	ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;
	

CREATE TABLE IF NOT EXISTS `proyectos` (
  `idProyecto` INT(15) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombreProyecto` VARCHAR(100) NOT NULL,
  `descripcion` TEXT NOT NULL,
  `fechaPropuesta` DATETIME NOT NULL,
  `fechaEjecutado` DATETIME NOT NULL,
  `idComunidad` INT(10) NOT NULL,
  `idInstitucion` INT(10) NOT NULL,
  CONSTRAINT `proyecto_lbk1` FOREIGN KEY (`idInstitucion`) REFERENCES `desarrolloSocial`.`instituciones` (`idInstitucion`) ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `proyecto_lbk2` FOREIGN KEY (`idComunidad`) REFERENCES `desarrolloSocial`.`comunidades` (`idComunidad`) ON UPDATE RESTRICT ON DELETE RESTRICT
  )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;


CREATE TABLE `login` (
  `id_usuario` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nombre_usuario` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_nloginivel` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `estatus` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `registrado_por` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=UTF8_UNICODE_CI;
