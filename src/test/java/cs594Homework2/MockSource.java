package cs594Homework2;

import java.util.Collection;

import com.google.common.collect.Lists;

public class MockSource implements SourceMock<MockData>{

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<MockData> next() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(
				new MockData("M 2.3 Eastern Sea of Japan", "Eastern Sea of Japan", "mb", "point", 357018912, 340233123, 12344555, 48,2.3, 0.3, 2.3, 0.0, 2.3, 66.508,-14.863,"si544435"),
				new MockData("M 4.2 Tarapaca, Chile", "Tarapaca, Chile", "mc", "point", 352438912, 444433123, 445344555, 23,4.2, 1.7, 0.5, 0.0, 2.3, 36.508,-12.863,"di57845"),
				new MockData("M 1.0 7km NNW of Westmorland, CA", "7km NNW of Westmorland, CA", "mb", "point",235018912, 560234323, 15644535, 54,1.0, 3.2, 0.2, 0.1,1.3, 45.865,-234.31,"gf86373"),
				new MockData("M 1.9 7km N of Westmorland, CA", "7km N of Westmorland, CA", "mc", "point", 326078921, 543458123, 32721822, 51,1.9, 0.3, 1.2, 0.4, 0.54, 12.832,-123.34,"sa541798"),
				new MockData("M 0.0 67km ENE of Bishop, CA", "67km ENE of Bishop, CA", "mh", "point", 364543111, 875633163, 98761115, 52,0.0, 1.2, 1.5, 0.2, 0.7, 23.445,-152.72,"si587235"),
				new MockData("M 2.8 Western Sea of India", "Western Sea of India", "mh", "point", 357018912, 340233123, 12344555, 28,2.8, 2.3, 2.3, 0.0, 2.3, 126.58,-124.863,"sd34435"),
				new MockData("M 0.0 near the east coast of Honshu, Japan", "the east coast of Honshu, Japan", "mb", "point", 353245912, 346784123, 43124555, 28,0.0, 1.3, 2.2, 0.2, 2.6, 26.456,-12.233,"si65423")				
				);
	}

}
