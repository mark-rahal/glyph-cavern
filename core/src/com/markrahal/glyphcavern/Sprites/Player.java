package com.markrahal.glyphcavern.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.markrahal.glyphcavern.GlyphCavern;

public class Player {

    private World world;
    public Body b2Body;

    private Vector2[] vertices;
    private PolygonShape playerBounds;

    public Player(World world) {
        this.world = world;
        vertices = new Vector2[4];

        vertices[0] = new Vector2(0, 0);
        vertices[1] = new Vector2(32 / GlyphCavern.PPM, 0);
        vertices[2] = new Vector2(32 / GlyphCavern.PPM, 64 / GlyphCavern.PPM);
        vertices[3] = new Vector2(0, 64 / GlyphCavern.PPM);

        playerBounds = new PolygonShape();
        playerBounds.set(vertices);

        BodyDef bDef = new BodyDef();
        bDef.position.set(new Vector2(32 / GlyphCavern.PPM, 64 / GlyphCavern.PPM));
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = playerBounds;
        //fDef.filter.categoryBits = 0x0000;
        //fDef.filter.maskBits = 0x0002;
        b2Body.createFixture(fDef);
    }
}
