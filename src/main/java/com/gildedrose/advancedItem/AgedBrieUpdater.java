package com.gildedrose.advancedItem;

import com.gildedrose.Item;

public class AgedBrieUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;

        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }

        if (item.sellIn < 0) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }
}
