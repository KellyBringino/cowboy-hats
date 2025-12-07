package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.GamblerArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class GamblerArmorRenderer extends GeoArmorRenderer<GamblerArmorItem> {
    public GamblerArmorRenderer() {
        super(new GamblerArmorModel());
    }
}
