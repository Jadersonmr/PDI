package pdi;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pdi {
	
	public static Image questao1(Image img, String selecao,int distancia) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		int red = 0;
		int green = 0;
		int blue = 0;

		red = selecao=="Vermelho"?1:red;
		green = selecao=="Verde"?1:green;
		blue = selecao=="Azul"?1:blue;

		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				if (i == distancia) {
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}else {
					Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
				
			}
		}
		
		return wi;
	}
	
	public static Image questao2(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				
				Color cor = pr.getColor(i, j);			
				Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
				pw.setColor(i, j, corNova);
				
				if (j >= h/2) {
					cor = pr.getColor(i, h-j);
					
					corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
				
			}
		}
		
		return wi;
	}
	
	public static Image desenha(double cx, double cy, double sx, double sy, Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				if (i>=cx && j>=cy && i<=sx && j<=sy) {
					Color corNova = new Color(0, 0, 0, 1);
					pw.setColor(i, j, corNova);
					
					if (i>=cx+1 && j>=cy+1 && i<=sx-1 && j<=sy-1) {
						Color corNova2 = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
						pw.setColor(i, j, corNova2);
					}
					
					
						if (cor.getRed()==1 && cor.getGreen()==0 && cor.getBlue()==0) {
							System.out.println("Vermelho");
						}else if (cor.getRed()==0 && cor.getGreen() == 1 && cor.getBlue()==0) {
							System.out.println("Verde");
						}else if (cor.getRed()==0 && cor.getGreen() == 0 && cor.getBlue() == 1) {
							System.out.println("Azul");
						}
					
				}else {
					Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, corNova);
				}

			}
		}
		
		return wi;
	}

	public static Image cinza(Image img, double r, double g, double b) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color oldCor = pr.getColor(i, j);
				double media = (oldCor.getRed() + oldCor.getGreen() + oldCor.getBlue()) / 3;
				if (r != 0 && g != 0 && b != 0)
					media = (oldCor.getRed() * r + oldCor.getGreen() * g + oldCor.getBlue() * b) / 100;
				Color newCor = new Color(media, media, media, oldCor.getOpacity());
				pw.setColor(i, j, newCor);
			}
		}
		return wi;
	}

	public static Image limiar (Image img, double limiar) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Color corNova = null;
				if (cor.getRed()>limiar)
					corNova = new Color(1, 1, 1, cor.getOpacity());
				else
					corNova = new Color(0, 0, 0, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		//System.out.println("Limiar: "+limiar);
		return wi;
	}
	
	public static Image negativa (Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				double red = 1 - cor.getRed();
				double green = 1 - cor.getGreen();
				double blue = 1 - cor.getBlue();
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getGrafico(Image img,BarChart<String, Number> grafico){
		CategoryAxis eixoX = new CategoryAxis();
		NumberAxis eixoY = new NumberAxis();
	    eixoX.setLabel("Canal");       
	    eixoY.setLabel("valor");
	    XYChart.Series vlr = new XYChart.Series();
	    vlr.setName("Intensidade");
	    int[] hist = histogramaUnico(img);
	    for (int i=0; i<hist.length; i++) {
	    	vlr.getData().add(new XYChart.Data(i+"", hist[i]/1000));
		}
	    grafico.getData().addAll(vlr);
	}
	
	public static int[] histogramaUnico(Image img) {
		int[] qt = new int[256];
		PixelReader pr = img.getPixelReader();
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
					qt[(int)(pr.getColor(i, j).getRed()*255)]++;
					qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
					qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
			}
		}
		return qt;
	}

	public static int[] histograma(Image img, int canal) {
		int[] qt = new int[256];
		PixelReader pr = img.getPixelReader();
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (canal==1) {
					qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				}else {
					if (canal==2) {
						qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
					} else {
						qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
					}
				}
			}
		}
		return qt;
	}
	
	public static int[] histogramaAcumulado(int[] hist) {
		int[] ret = new int[hist.length];
		int vl = hist[0];
		for (int i = 0; i < hist.length-1; i++) {
			ret[i] = vl;
			vl +=hist[i+1];
		}
		return ret;
	}
	
	public static Image equalizar(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		int[] histR = histograma(img,1);
		int[] histG = histograma(img,2);
		int[] histB = histograma(img,3);
		
		int[] hacR = histogramaAcumulado(histR);
		int[] hacG = histogramaAcumulado(histG);
		int[] hacB = histogramaAcumulado(histB);
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				double acR = hacR[(int)(cor.getRed()*255)];
				double acG = hacG[(int)(cor.getGreen()*255)];
				double acB = hacB[(int)(cor.getBlue()*255)];
				double n = w*h;
				double pxR = ((255-1)/n) * acR;
				double pxG = ((255-1)/n) * acG;
				double pxB = ((255-1)/n) * acB;
				double corR = pxR = pxR/255;
				double corG = pxR = pxG/255;
				double corB = pxR = pxB/255;
				Color corNova = new Color(corR, corG, corB, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}
	
	public static Image segmentacao(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		double[] limiar = new double[] {0.33, 0.66};
		
    	for (int i=1; i < w; i++){
    		for (int j=1; j < h; j++){	
    			
    			Color oldCor = pr.getColor(i,j);
    			Color newCor = null;
    			if(oldCor.getRed()<limiar[0]) {
    				newCor = new Color(1,0,0,1);
    			}else {
    				if(oldCor.getRed()>limiar[0] && oldCor.getRed()<limiar[1]) {
    					newCor = new Color(1,1,0,1);
    				}else {
    					newCor = new Color(0,0,1,1);
    				}
    			}
    			    			
    			pw.setColor(i, j, newCor);
    		}
    	}
		return wi;
	}
	
	public static Image moldura(Image img, String selecao,int largura) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		int red = 0;
		int green = 0;
		int blue = 0;
		
		red = selecao=="Vermelho"?1:red;
		green = selecao=="Verde"?1:green;
		blue = selecao=="Azul"?1:blue;

		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				if (i<=largura || j<=largura || i>=(w-1)-largura || j>=(h-1)-largura) {
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}else {
					Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
			}
		}
		
		return wi;
	}
	
	public static Image dividir(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				double red = 0;
				double green = 0;
				double blue = 0;
				
				if (j <= h/2) {
					red = 1 - cor.getRed();
					green = 1 - cor.getGreen();
					blue = 1 - cor.getBlue();
					
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}else {
					red = cor.getRed();
					green = cor.getGreen();
					blue = cor.getBlue();
					
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
				
			}
		}
		
		return wi;
	}
	
	public static Image identificar(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();

		int qd = 0;
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				if (i!=0 && i!=w) {
					cor = pr.getColor(i-1, j);
					Color cor2 = pr.getColor(i+1, j);
					if (cor.getRed() == 0 && cor.getGreen() == 0 && cor.getBlue() == 0 && cor2.getRed() == 0 && cor2.getGreen() == 0 && cor2.getBlue() == 0) {
						qd++;
					}
				}
				cor = pr.getColor(i, j);
				Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		
		if (qd > 100) {
			System.out.println("Quadrado");
		}else {
			System.out.println("Círculo");
		}
		
		return wi;
	}
	
	public static Image diminuir(Image img) {
		int w = (int)img.getWidth()/2;
		int h = (int)img.getHeight()/2;
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i*2, j*2);
				
				double red = cor.getRed();
				double green = cor.getGreen();
				double blue = cor.getBlue();

				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		
		return wi;
	}
	
	public static Image aumentar(Image img) {
		int w = 2*(int)img.getWidth();
		int h = 2*(int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i/2, j/2);
				
				double red = cor.getRed();
				double green = cor.getGreen();
				double blue = cor.getBlue();

				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		
		return wi;
	}

	public static Image girar(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();

		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				
				Color corNova = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
				pw.setColor(j, i, corNova);
			}
		}
		
		return wi;
	}
	
	public static Image adicao(Image img, Image img2, double op1, double op2) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		w = Math.min(w, w2);
		h = Math.min(h, h2);
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Color cor2 = pr2.getColor(i, j);
				
				double red1 = cor.getRed()*op1;
				double red2 = cor2.getRed()*op2;
				
				double green1 = cor.getGreen()*op1;
				double green2 = cor2.getGreen()*op2;
				
				double blue1 = cor.getBlue()*op1;
				double blue2 = cor2.getBlue()*op2;
				
				double r = red1+red2;
				double g = green1+green2;
				double b = blue1+blue2;
				
				Color corNova = new Color(r, g, b, 1);
				pw.setColor(i, j, corNova);
			}
		}
		
		return wi;
	}
	
	public static Image subtracao(Image img, Image img2) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		w = Math.min(w, w2);
		h = Math.min(h, h2);
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				Color cor = pr.getColor(i, j);
				Color cor2 = pr2.getColor(i, j);
				
				double red = cor.getRed()-cor2.getRed();
				double green = cor.getGreen()-cor2.getGreen();
				double blue = cor.getBlue()-cor2.getBlue();
				
				red = red<0?0:red;
				green = green<0?0:green;
				blue = blue<0?0:blue;
				
				Color corNova = new Color(red, green, blue, 1);
				pw.setColor(i, j, corNova);
			}
		}
		
		return wi;
	}
	
	public static Image ruido3x3 (Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w-1 ; i++) {
			for(int j=0; j<h-1; j++) {
				
				double red = 0;
				double green = 0;
				double blue = 0;
				
				double[] r = new double[8];
				double[] g= new double[8];
				double[] b = new double[8];
				Color cor = pr.getColor(i, j);
				
				if (i == 0) {
					if (j == 0) {
						cor = pr.getColor(0, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(1, 0);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(1, 1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}else if(j == h) {
						cor = pr.getColor(0, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(1, j-1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}else {
						cor = pr.getColor(0, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(0, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(1, j-1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
						
						cor = pr.getColor(1, j+1);
						r[4] = cor.getRed();
						g[4] = cor.getGreen();
						b[4] = cor.getBlue();
					}
				}else if(i == w) {
					if (j == 0) {
						cor = pr.getColor(i, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, 0);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, 1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
					}else if(j == h) {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}else {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i-1, j+1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[4] = cor.getRed();
						g[4] = cor.getGreen();
						b[4] = cor.getBlue();
					}
				}else {
					if (j == 0) {
						cor = pr.getColor(i, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i+1, j+1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
						
						cor = pr.getColor(i-1, j+1);
						r[4] = cor.getRed();
						g[4] = cor.getGreen();
						b[4] = cor.getBlue();
						
					}else if (j == h) {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i+1, j-1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[4] = cor.getRed();
						g[4] = cor.getGreen();
						b[4] = cor.getBlue();
					}else {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i-1, j+1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[4] = cor.getRed();
						g[4] = cor.getGreen();
						b[4] = cor.getBlue();
						
						cor = pr.getColor(i+1, j-1);
						r[5] = cor.getRed();
						g[5] = cor.getGreen();
						b[5] = cor.getBlue();
						
						cor = pr.getColor(i+1, j+1);
						r[6] = cor.getRed();
						g[6] = cor.getGreen();
						b[6] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[7] = cor.getRed();
						g[7] = cor.getGreen();
						b[7] = cor.getBlue();
					}
				}
				
				ordenaNr(r);
				ordenaNr(g);
				ordenaNr(b);
				
				//Mediana
				if (r.length == 8) {
					red = (r[3]+r[4])/2;
					green = (g[3]+g[4])/2;
					blue = (b[3]+b[4])/2;
				}else if(r.length == 5) {
					red = r[2];
					green = g[2];
					blue = b[2];
				}else {
					red = r[1];
					green = g[1];
					blue = b[1];
				}
				
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}

	public static Image ruidoX (Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		double red = 0;
		double green = 0;
		double blue = 0;
		
		double[] r = new double[4];
		double[] g= new double[4];
		double[] b = new double[4];
		
		for(int i=0; i<w-1; i++) {
			for(int j=0; j<h-1; j++) {

				Color cor = pr.getColor(i, j);
				
				if (j==0) {
					if (i==0) {
						cor = pr.getColor(1, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
					}else if(i==w){
						cor = pr.getColor(i-1, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
					}else{
						cor = pr.getColor(i-1, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i+1, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}
				}else if(j==h){
					if (i==0) {
						cor = pr.getColor(1, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
					}else if(i==w) {
						cor = pr.getColor(i-1, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
					}else {
						cor = pr.getColor(i-1, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i+1, j-1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}
				}else{
					if (i==0) {
						cor = pr.getColor(i+1, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i+1, j-1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}else if(i==w) {
						cor = pr.getColor(i-1, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}else{
						cor = pr.getColor(i-1, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i+1, j-1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j-1);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i+1, j+1);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
					}
				}
				
				ordenaNr(r);
				ordenaNr(g);
				ordenaNr(b);
				
				//cor = pr.getColor(i,j);
				
				/*
				int tm = 2;
				if (r.length < 4) {
					tm = 1;
				}
				
				if (r[tm] < cor.getRed()) {
					red = r[tm];
				}
				
				if (g[tm] < cor.getGreen()) {
					green = g[tm];
				}
				
				if (b[tm] < cor.getBlue()) {
					blue = b[tm];
				}
				*/
				
				//Mediana
				if (r.length == 4) {
					red = (r[1]+r[2])/2;
					green = (g[1]+g[2])/2;
					blue = (b[1]+b[2])/2;
				}else if(r.length == 2) {
					red = (r[0]+r[1])/2;
					green = (g[0]+g[1])/2;
					blue = (b[0]+b[1])/2;
				}else {
					red = r[0];
					green = g[0];
					blue = b[0];
				}
								
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}	

	public static Image ruidoCruz (Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		
		double red = 0;
		double green = 0;
		double blue = 0;
		
		double[] r = new double[5];
		double[] g= new double[5];
		double[] b = new double[5];
		
		for(int i=0; i<w-1 ; i++) {
			for(int j=0; j<h-1; j++) {
				
				Color cor = pr.getColor(i, j);
				
				cor = pr.getColor(i, j);
				r[0] = cor.getRed();
				g[0] = cor.getGreen();
				b[0] = cor.getBlue();
				
				if (i == 0) {
					if (j == 0) {
						cor = pr.getColor(0, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(1, 0);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}else if(j == h) {
						cor = pr.getColor(0, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}else {
						cor = pr.getColor(0, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(0, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}
				}else if(i == w) {
					if (j == 0) {
						cor = pr.getColor(i, 1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, 0);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
					}else if(j == h) {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
					}else {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}
				}else {
					if (j == 0) {
						cor = pr.getColor(i, j+1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
					}else if (j == h) {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
					}else {
						cor = pr.getColor(i, j-1);
						r[0] = cor.getRed();
						g[0] = cor.getGreen();
						b[0] = cor.getBlue();
						
						cor = pr.getColor(i, j+1);
						r[1] = cor.getRed();
						g[1] = cor.getGreen();
						b[1] = cor.getBlue();
						
						cor = pr.getColor(i-1, j);
						r[2] = cor.getRed();
						g[2] = cor.getGreen();
						b[2] = cor.getBlue();
						
						cor = pr.getColor(i+1, j);
						r[3] = cor.getRed();
						g[3] = cor.getGreen();
						b[3] = cor.getBlue();
					}
				}

				ordenaNr(r);
				ordenaNr(g);
				ordenaNr(b);
				
				/*
				if (r[r.length/2] < cor.getRed()) {
					red = r[r.length/2];
				}
				
				if (g[r.length/2] < cor.getGreen()) {
					green = g[r.length/2];
				}
				
				if (b[r.length/2] < cor.getBlue()) {
					blue = b[r.length/2];
				}
				*/
				
				//Mediana
				if (r.length == 4) {
					red = (r[1]+r[2])/2;
					green = (g[1]+g[2])/2;
					blue = (b[1]+b[2])/2;
				}else if(r.length == 3) {
					red = (r[0]+r[1])/2;
					green = (g[0]+g[1])/2;
					blue = (b[0]+b[1])/2;
				}else {
					red = (r[0]+r[1])/2;
					green = (g[0]+g[1])/2;
					blue = (b[0]+b[1])/2;
				}
				
				Color corNova = new Color(red, green, blue, cor.getOpacity());
				pw.setColor(i, j, corNova);
			}
		}
		return wi;
	}

	static void ordenaNr(double[] v){
		int pos = 0;
		double ultima = v.length-1;
		double aux;
		
		while (ultima != 0){
			while (pos != ultima) {
				if (v[pos] > v[pos+1]) {
					aux = v[pos];
					v[pos] = v[pos+1];
					v[pos+1] = aux;
				}
				pos++;
			}
			pos = 0;
			ultima--;
		}
	}

	public static Image desafio (Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for(int i=0; i<w ; i++) {
			for(int j=0; j<h; j++) {
				if (i <= w/3) {
					Color cor = pr.getColor(i, j);
					Color corNova = new Color(cor.getRed()*0.3, cor.getGreen()*0.4, cor.getBlue()*0.3, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}else if(i <= w/3*2) {
					Color cor = pr.getColor(i, j);
					Color corNova = null;
					if (cor.getRed()>(170/250)) 
						corNova = new Color(1, 1, 1, cor.getOpacity());
					else 
						corNova = new Color(0, 0, 0, cor.getOpacity());
						pw.setColor(i, j, corNova);
				}else {
					Color cor = pr.getColor(i, j);
					double red = 1 - cor.getRed();
					double green = 1 - cor.getGreen();
					double blue = 1 - cor.getBlue();
					Color corNova = new Color(red, green, blue, cor.getOpacity());
					pw.setColor(i, j, corNova);
				}
				
			}
		}
		
		return wi;
	}
	
}
