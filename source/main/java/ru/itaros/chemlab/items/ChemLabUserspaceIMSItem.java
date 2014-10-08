package ru.itaros.chemlab.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.achievements.ChemLabAchievements;
import ru.itaros.chemlab.addon.cl3.userspace.UserspaceParsingAssist;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.physics.IMatterState;

public class ChemLabUserspaceIMSItem extends Item implements IMatterState {

	
	private String groupname,name;
	public ChemLabUserspaceIMSItem(String groupname, String name){
		super();
		this.name=name;
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setUnlocalizedName(groupname+"."+name);
		this.setTextureName("chemlab:"+groupname+"."+name);
	}
	@Override
	public void onCreated(ItemStack stack, World world,
			EntityPlayer player) {
		ChemLabAchievements.onCrafting(player,stack);
		super.onCreated(stack, world, player);
	}	
	
	
	private int lowerPoint,upperPoint;
	private IMatterState lowerForm,upperForm;
	private float heatCapacity;
	private long resistance;
	
	
	
	public final void setLowerPoint(int lowerPoint) {
		this.lowerPoint = lowerPoint;
	}
	public final void setUpperPoint(int upperPoint) {
		this.upperPoint = upperPoint;
	}
	public final void setLowerForm(IMatterState lowerForm) {
		this.lowerForm = lowerForm;
	}
	public final void setUpperForm(IMatterState upperForm) {
		this.upperForm = upperForm;
	}
	public final void setHeatCapacity(float heatCapacity) {
		this.heatCapacity = heatCapacity;
	}
	public final void setResistance(long resistance) {
		this.resistance = resistance;
	}
	
	@Override
	public int lowerPoint() {
		return lowerPoint;
	}

	@Override
	public IMatterState lowerForm() {
		return lowerForm;
	}

	@Override
	public int upperPoint() {
		return upperPoint;
	}

	@Override
	public IMatterState upperForm() {
		return upperForm;
	}

	@Override
	public float heatCapacity() {
		return heatCapacity;
	}

	@Override
	public long resistance() {
		return resistance;
	}
	
	private String lowerItemSearchString;
	private String upperItemSearchString;
	public void setLowerItemLinkString(String itemLower) {
		lowerItemSearchString=itemLower;
	}
	public void setUpperItemLinkString(String itemUpper) {
		upperItemSearchString=itemUpper;
	}
	
	
	public void link() {
		IUniversalStack lowerFormStack = UserspaceParsingAssist.parseLinkString(lowerItemSearchString);
		IUniversalStack upperFormStack = UserspaceParsingAssist.parseLinkString(upperItemSearchString);
		if(lowerFormStack!=null){
			lowerForm = (IMatterState)lowerFormStack.getItem();
		}
		if(upperFormStack!=null){
			upperForm = (IMatterState)upperFormStack.getItem();
		}
	}

}
