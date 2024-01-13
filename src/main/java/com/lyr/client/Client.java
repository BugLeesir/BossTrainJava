package com.lyr.client;
import com.lyr.parser.JsonParser;
import com.lyr.parser.XmlParser;
import com.lyr.pojo.User;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @class Client
 * @description 客户端,向服务器发送文件,并接收服务器返回的文件接收完成的消息,并显示文件内容或客户端ID,并将文件内容解析为User对象,并打印User对象的信息
 * @author yunruili
 * @date 2024/01/14  05:18
 * @version 1.0.0
 */
public class Client {
    private final Socket socket;
    private final int id;

    static Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * @description 构造方法,创建一个客户端,连接到指定的服务器的指定端口,并生成一个五位数的ID,用于标识客户端,并打印客户端ID
     * @author yunruili
     * @date 2024/01/14 05:38
     * @param host 服务器地址
     * @param port 端口号
     */
    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.id = new Random().nextInt(90000) + 10000; // 生成一个五位数的ID
    }

    /**
     * @description 向服务器发送文件,并接收服务器返回的文件接收完成的消息,并显示文件内容或客户端ID,并将文件内容解析为User对象,并打印User对象的信息
     * @author yunruili
     * @date 2024/01/14 05:39
     * @param filePath 文件路径
     */
    public void send(String filePath) throws IOException {
        // 读取文件内容
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        // 使用Base64算法加密
        String encodedContent = Base64.getEncoder().encodeToString(fileContent);
        // 获取文件的后缀名
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
        // 发送到服务器，并指定字符集编码为UTF-8
        this.socket.getOutputStream().write((this.id + " " + fileExtension + " " + encodedContent).getBytes(StandardCharsets.UTF_8));
        // 提示文件接收中
        logger.info("文件接收中");
        // 接收服务器的消息
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
        String messageFromServer;
        while ((messageFromServer = reader.readLine()) != null) {
            logger.info(messageFromServer);
            if ("文件接收完成".equals(messageFromServer)) {
                break;
            }
        }
    }

    /**
     * @description 显示文件内容或客户端ID,并将文件内容解析为User对象,并打印User对象的信息,
     * 如果文件类型不是xml或json,则提示错误,并退出程序,如果文件类型是xml或json,
     * 则将文件内容解析为User对象,并打印User对象的信息
     * @author yunruili
     * @date 2024/01/14 05:41
     * @param filePath 文件路径
     */
    public void show(String filePath) {
        // 判断文件类型
        if (filePath.endsWith(".xml")) {
            User user =  new XmlParser().parseToUser(filePath);
            String userString = Objects.requireNonNull(user).toString();
            logger.info(userString);
        } else if (filePath.endsWith(".json")) {
            User user = new JsonParser().parseToUser(filePath);
            String userString = Objects.requireNonNull(user).toString();
            logger.info(userString);
        }
    }

    /**
     * @description 主方法,创建一个客户端,连接到指定的服务器的指定端口
     * 案例：Client1 在控制台输入命令 show id 显示客户端ID 55652
     * Client2 在控制台输入命令 show id 显示客户端ID 12345
     * Client1 在控制台输入命令 send 55652 E:\BossTrain\BossTrainJava\file_data\sample.json 发送文件
     * 发送过程中报文经过Base64编码
     * 保存在项目根目录下的以客户端ID命名的文件夹中\12345\receivedFile.json
     * Client2 在控制台输入命令 show **\12345\receivedFile.json 显示文件内容
     * @author yunruili
     * @date 2024/01/14 05:43
     * @param args 命令行参数
     */
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