package dat.controller;

import io.javalin.http.Context;

public interface IController<T,D> {

    void getById(Context ctx);
    void getAll(Context ctx);
    void create(Context ctx);
    void update(Context ctx);
    void delete(Context ctx);
    boolean validatePrimaryKey(D d);
    T validateEntity(Context ctx);

}
