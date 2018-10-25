package com.markrahal.glyphcavern.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.markrahal.glyphcavern.GlyphCavern;
import com.markrahal.glyphcavern.Sprites.Player;
import com.markrahal.glyphcavern.World.B2BodyBuilder;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by mark on 10/24/2017.
 */

public class PlayScreen implements Screen {
    private GlyphCavern game;
    private FitViewport port;
    private OrthographicCamera cam;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    private RayHandler rayHandler;
    private ConeLight light;

    private boolean inHitbox;
    private double time;

    public PlayScreen(GlyphCavern game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        port = new FitViewport(GlyphCavern.WIDTH / GlyphCavern.PPM, GlyphCavern.HEIGHT / GlyphCavern.PPM, cam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("C:\\Users\\mark\\Desktop\\Glyph Cavern\\android\\assets\\map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GlyphCavern.PPM);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -15), true);
        b2dr = new Box2DDebugRenderer();
        player = new Player(world);
        B2BodyBuilder b2BodyBuilder = new B2BodyBuilder(world, map);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(.4f);
        //light = new ConeLight(rayHandler, 100, Color.valueOf("ffffb3"), 200 / GlyphCavern.PPM, 0, 0, 0, 180);
        //light.setSoftnessLength(.6f);
        //light.setSoft(true);
        //light.setContactFilter((short) 0x0001, (short) 0x0001, (short) 0x0003);
        //light.attachToBody(player.b2Body, player.b2Body.getWorldCenter().x / 2, player.b2Body.getWorldCenter().y / 2);
        inHitbox = false;
        time = 0.0;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                inHitbox = true;
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                inHitbox = false;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private void handleInput(float dt) {
        if (input.isKeyPressed(Input.Keys.A) && player.b2Body.getLinearVelocity().x >= -2) {
            player.b2Body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2Body.getWorldCenter(), true);
        }
        if (input.isKeyPressed(Input.Keys.D) && player.b2Body.getLinearVelocity().x <= 2) {
            player.b2Body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2Body.getWorldCenter(), true);
        }
        if (input.isKeyJustPressed(Input.Keys.SPACE) && player.b2Body.getLinearVelocity().y == 0) {
            player.b2Body.applyLinearImpulse(new Vector2(0, 6.8f), player.b2Body.getWorldCenter(), true);
        }
        if (inHitbox && input.isKeyPressed(Input.Keys.E)) {
            time += dt;
            if (time >= 3000.0) {
                time = 0.0;
                Gdx.app.log("Key held for", "three seconds");
            }
        }
    }

    private void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        if (player.b2Body.getPosition().x > cam.viewportWidth / 2) {
            cam.position.x = player.b2Body.getPosition().x;
        }
        if (player.b2Body.getPosition().y < 0) {
            game.setScreen(new PlayScreen(game));
        }
        cam.update();
        renderer.setView(cam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, cam.combined);
        rayHandler.setCombinedMatrix(cam.combined);
        rayHandler.updateAndRender();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        rayHandler.dispose();
    }
}
