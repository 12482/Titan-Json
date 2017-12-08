package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsConn {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://cmpc2:9000");
		FileSystem fileSystem=FileSystem.get(conf);
//		System.out.println(fileSystem.getUsed());
//		FSDataOutputStream out=fileSystem.create(new Path("/ddata")) ;
		
		File file2 = new File("gson");
		 //导出
        FSDataInputStream inputStream = fileSystem.open(new Path("hdfs://cmpc2:9000/gson"));  
        FileOutputStream outputStream = new FileOutputStream(file2.getAbsolutePath());  
        IOUtils.copyBytes(inputStream, outputStream, 1024, true);  
		System.err.println("succ ..");
	}

}
