package dat.controller;

import io.javalin.http.Context;

public interface IController {

    // CRUD operations
    void getById(Context ctx);
    void getAll(Context ctx);
    void create(Context ctx);
    void update(Context ctx);
    void delete(Context ctx);


}
