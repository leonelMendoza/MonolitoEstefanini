<b> ¿Cómo está organizado el Monolito? </b>

1) Tiene la estructura traicional EJB. Con su módulo Dao para la persitencia  y la capaintermedia del service.
2) Por falta de  tiempo he creado un componente util/DBConnection para simular una conexión a Mysql.
3) He creado un socker, que desde la terminal o donde sea llamado reciba los parametros se maneja con un CASE. El socket es inicializado desde el main.
4) He utilizado para ahorrar tiempo y por gusto personal la librería lombok para instanciar objetos y crear setter y getters.
5) Suelo manejar dos tipos de DTO's request y response para separar la capa de negocio y la  capa persistencia.
6) Es un proyecto Mavenizado, si bien no está pensando como EAR o un WAR. Más como un API o un servicio expuesto.
7) Para la parte de concurrencia he generado un SimuladorUsuario.java, donded su utilización seincluye en el SocketServer.
8) Mis pruebas locales las di por exitosas cuando alcancé la capa de persitencia (DaoImpl). Ya que no tengo montado un servicio de BDD.

<b> ¿cómo manejan los hilos? </b>

De esta parte fue la que más dudas tuve, he decidido usar ExecutorService dentro del SimuladorUsuarios.java, 
forzando la llamada a un sleep de 5 segundos para realizar otro llamado al Simulador y verificar que no se este bloqueando.
De esto, repito he tenio varias dudas sobre el ejercicio y cómo montarlo.

<b> ¿Cómo funciona el SocketServer? </b>

Una vez que el SocketServer está en funcionamiento, puede recibir el formato esperado seperado por ";"
se separan los argumentos  y se invoca a la capa de servicio según el CASE correspondiente. Es cómo el menu de operaciones
principal.

<b> ¿Qué problemas evitó? </b>
Creo que bien orientado hacía el SimuladorUsuarios, puede evitar los deadlock en grandes cargas, concentrar en una clase los hilos puede ser más legible. Por otro lado
la estructural del proyecto crea capas independientes y lo puede hacer escalable. Igualmente el uso de Maven, aunque está pensando para java 8 (compílado en 21), puede ser
escalable a versiones recientes. La verdad, pensé más esto como crear un API en Spring Boot, no sé qué tanta carga pueda generar el socket montado en un servidor pero al menos
podrías también bloquearse cuando sea necesario o cambiar ed puerto facilmente.


<b> ¿Qué mejoraría en producción? </b>
Empezando por ocultar información sensible, crear un properties o en mi preferencia declarar variables de entorno a nivel del servidor, como los puertos, constraseñas y bdd.
Claramente una conexión JDBC bien definida.
Más uso de lambdas.
Crear más trace log bien estructurado para rastrear los request.
Mejor manejo de errores, más especificos y más controlados sin parar la aplicación.
Crear timeouts.
Mucho de esto no se implementó por falta de tiempo.





