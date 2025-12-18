package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.BuckarooBootsArmorItem;
import net.dontouchat.cowboyhats.item.custom.PlasticBootsArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlasticBootsArmorRenderer extends GeoArmorRenderer<PlasticBootsArmorItem> {
    public PlasticBootsArmorRenderer() {
        super(new PlasticBootsArmorModel());
    }
}