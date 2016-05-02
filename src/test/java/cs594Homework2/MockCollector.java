package cs594Homework2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

;

public class MockCollector implements CollectorMock<SimpleModelClass, MockData> {

	public Collection<SimpleModelClass> mungee(Collection<MockData> src) {
		// TODO Auto-generated method stub
//		Collection<SimpleModelClass> op=new ArrayList<SimpleModelClass>();
		return src.stream().filter(data -> data.getMag()!=0.0 ).map(SimpleModelClass::build).collect(Collectors.toList());
		/*for(MockData s:src)
		{
			if(s.getMag()!=0.0)
			{
				op.add(s);
			}
		}
		return op;*/
	}

	@Override
	public void save1(Collection<SimpleModelClass> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<SimpleModelClass> mungee(MockData json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Collection<ModelClass> data) {
		// TODO Auto-generated method stub
		
	}

}
