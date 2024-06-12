package org.orinn.tuchetao.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.orinn.tuchetao.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataFile {

    private static final Logger logger = Logger.getLogger(DataFile.class.getName());

    public static File getFile(String fileName) {
        File dataFolder = Main.getInstance().getDataFolder(); // Lấy thư mục data của plugin
        File dataDirectory = new File(dataFolder, "data"); // Tạo đối tượng File cho thư mục "data"
        if (!dataDirectory.exists()) {
                    dataDirectory.mkdirs(); // Tạo thư mục "data" nếu nó chưa tồn tại
        }

        File file = new File(dataDirectory, fileName + ".yml"); // Tạo đối tượng File cho file data

        if (!file.exists()) {
            createDefaultFile(file);
        }

        return file;
    }

    private static void createDefaultFile(File file) {
        try {
            if (!file.exists()) { // Kiểm tra xem file đã tồn tại hay chưa
                boolean created = file.createNewFile();
                if (created) {
                    logger.info("Created new file: " + file.getName());
                    addDefaultData(file);
                } else {
                    logger.warning("File already exists: " + file.getName());
                }
            } else {
                logger.info("File already exists: " + file.getName());
                addDefaultData(file); // Thêm dữ liệu mặc định nếu file đã tồn tại
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create new file: " + file.getName(), e);
        }
    }

    public static void addDefaultData(File file) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        String[] sections = {"storage"};

        // Duyệt qua danh sách và tạo section nếu chưa tồn tại
        for (String section : sections) {
            if (!yaml.isConfigurationSection(section)) {
                yaml.createSection(section);
            }
        }

        // Giả sử dropsList được khai báo ở đâu đó trong plugin
        // ...
        for (String material : DropsList.dropsList) {
            if (!yaml.contains("storage." + material)) {
                yaml.set("storage." + material, 0);
            }
        }

        try {
            yaml.save(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save default data to file: " + file.getName(), e);
        }
    }
}