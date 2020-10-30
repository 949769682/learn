package club.hggzs.security;

import club.hggzs.entity.Users;
import club.hggzs.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringBootSecurityApplicationTests {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		Users user = new Users();
		user.setUsername("949769682");
		user.setPassword("123456");
		user.setMailbox("949769682@qq.com");
		userService.insert(user);

	}

}
