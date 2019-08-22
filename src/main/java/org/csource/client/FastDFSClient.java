package org.csource.client;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FastDFSClient {
	private static final String CONFIG_FILENAME = "fastdfs-client.properties";
	private static final String GROUP_NAME = "group1";
	private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;

    static{
    	try {
			ClientGlobal.initByProperties(CONFIG_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
    }
    public FastDFSClient() throws Exception {
	   trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
	   trackerServer = trackerClient.getConnection();
	   storageServer = trackerClient.getStoreStorage(trackerServer);;
	   storageClient = new StorageClient(trackerServer, storageServer);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @return
     */
    public  String[] uploadFile(File file, String fileName) {
        return uploadFile(file,fileName,null);
    }


    public byte[] toByteArray(File file) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            int nRead;
            byte[] data = new byte[4096];
            while ((nRead = is.read(data, 0, 4096)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toByteArray();
    }
    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @param metaList 文件元数据
     * @return
     */
    public  String[] uploadFile(File file, String fileName, Map<String,String> metaList) {
        try {
            byte[] buff = toByteArray(file);
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<String,String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name,value);
                }
            }
            return storageClient.upload_file(GROUP_NAME,buff,fileName,nameValuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     * @param buff 文件字节
     * @param fileName 文件名
     * @param metaList 文件元数据
     * @return
     */
    public  String[] uploadFile(byte[] buff, String fileName, Map<String,String> metaList) {
        try {
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<String,String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name,value);
                }
            }
            return storageClient.upload_file(GROUP_NAME,buff,fileName,nameValuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public Map<String,String> getFileMetadata(String groupname,String fileId) {
        try {
            NameValuePair[] metaList = storageClient.get_metadata(groupname,fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public int deleteFile(String groupname,String fileId) {
        try {
            return storageClient.delete_file(groupname,fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param outFile 文件下载保存位置
     * @return
     */
    public  int downloadFile(String groupName,String fileId, File outFile) {
        FileOutputStream fos = null;
        try {
            byte[] content = storageClient.download_file(groupName,fileId);
            fos = new FileOutputStream(outFile);
            InputStream ips = new ByteArrayInputStream(content);
            IOUtils.copy(ips,fos);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
    	FastDFSClient client = new FastDFSClient();
        File file = new File("_image.PNG");
        client.downloadFile("group1", "M00/00/00/CqQKBF1ABFmAagcSAAO_VDOnJEY969.pdf", file);
//    	File file = new File("C:\\Users\\CMIC\\Desktop\\dzqz\\学习文档\\SpringBoot.pdf");
//        String[] result = client.uploadFile(file, "pdf");
//        System.out.println(result.length);
//        System.out.println(result[0]);
//        System.out.println(result[1]);
//
//        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

//        File downfile = new File("C:\\Users\\CMIC\\Desktop\\dzqz\\dowoload\\test_download.pdf");
//        if (!downfile.exists()) {
//            boolean success = downfile.createNewFile();
//            if (!success) {
//                return;
//            }
//        }
//        client.downloadFile(GROUP_NAME, "M00/00/00/CpljI1x8sGeAPx9fAJBUtleJtLM276.pdf", downfile);
    }
}
