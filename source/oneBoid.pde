class Boid {

  PVector p,v,acc,aforce,cforce,sforce;          // position 

  float neighborhoodRadius; //radius in which it looks for fellow boids
  float maxSpeed = 2; //maximum magnitude for the velocity vector
  float maxSteerForce = 0.05; //maximum magnitude of the steering vector
  float h;
  float r;
  float maxforce;    // Maximum steering force
   // Maximum speed

  
  
Boid(float x, float y,float z) {
       PVector huha=new PVector(x,y,z);
  p = new PVector(random(1500), random(1000), random(1000));
    p.set(huha);
    v = new PVector(random(-1, 1), random(-1, 1), random(1, -1));
    acc = new PVector(0, 0, 0);
    neighborhoodRadius = 100;
  
  
  }

  
void newtryfunc(){
  PVector a1 = new PVector();
      a1.set(come_back(new PVector(p.x, height, p.z))) ;
      acc.add(PVector.mult(a1, 5));
      
      PVector a2 = new PVector();
      a2.set(come_back(new PVector(p.x, 0, p.z))) ;
      acc.add(PVector.mult(a2, 5));
      
      PVector a3 = new PVector();
      a3.set(come_back(new PVector(width, p.y, p.z))) ;
      acc.add(PVector.mult(a3, 5));
      
      PVector a4 = new PVector();
      a4.set(come_back(new PVector(0, p.y, p.z))) ;
      acc.add(PVector.mult(a4, 5));      

      PVector a5 = new PVector();
      a5.set(come_back(new PVector(p.x, p.y, 300))) ;
      acc.add(PVector.mult(a5, 5));   

      PVector a6 = new PVector();
      a6.set(come_back(new PVector(p.x, p.y, 900))) ;
      acc.add(PVector.mult(a6, 5));   
     
  
}
  void run(ArrayList bl, float slider1, float slider2, float slider3)
  {
    //t+=0.1;
      newtryfunc();
    
    flock(bl,slider1,slider2,slider3);
  
     v.add(acc); 
    v.limit(maxSpeed); //velocity ki limit rakh leun
    p.add(v);
    acc.mult(0); 
  
    checkBounds();
    //flap = 10*sin(t);
    render();
  }
  
  
  
    void flock(ArrayList pushlist,float aa, float bb, float cc)
  {

    aforce = alignment(pushlist);
    acc.add(PVector.mult(aforce, bb));
    cforce = cohesion(pushlist);
    acc.add(PVector.mult(cforce, cc));
    sforce = seperation(pushlist);
    acc.add(PVector.mult(sforce, aa));
  }


boolean checkx(float a,float b ){
return (a>b) ;
}
//boolean checkx(float a,float b ){
//return (a>b) ;
//}
//boolean checkx(float a,float b ){
//return (a>b) ;
//}

  void checkBounds()
  {
    boolean b1=checkx(p.x,width);
    boolean b2=checkx(0,p.x);
    boolean b3=checkx(p.y,height);
    boolean b4=checkx(0,p.y);
    boolean b5=checkx(p.z,900);
        boolean b6=checkx(300,p.z);
    //boolean b2=checky();
    //boolean b3=checkz();
    
    if (b1) p.x=0;
    if (b2) p.x=width;
    if (b3) p.y=0;
    if (b4) p.y=height;
    if (b5) p.z=300;
    if (b6) p.z=900;
  }
  
  
  
  

  void render()
  {

    pushMatrix();
    translate(p.x, p.y, p.z);
    rotateY(atan2(-v.z, v.x));
    rotateZ(asin(v.y/v.mag()));
    stroke(h);
    noFill();
    noStroke();
    fill(h);
    //draw bird
    beginShape(TRIANGLES);
    vertex(9,0,0);
    vertex(-9,6,0);
    vertex(-9,-6,0);

    vertex(9,0,0);
    vertex(-9,6,0);
    vertex(-9,0,6);

    vertex(9,0,0);
    vertex(-9,0,6);
    vertex(-9,-6,0);

    endShape();
    //box(10);
    popMatrix();
  }

 PVector come_back(PVector target)
  {
    PVector steer = new PVector(); //creates vector for steering
    steer.set(PVector.sub(p, target)); //steering vector points away from target
    steer.mult(1/sq(PVector.dist(p, target)));
    //steer.limit(maxSteerForce); //limits the steering force to maxSteerForce
    return steer;
 }
  

  PVector letsepa(float d ,PVector repulse,PVector posSum){
    repulse.normalize();
        repulse.div(d);
        posSum.add(repulse);
    return posSum;
  }

  PVector seperation(ArrayList boids)
  {
    PVector posSum = new PVector(0, 0, 0);
    PVector repulse;
    for (int i=0; i<boids.size(); i++)
    {
      Boid b = (Boid)boids.get(i);
      float d = PVector.dist(p, b.p);
      if (d>0&&d<=neighborhoodRadius)
      {
      repulse = PVector.sub(p, b.p);  
         posSum= letsepa(d,repulse,posSum);  
      
      }
    }
    return posSum;
  }


  PVector letalign(float count,PVector velSum ){
      velSum.div((float)count);
      velSum.limit(maxSteerForce);
    return velSum;
  }
  
  PVector alignment(ArrayList boids)
  {
    PVector velSum = new PVector(0, 0, 0);
    int count = 0;
    for (int i=0; i<boids.size(); i++)
    {
      Boid b = (Boid)boids.get(i);
      float d = PVector.dist(p, b.p);
      if (d>0&&d<=neighborhoodRadius)
      {
        velSum.add(b.v);
        count++;
      }
    }
    if (count>0)
    {

      velSum=letalign(count,velSum) ;
      
      
    }
    return velSum;
  }





  PVector cohesion(ArrayList boids)
  {
    PVector posSum = new PVector(0, 0, 0);
    PVector steer = new PVector(0, 0, 0);
    int count = 0;
    for (int i=0; i<boids.size(); i++)
    {
      Boid b = (Boid)boids.get(i);
      float d = dist(p.x, p.y, b.p.x, b.p.y);
      if (d>0&&d<=neighborhoodRadius)
      {
        posSum.add(b.p);
        count++;
      }
    }
    if (count>0)
    {
      posSum.div((float)count);
    }
    steer = PVector.sub(posSum, p);
    steer.limit(maxSteerForce); 
    return steer;
  }

}
