package ru.itaros.debug;

import ru.itaros.api.hoe.heat.Heat;

public class HeatEqualizationTest {

	public static void main(String[] args) {
		Heat h1 = new Heat(2000000L);
		Heat h2 = new Heat(1000000L);
		h1.addEnergy(2000000L*20L);
		
		for(int i = 0 ; i < 100 ; i++){
			h1.exchange(h2);
			h2.exchange(h1);
		}
		
	}

}
