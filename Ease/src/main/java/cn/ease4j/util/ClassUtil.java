package cn.ease4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作工具
 */
public class ClassUtil {
    //获取日志打印的容器
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
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,Boolean isInitialized){
        Class<?> clazz=null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 获取包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            //URL类可以直接根据文件路径对文件进行读写操作
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll(".", "/"));
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(null != url){
                    String protocol = url.getProtocol();
                    //如果是常规的文件，则调用addClass方法提取class文件，加入classSet集合
                    if(protocol.equals("file")){
                        String packagePath = url.getPath().replaceAll("20%", " ");
                        //
                        addClass(classSet,packagePath,packageName);
                    }
                    //如果是jar包类型的文件
                    else if(protocol.equals("jar")){

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        //筛选文件列表（包括class文件、目录）
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith("class")) || file.isDirectory();
            }
        });
        for (File f : files) {
            String fileName=f.getName();
            if(f.isFile()){
                String className=fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(packageName)){
                    className=packageName+"."+className;
                }
                doAddClass(classSet,className);
            }
            else{
                String subPackagePath=fileName;
                if(StringUtil.isNotEmpty(subPackagePath)){
                    subPackagePath=packagePath+"/"+subPackagePath;
                }
                String subPackageName=fileName;
                if(StringUtil.isNotEmpty(subPackageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> clazz = loadClass(className, false);
        classSet.add(clazz);
    }


}
