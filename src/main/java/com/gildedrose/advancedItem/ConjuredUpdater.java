package com.gildedrose.advancedItem;

import com.gildedrose.Item;

public class ConjuredUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {

        item.sellIn = item.sellIn - 1;

        if (item.quality > 0) {
            item.quality = item.quality - 2;
        }

        if (item.sellIn < 0) {
            if (item.quality > 0) {
                item.quality = item.quality - 2;
            }
        }

        if(item.quality < 0 ){
            item.quality = 0;
        }
    }
}
