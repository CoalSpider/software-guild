package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends ApplicationAdapter {

    // IDEAS: ninja game!
    // Can throw shurikens to make platforms (horizontally?)
    // Lets start with that before moving onto platforms
    SpriteBatch batch;
    Texture img;

    World world;
    Body player;
    Body groundB;
    Body leftWall;

    Box2DDebugRenderer renderer;

    Matrix4 debugMatrix = new Matrix4();
    OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        world = new World(new Vector2(0, -10), true);

        renderer = new Box2DDebugRenderer();
        renderer.setDrawAABBs(false);
        renderer.setDrawBodies(true);
        renderer.setDrawContacts(true);
        renderer.setDrawInactiveBodies(true);
        renderer.setDrawJoints(true);
        renderer.setDrawVelocities(false);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        // CREATE GROUND
        BodyDef ground = new BodyDef();
        ground.type = BodyType.StaticBody;
        ground.fixedRotation = true;
        ground.position.set(3f, 0.5f);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(3f, 0.5f);

        groundB = world.createBody(ground);
        groundB.createFixture(groundShape, 0);

        // CREATE LEFT WALL
        ground.position.set(0.5f, 3f);
        groundShape.setAsBox(0.5f, 3f);

        leftWall = world.createBody(ground);
        leftWall.createFixture(groundShape, 0);

        // CREATE PLAYER
        groundShape.setAsBox(0.5f, 0.5f);
        ground.position.set(2, 2);
        ground.type = BodyType.DynamicBody;

        player = world.createBody(ground);
        player.createFixture(groundShape, 1);

        groundShape.dispose();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                // comment out line to enable hacky wall jumping
                // with the wall code this also makes them "run up" the wall a bit
                // should be smoother once we do variable jumping
                // TODO: check for statics instead of just ground
                
             //   if (a.equals(player) && b.equals(groundB) || b.equals(player) && a.equals(groundB)) {
                    canJump = true;
            //    }
                // were gonna have to add a friction top to walls or yoll slide around on top of them
                if (a.equals(player) && b.equals(leftWall) || b.equals(player) && a.equals(leftWall)) {
                    agianstWall = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if (a.equals(player) && b.equals(leftWall) || b.equals(player) && a.equals(leftWall)) {
                    agianstWall = false;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // empty
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // empty
            }

        });
    }

    private boolean canJump = false;
    private boolean agianstWall = false;

    private void checkInput() {
        float vX = player.getLinearVelocity().x;
        float vY = player.getLinearVelocity().y;
        if (canJump && Gdx.input.isKeyPressed(Keys.W)) {
            vY = 5f;
            canJump = false;
        }
        
        if (agianstWall==false && Gdx.input.isKeyPressed(Keys.A)) {
            vX = -2f;
        }
        
        if (Gdx.input.isKeyPressed(Keys.D)) {
            vX = 2f;
        }
        
        if(Gdx.input.isKeyPressed(Keys.Q)){
            throwShuriken();
        }
        player.setLinearVelocity(vX, vY);
        player.setAwake(true);
    }
    
    /** player can throw shurikens that can be jumped on. In this way the player can sort of make there own platforms **/
    private void throwShuriken(){
        
    }

    @Override
    public void render() {
        checkInput();
        world.step(1 / 60f, 8, 5);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        debugMatrix.set(camera.combined);
        debugMatrix.scale(50, 50, 50);
        //    batch.begin();
        //    batch.draw(img, 0, 0);
        //    batch.end();
        renderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
