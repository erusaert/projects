package com.SEG5C.Model;

import java.util.ArrayList;

public class ItemContainer extends Component {
	
	private ArrayList<Component> ItemList;
	
	public ItemContainer(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.ItemList = new ArrayList<Component>();
	}

	public void addItem(Component Item) {
		ItemList.add(Item);
	}

	public void removeItem(Component Item) {
		ItemList.remove(Item);
	}

}
