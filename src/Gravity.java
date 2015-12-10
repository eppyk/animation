import java.awt.image.BufferedImage;
import java.awt.*;
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

	static void drawFrameDown(Graphics2D g, int height){
		g.setColor(Color.red);
	    g.fillRect(0, 0, 300, 300);	
	    g.setColor(Color.blue);

		for (int h=0; h<height; h++){
		    g.fillOval(height+h,0,20,20);
		}
	}
	
	static void drawFrameUp(Graphics2D g, int height){
		g.setColor(Color.red);
	    g.fillRect(0, 0, 300, 300);
	    g.setColor(Color.blue);
	 
		for (int h=0; h<height; h++){
		    g.fillOval(0,height-h,20,20);
		}
	}
	
    static void drop(Graphics2D g, int x, int y) {
    	g.setColor(Color.blue);
    	g.fillOval(x, y, 50, 50);
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (request.getParameter("mass")==null){
			PrintWriter out = response.getWriter();
		response.setContentType("text/html");
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

		} else {
			
			try {
		
		//parsing all the parameters
		int initialHeight = Integer.parseInt(request.getParameter("height"));
		int mass = Integer.parseInt(request.getParameter("mass"));
		int k = Integer.parseInt(request.getParameter("k"))/100;
		int bounceNo = Integer.parseInt(request.getParameter("bounceNo"));

		//physics magic - calculating 
		int g = 10;

		float height = initialHeight;
		float ep = initialHeight*mass*g;
		int width = 1000;
		int heightImg = 1500;
		int x = width / 2 - 25;;
		int y = 0;
		int vel = 5;
		
		Gif89Encoder genc = new Gif89Encoder();
		for (int i = 1; i < 500; i++) {
			BufferedImage image = new BufferedImage(width, heightImg, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g1 = image.createGraphics();
			g1.setColor(Color.decode("#AAAAAA"));
			g1.fillRect(0, 0, width, heightImg);

			if (y > height - 50 && vel < 5 && vel >= 0){
				g1.setColor(Color.blue);
				g1.fillOval(x, heightImg - 45, 50, 50);
			} else {
				drop(g1, x, y);
				if (y >= heightImg - 50 && vel > 20)
					vel = -vel + 10;
				else if (y >= heightImg - 50 && vel > 0)
					vel = -vel + 5;
				y += vel;
				if (vel < 0)
					vel++;
				if (i % 5 == 0 && vel < 40)
					vel += 10;
			}
			
		genc.setUniformDelay(10);
        genc.setLoopCount(0);
        genc.encode(response.getOutputStream());
		
	} 
			}catch(Exception e){
		System.err.println(e);
	}
	
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
