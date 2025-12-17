package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.BuckarooBootsArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BuckarooBootsArmorRenderer extends GeoArmorRenderer<BuckarooBootsArmorItem> {
    public BuckarooBootsArmorRenderer() {
        super(new BuckarooBootsArmorModel());
    }
}