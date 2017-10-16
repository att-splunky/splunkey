package com.techm.att.splunk.service.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    private InMemoryUserDetailsManager sampleService;
    public UserDetailsServiceImpl() {
        List<UserDetails> users = new ArrayList<>();
		for (Map.Entry<String, String> e : readResources().entrySet()) {
			if ("admin".equalsIgnoreCase(e.getKey()))
				users.add(new User(e.getKey(), e.getValue(),
						Collections.singletonList(new SimpleGrantedAuthority(
								"ROLE_ADMIN"))));
			else
				users.add(new User(e.getKey(), e.getValue(),
						Collections.singletonList(new SimpleGrantedAuthority(
								"ROLE_USER"))));

		}
        //users.add(new User("admin", "admin", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        //users.add(new User("user", "user", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        sampleService = new InMemoryUserDetailsManager(users);
    }
   

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sampleService.loadUserByUsername(username);
    }

	private Map<String, String> readResources() {
		File file = new File("userconfig.properties");
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LOGGER.info("IOException err: ", e1.getMessage());
		}
		Map<String, String> mapProp = properties
				.entrySet()
				.stream()
				.collect(
						Collectors.toMap(e -> (String) e.getKey(),
								e -> (String) e.getValue()));
		System.out.println("properties file : " + mapProp);
		return mapProp;
	}
}
