package es.comepiedras.retosprogramacion;

import es.comepiedras.retosprogramacion.Service.UserService;
import es.comepiedras.retosprogramacion.config.JdbcConfig;
import es.comepiedras.retosprogramacion.model.User;
import es.comepiedras.retosprogramacion.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        UserService userService = new UserService(userRepository);
        String email1 = "johndoe@example_jdbcTemplate.com";
        User newUser = new User("JohnDoe_jdbcTemplate", email1);
        // Esta linea está para H2 que necesita crear la tabla porque la bd es volatil
        // userService.initDB();
        userService.createUser(newUser);
        logger.info("Creado satisfactoriamente el usuario");

        User user = userService.getUserByEmail(email1);
        logger.info("Recuperado usuario: " + user.toString());

        user.setUsername("Susan");
        userService.updateUser(user);
        logger.info("Recuperado usuario actualizado: " + user.toString());
        userService.getAllUsers().forEach(user1 -> logger.info(user1.toString()));

        userService.deleteUserByEmail(email1);
        logger.info("Eliminado usuario: " + email1);

        userService.getAllUsers().forEach(user1 -> logger.info(user1.toString()));

    }
}