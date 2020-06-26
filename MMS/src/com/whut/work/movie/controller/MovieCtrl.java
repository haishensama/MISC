package com.whut.work.movie.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Movie;
import com.whut.work.movie.service.IMovieService;

@Controller
@RequestMapping("/movie")
public class MovieCtrl {
	
	@Autowired
	private IMovieService movieService;
	
	@RequestMapping(value="/addToMovie",method= RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addToMovie(String name, String director, String actor, Float score, String plot,Date premiere, String url){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = movieService.addToMovie(name, director, actor, score, plot, premiere, url);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：新增电影失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
		
	}

	@RequestMapping(value="/editOneMovie",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editOneMovie(int id, String name, String director, String actor, Float score, String plot, Date premiere, String url){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = movieService.editOneMovie(id, name, director, actor, score, plot, premiere, url);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：编辑电影失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
    }

	@RequestMapping(value="/deleteOneMovie",method= RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> deleteOneMovie(Integer id){
		 Map<String,Object> returnMap = new HashMap<String,Object>();

	        try {
	            Map<String,Object> result = movieService.deleteOneMovie(id);
	            returnMap.put("value", result);
	            returnMap.put("success", true);
	        } catch (Exception e) {
	            returnMap.put("message", "异常：操作失败!");
	            returnMap.put("success", false);
	            e.printStackTrace();
	        }
	        return returnMap;
	}
	
	@RequestMapping(value="/getOneMovie",method= RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> getOneMovie(Integer id){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try {
            Map<String,Object> result = movieService.getOneMovie(id);
            returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：操作失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
    
	  	@RequestMapping(value="/getMoviePageList",method= RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object>getMoviePageList(int currentPage, int pageSize,String blurName, String sortRuleArray){ 

	        Map<String,Object> returnMap = new HashMap<String,Object>();

	        try {
	            Page<Movie> result = movieService.getMoviePageList(currentPage, pageSize,blurName, sortRuleArray);
	            returnMap.put("page", result);
	            returnMap.put("success", true);
	        } catch (Exception e) {
	            returnMap.put("message", "异常：操作失败!");
	            returnMap.put("success", false);
	            e.printStackTrace();
	        }
	        return returnMap;
	    }
	  	
	  	@RequestMapping(value="/addOneMovie_pic",method= RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> addOneGoods_pic(HttpServletRequest request, @RequestParam("file") MultipartFile file, ModelMap model){
	        Map<String,Object> returnMap = new HashMap<String,Object>();
	        System.out.println(file.getName());
	        /*System.out.println("start-------------");
	        String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/goods-imgs/");//存储路径
	        System.out.println(ServletFileUpload.isMultipartContent(request));
	        System.out.println(savePath);
	        System.out.println(request);
	        try {
	            if (ServletFileUpload.isMultipartContent(request)) {
	                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	                System.out.println(items);
	                for (FileItem item : items) {
	                        System.out.println("helloosjfos");
	                        String fileType = item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();//文件类型
	                        String fileName = new Date().getTime() + "." + fileType; //保存的文件名
	                        String filePath = savePath + "\\" + fileName; //保存的文件路径
	                        System.out.println(fileType+fileName+filePath+"---------");
	                        System.out.println(savePath);

	                        BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入流
	                        BufferedOutputStream out = new BufferedOutputStream(
	                                new FileOutputStream(new File(filePath)));// 获得文件输出流
	                        Streams.copy(in, out, true);// 开始把文件写到指定的上传文件夹
	                        in.close();
	                        out.close();
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }*/


	        /****************************************************************/
	        try {

	            System.out.println("开始");


	            String savePath = request.getSession().getServletContext().getRealPath("/movie-imgs/");//存储路径
	            File myPath = new File(savePath);
	            if ( !myPath.exists()){//若此目录不存在，则创建之// 这个东西只能一级建立文件夹，两级是无法建立的。。。。。
	                myPath.mkdir();}
	            System.out.println(ServletFileUpload.isMultipartContent(request));
	            System.out.println(savePath);

	            String fileName = file.getOriginalFilename();

	            System.out.println(fileName);

	            BufferedInputStream bufrs = new BufferedInputStream(file.getInputStream());
	            BufferedOutputStream out = new BufferedOutputStream(
	                    new FileOutputStream(new File(savePath,fileName)));// 获得文件输出流
	            byte[] by = new byte[1024];
	            int ch = 0;
	            while ((ch = bufrs.read(by)) != -1) {
	                out.write(by);


	            }
	            out.flush();
	            out.close();
	            bufrs.close();

	            returnMap.put("success", "sucess");
	            ;
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return returnMap;
	    }
	  	
}
