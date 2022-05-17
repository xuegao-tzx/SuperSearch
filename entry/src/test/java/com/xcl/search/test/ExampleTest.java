package com.xcl.search.test;

import com.xcl.imagetracer_mod.ImageTracer;
import org.junit.Test;

import java.util.HashMap;

public class ExampleTest {
    @Test
    public void onStart() throws Exception {
        // Options
        HashMap<String, Float> options = new HashMap<String, Float>();
        // Tracing
        options.put("ltres", 1f);
        options.put("qtres", 1f);
        options.put("pathomit", 8f);
        // Color quantization
        options.put("colorsampling", 1f); // 1f means true ; 0f means false: starting with generated palette
        options.put("numberofcolors", 16f);
        options.put("mincolorratio", 0.02f);
        options.put("colorquantcycles", 3f);
        // SVG rendering
        options.put("scale", 1f);
        options.put("roundcoords", 1f); // 1f means rounded to 1 decimal places, like 7.3 ; 3f means rounded to 3 places, like 7.356 ; etc.
        options.put("lcpr", 0f);
        options.put("qcpr", 0f);
        options.put("desc", 1f); // 1f means true ; 0f means false: SVG descriptions deactivated
        options.put("viewbox", 0f); // 1f means true ; 0f means false: fixed width and height
        // Selective Gauss Blur
        options.put("blurradius", 0f); // 0f means deactivated; 1f .. 5f : blur with this radius
        options.put("blurdelta", 20f); // smaller than this RGB difference will be blurred
        // Palette
        // This is an example of a grayscale palette
        // please note that signed byte values [ -128 .. 127 ] will be converted to [ 0 .. 255 ] in the getsvgstring function
        //下方的两个数字16请参照实际图片动态调整以实现最佳效果
        byte[][] palette = new byte[16][4];
        for (int colorcnt = 0; colorcnt < 16; colorcnt++) {
            palette[colorcnt][0] = (byte) (-128 + colorcnt * 32); // R
            palette[colorcnt][1] = (byte) (-128 + colorcnt * 32); // G
            palette[colorcnt][2] = (byte) (-128 + colorcnt * 32); // B
            palette[colorcnt][3] = (byte) 127;              // A
        }

        ImageTracer.saveString(
                "src\\main\\resources\\base\\media\\ic_close_black_24dp.svg",
                ImageTracer.imageToSVG("src\\main\\resources\\base\\media\\ic_close_black_24dp.png", options, palette)
        );
    }
}
