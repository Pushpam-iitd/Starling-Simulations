import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class finalStarling extends PApplet {

//top
  

ControlP5 cp5;


String text1,text2,text3; 
float separate = 1;
PVector location;
PVector velocity;

float boxSize = 40.0f;
float cohes = 1;
float rotX,rotY;
float align = 1;


int totnuminitial = 25; //amount of boids to start the program with
boidarray flock1;//,flock2,flock3;
float zoom=-1000;
boolean smoothEdges = true,avoidWalls = true;





PFont myFont; 
String myText = "Lorem ipsum"; 


public void settings() {
  //size(displayWidth,displayHeight,P3D);  
  size(1000,700,P3D); 
  if(smoothEdges)
    smooth();
  else
    noSmooth();
}

public void setup()
{
  //size(640, 360);
    location = new PVector(mouseX, mouseY);
    
    PFont myFont = createFont("Advent", 40, true); // create a font object 
// using a specified font
textFont(myFont, 48);
  //sliders
  cp5 = new ControlP5(this);
  cp5.setAutoDraw(false);
  
  
//PFont pfont = createFont("Advent", 40, true); // use true/false for smooth/no-smooth
//ControlFont font = new ControlFont(pfont, 241);
//int fontSize=20;

//int heightOfSlider=20;
//int widthOfSlider=200;
//int gapBetweenSliders=10;
  
  
      cp5 = new ControlP5(this);

  //          ;
            
  cp5.addTextfield("enter1").setPosition(850,135).setSize(60,20).setAutoClear(false);
  cp5.addBang("Set_Separation").setPosition(900,135).setSize(60,20);  
  cp5.addTextfield("enter2").setPosition(850,100).setSize(60,20).setAutoClear(false);
  cp5.addBang("Set_Cohesion").setPosition(900,100).setSize(60,20);  
  cp5.addTextfield("enter3").setPosition(850,170).setSize(60,20).setAutoClear(false);
  cp5.addBang("Set_Alignment").setPosition(900,170).setSize(60,20);  
     
  //cp5.getController("align")
  ////.setSize(24)
  //;

  //create and fill the list of boids
  flock1 = new boidarray(totnuminitial);


}



public void Set_Separation()
{
  text1 = cp5.get(Textfield.class,"enter1").getText();
  separate = PApplet.parseFloat(text1);
}

public void Set_Cohesion()
{
  text2 = cp5.get(Textfield.class,"enter2").getText();
  cohes = PApplet.parseFloat(text2);
}

public void Set_Alignment()
{
  text3 = cp5.get(Textfield.class,"enter3").getText();
  align = PApplet.parseFloat(text3);
}




public void drawAxes(float size){
  //X  - red
  stroke(192,0,0);
  line(0,0,0,size,0,0);
  //Y - green
  stroke(0,192,0);
  line(0,0,0,0,size,0);
  //Z - blue
  stroke(0,0,192);
  line(0,0,0,0,0,size);
}







public void draw()
{
  pushMatrix();
  //clear screen
  beginCamera();
  camera();
  //rotateX(map(mouseY,0,height,0,TWO_PI));
  //rotateY(map(mouseX,width,0,0,TWO_PI));
  translate(0,0,zoom);
  endCamera();
  background(0xffd65044);

  myText = "Current Number of Birds => ";
  text(myText,550,1200);
  myText = Integer.toString(flock1.boids.size());
  text(myText,1200,1200);
  

 myText = "Total Kinetic Energy => ";
   text(myText,550,1100);
  myText = Float.toString((flock1.energy()));
  text(myText,1080,1100);
  
 myText = "Total Angular Momentum => ";
   text(myText,550,1000);
  myText = Float.toString((flock1.Angularmomentum()));
  text(myText,1180,1000);  

  
    // get velocity vector relative to mouse position
  velocity = new PVector(mouseX - pmouseX, mouseY - pmouseY);
  location.add(velocity);
  translate(0,0,0);
  //drawAxes(200);
  
  // move box to location
  //translate(location.x, location.y);
  
  // check if mouse is moving
  //if (mouseX == pmouseX && mouseY == pmouseY) {
  //  rotate(0.0);
  //}
  //else {
  //  rotate(velocity.heading());
  //}
        //translate(width/2,height/2,0);
  rotateX(-rotY);
  rotateY(rotX);
  //rectMode(CENTER);
  translate(0,0,0);

  noFill();
  stroke(0);
    directionalLight(140,210,255, 0, 1, -100); 
    
      stroke(192,0,0);
  line(width/2-400,height/2,600,width/2+400,height/2,600);
  //Y - green
  stroke(0,192,0);
  line(width/2,height/2-400,600,width/2,height/2+400,600);
  //Z - blue
  stroke(0,0,192);
  line(width/2,height/2,600-400,width/2,height/2,600+400);
    
  //line(0,0,300,  0,height,300);
  //line(0,0,900,  0,height,900);
  //line(0,0,300,  width,0,300);
  //line(0,0,900,  width,0,900);
  
  //line(width,0,300,  width,height,300);
  //line(width,0,900,  width,height,900);
  //line(0,height,300,  width,height,300);
  //line(0,height,900,  width,height,900);
  
  //line(0,0,300,  0,0,900);
  //line(0,height,300,  0,height,900);
  //line(width,0,300,  width,0,900);
  //line(width,height,300,  width,height,900);
  
  flock1.run(separate, align, cohes);
  //flock2.run();
  //flock3.run();

  
  popMatrix();
  

}

public void mouseDragged()
{
  rotX +=(mouseX-pmouseX)*0.001f;
  rotY +=(mouseY-pmouseY)*0.001f;
}


public void keyPressed()
{
  switch (keyCode)
  {
    case UP: zoom-=100; break;
    case DOWN: zoom+=100; break;
  }
  switch (key)
  {
    case 's': smoothEdges = !smoothEdges; break;
    case 'a': avoidWalls = !avoidWalls; break;
  }
}

  public void mousePressed() {
  flock1.addBoid(new Boid(mouseX,mouseY,700));
  print(flock1.boids.size());
  }
  
  
  
  
  
  
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  

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
  

 public void run( float af,float cf,float sf)
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
  
public void add()
  {
    boids.add(new Boid(width/2,height/2,600));
  }
  
public void addBoid(Boid b)
  {
    boids.add(b);
  }
  
    public float energy(){
    float e=0;
    for(int i=0;i<boids.size();i++) //iterate through the list of boids
    {
       Boid tempBoid = (Boid)boids.get(i);
       e += tempBoid.v.mag()*tempBoid.v.mag(); 
    }
    return e;
  }
  
    public float Angularmomentum(){
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
class Boid {

  PVector p,v,acc,aforce,cforce,sforce;          // position 

  float neighborhoodRadius; //radius in which it looks for fellow boids
  float maxSpeed = 2; //maximum magnitude for the velocity vector
  float maxSteerForce = 0.05f; //maximum magnitude of the steering vector
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

  
public void newtryfunc(){
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
  public void run(ArrayList bl, float slider1, float slider2, float slider3)
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
  
  
  
    public void flock(ArrayList pushlist,float aa, float bb, float cc)
  {

    aforce = alignment(pushlist);
    acc.add(PVector.mult(aforce, bb));
    cforce = cohesion(pushlist);
    acc.add(PVector.mult(cforce, cc));
    sforce = seperation(pushlist);
    acc.add(PVector.mult(sforce, aa));
  }


public boolean checkx(float a,float b ){
return (a>b) ;
}
//boolean checkx(float a,float b ){
//return (a>b) ;
//}
//boolean checkx(float a,float b ){
//return (a>b) ;
//}

  public void checkBounds()
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
  
  
  
  

  public void render()
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

 public PVector come_back(PVector target)
  {
    PVector steer = new PVector(); //creates vector for steering
    steer.set(PVector.sub(p, target)); //steering vector points away from target
    steer.mult(1/sq(PVector.dist(p, target)));
    //steer.limit(maxSteerForce); //limits the steering force to maxSteerForce
    return steer;
 }
  

  public PVector letsepa(float d ,PVector repulse,PVector posSum){
    repulse.normalize();
        repulse.div(d);
        posSum.add(repulse);
    return posSum;
  }

  public PVector seperation(ArrayList boids)
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


  public PVector letalign(float count,PVector velSum ){
      velSum.div((float)count);
      velSum.limit(maxSteerForce);
    return velSum;
  }
  
  public PVector alignment(ArrayList boids)
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





  public PVector cohesion(ArrayList boids)
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "finalStarling" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
