///shape change
//collide lessness
//functions











//#include <GL/gl.h>
#include <GL/glut.h>
#include<Math.h>
#include <math.h>
#include<iostream>
#include <cstdlib>
#include <ctime>
#include <vector>
// #include<windows.h>

// g++ -Wall -otuku tempbo.cpp -lglut32cu -lglu32 -lopengl32

#define PI 3.14159265f

GLfloat brad=0.04f;
GLfloat bx=0.0f;
GLfloat by=0.0f;
int totnum=2;
GLfloat xmax,xmin,ymax,ymin;

std::vector<float> ballx1;
std::vector<float> bally1;
std::vector<float> ballx2;
std::vector<float> bally2;
std::vector<float> ballx3;
std::vector<float> bally3;

std::vector<float> birdoffset;

std::vector<float> bxspeed;
std::vector<float> byspeed;



GLfloat vospeed=0.05f;

GLfloat offset = PI/4;

GLfloat xspeed;
GLfloat yspeed;

int t;

int refMillis=30;

GLdouble cxarealeft,cxarearight,cyareatop ,cyareabottom ;



float alignment (int n ){
	float offn=0,offcount=0;
	float offx1,ty1,tx1;
	tx1=ballx1.at(n);
	ty1=bally1.at(n);
	offx1= birdoffset.at(n);

	//float tempoffset;
	for (int i=0 ;i< birdoffset.size();i++){
		if(i!=n && (abs(tx1 - ballx1.at(i))<0.3) && (abs(ty1 - bally1.at(i))<0.1)){
			offcount++;
			offn += birdoffset.at(i);

		}
	}

	offn=offn/offcount;
float tempoffsetn;
tempoffsetn= offx1-offn;
if (offcount==0 ) return 0;
else{
	return tempoffsetn;
}

}

float separation (int n ){
	float comx,comy,comcount=0;
	float tx1,ty1;
	tx1= ballx1.at(n);
	ty1= bally1.at(n);


	float tempoffset;
	for (int i=0 ;i< ballx1.size();i++){
		if(i!=n && (abs(tx1 - ballx1.at(i))<0.05) && (abs(ty1 - bally1.at(i))<0.02)){
			comcount++;
			comx += ballx1.at(i); comy += bally1.at(i);
		}
	}
	if (comcount == 0) return 0;
	else {comx = comx/comcount;
	comy = comy/comcount;
	float ang = ((comy-ty1)/((comx - tx1)+0.0001));

	if( ((comy-ty1)<0 && (comx - tx1)<0 ) || ((comy-ty1)>0 && (comx - tx1)<0)) tempoffset = PI + atan(ang);
	else tempoffset = PI + atan(ang);
	return tempoffset;}
}

















float cohesion (int n ){
	float comx=0,comy=0,comcount=0;
	float tx1,ty1;
	tx1= ballx1.at(n);
	ty1= bally1.at(n);

	float tempoffset;
	for (int i=0 ;i< ballx1.size();i++){
		if(i!=n && (abs(tx1 - ballx1.at(i))<0.3) && (abs(ty1 - bally1.at(i))<0.1) && (abs(tx1 - ballx1.at(i))>0.1)&&(abs(ty1 - bally1.at(i))>0.03)) {
			comcount++;
			comx += ballx1.at(i); comy += bally1.at(i);
		}
	}
	comx = comx/comcount;
	comy = comy/comcount;
	float ang = ((comy-ty1)/(0.00001+(comx - tx1)));
	if( ((comy-ty1)<0 && (comx - tx1)<0 ) || ((comy-ty1)>0 && (comx - tx1)<0)) tempoffset = PI + atan(ang);
	else tempoffset = atan(ang);


if(comcount==0) return 0;
else {
	return tempoffset;
}
}


double myrand( void )
{
static unsigned long seed = 123;
seed = (1103515245L * seed + 12345L ) & 0x7fffffffL;
return 1.0 - ((double)seed/(double)0x3fffffff);
}


void initGL(){
	glClearColor(0.0 , 0.0 , 0.0 ,1.0);
}

void display(){



	glClear(GL_COLOR_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();


	glTranslatef(0.0f,0.0f,0.0f);
//vector. erase( vector. begin() + 3 )
//v.insert(v1.begin()+i,val)
for(int k=0;k<totnum;k++){
int changed=0;
	glBegin(GL_TRIANGLE_FAN);

	float numo=ballx1.at(k) + bally1.at(k);
	float c1=ballx1.at(k)/numo;
	float c2=bally1.at(k)/numo;

if (k==0) {	glColor3f(1.0f,0.0f,0.0f);}
else {glColor3f(0.0f,0.0f,1.0f);}

	GLfloat angle;
bxspeed.insert(bxspeed.begin()+k, vospeed*(cos(birdoffset.at(k))) );
byspeed.insert(byspeed.begin()+k, vospeed*(sin(birdoffset.at(k))) );

	double bt;
bt=byspeed.at(k)/bxspeed.at(k);

if( (byspeed.at(k)<0 && bxspeed.at(k)<0 ) || (byspeed.at(k)>0 && bxspeed.at(k)<0)) birdoffset.at(k) = PI + atan(bt);
else birdoffset.at(k)=atan(bt);

		angle =birdoffset.at(k);
		glVertex2f(cos(angle)*brad +ballx1.at(k) ,sin(angle)*brad + bally1.at(k));
//std::cout<<"hoy1angle "<<" k- "<<k<<std::endl;
        angle =birdoffset.at(k)+5*PI/6;
		glVertex2f(cos(angle)*brad +ballx1.at(k),sin(angle)*brad + bally1.at(k));
		angle =birdoffset.at(k)+7*PI/6;
		glVertex2f(cos(angle)*brad + ballx1.at(k),sin(angle)*brad + bally1.at(k));

	 ballx1.at(k) += bxspeed.at(k);
	 ballx2.at(k) += bxspeed.at(k);
	 ballx3.at(k) += bxspeed.at(k);

	 bally1.at(k) += byspeed.at(k);
	 bally2.at(k) += byspeed.at(k);
	 bally3.at(k) += byspeed.at(k);

	double at;
	 if (ballx1.at(k) > xmax)
	 {
	 	ballx1.at(k) = xmax;
	 	bxspeed.at(k) = -bxspeed.at(k);
	 	changed=1;
	 }

	 else if (ballx1.at(k) < xmin)
	 {
	 	ballx1.at(k) = xmin;
	 	bxspeed.at(k) = -bxspeed.at(k);
	 	changed=1;
	 }

	 if (bally1.at(k) > ymax)
	 {
	 	bally1.at(k) = ymax;
	 	byspeed.at(k) = -byspeed.at(k);
	 	changed=1;
	 }

	 else if (bally1.at(k) < ymin)
	 {
	 	bally1.at(k) = ymin;
	 	byspeed.at(k) = -byspeed.at(k);
	 	changed=1;
	 }

		at=byspeed.at(k)/bxspeed.at(k);
        float k1=cohesion(k);
        float k2=alignment(k);
        float k3=separation(k);

        // if(k==0){
        // 	k1=PI/4;k2=0;k3=0;
        // }
if((byspeed.at(k)<0 && bxspeed.at(k)<0) || (byspeed.at(k)>0 && bxspeed.at(k)<0))
{
	if(k==0)birdoffset.at(k) = PI + atan(at)  ;
    else {
    	if(changed==1){birdoffset.at(k) = PI + atan(at) ;}
    	else {birdoffset.at(k) = (2*k1+k2)/3 ;}
    }

//std::cout<<"hoyif "<<birdoffset.at(k)<<"  k- "<<k<<std::endl;
} //(PI + atan(at) + (k1/2))/2 ;
else{
	if (k==0)birdoffset.at(k)=   atan(at);
	else {
    	if(changed==1){birdoffset.at(k) = atan(at) ;}
    	else {birdoffset.at(k) = (2*k1+k3)/3 ;}//

	} //birdoffset.at(k)=   (2*k1+k3)/3;
//	std::cout<<"hoyelse "<<birdoffset.at(k)<<"  k- "<<k<<"  k1- "<<k1<<"  k2- "<<k2<<"  at- "<<at<<std::endl;
}

	glEnd();

}

	glutSwapBuffers();


}





void reshape(GLsizei width, GLsizei height){
	if (height == 0)
	{
		height =1;/* code */
	}

double yay=myrand();
//float randomNumber = (2.0 * float(rand()) / 40.0) - 1.0;
//std::cout<<"width "<<yay <<std::endl;
	GLfloat aspect = GLfloat(width) / GLfloat(height);

	// glViewport(0,0,width,height);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	if ( width > height){
		cxarealeft = -1.0 * aspect;
		cxarearight = 1.0 * aspect;
		cyareabottom = -1.0 ;
		cyareatop = 1.0 ;
	}
	else {
		cxarealeft = -1.0 ;
		cxarearight = 1.0 ;
		cyareabottom = -1.0/aspect ;
		cyareatop = -1.0/aspect ;
	}


	gluOrtho2D(cxarealeft, cxarearight,cyareabottom,cyareatop);
	xmin = cxarealeft + brad;
	xmax = cxarearight - brad;
	ymin = cyareabottom + brad;
	ymax = cyareatop - brad;

}


//

void special(int key, int x, int y)
{



    switch(key) {
    case GLUT_KEY_LEFT:

ballx1.push_back(0.2);
bally1.push_back(0.2);
ballx2.push_back(0.0);
bally2.push_back(0.0);
ballx3.push_back(0.0);
bally3.push_back(0.0);

totnum++;
birdoffset.push_back(PI/4);

             break;
    case GLUT_KEY_RIGHT:
        //sceneinfo.triangle.pos.x += 0.2;
        break;
    case GLUT_KEY_UP:
        //sceneinfo.triangle.pos.y += 0.2;
        break;
    case GLUT_KEY_DOWN:
        //sceneinfo.triangle.pos.y -= 0.2;
        break;
    }
    glutPostRedisplay();
}
//

	void Timer(int value){

		glutPostRedisplay();
		glutTimerFunc(refMillis,Timer,0);
}


		int wwidth = 640;
		int wheight = 480;

		int wposx = 50;
		int wposy = 50;





	int main(int argc, char** argv)
	{

ballx1.push_back(0.0);
bally1.push_back(0.0);
ballx2.push_back(0.0);
bally2.push_back(0.0);
ballx3.push_back(0.0);
bally3.push_back(0.0);

ballx1.push_back(0.5);
bally1.push_back(0.5);
ballx2.push_back(0.5);
bally2.push_back(0.5);
ballx3.push_back(0.5);
bally3.push_back(0.5);


// ballx1.push_back(-0.5);
// bally1.push_back(-0.5);
// ballx2.push_back(-0.5);
// bally2.push_back(-0.5);
// ballx3.push_back(-0.5);
// bally3.push_back(-0.5);

birdoffset.push_back(PI/4);
birdoffset.push_back(PI/6);
// birdoffset.push_back(-PI/3);

		glutInit(&argc,argv);
		glutInitDisplayMode(GLUT_DOUBLE);
		glutInitWindowSize(wwidth,wheight);
		glutInitWindowPosition(wposx,wposy);
		glutCreateWindow("Pushpam and Nikhil");
		glutDisplayFunc(display);
		glutSpecialFunc(special);
		glutReshapeFunc(reshape);
		glutTimerFunc(0,Timer,0);
		initGL();
		glutMainLoop();


		return 0;
	}


/*glBegin(GL_LINES);
glVertex3f(0.0, 0.0, 0.0);
glVertex3f(15, 0, 0);
glEnd();
*/
