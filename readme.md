# GildedRose elegant Solution

This is an explanation of how to solve this beauty kata.

[KATA LINK](https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/master/GildedRoseRequirements.txt)

The problem we find is that we have all the logic on the same method.

What are the problems about it?

1. Not maintainable code
2. Not readable code

After doing 3 different solutions:
[PREVIOUS SOLUTIONS](https://github.com/delalama/Gilded-rose-kata)

I propose an alternative solution that in my opinion offers more readable code.

# Solution explanation

After checking that legacy code works fine for all old requirements we add a massive approval bunch of tests.

```java
@Test
public void gildedRoseApprovalTest(){
        CombinationApprovals.verifyAllCombinations(
        this::updateItem,
        new String[]{"anyItem","+5 Dexterity Vest","Aged Brie","Elixir of the Mongoose","Sulfuras, Hand of Ragnaros","Backstage passes to a TAFKAL80ETC concert","Iñaki me debes un ramen"},
        range(-1,51),
        range(-1,12)
        );
}
```

This offers us output data about 4732 different combinations.

# Let's dive in logic.

The legacy code go through the Item[] inside the update method. We can directly substitute for this:

```java
for(Item item:items){
        updateController.run(item);
}
```

With this change that doesn't modify the internal logic we started to gain readability because we delete the first loop.

So we continue creating the `updateController`.

```java
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
```

This controller has the `ItemUpdaterSetter` and `ItemUpdater` as a dependency, why?
- Because we need to implement different code branches to split the logic into readable and small logic
- Because we like the maintainable code and we don't know if we will add different items in the future.
- Because is possible to need in the future an item classifier.

So this `update()` will only set the `ItemUpdater` and execute the `update()`.

### ItemUpdaterSetter

```java
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
    private final String isBackstage = "Backstage passes to a TAFKAL80ETC concert Brie";
    private final String isConjured = "Conjured Mana Cake Brie";
}

```
### ItemUpdater

```java
public interface ItemUpdater {
    public void update(Item item);
}

```
Why ?

If we do the job with the help of an interface we can continue without touching 
the legacy logic.

## Interface implementations
The place where remains the "custom" logic for each kind of item.

This is the initial status of AgedBrie item

```java
public class AgedBrieUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        if (!item.name.equals("Aged Brie")
                && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.quality > 0) {
                if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn < 0) {
            if (!item.name.equals("Aged Brie")) {
                if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.quality > 0) {
                        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }
}

```
This is the legacy logic inside AgedBrieUpdater.

Simply extracting all booleans (`ctrl+alt+v` in intelij) we will have this new code.

```java
        boolean isAgedBrie = item.name.equals("Aged Brie");
        boolean isBackstage = item.name.equals("Backstage passes to a TAFKAL80ETC concert");
        boolean isNotSulfuras = !item.name.equals("Sulfuras, Hand of Ragnaros");
        
```
Now, as we are in AgedBrie updateMethod we can assign final values to that booleans.
```java
        boolean isAgedBrie = true;
        boolean isBackstage = false;
        boolean isNotSulfuras = true;
```

Now you will find the class plenty of lights that tells you that you can simplify mostly all the logic.
After autoSimplify the code this is the class
```java
public class AgedBrieUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        boolean isAgedBrie = true;
        boolean isBackstage = false;
        boolean isNotSulfuras = true;

        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }

        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }
}

```
Since all booleans now are not needed......we can delete them, but is preferable to simply copy them to refactor the other updaters

AgedBrie after automatic refactor
```java
public class AgedBrieUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {

        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }

        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }
}
```
Same for all updaters.

Once this is done we must only implement the StandardUpdater and the conjuredUpdater(requirement)

```java
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
```

 ```java
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
```

And that's all, thanks to the IDE we've done the job.
If you find a more readable option please comment it, thanks
