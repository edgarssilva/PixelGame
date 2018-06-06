package com.edgarsilva.pixelgame.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.edgarsilva.pixelgame.ashley.components.AnimationComponent;
import com.edgarsilva.pixelgame.ashley.components.B2dBodyComponent;
import com.edgarsilva.pixelgame.ashley.components.CollisionComponent;
import com.edgarsilva.pixelgame.ashley.components.PlayerComponent;
import com.edgarsilva.pixelgame.ashley.components.StateComponent;
import com.edgarsilva.pixelgame.ashley.components.TextureComponent;
import com.edgarsilva.pixelgame.ashley.components.TransformComponent;
import com.edgarsilva.pixelgame.ashley.components.TypeComponent;

public class EntitiesFactory {

    private static PooledEngine engine;

    public EntitiesFactory(PooledEngine engine) {
        this.engine = engine;
    }

    public static void createPlayer(World world, float x, float y, float width, float height){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        // create the data for the components and add them to the components
        b2dbody.body = BodyFactory.makeBox(world, x, y, width/4, height/2.5f, false);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(x, y,0);
        position.width = width;
        position.height = height;
        Texture source = new Texture("sprites/characters.png");

        TextureRegion[] regions = new TextureRegion().split(source,32,32)[1];
        TextureRegion[] walkAnimation  = {regions[0],regions[1],regions[2],regions[3]};
        TextureRegion[] JumpAnimaiton  = {regions[4],regions[5],regions[6]};
        TextureRegion[] HitAnimation   = {regions[8],regions[9],regions[8]};
        TextureRegion[] SlashAnimation = {regions[10],regions[11],regions[12]};
        TextureRegion[] PunchAnimation = {regions[13],regions[11]};
        TextureRegion[] RunAnimation   = {regions[14],regions[15],regions[16],regions[17]};
        TextureRegion[] ClimbAnimation = {regions[18],regions[19],regions[20],regions[21]};

        animation.animations.put(StateComponent.STATE_NORMAL,new Animation<TextureRegion>(1 / 8f, regions[0]));
        animation.animations.put(StateComponent.STATE_MOVING,new Animation<TextureRegion>(1 / 8f, walkAnimation));
        animation.animations.put(StateComponent.STATE_JUMPING,new Animation<TextureRegion>(1 / 8f,JumpAnimaiton));
        //animation.animations.put(StateComponent.STATE_FALLING,new Animation<TextureRegion>(1 / 8f, ClimbAnimation));
        animation.animations.put(StateComponent.STATE_HIT,new Animation<TextureRegion>(1 / 8f, HitAnimation));

        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        b2dbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(animation);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        // add the entity to the engine
        engine.addEntity(entity);

    }
}
