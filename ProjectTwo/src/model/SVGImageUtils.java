/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Bravo
 */
/**
 * Utility class for converting SVG images to ImageIcon objects.
 */
public class SVGImageUtils {
//Converts an SVG image from the specified URL to an ImageIcon.

    public static ImageIcon getSVGIcon(String svgUrl) {
        try {
            byte[] bytes = convertSVGToBytes(svgUrl);
            return new ImageIcon(bytes);
        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
            return null;
        }
    }
//Converts an SVG image from the specified URL to a byte array.

    private static byte[] convertSVGToBytes(String svgUrl) throws IOException, TranscoderException {
        SVGTranscoder transcoder = new SVGTranscoder();
        TranscoderInput input = new TranscoderInput(new URL(svgUrl).openStream());
        transcoder.transcode(input, null);

        BufferedImage bufferedImage = transcoder.getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }
//Custom SVG transcoder class for converting SVG to BufferedImage.

    private static class SVGTranscoder extends ImageTranscoder {

        private BufferedImage image = null;
//Creates a BufferedImage with specified width and height.

        @Override
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        //Writes the transcoded image to the BufferedImage.
        @Override
        public void writeImage(BufferedImage bufferedImage, TranscoderOutput transcoderOutput) throws TranscoderException {
            this.image = bufferedImage;
        }

        //Gets the transcoder output image.
        BufferedImage getImage() {
            return image;
        }
    }
}
