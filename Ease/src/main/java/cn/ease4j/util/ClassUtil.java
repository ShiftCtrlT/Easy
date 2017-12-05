package cn.ease4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className
     * @param ifInitialize
     * @return
     */
    public static Class<?> loadClass(String className,boolean ifInitialize){
        Class<?> clz = null;
        try {
            clz = Class.forName(className,ifInitialize,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return clz;
    }

    /**
     * 获取指定包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        //todo
        //用于存储类名的集合
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources("".replaceAll(".", "/"));
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                //非空校验
                if(null != url){
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        String packagePath = url.getPath().replace("%20", "");
                        addClass(classSet,packagePath,packageName);
                    }
                    else if(protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if(null !=jarURLConnection){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(null != jarFile){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while(jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String name = jarEntry.getName();
                                    if(name.endsWith(".class")){
                                        String className = name.substring(0,name.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet,className);

                                    }

                                }
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            LOGGER.error("get class set failure",e);
        }

        return null;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(packageName)){
                    doAddClass(classSet,className);
                }
            }
            else{
                String subPackagePath = fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if(StringUtil.isNotEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                //递归
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> clz = loadClass(className, false);
        classSet.add(clz);
    }

}
