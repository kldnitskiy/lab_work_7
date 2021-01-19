package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import shared.city.City;

import java.awt.print.PrinterException;
import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;

public class FileWorker {
    private static final String path = new String("./collection.xml");
    private final File file;
    public FileWorker(File file){ this.file=file; }
    public File getFile() {return file;}
    public static String getFile_name() {
        return path;
    }
    private boolean checkAccess(File file) { return (file.canWrite() && file.canRead() && file.exists()); }
    public PriorityQueue<City> parse(File file) {
        try(FileInputStream fis = new FileInputStream(file)) {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = inputStreamToString(fis);
            City[] value = xmlMapper.readValue(xml, City[].class);
            return new PriorityQueue<City>(Arrays.asList(value));
        }
        catch (Exception e) {
            return new PriorityQueue<City>();
        }

    }
    public String inputStreamToString(InputStream is) throws IOException {
        return String.join("", new BufferedReader(new InputStreamReader(is)).lines().toArray(String[]::new));
    }
    public void serialize(PriorityQueue<City> cities) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        City[] blyat = cities.toArray(new City[0]);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            xmlMapper.writeValue(fos, blyat);
        }
    }
    public void saveCollection(PriorityQueue<City> cities){
        try {
            serialize(cities);
        } catch (IOException e) {
           System.out.println("Возникла ошибка при сохранении файла.");
        }
    }
}
