/**
 * This is the superclass of all entities in the game.
 * @author Kibo
 *
 */
public class Entity {
		//Location
		private float X;
		private float Y;
		//Speed
		private float dX;
		private float dY;
		
		public Entity(float X, float Y, float dX, float dY){
			this.X=X;
			this.Y=Y;
			this.dX=dX;
			this.dY=dY;
		}
		
		public void update(){
			X=X+dX;
			Y=Y+dY;
			checkCollision();
			draw();
		}
		
		public void draw(){
			//TODO: draw
		}
		
		public void checkCollision(){
			//TODO: collision(this, otherEntity)
		}
		
		public void collision(Entity e1, Entity e2){
			//TODO: check which entities and handle collision.
		}
		
		public void destroy(){
			Game.destroy(this);
		}
		
}
