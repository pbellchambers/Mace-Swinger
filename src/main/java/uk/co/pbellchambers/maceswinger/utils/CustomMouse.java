package uk.co.pbellchambers.maceswinger.utils;

import org.lwjgl.input.Mouse;

public class CustomMouse {
	
	
	
	public static float getX(){
		return Mouse.getX()*CustomDisplay.getxScale();
		
	}
	public static float getY(){
		return Mouse.getY()*CustomDisplay.getyScale();
		
	}
	
	public static boolean isButtonDown(int i) {
		return Mouse.isButtonDown(i);
	}
}
