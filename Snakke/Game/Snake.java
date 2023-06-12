package Game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


public class Snake extends JFrame {

	
    ImagenSnake imagenSnake;
    Point snake;
    //Point lastSnake;
    Point comida;
    ArrayList<Point> listaPosiciones = new ArrayList<Point>();

    int longitud = 2;
    
    int width = 1080;
    int height = 640;
    int can=0;
    int cont=30;
    
	/*Sound musica1=new Sound();
	Sound musica2=new Sound();
	Sound musica3=new Sound();*/
    
    int widthPoint = 10;
    int heightPoint = 10;
    
    int musicaCont=0;

	String direccion = "RIGHT";
	long frequency = 55;
	

    boolean gameOver = true;
	public Container jPanel1;

    public Snake() throws InterruptedException {
		setTitle("Snake");

        startGame();
        imagenSnake = new ImagenSnake();

        this.getContentPane().add(imagenSnake);

		setSize(width,height);
		
		this.addKeyListener(new Teclas());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        setVisible(true);
        Momento momento = new Momento();
		Thread trid = new Thread(momento);
		trid.start();
    }
    
    public void startGame() throws InterruptedException {
         
    	if (!gameOver) {
    		actualizarTimer();
    	}
    	musicaCont=0;
		comida = new Point(200,100);	
        snake = new Point(320,240);
		listaPosiciones = new ArrayList<Point>();
        listaPosiciones.add(snake);

		longitud = listaPosiciones.size(); 

    }
	public void generarComida() {
		Random rnd = new Random();
		
		comida.x = (rnd.nextInt(width));
		if((comida.x % 5) > 0) {
			comida.x = comida.x - (comida.x % 5);
		}

		if(comida.x < 5) {
			comida.x = comida.x + 10;
		}
		if(comida.x > width) {
			comida.x = comida.x - 10;
		}

		comida.y = (rnd.nextInt(height));
		if((comida.y % 5) > 0) {
			comida.y = comida.y - (comida.y % 5);
		}	

		if(comida.y > height) {
			comida.y = comida.y - 10;
		}
		if(comida.y < 0) {
			comida.y = comida.y + 10;
		}
		
		

//----------------------------------------------------------------------------

	}

	public void actualizarTimer() {	
	
	Timer timer = new Timer();
	
	TimerTask contador=new TimerTask() {
	
	@Override
		public void run() {
			
		if(!gameOver) {
		cont-=1;
		}
		
			if(cont== 0) {
				cont=30;
			}
			
		}
	
	};
		
	timer.schedule(contador, 0, 1000);
	
	}
	
	@SuppressWarnings("deprecation")
	public void actualizar() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (can==0) {

		can++;
		}
        listaPosiciones.add(0,new Point(snake.x,snake.y));
		listaPosiciones.remove(listaPosiciones.size()-1);
		
        for (int i=1;i<listaPosiciones.size();i++) {
            Point point = listaPosiciones.get(i);
            if(snake.x == point.x && snake.y  == point.y) {
                gameOver = true;
                System.exit(0);
            }
        }
        
       if((snake.x > (comida.x-10) && snake.x < (comida.x+10)) && (snake.y > (comida.y-10) && snake.y < (comida.y+10))) {
            listaPosiciones.add(0,new Point(snake.x,snake.y));
            cont=30;
			generarComida();
			
       }
		
		if(cont==1) {
			gameOver=true;
			System.exit(0);
		}
					
			imagenSnake.repaint();

	}
//-----------------------------------------------------------------------------------
	

	public static void main(String[] args) throws InterruptedException {
		
		Snake snake1 = new Snake();
	}
 
    public class ImagenSnake extends JPanel {

		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if(gameOver) {
                g.setColor(new Color( 0, 0, 0));
            } else {
                g.setColor(new Color(5,118,201));
            }
            g.fillRect(0,0, width, height);
            g.setColor(new Color(0,255,0));
    
            if(listaPosiciones.size() > 0) {
                for(int i=0;i<listaPosiciones.size();i++) {
                    Point p = (Point)listaPosiciones.get(i);
                    g.fillRect(p.x,p.y,widthPoint,heightPoint);
                }
            }
         
            g.setColor(new Color(255,0,0));
            g.fillRect(comida.x,comida.y,widthPoint,heightPoint);
            
            g.setFont(new Font("Arial Bold", Font.BOLD, 40));
            g.setColor(new Color( 0, 243, 243 ));
            g.drawString(""+cont, 10, 40);
                        
            if(gameOver) {          
                g.setFont(new Font("Arial Bold", Font.BOLD, 40));
                g.drawString("Pequeño Viaje por 2020", 310, 100);
                g.drawString("SCORE "+(listaPosiciones.size()-1), 450, 150);

                g.setFont(new Font("Arial Bold", Font.BOLD, 20));
                g.drawString("Antes de empezar debes saber que solo tienes 1 oportunidad", 250, 320);
                g.drawString("Una vez empieces no hay vuelta atras...", 350, 340);
                g.drawString("Presta mucha atencion <O-O>", 390, 360);
                g.drawString("Manejo:(w,a,s y d) o (flechitas)", 390, 380);
                g.drawString("Pulsa la tecla N para empezar", 390, 400);
                
              //  g.drawString("ESC to Exit", 100, 340);
            }

        }
    }
    public class imagenes extends JFrame {
        Image imagen1,imagen2,imagen3; //declaro 3 variables del tipo iamgen
        InputStream imgStream;  
        
        public imagenes(){
        	
        	super("");
     
           if (listaPosiciones.size()==33) {
               this.setSize(960, 1080);
           }else if (listaPosiciones.size()!=33&&listaPosiciones.size()!=36) {
        	   this.setSize(1920, 1080);					//VERTICAL
           }else if (listaPosiciones.size()==36) {
        	   this.setSize(640, 530);					//VERTICAL
           }
             
          this.setVisible(true);
          this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
          cargaImagen();
          repaint();
        }
        public void cargaImagen(){
             //Este metodo se puede codificar para cargar una imagen y proyectarla mientras se esta jugando              
        }
       
        public void paint(Graphics g)
        {
              Graphics g2 = (Graphics2D)g;
              g2.setColor(Color.black);
              
                  if (listaPosiciones.size()==33) {
                	  g2.fillRect(0, 0, 960, 1080);
                      g2.drawImage(imagen1, 0, 0, 960, 1080, null);							//VERTICAL
                  }else if (listaPosiciones.size()!=33&&listaPosiciones.size()!=36) {
                      g2.fillRect(0, 0, 1920, 1080);
                      g2.drawImage(imagen1, 0, 0, 1920, 1080, null);							//VERTICAL
                  }else if (listaPosiciones.size()==36) {
                      g2.fillRect(0, 50, 1920, 1080);
                      g2.drawImage(imagen1, 0, 50, 640, 480, null);							//VERTICAL
                  }                                      
        }
       

  }
	public class Teclas extends java.awt.event.KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D) {

				if(direccion != "LEFT") {
                    direccion = "RIGHT";
				}
			} else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A) {
				if(direccion != "RIGHT") {
                    direccion = "LEFT";
				}
			} else if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W) {
				if(direccion != "DOWN") {
                    direccion = "UP";
				}
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S) {
				if(direccion != "UP") {
                    direccion = "DOWN";
			}
			
			} else if(e.getKeyCode() == KeyEvent.VK_N) {
                gameOver = false;
                try {
					startGame();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}				
			}
		}

	}

	public class Momento extends Thread {
		
		private long last = 0;
		
		public Momento() {
			
		}

		public void run() {
			while(true) {
				if((java.lang.System.currentTimeMillis() - last) > frequency) {
					if(!gameOver) {

                        if(direccion == "RIGHT") {
                            snake.x = snake.x + widthPoint;
                            if(snake.x > width) {
                                snake.x = 0;
                            }
                        } else if(direccion == "LEFT") {
                            snake.x = snake.x - widthPoint;
                            if(snake.x < 0) {
                                snake.x = width - widthPoint;
                            }                        
                        } else if(direccion == "UP") {
                            snake.y = snake.y - heightPoint;
                            if(snake.y < 0) {
                                snake.y = height;
                            }                        
                        } else if(direccion == "DOWN") {
                            snake.y = snake.y + heightPoint;
                            if(snake.y > height) {
                                snake.y = 0;
                            }                        
                        }
                    }
                    try {
						actualizar();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
					
					last = java.lang.System.currentTimeMillis();
				}

			}

		}
	}

}