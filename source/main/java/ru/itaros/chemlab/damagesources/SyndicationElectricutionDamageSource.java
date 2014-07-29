package ru.itaros.chemlab.damagesources;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class SyndicationElectricutionDamageSource extends DamageSource {

	public SyndicationElectricutionDamageSource() {
		super("syndic_pressure");
		this.setDamageBypassesArmor();
		this.setDamageIsAbsolute();
		this.setExplosion();
	}

	@Override
	public boolean isUnblockable() {
		return true;
	}

	//death.attack.syndic_pressure.player
	
	@Override
	public IChatComponent func_151519_b(EntityLivingBase actor) {
        String s = "death.attack." + this.damageType;
        String s1 = s + ".player";
        return new ChatComponentTranslation(s, new Object[] {actor.func_145748_c_()});
	}
	
	
	
	
	

}
