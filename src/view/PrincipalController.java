package view;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.w3c.dom.css.Rect;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdi.Pdi;

public class PrincipalController {
	
	@FXML Label lblR;
	@FXML Label lblG;
	@FXML Label lblB;
	
	@FXML ImageView imgw1;
	@FXML ImageView imgw2;
	@FXML ImageView imgw3;
	
	@FXML TextField r;
	@FXML TextField g;
	@FXML TextField b;
	@FXML TextField op1;
	@FXML TextField op2;
	@FXML Slider limiar;
	@FXML ComboBox<String> cbCores;
	@FXML ComboBox<String> cbCores1;
	@FXML TextField pixels;
	@FXML TextField pixels1;
	
	private double clicouX, clicouY, soltouX, soltouY;
	private Image imagem1, imagem2, imagem3;
	private File f;
	
	@FXML
	public void initialize() {
		/*ArrayList<String> cores = new ArrayList<String>();
		cores.add("Vermelho");
		cores.add("Azul");
		cores.add("Verde");*/
		
		cbCores.getItems().add("Azul");
		cbCores.getItems().add("Vermelho");
		cbCores.getItems().add("Verde");
		
		cbCores1.getItems().add("Azul");
		cbCores1.getItems().add("Vermelho");
		cbCores1.getItems().add("Verde");
		
		//cbCores.setItems(FXCollections.observableArrayList(cbCores));
	}
	
	@FXML
	public void limpaLabels() {
		lblR.setText("R:");
		lblG.setText("G:");
		lblB.setText("B:");
	}
	
	@FXML
	public void processamento(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		imagem3 = Pdi.cinza(imagem1, 30, 30, 40);
		imagem3 = Pdi.limiar(imagem3, 0.58);
		imagem3 = Pdi.ruido3x3(imagem3);
		
		int width = (int) imagem3.getWidth();
	    int height = (int) imagem3.getHeight();
	    byte[] buffer = new byte[width * height * 4];

	    PixelReader reader = imagem3.getPixelReader();
	    WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
	    reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

	    Mat mat = new Mat(height, width, CvType.CV_8UC4);
	    mat.put(0, 0, buffer);
		
		Imgproc.erode(mat, mat, new Mat(),new Point(-1, -1), 1);
		Imgproc.dilate(mat, mat, new Mat(),new Point(-1, -1), 3);
		
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", mat, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		
		atualizaImg3();
	}
	

	
	@FXML
	public void identificaRostos(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, 
					new Point(rect.x, rect.y),
					new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0),3);
		}
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void erosao(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		Imgproc.erode(image, image, new Mat(),new Point(-1, -1), 1);
		
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void dilatacao(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		Imgproc.dilate(image, image, new Mat(),new Point(-1, -1), 3);
		
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void canny(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
		Imgproc.blur(image, image, new Size(3, 3));
		Imgproc.Canny(image, image, 9, 9*3, 5, false);
		
		Mat dest = new Mat();
		Core.add(dest, Scalar.all(0), dest);
		
		image.copyTo(dest, image);
		
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void laplace(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		
        Imgproc.GaussianBlur(image, image, new Size(3, 3), 0, 0, Core.BORDER_DEFAULT);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
        
        Imgproc.Laplacian(image, image, image.depth(), 3, 1, 0, Core.BORDER_DEFAULT);
        
        Core.convertScaleAbs(image, image);
        
        MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void sobel(ActionEvent event) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		
		/*Mat kernel = new Mat(9, 9, CvType.CV_32F){
	            {
	               put(0,0,-1);
	               put(0,1,0);
	               put(0,2,1);

	               put(1,0-2);
	               put(1,1,0);
	               put(1,2,2);

	               put(2,0,-1);
	               put(2,1,0);
	               put(2,2,1);
	            }
	    };*/

	    Imgproc.blur(image, image, new Size(3, 3));
	    Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
	    
        Mat abs_grad_x = new Mat(), abs_grad_y = new Mat();
        
        Imgproc.Sobel(image, image, image.depth(), 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
        Imgproc.Sobel(image, image, image.depth(), 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);
        
        Core.convertScaleAbs( image, abs_grad_x );
        Core.convertScaleAbs( image, abs_grad_y );
        Core.addWeighted( abs_grad_x, 0.5, abs_grad_y, 0.5, 0, new Mat());
        
        MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		atualizaImg3();
	}
	
	@FXML
	public void grade() {
		String selecao = cbCores1.getSelectionModel().getSelectedItem();
		imagem3 = Pdi.questao1(imagem1,selecao,Integer.parseInt(pixels1.getText()));
		atualizaImg3();
	}
	
	@FXML
	public void questao2() {
		imagem3 = Pdi.questao2(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void tonsCinza() {
		imagem3 = Pdi.cinza(imagem1, Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText()));
		atualizaImg3();
	}
	
	@FXML
	public void limiarizacao() {
		System.out.println("limiar: "+limiar.getValue());
		imagem3 = Pdi.limiar(imagem1, limiar.getValue()/255);
		atualizaImg3();
	}
	
	@FXML
	public void negativa() {
		imagem3 = Pdi.negativa(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void desafio() {
		imagem3 = Pdi.desafio(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void ruido3x3() {
		imagem3 = Pdi.ruido3x3(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void ruidoX() {
		imagem3 = Pdi.ruidoX(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void ruidoCruz() {
		imagem3 = Pdi.ruidoCruz(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void segmentacao() {
		imagem3 = Pdi.segmentacao(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void moldura(ActionEvent event) {
		String selecao = cbCores.getSelectionModel().getSelectedItem();
		imagem3 = Pdi.moldura(imagem1,selecao,Integer.parseInt(pixels.getText()));
		atualizaImg3();
	}
	
	@FXML
	public void dividir(ActionEvent event) {
		imagem3 = Pdi.dividir(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void identificar(ActionEvent event) {
		imagem3 = Pdi.identificar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void diminuir(ActionEvent event) {
		imagem3 = Pdi.diminuir(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void aumentar(ActionEvent event) {
		imagem3 = Pdi.aumentar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void girar(ActionEvent event) {
		imagem3 = Pdi.girar(imagem1);
		atualizaImg3();
	}
	
	@FXML
	public void adicao(ActionEvent event) {
		imagem3 = Pdi.adicao(imagem1, imagem2, Double.parseDouble(op1.getText())/100, Double.parseDouble(op2.getText())/100);
		atualizaImg3();
	}
	
	@FXML
	public void subtracao(ActionEvent event) {
		imagem3 = Pdi.subtracao(imagem1, imagem2);
		atualizaImg3();
	}
	
	@FXML
	public void clicou(MouseEvent evt) {
		clicouX = evt.getX();
		clicouY = evt.getY();
	}
	
	@FXML
	public void soltou(MouseEvent evt) {
		soltouX = evt.getX();
		soltouY = evt.getY();
		ImageView iw = (ImageView)evt.getTarget();
		if (iw.getImage()!=null) {
			imagem3 = Pdi.desenha(clicouX, clicouY, soltouX, soltouY, iw.getImage());
			atualizaImg3();
		}
	}
	
	public void gerarHistograma(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HistogramaModal.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histograma");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
			stage.show();
			
			HistogramaModalController controller = (HistogramaModalController)loader.getController();
			if(imagem1 != null)
				Pdi.getGrafico(imagem1, controller.histograma1);
			if(imagem2 != null)
				Pdi.getGrafico(imagem2, controller.histograma2);
			if(imagem3 != null)
				Pdi.getGrafico(imagem3, controller.histograma3);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void equalizar(ActionEvent event) {
		imagem3 = Pdi.equalizar(imagem1);
		atualizaImg3();
	}
	
	private void atualizaImg3() {
		imgw3.setImage(imagem3);
		imgw3.setFitWidth(imagem3.getWidth());
		imgw3.setFitHeight(imagem3.getHeight());
	}
	
	@FXML
	public void abreImg1() {
		f = selecionaImagem();
		if (f != null) {
			imagem1 = new Image(f.toURI().toString());
			imgw1.setImage(imagem1);
			imgw1.setFitWidth(imagem1.getWidth());
			imgw1.setFitHeight(imagem1.getHeight());
			
			imagem3 = new Image(f.toURI().toString());
			imgw3.setImage(imagem3);
			imgw3.setFitWidth(imagem3.getWidth());
			imgw3.setFitHeight(imagem3.getHeight());
		}
	}
	
	@FXML
	public void abreImg2() {
		f = selecionaImagem();
		if (f != null) {
			imagem2 = new Image(f.toURI().toString());
			imgw2.setImage(imagem2);
			imgw2.setFitWidth(imagem2.getWidth());
			imgw2.setFitHeight(imagem2.getHeight());
		}
	}
	
	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				FileChooser.ExtensionFilter("Imagens","*.jpg","*.JPG","*.png","*.PNG","*.gif","*.GIF","*.bmp","*.BMP")
		);
		fileChooser.setInitialDirectory(new File("C:/Users/User/workspace/Pdi2018A2/imagens"));
		File imgSelec = fileChooser.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@FXML
	public void rasterImg(MouseEvent evt) {
		ImageView iw = (ImageView)evt.getTarget();
		if(iw.getImage()!=null)
			verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
	}
	
	private void verificaCor(Image img, int x, int y) {
		try {
			if(x<255 && y<255) {
				Color cor = img.getPixelReader().getColor(x-1, y-1);
				lblR.setText("R: "+(int) (cor.getRed()*255));
				lblG.setText("G: "+(int) (cor.getGreen()*255));
				lblB.setText("B: "+(int) (cor.getBlue()*255));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void salvar() {
		if(imagem3 != null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new 
					FileChooser.ExtensionFilter("Imagens","*.jpg","*.JPG","*.png","*.PNG","*.gif","*.GIF","*.bmp","*.BMP")
			);
			fileChooser.setInitialDirectory(new File("C:/Users/User/workspace/Pdi2018A2/imagens"));
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				BufferedImage bImg = SwingFXUtils.fromFXImage(imagem3, null);
				try {
					ImageIO.write(bImg, "PNG", file);
					exibeMsg("Salvar imagem", "Imagem salva com sucesso.", null, AlertType.CONFIRMATION);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				exibeMsg("Salvar imagem","Não é possível salvar a imagem","Não há nenhuma imagem modificada." , AlertType.ERROR);
			}
		}
	}
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
}
