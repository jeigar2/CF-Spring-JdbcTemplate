package es.comepiedras.retosprogramacion.repository;

import es.comepiedras.retosprogramacion.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class UserRepository {
    Logger logger = Logger.getLogger(UserRepository.class.getName());

//    public static final String SCRIPT_H2 = "src/main/resources/sql/createDatabase_h2.sql";

    private final JdbcTemplate jdbcTemplate;

    public void initDB() {
        try {
//            String script = FileUtils.readFileToString(new File(SCRIPT_H2), "UTF-8");
//            jdbcTemplate.execute(script);
            jdbcTemplate.execute("CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE)");
            jdbcTemplate.execute("INSERT INTO users (username, email) VALUES (\"RAUL\", \"raul at ul.ol\")");
        } catch (NullPointerException | DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"));
        }
    };

    public void createUser(User user) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail());
        logger.info("Usuario creado exitosamente");
    }

    public User getUserById(int id) {
        String sql = "SELECT * fom users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * from users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, email);
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getId());
        if (affectedRows > 0) {
            logger.info("Usuario actualizado exitosamente");
        } else {
            logger.warning("No se encontró el usuario con el id; " + user.getId());
        }
    }

    public void updateUserByEmail(User user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE email = ?";
        int affectedRows = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getEmail());
        if (affectedRows > 0) {
            logger.info("Usuario actualizado exitosamente");
        } else {
            logger.warning("No se encontró el usuario con el id; " + user.getId());
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        if (affectedRows > 0) {
            logger.info("Usuario eliminado exitosamente");
        } else {
            logger.warning("No se encontró el usuario con el id: " + id);
        }
    }

    public void deleteUserByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        int affectedRows = jdbcTemplate.update(sql, email);
        if (affectedRows > 0) {
            logger.info("Usuario eliminado exitosamente");
        } else {
            logger.warning("No se encontró el usuario con el email: " + email);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * from users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

}
