package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import sample.domain.Role;
import sample.domain.User;
import sample.repository.RoleRepository;
import sample.repository.UserRepository;

@Component
public final class InitializationService implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		if (roleRepo.findAll().size() == 0) {
			roleRepo.save(new Role(1L, "ROLE_USER", true));
			roleRepo.save(new Role(2L, "ROLE_ADMIN", false));
			roleRepo.save(new Role(3L, "ROLE_GUEST", false));
			
			userRepo.save(new User(1L, "admin", "admin", "admin@admin.com", "123456", "admin", 2L));
		}
    }

}
