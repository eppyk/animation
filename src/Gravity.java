import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import net.jmge.gif.Gif89Encoder;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Gravity
 */
@WebServlet("/Gravity")
public class Gravity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Gravity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	static void drawFrameDown(Graphics2D g, int height, int h){
		g.setColor(Color.red);
	    g.fillRect(0, 0, 300, 300);
	    g.setColor(Color.blue);
	    g.fillOval(0,height-h,20,20);
		}
	
	static void drawFrameUp(Graphics2D g, int height, int h){
		g.setColor(Color.red);
	    g.fillRect(0, 0, 300, 300);
	    g.setColor(Color.blue);
	    g.fillOval(0,height-h,20,20);
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		//taking the input from the user - initial height at which the ball is
		//held, its mass, energy loss coefficient, number of bounces
		out.println("<h2>An animation of a bouncing ball</h2>");
		out.println("<form>");
		out.println("Initial height at which the ball is being released: <input name='height'>");
		out.println("</br>");
		
		out.println("Mass of the ball: <input name='mass'/>");
		out.println("</br>");
		
		out.println("Energy loss coefficient in %: <input name='k'/>");
		out.println("</br>");
		
		out.println("Number of preferred bounces: <input name='bounceNo'/>");
		out.println("</br>");
		out.println("<input type='submit'/>");
		out.println("</form>");

		try {
		//parsing all the parameters
		int initialHeight = Integer.parseInt(request.getParameter("height"));
		int mass = Integer.parseInt(request.getParameter("mass"));
		int k = Integer.parseInt(request.getParameter("k"))/100;
		int bounceNo = Integer.parseInt(request.getParameter("bounceNo"));

		//physics magic - calculating 
		int g = 10;

		int height = initialHeight;
		
		Gif89Encoder genc = new Gif89Encoder();
		for (int i=0; i<bounceNo; i++){
			int ep = initialHeight*mass*g;
			out.println(ep);
			for (int h=0; h<height; h++){
				out.println(ep);
				BufferedImage image = new BufferedImage(150,100,BufferedImage.TYPE_INT_ARGB);
				Graphics2D g1 = image.createGraphics();
			    drawFrameDown(g1, height, h);
		        genc.addFrame(image);
		        g1.dispose();
		        ep = height - (k/100)*ep;
		        height = ep/(mass*g);
				BufferedImage image2 = new BufferedImage(150,100,BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = image.createGraphics();
		        drawFrameUp(g2, height, h);
			}	}
			
		genc.setUniformDelay(10);
        genc.setLoopCount(0);
        genc.encode(response.getOutputStream());
		
	} catch(Exception e){
		System.err.println(e);
	}
	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
