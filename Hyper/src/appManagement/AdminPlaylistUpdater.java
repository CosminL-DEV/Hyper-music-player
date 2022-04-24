package appManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class AdminPlaylistUpdater {

    public void actualizarNovedades() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '1'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT song.song_id "
                        + "FROM song "
                        + "ORDER BY song.song_id DESC "
                        + "LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('1','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDescubrimientos() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '2'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('2','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEspecial1() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '3'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('3','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEspecial2() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '4'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('4','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEspecial3() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '5'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('5','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarTrap() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '6'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "WHERE song.genre = 'trap' "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('6','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarReggaeton() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '7'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "WHERE song.genre = 'reggaeton' "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('7','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarRap() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '8'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "WHERE song.genre = 'rap' "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('8','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarTechno() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '9'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "WHERE song.genre = 'techno' "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('9','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarHouse() {
        Statement sentencia;
        Statement sentencia2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sentencia2 = conexion.createStatement();
                String sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.playlist_id = '10'";
                sentencia2.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "(SELECT song.song_id "
                        + "FROM song "
                        + "WHERE song.genre = 'house' "
                        + "ORDER BY song.song_id DESC "
                        + ") AS temp "
                        + "ORDER BY RAND() LIMIT 30";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) {
                    sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                            + "VALUES ('10','" + resul.getString("song_id") + "','Hyper')";
                    sentencia2.executeUpdate(sql);
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminPlaylistUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
