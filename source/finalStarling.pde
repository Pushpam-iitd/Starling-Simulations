//top
  import controlP5.*;

ControlP5 cp5;


String text1,text2,text3; 
float separate = 1;
PVector location;
PVector velocity;

float boxSize = 40.0;
float cohes = 1;
float rotX,rotY;
float align = 1;


int totnuminitial = 25; //amount of boids to start the program with
boidarray flock1;//,flock2,flock3;
float zoom=-1000;
boolean smoothEdges = true,avoidWalls = true;





PFont myFont; 
String myText = "Lorem ipsum"; 


void settings() {
  //size(displayWidth,displayHeight,P3D);  
  size(1000,700,P3D); 
  if(smoothEdges)
    smooth();
  else
    noSmooth();
}

void setup()
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



void Set_Separation()
{
  text1 = cp5.get(Textfield.class,"enter1").getText();
  separate = float(text1);
}

void Set_Cohesion()
{
  text2 = cp5.get(Textfield.class,"enter2").getText();
  cohes = float(text2);
}

void Set_Alignment()
{
  text3 = cp5.get(Textfield.class,"enter3").getText();
  align = float(text3);
}




void drawAxes(float size){
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







void draw()
{
  pushMatrix();
  //clear screen
  beginCamera();
  camera();
  //rotateX(map(mouseY,0,height,0,TWO_PI));
  //rotateY(map(mouseX,width,0,0,TWO_PI));
  translate(0,0,zoom);
  endCamera();
  background(#d65044);

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

void mouseDragged()
{
  rotX +=(mouseX-pmouseX)*0.001;
  rotY +=(mouseY-pmouseY)*0.001;
}


void keyPressed()
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

  void mousePressed() {
  flock1.addBoid(new Boid(mouseX,mouseY,700));
  print(flock1.boids.size());
  }
  
  
  
  
  
  
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
