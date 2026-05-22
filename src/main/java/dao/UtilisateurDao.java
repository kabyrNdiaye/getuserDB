package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Utilisateur;

/**
 * DAO avec persistance MySQL via JDBC.
 * 
 * Prérequis :
 *   - Driver MySQL Connector/J dans WEB-INF/lib/
 *   - Base de données créée (voir script SQL ci-dessous)
 * 
 * Script SQL d'initialisation :
 *   CREATE DATABASE IF NOT EXISTS gestusers CHARACTER SET utf8mb4;
 *   USE gestusers;
 *   CREATE TABLE IF NOT EXISTS utilisateurs (
 *       id       INT AUTO_INCREMENT PRIMARY KEY,
 *       nom      VARCHAR(100) NOT NULL,
 *       prenom   VARCHAR(100) NOT NULL,
 *       login    VARCHAR(100) NOT NULL UNIQUE,
 *       password VARCHAR(255) NOT NULL
 *   );
 */
public class UtilisateurDao
{
    // ── Configuration de la connexion ─────────────────────────────────────────
    private static final String JDBC_URL      = "jdbc:mysql://localhost:3306/gestusers?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String JDBC_USER     = "root";
    private static final String JDBC_PASSWORD = "";

    // ── Chargement du driver ──────────────────────────────────────────────────
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL introuvable. Vérifiez mysql-connector-j.jar dans WEB-INF/lib/", e);
        }
    }

    // ── Connexion ─────────────────────────────────────────────────────────────
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    /**
     * Ajoute un utilisateur en base.
     * L'id généré par AUTO_INCREMENT est injecté dans l'objet.
     */
    public static boolean ajouter(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (nom, prenom, login, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getLogin());
            stmt.setString(4, utilisateur.getPassword());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    utilisateur.setId(keys.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retourne la liste complète des utilisateurs.
     */
    public static ArrayList<Utilisateur> lister() {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, login, password FROM utilisateurs ORDER BY id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                liste.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("login"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * Retourne un utilisateur par son id, ou null s'il n'existe pas.
     */
    public static Utilisateur get(int id) {
        String sql = "SELECT id, nom, prenom, login, password FROM utilisateurs WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("login"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Met à jour les données d'un utilisateur existant.
     */
    public static boolean modifier(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET nom = ?, prenom = ?, login = ?, password = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getLogin());
            stmt.setString(4, utilisateur.getPassword());
            stmt.setInt(5, utilisateur.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un utilisateur par son id.
     */
    public static boolean supprimer(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
