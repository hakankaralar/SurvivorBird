package com.hakankaralar.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	int gamestate = 0;
	float velocity = 0;
	float gravity = 0.7f;
	float enemyVelocity = 4;
	Random random ;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle birdCircle;

	ShapeRenderer shapeRenderer;



	int numberofEnemies = 4;
	float[] enemyX = new float[numberofEnemies];
	float[] enemyOffSet = new float[numberofEnemies];
	float[] enemyOffSet2 = new float[numberofEnemies];
	float[] enemyOffSet3 = new float[numberofEnemies];
	float distance = 0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;


	@Override
	public void create () {

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberofEnemies];
		enemyCircles2 = new Circle[numberofEnemies];
		enemyCircles3 = new Circle[numberofEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);


		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);



		for (int i = 0; i < numberofEnemies; i++) {

			enemyOffSet[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;

			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gamestate == 1){

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2){
				score++;

				if(scoredEnemy < numberofEnemies - 1){
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){

				velocity = -12;
			}


			for (int i = 0; i < numberofEnemies; i++) {

				if(enemyX[i] < 0){
					enemyX[i] = enemyX[i] +numberofEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 100;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 100;
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 100;

				}else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight() /10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight() /10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight() /10);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);



			}

			if(birdY > 0){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else {
				gamestate = 2;
			}

		}else if (gamestate == 0){
			if(Gdx.input.justTouched()){
				gamestate = 1;
			}
		} else if (gamestate == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!" ,450,650);

			if(Gdx.input.justTouched()){
				gamestate = 1;

				birdY = Gdx.graphics.getHeight()/2;

				for (int i = 0; i < numberofEnemies; i++) {

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() -100;

					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}
				velocity = 0;
				score = 0;
				scoredEnemy = 0;


			}

		}

		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),100,200);


		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30 ,birdY + Gdx.graphics.getHeight() / 20 ,Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x + Gdx.graphics.getWidth() / 30 ,birdCircle.y + Gdx.graphics.getHeight() / 20,birdCircle.radius);

		for (int i = 0; i < numberofEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(birdCircle,enemyCircles[i] ) ||Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])){

				gamestate = 2;
			}
		}
		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
