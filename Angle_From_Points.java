import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;
import java.awt.Color;

	public class Angle_From_Points implements PlugInFilter, MouseListener, MouseMotionListener {
		ImagePlus img;
		ImageCanvas canvas;
		static Vector images = new Vector();
		int x1_A, x2_A, x1_B, x2_B, y1_A, y2_A, y1_B, y2_B = 0;
		int click_count=0;

		Overlay overlay = new Overlay();
		OvalRoi point1, point2, point3, point4;
		
		//int POINT_RADIUS = 3;
		
	public int setup(String arg, ImagePlus img) {
		this.img = img;
		IJ.register(Angle_From_Points.class);
		return DOES_ALL+NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		Integer id = new Integer(img.getID());
		if (images.contains(id)) {
			IJ.log("Already listening to this image");
			return;
		} else {
			ImageWindow win = img.getWindow();
			canvas = win.getCanvas();
			canvas.addMouseListener(this);
			canvas.addMouseMotionListener(this);
			images.addElement(id);
		}

	}
	
	public void mousePressed(MouseEvent e) {
		click_count = click_count + 1;
		//IJ.log("Click Count: " + click_count);
		//IJ.showMessage("Click Count: " + click_count);
		int x = e.getX();
		int y = e.getY();
		int offscreenX = canvas.offScreenX(x);
		int offscreenY = canvas.offScreenY(y);
		//IJ.log("Mouse pressed: "+offscreenX+","+offscreenY+modifiers(e.getModifiers()));
		
		int POINT_RADIUS = 9;
		
		if(click_count == 1){
			x1_A = offscreenX;
			y1_A = offscreenY;

			point1 = new OvalRoi( x1_A - POINT_RADIUS , y1_A - POINT_RADIUS , POINT_RADIUS*2 , POINT_RADIUS*2 );

			point1.setStrokeColor( Color.RED );
			point1.setStrokeWidth( POINT_RADIUS );

			overlay.add(point1);
			
			img.setOverlay( overlay );
			img.updateAndDraw();
			
		}else if(click_count == 2){
			x2_A = offscreenX;
			y2_A = offscreenY;

			point2 = new OvalRoi( x2_A - POINT_RADIUS , y2_A - POINT_RADIUS , POINT_RADIUS*2 , POINT_RADIUS*2 );

			point2.setStrokeColor( Color.RED );
			point2.setStrokeWidth( POINT_RADIUS );

			overlay.add(point2);
			
			img.setOverlay( overlay );
			img.updateAndDraw();
			
		}else if(click_count == 3){
			x1_B = offscreenX;
			y1_B = offscreenY;

			point3 = new OvalRoi( x1_B - POINT_RADIUS , y1_B - POINT_RADIUS , POINT_RADIUS*2 , POINT_RADIUS*2 );

			point3.setStrokeColor( Color.BLUE );
			point3.setStrokeWidth( POINT_RADIUS );

			overlay.add(point3);
			
			img.setOverlay( overlay );
			img.updateAndDraw();
			
		}else if(click_count == 4){
			x2_B = offscreenX;
			y2_B = offscreenY;

			point4 = new OvalRoi( x2_B - POINT_RADIUS , y2_B - POINT_RADIUS , POINT_RADIUS*2 , POINT_RADIUS*2 );

			point4.setStrokeColor( Color.BLUE );
			point4.setStrokeWidth( POINT_RADIUS );

			overlay.add(point4);
			
			img.setOverlay( overlay );
			img.updateAndDraw();

			
			//IJ.log("A: " + x1_A + "," + y1_A + "," + x2_A + "," + y2_A + "," + x1_B + "," + y1_B + "," + x2_B + "," + y2_B);
			
			double slopeA = (double)(y2_A-y1_A)/(double)(x2_A-x1_A);
			double slopeB = (double)(y2_B-y1_B)/(double)(x2_B-x1_B);

			//IJ.log("Slope A: " + slopeA);
			//IJ.log("Slope B: " + slopeB);


			double theta_A = Math.atan(slopeA);
			double theta_B = Math.atan(slopeB);
			double theta_AB = Math.abs(Math.toDegrees(theta_B-theta_A));

			if(theta_AB > 90){
				theta_AB = 180 - theta_AB;
			}
			
			IJ.showMessage("Angle from points: " + theta_AB + " degrees");


			overlay.remove(point1);
			overlay.remove(point2);
			overlay.remove(point3);
			overlay.remove(point4);
			img.updateAndDraw();
			click_count = 0;
			x1_A = 0;
			y1_A = 0;
			x2_A = 0;
			y2_A = 0;
			x1_B = 0;
			y1_B = 0;
			x2_B = 0;
			y2_B = 0;


			
		}
		
	}

	public void mouseDragged(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}


}

