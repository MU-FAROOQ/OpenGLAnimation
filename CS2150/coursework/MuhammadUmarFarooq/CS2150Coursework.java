/* CS2150Coursework.java
 * TODO: put your university username and full name here
 * University: Aston University
 * Username: 180034882
 * Name: Muhammad Umar Farooq
 * Course: Computer Science BSc - Year 2
 * This is my own piece of work.
 * 
 * Scene Graph:
 *  Scene origin
 *  |
 *  +-- [T(0,-1,-10) S(25,1,20)] Ground plane
 *  |
 *  +-- [T(0,4,-20) Rx(90) S(25,1,10)] Sky plane
 *  |
 *  +-- [T(moonX,moonY,-19) S(0.6,0.6,0.6)] Moon
 *  |
 *  +-- [T(sunX,sunY,-19) S(0.6,0.6,0.6)] Sun
 *  |
 *  +-- [T(-3,0,-10) S(2,2,2) Ry(40)] Barn Base
 *  |	|
 *  |	+-- [T(0,0.75,0) S(1,0.5,1)] Barn Roof
 *  |	|
 *  |	+-- [T(-0.5001,-1.5,-0.28) Ry(-90) S(3,2,1.5)] Barn Door
 *  |
 *  +-- [T(-3.0,-1.57,-10) S(2,3,2) Ry(10) Rx(18.4)] Windmill Stand
 *  |
 *  +-- [T(-2.7,1.45,-9.95) S(1.5,1.5,1.5) Ry(10) Rz(windmillSpin)] Windmill Blades
 *  |
 *  +-- [T(moveTruck+1,-0.65,-5) S(1,0.7,1)] Truck Hood
 *  |
 *  +-- [T(moveTruck,-0.65,-5) S(1,0.7,1)] Truck Back
 *  |
 *  +-- [T(moveTruck+0.7,-0.3,-5) S(0.85,0.7,1)] Truck Top/Roof
 *  |
 *  +-- [T(moveTruck+0.7,-0.3,-5) S(0.85,0.7,1)] Truck Windows
 *  |
 *  +-- [T(moveTruck+1.05,-0.83,-4.68) Rz(truckWheelSpin) S(0.14,0.14,0.05)] Truck Front Right Wheel
 *  |
 *  +-- [T(moveTruck+1.05,-0.83,-5.31) Rz(truckWheelSpin) S(0.14,0.14,0.05)] Truck Front Left Wheel
 *  |
 *  +-- [T(moveTruck,-0.83,-4.68) Rz(truckWheelSpin) S(0.14,0.14,0.05)] Truck Back Right Wheel
 *  |
 *  +-- [T(moveTruck,-0.83,-5.31) Rz(truckWheelSpin) S(0.14,0.14,0.05)] Truck Back Left Wheel
 *  |
 *  +-- [T(0,-0.1,-15) S(25,2,0)] Fence
 *
 *  TODO: Provide a scene graph for your submission
 */
package coursework.MuhammadUmarFarooq;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;
import GraphicsLab.*;

/**
 * TODO: Briefly describe your submission here
 * 
 * <p>
 * My animation is set on a farm with a sky and ground plane. There is also a
 * barn and a fence around the farm. There is a windmill which is spinning due
 * to the wind. The more complex animations involve the truck on the farm which
 * the user can interact with by moving it forward and back while the wheels on the
 * truck also spin in accordance with the truck moving forward or back. Another complex
 * animation is the day and night cycle where the moon comes up and the lighting
 * gets darker gradually while also a starry night sky is displayed. Then once the moon
 * goes down, the sunrises and the lighting gets brighter gradually and a cloudy sky is
 * displayed; this repeats for as long as the animation is open.
 * </p>
 * 
 * <p>
 * Controls:
 * <ul>
 * <li>Press the escape key to exit the application.
 * <li>Hold the x, y and z keys to view the scene along the x, y and z axis,
 * respectively
 * <li>While viewing the scene along the x, y or z axis, use the up and down
 * cursor keys to increase or decrease the viewpoint's distance from the scene
 * origin
 * <li>Pressing space bar will reset all animations and the scene.
 * <li>Holding up key will move the truck forward.
 * <li>Holding down key will move the truck backwards.
 * <li>Holding the R key will rotate the camera around the scene.
 * </ul>
 * TODO: Add any additional controls for your sample to the list above
 *
 */
public class CS2150Coursework extends GraphicsLab {
	//colours for orange and grey
	public static final Colour ORANGE = new Colour(238, 154, 0);
	public static final Colour GREY = new Colour(128, 128, 128);

	// display list id for plane
	private final int planeList = 1;
	// display list id for cube
	private final int cubeList = 2;
	// display list id for roof
	private final int roofList = 3;
	// display list id for door
	private final int doorList = 4;
	// display list id for windmill stand
	private final int windmillStandList = 5;
	// display list id for windmill blades
	private final int windmillBladesList = 6;
	// display list id for truck hood
	private final int truckHoodList = 7;
	// display list id for truck roof
	private final int truckRoofList = 8;
	// display list id for truck back
	private final int truckBackList = 9;
	// display list id for fence
	private final int fenceList = 10;
	// display list id for truck windows
	private final int truckWindowsList = 11;
	// windmill spin
	private float windmillSpin = 0.0f;
	// scene rotation
	private float rotateScene = 0.0f;
	// move truck
	private float moveTruck = -2.8f;
	// truck wheel spin
	private float truckWheelSpin = 0f;
	// moon X
	private float moonX = 12.0f;
	// moon Y
	private float moonY = -1.8f;
	// sun X
	private float sunX = -12.0f;
	// sun Y
	private float sunY = -1.8f;
	// increase diffuse
	private float diffuseIncrease = 0.3f;
	// increase ambient
	private float ambientIncrease = 0.2f;
	// is it night or day
	private boolean night = true;
	// is it dusk or dawn
	private boolean duskDawn = false;

	/** ids for nearest, linear and mipmapped textures for the ground plane */
	private Texture grass;
	/** ids for nearest, linear and mipmapped textures for the back plane */
	private Texture nightSky;
	/** ids for nearest, linear and mipmapped textures for the back plane */
	private Texture sky;
	// store door texture
	private Texture door;
	// store fence texture
	private Texture fence;
	// store dusk/dawn image
	private Texture dawn;

	// TODO: Feel free to change the window title and default animation scale here
	public static void main(String args[]) {
		new CS2150Coursework().run(WINDOWED, "CS2150 Coursework Submission", 0.015f);
	}

	protected void initScene() throws Exception {
		// TODO: Initialise your resources here - might well call other methods you write.
		// load the textures
		grass = loadTexture("coursework/MuhammadUmarFarooq/textures/grass.jpg");
		nightSky = loadTexture("coursework/MuhammadUmarFarooq/textures/starSky.jpg");
		sky = loadTexture("coursework/MuhammadUmarFarooq/textures/sky.jpg");
		dawn = loadTexture("coursework/MuhammadUmarFarooq/textures/dawn.jpg");
		door = loadTexture("coursework/MuhammadUmarFarooq/textures/doors.jpg");
		fence = loadTexture("coursework/MuhammadUmarFarooq/textures/fence.png");

		// global ambient light level
		float globalAmbient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// set the global ambient lighting
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(globalAmbient));

		// the first light for the scene is white...
		float diffuse0[] = { 0.3f, 0.3f, 0.3f, 1.0f };
		// ...with a dim ambient contribution...
		float ambient0[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// ...and is positioned above the viewpoint
		float position0[] = { 0.0f, 10.0f, 0.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, FloatBuffer.wrap(position0));

		// enable the first light
		GL11.glEnable(GL11.GL_LIGHT0);

		// enable lighting calculations
		GL11.glEnable(GL11.GL_LIGHTING);
		// ensure that all normals are re-normalised after transformations automatically
		GL11.glEnable(GL11.GL_NORMALIZE);

		// prepare the display lists for later use
		GL11.glNewList(planeList, GL11.GL_COMPILE);
		{
			drawPlane();
		}
		GL11.glEndList();
		GL11.glNewList(cubeList, GL11.GL_COMPILE);
		{
			drawUnitCube();
		}
		GL11.glEndList();
		GL11.glNewList(roofList, GL11.GL_COMPILE);
		{
			drawUnitRoof();
		}
		GL11.glEndList();
		GL11.glNewList(doorList, GL11.GL_COMPILE);
		{
			drawUnitDoor();
		}
		GL11.glEndList();
		GL11.glNewList(windmillStandList, GL11.GL_COMPILE);
		{
			drawWindmillStand();
		}
		GL11.glEndList();
		GL11.glNewList(windmillBladesList, GL11.GL_COMPILE);
		{
			drawWindmillBlades();
		}
		GL11.glEndList();
		GL11.glNewList(truckHoodList, GL11.GL_COMPILE);
		{
			drawTruckHood();
		}
		GL11.glEndList();
		GL11.glNewList(truckRoofList, GL11.GL_COMPILE);
		{
			drawTruckRoof();
		}
		GL11.glEndList();
		GL11.glNewList(truckBackList, GL11.GL_COMPILE);
		{
			drawTruckBack();
		}
		GL11.glEndList();
		GL11.glNewList(fenceList, GL11.GL_COMPILE);
		{
			drawFence();
		}
		GL11.glEndList();
		GL11.glNewList(truckWindowsList, GL11.GL_COMPILE);
		{
			drawTruckWindows();
		}
		GL11.glEndList();
	}

	protected void checkSceneInput() {// TODO: Check for keyboard and mouse input here
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			rotateScene -= 1.0f * getAnimationScale(); // go around if the R key is pressed
			if (rotateScene > 360.0f) // Wrap the angle back around into 0-360 degrees.
			{
				rotateScene = 0.0f;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (moveTruck < 1.8f) {
				moveTruck += 0.02f * getAnimationScale(); // make truck x axis increase
				truckWheelSpin -= 3f * getAnimationScale(); // make truck wheels rotate forward
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (moveTruck > -2.8f) {
				moveTruck -= 0.02f * getAnimationScale(); // make truck x axis decrease
				truckWheelSpin += 3f * getAnimationScale(); // make truck wheels rotate backwards
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			resetAnimations();
		}
	}

	protected void updateScene() {
		// TODO: Update your scene variables here - remember to use the current
		// animation scale value
		// (obtained via a call to getAnimationScale()) in your modifications so that
		// your animations
		// can be made faster or slower depending on the machine you are working on

		// increase windmillSpin
		windmillSpin += -3 * getAnimationScale();

		sunMoonCycle();

	}

	protected void renderScene() {
		// TODO: Render your scene here - remember that a scene graph will help you
		// write this method!
		// It will probably call a number of other methods you will write.
		
		// the first light for the scene is white...
		float diffuse0[] = { diffuseIncrease, diffuseIncrease, diffuseIncrease, 1.0f };
		// ...with a dim ambient contribution...
		float ambient0[] = { ambientIncrease, ambientIncrease, ambientIncrease, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			GL11.glTranslatef(0.0f, 0.0f, -20.0f); //if R pressed, move camera back to see whole scene
		}
		else {
			rotateScene = 0.0f; //if R released, reset camera position by resetting rotateScene
		}
		
		GL11.glRotatef(rotateScene, 0.0f, 1.0f, 0.0f); //rotate camera depending on the value of rotateScene

		// draw the ground plane
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to orange so that the texture
			// is bright and details can be seen clearly and looks like farm land
			ORANGE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, grass.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f, -1.0f, -10.0f);
			GL11.glScalef(25.0f, 1.0f, 20.0f);

			// GL11.glRotatef(rotateScene, 0.0f, 1.0f, 0.0f);

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the sky plane
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			if (night) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, nightSky.getTextureID());
			} else if (duskDawn) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, dawn.getTextureID());
			} else {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, sky.getTextureID());
			}

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(0.0f, 4.0f, -20.0f);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the moon
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);
			// how shiny are the front faces of the moon (specular exponent)
			float moonFrontShininess = 2.0f;
			// specular reflection of the front faces of the moon
			float moonFrontSpecular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
			// diffuse reflection of the front faces of the moon
			float moonFrontDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };
			float moonFrontEmission[] = { 0.1f, 0.1f, 0.1f, 1.0f };

			// set the material properties for the moon using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, moonFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(moonFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(moonFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_EMISSION, FloatBuffer.wrap(moonFrontEmission));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(moonX, moonY, -19.0f);
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			new Sphere().draw(1.0f, 25, 25);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the sun
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);

			// how shiny are the front faces of the sun (specular exponent)
			float frontShininess = 2.0f;
			// specular reflection of the front faces of the sun
			float frontSpecular[] = { 0.3f, 0.3f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the sun
			float frontDiffuse[] = { 0.9f, 1.0f, 0.0f, 1.0f };

			// set the material properties for the moon using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, frontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(frontDiffuse));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(sunX, sunY, -19.0f);
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			new Sphere().draw(1.0f, 25, 25);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the barn base, its roof and door
		GL11.glPushMatrix();
		{
			// position and scale the barn base
			GL11.glTranslatef(3.0f, 0.0f, -10.0f);
			GL11.glScalef(2.0f, 2.0f, 2.0f);
			// rotate the house a little so that we can see more of it
			GL11.glRotatef(40.0f, 0.0f, 1.0f, 0.0f);

			// how shiny are the front faces of the house (specular exponent)
			float houseFrontShininess = 2.0f;
			// specular reflection of the front faces of the house
			float houseFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the house
			float houseFrontDiffuse[] = { 0.6f, 0.2f, 0.2f, 1.0f };

			// set the material properties for the house using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, houseFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(houseFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(houseFrontDiffuse));

			// draw the base of the house using its display list
			GL11.glCallList(cubeList);

			// position and scale the barns roof relative to the base of the house
			GL11.glTranslatef(0.0f, 0.75f, 0.0f);
			GL11.glScalef(1.0f, 0.5f, 1.0f);

			// how shiny are the front faces of the roof (specular exponent)
			float roofFrontShininess = 2.0f;
			// specular reflection of the front faces of the roof
			float roofFrontSpecular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the roof
			float roofFrontDiffuse[] = { 0.2f, 0.2f, 0.2f, 1.0f };

			// Set the material properties for the house using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, roofFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(roofFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(roofFrontDiffuse));

			// draw the roof using its display list
			GL11.glCallList(roofList);

			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, door.getTextureID());

			// pos/scale barn door
			GL11.glTranslatef(-0.5001f, -1.5f, -0.28f);
			GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(3f, 2f, 1.5f);

			// how shiny are the front faces of the door
			float doorFrontShininess = 2.0f;
			// specular reflection of the front faces of the door
			float doorFrontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };
			// diffuse reflection of the front faces of the door
			float doorFrontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the door
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, doorFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(doorFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(doorFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(doorFrontDiffuse));

			// draw door using its display list
			GL11.glCallList(doorList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw windmill stand
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			// pos/scale windmill stand
			GL11.glTranslatef(-3.0f, -1.57f, -10.0f);
			GL11.glScalef(2f, 3f, 2f);
			GL11.glRotatef(10, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-18.4f, 1.0f, 0.0f, 0.0f);

			// how shiny are the front faces of the stand
			float windmillStandFrontShininess = 2.0f;
			// specular reflection of the front faces of the stand
			float windmillStandFrontSpecular[] = { 0.5f, 0.4f, 0.3f, 1.0f };
			// diffuse reflection of the front faces of the stand
			float windmillStandFrontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the stand
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, windmillStandFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(windmillStandFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(windmillStandFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(windmillStandFrontDiffuse));

			//draw stand using its display list
			GL11.glCallList(windmillStandList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw windmill blades
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			// pos/scale windmill blades
			GL11.glTranslatef(-2.7f, 1.45f, -9.95f);
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glRotatef(10, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(windmillSpin, 0.0f, 0.0f, 1.0f);

			// how shiny are the front faces of the blades
			float windmillBladesFrontShininess = 2.0f;
			// specular reflection of the front faces of the blades
			float windmillBladesFrontSpecular[] = { 0.5f, 0.4f, 0.3f, 1.0f };
			// diffuse reflection of the front faces of the blades
			float windmillBladesFrontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the blades
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, windmillBladesFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(windmillBladesFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(windmillBladesFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(windmillBladesFrontDiffuse));

			//draw blades using its display list
			GL11.glCallList(windmillBladesList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw truck hood
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			// pos/scale truck hood
			GL11.glTranslatef(moveTruck + 1f, -0.65f, -5.0f);
			GL11.glScalef(1f, 0.7f, 1.0f);

			// how shiny are the front faces of the hood
			float FrontShininess = 2.0f;
			// specular reflection of the front faces of the hood
			float FrontSpecular[] = { 1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the hood
			float FrontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the hood
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, FrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(FrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(FrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(FrontDiffuse));

			//draw hood using its display list
			GL11.glCallList(truckHoodList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw truck back
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			// pos/scale truck back
			GL11.glTranslatef(moveTruck, -0.65f, -5.0f);
			GL11.glScalef(1f, 0.7f, 1.0f);

			// how shiny are the front faces of the back
			float FrontShininess = 2.0f;
			// specular reflection of the front faces of the back
			float FrontSpecular[] = { 1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the back
			float FrontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the back
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, FrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(FrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(FrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(FrontDiffuse));

			//draw back using its display list
			GL11.glCallList(truckBackList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw truck top/roof
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			// pos/scale truck top
			GL11.glTranslatef(moveTruck + 0.7f, -0.3f, -5f);
			GL11.glScalef(0.85f, 0.7f, 1.0f);

			// how shiny are the front faces of the top
			float frontShininess = 2.0f;
			// specular reflection of the front faces of the top
			float frontSpecular[] = { 1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the top
			float frontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the top
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, frontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(frontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(frontDiffuse));

			//draw roof using its display list
			GL11.glCallList(truckRoofList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();
		
		// draw truck windows
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			// pos/scale truck top
			GL11.glTranslatef(moveTruck + 0.7f, -0.3f, -5f);
			GL11.glScalef(0.85f, 0.7f, 1.0f);

			// how shiny are the front faces of the top
			float frontShininess = 2.0f;
			// specular reflection of the front faces of the top
			float frontSpecular[] = { 0.8f, 0.8f, 0.8f, 1.0f };
			// diffuse reflection of the front faces of the top
			float frontDiffuse[] = { 0.4f, 0.4f, 0.4f, 1.0f };

			// set material properties of the top
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, frontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(frontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(frontDiffuse));

			//draw windows using its display list
			GL11.glCallList(truckWindowsList);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the truck front right wheel
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);
			// specular reflection of the front faces of the wheel
			float frontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the wheel using OpenGL
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(moveTruck + 1.05f, -0.83f, -4.68f);
			GL11.glRotatef(truckWheelSpin, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(0.14f, 0.14f, 0.05f);
			new Sphere().draw(1.5f, 10, 3);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the truck front left wheel
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);
			// specular reflection of the front faces of the wheel
			float frontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the wheel using OpenGL
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(moveTruck + 1.05f, -0.83f, -5.31f);
			GL11.glRotatef(truckWheelSpin, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(0.14f, 0.14f, 0.05f);
			new Sphere().draw(1.5f, 10, 3);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the truck back right wheel
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);
			// specular reflection of the front faces of the wheel
			float frontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the wheel using OpenGL
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(moveTruck, -0.83f, -4.68f);
			GL11.glRotatef(truckWheelSpin, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(0.14f, 0.14f, 0.05f);
			new Sphere().draw(1.5f, 10, 3);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the truck back left wheel
		GL11.glPushMatrix();
		{
			GL11.glPushAttrib(GL11.GL_COLOR_MATERIAL);
			// specular reflection of the front faces of the wheel
			float frontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the wheel using OpenGL
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(frontSpecular));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(moveTruck, -0.83f, -5.31f);
			GL11.glRotatef(truckWheelSpin, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(0.14f, 0.14f, 0.05f);
			new Sphere().draw(1.5f, 10, 3);

			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the fence
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);

			// change the geometry colour to grey if night or duskDawn is true
			// else make it white
			if (night || duskDawn) {
				GREY.submit();
			} else {
				Colour.WHITE.submit();
			}
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, fence.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f, -0.1f, -15.0f);
			GL11.glScalef(25.0f, 2.0f, 0f);

			//draw fence using its display list
			GL11.glCallList(fenceList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();
	}

	protected void setSceneCamera() {
		// call the default behaviour defined in GraphicsLab. This will set a default
		// perspective projection
		// and default camera settings ready for some custom camera positioning below...
		super.setSceneCamera();

		// TODO: If it is appropriate for your scene, modify the camera's position and
		// orientation here
		// using a call to GL11.gluLookAt(...)
	}

	protected void cleanupScene() {// TODO: Clean up your resources here
	}

	private void resetAnimations() {
		// TODO Auto-generated method stub
		// reset windmill spin
		windmillSpin = 0.0f;
		// reset scene rotation
		rotateScene = 0.0f;
		// reset move truck
		moveTruck = -2.8f;
		// reset truck wheel spin
		truckWheelSpin = 0f;
		// reset moon X
		moonX = 12.0f;
		// reset moon Y
		moonY = -1.8f;
		// reset sun X
		sunX = -12.0f;
		// reset sun Y
		sunY = -1.8f;
		// reset increase diffuse
		diffuseIncrease = 0.3f;
		// reset increase ambient
		ambientIncrease = 0.2f;
		// reset night
		night = true;
		// reset duskDawn
		duskDawn = false;
	}

	private void sunMoonCycle() {
		//moon rising y axis
		if ((sunX <= -10f || sunX == 12f) && sunY <= -1.0f) {
			if (moonY <= 6.0f && moonX >= 4.0f) {
				moonY += 0.02f * getAnimationScale(); // make moon rise in Y axis
				if (diffuseIncrease > 0.1 && ambientIncrease > 0.1) {
					// decrease lighting
					diffuseIncrease -= 0.0005f * getAnimationScale();
					ambientIncrease -= 0.0005f * getAnimationScale();
				}
			}

			if (moonX >= 4.0f) {
				moonX -= 0.02f * getAnimationScale(); // make moon go towards the left
			}

			if (moonX >= -10f && moonX <= 4.0f) {
				// make moon go towards the left without rising in Y axis
				moonX -= 0.02 * getAnimationScale();
			}

			if (moonY >= -1.0f && moonX <= -4f) {
				moonY -= 0.02 * getAnimationScale(); // make moon go down in Y axis
				if (diffuseIncrease < 0.5 && ambientIncrease < 0.5) {
					//increase lighting as moon goes down
					diffuseIncrease += 0.0005f * getAnimationScale();
					ambientIncrease += 0.0005f * getAnimationScale();
				}
			}
		}

		//if moon rising
		if (moonX < 12f && moonY > -1.0f) {
			night = true; // make it night
			duskDawn = false; // not dusk or dawn
			sunX = 12f; // set sun x to 12f
		}

		//sun rising y axis
		if ((moonX <= -10f || moonX == 12f) && moonY <= -1.0f) {
			if (sunY <= 6.0f && sunX >= 4.0f) {
				sunY += 0.02f * getAnimationScale();// make sun rise in Y axis
				if (diffuseIncrease < 1.5 && ambientIncrease < 1.5) {
					// increase lighting
					diffuseIncrease += 0.001f * getAnimationScale();
					ambientIncrease += 0.0005f * getAnimationScale();
				}
				duskDawn = true; // make it dawn as sun comes up
			}

			if (sunX >= 4.0f) {
				// make sun go towards the left
				sunX -= 0.02f * getAnimationScale();
			}

			if (sunX >= -10f && sunX <= 4.0f) {
				// make sun go towards the left without rising in y axis
				sunX -= 0.02 * getAnimationScale();
				// make it midday
				duskDawn = false;
			}

			if (sunY >= -1.0f && sunX <= -4f) {
				sunY -= 0.02 * getAnimationScale(); // make sun go down in Y axis
				if (diffuseIncrease > 0.3 && ambientIncrease > 0.3) {
					//decrease lighting as moon goes down
					diffuseIncrease -= 0.001f * getAnimationScale();
					ambientIncrease -= 0.0005f * getAnimationScale();
				}
				duskDawn = true; // make it dusk as soon goes down
			}
		}

		// if sun rising
		if (sunX < 12f && sunY > -1.0f) {
			night = false; // make night false, so it is day
			moonX = 12f; // set moon x to 12f
		}
	}

	private void sceneLight() {
		// the first light for the scene is white...
		float diffuse0[] = { diffuseIncrease, diffuseIncrease, diffuseIncrease, 1.0f };
		// ...with a dim ambient contribution...
		float ambient0[] = { ambientIncrease, ambientIncrease, ambientIncrease, 1.0f };
		// ...and is positioned above the viewpoint
		float position0[] = { 0.0f, 10.0f, 0.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, FloatBuffer.wrap(position0));
	}

	/**
	 * Draws a plane aligned with the X and Z axis, with its front face toward
	 * positive Y. The plane is of unit width and height, and uses the current
	 * OpenGL material settings for its appearance
	 */
	private void drawPlane() {
		//vertices for the plane
		Vertex v1 = new Vertex(-0.5f, 0.0f, -0.5f); // left, back
		Vertex v2 = new Vertex(0.5f, 0.0f, -0.5f); // right, back
		Vertex v3 = new Vertex(0.5f, 0.0f, 0.5f); // right, front
		Vertex v4 = new Vertex(-0.5f, 0.0f, 0.5f); // left, front

		// draw the plane geometry. order the vertices so that the plane faces up
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();

			GL11.glTexCoord2f(6.0f, 0.0f);
			v3.submit();

			GL11.glTexCoord2f(6.0f, 4.0f);
			v2.submit();

			GL11.glTexCoord2f(0.0f, 4.0f);
			v1.submit();
		}
		GL11.glEnd();

		// if the user is viewing an axis, then also draw this plane
		// using lines so that axis aligned planes can still be seen
		if (isViewingAxis()) {
			// also disable textures when drawing as lines
			// so that the lines can be seen more clearly
			GL11.glPushAttrib(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			{
				v3.submit();
				v2.submit();
				v1.submit();
			}
			GL11.glEnd();
			GL11.glPopAttrib();
		}
	}

	/**
	 * Draws a roof geometry of unit length, width and height aligned along the x
	 * axis. The roof uses the current OpenGL material settings for its appearance
	 */
	private void drawUnitRoof() {
		//vertices for the roof
		Vertex v1 = new Vertex(-0.5f, -0.5f, -0.5f);
		Vertex v2 = new Vertex(-0.5f, 0.5f, 0.0f);
		Vertex v3 = new Vertex(-0.5f, -0.5f, 0.5f);
		Vertex v4 = new Vertex(0.5f, -0.5f, -0.5f);
		Vertex v5 = new Vertex(0.5f, 0.5f, 0.0f);
		Vertex v6 = new Vertex(0.5f, -0.5f, 0.5f);

		// left gable
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			new Normal(v3.toVector(), v2.toVector(), v1.toVector()).submit();

			v3.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		// back slope
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v5.toVector(), v4.toVector()).submit();

			v1.submit();
			v2.submit();
			v5.submit();
			v4.submit();
		}
		GL11.glEnd();

		// front slope
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v2.toVector(), v3.toVector(), v6.toVector()).submit();

			v5.submit();
			v2.submit();
			v3.submit();
			v6.submit();
		}
		GL11.glEnd();

		// right gable
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			new Normal(v5.toVector(), v6.toVector(), v4.toVector()).submit();

			v5.submit();
			v6.submit();
			v4.submit();
		}
		GL11.glEnd();
	}

	/**
	 * Draws a cube of unit length, width and height using the current OpenGL
	 * material settings
	 */
	private void drawUnitCube() {
		// the vertices for the cube (note that all sides have a length of 1)
		Vertex v1 = new Vertex(-0.5f, -0.5f, 0.5f);
		Vertex v2 = new Vertex(-0.5f, 0.5f, 0.5f);
		Vertex v3 = new Vertex(0.5f, 0.5f, 0.5f);
		Vertex v4 = new Vertex(0.5f, -0.5f, 0.5f);
		Vertex v5 = new Vertex(-0.5f, -0.5f, -0.5f);
		Vertex v6 = new Vertex(-0.5f, 0.5f, -0.5f);
		Vertex v7 = new Vertex(0.5f, 0.5f, -0.5f);
		Vertex v8 = new Vertex(0.5f, -0.5f, -0.5f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v2.toVector(), v1.toVector(), v4.toVector()).submit();

			v3.submit();
			v2.submit();
			v1.submit();
			v4.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v6.toVector(), v5.toVector(), v1.toVector()).submit();

			v2.submit();
			v6.submit();
			v5.submit();
			v1.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v3.toVector(), v4.toVector(), v8.toVector()).submit();

			v7.submit();
			v3.submit();
			v4.submit();
			v8.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v6.toVector(), v2.toVector(), v3.toVector()).submit();

			v7.submit();
			v6.submit();
			v2.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v1.toVector(), v5.toVector(), v8.toVector()).submit();

			v4.submit();
			v1.submit();
			v5.submit();
			v8.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v7.toVector(), v8.toVector(), v5.toVector()).submit();

			v6.submit();
			v7.submit();
			v8.submit();
			v5.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitDoor() {
		//vertices for the door
		Vertex v1 = new Vertex(0.0f, -0.5f, 0.0f);
		Vertex v2 = new Vertex(0.0f, 0.1f, 0.0f);
		Vertex v3 = new Vertex(0.2f, 0.1f, 0.0f);
		Vertex v4 = new Vertex(0.2f, -0.5f, 0.0f);

		//door near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v2.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();
	}

	private void drawWindmillStand() {
		//vertices for the windmill stand
		Vertex v1 = new Vertex(0.0f, 0.0f, 0.6f);
		Vertex v2 = new Vertex(0.1f, 1.0f, 0.3f);
		Vertex v3 = new Vertex(0.2f, 0.0f, 0.6f);
		Vertex v4 = new Vertex(-0.2f, 0.2f, 0.0f);
		Vertex v5 = new Vertex(0.0f, 0.2f, 0.0f);
		Vertex v6 = new Vertex(0.2f, 0.2f, 0.0f);
		Vertex v7 = new Vertex(0.4f, 0.2f, 0.0f);

		//first stand near face
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			new Normal(v3.toVector(), v2.toVector(), v1.toVector()).submit();

			v3.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		//second stand near face
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			new Normal(v7.toVector(), v2.toVector(), v6.toVector()).submit();

			v7.submit();
			v2.submit();
			v6.submit();
		}
		GL11.glEnd();

		//third stand near face
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			new Normal(v5.toVector(), v2.toVector(), v4.toVector()).submit();

			v5.submit();
			v2.submit();
			v4.submit();
		}
		GL11.glEnd();
	}

	private void drawWindmillBlades() {
		//vertices for the windmill blades
		Vertex v1 = new Vertex(-0.1f, 0.1f, 0.0f);
		Vertex v2 = new Vertex(-0.3f, 0.5f, 0.2f);
		Vertex v3 = new Vertex(0.3f, 0.5f, 0.2f);
		Vertex v4 = new Vertex(0.1f, 0.1f, 0.0f);
		Vertex v5 = new Vertex(0.5f, 0.3f, 0.2f);
		Vertex v6 = new Vertex(0.5f, -0.3f, 0.2f);
		Vertex v7 = new Vertex(0.1f, -0.1f, 0.0f);
		Vertex v8 = new Vertex(0.3f, -0.5f, 0.2f);
		Vertex v9 = new Vertex(-0.3f, -0.5f, 0.2f);
		Vertex v10 = new Vertex(-0.1f, -0.1f, 0.0f);
		Vertex v11 = new Vertex(-0.5f, -0.3f, 0.2f);
		Vertex v12 = new Vertex(-0.5f, 0.3f, 0.2f);

		//first blade near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		//second blade near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v5.toVector(), v4.toVector(), v7.toVector()).submit();

			v6.submit();
			v5.submit();
			v4.submit();
			v7.submit();
		}
		GL11.glEnd();

		//third blade near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v10.toVector(), v9.toVector()).submit();

			v8.submit();
			v7.submit();
			v10.submit();
			v9.submit();
		}
		GL11.glEnd();
		
		//fourth blade near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v10.toVector(), v1.toVector(), v12.toVector(), v11.toVector()).submit();

			v10.submit();
			v1.submit();
			v12.submit();
			v11.submit();
		}
		GL11.glEnd();
		
		//middle near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v4.toVector(), v1.toVector(), v10.toVector()).submit();

			v7.submit();
			v4.submit();
			v1.submit();
			v10.submit();
		}
		GL11.glEnd();
	}

	private void drawTruckRoof() {
		//vertices for the truck roof
		Vertex v1 = new Vertex(-0.5f, -0.25f, 0.4f);
		Vertex v2 = new Vertex(-0.5f, 0.25f, 0.4f);
		Vertex v3 = new Vertex(0.2f, 0.25f, 0.4f);
		Vertex v4 = new Vertex(0.5f, -0.25f, 0.4f);
		Vertex v5 = new Vertex(-0.5f, -0.25f, -0.4f);
		Vertex v6 = new Vertex(-0.5f, 0.25f, -0.4f);
		Vertex v7 = new Vertex(0.2f, 0.25f, -0.4f);
		Vertex v8 = new Vertex(0.5f, -0.25f, -0.4f);

		//near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		//left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v6.toVector(), v5.toVector(), v1.toVector()).submit();

			v2.submit();
			v6.submit();
			v5.submit();
			v1.submit();
		}
		GL11.glEnd();

		//right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v3.toVector(), v4.toVector()).submit();

			v8.submit();
			v7.submit();
			v3.submit();
			v4.submit();
		}
		GL11.glEnd();

		//top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v7.toVector(), v6.toVector(), v2.toVector()).submit();

			v3.submit();
			v7.submit();
			v6.submit();
			v2.submit();
		}
		GL11.glEnd();

		//bottom face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v4.toVector(), v1.toVector(), v5.toVector()).submit();

			v8.submit();
			v4.submit();
			v1.submit();
			v5.submit();
		}
		GL11.glEnd();

		//far face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v6.toVector(), v7.toVector(), v8.toVector()).submit();

			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}

	private void drawTruckHood() {
		//vertices for the truck hood
		Vertex v1 = new Vertex(-0.5f, -0.25f, 0.4f);
		Vertex v2 = new Vertex(-0.5f, 0.25f, 0.4f);
		Vertex v3 = new Vertex(0.5f, 0.25f, 0.4f);
		Vertex v4 = new Vertex(0.5f, -0.25f, 0.4f);
		Vertex v5 = new Vertex(0.25f, -0.25f, 0.4f);
		Vertex v6 = new Vertex(0.25f, -0.05f, 0.4f);
		Vertex v7 = new Vertex(0.225f, 0f, 0.4f);
		Vertex v8 = new Vertex(-0.125f, 0f, 0.4f);
		Vertex v9 = new Vertex(-0.15f, -0.05f, 0.4f);
		Vertex v10 = new Vertex(-0.15f, -0.25f, 0.4f);
		Vertex v11 = new Vertex(0.5f, -0.25f, -0.4f);
		Vertex v12 = new Vertex(0.5f, 0.25f, -0.4f);
		Vertex v13 = new Vertex(-0.5f, 0.25f, -0.4f);
		Vertex v14 = new Vertex(-0.5f, -0.25f, -0.4f);
		Vertex v15 = new Vertex(-0.15f, -0.25f, -0.4f);
		Vertex v16 = new Vertex(-0.15f, -0.05f, -0.4f);
		Vertex v17 = new Vertex(-0.125f, 0f, -0.4f);
		Vertex v18 = new Vertex(0.225f, 0f, -0.4f);
		Vertex v19 = new Vertex(0.25f, -0.05f, -0.4f);
		Vertex v20 = new Vertex(0.25f, -0.25f, -0.4f);
		Vertex v21 = new Vertex(0.225f, 0.25f, 0.4f);
		Vertex v22 = new Vertex(-0.125f, 0.25f, 0.4f);
		Vertex v23 = new Vertex(0.225f, 0.25f, -0.4f);
		Vertex v24 = new Vertex(-0.125f, 0.25f, -0.4f);

		//near left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v1.toVector(), v10.toVector(), v9.toVector()).submit();
			v2.submit();
			v1.submit();
			v10.submit();
			v9.submit();
			v8.submit();
			v22.submit();

		}
		GL11.glEnd();

		//near middle face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v21.toVector(), v22.toVector(), v8.toVector()).submit();
			v7.submit();
			v21.submit();
			v22.submit();
			v8.submit();
		}
		GL11.glEnd();

		//near right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v21.toVector(), v7.toVector()).submit();
			v4.submit();
			v3.submit();
			v21.submit();
			v7.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		//left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v13.toVector(), v14.toVector()).submit();
			v1.submit();
			v2.submit();
			v13.submit();
			v14.submit();
		}
		GL11.glEnd();

		//right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v11.toVector(), v12.toVector(), v3.toVector(), v4.toVector()).submit();
			v11.submit();
			v12.submit();
			v3.submit();
			v4.submit();
		}
		GL11.glEnd();

		//top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v12.toVector(), v13.toVector(), v2.toVector()).submit();
			v3.submit();
			v12.submit();
			v13.submit();
			v2.submit();
		}
		GL11.glEnd();

		//far left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v12.toVector(), v11.toVector(), v20.toVector(), v19.toVector()).submit();
			v12.submit();
			v11.submit();
			v20.submit();
			v19.submit();
			v18.submit();
			v23.submit();
		}
		GL11.glEnd();

		//far middle face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v17.toVector(), v24.toVector(), v23.toVector(), v18.toVector()).submit();
			v17.submit();
			v24.submit();
			v23.submit();
			v18.submit();
		}
		GL11.glEnd();

		//far right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v14.toVector(), v13.toVector(), v24.toVector(), v17.toVector()).submit();
			v14.submit();
			v13.submit();
			v24.submit();
			v17.submit();
			v16.submit();
			v15.submit();
		}
		GL11.glEnd();

		//bottom left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v15.toVector(), v10.toVector(), v1.toVector(), v14.toVector()).submit();
			v15.submit();
			v10.submit();
			v1.submit();
			v14.submit();
		}
		GL11.glEnd();

		//wheel space left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v15.toVector(), v16.toVector(), v9.toVector(), v10.toVector()).submit();
			v15.submit();
			v16.submit();
			v9.submit();
			v10.submit();
		}
		GL11.glEnd();

		//wheel space angle left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v16.toVector(), v17.toVector(), v8.toVector(), v9.toVector()).submit();
			v16.submit();
			v17.submit();
			v8.submit();
			v9.submit();
		}
		GL11.glEnd();

		//wheel space top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v17.toVector(), v18.toVector(), v7.toVector(), v8.toVector()).submit();
			v17.submit();
			v18.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();

		//wheel space angle right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v7.toVector(), v18.toVector(), v19.toVector()).submit();
			v6.submit();
			v7.submit();
			v18.submit();
			v19.submit();
		}
		GL11.glEnd();

		//wheel space right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v6.toVector(), v19.toVector(), v20.toVector()).submit();
			v5.submit();
			v6.submit();
			v19.submit();
			v20.submit();
		}
		GL11.glEnd();

		//bottom right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v11.toVector(), v4.toVector(), v5.toVector(), v20.toVector()).submit();
			v11.submit();
			v4.submit();
			v5.submit();
			v20.submit();
		}
		GL11.glEnd();

	}

	private void drawTruckBack() {
		//vertices for the truck back
		Vertex v1 = new Vertex(-0.5f, -0.25f, 0.4f);
		Vertex v2 = new Vertex(-0.5f, 0f, 0.4f);
		Vertex v3 = new Vertex(-0.5f, 0.25f, 0.4f);
		Vertex v4 = new Vertex(0.5f, 0.25f, 0.4f);
		Vertex v5 = new Vertex(0.5f, 0f, 0.4f);
		Vertex v6 = new Vertex(0.5f, -0.25f, 0.4f);
		Vertex v7 = new Vertex(0.2f, -0.25f, 0.4f);
		Vertex v8 = new Vertex(0.2f, -0.05f, 0.4f);
		Vertex v9 = new Vertex(0.175f, 0f, 0.4f);
		Vertex v10 = new Vertex(-0.175f, 0f, 0.4f);
		Vertex v11 = new Vertex(-0.2f, -0.05f, 0.4f);
		Vertex v12 = new Vertex(-0.2f, -0.25f, 0.4f);
		Vertex v13 = new Vertex(0.5f, -0.25f, -0.4f);
		Vertex v14 = new Vertex(0.5f, 0f, -0.4f);
		Vertex v15 = new Vertex(0.5f, 0.25f, -0.4f);
		Vertex v16 = new Vertex(-0.5f, 0.25f, -0.4f);
		Vertex v17 = new Vertex(-0.5f, 0f, -0.4f);
		Vertex v18 = new Vertex(-0.5f, -0.25f, -0.4f);
		Vertex v19 = new Vertex(-0.2f, -0.25f, -0.4f);
		Vertex v20 = new Vertex(-0.2f, -0.05f, -0.4f);
		Vertex v21 = new Vertex(-0.175f, 0f, -0.4f);
		Vertex v22 = new Vertex(0.175f, 0f, -0.4f);
		Vertex v23 = new Vertex(0.2f, -0.05f, -0.4f);
		Vertex v24 = new Vertex(0.2f, -0.25f, -0.4f);

		//left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v3.toVector(), v16.toVector()).submit();
			v1.submit();
			v2.submit();
			v3.submit();
			v16.submit();
			v17.submit();
			v18.submit();

		}
		GL11.glEnd();

		//right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v13.toVector(), v14.toVector(), v15.toVector(), v4.toVector()).submit();
			v13.submit();
			v14.submit();
			v15.submit();
			v4.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		//inner top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v14.toVector(), v17.toVector(), v2.toVector()).submit();
			v5.submit();
			v14.submit();
			v17.submit();
			v2.submit();
		}
		GL11.glEnd();

		//inner near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v3.toVector(), v4.toVector(), v5.toVector()).submit();
			v2.submit();
			v3.submit();
			v4.submit();
			v5.submit();
		}
		GL11.glEnd();

		//inner far face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v14.toVector(), v15.toVector(), v16.toVector(), v17.toVector()).submit();
			v14.submit();
			v15.submit();
			v16.submit();
			v17.submit();
		}
		GL11.glEnd();

		//inner left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v17.toVector(), v16.toVector(), v3.toVector(), v2.toVector()).submit();
			v17.submit();
			v16.submit();
			v3.submit();
			v2.submit();
		}
		GL11.glEnd();

		//inner right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v4.toVector(), v15.toVector(), v14.toVector()).submit();
			v5.submit();
			v4.submit();
			v15.submit();
			v14.submit();
		}
		GL11.glEnd();

		//near top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v4.toVector(), v3.toVector(), v2.toVector()).submit();
			v5.submit();
			v4.submit();
			v3.submit();
			v2.submit();
		}
		GL11.glEnd();

		//near bottom left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v1.toVector(), v12.toVector(), v11.toVector()).submit();
			v2.submit();
			v1.submit();
			v12.submit();
			v11.submit();
			v10.submit();
		}
		GL11.glEnd();

		//near bottom right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v5.toVector(), v9.toVector(), v8.toVector()).submit();
			v6.submit();
			v5.submit();
			v9.submit();
			v8.submit();
			v7.submit();
		}
		GL11.glEnd();

		//far top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v17.toVector(), v16.toVector(), v15.toVector(), v14.toVector()).submit();
			v17.submit();
			v16.submit();
			v15.submit();
			v14.submit();
		}
		GL11.glEnd();

		//far bottom right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v18.toVector(), v17.toVector(), v21.toVector(), v20.toVector()).submit();
			v18.submit();
			v17.submit();
			v21.submit();
			v20.submit();
			v19.submit();
		}
		GL11.glEnd();

		//far bottom left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v14.toVector(), v13.toVector(), v24.toVector(), v23.toVector()).submit();
			v14.submit();
			v13.submit();
			v24.submit();
			v23.submit();
			v22.submit();
		}
		GL11.glEnd();

		//bottom left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v19.toVector(), v12.toVector(), v1.toVector(), v18.toVector()).submit();
			v19.submit();
			v12.submit();
			v1.submit();
			v18.submit();
		}
		GL11.glEnd();

		//wheel space left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v19.toVector(), v20.toVector(), v11.toVector(), v12.toVector()).submit();
			v19.submit();
			v20.submit();
			v11.submit();
			v12.submit();
		}
		GL11.glEnd();

		//wheel space angle left face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v20.toVector(), v21.toVector(), v10.toVector(), v11.toVector()).submit();
			v20.submit();
			v21.submit();
			v10.submit();
			v11.submit();
		}
		GL11.glEnd();

		//wheel space top face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v22.toVector(), v9.toVector(), v10.toVector(), v21.toVector()).submit();
			v22.submit();
			v9.submit();
			v10.submit();
			v21.submit();
		}
		GL11.glEnd();

		//wheel space angle right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v9.toVector(), v22.toVector(), v23.toVector()).submit();
			v8.submit();
			v9.submit();
			v22.submit();
			v23.submit();
		}
		GL11.glEnd();

		//wheel space right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v8.toVector(), v23.toVector(), v24.toVector()).submit();
			v7.submit();
			v8.submit();
			v23.submit();
			v24.submit();
		}
		GL11.glEnd();

		//bottom right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v13.toVector(), v6.toVector(), v7.toVector(), v24.toVector()).submit();
			v13.submit();
			v6.submit();
			v7.submit();
			v24.submit();
		}
		GL11.glEnd();
	}

	/**
	 * Draws a plane aligned with the X and Z axis, with its front face toward
	 * positive Y. The plane is of unit width and height, and uses the current
	 * OpenGL material settings for its appearance
	 */
	private void drawFence() {
		//vertices of the fence
		Vertex v1 = new Vertex(-0.5f, -0.5f, 0.0f); // left, back
		Vertex v2 = new Vertex(-0.5f, 0.5f, 0.0f); // right, back
		Vertex v3 = new Vertex(0.5f, 0.5f, 0.0f); // right, front
		Vertex v4 = new Vertex(0.5f, -0.5f, 0.0f); // left, front

		// draw the plane geometry. order the vertices so that the plane faces up
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();

			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();

			GL11.glTexCoord2f(1.0f, 8.0f);
			v2.submit();

			GL11.glTexCoord2f(0.0f, 8.0f);
			v1.submit();
		}
		GL11.glEnd();

		// if the user is viewing an axis, then also draw this plane
		// using lines so that axis aligned planes can still be seen
		if (isViewingAxis()) {
			// also disable textures when drawing as lines
			// so that the lines can be seen more clearly
			GL11.glPushAttrib(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			{
				v3.submit();
				v2.submit();
				v1.submit();
			}
			GL11.glEnd();
			GL11.glPopAttrib();
		}
	}

	private void drawTruckWindows() {
		//vertices of the truck windows
		Vertex v1 = new Vertex(-0.4f, -0.2f, 0.4001f);
		Vertex v2 = new Vertex(-0.4f, 0.15f, 0.4001f);
		Vertex v3 = new Vertex(0.15f, 0.15f, 0.4001f);
		Vertex v4 = new Vertex(0.34f, -0.2f, 0.4001f);
		Vertex v5 = new Vertex(-0.4f, -0.2f, -0.4001f);
		Vertex v6 = new Vertex(-0.4f, 0.15f, -0.4001f);
		Vertex v7 = new Vertex(0.15f, 0.15f, -0.4001f);
		Vertex v8 = new Vertex(0.34f, -0.2f, -0.4001f);
		Vertex v9 = new Vertex(0.47f, -0.2f, -0.3f);
		Vertex v10 = new Vertex(0.27f, 0.15f, -0.3f);
		Vertex v11 = new Vertex(0.27f, 0.15f, 0.3f);
		Vertex v12 = new Vertex(0.47f, -0.2f, 0.3f);
		
		//near face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();
			
			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();
		
		//right face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v9.toVector(), v10.toVector(), v11.toVector(), v12.toVector()).submit();
			
			v9.submit();
			v10.submit();
			v11.submit();
			v12.submit();
		}
		GL11.glEnd();
		
		//far face
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v5.toVector(), v6.toVector(), v7.toVector(), v8.toVector()).submit();
			
			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}
}
