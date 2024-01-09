package com.lyr.client;
import com.lyr.parser.JsonParser;
import com.lyr.parser.XmlParser;
import com.lyr.pojo.User;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * 客户端
 * @author yunruili
 * @date 2024/01/09 19:46
 */
public class Client {
    private final Socket socket;
    private final int id;

    static Logger logger = Logger.getLogger(Client.class.getName());

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.id = new Random().nextInt(90000) + 10000; // 生成一个五位数的ID
    }

    public void send(String filePath) throws IOException {
        // 读取文件内容
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        // 使用Base64算法加密
        String encodedContent = Base64.getEncoder().encodeToString(fileContent);
        // 获取文件的后缀名
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
        // 发送到服务器
        this.socket.getOutputStream().write((this.id + " " + fileExtension + " " + encodedContent).getBytes());
    }

    public void show(String filePath) {
        // 判断文件类型
        if (filePath.endsWith(".xml")) {
            User user = XmlParser.parseXmlToUser(filePath);
            String userString = Objects.requireNonNull(user).toString();
            logger.info(userString);
        } else if (filePath.endsWith(".json")) {
            User user = JsonParser.parseJsonToUser(filePath);
            String userString = Objects.requireNonNull(user).toString();
            logger.info(userString);
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 12345); // 假设服务器在本地的12345端口监听

        Scanner scanner = new Scanner(System.in);
        int j = 0;
        do {
            j++;
            logger.info("Enter command: ");
            String command = scanner.nextLine();

            if (command.startsWith("send ")) {
                String filePath = command.substring(11);
                client.send(filePath);
            } else if (command.startsWith("show ")) {
                String filePath = command.substring(5);
                if ("id".equals(filePath)) {
                    String clientMsg = "Client ID: " + client.id;
                    logger.info(clientMsg);
                } else {
                    client.show(filePath);
                }
            } else {
                logger.info("Unknown command");
            }
        } while (j != Integer.MAX_VALUE);
    }
}