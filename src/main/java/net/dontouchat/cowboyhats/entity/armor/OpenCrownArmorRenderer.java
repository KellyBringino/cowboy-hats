package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.OpenCrownArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class OpenCrownArmorRenderer extends GeoArmorRenderer<OpenCrownArmorItem> {
    public OpenCrownArmorRenderer() {
        super(new OpenCrownArmorModel());
    }
}
