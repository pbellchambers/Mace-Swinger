package uk.co.pbellchambers.maceswinger.gui.components;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector4f;
import uk.co.pbellchambers.maceswinger.gui.Gui;
import uk.co.pbellchambers.maceswinger.utils.Easing;
import uk.co.pbellchambers.maceswinger.utils.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiString extends GuiComponent {

	private float ticks;
	private Random rand = new Random();
	private List<Text> options = new ArrayList<Text>();

	public GuiString(int id, Gui gui) {
		super(id, gui);
	}

	public void addString(Text button) {
		options.add(button);

	}

	public int getId() {
		return id;
	}

	public class Text {
		public int id;
		public String text;
		public boolean isAvailable = true;
		private float x, y;
		private float yy;
		private float easDur;
		private float width = 300, height = 50;

		public Text(int id, String text, float xx, float yy) {
			this.id = id;
			this.text = text;
			this.x = xx;
			this.y = yy;
			this.yy = yy;
			this.easDur = 200.0f + (rand.nextFloat() * 100);
		}

		public boolean isMouseinBounds() {

			if (Mouse.getX() < this.x + width && Mouse.getX() > this.x
					&& Mouse.getY() < this.y + height && Mouse.getY() > this.y) {
				return true;
			}
			return false;

		}

		public void tick() {
			ticks++;
			if (Mouse.isButtonDown(0) && isMouseinBounds())
				parent.guiActionPerformed(id, 0);
			y = Easing.elasticOut(ticks, 0.0f, yy, easDur + 100);
			if (rand.nextInt(100) == 0)
				waveChars();
		}

	}

	public void render() {
		for (Text o : options) {

			Font.drawString(o.text, o.x, o.y, 3f, new Vector4f(1, 1, 1, 1));

		}

	}

	public void waveChars() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(int ticks) {
		for (Text b : options) {
			b.tick();
		}

	}

}

