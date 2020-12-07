package com.gildedrose.advancedItem;

import com.gildedrose.Item;

public class StandardItemUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {

        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }

        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        }
    }
}
