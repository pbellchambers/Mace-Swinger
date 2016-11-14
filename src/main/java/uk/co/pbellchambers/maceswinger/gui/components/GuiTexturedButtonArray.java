
package uk.co.pbellchambers.maceswinger.gui.components;


import org.lwjgl.opengl.GL11;
import uk.co.pbellchambers.maceswinger.gui.Gui;
import uk.co.pbellchambers.maceswinger.utils.CustomMouse;
import uk.co.pbellchambers.maceswinger.utils.Textures;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GuiTexturedButtonArray extends GuiComponent{



	private List<TexturedButton> options = new ArrayList<TexturedButton>();
	

	

	public GuiTexturedButtonArray(int id, Gui gui) {
		super(id, gui);
	}

	public void addButton(TexturedButton button) {
		options.add(button);
		
	}

	public int getId() {
		return id;
	}

	
	public class TexturedButton {
		public int id;
		public int text, ttext;
		public boolean isAvailable = true;
		private float x,y;
		private float width,height;
		private boolean isActive = true;
		public TexturedButton(int id, int text,int ttext, float x, float y, float width, float height) {
			this.id = id;
			this.text = text;
			this.ttext=ttext;
			this.x=x;
			this.y=y;
			this.width = width;
			this.height=height;
		}
		public boolean isCustomMouseinBounds(){
			
			if(CustomMouse.getX()<this.x+width&&CustomMouse.getX()>this.x&&CustomMouse.getY()<this.y+height&&CustomMouse.getY()>this.y){
				return true;
			}
			return false;
			
		}
		public void tick(){
			if (isActive && CustomMouse.isButtonDown(0) && isCustomMouseinBounds()) {
				parent.guiActionPerformed(id, 0);
			} else if (!isCustomMouseinBounds()) {
				isActive = true;
			}
		}
	}
		
	
	

	public void render() {
		for (TexturedButton o : options) {
			glPushMatrix();
			if(o.isCustomMouseinBounds())GL11.glColor4f(1,1,1,1);
			if(!o.isCustomMouseinBounds())GL11.glColor4f(1,1,1,0.5f);
			glBindTexture(GL_TEXTURE_2D,Textures.textureID[o.text]);
			glBegin(GL_QUADS);
			glTexCoord2f(0,1);
			glVertex2f(o.x, o.y);
			glTexCoord2f(1,1);
		    glVertex2f(o.x+o.width, o.y);
		    glTexCoord2f(1,0);
		    glVertex2f(o.x+o.width, o.y+o.height);
		    glTexCoord2f(0,0);
		    glVertex2f(o.x, o.y+o.height);
		    GL11.glColor4f(1,1,1,1);
	        glEnd();
	        glBindTexture(GL_TEXTURE_2D,Textures.textureID[o.ttext]);
	        if(o.isCustomMouseinBounds())GL11.glTranslatef(0,6,0);
			glBegin(GL_QUADS);
			glTexCoord2f(0,1);
			glVertex2f(o.x, o.y);
			glTexCoord2f(1,1);
		    glVertex2f(o.x+o.width, o.y);
		    glTexCoord2f(1,0);
		    glVertex2f(o.x+o.width, o.y+o.height);
		    glTexCoord2f(0,0);
		    glVertex2f(o.x, o.y+o.height);
		    GL11.glColor4f(1,1,1,1);
	        glEnd();
	        glPopMatrix();
		}
		
	}

	@Override
	public void tick(int ticks) {
		for(TexturedButton b:options){
			b.tick();
		}
		
	}
	public void deactivate(int id) {
		for (TexturedButton o : options) {
			if (o.id == id) {
				o.isActive = false;
				return;
			}
		}
	}
	
}

