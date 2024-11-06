package dat.dao;

import java.util.List;

                    //Generics
public interface IDAO<T, I> {

    T getById(I i);

    List<T> getAll();       // Set or ArrayList

    T create(T t);

    T update(I i, T t);

    void delete(I i);

    //Generics = Reusability and Flexibility


}
