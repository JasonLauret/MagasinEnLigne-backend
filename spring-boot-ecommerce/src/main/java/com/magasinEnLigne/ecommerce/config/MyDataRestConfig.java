package com.magasinEnLigne.ecommerce.config;

import com.magasinEnLigne.ecommerce.entity.*;
import com.magasinEnLigne.ecommerce.entity.Order;
import com.magasinEnLigne.ecommerce.entity.Product;
import com.magasinEnLigne.ecommerce.entity.ProductCategory;
import com.magasinEnLigne.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Permet de numériser la class en tant qu'élément
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // --- Les configurations ci-dessous permettent de rendre ces class en lecture seule ---
        // Tableau de méthode dont je ne veux pas
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // Appel de la méthode disableHttpMethods qui permet la desactivation des méthodes HTTP pour ProductCategory : PUT, POST, DELETE, PATCH
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);
        disableHttpMethods(Order.class, config, theUnsupportedActions);

        // Appeler une méthode d'assistance interne
        exposeIds(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    /*
    * Permet de désactiver les méthodes HTTP : PUT, POST, DELETE, de la class en paramètre
    */
    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                // Type de domaine donné
                .forDomainType(theClass)
                .withItemExposure((matdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // Exposer les IDs d'entité

        // - Obtenir une liste de toutes les classes d'entités de entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - Créer un tableau de ces types d'entités
        List<Class> entityClasses = new ArrayList<>();

        // - Obtenir les types d'entités pour les entités
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - Exposer les ID d'entité pour les types d'entités/domaines
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
