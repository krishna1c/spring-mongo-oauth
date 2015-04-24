package sample.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sample.domain.Expense;
import sample.domain.Role;
import sample.domain.User;
import sample.repository.ExpenseRepository;
import sample.repository.RoleRepository;
import sample.repository.UserRepository;

@Component
public final class InitializationService implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private CounterService counter;
	@Autowired
	private ExpenseRepository expenseRepo;
	
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		if (roleRepo.findAll().size() == 0) {
			roleRepo.save(new Role(1L, "ROLE_USER", true));
			roleRepo.save(new Role(2L, "ROLE_ADMIN", false));
			roleRepo.save(new Role(3L, "ROLE_GUEST", false));
		}
		if (userRepo.findAll().size() == 0) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode("123456");
			userRepo.save(new User(counter.getNextUserIdSequence(), "admin", "admin", "admin@admin.com", hashedPassword, "admin", 2L));
		}
		if (expenseRepo.findAll().size() == 0){
			User user = userRepo.findAll().get(0);
			Expense expense = new Expense();
			expense.setAmount(new BigDecimal(123.45));
			expense.setDate(new Date());
			expense.setDescription("new expense");
			expense.setUserId(user.getUserId());
			expense.setExpenseId(counter.getNextExpenseIdSequence());
			expenseRepo.save(expense);
		}
    }

}
