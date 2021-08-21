package com.cigarette.common.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author EdwardLee
 */
public class CheckCodeUtils {

    /**
     * 验证码位数
     */
    private static final int DEFAULT_LENGTH_OF_CODE = 5;

    /**
     * 验证码图形宽度
     */
    private static int DEFAULT_WIDTH = 100;

    /**
     * 验证码图形高度
     */
    private static int DEFAULT_HEIGHT = 38;

    public static Map<String, Object> addCheckCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return addCheckCode(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_LENGTH_OF_CODE, request, response);
    }

    public static Map<String, Object> addCheckCode(int width, int height, int lengthofcode, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //创建一个验证码图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //美化图片
        //获取画笔
        Graphics graphics = image.getGraphics();
        //填充背景色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        //画边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        //写验证码
        String str = "CDABEFvw6GHIopqrsJK23LMNW7OPQRXYxyz0145ZabcdefghijklmntuSTUV89";
        StringBuilder checkcode = new StringBuilder(lengthofcode);
        Random random = new Random();
        for (int i = 0; i < lengthofcode; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            int y = random.nextInt(height / 5);
            graphics.setFont(new Font("微软雅黑", 0, 20));
            graphics.drawString(c + "", (width / (lengthofcode * 2 + 1)) * (2 * i + 1), y + 25);
            checkcode.append(c);
        }
        //画干扰线
        graphics.setColor(Color.CYAN);
        for (int i = 0; i < lengthofcode * 2; i++) {
            int x1 = random.nextInt(str.length());
            int x2 = random.nextInt(str.length());
            int y1 = random.nextInt(str.length());
            int y2 = random.nextInt(str.length());
            graphics.drawLine(x1, y1, x2, y2);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("checkCode",checkcode.toString().toLowerCase());
        map.put("image",image);
        return map;
    }

}
