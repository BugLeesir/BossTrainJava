package com.lyr.server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @class Server
 * @description 服务器端,接收客户端发送的文件,并保存到当前目录下的以客户端ID命名的文件夹中,并向客户端发送文件接收完成的消息
 * @author yunruili
 * @date 2024/01/14  05:17
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
public class Server {
    private final ServerSocket serverSocket;
    private final ExecutorService threadPool;

    /**
     * @description 构造方法,创建一个服务器,监听指定端口
     * @author yunruili
     * @date 2024/01/14 05:23
     * @param port
     * @return * @return: null
     */
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.threadPool = Executors.newFixedThreadPool(10); // 创建一个线程池，最多可以同时处理10个客户端的连接
    }

    /**
     * @description 开始接收客户端的连接,并在一个新的线程中处理这个客户端的请求
     * @author yunruili
     * @date 2024/01/14 05:26
     * @return * @return: void
     */
    public void start() throws IOException {
        int j = 0;
        do {
            j++;
            Socket clientSocket = this.serverSocket.accept(); // 接收一个客户端的连接
            this.threadPool.execute(() -> {
                try {
                    handleClient(clientSocket); // 在一个新的线程中处理这个客户端的请求
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } while (j != Integer.MAX_VALUE);
    }

    /**
     * @description 处理客户端的请求,并将文件保存到当前目录下的以客户端ID命名的文件夹中,并向客户端发送文件接收完成的消息
     * @author yunruili
     * @date 2024/01/14 05:27
     * @param clientSocket
     * @return * @return: void
     */
    private void handleClient(Socket clientSocket) throws IOException {
        // 读取客户端发送的数据
        byte[] buffer = new byte[1024];
        int len = clientSocket.getInputStream().read(buffer);
        String receivedData = new String(buffer, 0, len, StandardCharsets.UTF_8);
        // 分割ID、文件类型和文件内容
        String[] parts = receivedData.split(" ", 3);
        String id = parts[0];
        String fileExtension = parts[1];
        String encodedContent = parts[2];
        // 使用Base64算法解密
        byte[] fileContent = Base64.getDecoder().decode(encodedContent);
        // 保存到当前目录下的以客户端ID命名的文件夹中
        Files.createDirectories(Paths.get("./" + id));
        Files.write(Paths.get("./" + id + "/receivedFile." + fileExtension), fileContent);
        // 向客户端发送文件接收完成的消息
        OutputStream outputStream = clientSocket.getOutputStream();
        String message = "文件接收完成\n";
        // 将消息写入TCP缓冲区，并指定UTF-8编码
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(12345); // 创建一个服务器，监听12345端口
        server.start(); // 开始接收客户端的连接
    }
}