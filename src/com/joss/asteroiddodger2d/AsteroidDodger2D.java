package com.joss.asteroiddodger2d;

/* Written to display the different working parts of a simple 2d game
 * in Java using LWJGL.
 * Different domains are left coupled in the 'logic' method--
 * a more complex architecture would be overkill for this simple game.
 * 
 * Objective of the Game:
 * Keep the rectangle on the platform while dodging 'asteroids' that fall from above.
 * You have three lives, and each life can withstand five asteroid collisions 
 * 
 * Written By:
 * Tristan Moss
 * 
 * Game Idea By:
 * Drew */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.joss.asteroiddodger2d.glwrappers.DisplayGL;
import com.joss.asteroiddodger2d.glwrappers.InputGL;
import com.joss.asteroiddodger2d.glwrappers.RenderGL;

public class AsteroidDodger2D {

	public static void main(String[] args) {
		new AsteroidDodger2D();
	}
	
	AsteroidDodger2D() {
		setup();
		loop();
		close();
	}
	
	private void setup() {
		// Create the display, setup player & ground, setup life icons
		DisplayGL.createDisplay(700, 700, "Fire in the Sky");
		setupGameObjects();
		setupHUD();
	}
	
	private GameObject ground, player;
	
	private List<GameObject> asteroids = new ArrayList<GameObject>();
	
	// Define initial colors for types of game objects
	private float[] groundColor = new float[] {0.8f, 0.6f, 0.2f, 1f};
	private float[] playerColor = new float[] {0f, 1f, 0f, 1f};
	private float[] asteroidColor = new float[] {1f, 0f, 0f, 1f};
	
	private void setupGameObjects() {
		// Setup player and ground game-objects
		// new GameObject( x, y, width, height, color, isTriangle? )
		ground = new GameObject(0f, -0.725f, 1.5f, 0.035f, groundColor, false);
		player = new GameObject(0f, -0.5f, 0.3f, 0.3f, playerColor, false);
	}
	
	private List<GameObject> livesHUD = new ArrayList<GameObject>();

	private float[] livesHUDColor = new float[] {0f, 1f, 0f, 1f};
	
	private void setupHUD() {
		// Position life icons on the top-right of the screen
		float lifeIconX = 0.8f;
		for(int i = 0; i < 3; i++) {
			livesHUD.add(new GameObject(lifeIconX, 0.8f, 0.1f, 0.1f, livesHUDColor, false));
			lifeIconX -= 0.2f;
		}	
	}
	
	private void loop() {
		while(!DisplayGL.isCloseReq()) {
			// Record raw keyboard input, process game logic, draw objects onto screen
			InputGL.record();
			logic();
			render();
		}
	}
	
	// Defines the player's direction
	private boolean isMovingLeft = true;
	
	// Speed variables are initialized to make tweaking the game easier
	private float asteroidSpeed = 0.0175f;
	private float playerSpeed = 0.0125f;
	
	// Used to determine how often asteroids are spawned
	private float asteroidTimer = 0f;
	
	private int lives = 3;
	private int score = 0;
	
	private void logic() {
		/* The logic method performs various game/game-object specific operations,
		 * and prepares all visible game-objects to be rendered on the screen
		 * by adding them to the render list */
		renderList.add(ground);
		for(GameObject lifeIcon : livesHUD) {
			renderList.add(lifeIcon);
		}
		playerLogic();
		asteroidLogic();
	}
	
	private void playerLogic() {
		/* Handles the player direction, speed, and potential to fall,
		 * also handles some aspects of scoring/life depletion */
		if(player.rightBound > ground.leftBound && player.leftBound < ground.rightBound) {
			if(InputGL.downKeys[InputGL.KEY_SPACE]) {
			isMovingLeft = !isMovingLeft;
			}
			if(isMovingLeft) {
				player.addToX(-playerSpeed);
			} else {
				player.addToX(playerSpeed);
			}
		} else {
			if(player.getY() < -1.5f) {
				score -= 100;
				System.out.println("Player Score: " + score);
				lives--;
				if(lives < 0) {
					lives = 3;
					setupHUD();
				} else {
					livesHUD.remove(lives);
					System.out.println("Lives: " + lives);
				}
				if(player.getColor()[0] > 0f) {
					player.setColor(new float[] {0f, 1f, 0f, 1f});	
				}
				player.setX(0f);
				player.setY(-0.5f);
				asteroids.clear();
			} else {
				player.addToY(-0.005f);
			}
		}
		renderList.add(player);
	}
	
	private void asteroidLogic() {
		/* Handles the creation, deletion and movement of 'asteroids'
		 * also handles asteroid/player collision
		 * handles some scoring/life depletion */
		List<GameObject> asteroidRemove = new ArrayList<GameObject>();
		for(GameObject asteroid : asteroids) {
			if(asteroid.getY() < -1.2f) {
				score += 50;
				System.out.println("Player Score: " + score);
				asteroidRemove.add(asteroid);
			} else {
				if(asteroid.bottomBound <= player.topBound
						&& asteroid.topBound >= player.bottomBound
						&& asteroid.leftBound <= player.rightBound
						&& asteroid.rightBound >= player.leftBound) {
					System.out.println("player hit");
					if(player.getColor()[0] >= 1f) {
						score -= 100;
						lives--;
						if(lives < 0) {
							lives = 3;
							setupHUD();
						} else {
							livesHUD.remove(lives);
							System.out.println("Lives: " + lives);
						}
						player.setColor(new float[] {0f, 1f, 0f, 1f});
						player.setX(0f);
						for(GameObject a : asteroids)
							asteroidRemove.add(a);
					} else {
						score -= 25;
						player.setColor(new float[] 
							{player.getColor()[0] + 0.25f,
							player.getColor()[1] - 0.25f,
							player.getColor()[2],
							player.getColor()[3]});
						if(lives > 0) {
							livesHUD.get(lives - 1).setColor(new float[] 
								{livesHUD.get(lives - 1).getColor()[0] + 0.25f,
								livesHUD.get(lives - 1).getColor()[1] - 0.25f,
								livesHUD.get(lives - 1).getColor()[2],
								livesHUD.get(lives -  1).getColor()[3]});
						}
					}
					System.out.println("Player Score: " + score);
					asteroidRemove.add(asteroid);
				} else {
					asteroid.addToY(-asteroidSpeed);
					renderList.add(asteroid);
				}
			}
		}
		
		// Determine when asteroids are spawned
		if(asteroidTimer == 70) {
			Random rand = new Random();
			float x;
			if(rand.nextFloat() < 0.5f) {
				x = -rand.nextFloat();
			} else {
				x = rand.nextFloat();
			}
			GameObject asteroid = new GameObject(x, 0.8f, 0.2f, 0.2f, asteroidColor, true);
			asteroids.add(asteroid);
			asteroidTimer = 0;
		} else {
			asteroidTimer++;
		}
		
		// Delete asteroids that were gathered on a list over the last operations
		for(GameObject removeItem : asteroidRemove) {
			asteroids.remove(removeItem);
		}
	}
	
	private List<GameObject> renderList = new ArrayList<GameObject>();
	
	private void render() {
		// Render all items on the renderList
		for(GameObject gameObj : renderList) {
			RenderGL.changeColor(gameObj.getColor());
			if(gameObj.getIsTri()) {
				RenderGL.renderIMTri(gameObj.getRenderInfo());
			} else {
				RenderGL.renderIMQuad(gameObj.getRenderInfo());
			}
		}
		// Update the LWJGL context
		DisplayGL.updateDisplay();
		DisplayGL.syncDisplay(60);
		// Clear the render list and clear the LWJGL context
		renderList.clear();
		RenderGL.clear();
	}
	
	private void close() {
		DisplayGL.deleteDisplay();
	}

}
