package br.ufc.great.es.api.demo.utils;

import java.nio.file.FileSystems;

public class Constantes {
	public static final int itensPorPagina=10;
	public static String userDirectory = System.getProperty("user.dir");
	public static String uploadDirectory = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator()+ "uploads";
	public static final String filePathQRCode = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "coupons";
	public static String uploadUserDirectory = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "users";
	public static String picturesDirectory = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "uploads" + FileSystems.getDefault().getSeparator() + "pictures";
}
