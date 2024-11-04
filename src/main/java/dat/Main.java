package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.utils.ApiProperties;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        //Populate.main(args);
        ApplicationConfig.startServer(ApiProperties.PORT);


        // husk at sætte entites i hiberNateconfig

        // sæt hibernate til når du laver routes og dao
    }
}