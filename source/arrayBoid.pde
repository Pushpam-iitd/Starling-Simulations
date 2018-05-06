
///flockk
// The memarray (a list of Member objects)

class boidarray {
 // ArrayList<Member> members; // An ArrayList for all the members
  ArrayList boids; 
  float h; 
  

  boidarray(int n)
  {
    boids = new ArrayList();
    h = 200;
    for(int i=0;i<n;i++)
      boids.add(new Boid(width/2,height/2,600));
  }
  

 void run( float af,float cf,float sf)
  {
    for(int i=0;i<boids.size();i++) //iterate through the list of boids
    {
      Boid tempBoid = (Boid)boids.get(i); //create a temporary boid to process and make it the current boid in the list
      tempBoid.h = h;
      tempBoid.run(boids,af,cf,sf); //tell the temporary boid to execute its run method
    }
  }
  
  

//void addMember(Member b) {
//    members.add(b);
//  }
  
void add()
  {
    boids.add(new Boid(width/2,height/2,600));
  }
  
void addBoid(Boid b)
  {
    boids.add(b);
  }
  
    float energy(){
    float e=0;
    for(int i=0;i<boids.size();i++) //iterate through the list of boids
    {
       Boid tempBoid = (Boid)boids.get(i);
       e += tempBoid.v.mag()*tempBoid.v.mag(); 
    }
    return e;
  }
  
    float Angularmomentum(){
    float am = 0;
    for(int i=0;i<boids.size();i++) //iterate through the list of boids
    {
       
       Boid tempBoid = (Boid)boids.get(i);
       float m1 = ((tempBoid.p).cross(tempBoid.v)).mag(); 
       am += m1; 
    }
    return am/1000;
  }
  

}
