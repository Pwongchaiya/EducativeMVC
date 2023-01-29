package com.example.springmicro.reactive.entity;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	//write your code here
    private @Id String id;
    private String name;
    private String password;
    private List<String> roles;


    public User(String name, String password, List<String> roles){
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(name, user.name) &&
			Objects.equals(password, user.password) &&
			Objects.equals(roles, user.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, roles);
	}

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			", password='" + "*******" + '\'' +
			", roles=" + roles +
			'}';
	}
}
