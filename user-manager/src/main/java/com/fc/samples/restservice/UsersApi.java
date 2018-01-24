package com.fc.samples.restservice;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import spark.Request;
import spark.Response;
import spark.Route;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class UsersApi {

    private static ObjectRepository<User> repository;
    private static Nitrite db;

    public static void init() {
        db = Nitrite.builder()
                .compressed()
                .filePath("/tmp/users.db")
                .openOrCreate("admin","admin");
        repository = db.getRepository(User.class);
    }

    public static void shutdown() {
        db.close();
    }

    public static Route addUser = (Request request, Response response) -> {

        response.type("application/json");

        User user = User.build()
                .setAccount(request.queryParams("account"))
                .setPassword(request.queryParams("password"))
                .setName(request.queryParams("name"))
                .setEmail(request.queryParams("email"))
                .setDescription(request.queryParams("description"));

        repository.insert(user);

        response.status(201);

        return user;

    };

    public static Route changeUser = (Request request, Response response) -> {

        response.type("application/json");

        User user = repository
                .find(eq("account",request.queryParams("account")))
                .firstOrDefault();

        if (user != null) {

            user.setPassword(request.queryParams("password"))
                    .setName(request.queryParams("name"))
                    .setEmail(request.queryParams("email"))
                    .setDescription(request.queryParams("description"));

            repository.update(user);

            response.status(200);

            return user;

        }

        response.status(404);
        return Message.build("404: account %s not found",request.queryParams("account"));

    };

    public static Route removeUser = (Request request, Response response) -> {

        response.type("application/json");

        User user = repository
                .find(eq("account",request.params(":account")))
                .firstOrDefault();
        if(user != null) {
            repository.remove(user);
            response.status(200);
            return Message.build("account %s removed",request.params(":account"));
        }

        response.status(404);
        return Message.build("404: user %s not found",request.params(":account"));

    };

    public static Route listUsers = (Request request, Response response) -> {

        response.type("application/json");

        Cursor<User> cursor = repository.find();

        response.status(200);

        return cursor.toList();

    };

    public static Route findUser = (Request request, Response response) -> {

        response.type("application/json");

        User user = repository
                .find(eq("account",request.params(":account")))
                .firstOrDefault();

        if(user != null) {
            response.status(200);
            return user;
        } else {
            response.status(404);
            return Message.build("404: user %s not found",request.params(":account"));
        }

    };

}
