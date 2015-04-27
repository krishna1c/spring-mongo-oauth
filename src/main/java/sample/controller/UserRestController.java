package sample.controller;

import java.util.List;

import sample.exception.RestPreconditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.domain.ExpenseSummary;
import sample.domain.User;
import sample.domain.UserEntry;
import sample.repository.RoleRepository;
import sample.repository.UserRepository;
import sample.service.CounterService;
import sample.service.ExpenseService;

/**
 * User Rest Controller
 * @author pmincz
 *
 */
@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private CounterService counter;
	@Autowired
	private RoleRepository role;
	@Autowired
	private ExpenseService service;

	/**
	 * Get all the users
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public List<User> getAll() {
		return repo.findAll();
	}
	
	/**
	 * Get user by userId
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public User getUser(@PathVariable Long id) {
		return RestPreconditions.checkFound(repo.findByUserId(id));
	}

	/**
	 * Create user by user entry
	 * @param entry
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public User create(@AuthenticationPrincipal @RequestBody UserEntry entry) {
		User user = new User(entry);
		user.setUserId(counter.getNextUserIdSequence());
		user.setRoleId(role.findByIsDefault(true).getRoleId());
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		return repo.save(user);
	}

	/**
	 * Delete user by userId
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="{id}")
	public void delete(@AuthenticationPrincipal @PathVariable Long id) {
		User user = RestPreconditions.checkFound(repo.findByUserId(id));
		repo.delete(user.getId());
	}

	/**
	 * Update user by userId
	 * @param id
	 * @param entry
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT, value="{id}")
	public User update(@AuthenticationPrincipal @PathVariable Long id, @RequestBody UserEntry entry) {
		User update = RestPreconditions.checkFound(repo.findByUserId(id));
		update.setEmail(entry.getEmail());
		update.setFirstName(entry.getFirstName());
		update.setLastName(entry.getLastName());
		update.setUsername(entry.getUsername());
		if (entry.getPassword() != "") {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(entry.getPassword());
			update.setPassword(hashedPassword);
		}
		return repo.save(update);
	}
	
	/**
	 * Get all the users summary
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value = "/summary")
	public List<ExpenseSummary> getSummaries() {
		return service.getSummary();
	}

}