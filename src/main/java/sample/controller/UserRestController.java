package sample.controller;

import java.util.List;

import sample.exception.RestPreconditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.domain.User;
import sample.repository.RoleRepository;
import sample.repository.UserRepository;
import sample.service.CounterService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private CounterService counter;
	@Autowired
	private RoleRepository role;

	@RequestMapping(method=RequestMethod.GET)
	public List<User> getAll() {
		return repo.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public User getUser(@PathVariable Long id) {
		return RestPreconditions.checkFound(repo.findByUserId(id));
	}

	@RequestMapping(method=RequestMethod.POST)
	public User create(@RequestBody User user) {
		user.setUserId(counter.getNextUserIdSequence());
		user.setRoleId(role.findByIsDefault(true).getRoleId());
		return repo.save(user);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="{id}")
	public void delete(@AuthenticationPrincipal @PathVariable Long id) {
		User user = RestPreconditions.checkFound(repo.findByUserId(id));
		repo.delete(user.getId());
	}

	@RequestMapping(method=RequestMethod.PUT, value="{id}")
	public User update(@PathVariable Long id, @RequestBody User user) {
		User update = RestPreconditions.checkFound(repo.findByUserId(id));
		update.setEmail(user.getEmail());
		update.setFirstName(user.getFirstName());
		update.setLastName(user.getLastName());
		update.setUsername(user.getUsername());
		return repo.save(update);
	}

}