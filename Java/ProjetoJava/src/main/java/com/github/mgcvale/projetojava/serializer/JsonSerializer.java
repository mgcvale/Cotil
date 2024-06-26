package com.github.mgcvale.projetojava.serializer;

import com.github.mgcvale.projetojava.service.Service;
import com.github.mgcvale.projetojava.utils.FileUtils;
import com.google.gson.Gson;

import java.io.*;


public class JsonSerializer {

    private static Gson gson = new Gson();
    private static String dataDir = System.getProperty("user.home") + "/JavaProjects/json";

    public static String getDataDir() {
        return dataDir;
    }

    public static void setDataDir(String dataDir) {
        JsonSerializer.dataDir = dataDir;
    }

    public static String serializeToString(Object service) {
        String json = gson.toJson(service);
        return json;
    }

    public static void serializeService(Service service) throws IOException {
        //serialize class and get json file
        String json = serializeToString(service);
        File jsonFile = new File(getFileDir(service));
        jsonFile.createNewFile();

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(json);
        fw.close();
    }

    public static void serializeObject(Object object, String name) throws IOException{
        String json = serializeToString(object);
        File jsonFile = new File((dataDir + "/" + name + ".json"));

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(json);
        fw.close();
    }

    public static <T> T importServiceJson(String serviceName, Class<T> classOfT) throws IOException {
        //read the file into a string
        String json = FileUtils.readAll(getFileDir(serviceName));
        return gson.fromJson(json, classOfT);
    }

    public static <T> T importJson(String jsonName, Class<T> classOfT) throws IOException {
        String json = FileUtils.readAll(dataDir + "/" + jsonName + ".json");
        return gson.fromJson(json, classOfT);
    }

    private static String getFileDir(Service service) {
        return getFileDir(service.getObjectName());
    }

    private static String getFileDir(String className) {
        return dataDir + "/" + className + ".json";
    }

}
