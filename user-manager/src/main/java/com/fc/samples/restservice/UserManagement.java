package com.fc.samples.restservice;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class UserManagement {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(UserManagement.class);
        SparkUtils.createServerWithRequestLog(logger);

        UsersApi.init();

        port(8080);

        threadPool(40, 10, 30000);

        Gson gson = new Gson();

        notFound((request, response) -> {
            response.type("application/json");
            return gson.toJson(Message.build("Custom 404: request %s not found",request.toString()));
        });

        internalServerError((request, response) -> {
            response.type("application/json");
            return gson.toJson(Message.build("Custom 500: request %s provoke an internal server error",request.toString()));
        });

        path("/api", () -> {

            before("/*", (q,a) -> logger.info(String.format("Received api call %s",q.toString())));

            path("/user", () -> {

                get("/list", UsersApi.listUsers, gson::toJson);

                get("/account/:account", UsersApi.findUser, gson::toJson);

                post("/add", UsersApi.addUser, gson::toJson);

                put("/change", UsersApi.changeUser, gson::toJson);

                delete("/remove/:account", UsersApi.removeUser, gson::toJson);

            });

            path("/service", () -> {

                get("/stop", (request, response) -> {

                    stop();

                    UsersApi.shutdown();

                    return null;

                });

            });

        });

    }

}
