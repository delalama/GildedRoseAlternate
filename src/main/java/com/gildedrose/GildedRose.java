package com.gildedrose;

class GildedRose {
    Item[] items;
    UpdateController updateController = new UpdateController();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        i_want_that_magic_happens();
    }
    public void i_want_that_magic_happens(){
        for (Item item : items) {
            updateController.update(item);
        }
    }
}