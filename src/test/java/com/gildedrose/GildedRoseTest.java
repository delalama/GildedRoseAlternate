package com.gildedrose;

import com.gildedrose.builder.ItemBuilder;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    public void
    gildedRoseApprovalTest() {
        CombinationApprovals.verifyAllCombinations(
                this::updateItem,
                new String[]{"anyItem", "+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Iñaki me debes un ramen"},
                range(-1, 51),
                range(-1, 12)
        );
    }

    private Item updateItem(String name, int sellIn, int quality) {
        Item it = new ItemBuilder()
                .setName(name)
                .setSellIn(sellIn)
                .setQuality(quality)
                .createItem();

        new GildedRose(new Item[]{it}).updateQuality();

        return it;
    }

    private Integer[] range(int initial, int end) {
        return IntStream.range(initial, end).boxed().toArray(Integer[]::new);
    }

    @Test
    public void aged_brie_shoul_increase_quality(){
        Item[] items = new Item[]{new Item(AGED_BRIE,10,10)};
        GildedRose gildedRose = new GildedRose( items );

        gildedRose.updateQuality();

        assertEquals(11, items[0].quality);
    }

    @Test
    public void backstage_should_increase_twice_when_sellin_between_6_and_10(){
        Item[] items = new Item[]{new Item(BACKSTAGE,8,10)};
        GildedRose gildedRose = new GildedRose( items );

        gildedRose.updateQuality();

        assertEquals(12, items[0].quality);
    }


    @Test
    public void conjurer_should_decrease_quality_twice(){
        Item[] items = new Item[]{new Item(CONJURED,10,10)};
        GildedRose gildedRose = new GildedRose( items );

        gildedRose.updateQuality();

        assertEquals(8, items[0].quality);
    }

    @Test
    public void aged_brie_quality_should_not_be_over_50(){
        Item[] items = new Item[]{new Item(AGED_BRIE,5,50)};
        GildedRose gildedRose = new GildedRose( items );

        gildedRose.updateQuality();

        assertEquals(50, items[0].quality);
    }


    private String FOO = "+5 Dexterity Vest";
    private String AGED_BRIE = "Aged Brie";
    private String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private String CONJURED = "Conjured Mana Cake";

}
