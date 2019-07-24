package com.devglan.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.devglan.dao.PrivilegeRepository;
import com.devglan.dao.RoleRepository;
import com.devglan.dao.UserRepository;
import com.devglan.dto.PrivilegeDto;
import com.devglan.dto.RoleDto;
import com.devglan.dto.UserDto;
import com.devglan.entities.Privilege;
import com.devglan.entities.Role;
import com.devglan.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	boolean alreadySetup = false;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Value("${privilegePath}")
	private String privilegePath;
	
	@Value("${rolePath}")
	private String rolePath;
	
	@Value("${userPath}")
	private String userPath;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;
		try {
			fileRegisterPrivileges();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileRegisterRoles();
		try {
			fileRegisterUsers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		alreadySetup = true;
	}

	private List<Privilege> fileRegisterPrivileges() throws FileNotFoundException {
		File src = ResourceUtils.getFile(privilegePath);
		if(src==null|| !src.exists()) {
			return null;
		}
		List<Privilege> privileges=new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<PrivilegeDto>> valueTypeRef = new TypeReference<List<PrivilegeDto>>() {
		};
		try {
			List<PrivilegeDto> privilegesDto = mapper.readValue(src, valueTypeRef);
			for(PrivilegeDto privilegeDto:privilegesDto) {
				Privilege privilege=createPrivilegeIfNotFound(privilegeDto);
				privileges.add(privilege);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return privileges;
	}
	
	private List<User> fileRegisterUsers() throws FileNotFoundException {
		File src = ResourceUtils.getFile(userPath);
		if(src==null|| !src.exists()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<UserDto>> valueTypeRef = new TypeReference<List<UserDto>>() {
		};
		try {
			List<UserDto> userDtos = mapper.readValue(src, valueTypeRef);
			for (UserDto userDto : userDtos) {
				User user= createUserIfNotFound(userDto);
				users.add(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	private User createUserIfNotFound(UserDto userDto) {
		User user = userRepository.findByUsername(userDto.getUsername());
		if (user == null) {
			user = new User();
			BeanUtils.copyProperties(userDto, user, "role");
			//Role role=roleRepository.findByName(userDto.getRole());
			//user.setRole(role);
			userRepository.save(user);
		}
		return user;
	}

	private List<Role> fileRegisterRoles() {
		List<Role> roles = new ArrayList<>();
		try {
			File src = ResourceUtils.getFile(rolePath);
			if(src==null|| !src.exists()) {
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<RoleDto>> valueTypeRef = new TypeReference<List<RoleDto>>() {
			};
			List<RoleDto> rolesDto = mapper.readValue(src, valueTypeRef);
			for (RoleDto roleDto : rolesDto) {
				List<Privilege> privileges = findByNameList(roleDto.getPrivileges());
				Role role = createRoleIfNotFound(roleDto, privileges);
				roles.add(role);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roles;
	}

	private List<Privilege> findByNameList(Collection<String> privileges) {
		if(privileges==null) {
			return null;
		}
		List<Privilege> privilegesList=new ArrayList<>();
		privileges.forEach(privilege->{
			privilegesList.add(privilegeRepository.findByName(privilege));
		});
		return privilegesList;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(PrivilegeDto privilegeDto) {
		Privilege privilege = privilegeRepository.findByName(privilegeDto.getName());
		if (privilege == null) {
			privilege= new Privilege(privilegeDto.getName());
			BeanUtils.copyProperties(privilegeDto, privilege);
			privilege = privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

	@Transactional
	private Role createRoleIfNotFound(RoleDto roleDto, Collection<Privilege> privileges) {
		Role role = roleRepository.findByName(roleDto.getName());
		if (role == null) {
			role = new Role(roleDto.getName());
			BeanUtils.copyProperties(roleDto, role, "privileges");
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}
}