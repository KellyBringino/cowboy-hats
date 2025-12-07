package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.DerbyArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DerbyArmorRenderer extends GeoArmorRenderer<DerbyArmorItem> {
    public DerbyArmorRenderer() {
        super(new DerbyArmorModel());
    }
}
