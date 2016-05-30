package cs594Homework2;

import java.util.Collection;

public interface CollectorMock<SimpleModelClass, Mockdata> {

	Collection<cs594Homework2.SimpleModelClass> mungee(MockData data);

    void save(Collection<ModelClass> data);

	void save1(Collection<SimpleModelClass> data);
}
