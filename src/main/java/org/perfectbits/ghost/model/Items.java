package org.perfectbits.ghost.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Container/wrapper class for all items
 * @author jbelickas
 *
 */
public class Items {
	private Map<Color, Integer> taoTokens = new  HashMap<Color, Integer>();
	private Integer yinYangTokens = 0;
	private Integer neutralPowerTokens = 0;
	private Integer buddas = 0;
	
	public Items() {
		// initiliaze tokens
		taoTokens.put(Color.BLUE, 0);
		taoTokens.put(Color.GREEN, 0);
		taoTokens.put(Color.YELLOW, 0);
		taoTokens.put(Color.RED, 0);
	}
	int taoTokens(Color color) {
		return taoTokens.get(color);
	}
}
