package ru.itaros.hoe.physics;

public interface IMatterState {

	//Those for iron:
	//-1 - 1538 C(solid)
	//1538 - 2862 C(molten)
	//2862 - MAX C(gas?)
	//Cp = 3.539(s)/3.624(g)
	
	/*
	 * Lower temp point before solidifying more
	 */
	public int lowerPoint();
	
	public IMatterState lowerForm();
	/*
	 * Upper temp point before melting/gasificate
	 */
	public int upperPoint();
	
	public IMatterState upperForm();
	/*
	 * Cp per 1 unit of Volume. In MJoules/K
	 */
	public float heatCapacity();
	
	/*
	 * Resistance in nOhm per 1 meter. Without temperature shift(at 25C)
	 */
	public long resistance();
	
}
