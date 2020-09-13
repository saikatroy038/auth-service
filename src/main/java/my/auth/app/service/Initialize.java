package my.auth.app.service;

import my.auth.app.entity.Grant;
import my.auth.app.entity.Service;
import my.auth.app.repository.GrantRepository;
import my.auth.app.repository.ServiceRepository;
import my.auth.app.wrapper.AccessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * Initialise DB with dummy grant and services on startup in dev
 *
 * @author github.com/saikatroy038
 */
@Profile("dev")
@Component
public class Initialize {

    @Autowired
    GrantRepository grantRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @PostConstruct
    public void test() {
        // create dummy service
        Service service = new Service();
        service.setName("App1");
        serviceRepository.save(service);

        // create grants
        Grant grant = new Grant();
        grant.setAccessType(AccessType.READ);
        grant.setService(service);
        grant = grantRepository.save(grant);

        grant = new Grant();
        grant.setAccessType(AccessType.WRITE);
        grant.setService(service);
        grant = grantRepository.save(grant);
    }
}
