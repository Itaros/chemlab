package ru.itaros.hoe.coremod;

import java.io.IOException;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class HOEAccessTransformer extends AccessTransformer {
    public HOEAccessTransformer() throws IOException {
            super("hoelib_at.cfg");  
    }
}
