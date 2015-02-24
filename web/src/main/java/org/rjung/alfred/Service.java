package org.rjung.alfred;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@ComponentScan(basePackages = "org.rjung.alfred")
@EnableAutoConfiguration
@Configuration
public class Service {

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

}
