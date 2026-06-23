package net.mofusya.randomdrops.component;

import net.mofusya.ornatelib.registries.component.TagRegisterer;
import net.mofusya.ornatelib.registries.component.tag.BooleanTag;
import net.mofusya.randomdrops.C;

public class ModComponents {
    public static final TagRegisterer TAGS = new TagRegisterer(C.MOD_ID);

    public static final BooleanTag HAS_BEEN_MODIFIED = TAGS.register(new BooleanTag("has_been_modified"));
}
