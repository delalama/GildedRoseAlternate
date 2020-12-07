package com.gildedrose;

import com.gildedrose.advancedItem.*;

public class ItemUpdaterSetter {
    public ItemUpdater set(Item item) {
        switch (item.name){
            case isAgedBrie: return new AgedBrieUpdater();
            case isBackstage:return new BackstageUpdater();
            case isConjured: return new ConjuredUpdater();
            case isSulfuras: return new SulfurasUpdater();
            default : return new StandardItemUpdater();
        }
    }
    private final String isAgedBrie = "Aged Brie";
    private final String isSulfuras = "Sulfuras, Hand of Ragnaros";
    private final String isBackstage = "Backstage passes to a TAFKAL80ETC concert";
    private final String isConjured = "Conjured Mana Cake";
}
