package com.edgarsilva.pixelgame.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.edgarsilva.pixelgame.PixelGame;
import com.edgarsilva.pixelgame.ashley.components.AnimationComponent;
import com.edgarsilva.pixelgame.ashley.components.B2dBodyComponent;
import com.edgarsilva.pixelgame.ashley.components.StateComponent;
import com.edgarsilva.pixelgame.ashley.components.TextureComponent;
import com.edgarsilva.pixelgame.ashley.components.TransformComponent;
import com.edgarsilva.pixelgame.ashley.systems.AnimationSystem;
import com.edgarsilva.pixelgame.ashley.systems.AttackSystem;
import com.edgarsilva.pixelgame.ashley.systems.CollisionSystem;
import com.edgarsilva.pixelgame.ashley.systems.GameOverSystem;
import com.edgarsilva.pixelgame.ashley.systems.PhysicsDebugSystem;
import com.edgarsilva.pixelgame.ashley.systems.PhysicsSystem;
import com.edgarsilva.pixelgame.ashley.systems.PlayerControlSystem;
import com.edgarsilva.pixelgame.ashley.systems.RenderSystem;
import com.edgarsilva.pixelgame.utils.B2dContactListener;
import com.edgarsilva.pixelgame.utils.BodyFactory;
import com.edgarsilva.pixelgame.utils.EntitiesFactory;
import com.edgarsilva.pixelgame.utils.KeyboardController;
import com.edgarsilva.pixelgame.utils.LevelGenerator;

public class PlayScreen implements Screen {

    private PixelGame game;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private World world;

    private PooledEngine engine;

    //Tiled Map
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private LevelGenerator levelCollision;

    private KeyboardController controller;

    public PlayScreen(PixelGame game) {
        this.game = game;
        batch = new SpriteBatch();
        controller = new KeyboardController();
        Gdx.input.setInputProcessor(controller);
        world = new World(new Vector2(0,-9.6f),true);
        world.setContactListener(new B2dContactListener());

        RenderSystem renderSystem = new RenderSystem(batch);

        camera = renderSystem.getCam();

        batch.setProjectionMatrix(camera.combined);

        //Engine
        engine = new PooledEngine();

        //Engine Systems
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderSystem);
        engine.addSystem(new PhysicsSystem(world,renderSystem.getCam()));
        engine.addSystem(new PhysicsDebugSystem(world, renderSystem.getCam()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
        engine.addSystem(new GameOverSystem(this));
        engine.addSystem(new AttackSystem(this, controller));

        //Map, MapObjects and Entities
        map = new TmxMapLoader().load("maps/Sandbox.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map,RenderSystem.PIXELS_TO_METRES);
        levelCollision = new LevelGenerator(world);
        new EntitiesFactory(engine);
        levelCollision.createPhysics(map,"Collision");
        levelCollision.makeEntities(map,"Entities");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Update
        camera.update();
        mapRenderer.setView(camera);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(38/255f,32/255f,51/255f,1);
        mapRenderer.render();
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        engine.clearPools();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void createAttack(Body player, float x1, float y1) {

        Entity attack = engine.createEntity();

        StateComponent state = engine.createComponent(StateComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        B2dBodyComponent body = engine.createComponent(B2dBodyComponent.class);
        TextureComponent texturec = engine.createComponent(TextureComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);

        transform.width = 16;
        transform.height = 16;


        body.body = BodyFactory.makeBox(world,player.getPosition().x,player.getPosition().y,16/2,16/2, true);


        Vector2 v = player.getWorldPoint(
                new Vector2(x1 / RenderSystem.PIXELS_TO_METRES, y1 / RenderSystem.PIXELS_TO_METRES));


        Texture texture = new Texture("sprites/swoosh.png");

        TextureRegion[] regions = new TextureRegion().split(texture, 32 ,32 )[0];

        animation.animations.put(StateComponent.STATE_NORMAL,new Animation<TextureRegion>(1 / 8f,regions));

        attack.add(state);
        attack.add(animation);
        attack.add(body);
        attack.add(texturec);
        attack.add(transform);

        engine.addEntity(attack);
    }

    public void gameOver() {
        System.out.println("Game Over");
        game.changeScreen(PixelGame.ENDGAME);
        dispose();
    }
}
