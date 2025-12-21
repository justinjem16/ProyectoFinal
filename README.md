ProyectoFinal
Sistema de Cálculo de Nómina - Costa Rica
Sistema de gestión y cálculo automatizado de nóminas desarrollado en Java, siguiendo la legislación laboral costarricense. Diseñado para departamentos de Recursos Humanos.
Contenido

Características
Requisitos
Instalación
Cómo Usar
Novedades Versión 3.0
Configuración
Solución de Problemas

Características
Funcionalidades Principales
El sistema incluye todo lo necesario para gestionar la nómina de una empresa:

Gestión de Empleados: Registra, edita y elimina información de empleados
Fecha de Ingreso: Ahora puedes registrar cuándo empezó a trabajar cada empleado (NUEVO)
Cálculo Automático: El sistema calcula todas las deducciones y aportes según la ley costarricense
Validación Inteligente: No te deja generar nóminas para fechas anteriores al ingreso del empleado (NUEVO)
Reportes PDF:

Comprobantes individuales para cada empleado
Reportes patronales individuales
Reporte consolidado de toda la planilla en un solo documento (NUEVO)


Envío de Correos: Envía los comprobantes automáticamente por email
Login Seguro: Sistema de autenticación para controlar el acceso

Cálculos que Realiza
El sistema calcula automáticamente:
Deducciones del empleado:

CCSS - IVM: 4.17%
CCSS - SEM: 6.50%
Banco Popular: 1%
Impuesto sobre la Renta según tramos progresivos

Aportes patronales:

CCSS - IVM: 7.08%
CCSS - SEM: 10.59%
INA: 1.5%
Fondo de Capitalización Laboral: 3%
Asignaciones Familiares: 5%

Impuesto sobre la Renta
El impuesto se calcula por tramos según la legislación vigente:

De ₡0 a ₡941,000: Exento (0%)
De ₡941,001 a ₡1,405,000: 10%
De ₡1,405,001 a ₡2,108,000: 15%
De ₡2,108,001 a ₡4,215,000: 20%
Más de ₡4,215,000: 25%

Requisitos
Para usar el sistema necesitas:

Java JDK 8 o superior - El lenguaje en que está desarrollado
NetBeans IDE - Recomendamos la versión 12 o superior, aunque funciona con cualquier IDE Java
Apache Ant - Ya viene incluido con NetBeans

Librerías Incluidas
El proyecto ya incluye todas las librerías necesarias en la carpeta lib/:

itextpdf-5.5.13.jar para generar PDFs
mail.jar para enviar correos
activation.jar requerido por JavaMail
jcalendar-1.4.jar para el selector de fechas

Instalación
Paso 1: Descargar y Extraer
Descarga el archivo ZIP del proyecto y extráelo donde quieras tenerlo.
Paso 2: Abrir en NetBeans

Abre NetBeans
Ve a File → Open Project
Busca la carpeta del proyecto
Ábrela

Paso 3: Verificar Librerías
Las librerías deberían estar ya configuradas. Para verificar:

Click derecho en el proyecto → Properties
Ve a Libraries
En la pestaña Compile, deben aparecer las 4 librerías mencionadas arriba
Si falta alguna, agrégala con Add JAR/Folder desde la carpeta lib/

Paso 4: Ejecutar

Presiona F6 o click derecho → Run
Se abrirá la ventana de login
Usa las credenciales de prueba:

Usuario: rrhh
Contraseña: nominas123


Cómo Usar
Gestionar Empleados
Desde el menú principal selecciona "Gestión de Empleados".
Para agregar un empleado nuevo:

Click en "Agregar Empleado"
Llena todos los campos obligatorios
Selecciona la fecha de ingreso en el calendario (NUEVO)
Guarda

Los campos obligatorios son: cédula, nombre, primer apellido, email, salario, tipo de planilla, puesto y fecha de ingreso.
Generar Nómina Individual
Desde el menú principal selecciona "Generar Nómina".
Proceso:

Selecciona un empleado de la lista
Elige el tipo de planilla (debe coincidir con el del empleado)
Selecciona la fecha del período
Click en "Calcular Nómina"

El sistema calculará todo automáticamente y te mostrará el desglose completo.
Nota importante: Si intentas generar una nómina para una fecha anterior a cuando el empleado ingresó, el sistema te lo impedirá y te mostrará un mensaje explicando el problema.
Para planilla quincenal:

Si seleccionas del 1 al 15: genera la primera quincena
Si seleccionas del 16 al fin de mes: genera la segunda quincena

Para planilla mensual:

Cualquier día del mes genera el período mensual completo

Después de calcular puedes:

Generar el PDF para el empleado
Enviar el comprobante por correo
Ver el detalle completo de deducciones y aportes

Generar Reporte Consolidado (NUEVO)
Esta es la función más útil de la nueva versión. Te permite ver toda la planilla en un solo PDF.
Cómo funciona:

Ve a "Generar Nómina"
NO selecciones ningún empleado
Solo selecciona el período que quieres consultar
Click en "Generar PDF Patrono"

El sistema automáticamente:

Carga todos tus empleados
Calcula la nómina de cada uno
Excluye a los que no habían ingresado todavía en ese período
Te genera un PDF con toda la información consolidada

Qué incluye el reporte:
El PDF muestra una tabla con todos los empleados y sus datos:

Nombre completo
Salario bruto
Total de CCSS
INA
FCL
Asignaciones
Total por empleado

Al final incluye totales generales de todo, incluyendo el monto total que debes pagar ese mes.
El archivo se guarda como: Patronal_Mensual_[mes]_[año].pdf
Novedades Versión 3.0
Esta versión incluye tres mejoras que surgieron de usar el sistema en situaciones reales:
1. Campo de Fecha de Ingreso
Ahora puedes registrar cuándo cada empleado empezó a trabajar. Esto te sirve para:

Llevar control de antigüedad
Validar que las nóminas sean correctas
Tener la información completa del empleado

Cómo funciona:

Al registrar un empleado nuevo, seleccionas la fecha en un calendario
Si editas un empleado antiguo, puedes agregarle la fecha
Los empleados sin fecha siguen funcionando normal (para no romper nada)

2. Validación Automática de Fechas
El sistema ahora te avisa si intentas generar una nómina para una fecha anterior a cuando el empleado ingresó. Esto evita errores y mantiene todo consistente.
Si intentas hacerlo, te muestra un mensaje claro con ambas fechas para que veas cuál es el problema.
3. Reporte Patronal Consolidado
Esta fue probablemente la mejora más pedida. Antes tenías que generar un PDF por cada empleado y luego sumar todo manualmente. Ahora puedes generar un solo PDF con toda la planilla.
Ventajas:

Ves todo en un solo documento
No tienes que sumar nada manualmente
El total ya viene calculado
Es más fácil de archivar y presentar
Te dice cuántos empleados se procesaron

Sobre la exclusión automática:
Si generas un reporte de, por ejemplo, marzo 2024, el sistema automáticamente excluye a los empleados que ingresaron después de marzo. Esto te asegura que los cálculos sean correctos y no incluyas gente que no trabajaba todavía en ese período.
Diferencias entre Versión 2.0 y 3.0
Versión 2.0: Solo podías hacer reportes individuales, no había control de fechas de ingreso, y tenías que sumar todo manualmente.
Versión 3.0: Registras fecha de ingreso, el sistema valida las fechas automáticamente, y puedes generar reportes consolidados de toda la planilla con un solo click.
Configuración
Correo Electrónico
Para que funcione el envío de correos, necesitas configurar tu servidor SMTP en el archivo Constantes.java que está en src/Utilidades/.
Busca las líneas que dicen:

SMTP_HOST
SMTP_PORT
EMAIL_FROM
EMAIL_PASSWORD

Y cámbialas por los datos de tu servidor de correo.
Importante: Por seguridad, en un ambiente real deberías usar variables de entorno en lugar de poner las contraseñas directamente en el código.
Archivos de Datos
El sistema guarda toda la información en archivos de texto:
empleados.txt contiene los datos de cada empleado separados por comas. Ahora incluye la fecha de ingreso al final.
usuarios.txt contiene los usuarios que pueden acceder al sistema.
idControl.txt mantiene el control de los IDs para que no se repitan.
Modificar Porcentajes
Si cambia la legislación o necesitas ajustar algún porcentaje, todo está centralizado en el archivo Constantes.java dentro de src/Utilidades/.
Ahí puedes cambiar:

Los porcentajes de deducciones
Los porcentajes de aportes patronales
Los tramos del impuesto sobre la renta

Solución de Problemas
No encuentra la clase principal
Ve a las propiedades del proyecto, sección Run, y asegúrate que Main Class diga: Presentacion.FrmLogin
Error con las librerías
Si te da error de que no encuentra iText o JavaMail:

Verifica que las librerías estén en la carpeta lib/
Ve a propiedades del proyecto → Libraries
Agrega las que falten con "Add JAR/Folder"

No se genera el PDF
Puede ser por permisos. Verifica que tienes permisos de escritura en la carpeta del proyecto. También revisa la consola de NetBeans para ver el error específico.
Problemas con el correo
Revisa que la configuración del SMTP esté correcta en Constantes.java. Asegúrate que el puerto 465 esté disponible y que el usuario y contraseña del correo sean correctos.
No aparece el calendario
Si no te aparece el selector de fecha:

Verifica que jcalendar-1.4.jar esté en lib/
Agrégalo en las propiedades del proyecto si no está
Haz Clean and Build

La validación de fecha no funciona
Si no te valida las fechas, verifica que:

El empleado tenga fecha de ingreso registrada
Los empleados sin fecha no tienen validación (esto es normal)
Hayas actualizado correctamente los archivos modificados

El reporte consolidado no incluye a todos
Esto es normal. El sistema excluye automáticamente a los empleados que ingresaron después del período que seleccionaste. Fíjate en el mensaje que te dice cuántos fueron excluidos y por qué.
Los empleados sin fecha de ingreso siempre se incluyen para mantener compatibilidad.

Estructura del Proyecto
El proyecto está organizado en paquetes:
AccesoDatos: Manejo de archivos y control de IDs
Entidades: Modelos de datos (Empleado, Nomina, Usuario, Correo)
LogicaNegocio: Toda la lógica del sistema (cálculos, generación de PDFs, envío de correos)
Presentacion: Las ventanas del sistema (Login, Menú, Gestión de Empleados, Generación de Nómina)
Utilidades: Constantes del sistema y nombres de archivos
La carpeta lib/ contiene las librerías externas necesarias.

Recursos
Si necesitas más información sobre las librerías:

Documentación de iText PDF: itextpdf.com
JavaMail API: javaee.github.io/javamail
JCalendar: toedter.com/jcalendar
Legislación Laboral de Costa Rica: mtss.go.cr


Versión: 3.0
Última actualización: Diciembre 18 2024
Desarrollado por: Rachell Mora, Alejandro Moran, Justin Espinoza
Curso: Programación II

Historial
Versión 3.0 - Diciembre 18 2024
Agregamos tres cosas importantes: el campo de fecha de ingreso con un calendario visual, validación automática para que no puedas generar nóminas de fechas incorrectas, y el reporte consolidado que muestra toda la planilla en un solo PDF.
Versión 2.0 - Diciembre 16 2024
Primera versión funcional del sistema con gestión de empleados, cálculo de nóminas, generación de PDFs y envío de correos.
