package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.dontouchat.cowboyhats.item.custom.PlasticArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlasticArmorRenderer extends GeoArmorRenderer<PlasticArmorItem> {
    public PlasticArmorRenderer() {
        super(new PlasticArmorModel());
    }
}