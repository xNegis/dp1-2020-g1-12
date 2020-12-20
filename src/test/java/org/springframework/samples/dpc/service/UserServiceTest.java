package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.samples.dpc.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void shouldFindUser() {
		User optperfil = this.userService.findUser("cliente1");
		assertEquals("cliente1", optperfil.getPassword());
	}

	@Test
	@Transactional
	void shouldSaveUser() {
		User u = new User();
		u.setUsername("user");
		u.setPassword("cont");
		this.userService.saveUser(u);
		User user = this.userService.findUser("user");
		assertEquals(u, user);
	}

}
