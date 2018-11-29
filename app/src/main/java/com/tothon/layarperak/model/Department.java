package com.tothon.layarperak.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Department extends ExpandableGroup<Crew> {

    private int itemSize;

    public Department(String title, List<Crew> items) {
        super(title, items);
        this.itemSize = items.size();
    }

    public int getItemSize() {
        return itemSize;
    }

}
