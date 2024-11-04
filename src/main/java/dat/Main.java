package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.utils.ApiProperties;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populate populator = new Populate(emf);


        ApplicationConfig.startServer(ApiProperties.PORT);
        populator.populate();


        // sæt hibernate til når du laver routes og dao
    }
}