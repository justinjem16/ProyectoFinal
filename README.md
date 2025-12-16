# ProyectoFinal



\# Sistema de Cálculo de Nómina - Costa Rica



Sistema de gestión y cálculo automatizado de nóminas desarrollado en Java, siguiendo la legislación laboral costarricense. Diseñado para departamentos de Recursos Humanos.



\## Tabla de Contenidos



\- \[Características](#-características)

\- \[Requisitos del Sistema](#-requisitos-del-sistema)

\- \[Instalación](#-instalación)

\- \[Uso del Sistema](#-uso-del-sistema)

\- \[Estructura del Proyecto](#-estructura-del-proyecto)

\- \[Configuración](#-configuración)

\- \[Cálculos de Nómina](#-cálculos-de-nómina)

\- \[Guía de Edición](#-guía-de-edición)

\- \[Solución de Problemas](#-solución-de-problemas)

\- \[Contribuciones](#-contribuciones)

\- \[Licencia](#-licencia)



\## Características



\### Funcionalidades Principales



\- \*\*Gestión de Empleados\*\*: CRUD completo de información de empleados

\- \*\*Cálculo Automático de Nómina\*\*: Deducciones y aportes según ley costarricense

\- \*\*Generación de Reportes PDF\*\*: Comprobantes para empleados y reportes patronales

\- \*\*Envío de Correos\*\*: Envío automático de comprobantes por email

\- \*\*Sistema de Autenticación\*\*: Login de usuarios con validación

\- \*\*Persistencia en Archivos\*\*: Almacenamiento en archivos de texto



\### Cálculos Implementados



\*\*Deducciones del Empleado:\*\*

\- CCSS - IVM: 4.17%

\- CCSS - SEM: 6.50%

\- Banco Popular: 1%

\- Impuesto sobre la Renta (por tramos)



\*\*Aportes Patronales:\*\*

\- CCSS - IVM: 7.08%

\- CCSS - SEM: 10.59%

\- INA: 1.5%

\- Fondo de Capitalización Laboral: 3%

\- Asignaciones Familiares: 5%



\## Requisitos del Sistema



\### Software Necesario



\- \*\*Java JDK\*\*: Versión 8 o superior

\- \*\*NetBeans IDE\*\*: Versión 12 o superior (recomendado) o cualquier IDE Java

\- \*\*Apache Ant\*\*: Incluido en NetBeans (para compilar y construir el proyecto)

\- \*\*Librerías Externas\*\*: Las 4 librerías JAR necesarias están incluidas en el proyecto:

&nbsp; - `itextpdf-5.5.13.jar` - Para generación de PDFs

&nbsp; - `mail.jar` - Para envío de correos electrónicos

&nbsp; - `activation.jar` - Requerido por JavaMail

&nbsp; - `jcalendar-1.4.jar` - Para el selector de fechas (DateChooser)



\## Instalación



\### Paso 1: Descargar el Proyecto



1\. Descarga el archivo ZIP del proyecto

2\. Extrae el contenido en la ubicación deseada



\### Paso 2: Abrir el Proyecto en NetBeans



1\. Abre NetBeans IDE

2\. Ve a \*\*File → Open Project\*\*

3\. Navega hasta la carpeta donde extrajiste el proyecto

4\. Selecciona la carpeta del proyecto

5\. Click en \*\*Open Project\*\*



\### Paso 3: Verificar las Librerías



Las librerías ya están incluidas en el proyecto dentro de la carpeta `lib/`. 



Para verificar que estén correctamente configuradas:



1\. Click derecho en el proyecto → \*\*Properties\*\*

2\. Ve a \*\*Libraries\*\* en el panel izquierdo

3\. En la pestaña \*\*Compile\*\*, verifica que aparezcan las 4 librerías:

&nbsp;  - `itextpdf-5.5.13.jar`

&nbsp;  - `mail.jar`

&nbsp;  - `activation.jar`

&nbsp;  - `jcalendar-1.4.jar`

4\. Si alguna no aparece, click en \*\*Add JAR/Folder\*\* y agrégala desde la carpeta `lib/`

5\. Click en \*\*OK\*\*



\### Paso 4: Limpiar y Construir el Proyecto



1\. Click derecho en el proyecto

2\. Selecciona \*\*Clean and Build\*\* (o presiona Shift + F11)

3\. Verifica que no haya errores de compilación

4\. Si todo está bien, verás: \*\*BUILD SUCCESSFUL\*\*



\### Paso 5: Ejecutar el Proyecto



1\. Presiona \*\*F6\*\* o click derecho en el proyecto → \*\*Run\*\*

2\. Se abrirá la ventana de Login

3\. Ingresa las credenciales:

&nbsp;  - Usuario: `rrhh`

&nbsp;  - Contraseña: `nominas123`

4\. ¡Listo! Ya puedes usar el sistema



\## Uso del Sistema



\### Iniciar el Sistema



1\. \*\*Compilar el Proyecto\*\* (si aún no lo has hecho):

&nbsp;  - En NetBeans: Click derecho en el proyecto → \*\*Clean and Build\*\*

&nbsp;  - O presiona `Shift + F11`



2\. \*\*Ejecutar el Proyecto\*\*:

&nbsp;  - En NetBeans: Click derecho en el proyecto → \*\*Run\*\*

&nbsp;  - O presiona `F6`

&nbsp;  - Clase principal: `Presentacion.FrmLogin`



3\. \*\*Iniciar Sesión\*\*:

&nbsp;  - Usuario: `rrhh`

&nbsp;  - Contraseña: `nominass123`



\### Flujo de Trabajo Típico



\#### 1. Gestionar Empleados



```

Login → Menú Principal → Gestión de Empleados

```



\*\*Agregar Empleado:\*\*

\- Click en "Agregar Empleado"

\- Completa los campos obligatorios:

&nbsp; - Cédula (formato: X-XXXX-XXXX)

&nbsp; - Nombre y apellidos

&nbsp; - Email

&nbsp; - Teléfono

&nbsp; - Salario bruto

&nbsp; - Tipo de planilla (Quincenal/Mensual)

&nbsp; - Puesto

\- Click en "Guardar"



\*\*Actualizar Tabla:\*\*

\- Click en "Actualizar" para refrescar la lista



\#### 2. Generar Nómina



```

Login → Menú Principal → Generar Nómina

```



\*\*Proceso:\*\*

1\. Selecciona un empleado de la lista

2\. \*\*Selecciona el tipo de planilla\*\* (Quincenal/Mensual)

&nbsp;  - Debe coincidir con el tipo de planilla del empleado

3\. \*\*Selecciona la fecha del período\*\* usando el DateChooser

&nbsp;  - Para \*\*planilla quincenal\*\*:

&nbsp;    - Fechas del 1 al 15 → Genera período Q1 (Primera quincena)

&nbsp;    - Fechas del 16 al fin de mes → Genera período Q2 (Segunda quincena)

&nbsp;    - Ejemplo: Si seleccionas 10/12/2024 → Período: `2024-12-Q1`

&nbsp;  - Para \*\*planilla mensual\*\*:

&nbsp;    - Cualquier día del mes genera el período mensual

&nbsp;    - Ejemplo: Si seleccionas 15/12/2024 → Período: `2024-12-M`

4\. Click en "Calcular Nómina"

5\. El sistema calculará automáticamente:

&nbsp;  - Deducciones CCSS

&nbsp;  - Banco Popular

&nbsp;  - Impuesto sobre la Renta

&nbsp;  - Aportes patronales

5\. Se generarán 2 PDFs con nombres que incluyen el período:

&nbsp;  - `Nomina\_\[cedula]\_\[periodo].pdf` - Para el empleado

&nbsp;    - Ejemplo: `Nomina\_1-2345-6789\_2024-12-Q1.pdf`

&nbsp;  - `Patronal\_\[cedula]\_\[periodo].pdf` - Para la empresa

&nbsp;    - Ejemplo: `Patronal\_1-2345-6789\_2024-12-M.pdf`



\*\*Enviar por Correo (Opcional):\*\*

\- Click en "Enviar por Correo"

\- Los PDFs se adjuntarán automáticamente al email del empleado



\## Estructura del Proyecto



```

SistemaNomina/

│

├── src/

│   ├── AccesoDatos/

│   │   ├── AccesoDatos.java      # Manejo de archivos

│   │   └── IdControl.java        # Control de IDs

│   │

│   ├── Entidades/

│   │   ├── Empleado.java         # Modelo de empleado

│   │   ├── Nomina.java           # Modelo de nómina

│   │   ├── Usuario.java          # Modelo de usuario

│   │   └── Correo.java           # Modelo de correo

│   │

│   ├── LogicaNegocio/

│   │   ├── LogicaBase.java       # Clase base abstracta

│   │   ├── LogicaEmpleado.java   # Lógica de empleados

│   │   ├── LogicaUsuario.java    # Lógica de usuarios

│   │   ├── LogicaCorreo.java     # Lógica de correos

│   │   ├── CalculadoraNomina.java # Cálculos de nómina

│   │   └── GeneradorPDF.java     # Generación de PDFs

│   │

│   ├── Presentacion/

│   │   ├── FrmLogin.java         # Ventana de login

│   │   ├── FrmMenuPrincipal.java # Menú principal

│   │   ├── FrmGestionEmpleados.java # Gestión de empleados

│   │   ├── FrmEmpleado.java      # Formulario de empleado

│   │   └── FrmGenerarNomina.java # Generación de nómina

│   │

│   └── Utilidades/

│       ├── Constantes.java       # Constantes del sistema

│       └── NombresArchivos.java  # Enum de archivos

│

├── lib/                          # Librerías externas (incluidas)

│   ├── itextpdf-5.5.13.jar      # Generación de PDFs

│   ├── mail.jar                 # Envío de correos

│   ├── activation.jar           # Requerido por JavaMail

│   └── jcalendar-1.4.jar        # Selector de fechas

│

├── build/                        # Carpeta de compilación (generada por Ant)

├── dist/                         # Carpeta de distribución (generada por Ant)

├── nbproject/                    # Configuración de NetBeans

├── build.xml                     # Script de construcción Ant

├── manifest.mf                   # Archivo manifest

│

├── empleados.txt                 # Base de datos de empleados (incluida)

├── usuarios.txt                  # Base de datos de usuarios (incluida)

├── idControl.txt                 # Control de IDs (incluido)

└── README.md                     # Este archivo

```



---



\*\*Desarrollado por:\*\* Rachell Mora, Alejandro Moran, Justin Espinoza  

\*\*Curso:\*\* Programación II 

\*\*Fecha:\*\* Diciembre 2025

