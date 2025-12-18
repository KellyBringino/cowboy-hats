package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.SlickBootsArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SlickBootsArmorRenderer extends GeoArmorRenderer<SlickBootsArmorItem> {
    public SlickBootsArmorRenderer() {
        super(new SlickBootsArmorModel());
    }
}