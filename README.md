![hyper-header](https://i.imgur.com/yjkz8v9.png)
# Hyper
Hyper Music Player es una aplicacion para la reproduccion y organizacion de musica permitiendo el acceso a ella tanto localmente como a traves de la base de datos.


## Vistas
- Login/Register: Al abrir por primera vez la aplicacion hay una ventana para iniciar sesion o registrarse.
- Inicio: Habra 5 filas de albumes y playlist, algunos generados por la aplicacion y algunos de otros usuarios de la aplicacion.
- Buscar: Habra 4 pestañas para poder buscar entre todas las canciones, todas las playlist publicas, todos los albumes y todos los artistas.
- Biblioteca: En esta vista se encuentran todas las playlist y albumes que se hayan guardado.
- Playlist/Album: Contenedores de canciones creados por artistas o usuarios, permiten una reproduccion de canciones especicas.
- Perfil/Artista: Vista general sobre un usuario o artista.
- Configuracion: Vista con diferentes opciones sobre la aplicacion, aqui se encuentra el ThemeSelecter.


## Caracteristas a mencionar
- Para reproducir una cancion en concreto habra que darle doble click
- Existe un menu al dar click derecho sobre alguna cancion.
- Al darle doble click a una cancion desde Buscar o desde el Artista, se reproducira unicamente esa, mientras que si se da desde un album o playlist se reproduciran en orden desde esa hasta el final.
- En caso de estar reproduciendo una playlist o un album, activando el modo aleatorio se usara todo el contenido de la playlist o el album. Y en caso de tener canciones añadidas a la cola se perderan.
- El menu de usuario se despliega dandole un click izquierdo encima del icono de usuario, tendremos 2 opciones mas si el perfil es de artista.
- En caso de estar con la cuenta de Hyper (administrador) tendremos en el menu de usuario una opcion mas que nos abrira un dialogo para renovar las canciones de las playlist creadas por Hyper.
- En las playlist que sean del usuario logueado al dar click derecho sobre una cancion, estara tambien la opcion de quitarla de la lista.
- En las playlist o albums que sean del usuario logueado, habra una opcion al lado de descarga para editar este.
- Para cambiar de tema es obligatorio reiniciar la aplicacion, y al cambiar la propia aplicacion reiniciara una vez cerremos el dialogo en el que nos informa de ello.


## Xampp Lite
Se ha proporcionado la version lite de Xampp con solo 4 canciones para reducir el espacio final.

Caracteristicas:
- Dentro de Hyper en el inicio: se encuentra un Album llamado "ALBUM VERSION LITE" en la segunda fila; y en la cuarta fila tenemos una Playlist llamada "LIST VERSION LITE". El contenido de ambas es el disponible en la version Lite de Xampp para ser reproducido, en caso de querer testear el programa se recomienda borrar canciones de ahi y añadirlas, ya que en caso de intentar reproducir otra cancion, el programa dara error ya que no esta pensado para una version Lite del servidor.
- Hyper no permite añadir duplicados a las Playlist pero si eliminarlas y volveras a añadir por si se quiere comprobar esas funcionalidades.
- También se podra reproducir esas 4 canciones desde cualquier otra lista o desde el propio buscador, aunque es posible que si se reproduce la siguiente cancion y no se encuentra entre esas 4, de lugar a error del programa.

![listasLite](https://i.imgur.com/RsjfHZs.png)


## Requisitos
- Xampp: Es el servidor local con el contenido de la base de datos MySQL y gran parte de los archivos de Hyper.


## Configurar Xampp
Para configurar Xampp tendremos que acceder al archivo themes.xml, el cual se encuentra en la ruta Hyper\scr\ y tendremos el siguiente contenido: 

![themes.xml](https://i.imgur.com/6HkfzDX.png)

Y a continuacion reemplazar la ruta del elemento xampp, poniendo en su lugar la ruta en la que se haya guardado el servidor.

Es obligatorio guardar la ruta terminandola en /htdocs/ para su correcto funcionamiento


## Lanzar Xampp
Para iniciar el servidor, habra que dirigirse dentro de la carpeta de Xampp y buscar el archivo xampp_start.

Una vez ejecutado ya se podra iniciar Hyper sin ningun tipo de fallo.


## Fallo MySQL
En caso de que el puerto que usa MySQL este ocupado por un MySQL local ya instalado, habra que apagar el MySQL local de la siguiente manera:

Abrir un terminal cmd y ejecutar "cd C:\Program Files\MySQL\MySQL Server 8.0\bin" (Sin las comillas).

Una vez nos encontremos en esa ruta ejecutar "mysqladmin -u root -p shutdown" (Sin las comillas).

El resultado final debe ser asi:

![mysql](https://i.imgur.com/qca3xUL.png)