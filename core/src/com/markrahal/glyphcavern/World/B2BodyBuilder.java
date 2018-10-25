package com.markrahal.glyphcavern.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.markrahal.glyphcavern.GlyphCavern;

public class B2BodyBuilder {
    public B2BodyBuilder(World world, TiledMap map) {
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        //creates ground
        for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getY() + rect.getHeight() / 2) / GlyphCavern.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getHeight() / 2) / GlyphCavern.PPM);
            fDef.shape = shape;
            //fDef.filter.categoryBits = 0x0000;
            //fDef.filter.maskBits = 0x0002;
            body.createFixture(fDef);
        }
        //creates platforms
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getY() + rect.getHeight() / 2) / GlyphCavern.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getHeight() / 2) / GlyphCavern.PPM);
            fDef.shape = shape;
            //fDef.filter.categoryBits = 0x0000;
            //fDef.filter.maskBits = 0x0002;
            body.createFixture(fDef);
        }
        //creates interactable hitboxes
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getY() + rect.getHeight() / 2) / GlyphCavern.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / GlyphCavern.PPM, (rect.getHeight() / 2) / GlyphCavern.PPM);
            fDef.shape = shape;
            fDef.isSensor = true;
            fDef.filter.categoryBits = 0x0001;
            fDef.filter.maskBits = 0x0003;
            body.createFixture(fDef);
        }
    }
}
