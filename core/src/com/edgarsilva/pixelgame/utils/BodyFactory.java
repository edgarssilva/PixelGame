package com.edgarsilva.pixelgame.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.edgarsilva.pixelgame.ashley.systems.RenderSystem;

public class BodyFactory {

    public static Body makeBox(World world, float x, float y, float width, float height, boolean isSensor){
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (isSensor) bodyDef.gravityScale = 0;
        bodyDef.position.set(x - (width * RenderSystem.PIXELS_TO_METRES),y - (height* RenderSystem.PIXELS_TO_METRES));
        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width * RenderSystem.PIXELS_TO_METRES ,height * RenderSystem.PIXELS_TO_METRES);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = box;
        fdef.isSensor = isSensor;

        body.createFixture(fdef);
        box.dispose();
        return body;
    }
}
