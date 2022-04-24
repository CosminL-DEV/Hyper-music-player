![hyper-header](https://i.imgur.com/yjkz8v9.png)
# Hyper
Hyper Music Player es una aplicacion para la reproduccion y organizacion de musica permitiendo el acceso a ella tanto localmente como a traves de la base de datos.


## Caracteristicas


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