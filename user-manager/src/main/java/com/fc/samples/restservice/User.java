package com.fc.samples.restservice;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

@Indices({
        @Index(value = "name", type = IndexType.NonUnique),
        @Index(value = "email", type = IndexType.Unique)
})
public class User {

    @Id
    private String account;
    private String password;
    private String name;
    private String email;
    private String description;

    public static User build() {
        return new User();
    }

    private User() {
        // only builder can give an instance
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

}
