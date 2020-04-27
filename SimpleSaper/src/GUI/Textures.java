package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum Textures {

	INSTANCE;

	private static BufferedImage FIELD_HIDDEN;
	private static BufferedImage FIELD_REVEALED;
	private static BufferedImage FLAG;

	private static int FIELD_SIZE_ORIGINAL;
	private static int FIELD_SIZE;
	private static float FIELD_SCALE = 1.0f;

	// _____________________________________________________________

	private Textures() {

		loadTextures();
	}

	private void loadTextures() {

		try {

			FIELD_HIDDEN = ImageIO.read(new File("Textures/field.png"));
			FIELD_REVEALED = ImageIO.read(new File("Textures/field_revealed.png"));
			FLAG = ImageIO.read(new File("Textures/flag.png"));

			FIELD_SIZE = FIELD_HIDDEN.getHeight();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FIELD_SIZE_ORIGINAL = FIELD_HIDDEN.getHeight();
	}

	public static Textures getInstance() {

		return INSTANCE;
	}

	public static void reduce_field_scale_by(int percentage) {

		FIELD_SCALE *= (100f - percentage) / 100;
		FIELD_SIZE = (int) (FIELD_SIZE * FIELD_SCALE);
	}

	public static int FIELD_SIZE_ORIGINAL() {
		return FIELD_SIZE_ORIGINAL;
	}

	public static BufferedImage FIELD_HIDDEN() {
		return FIELD_HIDDEN;
	}

	public static BufferedImage FIELD_REVEALED() {
		return FIELD_REVEALED;
	}

	public static BufferedImage FLAG() {
		return FLAG;
	}

	public static int FIELD_SIZE() {
		return FIELD_SIZE;
	}

	public static float FIELD_SCALE() {
		return FIELD_SCALE;
	}
}
