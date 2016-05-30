package cs594Homework2;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;




public class CollectorTest {
	private CollectorMock<SimpleModelClass, MockData> collector;
	private SourceMock<MockData> source;

	/*@Before
    public void setup() {
        collector = new CollectorMock();
        source = (Source<MockData>) new MockSource();
    }*/
	
	@Test
    public void mungee() throws Exception {
		source=(SourceMock<MockData>) new MockSource();
       Collection<MockData> src=source.next();
       Assert.assertEquals(src.size(), 7);
       MockCollector m=new MockCollector();
       Collection<SimpleModelClass> data=m.mungee(src);
       Assert.assertEquals(data.size(), 5);
        
        

    }
	

}
