package quepierts.iter.util;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceLoader {
    private static final Logger LOGGER = IterLogManager.getLogger();
    private static final ClassLoader classLoader = ResourceLoader.class.getClassLoader();

    public static InputStream getResource(String path) {
        return classLoader.getResourceAsStream(path);
    }

    public static Map<String, InputStream> getAllResources(String path, CharSequence fileType) throws IOException {
        Map<String, InputStream> result = new HashMap<>();

        if (!fileType.toString().startsWith(".")) {
            fileType = "." + fileType;
        }

        URL url = classLoader.getResource(path);
        if (url == null) {
            throw new IOException("Nonexistent path");
        }

        File root = new File(url.getPath());
        String rootPath = root.getPath() + "\\";
        List<File> fileList = new ArrayList<>();
        searchFiles(root, fileList, fileType);

        InputStream stream;

        for (File file : fileList) {
            String filePath = file.getPath().replace(rootPath, "").replace("\\", "/");
            String name = path + "/" + filePath;
            stream = classLoader.getResourceAsStream(name);

             if (stream != null) {
                 result.put(filePath.replace(fileType, ""), stream);
             }
        }

        return result;
    }

    public static byte[] getResourceBytes(String path) throws IOException {
        InputStream resource = getResource(path);

        if (resource == null) {
            LOGGER.error("Nonexistent resource path: " + path);
            return null;
        } else {
            byte[] result = resource.readAllBytes();
            resource.close();
            return result;
        }
    }

    public static ClassLoader getClassLoader() {
        return classLoader;
    }

    private static void searchFiles(File root, List<File> fileList, CharSequence fileType) {
        File[] files = root.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                searchFiles(file, fileList, fileType);
            } else {
                if (file.getName().contains(fileType)) {
                    fileList.add(file);
                }
            }
        }
    }
}
