package persistence;

import java.util.List;
import java.util.Map;

class LuceneTextResultSet implements BDeResultSet {
	
	// List representing Text result set
	private List<Map<String, Object>> textResultSet;
	
	// Index representing the position of the cursor in the Text result set
	private int index;
	
	public LuceneTextResultSet(List<Map<String, Object>> results) {
		textResultSet = results;
		init();
	}

	@Override
	public void init() {
		index = -1;
	}

	@Override
	public boolean next() {
		index++;
		return (index >= 0 && index < textResultSet.size());
	}

	@Override
	public Map<String, Object> getCurrentItem() {
		try {
			return textResultSet.get(index);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

}
