package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.vanilla.SyndicationElectricutionDamageSource;
import net.minecraft.util.DamageSource;

public class DamageSourceLoader {

	public static DamageSource syndic_electro;
	
	public static void load(){
		syndic_electro = new SyndicationElectricutionDamageSource();
	}
	
}
