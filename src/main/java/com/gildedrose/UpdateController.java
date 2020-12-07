package com.gildedrose;

import com.gildedrose.advancedItem.ItemUpdater;

public class UpdateController {

    ItemUpdater itemUpdater;
    ItemUpdaterSetter itemUpdaterSetter = new ItemUpdaterSetter();

    public void update(Item item) {
        setItemUpdater(item);
        itemUpdater.update(item);
    }

    private void setItemUpdater(Item item) {
        itemUpdater = itemUpdaterSetter.set(item);
    }
}
