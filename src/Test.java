import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jmge.gif.Gif89Encoder;

/**
 * Servlet implementation class Gravity
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getParameter("height")==null){
			PrintWriter out = response.getWriter();
		response.setContentType("text/html");
				//taking the input from the user - initial height at which the ball is
		//held, its mass, energy loss coefficient, number of bounces
		out.println("<h2>An animation of a bouncing ball</h2>");
		out.println("<form>");
		out.println("Initial height at which the ball is being released: <input name='height'>");
		out.println("</br>");
		
		out.println("Mass of the ball: <input name='mass'>");
		out.println("</br>");
		
		out.println("<input type='submit'/>");
		out.println("</form>");

		} else {
    	try {
    		
    		int initialHeight = Integer.parseInt(request.getParameter("height"));
    		//mass determines how big the ball will be; see drop method
    		int mass =  Integer.parseInt(request.getParameter("mass"));
    		
        		
    		Gif89Encoder genc = new Gif89Encoder();
    		int width = 400;
    		
    		//all heights are the same at the beginning; later height1 is used to fillRect and 
    		//for the ball not to go too far at the bottom (won't change); height will be used
    		//as the height of each bounce (it doesn't work but that's the intended use of it)
    		int height1 = initialHeight *100;
    		float height = height1;
    		
    		int size = mass*10;
    		int x = width / 2 - size / 2;
    		int y = 1;
    		//direction determines where the ball is going atm
    		String direction = "down";
    		
    		//k is the percent of height the ball should 'loose' with each bounce;
    		//it changes to 0.25 after the first iteration in the loop
    		float k = (float) 0;
    		for (int t = 1; t < 100; t+=1) {
    			
    			BufferedImage image = new BufferedImage(width, height1, BufferedImage.TYPE_INT_ARGB);
    			Graphics2D g = image.createGraphics();
    			g.setColor(Color.blue);
    			g.fillRect(0, 0, width, height1);
    			g.setColor(Color.pink);
    			
    			drop(g,x,y, size);

    			//if the ball is within the margins
    			if (y>=height*k || y<height1-size){
 	
    				if(direction.equals("down")){
            			y = y + t;
    				} else if (direction.equals("up")) {
    					y = y - t;
    					
    			}
    			}
    			//if the ball is outside the margins, change the direction of it
    				if(y>=(height1-size) || y<=k*height){
    					if(direction.equals("down")){
                			direction = "up";
        				} else {
        					direction = "down";
        				}
    				}
    				
    		//test prints
    		System.out.println("for:");
    		System.out.println(t);
    		System.out.println(height);	
    		System.out.println(direction);
    		k = (float) 0.25;
    		height = height*(1-k);
    		
    			genc.addFrame(image);
    			g.dispose();
    		}
    		genc.setUniformDelay(10);
    		genc.setLoopCount(0);
    		genc.encode(response.getOutputStream());
    	}catch(Exception e){
    		System.err.println(e);
    	}	
    	
    }
    }

    static void drop(Graphics2D g, int x, int y, int size) {
    	g.setColor(Color.pink);
    	g.fillOval(x, y, size, size);
    }
}