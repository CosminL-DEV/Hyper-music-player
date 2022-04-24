![hyper-header](https://i.imgur.com/yjkz8v9.png)
# Hyper
Hyper Music Player es una aplicación para la reproducción y organización de música permitiendo el acceso a ella tanto localmente como a través de la base de datos.

## Vistas
- Login/Register: Al abrir por primera vez la aplicación hay una ventana para iniciar sesión o registrarse.
- Inicio: Habrá 5 filas de álbumes y playlist, algunos generados por la aplicación y algunos de otros usuarios de la aplicación.
- Buscar: Habrá 4 pestañas para poder buscar entre todas las canciones, todas las playlist públicas, todos los álbumes y todos los artistas.
- Biblioteca: En esta vista se encuentran todas las playlist y álbumes que se hayan guardado.
- Playlist/Álbum: Contenedores de canciones creados por artistas o usuarios, permiten una reproducción de canciones específicas.
- Perfil/Artista: Vista general sobre un usuario o artista.
- Configuración: Vista con diferentes opciones sobre la aplicación, aquí se encuentra el ThemeSelecter.

## Características a mencionar
- Para reproducir una canción en concreto habrá que darle doble click
- Existe un menú al dar click derecho sobre alguna canción.
- Al darle doble click a una canción desde Buscar o desde el Artista, se reproducirá únicamente esa, mientras que si se da desde un álbum o playlist se reproducirán en orden desde esa hasta el final.
- En caso de estar reproduciendo una playlist o un álbum, activando el modo aleatorio se usará todo el contenido de la playlist o el álbum. Y en caso de tener canciones añadidas a la cola se perderán.
- El menú de usuario se despliega dándole un click izquierdo encima del icono de usuario, tendremos 2 opciones más si el perfil es de artista.
- En caso de estar con la cuenta de Hyper (administrador) tendremos en el menú de usuario una opción más que nos abrirá un dialogo para renovar las canciones de las playlist creadas por Hyper.
- En las playlist que sean del usuario logueado al dar click derecho sobre una canción, estará también la opción de quitarla de la lista.
- En las playlist o álbumes que sean del usuario logueado, habrá una opción al lado de descarga para editar este.
- Para cambiar de tema es obligatorio reiniciar la aplicación, y al cambiar la propia aplicación reiniciara una vez cerremos el dialogo en el que nos informa de ello.

## Xampp Lite
Se ha proporcionado la versión lite de Xampp con solo 4 canciones para reducir el espacio final.

Caracteristicas:
- Dentro de Hyper en el inicio: se encuentra un Álbum llamado "ALBUM VERSION LITE" en la segunda fila; y en la cuarta fila tenemos una Playlist llamada "LIST VERSION LITE". El contenido de ambas es el disponible en la versión Lite de Xampp para ser reproducido, en caso de querer testear el programa se recomienda borrar canciones de ahí y añadirlas, ya que, en caso de intentar reproducir otra canción, el programa dará error ya que no está pensado para una versión Lite del servidor.
- Hyper no permite añadir duplicados a las Playlist, pero si eliminarlas y volverás a añadir por si se quiere comprobar esas funcionalidades.
- También se podrá reproducir esas 4 canciones desde cualquier otra lista o desde el propio buscador, aunque es posible que, si se reproduce la siguiente canción y no se encuentra entre esas 4, de lugar a error del programa.

![listasLite](https://i.imgur.com/RsjfHZs.png)

## Requisitos
- Xampp: Es el servidor local con el contenido de la base de datos MySQL y gran parte de los archivos de Hyper.

## Configurar Xampp
Para configurar Xampp tendremos que acceder al archivo themes.xml, el cual se encuentra en la ruta Hyper\scr\ y tendremos el siguiente contenido: 

![themes.xml](https://i.imgur.com/6HkfzDX.png)

Y a continuación reemplazar la ruta del elemento xampp, poniendo en su lugar la ruta en la que se haya guardado el servidor.

Es obligatorio guardar la ruta terminándola en /htdocs/ para su correcto funcionamiento

## Lanzar Xampp
Para iniciar el servidor, habrá que dirigirse dentro de la carpeta de Xampp y buscar el archivo xampp_start.

Una vez ejecutado ya se podrá iniciar Hyper sin ningún tipo de fallo (Importante no cerrarlo mientras se quiera usar Hyper).

![Xampp.start](https://i.imgur.com/dBgpE7X.png)

Para apagar Xampp de manera segura, sin cerrar el otro terminal, ejecutamos el archivo xampp_stop y esperamos a que se cierren ambos por si solos.

## Fallo MySQL
En caso de que el puerto que usa MySQL este ocupado por un MySQL local ya instalado, habrá que apagar el MySQL local de la siguiente manera:

Abrir un terminal cmd y ejecutar "cd C:\Program Files\MySQL\MySQL Server 8.0\bin" (Sin las comillas).

Una vez nos encontremos en esa ruta ejecutar "mysqladmin -u root -p shutdown" (Sin las comillas).

El resultado final debe ser así:

![mysql](https://i.imgur.com/qca3xUL.png)