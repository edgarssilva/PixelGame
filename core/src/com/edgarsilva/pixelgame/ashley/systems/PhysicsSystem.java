package com.edgarsilva.pixelgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.edgarsilva.pixelgame.ashley.components.B2dBodyComponent;
import com.edgarsilva.pixelgame.ashley.components.PlayerComponent;
import com.edgarsilva.pixelgame.ashley.components.TransformComponent;
import com.edgarsilva.pixelgame.screens.PlayScreen;

public class PhysicsSystem extends IteratingSystem {

    private World world;
    private OrthographicCamera camera;

    private ComponentMapper<B2dBodyComponent> bm;
    private ComponentMapper<TransformComponent> tm;

    public PhysicsSystem(World world,OrthographicCamera cam) {
        super(Family.all(B2dBodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.camera = cam;
        bm = ComponentMapper.getFor(B2dBodyComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(deltaTime, 6, 2);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);
        B2dBodyComponent bodyComp = bm.get(entity);
        Vector2 position = bodyComp.body.getPosition();
        tfm.position.x = position.x + tfm.width * RenderSystem.PIXELS_TO_METRES/2;
        tfm.position.y = position.y + tfm.height * RenderSystem.PIXELS_TO_METRES/1.75f;
        tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;

        if (entity.getComponent(PlayerComponent.class) != null) {
            camera.position.x = MathUtils.lerp(camera.position.x,tfm.position.x,0.05f);
            camera.position.y = MathUtils.lerp(camera.position.y,tfm.position.y + RenderSystem.FRUSTUM_HEIGHT/18,0.05f) ;

            if (tfm.position.y < 0) {
                PlayerComponent pc = entity.getComponent(PlayerComponent.class);
                if (pc != null) {
                    pc.alive = false;
                }
            }
        }



    }
}
